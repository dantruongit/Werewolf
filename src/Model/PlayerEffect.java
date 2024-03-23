package Model;

import java.io.Serializable;

public class PlayerEffect implements Serializable{
    transient public byte idRole; 
    //Role
    transient public boolean isDanLang = false;
    transient public boolean isThayBoi = false;
    transient public boolean isBacSi = false;
    transient public boolean isBanSoi = false;
    transient public boolean isXaThu = false;
    transient public boolean isThayDong = false; 
    transient public boolean isTienTri = false;
    transient public boolean isSoi = false;
    transient public boolean isSoiTienTri = false;
    transient public boolean isChuaHe = false;
    
    public boolean isWolf(){
        return isSoi;
    }
    
    public boolean isUnknown(){
        return isSoiTienTri || isXaThu || isThayDong || isChuaHe;
    }
    
    public boolean isVillage(){
        return !isWolf() && !isUnknown();
    }
    
    //effect
    public boolean isShowRole = false;
    public boolean duocBaoVe = false;
    public boolean isDie = false;
    public int bullet = 0;
    public boolean hasRevival = false;
}
