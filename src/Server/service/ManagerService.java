package Server.service;

import javax.swing.table.DefaultTableModel;
import Model.*;

public class ManagerService {
    public DefaultTableModel model;
    private static final ManagerService managerService = new ManagerService();
    private ManagerService(){}
    
    public static ManagerService gI(){
        return managerService;
    }
    
    public static void init(){
        RoleService.gI().init();
        RoomService.gI().init();
        SocketService.gI().init();
        
        //Mock up player
        for(int i = 1 ; i <= 8 ; i++){
            AIPlayer p = PlayerService.gI().makeBot("" + i);
            PlayerService.gI().bots.add(p);
        }
    }
    
    public static void stop(){
        RoleService.gI().stop();
        RoomService.gI().stop();
        SocketService.gI().stop();
    }
    
    public static void err(TemplateService x, String action){
        System.err.println(x.getServiceName() + " " + action + " failed!");
        System.exit(0);
    }
    
    public static void initSuccess(String nameService){
        System.out.println(
                String.format("[%s] %s init success !", nameService, nameService));
    }
}
