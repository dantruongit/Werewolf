/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Client;

import Client.Session.Service;
import Client.utils.gui;
import config.Constaint;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import Model.*;
import java.util.ArrayList;

/**
 *
 * @author COMPUTER
 */
public class Create_Room extends javax.swing.JPanel {
    
    private int getCurrentPlayerSetting(){
        int i = 0;
        for(RoleUI u : ui){
            i += u.count;
        }
        return i;
    }
    
    private void calculate(int index){
        try {
            int current = getCurrentPlayerSetting();
            boolean isSuccess = false;
            int i = 0;
            if(current < 9){
                while(isSuccess == false){
                    if(i != index && ui[i].count < ui[i].max_required){
                        ui[i].setCount(ui[i].count + 1);
                        isSuccess = true;
                        break;
                    }
                    i++;
                }
            }
            else if(current > 9){
                while(isSuccess == false){
                    if(i != index && ui[i].count > ui[i].min_required){
                        ui[i].setCount(ui[i].count - 1);
                        isSuccess = true;
                        break;
                    }
                    i++;
                }
            }
        } catch (Exception e) {
        }
    }
    
    private class RoleUI{
        public int index;
        public  JButton btnUp, btnDown;
        public  JLabel quantity;
        public int count = 0;
        public int max_required = 1, min_required = 0;
        
