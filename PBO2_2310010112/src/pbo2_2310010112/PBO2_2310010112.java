/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pbo2_2310010112;

// Impor frame_dashboard dari paket frame
import frame.frame_dashboard;

/**
 *
 * @author User
 */
public class PBO2_2310010112 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Menjalankan frame_dashboard
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frame_dashboard().setVisible(true);
            }
        });
    }
    
}