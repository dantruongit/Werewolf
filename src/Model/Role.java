package Model;

import java.io.Serializable;

public class Role implements Serializable{
    public int idRole;
    public String nameRole;
    public String descriptionRole;
    public int teamRole;
    public int requiredMin; // Số lượng tối thiểu cần có trong 1 game
    public int requiredMax; // Số lượng tối đa có thể có trong 1 game
    public int quantity = 0;
    public Role(){}
    public Role(int idRole, String nameRole, String descriptionRole,int teamRole, int requiredMin, int requiredMax) {
        this.idRole = idRole;
        this.nameRole = nameRole;
        this.descriptionRole = descriptionRole;
        this.requiredMin = requiredMin;
        this.requiredMax = requiredMax;
    }
    
//    public static Role getConfigRole(Role r){
//        Role role = new Role();
//        role.idRole = r.idRole;
//        role.quantity = r.requiredMin;
//        return role;
//    }
}
