/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Client;

import Client.Session.Service;
import Client.utils.gui;
import config.Constaint;
import javax.swing.JLabel;
import Model.*;
import java.awt.Color;

public class GameRoom extends javax.swing.JPanel {
    
    private class PlayerUI{
        public int index;
        private JLabel avatar, voteIcon, count, iconRole, name;

        public void setAvatar(JLabel avatar) {
            this.avatar = avatar;
        }

        public void setVoteIcon(JLabel voteIcon) {
            this.voteIcon = voteIcon;
        }

        public void setCount(JLabel count) {
            this.count = count;
        }

        public void setIconRole(JLabel iconRole) {
            this.iconRole = iconRole;
        }

        public void setName(JLabel name) {
            this.name = name;
        }
        
        public void loadPlayer(Player p){
            int avatarId  = Utils.Utils.nextInt(1, 4);
            avatar.setIcon(gui.resizeImage(Constaint.pathRoot + "/assets/avatar" + avatarId+ ".jpg", 75, 75));
            
            name.setText(index + " " + p.namePlayer);
        }
        
    }
    private class State{
        public int idRole; 
        public JLabel label;
        public boolean isAlive = true;

        public void setLabel(JLabel label) {
            this.label = label;
        }

        public void setIsAlive(boolean isAlive) {
            this.isAlive = isAlive;
        }
        
    }
    private final State[] states = new State[10];
    private final PlayerUI[] playersUI = new PlayerUI[9];
    
    private void initGUI(){
        for(int i = 0; i < 9;i++){
            playersUI[i] = new PlayerUI();
            playersUI[i].index = i+1;
        }
        
        playersUI[0].setAvatar(avatar0);playersUI[0].setVoteIcon(hand0);playersUI[0].setCount(c0);playersUI[0].setIconRole(roleUser0);playersUI[0].setName(name0);
        playersUI[1].setAvatar(avatar1);playersUI[1].setVoteIcon(hand1);playersUI[1].setCount(c1);playersUI[1].setIconRole(roleUser1);playersUI[1].setName(name1);
        playersUI[2].setAvatar(avatar2);playersUI[2].setVoteIcon(hand2);playersUI[2].setCount(c2);playersUI[2].setIconRole(roleUser2);playersUI[2].setName(name2);
        playersUI[3].setAvatar(avatar3);playersUI[3].setVoteIcon(hand3);playersUI[3].setCount(c3);playersUI[3].setIconRole(roleUser3);playersUI[3].setName(name3);
        playersUI[4].setAvatar(avatar4);playersUI[4].setVoteIcon(hand4);playersUI[4].setCount(c4);playersUI[4].setIconRole(roleUser4);playersUI[4].setName(name4);
        playersUI[5].setAvatar(avatar5);playersUI[5].setVoteIcon(hand5);playersUI[5].setCount(c5);playersUI[5].setIconRole(roleUser5);playersUI[5].setName(name5);
        playersUI[6].setAvatar(avatar6);playersUI[6].setVoteIcon(hand6);playersUI[6].setCount(c6);playersUI[6].setIconRole(roleUser6);playersUI[6].setName(name6);
        playersUI[7].setAvatar(avatar7);playersUI[7].setVoteIcon(hand7);playersUI[7].setCount(c7);playersUI[7].setIconRole(roleUser7);playersUI[7].setName(name7);
        playersUI[8].setAvatar(avatar8);playersUI[8].setVoteIcon(hand8);playersUI[8].setCount(c8);playersUI[8].setIconRole(roleUser8);playersUI[8].setName(name8);
        for(int i = 0; i < 9;i++){
            playersUI[i].avatar.setIcon(gui.resizeImage(Constaint.pathRoot + "/assets/avatar_unknown.jpg", 75, 75));
            playersUI[i].name.setText("");
            playersUI[i].count.setVisible(false);
            playersUI[i].iconRole.setVisible(false);
            playersUI[i].voteIcon.setVisible(false);
        }
        turnDay();
    }
    
    public void resetUI(PlayerUI ui){
        ui.avatar.setIcon(gui.resizeImage(Constaint.pathRoot + "/assets/avatar_unknown.jpg", 75, 75));
        ui.name.setText("");
        ui.count.setVisible(false);
        ui.iconRole.setVisible(false);
        ui.voteIcon.setVisible(false);
    }
    
