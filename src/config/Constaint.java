package config;

public class Constaint {
    public static final String pathRoot = "C://Users/cr4zyb0t/Desktop/btl/WerewolfGit/Werewolf/src";
    
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
    
    //Time
    public static class Time{
        public static int TIME_SLEEPING = 100000; // 25
        public static int TIME_DISCUSSING = 11000; // 30
        public static int TIME_VOTING = 12000; //20
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
    //DAY
    public static final byte MESSAGE_PICK_ROLE = -118;
    public static final byte MESSAGE_START_DISCUSSING = 1;
    public static final byte MESSAGE_END_DISCUSSING = 1;
    public static final byte MESSAGE_START_VOTING   = 1;
    public static final byte MESSAGE_UPDATE_VOTING = -106;
    public static final byte MESSAGE_PLAYER_VOTES = -115;
    public static final byte MESSAGE_PLAYER_CANCEL_VOTES = -114;
    public static final byte MESSAGE_END_VOTING = 1;
    public static final byte MESSAGE_VOTING_RESULT = 1;
    public static final byte MESSAGE_VOTING_JOKES_MAN = 1;
    public static final byte MESSAGE_XATHU_SHOOT = -113;
    public static final byte MESSAGE_UPDATE_PLAYER = -93;
    //NIGHT
    //Message wake-up
    //Gọi các character dậy
    public static class WakeUp{
        public static final byte ROLE_THAYBOI = -100;
        public static final byte ROLE_BACSI = -99;
        public static final byte ROLE_THAYDONG = -98;
        public static final byte ROLE_TIENTRI = -97;
        public static final byte ROLE_SOITIENTRI = -96;
        public static final byte ROLE_SOI = -95;
    }
    public final static byte CAN_CHAT_IN_HELL = -94;
    
    public static final byte MESSAGE_WOLF_VOTES = -112; //SÓI VOTE
    public static final byte MESSAGE_VOTING_RESULT_WOLF = 1;
    public static final byte MESSAGE_BANSOI_ACTIVED = 1;
    
    public static final byte MESSAGE_TIENTRI_SEE = -111;
    public static final byte MESSAGE_THAYBOI_SEE = -110;
    public static final byte MESSAGE_BACSI_ACT = -109;
    public static final byte MESSAGE_CHAT_FROM_HELL = -108;
    public static final byte MESSAGE_CHAT_FROM_WOLF = -104;
    public static final byte MESSAGE_SOITIENTRI_SEE = -107;
}
