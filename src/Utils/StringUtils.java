package Utils;

import config.Constaint;
import static config.Constaint.ROLE_BACSI;
import static config.Constaint.ROLE_BANSOI;
import static config.Constaint.ROLE_DANLANG;
import static config.Constaint.ROLE_SOI;
import static config.Constaint.ROLE_SOITIENTRI;
import static config.Constaint.ROLE_THANGNGO;
import static config.Constaint.ROLE_THAYBOI;
import static config.Constaint.ROLE_THAYDONG;
import static config.Constaint.ROLE_TIENTRI;
import static config.Constaint.ROLE_XATHU;
import java.util.regex.Pattern;

public class StringUtils {
    public static boolean isValidUsername(String username){
        String regex = "^[a-z0-9]{1,12}$";
        Pattern pattern = Pattern.compile(regex);
        
        if(username == null || username.equals("")){
            return false;
        }
        return pattern.matcher(username).matches();
    }
    
    public static String getRoleNameById(byte id){
        String res;
        switch(id){
            case ROLE_DANLANG:
                res = "Dân làng";
                break;
            case ROLE_THAYBOI:
                res = "Thầy bói";
                break;
            case ROLE_BACSI:
                res = "Bác sĩ";
                break;
            case ROLE_BANSOI:
                res = "Bản sơi";
                break;
            case ROLE_THANGNGO:
                res = "Thằng ngố";
                break;
            case ROLE_XATHU:
                res = "Xạ thủ";
                break;
            case ROLE_THAYDONG:
                res = "Thầy đồng";
                break;
            case ROLE_TIENTRI:
                res = "Tiên tri";
                break;
            case ROLE_SOI:
                res = "Sói";
                break;
            case ROLE_SOITIENTRI:
                res = "Sói tiên tri";
                break;
            default:
                res = "Không xác định";
                break;
        }
        return res;
    }
    /**
     * Hàm lấy ra mô tả chức năng của từng role
     * @param role Role cần lấy
     * @return Mô tả của chức năng đó
     */
    public static String getDescFuncRole(byte role){
        switch(role){
            case Constaint.ROLE_THAYBOI:
                return "chọn 1 người để xem phe của họ";
            case Constaint.ROLE_BACSI:
                return "chọn 1 người để bảo vệ họ khỏi sói cắn";
            case Constaint.ROLE_THAYDONG:
                return "chọn 1 người để hồi sinh họ";
            case Constaint.ROLE_TIENTRI:
                return "chọn 1 người để xem vai trò của họ";
            case Constaint.ROLE_SOI:
                return "chọn 1 người bỏ phiếu cắn họ";
            case Constaint.ROLE_SOITIENTRI:
                return "chọn 1 người để xem vai trò của họ";
            default:
                return "";
        }
    }
    /**
     * Hàm lấy ra chuỗi message trong thanh status tương ứng với mỗi Role trong ban đêm
     * @param stage Stage hiện tại
     * @param role Role cần lấy chuỗi tương ứng với Stage
     * @return Kết quả cần tìm
     */
    public static String getMessageStageByRole(byte stage, byte role){
        switch(stage){
            case Constaint.STAGE_SLEEPING:{
                switch(role){
                    case Constaint.ROLE_THAYBOI:
                    case Constaint.ROLE_BACSI:
                    case Constaint.ROLE_THAYDONG:
                    case Constaint.ROLE_TIENTRI:
                    case Constaint.ROLE_SOI:
                    case Constaint.ROLE_SOITIENTRI:
                        return "Thực hiện chức năng";
                }
                return "Thời gian ngủ đêm";
            }
            case Constaint.STAGE_DISCUSSING:{
                return "Thời gian thảo luận";
            }
            case Constaint.STAGE_VOTING:{
                return "Thời gian bỏ phiếu";
            }
        }
        return "";
    }
}