    public void reloadPlayers(){
        int index = 0;
        var players = Service.gI().dataSource.player.room.players;
        System.out.println("Có " + players.size() + " player");
        for(Player p: players){
            playersUI[index++].loadPlayer(p);
        }
        for(int j = index; j < 9; j++){
            resetUI(playersUI[j]);
        }
    }
    
    private void turnNight(){
        System.out.println("NIGHT");
        Color day = new Color(153, 153, 153);
        txtInput.setBackground(day);
        txtChat.setBackground(day);
        txtChat.setForeground(Color.WHITE);
        btnSend.setBackground(day);
    }
    
    private void turnDay(){
        System.out.println("DAY");
        Color day = new Color(242, 242, 242);
        txtInput.setBackground(day);
        txtChat.setBackground(day);
        txtChat.setForeground(Color.BLACK);
        btnSend.setBackground(day);
    }
    
    public void addMessage(String message){
        String old = txtChat.getText();
        txtChat.setText(old + "\n" + message);
    }
    
    public GameRoom() {
        initComponents();
        initGUI();
        Service.gI().panelGame = this;
        gui.changePanel(mainPanel, new PanelStatus());
//        reloadPlayers();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        btnExit = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtChat = new javax.swing.JTextArea();
        txtInput = new javax.swing.JTextField();
        btnSend = new javax.swing.JButton();
        jLabel69 = new javax.swing.JLabel();
        btnStart = new javax.swing.JButton();
        hand0 = new javax.swing.JLabel();
        avatar0 = new javax.swing.JLabel();
        name0 = new javax.swing.JLabel();
        roleUser0 = new javax.swing.JLabel();
        c0 = new javax.swing.JLabel();
        hand1 = new javax.swing.JLabel();
        avatar1 = new javax.swing.JLabel();
        name1 = new javax.swing.JLabel();
        roleUser1 = new javax.swing.JLabel();
        c1 = new javax.swing.JLabel();
        hand2 = new javax.swing.JLabel();
        avatar2 = new javax.swing.JLabel();
        name2 = new javax.swing.JLabel();
        roleUser2 = new javax.swing.JLabel();
        c2 = new javax.swing.JLabel();
        hand3 = new javax.swing.JLabel();
        avatar3 = new javax.swing.JLabel();
        name3 = new javax.swing.JLabel();
        roleUser3 = new javax.swing.JLabel();
        c3 = new javax.swing.JLabel();
        hand4 = new javax.swing.JLabel();
        avatar4 = new javax.swing.JLabel();
        name4 = new javax.swing.JLabel();
        roleUser4 = new javax.swing.JLabel();
        c4 = new javax.swing.JLabel();
        hand5 = new javax.swing.JLabel();
        avatar5 = new javax.swing.JLabel();
        name5 = new javax.swing.JLabel();
        roleUser5 = new javax.swing.JLabel();
        c5 = new javax.swing.JLabel();
        hand6 = new javax.swing.JLabel();
        avatar6 = new javax.swing.JLabel();
        name6 = new javax.swing.JLabel();
        roleUser6 = new javax.swing.JLabel();
        c6 = new javax.swing.JLabel();
        hand7 = new javax.swing.JLabel();
        avatar7 = new javax.swing.JLabel();
        name7 = new javax.swing.JLabel();
        roleUser7 = new javax.swing.JLabel();
        c7 = new javax.swing.JLabel();
        hand8 = new javax.swing.JLabel();
        avatar8 = new javax.swing.JLabel();
        name8 = new javax.swing.JLabel();
        roleUser8 = new javax.swing.JLabel();
        c8 = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();

        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(860, 502));
        jPanel1.setLayout(null);
        jPanel1.add(jLabel2);
        jLabel2.setBounds(148, 73, 102, 0);

        jLabel3.setText("jLabel2");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(30, 208, 100, 16);

        jLabel4.setText("jLabel2");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(156, 208, 100, 16);

        jLabel5.setText("jLabel2");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(30, 73, 100, 16);

        jLabel6.setText("jLabel2");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(30, 342, 100, 16);

        jLabel7.setText("jLabel2");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(268, 208, 100, 16);

        jLabel8.setText("jLabel2");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(268, 342, 100, 16);

        jLabel9.setText("jLabel2");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(268, 73, 100, 16);

        jLabel10.setText("jLabel2");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(150, 342, 100, 16);

        jLabel1.setText("Chủ phòng");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(30, 48, 60, 16);

