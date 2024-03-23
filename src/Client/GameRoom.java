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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

/**
 *
 * @author cr4zyb0t
 */
public class GameRoom extends javax.swing.JPanel {
    private final String idRoomOwner; 
    private class PlayerUI{
        public int index;
        public JLabel avatar, voteIcon, iconRole, name, vote;
        public Player p;
        
        public void setAvatar(JLabel avatar) {
            this.avatar = avatar;
            // Xóa tất cả các MouseListener hiện có
            for (MouseListener listener : this.avatar.getMouseListeners()) {
                this.avatar.removeMouseListener(listener);
            }
            // Thêm một MouseListener mới
            this.avatar.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        String username = getUsername();
                        System.out.println("CLick ed " + username);
                    } catch (Exception e1) {
                    }
                }
            });
        }

        public void setVoteIcon(JLabel voteIcon) {
            this.voteIcon = voteIcon;
        }
        
        /**
         * Hàm để set cái tay chỉ số lượng người đang bỏ phiếu cho UI này
         */
        public void setVoteIcon(int quantity){
            String path = Constaint.pathRoot + "/assets/hand/" + quantity + ".png";
            var icon = gui.resizeImage(path, 60, 52);
            this.voteIcon.setIcon(icon);
            this.voteIcon.setVisible(true);
        }
        
        /**
         * Hàm để ẩn đi cái tay chỉ vào UI
         */
        public void hiddenVoteIcon(){
            this.voteIcon.setVisible(false);
        }

        public void setIconRole(JLabel iconRole) {
            this.iconRole = iconRole;
        }

        public void setName(JLabel name) {
            this.name = name;
        }
        
        public String getUsername(){
            return this.name.getText().split("\\ ")[1];
        }
        
        public void setVote(JLabel vote){
            this.vote = vote;
        }
        
        /**
         * Hàm để set cái bảng cầm tay chỉ đang vote ai ấy
         */
        public void setVote(int indexPlayer){
            String path = Constaint.pathRoot + "/assets/hand_table/hand_vote_" + indexPlayer + ".png";
            var icon = gui.resizeImage(path, 70, 30);
            this.vote.setIcon(icon);
            this.vote.setVisible(true);
        }
        /**
         * Hàm để ẩn đi cái bảng cầm tay 
         */
        public void hiddenVote(){
            this.vote.setVisible(false);
        }
        
        public void loadPlayer(Player p){
            this.p = p;
            int avatarId  = Utils.RandomUtils.nextInt(1, 4);
            avatar.setIcon(gui.resizeImage(Constaint.pathRoot + "/assets/avatar" + avatarId+ ".jpg", 75, 75));
            String currentNamePlayer = Service.gI().dataSource.player.namePlayer;
            name.setText(index + " " + p.namePlayer);
            if(p.namePlayer.equals(currentNamePlayer)){
                name.setForeground(Color.red);
                thisUI = this;
            }
            if(idRoomOwner.equals(p.namePlayer)){
                String path = Constaint.pathRoot + "/assets/crown.png";
                System.out.println(path);
                this.iconRole.setIcon(gui.resizeImage
                    (path, 24, 24));
                this.iconRole.setVisible(true);
            }
        }
        
        public void resetUI(){
            
        }
        
    }
    private PlayerUI thisUI;
    
    private final PlayerUI[] playersUI = new PlayerUI[9];
    
    private PlayerUI getUIbyUserName(String username){
        for(var u : playersUI){
            if(u.getUsername().equals(username)) return u;
        }
        return null;
    }
    
    private void initGUI(){
        JLabel[] votes = new JLabel[]{vote1, vote2, vote3, vote4, vote5, vote6, vote7, vote8, vote9};
        JLabel[] avatars = new JLabel[]{
            avatar0, avatar1, avatar2, avatar3, avatar4, avatar5, avatar6, avatar7, avatar8
        };
        JLabel[] voteicons = new JLabel[]{
            hand0, hand1, hand2, hand3, hand4, hand5, hand6, hand7, hand8
        };
        JLabel[] iconroles = new JLabel[]{
            roleUser0, roleUser1, roleUser2, roleUser3, roleUser4, roleUser5, roleUser6, roleUser7, roleUser8
        };
        JLabel[] names = new JLabel[]{
            name0, name1, name2, name3, name4, name5, name6, name7, name8
        };

        for(int i = 0; i < 9;i++){
            playersUI[i] = new PlayerUI();
            PlayerUI pU = playersUI[i];
            pU.index = i+1;
            pU.setVote(votes[i]);
            pU.setAvatar(avatars[i]);
            pU.setVoteIcon(voteicons[i]);
            pU.setIconRole(iconroles[i]);
            pU.setName(names[i]);
            pU.avatar.setIcon(gui.resizeImage(Constaint.pathRoot + "/assets/avatar_unknown.jpg", 75, 75));
            pU.name.setText("");
            pU.iconRole.setVisible(false);
            pU.voteIcon.setVisible(false);
            pU.vote.setVisible(false);
        }
        turnDay();
    }
    
    private void resetUI(PlayerUI ui){
        ui.avatar.setIcon(gui.resizeImage(Constaint.pathRoot + "/assets/avatar_unknown.jpg", 75, 75));
        ui.name.setText("");
        ui.iconRole.setVisible(false);
        ui.voteIcon.setVisible(false);
    }
    
    /**
     * Hàm reload players khi có thay đổi
     */
    public void reloadPlayers(){
        int index = 0;
        var players = Service.gI().dataSource.player.room.players;
        for(Player p: players){
            playersUI[index++].loadPlayer(p);
        }
        for(int j = index; j < 9; j++){
            resetUI(playersUI[j]);
        }
        //addMessage("Load được " + Service.gI().dataSource.player.room.players.size());
    }
    
    /**
     * Hàm thêm message vào GUI của Client
     * @param message Chuỗi cần thêm vào
     */
    public void addMessage(String message){
        String old = txtChat.getText();
        txtChat.setText(old + "\n" + message);
    }
    
    /**
     * Hàm update thanh status khi stage thay đổi
     * @param stage đối tượng Stage bao gồm time countdown, message và 
     */
    public void updateStage(Stage stage){
        gui.changePanel(mainPanel, new PanelTime(stage));
    }
    
    //Xử lý trong quá trình chơi
    /**
     * Bật chế độ ban ngày cho UI
     */
    public void turnDay(){
        Color day = new Color(242, 242, 242);
        txtInput.setBackground(day);
        txtChat.setBackground(day);
        txtChat.setForeground(Color.BLACK);
        btnSend.setBackground(day);
    }
    
    /**
     * Bật chế độ ban đêm cho UI
     */
    public void turnNight(){
        Color day = new Color(153, 153, 153);
        txtInput.setBackground(day);
        txtChat.setBackground(day);
        txtChat.setForeground(Color.WHITE);
        btnSend.setBackground(day);
    }
    
    public void reloadWolfVotes(List<Player> playerStates){
        for(var p: playerStates){
            String username = p.namePlayer;
            PlayerVote pV = p.playerVote;
            for(PlayerUI ui: playersUI){
                //Trong trường hợp cái UI đó đại diện cho cái Vote đang duyệt
                if(ui.getUsername().equals(username)){
                    //Set cái tay chỉ vào UI
                    if(!pV.voters.isEmpty())
                        ui.setVoteIcon(pV.voters.size());
                    else
                        ui.hiddenVoteIcon();
                    
                    //Set người đang bỏ phiếu
                    if(pV.target != null){
                        int indexPlayer = getUIbyUserName(pV.target.namePlayer).index;
                        ui.setVote(indexPlayer);
                    }
                    else{
                        ui.hiddenVote();
                    }
                    break;
                }
            }
        }
    }
    
    /**
     * Xử lý nhẹ khi game bắt đầu
     * @param flag status game đã bắt đầu hay chưa
     */
    public void gameStarted(boolean flag){
        btnStart.setVisible(!flag);
        String x = flag ? "bắt đầu" : "kết thúc";
        addMessage("[Server]: Trò chơi đã " + x + " !");
    }
    /**
     * Hàm set role và chạy hiệu ứng random role
     * @param idRole id role nhận được từ server
     */
    public void setRoleMySelf(byte idRole){
        new Thread(()->{
            PlayerUI uj = null;
            for(PlayerUI u: playersUI){
                if(u.p != null && u.p.namePlayer.equals(Service.gI().dataSource.player.namePlayer)) {
                    uj = u;
                }
            }
            try {
                for(int i = 0 ; i < 30;i++){
                    Thread.sleep(50);
                    byte rd = (byte)Utils.RandomUtils.nextInt(0, 9);
                    if(uj != null){
                        String path = Constaint.pathRoot + "/assets/icon_role/icon_role" + rd + ".png";
                        uj.iconRole.setIcon(gui.resizeImage(path, 36, 36));
                        uj.iconRole.setVisible(true);
                    }
                }
            } catch (Exception e) {
            }
            if(uj != null){
                String path = Constaint.pathRoot + "/assets/icon_role/icon_role" + idRole + ".png";
                uj.iconRole.setIcon(gui.resizeImage(path, 36, 36));
            }
            addMessage("Bạn đã nhận được vai trò " + Utils.StringUtils.getRoleNameById(idRole));
        }).start();
    }
    
    /**
     *
     */
    public GameRoom() {
        initComponents();
        initGUI();
        Service.gI().panelGame  = this;
        Player player = Service.gI().dataSource.player;
        Room room = player.room;
        this.idRoomOwner = room.owner.namePlayer;
        if(!room.owner.namePlayer.equals(player.namePlayer)){
            btnStart.setVisible(false);
        }
        gui.changePanel(mainPanel, new PanelStatus(room.configs, room.isRandom));
        reloadPlayers();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        vote9 = new javax.swing.JLabel();
        vote3 = new javax.swing.JLabel();
        vote1 = new javax.swing.JLabel();
        vote2 = new javax.swing.JLabel();
        vote4 = new javax.swing.JLabel();
        vote5 = new javax.swing.JLabel();
        vote6 = new javax.swing.JLabel();
        vote7 = new javax.swing.JLabel();
        vote8 = new javax.swing.JLabel();
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
        hand1 = new javax.swing.JLabel();
        avatar1 = new javax.swing.JLabel();
        name1 = new javax.swing.JLabel();
        roleUser1 = new javax.swing.JLabel();
        hand2 = new javax.swing.JLabel();
        avatar2 = new javax.swing.JLabel();
        name2 = new javax.swing.JLabel();
        roleUser2 = new javax.swing.JLabel();
        hand3 = new javax.swing.JLabel();
        avatar3 = new javax.swing.JLabel();
        name3 = new javax.swing.JLabel();
        roleUser3 = new javax.swing.JLabel();
        hand4 = new javax.swing.JLabel();
        avatar4 = new javax.swing.JLabel();
        name4 = new javax.swing.JLabel();
        roleUser4 = new javax.swing.JLabel();
        hand5 = new javax.swing.JLabel();
        avatar5 = new javax.swing.JLabel();
        name5 = new javax.swing.JLabel();
        roleUser5 = new javax.swing.JLabel();
        hand6 = new javax.swing.JLabel();
        avatar6 = new javax.swing.JLabel();
        name6 = new javax.swing.JLabel();
        roleUser6 = new javax.swing.JLabel();
        hand7 = new javax.swing.JLabel();
        avatar7 = new javax.swing.JLabel();
        name7 = new javax.swing.JLabel();
        roleUser7 = new javax.swing.JLabel();
        hand8 = new javax.swing.JLabel();
        avatar8 = new javax.swing.JLabel();
        name8 = new javax.swing.JLabel();
        roleUser8 = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();

        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setMinimumSize(new java.awt.Dimension(860, 502));
        jPanel2.setLayout(null);

        vote9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        vote9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/hand_table/hand_vote.png"))); // NOI18N
        jPanel2.add(vote9);
        vote9.setBounds(290, 440, 70, 40);

        vote3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        vote3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/hand_table/hand_vote.png"))); // NOI18N
        jPanel2.add(vote3);
        vote3.setBounds(290, 130, 70, 40);

        vote1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        vote1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/hand_table/hand_vote.png"))); // NOI18N
        jPanel2.add(vote1);
        vote1.setBounds(50, 130, 70, 40);

        vote2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        vote2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/hand_table/hand_vote.png"))); // NOI18N
        jPanel2.add(vote2);
        vote2.setBounds(170, 130, 70, 40);

        vote4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        vote4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/hand_table/hand_vote.png"))); // NOI18N
        jPanel2.add(vote4);
        vote4.setBounds(50, 290, 70, 40);

        vote5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        vote5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/hand_table/hand_vote.png"))); // NOI18N
        jPanel2.add(vote5);
        vote5.setBounds(170, 290, 70, 40);

        vote6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        vote6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/hand_table/hand_vote.png"))); // NOI18N
        jPanel2.add(vote6);
        vote6.setBounds(290, 290, 70, 40);

        vote7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        vote7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/hand_table/hand_vote.png"))); // NOI18N
        jPanel2.add(vote7);
        vote7.setBounds(50, 440, 70, 40);

        vote8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        vote8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/hand_table/hand_vote.png"))); // NOI18N
        jPanel2.add(vote8);
        vote8.setBounds(170, 440, 70, 40);
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
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });
        jPanel2.add(btnStart);
        btnStart.setBounds(790, 20, 60, 40);

        hand0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/hand/hand.png"))); // NOI18N
        jPanel2.add(hand0);
        hand0.setBounds(20, 80, 60, 40);

        avatar0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/avatar1.jpg"))); // NOI18N
        jPanel2.add(avatar0);
        avatar0.setBounds(50, 80, 75, 75);

        name0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        name0.setText("1 NguyenDanTruong");
        jPanel2.add(name0);
        name0.setBounds(40, 170, 100, 16);

        roleUser0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        roleUser0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_role/icon_role0.png"))); // NOI18N
        jPanel2.add(roleUser0);
        roleUser0.setBounds(70, 40, 40, 30);

        hand1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/hand/hand.png"))); // NOI18N
        jPanel2.add(hand1);
        hand1.setBounds(140, 80, 60, 40);

        avatar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/avatar1.jpg"))); // NOI18N
        jPanel2.add(avatar1);
        avatar1.setBounds(170, 80, 75, 75);

        name1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        name1.setText("1 NguyenDanTruong");
        jPanel2.add(name1);
        name1.setBounds(150, 170, 100, 16);

        roleUser1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        roleUser1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_role/icon_role0.png"))); // NOI18N
        jPanel2.add(roleUser1);
        roleUser1.setBounds(190, 40, 40, 30);

        hand2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/hand/hand.png"))); // NOI18N
        jPanel2.add(hand2);
        hand2.setBounds(260, 80, 60, 40);

        avatar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/avatar1.jpg"))); // NOI18N
        jPanel2.add(avatar2);
        avatar2.setBounds(290, 80, 75, 75);

        name2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        name2.setText("1 NguyenDanTruong");
        jPanel2.add(name2);
        name2.setBounds(270, 170, 100, 16);

        roleUser2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        roleUser2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_role/icon_role0.png"))); // NOI18N
        jPanel2.add(roleUser2);
        roleUser2.setBounds(310, 40, 40, 30);

        hand3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/hand/hand.png"))); // NOI18N
        jPanel2.add(hand3);
        hand3.setBounds(20, 240, 60, 40);

        avatar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/avatar1.jpg"))); // NOI18N
        jPanel2.add(avatar3);
        avatar3.setBounds(50, 240, 75, 75);

        name3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        name3.setText("1 NguyenDanTruong");
        jPanel2.add(name3);
        name3.setBounds(40, 330, 100, 16);

        roleUser3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        roleUser3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_role/icon_role0.png"))); // NOI18N
        jPanel2.add(roleUser3);
        roleUser3.setBounds(70, 200, 40, 30);

        hand4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/hand/hand.png"))); // NOI18N
        jPanel2.add(hand4);
        hand4.setBounds(140, 240, 60, 40);

        avatar4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/avatar1.jpg"))); // NOI18N
        jPanel2.add(avatar4);
        avatar4.setBounds(170, 240, 75, 75);

        name4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        name4.setText("1 NguyenDanTruong");
        jPanel2.add(name4);
        name4.setBounds(160, 330, 100, 16);

        roleUser4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        roleUser4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_role/icon_role0.png"))); // NOI18N
        jPanel2.add(roleUser4);
        roleUser4.setBounds(190, 200, 40, 30);

        hand5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/hand/hand.png"))); // NOI18N
        jPanel2.add(hand5);
        hand5.setBounds(260, 240, 60, 40);

        avatar5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/avatar1.jpg"))); // NOI18N
        jPanel2.add(avatar5);
        avatar5.setBounds(290, 240, 75, 75);

        name5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        name5.setText("1 NguyenDanTruong");
        jPanel2.add(name5);
        name5.setBounds(280, 330, 100, 16);

        roleUser5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        roleUser5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_role/icon_role0.png"))); // NOI18N
        jPanel2.add(roleUser5);
        roleUser5.setBounds(310, 200, 40, 30);

        hand6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/hand/hand.png"))); // NOI18N
        jPanel2.add(hand6);
        hand6.setBounds(20, 390, 60, 40);

        avatar6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/avatar1.jpg"))); // NOI18N
        jPanel2.add(avatar6);
        avatar6.setBounds(50, 390, 75, 75);

        name6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        name6.setText("1 NguyenDanTruong");
        jPanel2.add(name6);
        name6.setBounds(40, 480, 100, 16);

        roleUser6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        roleUser6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_role/icon_role0.png"))); // NOI18N
        jPanel2.add(roleUser6);
        roleUser6.setBounds(70, 350, 40, 30);

        hand7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/hand/hand.png"))); // NOI18N
        jPanel2.add(hand7);
        hand7.setBounds(140, 390, 60, 40);

        avatar7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/avatar1.jpg"))); // NOI18N
        jPanel2.add(avatar7);
        avatar7.setBounds(170, 390, 75, 75);

        name7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        name7.setText("1 NguyenDanTruong");
        jPanel2.add(name7);
        name7.setBounds(160, 480, 100, 16);

        roleUser7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        roleUser7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_role/icon_role0.png"))); // NOI18N
        jPanel2.add(roleUser7);
        roleUser7.setBounds(190, 350, 40, 30);

        hand8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/hand/hand.png"))); // NOI18N
        jPanel2.add(hand8);
        hand8.setBounds(260, 390, 60, 40);

        avatar8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/avatar1.jpg"))); // NOI18N
        jPanel2.add(avatar8);
        avatar8.setBounds(290, 390, 75, 75);

        name8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        name8.setText("1 NguyenDanTruong");
        jPanel2.add(name8);
        name8.setBounds(280, 480, 100, 16);

        roleUser8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        roleUser8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_role/icon_role0.png"))); // NOI18N
        jPanel2.add(roleUser8);
        roleUser8.setBounds(310, 350, 40, 30);

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
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 860, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 502, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

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
        Service.gI().sendMessage(Constaint.MESSAGE_LEAVE_ROOM, null);
        Service.gI().panelGame = null;
        Service.gI().frm.changePanel(new HomePanel());
    }//GEN-LAST:event_btnExitActionPerformed

    private void txtInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtInputActionPerformed
        sendMessage();
    }//GEN-LAST:event_txtInputActionPerformed

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        Service.gI().sendMessage(Constaint.MESSAGE_START_GAME, null);
    }//GEN-LAST:event_btnStartActionPerformed


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
    private javax.swing.JLabel hand0;
    private javax.swing.JLabel hand1;
    private javax.swing.JLabel hand2;
    private javax.swing.JLabel hand3;
    private javax.swing.JLabel hand4;
    private javax.swing.JLabel hand5;
    private javax.swing.JLabel hand6;
    private javax.swing.JLabel hand7;
    private javax.swing.JLabel hand8;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
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
    private javax.swing.JLabel vote1;
    private javax.swing.JLabel vote2;
    private javax.swing.JLabel vote3;
    private javax.swing.JLabel vote4;
    private javax.swing.JLabel vote5;
    private javax.swing.JLabel vote6;
    private javax.swing.JLabel vote7;
    private javax.swing.JLabel vote8;
    private javax.swing.JLabel vote9;
    // End of variables declaration//GEN-END:variables
}
