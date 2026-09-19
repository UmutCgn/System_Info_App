package com.System_Info_App;

import javax.swing.*;
import java.awt.*;
import javax.swing.Timer;

public class DashboardUI extends JFrame {
    private mySystem systemData;
    private mySensors liveSystemData;


    private JLabel lblRamValue;
    private JLabel lblCpuLoad;
    private JLabel lblCpuFreq;
    private JLabel lblCpuMaxFreq;
    private JLabel lblLiveRam;
    private  JLabel lblNetworkSpeed;
    private JLabel lblTopRamApps;
    private JLabel lblTopCpuApps;
    private JLabel lblDescription;

    public DashboardUI() {
        systemData = new mySystem();
        liveSystemData = new mySensors();
        setTitle("Sistem Monitörü");
        setSize(650, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JPanel anaPanel = createAnaPanel();
        tabbedPane.addTab("Genel Bakış", anaPanel);

        JPanel secondPanel = createSecondPanel();
        tabbedPane.addTab("Canlı İzleme", secondPanel);
        JPanel thirdPanel = createAppsPanel();
        tabbedPane.addTab("Uygulamalar",thirdPanel);
        JPanel settingsPanel = createSettingsPanel();
        tabbedPane.addTab("Ayarlar", settingsPanel);
        JPanel toolsPanel = createToolsPanel(tabbedPane);
        tabbedPane.addTab("Araçlar", createToolsPanel(tabbedPane));
        JPanel descriptionPanel = createDescriptionPanel();
        tabbedPane.addTab("Açıklama",descriptionPanel);

        add(tabbedPane, BorderLayout.CENTER);
        JButton btnGlobalSS = new JButton("Sadece Bu Sekmenin Fotoğrafını Çek");
        btnGlobalSS.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnGlobalSS.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnGlobalSS.addActionListener(e -> alSekmeSS(tabbedPane));


        add(btnGlobalSS, BorderLayout.SOUTH);


        Timer timer = new Timer(1000, e -> {
            lblRamValue.setText(systemData.getLiveRAM());
            if (lblCpuLoad != null) {
                lblCpuLoad.setText(liveSystemData.getLiveCPULoad());
            }
            if (lblCpuFreq != null) {
                lblCpuFreq.setText(liveSystemData.getLiveCPUFrequency());
            }
            if(lblNetworkSpeed != null){
                lblNetworkSpeed.setText(liveSystemData.getLiveNetworkSpeed());
            }
            if (lblTopRamApps != null) {
                lblTopRamApps.setText(liveSystemData.RamList());
            }
            if (lblTopCpuApps != null) {
                lblTopCpuApps.setText(liveSystemData.CpuList());
            }

        });
        timer.start();
    }

    private JPanel createAnaPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));


        lblRamValue = new JLabel(systemData.getMyRAM_string());


        panel.add(createRow("İşlemci:", new JLabel(systemData.getMyCPU_string())));
        panel.add(createRow("RAM:", new JLabel(systemData.getMyRAM_string())));
        panel.add(createRow("Ekran Kartı 1:", new JLabel(systemData.getMyGPU_string())));
        panel.add(createRow("Ekran Kartı 2:", new JLabel(systemData.getMyGPU_string2())));
        panel.add(createRow("Disk:", new JLabel(String.format("%.2f TB", systemData.getMyDisk_string()))));

        return panel;
    }
    private JPanel createSecondPanel() {
        JPanel panel = new JPanel(new GridLayout(9, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));


        lblCpuLoad = new JLabel("Hesaplanıyor...");
        lblCpuFreq = new JLabel("Hesaplanıyor...");
        lblCpuMaxFreq = new JLabel(liveSystemData.getMaxFrequency());
        lblLiveRam = new JLabel(systemData.getLiveRAM());
        lblNetworkSpeed = new JLabel("Hesaplanıyor...");

        panel.add(createRow("Anlık İşlemci Kullanımı:", lblCpuLoad));
        panel.add(createRow("Anlık İşlemci Frekansı:", lblCpuFreq));
        panel.add(createRow("Temel İşlemci Frekansı: ",lblCpuMaxFreq));
        panel.add(createRow("Ram Kullanımı: ", lblLiveRam));
        panel.add(createRow("Ağ Kullanımı:", lblNetworkSpeed));
        panel.add(createRow("Batarya Doluluğu:", new JLabel(String.format("%% %.0f", liveSystemData.getChargingpercent()))));
        panel.add(createRow("Şarjda mı?:", new JLabel(liveSystemData.getChargingpercent() < 100 ? "Evet / Pil Doluyor" : "Tam Dolu")));




        return panel;
    }
    private JPanel createAppsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 30, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));


        JPanel ramPanel = new JPanel(new BorderLayout());
        JLabel titleRam = new JLabel("En Çok RAM Tüketenler");
        titleRam.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleRam.setForeground(new Color(180, 180, 180));
        titleRam.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, new Color(230, 230, 230)));

        lblTopRamApps = new JLabel("Yükleniyor...");
        lblTopRamApps.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblTopRamApps.setVerticalAlignment(SwingConstants.TOP);
        ramPanel.add(titleRam, BorderLayout.NORTH);
        ramPanel.add(lblTopRamApps, BorderLayout.CENTER);


        JPanel cpuPanel = new JPanel(new BorderLayout());
        JLabel titleCpu = new JLabel("En Çok CPU Tüketenler");
        titleCpu.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleCpu.setForeground(new Color(180, 180, 180));
        titleCpu.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, new Color(230, 230, 230)));

        lblTopCpuApps = new JLabel("Yükleniyor...");
        lblTopCpuApps.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblTopCpuApps.setVerticalAlignment(SwingConstants.TOP);
        cpuPanel.add(titleCpu, BorderLayout.NORTH);
        cpuPanel.add(lblTopCpuApps, BorderLayout.CENTER);


        panel.add(ramPanel);
        panel.add(cpuPanel);

        return panel;
    }

    private JPanel createSettingsPanel() {
        JPanel panel = new JPanel(new GridLayout(9, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JCheckBox chkAlwaysOnTop = new JCheckBox("Aktif Et");
        chkAlwaysOnTop.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        chkAlwaysOnTop.setFocusPainted(false);
        chkAlwaysOnTop.setCursor(new Cursor(Cursor.HAND_CURSOR));
        chkAlwaysOnTop.addActionListener(e -> setAlwaysOnTop(chkAlwaysOnTop.isSelected()));

        panel.add(createSettingRow("Pencereyi Her Zaman Üstte Tut:", chkAlwaysOnTop));

        String[] temalar = {"Karanlık Tema", "Aydınlık Tema"};
        JComboBox<String> cmbTheme = new JComboBox<>(temalar);
        cmbTheme.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbTheme.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmbTheme.addActionListener(e -> {
            if (cmbTheme.getSelectedIndex() == 0) {
                com.formdev.flatlaf.FlatDarkLaf.setup();
            } else {
                com.formdev.flatlaf.FlatLightLaf.setup();
            }
            SwingUtilities.updateComponentTreeUI(this);
        });

        panel.add(createSettingRow("Arayüz Teması:", cmbTheme));


        JLabel lblVersion = new JLabel("v1.0.0 (Beta)");
        lblVersion.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblVersion.setForeground(new Color(255, 152, 0));
        panel.add(createSettingRow("Uygulama Sürümü:", lblVersion));

        return panel;
    }
    private JPanel createToolsPanel(JTabbedPane tabbedPane) {
        JPanel panel = new JPanel(new GridLayout(2, 1, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JButton btnOyunModu = new JButton("Oyun Modunu Başlat (Mini Ekran)");
        btnOyunModu.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnOyunModu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnOyunModu.addActionListener(e -> baslatOyunModu());
        panel.add(btnOyunModu);


        return panel;
    }

    private JPanel createDescriptionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel lblTitle = new JLabel("Açıklama");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitle.setForeground(new Color(180, 180, 180));
        lblTitle.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));

        String descriptionText = "<html><br>Bu bir test uygulamasıdır. Bu uygulama JAVA dilini hatırlama amacıyla<br>" +
                "<b>Umut Nebi Çağan</b> tarafından geliştirilmiştir.<br><br>" +
                "Java Kütüphanesi olan OSHI ile yapılan bu uygulama, kütüphanenin izin verdiği kadarıyla<br>" +
                "bilgisayarın özelliklerine ve anlık sistem verilerine erişmeyi hedeflemektedir.</html>";

        JLabel lblDescription = new JLabel(descriptionText);
        lblDescription.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblDescription.setVerticalAlignment(SwingConstants.TOP);
        lblDescription.setBorder(BorderFactory.createEmptyBorder(10, 5, 0, 0));
        panel.add(lblTitle, BorderLayout.NORTH);
        panel.add(lblDescription, BorderLayout.CENTER);

        return panel;
    }
    private void baslatOyunModu() {
        JFrame miniFrame = new JFrame("Oyun Modu");
        miniFrame.setSize(250, 120);
        miniFrame.setAlwaysOnTop(true);
        miniFrame.setResizable(true);
        miniFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        miniFrame.setLocationRelativeTo(null);


        JPanel miniPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        miniPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel lblMiniCpu = new JLabel("CPU: Yükleniyor...");
        lblMiniCpu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblMiniCpu.setForeground(new Color(255, 152, 0));

        JLabel lblMiniCpuT = new JLabel("Yükleniyor..");
        lblMiniCpuT.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblMiniCpuT.setForeground(new Color(255, 152, 0));

        JLabel lblMiniRam = new JLabel("RAM: Yükleniyor...");
        lblMiniRam.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblMiniRam.setForeground(new Color(255, 152, 0));

        miniPanel.add(lblMiniCpu);
        miniPanel.add(lblMiniCpuT);
        miniPanel.add(lblMiniRam);

        miniFrame.add(miniPanel);




        Timer miniTimer = new Timer(1000, e -> {
            lblMiniCpu.setText("CPU: " + liveSystemData.getLiveCPULoad());
            lblMiniCpuT.setText("CPU T :"+ liveSystemData.getMySensors());
            lblMiniRam.setText("RAM: " + systemData.getLiveRAM().split("/")[0].trim());

        });
        miniTimer.start();


        miniFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                miniTimer.stop();
            }
        });

        miniFrame.setVisible(true);
    }
    private void alSekmeSS(JTabbedPane tabbedPane) {
        try {
            java.awt.Component aktifSekme = tabbedPane.getSelectedComponent();
            java.awt.image.BufferedImage goruntu = new java.awt.image.BufferedImage(
                    aktifSekme.getWidth(), aktifSekme.getHeight(), java.awt.image.BufferedImage.TYPE_INT_RGB);

            java.awt.Graphics g = goruntu.getGraphics();
            aktifSekme.paint(g);
            g.dispose();

            String dosyaAdi = "Sistem_Monitörü_" + System.currentTimeMillis() + ".png";
            javax.imageio.ImageIO.write(goruntu, "png", new java.io.File(dosyaAdi));

            JOptionPane.showMessageDialog(this,
                    "Arka plan kırpıldı, sadece sekme kaydedildi!\nDosya: " + dosyaAdi,
                    "Başarılı", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Hata oluştu: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createSettingRow(String title, JComponent control) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(10, 5, 10, 5)
        ));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitle.setForeground(new Color(180, 180, 180));

        panel.add(lblTitle, BorderLayout.WEST);
        panel.add(control, BorderLayout.EAST);
        return panel;
    }



    private JPanel createRow(String title, JLabel lblValue) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)), // Çizgi
                BorderFactory.createEmptyBorder(10, 5, 10, 5)
        ));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitle.setForeground(new Color(180, 180, 180));


        lblValue.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblValue.setForeground(new Color(255, 152, 0));

        panel.add(lblTitle, BorderLayout.WEST);
        panel.add(lblValue, BorderLayout.EAST);
        return panel;
    }
}