        jButton1.setText("Thoát phòng");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(6, 6, 120, 32);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(401, 73, 455, 280);

        jTextField1.setText("Chat here");
        jPanel1.add(jTextField1);
        jTextField1.setBounds(401, 371, 337, 59);

        jButton2.setText("Gửi");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(744, 371, 112, 59);

        jLabel11.setText("Tên");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(30, 180, 19, 16);

        jLabel12.setText("Tên");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(148, 180, 19, 16);

        jLabel13.setText("Tên");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(262, 180, 19, 16);

        jLabel14.setText("Tên");
        jPanel1.add(jLabel14);
        jLabel14.setBounds(30, 312, 19, 24);

        jLabel15.setText("Tên");
        jPanel1.add(jLabel15);
        jLabel15.setBounds(150, 312, 19, 24);

        jLabel16.setText("Tên");
        jPanel1.add(jLabel16);
        jLabel16.setBounds(268, 312, 19, 24);

        jLabel17.setText("Tên");
        jPanel1.add(jLabel17);
        jLabel17.setBounds(30, 449, 19, 24);

        jLabel18.setText("Tên");
        jPanel1.add(jLabel18);
        jLabel18.setBounds(150, 449, 19, 24);

        jLabel19.setText("Tên");
        jPanel1.add(jLabel19);
        jLabel19.setBounds(268, 449, 19, 24);
        jPanel1.add(jLabel20);
        jLabel20.setBounds(385, 30, 0, 0);

        jLabel22.setText("role");
        jPanel1.add(jLabel22);
        jLabel22.setBounds(109, 180, 20, 16);

        jLabel23.setText("role");
        jPanel1.add(jLabel23);
        jLabel23.setBounds(235, 180, 20, 16);

        jLabel24.setText("role");
        jPanel1.add(jLabel24);
        jLabel24.setBounds(347, 180, 20, 16);

        jLabel25.setText("role");
        jPanel1.add(jLabel25);
        jLabel25.setBounds(347, 316, 20, 16);

        jLabel26.setText("role");
        jPanel1.add(jLabel26);
        jLabel26.setBounds(109, 316, 20, 16);

        jLabel27.setText("role");
        jPanel1.add(jLabel27);
        jLabel27.setBounds(229, 316, 20, 16);

        jLabel28.setText("role");
        jPanel1.add(jLabel28);
        jLabel28.setBounds(347, 453, 20, 16);

        jLabel29.setText("role");
        jPanel1.add(jLabel29);
        jLabel29.setBounds(109, 453, 20, 16);

        jLabel30.setText("role");
        jPanel1.add(jLabel30);
        jLabel30.setBounds(229, 453, 20, 16);

        jButton3.setText("Bắt Đầu");
        jPanel1.add(jButton3);
        jButton3.setBounds(673, 449, 165, 53);

