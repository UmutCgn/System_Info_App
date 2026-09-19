package com.System_Info_App;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OperatingSystem;
import oshi.software.os.OSProcess;
import java.util.List;


public class mySensors {
    SystemInfo systemInfo = new SystemInfo();
    HardwareAbstractionLayer hardware = systemInfo.getHardware();

    private double cpuTemperature;
    private CentralProcessor processor;
    private boolean isCharging;
    private final double chargingpercent;
    private double cpuPercent;
    private double maxFreq;
    private long[] oldTicks;

    private NetworkIF aktifAg;
    private long eskiGelenBayt;
    private long eskiGidenBayt;
    private long eskiZaman;

    public mySensors(){
        this.isCharging =hardware.getPowerSources().getFirst().isCharging();
        this.processor = hardware.getProcessor();
        this.chargingpercent = hardware.getPowerSources().getFirst().getRemainingCapacityPercent() * 100;
        this.oldTicks = processor.getSystemCpuLoadTicks();

        for (NetworkIF net : hardware.getNetworkIFs()) {

            if (net.getIPv4addr().length > 0 && net.getBytesRecv() > 0
                    && !net.getDisplayName().contains("VirtualBox")
                    && !net.getDisplayName().contains("Hyper-V")) {

                this.aktifAg = net;
                this.aktifAg.updateAttributes();
                this.eskiGelenBayt = aktifAg.getBytesRecv();
                this.eskiGidenBayt = aktifAg.getBytesSent();
                this.eskiZaman = aktifAg.getTimeStamp();
                break;
            }
        }


    }

    public String getLiveNetworkSpeed() {
        if (aktifAg == null) return "İnternet Bağlantısı Yok";

        aktifAg.updateAttributes();

        long yeniGelenBayt = aktifAg.getBytesRecv();
        long yeniGidenBayt = aktifAg.getBytesSent();
        long yeniZaman = aktifAg.getTimeStamp();

        long zamanFarki = yeniZaman - eskiZaman;
        if (zamanFarki == 0) return "0.0 KB/s | 0.0 KB/s";


        double downloadHizi = ((yeniGelenBayt - eskiGelenBayt) * 1000.0 / zamanFarki) / 1024.0;
        double uploadHizi = ((yeniGidenBayt - eskiGidenBayt) * 1000.0 / zamanFarki) / 1024.0;

        eskiGelenBayt = yeniGelenBayt;
        eskiGidenBayt = yeniGidenBayt;
        eskiZaman = yeniZaman;

        return String.format("İndirme: %.1f KB/s  |  Yükleme: %.1f KB/s", downloadHizi, uploadHizi);
    }

    public String getLiveCPULoad(){
        double cpuLoad = processor.getSystemCpuLoadBetweenTicks(oldTicks) * 100;
        oldTicks = processor.getSystemCpuLoadTicks();
        return String.format("%%% .1f", cpuLoad);
    }
    public String getLiveCPUFrequency() {
        long[] freqs = processor.getCurrentFreq();

        long toplamFrekans = 0;
        for (long f : freqs) {
            toplamFrekans += f;
        }
        double ortalamaHz = (double) toplamFrekans / freqs.length;
        double ortalamaGHz = ortalamaHz / 1000000000.0;

        return String.format("%.2f GHz", ortalamaGHz);
    }
    public String getMaxFrequency(){
        this.maxFreq = processor.getMaxFreq() / 1000000000.0;
        return String.format("%.2f GHz", maxFreq );

    }

    public String RamList() {
        OperatingSystem os = systemInfo.getOperatingSystem();
        List<OSProcess> procs = os.getProcesses(null, OperatingSystem.ProcessSorting.RSS_DESC, 25);
        StringBuilder sb = new StringBuilder("<html>");

        int sayac = 1;
        for (int i = 0; i < procs.size(); i++) {
            if (sayac > 18) break;

            OSProcess p = procs.get(i);

            String isim = p.getName().replace("<", "").replace(">", "");

            double ramKullanimiMB = p.getResidentSetSize() / 1048576.0;

            sb.append(sayac).append(". ").append(isim)
                    .append(" : <font color=\"#FF9800\"><b>").append(String.format("%.1f MB", ramKullanimiMB))
                    .append("</b></font><br><br>");

            sayac++;
        }

        sb.append("</html>");
        return sb.toString();
    }
    public String CpuList() {
        OperatingSystem os = systemInfo.getOperatingSystem();
        int cekirdekSayisi = systemInfo.getHardware().getProcessor().getLogicalProcessorCount();

        List<OSProcess> procs = os.getProcesses(null, OperatingSystem.ProcessSorting.CPU_DESC, 25);
        StringBuilder sb = new StringBuilder("<html>");

        int sayac = 1;
        for (int i = 0; i < procs.size(); i++) {
            if (sayac > 18) break;
            OSProcess p = procs.get(i);
            String isim = p.getName();
            if (isim.equalsIgnoreCase("Idle") || isim.equalsIgnoreCase("System Idle Process")) {
                continue;
            }

            double cpuYuzdesi = (100d * (p.getKernelTime() + p.getUserTime()) / Math.max(1L, p.getUpTime())) / cekirdekSayisi;

            sb.append(sayac).append(". ").append(isim)
                    .append(" : <font color='#FF9800'><b>%").append(String.format("%.1f", cpuYuzdesi))
                    .append("</b></font><br><br>");

            sayac++;
        }

        sb.append("</html>");
        return sb.toString();
    }



    public void Display(){
        System.out.println("Cpu Derecesi: "+ cpuTemperature);
        System.out.println("Şarj yüzdesi: " + chargingpercent);
        System.out.println("Şarjda ?: "+ isCharging);


    }

    public String getMySensors(){
        this.cpuTemperature = hardware.getSensors().getCpuTemperature();


        return String.format("%.2f C" , cpuTemperature);
    }
    public double getCpuPercent(){return cpuPercent;}
    public double getmaxFreq(){return maxFreq;}

    public double getChargingpercent(){return chargingpercent;}

}
