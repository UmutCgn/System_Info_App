
package com.System_Info_App;
import javax.swing.SwingUtilities;
import com. formdev.flatlaf.FlatDarkLaf;
import oshi.hardware.*;



public class Main {
    public static void main(String[] args) {
        FlatDarkLaf.setup();
        SwingUtilities.invokeLater(() -> {
            DashboardUI ui = new DashboardUI();
            ui.setVisible(true);
        });

        mySensors m1 = new mySensors();
        m1.Display();
    }
}