        jButton4.setText("Chỉnh sửa role");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4);
        jButton4.setBounds(401, 450, 138, 52);

        jLabel21.setText("Sói");
        jPanel1.add(jLabel21);
        jLabel21.setBounds(233, 7, 16, 16);

        jLabel31.setText("Dân làng");
        jPanel1.add(jLabel31);
        jLabel31.setBounds(303, 7, 47, 16);

        jLabel32.setText("Bảo vệ");
        jPanel1.add(jLabel32);
        jLabel32.setBounds(385, 7, 35, 16);

        jLabel33.setText("Tiên Tri");
        jPanel1.add(jLabel33);
        jLabel33.setBounds(498, 8, 38, 16);

        jLabel34.setText("Kẻ lây nhiễm");
        jPanel1.add(jLabel34);
        jLabel34.setBounds(577, 7, 68, 16);

        jLabel35.setText("Sói Tri");
        jPanel1.add(jLabel35);
        jLabel35.setBounds(233, 37, 32, 16);

        jLabel36.setText("Xạ thủ");
        jPanel1.add(jLabel36);
        jLabel36.setBounds(303, 37, 34, 16);

        jLabel37.setText("Thầy Đồng");
        jPanel1.add(jLabel37);
        jLabel37.setBounds(385, 37, 57, 16);

        jLabel38.setText("Thằng hề");
        jPanel1.add(jLabel38);
        jLabel38.setBounds(498, 37, 49, 16);

        jLabel39.setText("Role 10");
        jPanel1.add(jLabel39);
        jLabel39.setBounds(577, 37, 38, 16);

        jLabel40.setText("0");
        jLabel40.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 51, 51), 1, true));
        jPanel1.add(jLabel40);
        jLabel40.setBounds(209, 6, 18, 18);

        jLabel41.setText("0");
        jLabel41.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 51, 51), 1, true));
        jPanel1.add(jLabel41);
        jLabel41.setBounds(209, 36, 18, 18);

        jLabel42.setText("0");
        jLabel42.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 51, 51), 1, true));
        jPanel1.add(jLabel42);
        jLabel42.setBounds(279, 6, 18, 18);

        jLabel43.setText("0");
        jLabel43.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 51, 51), 1, true));
        jPanel1.add(jLabel43);
        jLabel43.setBounds(279, 36, 18, 18);

        jLabel44.setText("0");
        jLabel44.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 51, 51), 1, true));
        jPanel1.add(jLabel44);
        jLabel44.setBounds(363, 6, 18, 18);

        jLabel45.setText("0");
        jLabel45.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 51, 51), 1, true));
        jPanel1.add(jLabel45);
        jLabel45.setBounds(361, 36, 18, 18);

        jLabel46.setText("0");
        jLabel46.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 51, 51), 1, true));
        jPanel1.add(jLabel46);
        jLabel46.setBounds(479, 6, 18, 18);

        jLabel47.setText("0");
        jLabel47.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 51, 51), 1, true));
        jPanel1.add(jLabel47);
        jLabel47.setBounds(479, 36, 18, 18);

        jLabel48.setText("0");
        jLabel48.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 51, 51), 1, true));
        jPanel1.add(jLabel48);
        jLabel48.setBounds(553, 6, 18, 18);

        jLabel49.setText("0");
        jLabel49.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 51, 51), 1, true));
        jPanel1.add(jLabel49);
        jLabel49.setBounds(553, 36, 18, 18);

        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setMinimumSize(new java.awt.Dimension(860, 502));
        jPanel2.setLayout(null);
        jPanel2.add(jLabel50);
        jLabel50.setBounds(148, 73, 102, 0);

        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/exit.png"))); // NOI18N
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        jPanel2.add(btnExit);
        btnExit.setBounds(790, 70, 60, 40);

        txtChat.setEditable(false);
        txtChat.setBackground(new java.awt.Color(204, 204, 204));
        txtChat.setColumns(20);
        txtChat.setRows(5);
        jScrollPane2.setViewportView(txtChat);

        jPanel2.add(jScrollPane2);
        jScrollPane2.setBounds(400, 120, 455, 300);

        txtInput.setBackground(new java.awt.Color(204, 204, 204));
        txtInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtInputActionPerformed(evt);
            }
        });
        jPanel2.add(txtInput);
        txtInput.setBounds(400, 430, 337, 59);

        btnSend.setBackground(new java.awt.Color(204, 204, 204));
        btnSend.setText("Gửi");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });
        jPanel2.add(btnSend);
        btnSend.setBounds(740, 430, 112, 59);
        jPanel2.add(jLabel69);
        jLabel69.setBounds(385, 30, 0, 0);

        btnStart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/start.png"))); // NOI18N
        jPanel2.add(btnStart);
        btnStart.setBounds(790, 20, 60, 40);

        hand0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/hand.png"))); // NOI18N
        jPanel2.add(hand0);
        hand0.setBounds(20, 80, 60, 40);

        avatar0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/avatar1.jpg"))); // NOI18N
        jPanel2.add(avatar0);
        avatar0.setBounds(50, 80, 75, 75);

        name0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        name0.setText("1 NguyenDanTruong");
        jPanel2.add(name0);
        name0.setBounds(40, 170, 100, 16);

        roleUser0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_role0.png"))); // NOI18N
        jPanel2.add(roleUser0);
        roleUser0.setBounds(70, 40, 40, 30);

        c0.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        c0.setForeground(new java.awt.Color(255, 51, 51));
        c0.setText("10");
        jPanel2.add(c0);
        c0.setBounds(10, 60, 20, 20);

        hand1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/hand.png"))); // NOI18N
        jPanel2.add(hand1);
        hand1.setBounds(140, 80, 60, 40);

        avatar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/avatar1.jpg"))); // NOI18N
        jPanel2.add(avatar1);
        avatar1.setBounds(170, 80, 75, 75);

        name1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        name1.setText("1 NguyenDanTruong");
        jPanel2.add(name1);
        name1.setBounds(150, 170, 100, 16);

        roleUser1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_role0.png"))); // NOI18N
        jPanel2.add(roleUser1);
        roleUser1.setBounds(190, 40, 40, 30);

        c1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        c1.setForeground(new java.awt.Color(255, 51, 51));
        c1.setText("10");
        jPanel2.add(c1);
        c1.setBounds(130, 60, 20, 20);

        hand2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/hand.png"))); // NOI18N
        jPanel2.add(hand2);
        hand2.setBounds(260, 80, 60, 40);

        avatar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/avatar1.jpg"))); // NOI18N
        jPanel2.add(avatar2);
        avatar2.setBounds(290, 80, 75, 75);

        name2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        name2.setText("1 NguyenDanTruong");
        jPanel2.add(name2);
        name2.setBounds(270, 170, 100, 16);

        roleUser2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_role0.png"))); // NOI18N
        jPanel2.add(roleUser2);
        roleUser2.setBounds(310, 40, 40, 30);

        c2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        c2.setForeground(new java.awt.Color(255, 51, 51));
        c2.setText("10");
        jPanel2.add(c2);
        c2.setBounds(250, 60, 20, 20);

        hand3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/hand.png"))); // NOI18N
        jPanel2.add(hand3);
        hand3.setBounds(20, 240, 60, 40);

        avatar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/avatar1.jpg"))); // NOI18N
        jPanel2.add(avatar3);
        avatar3.setBounds(50, 240, 75, 75);

        name3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        name3.setText("1 NguyenDanTruong");
        jPanel2.add(name3);
        name3.setBounds(40, 330, 100, 16);

        roleUser3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_role0.png"))); // NOI18N
        jPanel2.add(roleUser3);
        roleUser3.setBounds(70, 200, 40, 30);

        c3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        c3.setForeground(new java.awt.Color(255, 51, 51));
        c3.setText("10");
        jPanel2.add(c3);
        c3.setBounds(20, 220, 20, 20);

        hand4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/hand.png"))); // NOI18N
        jPanel2.add(hand4);
        hand4.setBounds(140, 240, 60, 40);

        avatar4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/avatar1.jpg"))); // NOI18N
        jPanel2.add(avatar4);
        avatar4.setBounds(170, 240, 75, 75);

        name4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        name4.setText("1 NguyenDanTruong");
        jPanel2.add(name4);
        name4.setBounds(160, 330, 100, 16);

        roleUser4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_role0.png"))); // NOI18N
        jPanel2.add(roleUser4);
        roleUser4.setBounds(190, 200, 40, 30);

        c4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        c4.setForeground(new java.awt.Color(255, 51, 51));
        c4.setText("10");
        jPanel2.add(c4);
        c4.setBounds(140, 220, 20, 20);

        hand5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/hand.png"))); // NOI18N
        jPanel2.add(hand5);
        hand5.setBounds(260, 240, 60, 40);

        avatar5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/avatar1.jpg"))); // NOI18N
        jPanel2.add(avatar5);
        avatar5.setBounds(290, 240, 75, 75);

        name5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        name5.setText("1 NguyenDanTruong");
        jPanel2.add(name5);
        name5.setBounds(280, 330, 100, 16);

        roleUser5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_role0.png"))); // NOI18N
        jPanel2.add(roleUser5);
        roleUser5.setBounds(310, 200, 40, 30);

        c5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        c5.setForeground(new java.awt.Color(255, 51, 51));
        c5.setText("10");
        jPanel2.add(c5);
        c5.setBounds(260, 220, 20, 20);

        hand6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/hand.png"))); // NOI18N
        jPanel2.add(hand6);
        hand6.setBounds(20, 390, 60, 40);

        avatar6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/avatar1.jpg"))); // NOI18N
        jPanel2.add(avatar6);
        avatar6.setBounds(50, 390, 75, 75);

        name6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        name6.setText("1 NguyenDanTruong");
        jPanel2.add(name6);
        name6.setBounds(40, 480, 100, 16);

        roleUser6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_role0.png"))); // NOI18N
        jPanel2.add(roleUser6);
        roleUser6.setBounds(70, 350, 40, 30);

        c6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        c6.setForeground(new java.awt.Color(255, 51, 51));
        c6.setText("10");
        jPanel2.add(c6);
        c6.setBounds(20, 370, 20, 20);

        hand7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/hand.png"))); // NOI18N
        jPanel2.add(hand7);
        hand7.setBounds(140, 390, 60, 40);

        avatar7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/avatar1.jpg"))); // NOI18N
        jPanel2.add(avatar7);
        avatar7.setBounds(170, 390, 75, 75);

        name7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        name7.setText("1 NguyenDanTruong");
        jPanel2.add(name7);
        name7.setBounds(160, 480, 100, 16);

        roleUser7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_role0.png"))); // NOI18N
        jPanel2.add(roleUser7);
        roleUser7.setBounds(190, 350, 40, 30);

        c7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        c7.setForeground(new java.awt.Color(255, 51, 51));
        c7.setText("10");
        jPanel2.add(c7);
        c7.setBounds(140, 370, 20, 20);

        hand8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/hand.png"))); // NOI18N
        jPanel2.add(hand8);
        hand8.setBounds(260, 390, 60, 40);

        avatar8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/avatar1.jpg"))); // NOI18N
        jPanel2.add(avatar8);
        avatar8.setBounds(290, 390, 75, 75);

        name8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        name8.setText("1 NguyenDanTruong");
        jPanel2.add(name8);
        name8.setBounds(280, 480, 100, 16);

        roleUser8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_role0.png"))); // NOI18N
        jPanel2.add(roleUser8);
        roleUser8.setBounds(310, 350, 40, 30);

        c8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        c8.setForeground(new java.awt.Color(255, 51, 51));
        c8.setText("10");
        jPanel2.add(c8);
        c8.setBounds(260, 370, 20, 20);

        mainPanel.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 380, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 90, Short.MAX_VALUE)
        );

        jPanel2.add(mainPanel);
        mainPanel.setBounds(400, 20, 380, 90);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 860, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 502, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed
    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        sendMessage();
    }//GEN-LAST:event_btnSendActionPerformed
    
    private void sendMessage(){
        String content = txtInput.getText().strip();
        if(content.equals("")){
            return;
        }
        txtInput.setText("");
        Service.gI().sendMessage(Constaint.MESSAGE_CHAT, content);
    }
    
    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnExitActionPerformed

    private void txtInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtInputActionPerformed
        sendMessage();
    }//GEN-LAST:event_txtInputActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel avatar0;
    private javax.swing.JLabel avatar1;
    private javax.swing.JLabel avatar2;
    private javax.swing.JLabel avatar3;
    private javax.swing.JLabel avatar4;
    private javax.swing.JLabel avatar5;
    private javax.swing.JLabel avatar6;
    private javax.swing.JLabel avatar7;
    private javax.swing.JLabel avatar8;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnSend;
    private javax.swing.JButton btnStart;
    private javax.swing.JLabel c0;
    private javax.swing.JLabel c1;
    private javax.swing.JLabel c2;
    private javax.swing.JLabel c3;
    private javax.swing.JLabel c4;
    private javax.swing.JLabel c5;
    private javax.swing.JLabel c6;
    private javax.swing.JLabel c7;
    private javax.swing.JLabel c8;
    private javax.swing.JLabel hand0;
    private javax.swing.JLabel hand1;
    private javax.swing.JLabel hand2;
    private javax.swing.JLabel hand3;
    private javax.swing.JLabel hand4;
    private javax.swing.JLabel hand5;
    private javax.swing.JLabel hand6;
    private javax.swing.JLabel hand7;
    private javax.swing.JLabel hand8;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel name0;
    private javax.swing.JLabel name1;
    private javax.swing.JLabel name2;
    private javax.swing.JLabel name3;
    private javax.swing.JLabel name4;
    private javax.swing.JLabel name5;
    private javax.swing.JLabel name6;
    private javax.swing.JLabel name7;
    private javax.swing.JLabel name8;
    private javax.swing.JLabel roleUser0;
    private javax.swing.JLabel roleUser1;
    private javax.swing.JLabel roleUser2;
    private javax.swing.JLabel roleUser3;
    private javax.swing.JLabel roleUser4;
    private javax.swing.JLabel roleUser5;
    private javax.swing.JLabel roleUser6;
    private javax.swing.JLabel roleUser7;
    private javax.swing.JLabel roleUser8;
    private javax.swing.JTextArea txtChat;
    private javax.swing.JTextField txtInput;
    // End of variables declaration//GEN-END:variables
}
