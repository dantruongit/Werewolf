package Client;

import Client.utils.gui;
import Model.Role;
import Model.Stage;
import config.Constaint;
import java.util.List;
import javax.swing.JLabel;

/**
 *
 * @author cr4zyb0t
 */
public class PanelTime extends javax.swing.JPanel {
    private Stage stage;
    private class CountDown implements Runnable{
        private int delay;
        public CountDown(int delay){
            this.delay = delay;
        }
        @Override
        public void run() {
            while(delay >= 0){
                labelThongBao.setText(stage.message + " " + delay/1000 + "s");
                delay -= 1000;
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
            }
        }
        
    }
    
    public PanelTime(Stage stage) {
        initComponents();
        this.stage = stage;
        String url = Constaint.pathRoot + "/assets/";
        switch(stage.time){
            case Constaint.STAGE_SLEEPING:{
                url+="status_moon.png";
                break;
            }
            case Constaint.STAGE_DISCUSSING:
            case Constaint.STAGE_VOTING:{
                url += "status_sun.png";
                break;
            }
        }
        icon.setIcon(gui.resizeImage(url, 90, 90));
        new Thread(new CountDown(stage.miliseconds)).start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelThongBao = new javax.swing.JLabel();
        icon = new javax.swing.JLabel();

        labelThongBao.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        labelThongBao.setText("Thời gian thảo luận 34s");

        icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/status_moon.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(labelThongBao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(icon, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(labelThongBao)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(icon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel icon;
    private javax.swing.JLabel labelThongBao;
    // End of variables declaration//GEN-END:variables
}
