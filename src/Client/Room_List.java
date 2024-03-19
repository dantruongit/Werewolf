/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Client;

import Client.Session.Service;
import config.Constaint;
import java.util.List;
import Model.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author COMPUTER
 */
public class Room_List extends javax.swing.JPanel {

    private DefaultTableModel model;
    
    private void loadTable(){
        model = (DefaultTableModel)mainTable.getModel();
        model.setRowCount(0);
        List<Room> rooms = Service.gI().dataSource.rooms;
        if(rooms != null){
            System.out.println("Room bị null");
            for(var r: rooms){
                if(r != null)
                model.addRow(new Object[]{
                    r.idRoom,
                    r.players.size() + "/9", 
                    r.owner.namePlayer
                });
            }
        }
    }
    
    public Room_List() {
        initComponents();
        loadTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        mainTable = new javax.swing.JTable();
        btnJoin = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setLayout(null);

        mainTable.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        mainTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Số lượng người chơi", "Chủ phòng"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(mainTable);

        add(jScrollPane1);
        jScrollPane1.setBounds(56, 60, 630, 402);

        btnJoin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/join.png"))); // NOI18N
        btnJoin.setText("Tham gia");
        btnJoin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJoinActionPerformed(evt);
            }
        });
        add(btnJoin);
        btnJoin.setBounds(700, 350, 140, 50);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Danh sách phòng chơi");
        add(jLabel1);
        jLabel1.setBounds(320, 20, 220, 28);

        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/back.png"))); // NOI18N
        btnBack.setText("Quay lại");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        add(btnBack);
        btnBack.setBounds(700, 410, 140, 50);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/avatar_background_day_regular.png"))); // NOI18N
        jLabel2.setText("jLabel2");
        add(jLabel2);
        jLabel2.setBounds(0, 190, 870, 450);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/emoji_sun.png"))); // NOI18N
        add(jLabel3);
        jLabel3.setBounds(720, 20, 110, 100);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        Service.gI().frm.changePanel(new PlayPanel());
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnJoinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJoinActionPerformed
        int rowSelected = mainTable.getSelectedRow();
        if(rowSelected != -1){
            int idRoom = (int)mainTable.getValueAt(rowSelected, 0);
            Service.gI().sendMessage(Constaint.MESSAGE_JOIN_ROOM, idRoom);
        }
    }//GEN-LAST:event_btnJoinActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnJoin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable mainTable;
    // End of variables declaration//GEN-END:variables
}
