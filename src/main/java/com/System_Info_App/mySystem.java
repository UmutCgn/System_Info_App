package com.System_Info_App;
import oshi.SystemInfo;
import oshi.hardware.*;


import java.util.ArrayList;

public class mySystem {
    private final CentralProcessor myCPU;
    private final GlobalMemory myRAM;
    private final ComputerSystem myOS;
    private final ArrayList myDisks = new ArrayList<HWDiskStore>();
    private final ArrayList myGPUs = new ArrayList<GraphicsCard>();


    SystemInfo systemInfo = new SystemInfo();
    HardwareAbstractionLayer hardware = systemInfo.getHardware();
    private final int myBattery = hardware.getPowerSources().getFirst().getCurrentCapacity();

    //to string değişkenleri
    private final String myCPU_string;
    private final String myGPU_string;
    private final String myGPU_string2;
    private final String myRAM_string;
    private final double myDisk_long;

    public mySystem(){
        this.myCPU = hardware.getProcessor();
        this.myRAM = hardware.getMemory();
        this.myGPUs.addAll(hardware.getGraphicsCards());
        this.myOS = hardware.getComputerSystem();
        this.myDisks.addAll(hardware.getDiskStores());
        this.myCPU_string = hardware.getProcessor().getProcessorIdentifier().getName().toString();
        this.myGPU_string = hardware.getGraphicsCards().get(0).getName();
        this.myGPU_string2 = hardware.getGraphicsCards().get(1).getName();
        this.myRAM_string = hardware.getMemory().toString().substring(20);
        this.myDisk_long = hardware.getDiskStores().getFirst().getSize()/1000000000000L;

    }
    public void Display(){
        System.out.println("İşlemci: ");
        System.out.println("  " +myCPU_string);
        System.out.println("Grafik Kartları: ");
        System.out.println("  " +myGPU_string);
        System.out.println("  " +myGPU_string2);
        System.out.println("RAM: ");
        System.out.println("  " + myRAM_string.substring(20));
        System.out.println("Disk: ");
        System.out.println("  "+ myDisk_long+ " TB");
        System.out.println("Batarya: ");
        System.out.println("  "+ myBattery+ " mAh");


    }
    public void Display_Detailed(){
        System.out.println("Bilgisayar Sistemi: " + myOS);
        System.out.println("İşlemci: " +"\n\n"+ myCPU);
        System.out.println("Ram: " +"\n\n"+ myRAM);
        System.out.println("Ekran Kart(lar)ı: "+"\n");
        for (Object gpu: myGPUs){
            int n = 1;
            System.out.println(n+"."+": "+gpu);

        }
        System.out.println("");

        System.out.println("Diskler: "+"\n");
        for (Object disk: myDisks){
            int n = 1;
            System.out.println(n+"."+": "+disk);

        }
        System.out.println("");
    }

    //GETTER
    public String getMyCPU_string(){
        return myCPU_string;
    }
    public String getMyGPU_string(){
        return myGPU_string;
    }
    public String getMyGPU_string2(){
        return myGPU_string2;
    }
    public String getMyRAM_string(){
        return myRAM_string;
    }
    public double getMyDisk_string(){
        return myDisk_long;
    }

    public CentralProcessor getMyCPU() {
        return myCPU;
    }

    public ComputerSystem getMyOS() {
        return myOS;
    }
    public GlobalMemory getMyRAM(){
        return myRAM;
    }

    public ArrayList getMyDisks() {
        return myDisks;
    }

    public ArrayList getMyGPUs() {
        return myGPUs;
    }
    public String getLiveRAM() {
        return hardware.getMemory().toString();
    }
}
