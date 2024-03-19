package Server.service;

import Model.Role;
import Model.Player;
import Model.Room;
import Utils.Utils;
import config.Constaint;
import java.util.ArrayList;
import java.util.List;
import payload.Message;

public class RoleService implements TemplateService{
    private final static RoleService roleService = new RoleService();
    private final String nameService = "RoleService";
    public static RoleService gI(){
        return roleService;
    }
    
    public List<Role> roles = new ArrayList<>();
    
    @Override
    public void init(){
        roles.add(new Role(
                Constaint.ROLE_DANLANG, 
                "Dân làng", 
                "Dân làng không có kỹ năng đặc biệt gì, chỉ ăn với ngủ",
                Constaint.TEAM_VILLAGE,
                1, 
                8));
        roles.add(new Role(
                Constaint.ROLE_THAYBOI, 
                "Thầy bói", 
                "Thuộc phe dân làng, có thể kiểm tra người đó có phải là sói hoặc dân làng",
                Constaint.TEAM_VILLAGE,
                0, 
                2));
        roles.add(new Role(
                Constaint.ROLE_BACSI, 
                "Bác sĩ", 
                "Bạn có thể chọn bảo vệ 1 người vào ban ngày, ban đêm người đó sẽ không thể bị giết",
                Constaint.TEAM_VILLAGE,
                0, 
                1));
        roles.add(new Role(
                Constaint.ROLE_BANSOI, 
                "Bán sói", 
                "Bán sói là dân làng, nhưng bị cắn sẽ thành sói thay vì chết",
                Constaint.TEAM_VILLAGE,
                0, 
                1));
        roles.add(new Role(
                Constaint.ROLE_THANGNGO, 
                "Thằng ngố", 
                "Thuộc phe thứ 3, thắng khi bị làng treo cổ",
                Constaint.TEAM_THIRD,
                0, 
                1));
        roles.add(new Role(
                Constaint.ROLE_XATHU, 
                "Xạ thủ", 
                "Thuộc phe dân làng, có 2 viên đạn bắn trong ban ngày, lộ vị trí sau phát đầu tiên",
                Constaint.TEAM_VILLAGE,
                0, 
                1));
        roles.add(new Role(
                Constaint.ROLE_THAYDONG, 
                "Thầy đồng", 
                "Thuộc phe dân làng, có thể hồi sinh người chơi khác 1 lần và trò chuyện với người chết",
                Constaint.TEAM_VILLAGE,
                0, 
                1));
        roles.add(new Role(
                Constaint.ROLE_TIENTRI, 
                "Tiên tri", 
                "Thuộc phe dân làng, mỗi đêm có thể xem vai trò người khác 1 lần",
                Constaint.TEAM_VILLAGE,
                0, 
                1));
        roles.add(new Role(
                Constaint.ROLE_SOI, 
                "Sói", 
                "Thuộc phe sói, ban đêm chuyên đi cắn người",
                Constaint.TEAM_WOLF,
                1, 
                2));
        roles.add(new Role(
                Constaint.ROLE_SOITIENTRI, 
                "Sói tiên tri", 
                "Thuộc phe sói,có khả năng tiên tri và ban đêm chuyên đi cắn người",
                Constaint.TEAM_WOLF,
                0, 
                1));
        ManagerService.initSuccess(nameService);
    }
    
    public void setPropertyRole(Player p, int role){
        p.playerEffect.idRole = (byte)role;
        switch(role){
            case Constaint.ROLE_SOITIENTRI:{
                p.playerEffect.isSoiTienTri = true;
                break;
            }
            case Constaint.ROLE_SOI:{
                p.playerEffect.isSoi = true;
                break;
            }
            case Constaint.ROLE_TIENTRI:{
                p.playerEffect.isTienTri = true;
                break;
            }
            case Constaint.ROLE_THAYDONG:{
                p.playerEffect.isThayDong = true;
                break;
            }
            case Constaint.ROLE_BACSI:{
                p.playerEffect.isBacSi = true;
                break;
            }
            case Constaint.ROLE_THAYBOI:{
                p.playerEffect.isThayBoi = true;
                break;
            }
            case Constaint.ROLE_DANLANG:{
                p.playerEffect.isDanLang = true;
                break;
            }
            case Constaint.ROLE_BANSOI:{
                p.playerEffect.isBanSoi = true;
                break;
            }
            case Constaint.ROLE_THANGNGO:{
                p.playerEffect.isChuaHe = true;
                break;
            }
            case Constaint.ROLE_XATHU:{
                p.playerEffect.isXaThu = true;
                p.playerEffect.bullet = 2;
                break;
            }
        }
    }
    
    public void rollRoles(Room room){
        List<Integer> arr = new ArrayList<>();
        for(Role role : room.configs){
            for(int i = 0; i < role.quantity; i++){
                arr.add(role.idRole);
            }
        }
        for(Player p: room.players){
            int idRole = arr.get(Utils.nextInt(0, room.players.size() - 1));
            arr.remove(idRole);
            RoleService.gI().setPropertyRole(p, idRole);
            MessageService.gI().sendMessagePrivate(p, 
                    new Message(Constaint.MESSAGE_PICK_ROLE,idRole));
        }
        MessageService.gI().sendMessageInRoom(room,
                new Message(Constaint.MESSAGE_CHANGE_TIME, Constaint.TIME_DAY));        
    }
    
    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void stop() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getServiceName() {
        return nameService;
    }
}
