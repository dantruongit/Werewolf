package Client.Session;

import Client.GameRoom;
import Client.HomePanel;
import Client.Joker_WIN;
import Client.Wolf_WIN;
import Client.utils.gui;
import Model.*;
import Utils.StringUtils;
import Were_Wolf_Display.Village_WIN;
import config.Constaint;
import java.io.ObjectInputStream;
import java.util.List;
import javax.swing.JPanel;
import payload.Message;

public class Listener extends Thread{
    private ObjectInputStream ois;

    public void setOis(ObjectInputStream ois) {
        this.ois = ois;
    }
    
    @Override
    public void run() {
        Service service = Service.gI();
        while(true){
            try {
                Message message = (Message)ois.readObject();
                Object data = message.getData();
//                System.out.println("Receive : " + message.getMessageCode());
                switch(message.getMessageCode()){
                    case Constaint.MESSAGE_GET_CONFIG_ROLE:{
                        service.dataSource.roleConfigs = (List<Role>)data;
                        break;
                    }
                    case Constaint.MESSAGE_CREATE_ROOM:{
                        Room room = (Room) data;
                        if(room != null){
                            service.dataSource.player.room = room;
                            service.frm.changePanel(new GameRoom());
                        }
                        break;
                    }
                    case Constaint.MESSAGE_LOAD_ROOM:{
                        service.dataSource.rooms = (List<Room>)data;
                        break;
                    }
                    case Constaint.MESSAGE_JOIN_SERVER:{
//                        if(data == null){
//                            gui.showMessage("Tên bạn chọn đã có người đặt !");
//                            break;
//                        }
                        service.dataSource.player = (Player)data;
                        service.frm.changePanel(new HomePanel());
                        break;
                    }
                    case Constaint.MESSAGE_JOIN_ROOM:{
                        if(data == null){
                            gui.showMessage("Phòng này không khả dụng !");
                            break;
                        }
                        service.dataSource.player.room = (Room)data;
                        service.frm.changePanel(new GameRoom());
                        break;
                    }
                    case Constaint.MESSAGE_CHAT:{
                        String chat = (String)data;
                        if(service.panelGame != null)
                            service.panelGame.addMessage(chat);
                        break;
                    }
                    case Constaint.MESSAGE_RELOAD_PLAYER:{
                        Room room = (Room)data;
                        if(room != null && service.panelGame != null){
                            service.dataSource.player.room = room;
                            service.panelGame.reloadPlayers();
                        }
                        break;
                    }
                    case Constaint.MESSAGE_ROOM_REMOVED:{
                        gui.showMessage("Chủ phòng đã rời. Room sẽ bị đóng !");
                        service.panelGame = null;
                        service.frm.changePanel(new HomePanel());
                        break;
                    }
                    case Constaint.MESSAGE_START_GAME:{
                        Game game = (Game)data;
                        service.dataSource.player.game = game;
                        service.panelGame.gameStarted(true);
                        break;
                    }
                    //Nhận role trong game
                    case Constaint.MESSAGE_PICK_ROLE:{
                        Integer i = (Integer)data;
                        byte idRole = i.byteValue();
                        service.dataSource.player.playerEffect = new PlayerEffect();
                        service.dataSource.player.playerEffect.idRole = idRole;
                        service.panelGame.setRoleMySelf(idRole);
                        break;
                    }
                    //Done
                    case Constaint.STAGE_CHANGE:{
                        byte currentStage = (byte)data;
                        Stage stage;
                        switch(currentStage){
                            case Constaint.STAGE_SLEEPING:{
                                String messageStatus = Utils.StringUtils.getMessageStageByRole(currentStage, service.dataSource.player.playerEffect.idRole);
                                stage = new Stage(currentStage, messageStatus, Constaint.Time.TIME_SLEEPING);
                                service.panelGame.turnNight();
                                service.panelGame.updateStage(stage);
                                break;
                            }
                            case Constaint.STAGE_DISCUSSING:{
                                String messageStatus = Utils.StringUtils.getMessageStageByRole(currentStage, service.dataSource.player.playerEffect.idRole);
                                stage = new Stage(currentStage, messageStatus, Constaint.Time.TIME_DISCUSSING);
                                service.panelGame.turnDay();
                                service.panelGame.updateStage(stage);
                                break;
                            }
                            case Constaint.STAGE_VOTING:{
                                gui.playSound("votingbell");
                                String messageStatus = Utils.StringUtils.getMessageStageByRole(currentStage, service.dataSource.player.playerEffect.idRole);
                                stage = new Stage(currentStage, messageStatus, Constaint.Time.TIME_VOTING);
                                service.panelGame.updateStage(stage);
                                break;
                            }
                            
                        }
                        break;
                    }
                    //Done
                    //Message đêm gọi các role dậy
                    case Constaint.WakeUp.ROLE_BACSI:
                    case Constaint.WakeUp.ROLE_SOI:
                    case Constaint.WakeUp.ROLE_SOITIENTRI:
                    case Constaint.WakeUp.ROLE_THAYBOI:
                    case Constaint.WakeUp.ROLE_THAYDONG:
                    case Constaint.WakeUp.ROLE_TIENTRI:
                    case Constaint.WakeUp.ROLE_XATHU:{
                        byte idRole = service.dataSource.player.playerEffect.idRole ;
                        if(!service.panelGame.hasShowTutorial){
                            String messageAlert = String.format("[Server]: Bạn là %s.\nBạn có thể %s",
                                Utils.StringUtils.getRoleNameById(idRole),Utils.StringUtils.getDescFuncRole(idRole));
                            service.panelGame.addMessage(messageAlert);
                            service.panelGame.hasShowTutorial = true;
                        }
                        service.panelGame.turnOnBtnFunction(true);
                        break;
                    }
                    //Reload lại danh sách votes
                    case Constaint.MESSAGE_UPDATE_VOTING:{
                        List<Player> playerStates = (List<Player>)data;
                        service.panelGame.reloadVotes(playerStates);
                        break;
                    }
                    //Bem người chơi và hiển thị role của họ
                    case Constaint.MESSAGE_PLAYER_DIE:{
                        Player pTarget = (Player)data;
                        byte role = (byte)message.getTmp();
                        service.panelGame.setDiePlayer(pTarget, role);
                        break;
                    }
                    //Hồi sinh người chơi và hiển thị role của họ
                    case Constaint.MESSAGE_PLAYER_REVIVAL:{
                        Player pTarget = (Player)data;
                        byte role = (byte)message.getTmp();
                        service.panelGame.setRevivalPlayer(pTarget, role);
                        break;
                    }
                    //Nhận danh sách sói
                    case Constaint.MESSAGE_LIST_OTHER_WOLFS:{
                        List<Player> wolfs = (List<Player>)data;
                        service.panelGame.loadWolfs(wolfs);
                        break;
                    }
                    case Constaint.MESSAGE_THAYBOI_SEE:{
                        byte teamTarget = (byte)data;
                        service.panelGame.addMessage("Người chơi đó thuộc phe " + StringUtils.getTeamById(teamTarget) + ".");
                        break;
                    }
                    case Constaint.MESSAGE_TIENTRI_SEE:{
                        byte roleTarget = (byte)data;
                        service.panelGame.addMessage("Vai trò của người chơi đó là [" + StringUtils.getRoleNameById(roleTarget) +"].");
                        break; 
                    }
                    case Constaint.MESSAGE_SOITIENTRI_SEE:{
                        Player pTarget = (Player)data;
                        String content = String.format("Sói tiên tri đã soi người chơi %s là [%s].",pTarget.namePlayer,  StringUtils.getRoleNameById((byte)message.getTmp()));
                        service.panelGame.addMessage(content);
                        break; 
                    }
                    case Constaint.MESSAGE_BACSI_BAOVE:{
                        String userProtect = (String)data;
                        service.panelGame.setProtect(userProtect);
                        break;
                    }
                    //Xạ thủ lộ role 
                    case Constaint.MESSAGE_XATHU_SHOOT:{
                        String username = (String)data;
                        service.panelGame.showShooter(username);
                        break;
                    }
                    //Hết đạn 
                    case Constaint.MESSAGE_XATHU_OUT_OF_BULLET:{
                        service.panelGame.disableSpecial = true;
                        break;
                    }
                    //End game
                    case Constaint.MESSAGE_VILLAGERS_WIN:
                    case Constaint.MESSAGE_WOLFS_WIN:
                    case Constaint.MESSAGE_JOKER_WIN:{
                        JPanel panelWin = null;
                        switch(message.getMessageCode()){
                            case Constaint.MESSAGE_VILLAGERS_WIN:{
                                panelWin = new Village_WIN();
                                break;
                            }
                            case Constaint.MESSAGE_WOLFS_WIN:{
                                panelWin = new Wolf_WIN();
                                break;
                            }
                            case Constaint.MESSAGE_JOKER_WIN:{
                                panelWin = new Joker_WIN();
                                break;
                            }
                        }
                        if(panelWin != null)
                            service.frm.changePanel(panelWin);
                        new Thread(()->{
                            try {
                                Thread.sleep(3000);
                                service.frm.changePanel(new GameRoom());
                            } catch (Exception e) {
                            }
                        }).start();
                        break;
                    }
                    default:{
                        //System.out.println("[NotProcess] " + message.getMessageCode());
                        break;
                    }
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
    }
    
}
