package Utils;

import Model.Stage;
import config.Constaint;

public class Message {
    /**
     * Hàm lấy ra mô tả chức năng của từng role
     * @param role Role cần lấy
     * @return Mô tả của chức năng đó
     */
    public static String getDescFuncRole(byte role){
        switch(role){
            case Constaint.ROLE_THAYBOI:
                return "Chọn 1 người để xem phe của họ";
            case Constaint.ROLE_BACSI:
                return "Chọn 1 người để bảo vệ họ khỏi sói cắn";
            case Constaint.ROLE_THAYDONG:
                return "Chọn 1 người để hồi sinh họ";
            case Constaint.ROLE_TIENTRI:
                return "Chọn 1 người để xem vai trò của họ";
            case Constaint.ROLE_SOI:
                return "Chọn 1 người bỏ phiếu cắn họ";
            case Constaint.ROLE_SOITIENTRI:
                return "Chọn 1 người để xem vai trò của họ";
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
