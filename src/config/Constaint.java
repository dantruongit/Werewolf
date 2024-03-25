package config;

public class Constaint {
    public static final String pathRoot = "./src";
    
     public static String getNameOfMessageCode(byte code) {
        switch (code) {
            case MESSAGE_JOIN_SERVER:
                return "MESSAGE_JOIN_SERVER";
            case MESSAGE_LOAD_ROOM:
                return "MESSAGE_LOAD_ROOM";
            case MESSAGE_JOIN_ROOM:
                return "MESSAGE_JOIN_ROOM";
            case MESSAGE_LEAVE_ROOM:
                return "MESSAGE_LEAVE_ROOM";
            case MESSAGE_RELOAD_PLAYER:
                return "MESSAGE_RELOAD_PLAYER";
            case MESSAGE_GET_CONFIG_ROLE:
                return "MESSAGE_GET_CONFIG_ROLE";
            case MESSAGE_CREATE_ROOM:
                return "MESSAGE_CREATE_ROOM";
            case MESSAGE_START_GAME:
                return "MESSAGE_START_GAME";
            case MESSAGE_GAME_STARTED:
                return "MESSAGE_GAME_STARTED";
            case MESSAGE_CHAT:
                return "MESSAGE_CHAT";
            case MESSAGE_ROOM_REMOVED:
                return "MESSAGE_ROOM_REMOVED";
            case MESSAGE_PLAYER_DIE:
                return "MESSAGE_PLAYER_DIE";
            case MESSAGE_CHAT_IN_GAME:
                return "MESSAGE_CHAT_IN_GAME";
            case MESSAGE_PICK_ROLE:
                return "MESSAGE_PICK_ROLE";
            case MESSAGE_UPDATE_VOTING:
                return "MESSAGE_UPDATE_VOTING";
            case MESSAGE_PLAYER_VOTES:
                return "MESSAGE_PLAYER_VOTES";
            case MESSAGE_PLAYER_CANCEL_VOTES:
                return "MESSAGE_PLAYER_CANCEL_VOTES";
            case MESSAGE_XATHU_SHOOT:
                return "MESSAGE_XATHU_SHOOT";
            case MESSAGE_XATHU_OUT_OF_BULLET:
                return "MESSAGE_XATHU_OUT_OF_BULLET";
            case MESSAGE_UPDATE_PLAYER:
                return "MESSAGE_UPDATE_PLAYER";
            case MESSAGE_LIST_OTHER_WOLFS:
                return "MESSAGE_LIST_OTHER_WOLFS";
            case MESSAGE_WOLF_VOTES:
                return "MESSAGE_WOLF_VOTES";
            case MESSAGE_WOLF_CANCEL_VOTES:
                return "MESSAGE_WOLF_CANCEL_VOTES";
            case MESSAGE_TIENTRI_SEE:
                return "MESSAGE_TIENTRI_SEE";
            case MESSAGE_THAYBOI_SEE:
                return "MESSAGE_THAYBOI_SEE";
            case MESSAGE_BACSI_BAOVE:
                return "MESSAGE_BACSI_BAOVE";
            case MESSAGE_BACSI_HUY_BAOVE:
                return "MESSAGE_BACSI_HUY_BAOVE";
            case MESSAGE_THAYDONG_HOISINH:
                return "MESSAGE_THAYDONG_HOISINH";
            case MESSAGE_SOITIENTRI_SEE:
                return "MESSAGE_SOITIENTRI_SEE";
            default:
                return "";
        }
    }

    public static final byte ADD_BOT = -11;
    
    public static final byte ROLE_UNKNOWN = -1; //Role ẩn khi chết
    public static final byte ROLE_DANLANG    = 0; // Phe thiện
    public static final byte ROLE_THAYBOI = 1; // Phe thiện
    public static final byte ROLE_BACSI = 2; // Phe thiện
    public static final byte ROLE_BANSOI = 3; // Phe thiện (nếu chưa bị cắn)
    public static final byte ROLE_THANGNGO = 4; // Phe không rõ
    public static final byte ROLE_XATHU = 5; // Phe không rõ
    public static final byte ROLE_THAYDONG = 6; // Phe không rõ
    public static final byte ROLE_TIENTRI = 7; // Phe thiện
    public static final byte ROLE_SOI = 8; // Phe ác
    public static final byte ROLE_SOITIENTRI = 9; // Phe không rõ
    
    
    