        public void setBtnDecrease(JButton btnDown) {
            this.btnDown = btnDown;
            btnDown.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    count--;
                    if(count < min_required) count = min_required;
                    update();
                }
            });
        }

        public void setBtnIncrease(JButton btnUp) {
            this.btnUp = btnUp;
            btnUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                count++;
                if(count > max_required) count = max_required;
                update();
            }
        });
        }

        public void setQuantity(JLabel quantity) {
            this.quantity = quantity;
            this.quantity.setFont(new Font("Arial", Font.BOLD, 16));
            this.quantity.setForeground(Color.red);
            count = min_required;
            update();
        }
        
        public void setCount(int count){
            this.count = count;
            update();
        }
        
        public void update(){
            calculate(index);
            this.quantity.setText("" + count);
        }

        public void setMax_required(int max_required) {
            this.max_required = max_required;
        }

        public void setMin_required(int min_required) {
            this.min_required = min_required;
        }
        
        public void setConfig(int min, int max){
            setMax_required(max);
            setMin_required(min);
            count = min ;
            update();
        }
        
        public void setVisible(boolean flag){
            this.btnDown.setVisible(flag);
            this.btnUp.setVisible(flag);
            if(flag){
                this.quantity.setText(count + "");
            }
            else{
                this.quantity.setText("?");
            }
        }
    }
    
    private final RoleUI[] ui  = new RoleUI[10];
    
    private void loadConfig(){
        List<Role> roles = (List<Role>)Service.gI().dataSource.roleConfigs;
        if(roles != null)
            for(int i = 0; i < 10 ; i++){
               Role r = roles.get(i);
               ui[i].setConfig(r.requiredMin, r.requiredMax);
               ui[i].index = i;
            }
    }
    
    public void startUI(){
        var x = new JLabel[]{avt1, avt2, avt3, avt5, avt6, avt7, avt9, avt10, avt11, avt14};
        for(int i = 0 ; i < 10; i++){
            String path = String.format("%s/assets/role%d.png", Constaint.pathRoot, i);
            x[i].setIcon(gui.resizeImage(path, 100, 113));
            ui[i] = new RoleUI();
        }
        
        ui[0].setBtnDecrease(btn11);ui[0].setBtnIncrease(btn12);ui[0].setQuantity(label1);
        ui[1].setBtnDecrease(btn21);ui[1].setBtnIncrease(btn22);ui[1].setQuantity(label2);
        ui[2].setBtnDecrease(btn31);ui[2].setBtnIncrease(btn32);ui[2].setQuantity(label3);
        ui[3].setBtnDecrease(btn41);ui[3].setBtnIncrease(btn42);ui[3].setQuantity(label4);
        ui[4].setBtnDecrease(btn51);ui[4].setBtnIncrease(btn52);ui[4].setQuantity(label5);
        ui[5].setBtnDecrease(btn61);ui[5].setBtnIncrease(btn62);ui[5].setQuantity(label6);
        ui[6].setBtnDecrease(btn71);ui[6].setBtnIncrease(btn72);ui[6].setQuantity(label7);
        ui[7].setBtnDecrease(btn81);ui[7].setBtnIncrease(btn82);ui[7].setQuantity(label8);
        ui[8].setBtnDecrease(btn91);ui[8].setBtnIncrease(btn92);ui[8].setQuantity(label9);
        ui[9].setBtnDecrease(btn101);ui[9].setBtnIncrease(btn102);ui[9].setQuantity(label10);
    }
    
    public Create_Room() {
        initComponents();
        startUI();
        loadConfig();
        ui[0].setCount(7);
        ui[8].setCount(2);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn11 = new javax.swing.JButton();
        btn12 = new javax.swing.JButton();
        avt1 = new javax.swing.JLabel();
        label1 = new javax.swing.JLabel();
        btnThoatPhong = new javax.swing.JButton();
        btnTaoPhong = new javax.swing.JButton();
        avt2 = new javax.swing.JLabel();
        label2 = new javax.swing.JLabel();
        avt3 = new javax.swing.JLabel();
        label3 = new javax.swing.JLabel();
        avt5 = new javax.swing.JLabel();
        label4 = new javax.swing.JLabel();
        avt6 = new javax.swing.JLabel();
        label5 = new javax.swing.JLabel();
        avt7 = new javax.swing.JLabel();
        label6 = new javax.swing.JLabel();
        avt9 = new javax.swing.JLabel();
        label7 = new javax.swing.JLabel();
        avt10 = new javax.swing.JLabel();
        label8 = new javax.swing.JLabel();
        avt11 = new javax.swing.JLabel();
        label9 = new javax.swing.JLabel();
        avt14 = new javax.swing.JLabel();
        label10 = new javax.swing.JLabel();
        btn21 = new javax.swing.JButton();
        btn22 = new javax.swing.JButton();
        btn31 = new javax.swing.JButton();
        btn32 = new javax.swing.JButton();
        btn41 = new javax.swing.JButton();
        btn42 = new javax.swing.JButton();
        btn51 = new javax.swing.JButton();
        btn52 = new javax.swing.JButton();
        btn61 = new javax.swing.JButton();
        btn62 = new javax.swing.JButton();
        btn71 = new javax.swing.JButton();
        btn72 = new javax.swing.JButton();
        btn81 = new javax.swing.JButton();
        btn82 = new javax.swing.JButton();
        btn91 = new javax.swing.JButton();
        btn92 = new javax.swing.JButton();
        btn101 = new javax.swing.JButton();
        btn102 = new javax.swing.JButton();
        checkboxRandom = new javax.swing.JCheckBox();
        checkboxShowRole = new javax.swing.JCheckBox();

        setLayout(null);

        btn11.setText("-");
        btn11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn11ActionPerformed(evt);
            }
        });
        add(btn11);
        btn11.setBounds(50, 150, 40, 22);

        btn12.setText("+");
        add(btn12);
        btn12.setBounds(110, 150, 40, 22);

        avt1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        add(avt1);
        avt1.setBounds(50, 30, 100, 113);

        label1.setText("Số");
        add(label1);
        label1.setBounds(130, 30, 17, 16);

        btnThoatPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/exit.png"))); // NOI18N
        btnThoatPhong.setText("Thoát Phòng");
        btnThoatPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatPhongActionPerformed(evt);
            }
        });
        add(btnThoatPhong);
        btnThoatPhong.setBounds(610, 330, 190, 50);

        btnTaoPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/create.png"))); // NOI18N
        btnTaoPhong.setText("Tạo phòng");
        btnTaoPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoPhongActionPerformed(evt);
            }
        });
        add(btnTaoPhong);
        btnTaoPhong.setBounds(610, 400, 190, 50);

        avt2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        add(avt2);
        avt2.setBounds(170, 30, 100, 113);

        label2.setText("Số");
        add(label2);
        label2.setBounds(250, 30, 17, 16);

        avt3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        add(avt3);
        avt3.setBounds(290, 30, 100, 113);

        label3.setText("Số");
        add(label3);
        label3.setBounds(370, 30, 17, 16);

        avt5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        add(avt5);
        avt5.setBounds(50, 190, 100, 113);

        label4.setText("Số");
        add(label4);
        label4.setBounds(130, 190, 17, 16);

        avt6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        add(avt6);
        avt6.setBounds(170, 190, 100, 113);

        label5.setText("Số");
        add(label5);
        label5.setBounds(250, 190, 17, 16);

        avt7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        add(avt7);
        avt7.setBounds(290, 190, 100, 113);

        label6.setText("Số");
        add(label6);
        label6.setBounds(370, 190, 17, 16);

        avt9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        add(avt9);
        avt9.setBounds(50, 340, 100, 113);

        label7.setText("Số");
        add(label7);
        label7.setBounds(130, 340, 17, 16);

        avt10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        add(avt10);
        avt10.setBounds(170, 340, 100, 113);

        label8.setText("Số");
        add(label8);
        label8.setBounds(250, 340, 17, 16);

        avt11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        add(avt11);
        avt11.setBounds(290, 340, 100, 113);

        label9.setText("Số");
        add(label9);
        label9.setBounds(370, 340, 17, 16);

        avt14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        add(avt14);
        avt14.setBounds(410, 340, 100, 113);

        label10.setText("Số");
        add(label10);
        label10.setBounds(490, 340, 17, 16);

        btn21.setText("-");
        btn21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn21ActionPerformed(evt);
            }
        });
        add(btn21);
        btn21.setBounds(170, 150, 40, 22);

        btn22.setText("+");
        add(btn22);
        btn22.setBounds(230, 150, 40, 22);

        btn31.setText("-");
        btn31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn31ActionPerformed(evt);
            }
        });
        add(btn31);
        btn31.setBounds(290, 150, 40, 22);

        btn32.setText("+");
        add(btn32);
        btn32.setBounds(350, 150, 40, 22);

        btn41.setText("-");
        btn41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn41ActionPerformed(evt);
            }
        });
        add(btn41);
        btn41.setBounds(50, 310, 40, 22);

        btn42.setText("+");
        add(btn42);
        btn42.setBounds(110, 310, 40, 22);

        btn51.setText("-");
        btn51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn51ActionPerformed(evt);
            }
        });
        add(btn51);
        btn51.setBounds(170, 310, 40, 22);

        btn52.setText("+");
        add(btn52);
        btn52.setBounds(230, 310, 40, 22);

        btn61.setText("-");
        btn61.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn61ActionPerformed(evt);
            }
        });
        add(btn61);
        btn61.setBounds(290, 310, 40, 22);

        btn62.setText("+");
        add(btn62);
        btn62.setBounds(350, 310, 40, 22);

        btn71.setText("-");
        btn71.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn71ActionPerformed(evt);
            }
        });
        add(btn71);
        btn71.setBounds(50, 460, 40, 22);

        btn72.setText("+");
        add(btn72);
        btn72.setBounds(110, 460, 40, 22);

        btn81.setText("-");
        btn81.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn81ActionPerformed(evt);
            }
        });
        add(btn81);
        btn81.setBounds(170, 460, 40, 22);

        btn82.setText("+");
        add(btn82);
        btn82.setBounds(230, 460, 40, 22);

        btn91.setText("-");
        btn91.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn91ActionPerformed(evt);
            }
        });
        add(btn91);
        btn91.setBounds(290, 460, 40, 22);

        btn92.setText("+");
        add(btn92);
        btn92.setBounds(350, 460, 40, 22);

        btn101.setText("-");
        btn101.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn101ActionPerformed(evt);
            }
        });
        add(btn101);
        btn101.setBounds(410, 460, 40, 22);

        btn102.setText("+");
        add(btn102);
        btn102.setBounds(470, 460, 40, 22);

        checkboxRandom.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        checkboxRandom.setText("Ngẫu nhiên");
        checkboxRandom.setFocusable(false);
        checkboxRandom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxRandomActionPerformed(evt);
            }
        });
        add(checkboxRandom);
        checkboxRandom.setBounds(420, 280, 150, 24);

        checkboxShowRole.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        checkboxShowRole.setText("Hiện vai trò khi chết");
        checkboxShowRole.setFocusable(false);
        checkboxShowRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxShowRoleActionPerformed(evt);
            }
        });
        add(checkboxShowRole);
        checkboxShowRole.setBounds(420, 240, 170, 24);
    }// </editor-fold>//GEN-END:initComponents

    private void btn11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn11ActionPerformed

    private void btnThoatPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatPhongActionPerformed
        Service.gI().frm.changePanel(new HomePanel());
    }//GEN-LAST:event_btnThoatPhongActionPerformed

    private void btn21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn21ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn21ActionPerformed

    private void btn31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn31ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn31ActionPerformed

    private void btn41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn41ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn41ActionPerformed

    private void btn51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn51ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn51ActionPerformed

    private void btn61ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn61ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn61ActionPerformed

    private void btn71ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn71ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn71ActionPerformed

    private void btn81ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn81ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn81ActionPerformed

    private void btn91ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn91ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn91ActionPerformed

    private void btn101ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn101ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn101ActionPerformed

    private void btnTaoPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoPhongActionPerformed
        Room room = new Room();
        List<Role> roleConfigs = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Role r = new Role();
            r.idRole = i;
            r.quantity = ui[i].count;
            roleConfigs.add(r);
        }
        room.configs = roleConfigs;
        room.isRandom = checkboxRandom.isSelected();
        room.isShowRoleWhenDie = checkboxShowRole.isSelected();
        Service.gI().sendMessage(Constaint.MESSAGE_CREATE_ROOM, room);
    }//GEN-LAST:event_btnTaoPhongActionPerformed

    private void checkboxRandomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxRandomActionPerformed
        for(var u : ui){
            u.setVisible(!checkboxRandom.isSelected());
        }
    }//GEN-LAST:event_checkboxRandomActionPerformed

    private void checkboxShowRoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxShowRoleActionPerformed
        for(var u : ui){
            u.setVisible(!checkboxRandom.isSelected());
        }
    }//GEN-LAST:event_checkboxShowRoleActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel avt1;
    private javax.swing.JLabel avt10;
    private javax.swing.JLabel avt11;
    private javax.swing.JLabel avt14;
    private javax.swing.JLabel avt2;
    private javax.swing.JLabel avt3;
    private javax.swing.JLabel avt5;
    private javax.swing.JLabel avt6;
    private javax.swing.JLabel avt7;
    private javax.swing.JLabel avt9;
    private javax.swing.JButton btn101;
    private javax.swing.JButton btn102;
    private javax.swing.JButton btn11;
    private javax.swing.JButton btn12;
    private javax.swing.JButton btn21;
    private javax.swing.JButton btn22;
    private javax.swing.JButton btn31;
    private javax.swing.JButton btn32;
    private javax.swing.JButton btn41;
    private javax.swing.JButton btn42;
    private javax.swing.JButton btn51;
    private javax.swing.JButton btn52;
    private javax.swing.JButton btn61;
    private javax.swing.JButton btn62;
    private javax.swing.JButton btn71;
    private javax.swing.JButton btn72;
    private javax.swing.JButton btn81;
    private javax.swing.JButton btn82;
    private javax.swing.JButton btn91;
    private javax.swing.JButton btn92;
    private javax.swing.JButton btnTaoPhong;
    private javax.swing.JButton btnThoatPhong;
    private javax.swing.JCheckBox checkboxRandom;
    private javax.swing.JCheckBox checkboxShowRole;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel label10;
    private javax.swing.JLabel label2;
    private javax.swing.JLabel label3;
    private javax.swing.JLabel label4;
    private javax.swing.JLabel label5;
    private javax.swing.JLabel label6;
    private javax.swing.JLabel label7;
    private javax.swing.JLabel label8;
    private javax.swing.JLabel label9;
    // End of variables declaration//GEN-END:variables
}
