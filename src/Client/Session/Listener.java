package Client.Session;

import Client.GameRoom;
import Client.HomePanel;
import Client.utils.gui;
import Model.*;
import config.Constaint;
import java.io.ObjectInputStream;
import java.util.List;
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
                System.out.println("Receive : " + message.getMessageCode());
                switch(message.getMessageCode()){
                    case Constaint.MESSAGE_GET_CONFIG_ROLE:{
                        service.dataSource.roleConfigs = (List<Role>)data;
                        break;
                    }
                    case Constaint.MESSAGE_CREATE_ROOM:{
                        Room room = (Room) data;
                        if(room != null){
                            System.out.println("[CREATE] " +room.toString());
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
                        if(data == null){
                            gui.showMessage("Tên bạn chọn đã có người đặt !");
                            break;
                        }
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
                        System.out.println("Role nhận được : " + idRole);
                        service.panelGame.setRoleMySelf(idRole);
                        break;
                    }
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
                                String messageStatus = Utils.StringUtils.getMessageStageByRole(currentStage, service.dataSource.player.playerEffect.idRole);
                                stage = new Stage(currentStage, messageStatus, Constaint.Time.TIME_VOTING);
                                service.panelGame.updateStage(stage);
                                break;
                            }
                            
                        }
                        break;
                    }
                    //Message đêm gọi các role dậy
                    case Constaint.WakeUp.ROLE_BACSI:
                    case Constaint.WakeUp.ROLE_SOI:
                    case Constaint.WakeUp.ROLE_SOITIENTRI:
                    case Constaint.WakeUp.ROLE_THAYBOI:
                    case Constaint.WakeUp.ROLE_THAYDONG:
                    case Constaint.WakeUp.ROLE_TIENTRI:{
                        byte idRole = service.dataSource.player.playerEffect.idRole ;
                        String messageAlert = String.format("[Server]: Bạn là %s. Bạn có thể %s",
                                Utils.StringUtils.getRoleNameById(idRole),Utils.StringUtils.getDescFuncRole(idRole));
                        service.panelGame.addMessage(messageAlert);
                        break;
                    }
                    //Reload lại danh sách sói votes
                    case Constaint.MESSAGE_WOLF_VOTES:{
                        List<Player> playerStates = (List<Player>)data;
                        service.panelGame.reloadWolfVotes(playerStates);
                        break;
                    }
                    default:{
                        System.out.println("[NotProcess] " + message.getMessageCode());
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
}