    public static final byte TEAM_WOLF = 0;
    public static final byte TEAM_VILLAGE = 1;
    public static final byte TEAM_THIRD = 2;
    public static final byte TEAM_HELL = 3;
    
    //Time
    public static class Time{
        public static int TIME_SLEEPING = 10000; // 25
        public static int TIME_DISCUSSING = 10000; // 30
        public static int TIME_VOTING = 10000; //20
    }
    //Message code
    // In dashboard
    public static final byte MESSAGE_JOIN_SERVER = -128;
    public static final byte MESSAGE_LOAD_ROOM = -127;
    public static final byte MESSAGE_JOIN_ROOM = -126;
    public static final byte MESSAGE_LEAVE_ROOM = -116;
    public static final byte MESSAGE_RELOAD_PLAYER = -101;
    public static final byte MESSAGE_GET_CONFIG_ROLE = -103;
    //In room
    public static final byte MESSAGE_CREATE_ROOM = -102;
    public static final byte MESSAGE_START_GAME = -124;
    public static final byte MESSAGE_GAME_STARTED = -119;
    public static final byte MESSAGE_CHAT = -123;
    
    public static final byte MESSAGE_ROOM_REMOVED = -120;
    
    public static final byte STAGE_DISCUSSING = 127;
    public static final byte STAGE_VOTING = 126;
    public static final byte STAGE_SLEEPING = 125;
    public static final byte STAGE_CHANGE = 124;
    
    //In game
    public static final byte MESSAGE_PLAYER_DIE = -91;
    public static final byte MESSAGE_PLAYER_REVIVAL = -83;
    public static final byte MESSAGE_CHAT_IN_GAME = -84;
    //DAY
    public static final byte MESSAGE_PICK_ROLE = -118;
    public static final byte MESSAGE_UPDATE_VOTING = -106;
    public static final byte MESSAGE_PLAYER_VOTES = -115;
    public static final byte MESSAGE_PLAYER_CANCEL_VOTES = -88;
    public static final byte MESSAGE_XATHU_SHOOT = -113;
    public static final byte MESSAGE_XATHU_OUT_OF_BULLET = -86;
    public static final byte MESSAGE_UPDATE_PLAYER = -93;
    //NIGHT
    public static final byte MESSAGE_LIST_OTHER_WOLFS = -90;
    //Message wake-up
    //Gọi các character dậy
    public static class WakeUp{
        public static final byte ROLE_THAYBOI = -100;
        public static final byte ROLE_BACSI = -99;
        public static final byte ROLE_THAYDONG = -98;
        public static final byte ROLE_TIENTRI = -97;
        public static final byte ROLE_SOITIENTRI = -96;
        public static final byte ROLE_SOI = -95;
        public static final byte ROLE_XATHU = -85;
    }
    
    public static final byte MESSAGE_WOLF_VOTES = -112; //SÓI VOTE
    public static final byte MESSAGE_WOLF_CANCEL_VOTES = -89; // SÓI hủy vote
    
    public static final byte MESSAGE_TIENTRI_SEE = -111;
    public static final byte MESSAGE_THAYBOI_SEE = -110;
    public static final byte MESSAGE_BACSI_BAOVE = -109;
    public static final byte MESSAGE_BACSI_HUY_BAOVE = -87;
    public static final byte MESSAGE_THAYDONG_HOISINH = -92;
    public static final byte MESSAGE_SOITIENTRI_SEE = -107;
    
    //End game
    public static final byte MESSAGE_WOLFS_WIN = -82;
    public static final byte MESSAGE_VILLAGERS_WIN = -81;
    public static final byte MESSAGE_JOKER_WIN = -80;
}
