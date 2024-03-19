package Model;

import java.io.Serializable;

public class PlayerEffect implements Serializable{
    public byte idRole; 
    //Role
    public boolean isDanLang = false;
    public boolean isThayBoi = false;
    public boolean isBacSi = false;
    public boolean isBanSoi = false;
    public boolean isXaThu = false;
    public boolean isThayDong = false; 
    public boolean isTienTri = false;
    public boolean isSoi = false;
    public boolean isSoiTienTri = false;
    public boolean isChuaHe = false;
    
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
}
