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
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author cr4zyb0t
 */
public class GameRoom extends javax.swing.JPanel {
    public boolean hasShowTutorial = false;
    private final String idRoomOwner; 
    private byte idRole = -128;
    public boolean disableSpecial = false;
    private final List<String> teammateWolfs = new ArrayList<>();
    private String lastUserInteracted = "";
    private boolean hasInteraction = false;
    //Đây là lần đầu nhận role
    private boolean isFirstPickRole = true;
    
    private class PlayerUI{
        public int index;
        public JLabel avatar, voteIcon, iconRole, name, vote, function;
        public Player p;
        public boolean isDie = false;
        
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
                    if(isDie) return;
                    String username = getUsername();
                    if(username.equals(thisUI.getUsername())) return;
                    //Vote người mới hoặc đổi người khác
                    if(!username.equals(lastUserInteracted)){
                        lastUserInteracted = username;
                        Service.gI().sendMessage(Constaint.MESSAGE_PLAYER_VOTES, username);
                    }
                    //Hủy vote
                    else{
                        lastUserInteracted = "";
                        Service.gI().sendMessage(Constaint.MESSAGE_PLAYER_CANCEL_VOTES, username);
                    }
                }
            });
        }
        
        public void setFunction(JLabel function){
            this.function = function;
            // Xóa tất cả các MouseListener hiện có
            for (MouseListener listener : this.function.getMouseListeners()) {
                this.function.removeMouseListener(listener);
            }
            // Thêm một MouseListener mới
            this.function.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(isDie && idRole != Constaint.ROLE_THAYDONG) return;
                    try {
                        String username = getUsername();
                        if(idRole == Constaint.ROLE_BACSI) {
                            //Bảo vệ người khác
                            if(!lastUserInteracted.equals(username)){
                                lastUserInteracted = username ;
                                Service.gI().sendMessage(Constaint.MESSAGE_BACSI_BAOVE, username);
                                turnFunction = false;
                            }
                            //Hủy bảo vệ
                            else{
                                lastUserInteracted = "";
                                Service.gI().sendMessage(Constaint.MESSAGE_BACSI_HUY_BAOVE, username);
                                turnFunction = false;
                            }
                            for(var u: playersUI){
                                u.function.setVisible(turnFunction);
                            }
                        }
                        if(!username.equals(thisUI.getUsername())){
                            switch(idRole){
                                case Constaint.ROLE_XATHU:{
                                    Service.gI().sendMessage(Constaint.MESSAGE_XATHU_SHOOT, username);
                                    turnOnBtnFunction(false);
                                    break;
                                }
                                case Constaint.ROLE_THAYBOI:{
                                    Service.gI().sendMessage(Constaint.MESSAGE_THAYBOI_SEE, username);
                                    turnOnBtnFunction(false);
                                    break;
                                }
                                case Constaint.ROLE_TIENTRI:{
                                    Service.gI().sendMessage(Constaint.MESSAGE_TIENTRI_SEE, username);
                                    turnOnBtnFunction(false);
                                    break;
                                }
                                case Constaint.ROLE_BACSI:{
                                    Service.gI().sendMessage(Constaint.MESSAGE_BACSI_BAOVE, username);
                                    for(var u: playersUI){
                                        u.function.setVisible(false);
                                    }
                                    break;
                                }
                                case Constaint.ROLE_THAYDONG:{
                                    Service.gI().sendMessage(Constaint.MESSAGE_THAYDONG_HOISINH, username);
                                    addMessage("Bạn đã chọn hồi sinh người chơi " +username+ ".");
                                    disableSpecial = true;
                                    turnOnBtnFunction(false);
                                    break;
                                }
                                case Constaint.ROLE_SOI:{
                                    //Vote người khác
                                    if(!lastUserInteracted.equals(username)){
                                        lastUserInteracted = username ;
                                        Service.gI().sendMessage(Constaint.MESSAGE_WOLF_VOTES, username);
                                        turnFunction = false;
                                    }
                                    //Hủy vote
                                    else{
                                        lastUserInteracted = "";
                                        Service.gI().sendMessage(Constaint.MESSAGE_WOLF_CANCEL_VOTES, username);
                                        turnFunction = false;
                                    }
                                    for(var u: playersUI){
                                        u.function.setVisible(turnFunction);
                                    }
                                    break;
                                }
                                case Constaint.ROLE_SOITIENTRI:{
                                    Service.gI().sendMessage(Constaint.MESSAGE_SOITIENTRI_SEE, username);
                                    turnOnBtnFunction(false);
                                    break;
                                }
                            }
                        }
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
            try {
                return this.name.getText().split("\\ ")[1];
            } catch (Exception e) {
                return "";
            }
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
            if(p.isDie){
                var pathAvatar = Constaint.pathRoot + "/assets/avatar/rip.png";
                var icon = gui.resizeImage(pathAvatar, 75, 75);
                this.avatar.setIcon(icon);
            }
            else{
                byte avatarId  = p.avatarId;
                avatar.setIcon(gui.resizeImage(Constaint.pathRoot + "/assets/avatar/avatar_" + avatarId+ ".jpg", 75, 75));
            }
            String currentNamePlayer = Service.gI().dataSource.player.namePlayer;
            name.setText(index + " " + p.namePlayer);
            if(p.namePlayer.equals(currentNamePlayer)){
                name.setForeground(Color.red);
                thisUI = this;
            }
            if(idRoomOwner.equals(p.namePlayer)){
                String path = Constaint.pathRoot + "/assets/crown.png";
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
        JLabel[] functions = new JLabel[]{f0, f1, f2, f3, f4, f5, f6, f7, f8};

        for(int i = 0; i < 9;i++){
            playersUI[i] = new PlayerUI();
            PlayerUI pU = playersUI[i];
            pU.index = i+1;
            pU.setVote(votes[i]);
            pU.setAvatar(avatars[i]);
            pU.setVoteIcon(voteicons[i]);
            pU.setIconRole(iconroles[i]);
            pU.setName(names[i]);
            pU.avatar.setIcon(gui.resizeImage(Constaint.pathRoot + "/assets/avatar/avatar_unknown.jpg", 75, 75));
            pU.name.setText("");
            pU.iconRole.setVisible(false);
            pU.voteIcon.setVisible(false);
            pU.vote.setVisible(false);
            pU.setFunction(functions[i]);
            pU.function.setVisible(false);
        }
        turnDay();
    }
    
    private void resetUI(PlayerUI ui){
        ui.avatar.setIcon(gui.resizeImage(Constaint.pathRoot + "/assets/avatar/avatar_unknown.jpg", 75, 75));
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
        DefaultCaret caret = (DefaultCaret)txtChat.getCaret();
        caret.setUpdatePolicy(DefaultCaret.OUT_BOTTOM);
        //caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }
    
    /**
     * Hàm update thanh status khi stage thay đổi
     * @param stage đối tượng Stage bao gồm time countdown, message
     */
    public void updateStage(Stage stage){
        lastUserInteracted = "";
        turnFunction = false;
        for(PlayerUI pUI: playersUI){
            //Ẩn cái nút chức năng đi
            pUI.function.setVisible(false);
            //Xóa vote icon
            pUI.voteIcon.setVisible(false);
            //Xóa cái bảng vote
            pUI.vote.setVisible(false);
        }
        btnFunction.setEnabled(false);
        if(idRole == Constaint.ROLE_XATHU 
                && (stage.currentStage == Constaint.STAGE_DISCUSSING || stage.currentStage == Constaint.STAGE_VOTING )){
            turnOnBtnFunction(true);
        }
        
        gui.changePanel(mainPanel, new PanelTime(stage));
    }
    
    //Xử lý trong quá trình chơi
    /**
     * Bật chế độ ban ngày cho UI
     */
    public void turnDay(){
        if(idRole != -128)
            gui.playSound("rooster");
        Color day = new Color(242, 242, 242);
        txtInput.setBackground(day);
        txtChat.setBackground(day);
        txtChat.setForeground(Color.BLACK);
        btnFunction.setBackground(day);
        if(thisUI != null)
            txtInput.setEnabled(!thisUI.isDie);
        else
            txtInput.setEnabled(true);
    }
    
    /**
     * Bật chế độ ban đêm cho UI
     */
    public void loadWolfs(List<Player> wolfs){
        teammateWolfs.clear();
        for(Player wolf: wolfs){
            teammateWolfs.add(wolf.namePlayer);
            var pUI = getUIbyUserName(wolf.namePlayer);
            if(!pUI.getUsername().equals(thisUI.getUsername())){
                var path = Constaint.pathRoot + "/assets/icon_role/icon_role8.png";
                var icon = gui.resizeImage(path, 36, 36);
                pUI.iconRole.setIcon(icon);
                pUI.iconRole.setVisible(true);
            }
        }
    }
    
    public void turnNight(){
        gui.playSound("wolf");
        Color day = new Color(153, 153, 153);
        txtInput.setBackground(day);
        txtChat.setBackground(day);
        txtChat.setForeground(Color.WHITE);
        btnFunction.setBackground(day);
        txtInput.setEnabled(
                idRole == Constaint.ROLE_SOI || idRole == Constaint.ROLE_SOITIENTRI || 
                idRole == Constaint.ROLE_THAYDONG || thisUI.isDie
        );
    }
    
    public void reloadVotes(List<Player> playerStates){
        for(var p: playerStates){
            String username = p.namePlayer;
            PlayerVote pV = p.playerVote;
            for(PlayerUI ui: playersUI){
                //Trong trường hợp cái UI đó đại diện cho cái Vote đang duyệt
                if(ui.getUsername().equals(username)){
                    //Set cái tay chỉ vào UI
                    if(pV.voteCount > 0)
                        ui.setVoteIcon(pV.voteCount);
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
    
    public void setDiePlayer(Player pTarget, byte role){
        for(var u: playersUI){
            if(u.getUsername().equals(pTarget.namePlayer)){
                String pathRole;
                if(role == -1){
                    pathRole = Constaint.pathRoot + "/assets/icon_role/icon_role_-1.png";
                }
                else{
                    pathRole = Constaint.pathRoot + "/assets/icon_role/icon_role" + role + ".png";
                }
                var iconRole = gui.resizeImage(pathRole, 36, 36);
                u.iconRole.setIcon(iconRole);
                u.iconRole.setVisible(true);
                
                var pathAvatar = Constaint.pathRoot + "/assets/avatar/rip.png";
                var icon = gui.resizeImage(pathAvatar, 75, 75);
                u.avatar.setIcon(icon);
                u.isDie = true;
                gui.playSound("playerdie");
            }
        }
        if(pTarget.namePlayer.equals(thisUI.getUsername())){
            thisUI.isDie = true;
            txtInput.setEnabled(false);
            disableSpecial = true;
            addMessage("Bạn đã bị giết.");
        }
    }
    
    public void setRevivalPlayer(Player pTarget, byte role){
        for(var u: playersUI){
            if(u.getUsername().equals(pTarget.namePlayer)){
                String pathRole;
                if(role == -1){
                    pathRole = Constaint.pathRoot + "/assets/icon_role/icon_role_-1.png";
                }
                else{
                    pathRole = Constaint.pathRoot + "/assets/icon_role/icon_role" + role + ".png";
                }
                var iconRole = gui.resizeImage(pathRole, 36, 36);
                u.iconRole.setIcon(iconRole);
                u.iconRole.setVisible(true);
                
                var pathAvatar = Constaint.pathRoot + "/assets/avatar/avatar_1.jpg";
                var icon = gui.resizeImage(pathAvatar, 75, 75);
                u.avatar.setIcon(icon);
                u.isDie = false;
            }
        }
        if(pTarget.namePlayer.equals(thisUI.getUsername())){
            thisUI.isDie = false;
            txtInput.setEnabled(true);
            addMessage("Bạn đã được hồi sinh bởi Thầy đồng.");
        }
    }
    
    public void setProtect(String username){
        for(var pUJ : playersUI){
            pUJ.function.setVisible(false);
        }
        if(username != null && !username.equals("")){
            var pUI = getUIbyUserName(username);
            pUI.function.setVisible(true);
        }
    }
    
    public void showShooter(String username){
        var pUI = getUIbyUserName(username);
        if(pUI != null){
            var path = Constaint.pathRoot + "/assets/icon_role/icon_role5.png";
            var icon = gui.resizeImage(path, 36, 36);
            pUI.iconRole.setIcon(icon);
            pUI.iconRole.setVisible(true);
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
        this.idRole = idRole;
        if(isFirstPickRole){
            isFirstPickRole = false;
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
                //Set icon function cho cái nút
                String imgFunction = "none.png";
                switch(idRole){
                    case Constaint.ROLE_XATHU:{
                        imgFunction = "gunner_bullet.png";
                        break;
                    }
                    case Constaint.ROLE_THAYBOI:{
                        imgFunction = "seek.png";
                        break;
                    }
                    case Constaint.ROLE_TIENTRI:{
                        imgFunction = "seekVip.png";
                        break;
                    }
                    case Constaint.ROLE_BACSI:{
                        imgFunction = "protect.png";
                        break;
                    }
                    case Constaint.ROLE_THAYDONG:{
                        imgFunction = "revival.png";
                        break;
                    }
                    case Constaint.ROLE_SOITIENTRI:{
                        imgFunction = "seekWolf.png";
                        break;
                    }
                    case Constaint.ROLE_SOI:{
                        imgFunction = "wolf.png";
                        break;
                    }
                }
                hasInteraction = !imgFunction.equals("none.png");
                var path = Constaint.pathRoot + "/assets/function/" + imgFunction;
                var icon = gui.resizeImage(path, 40, 40);
                btnFunction.setIcon(icon);
                //btnFunction.setEnabled(turnFunction && !disableSpecial);
                if(!imgFunction.equals("none.png")){
                    var pathSelectable = Constaint.pathRoot + "/assets/function/selectable_" + imgFunction;
                    var iconFunction = gui.resizeImage(pathSelectable, 50, 50);
                    for(var u: playersUI){
                        u.function.setIcon(iconFunction);
                    }
                }
                addMessage("Bạn đã nhận được vai trò " + Utils.StringUtils.getRoleNameById(idRole));
            }).start();
        }
        else{
            PlayerUI uj = null;
            for(PlayerUI u: playersUI){
                if(u.p != null && u.p.namePlayer.equals(Service.gI().dataSource.player.namePlayer)) {
                    uj = u;
                }
            }
            if(uj != null){
                String path = Constaint.pathRoot + "/assets/icon_role/icon_role" + idRole + ".png";
                uj.iconRole.setIcon(gui.resizeImage(path, 36, 36));
            }
            //Set icon function cho cái nút
            String imgFunction = "none.png";
            switch(idRole){
                case Constaint.ROLE_SOI:{
                    imgFunction = "wolf.png";
                    break;
                }
            }
            hasInteraction = !imgFunction.equals("none.png");
            var path = Constaint.pathRoot + "/assets/function/" + imgFunction;
            var icon = gui.resizeImage(path, 40, 40);
            btnFunction.setIcon(icon);
            //btnFunction.setEnabled(turnFunction && !disableSpecial);
            if(!imgFunction.equals("none.png")){
                var pathSelectable = Constaint.pathRoot + "/assets/function/selectable_" + imgFunction;
                var iconFunction = gui.resizeImage(pathSelectable, 50, 50);
                for(var u: playersUI){
                    u.function.setIcon(iconFunction);
                }
            }
        }
        
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
        btnExit = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtChat = new javax.swing.JTextArea();
        txtInput = new javax.swing.JTextField();
        btnFunction = new javax.swing.JButton();
        btnStart = new javax.swing.JButton();
        hand0 = new javax.swing.JLabel();
        f0 = new javax.swing.JLabel();
        avatar0 = new javax.swing.JLabel();
        name0 = new javax.swing.JLabel();
        roleUser0 = new javax.swing.JLabel();
        hand1 = new javax.swing.JLabel();
        f1 = new javax.swing.JLabel();
        avatar1 = new javax.swing.JLabel();
        name1 = new javax.swing.JLabel();
        roleUser1 = new javax.swing.JLabel();
        hand2 = new javax.swing.JLabel();
        f2 = new javax.swing.JLabel();
        avatar2 = new javax.swing.JLabel();
        name2 = new javax.swing.JLabel();
        roleUser2 = new javax.swing.JLabel();
        hand3 = new javax.swing.JLabel();
        f3 = new javax.swing.JLabel();
        avatar3 = new javax.swing.JLabel();
        name3 = new javax.swing.JLabel();
        roleUser3 = new javax.swing.JLabel();
        hand4 = new javax.swing.JLabel();
        f4 = new javax.swing.JLabel();
        avatar4 = new javax.swing.JLabel();
        name4 = new javax.swing.JLabel();
        roleUser4 = new javax.swing.JLabel();
        hand5 = new javax.swing.JLabel();
        f5 = new javax.swing.JLabel();
        avatar5 = new javax.swing.JLabel();
        name5 = new javax.swing.JLabel();
        roleUser5 = new javax.swing.JLabel();
        hand6 = new javax.swing.JLabel();
        f6 = new javax.swing.JLabel();
        avatar6 = new javax.swing.JLabel();
        name6 = new javax.swing.JLabel();
        roleUser6 = new javax.swing.JLabel();
        hand7 = new javax.swing.JLabel();
        f7 = new javax.swing.JLabel();
        avatar7 = new javax.swing.JLabel();
        name7 = new javax.swing.JLabel();
        roleUser7 = new javax.swing.JLabel();
        hand8 = new javax.swing.JLabel();
        f8 = new javax.swing.JLabel();
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
        txtChat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
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
        txtInput.setBounds(400, 430, 380, 59);

        btnFunction.setBackground(new java.awt.Color(204, 204, 204));
        btnFunction.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/function/none.png"))); // NOI18N
        btnFunction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFunctionActionPerformed(evt);
            }
        });
        jPanel2.add(btnFunction);
        btnFunction.setBounds(792, 430, 60, 59);

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

        f0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        f0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/function/selectable_gunner_bullet.png"))); // NOI18N
        jPanel2.add(f0);
        f0.setBounds(50, 80, 75, 75);

        avatar0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        avatar0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/avatar/rip.png"))); // NOI18N
        jPanel2.add(avatar0);
        avatar0.setBounds(50, 80, 63, 75);

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

        f1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        f1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/function/selectable_gunner_bullet.png"))); // NOI18N
        f1.setMaximumSize(new java.awt.Dimension(75, 75));
        f1.setMinimumSize(new java.awt.Dimension(75, 75));
        f1.setPreferredSize(new java.awt.Dimension(75, 75));
        jPanel2.add(f1);
        f1.setBounds(170, 80, 75, 75);

        avatar1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        avatar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/avatar/rip.png"))); // NOI18N
        jPanel2.add(avatar1);
        avatar1.setBounds(170, 80, 63, 75);

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

        f2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        f2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/function/selectable_gunner_bullet.png"))); // NOI18N
        f2.setMaximumSize(new java.awt.Dimension(75, 75));
        f2.setMinimumSize(new java.awt.Dimension(75, 75));
        f2.setPreferredSize(new java.awt.Dimension(75, 75));
        jPanel2.add(f2);
        f2.setBounds(290, 80, 75, 75);

        avatar2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        avatar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/avatar/rip.png"))); // NOI18N
        jPanel2.add(avatar2);
        avatar2.setBounds(290, 80, 63, 75);

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

        f3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        f3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/function/selectable_gunner_bullet.png"))); // NOI18N
        f3.setMaximumSize(new java.awt.Dimension(75, 75));
        f3.setMinimumSize(new java.awt.Dimension(75, 75));
        f3.setPreferredSize(new java.awt.Dimension(75, 75));
        jPanel2.add(f3);
        f3.setBounds(50, 240, 75, 75);

        avatar3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        avatar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/avatar/rip.png"))); // NOI18N
        jPanel2.add(avatar3);
        avatar3.setBounds(50, 240, 63, 75);

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

        f4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        f4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/function/selectable_gunner_bullet.png"))); // NOI18N
        f4.setMaximumSize(new java.awt.Dimension(75, 75));
        f4.setMinimumSize(new java.awt.Dimension(75, 75));
        f4.setPreferredSize(new java.awt.Dimension(75, 75));
        jPanel2.add(f4);
        f4.setBounds(170, 240, 75, 75);

        avatar4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        avatar4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/avatar/rip.png"))); // NOI18N
        jPanel2.add(avatar4);
        avatar4.setBounds(170, 240, 63, 75);

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

        f5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        f5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/function/selectable_gunner_bullet.png"))); // NOI18N
        f5.setMaximumSize(new java.awt.Dimension(75, 75));
        f5.setMinimumSize(new java.awt.Dimension(75, 75));
        f5.setPreferredSize(new java.awt.Dimension(75, 75));
        jPanel2.add(f5);
        f5.setBounds(290, 240, 75, 75);

        avatar5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        avatar5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/avatar/rip.png"))); // NOI18N
        jPanel2.add(avatar5);
        avatar5.setBounds(290, 240, 63, 75);

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

        f6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        f6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/function/selectable_gunner_bullet.png"))); // NOI18N
        f6.setMaximumSize(new java.awt.Dimension(75, 75));
        f6.setMinimumSize(new java.awt.Dimension(75, 75));
        f6.setPreferredSize(new java.awt.Dimension(75, 75));
        jPanel2.add(f6);
        f6.setBounds(50, 390, 75, 75);

        avatar6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        avatar6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/avatar/rip.png"))); // NOI18N
        jPanel2.add(avatar6);
        avatar6.setBounds(50, 390, 63, 75);

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

        f7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        f7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/function/selectable_gunner_bullet.png"))); // NOI18N
        f7.setMaximumSize(new java.awt.Dimension(75, 75));
        f7.setMinimumSize(new java.awt.Dimension(75, 75));
        f7.setPreferredSize(new java.awt.Dimension(75, 75));
        jPanel2.add(f7);
        f7.setBounds(170, 390, 75, 75);

        avatar7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        avatar7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/avatar/rip.png"))); // NOI18N
        jPanel2.add(avatar7);
        avatar7.setBounds(170, 390, 63, 75);

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

        f8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        f8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/function/selectable_gunner_bullet.png"))); // NOI18N
        f8.setMaximumSize(new java.awt.Dimension(75, 75));
        f8.setMinimumSize(new java.awt.Dimension(75, 75));
        f8.setPreferredSize(new java.awt.Dimension(75, 75));
        jPanel2.add(f8);
        f8.setBounds(290, 390, 75, 75);

        avatar8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        avatar8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/avatar/rip.png"))); // NOI18N
        jPanel2.add(avatar8);
        avatar8.setBounds(290, 390, 63, 75);

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
    
    private boolean turnFunction = false;
    
    public void turnOnBtnFunction(boolean flag){
        for(var u: playersUI){
            u.function.setVisible(false);
        }
        btnFunction.setEnabled(flag && !disableSpecial && !thisUI.isDie);
    }
    
    private void btnFunctionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFunctionActionPerformed
        if(!hasInteraction) return;
        turnFunction = !turnFunction;
        for(var u: playersUI){
            //Nếu là thầy đồng thì hiện các player ngỏm
            if(idRole == Constaint.ROLE_THAYDONG && u.isDie && !u.getUsername().equals(thisUI.getUsername())){
                u.function.setVisible(turnFunction);
            }
            //Còn lại là sói không chọn người chết và sói khác
            else if(!teammateWolfs.contains(u.getUsername()) && !u.isDie)
                u.function.setVisible(turnFunction);
        }
    }//GEN-LAST:event_btnFunctionActionPerformed
    
    private void sendMessage(){
        String content = txtInput.getText().strip();
        if(content.equals("")){
            return;
        }
        txtInput.setText("");
        byte typeSend = idRole == -128 ? Constaint.MESSAGE_CHAT : Constaint.MESSAGE_CHAT_IN_GAME;
        Service.gI().sendMessage(typeSend, content);
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
    private javax.swing.JButton btnFunction;
    private javax.swing.JButton btnStart;
    private javax.swing.JLabel f0;
    private javax.swing.JLabel f1;
    private javax.swing.JLabel f2;
    private javax.swing.JLabel f3;
    private javax.swing.JLabel f4;
    private javax.swing.JLabel f5;
    private javax.swing.JLabel f6;
    private javax.swing.JLabel f7;
    private javax.swing.JLabel f8;
    private javax.swing.JLabel hand0;
    private javax.swing.JLabel hand1;
    private javax.swing.JLabel hand2;
    private javax.swing.JLabel hand3;
    private javax.swing.JLabel hand4;
    private javax.swing.JLabel hand5;
    private javax.swing.JLabel hand6;
    private javax.swing.JLabel hand7;
    private javax.swing.JLabel hand8;
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
