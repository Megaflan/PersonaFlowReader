package dataAccess;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class Library {

    private static final String RESET_COLOR = "\u001B[0m";
    private static final String BLUE_COLOR = "\u001B[34m";
    private static final String PINK_COLOR = "\u001B[35m";
    private static final String YELLOW_COLOR = "\u001B[33m";
    private static final String RED_COLOR = "\u001B[31m";

    public static final String ADDR_KEYWORD = "addr";
    public static final String SECTION_KEYWORD = "section";
    public static final String TALK_AREA_KEYWORD = ".talk";
    public static final String CODE_AREA_KEYWORD = ".code";
    public static final String TEXT_AREA_KEYWORD = ".text";
    public static final String SPACE_TAB_REGEX = "[ \t]";
    public static final String NOT_FORMATTED_ERR_TXT = "File is not formatted correctly";
    public static final String BIG_LINE_BREAK = "\n\n";
    public static final String LABEL_TXT = "LABEL";
    public static final String LABEL_SEPARATOR = "_";
    public static final String UNKNOWN_INSTR_TEXT = "unknown";
    public static final byte CMD_START = (byte)0xFF;
    public static final String HEX_PREFIX = "0x";

    public static final long ADDRESS_WITH_FLOW_SCRIPT_POINTER = 0x64;
    public static final long ADDRESS_WITH_TEXT_TABLE_POINTER = 0x34;

    public static final int ADDRESS_OF_CHARACTER_DATA = 0x1F4;
    public static final byte CHARACTER_DATA_SIZE = 0x24;
    public static final byte CHARACTER_DATA_EVENT_ADDRESS_OFFSET = 0x4;

    public static final String COMMENT_SYMBOL = "//";
    public static final String COMMENT_INDICATOR = "\1"; // used to indicate a line was fully commented

    public enum FlowInstruction {
        NOTHING,
        ret,
        jump,
        UNKNOWN_COMMAND_24,
        UNKNOWN_COMMAND_25,
        jump_if,
        UNKNOWN_COMMAND_27,
        battle,
        ld_world_map,
        ld_file,
        ld_3d_map,
        play_MV,
        UNKNOWN_COMMAND_30,
        UNKNOWN_COMMAND_31,
        UNKNOWN_COMMAND_3C,
        UNKNOWN_COMMAND_3D,
        UNKNOWN_COMMAND_3E,
        UNKNOWN_COMMAND_3F,
        UNKNOWN_COMMAND_44,
        UNKNOWN_COMMAND_45,
        UNKNOWN_COMMAND_47,
        open_save_menu,
        UNKNOWN_COMMAND_4C,
        wait,
        UNKNOWN_COMMAND_4F,
        player_option,
        ld_text,
        UNKNOWN_COMMAND_56,
        UNKNOWN_COMMAND_58,
        open_dialog,
        close_dialog,
        pose,
        fx,
        clr_char,
        ld_portrait,
        close_portrait,
        emote,
        screen_fx,
        UNKNOWN_COMMAND_6D,
        plan_char_mov,
        UNKNOWN_COMMAND_74,
        fade_char,
        follow_char,
        UNKNOWN_COMMAND_79,
        clr_emote,
        do_planned_moves,
        tp_char,
        play_song,
        play_sfx
    }

    public enum TextInstruction {
        SHOW_OPTIONS, END, AWAITING_INPUT, LINE_BREAK, CONTINUE, WAIT, PLAYER_NAME, SET_COLOR, PRINT_ICON, CHARACTER_NAME
    }

    public static final long FLOW_OFFSET = 0x64;
    public static final short CONTINUE_VAL = (short)0xff04;

    public static final HashMap<Byte, FlowInstruction> FLOW_INSTRUCTIONS;
    public static final HashMap<Short, TextInstruction> TEXT_INSTRUCTIONS;
    public static final HashMap<Short, String> TEXT_CODES;
    public static final HashMap<Short, Character> TEXT_COLORS;
    public static final HashMap<Short, String> TEXT_COLORS_ANSI;

    public static final HashMap<String, Byte> FLOW_INSTRUCTIONS_REVERSE;
    public static final HashMap<String, Short> TEXT_INSTRUCTIONS_REVERSE;
    public static final HashMap<String, Short> TEXT_CODES_REVERSE;
    public static final HashMap<Character, Short> TEXT_COLORS_REVERSE;

    public static final LinkedList<String> PARAMETERIZED_TEXT_INSTRUCTIONS;

    // hashmap with the number of parameters (in extra ints) per instruction
    public static final HashMap<FlowInstruction, Byte> PARAM_NUM;


    //public static final HashMap<Short, Character> CHAR_TABLE;
    static {
        FLOW_INSTRUCTIONS = new HashMap<>();
        FLOW_INSTRUCTIONS.put((byte) 0x00, FlowInstruction.NOTHING);
        FLOW_INSTRUCTIONS.put((byte) 0x21, FlowInstruction.ret);
        FLOW_INSTRUCTIONS.put((byte) 0x22, FlowInstruction.jump);
        FLOW_INSTRUCTIONS.put((byte) 0x24, FlowInstruction.UNKNOWN_COMMAND_24);
        FLOW_INSTRUCTIONS.put((byte) 0x25, FlowInstruction.UNKNOWN_COMMAND_25);
        FLOW_INSTRUCTIONS.put((byte) 0x26, FlowInstruction.jump_if);
        FLOW_INSTRUCTIONS.put((byte) 0x27, FlowInstruction.UNKNOWN_COMMAND_27);
        FLOW_INSTRUCTIONS.put((byte) 0x28, FlowInstruction.battle);
        FLOW_INSTRUCTIONS.put((byte) 0x29, FlowInstruction.ld_world_map);
        FLOW_INSTRUCTIONS.put((byte) 0x2B, FlowInstruction.ld_file);
        FLOW_INSTRUCTIONS.put((byte) 0x2C, FlowInstruction.ld_3d_map);
        FLOW_INSTRUCTIONS.put((byte) 0x2D, FlowInstruction.play_MV);
        FLOW_INSTRUCTIONS.put((byte) 0x30, FlowInstruction.UNKNOWN_COMMAND_30);
        FLOW_INSTRUCTIONS.put((byte) 0x31, FlowInstruction.UNKNOWN_COMMAND_31);
        FLOW_INSTRUCTIONS.put((byte) 0x3C, FlowInstruction.UNKNOWN_COMMAND_3C);
        FLOW_INSTRUCTIONS.put((byte) 0x3D, FlowInstruction.UNKNOWN_COMMAND_3D);
        FLOW_INSTRUCTIONS.put((byte) 0x3E, FlowInstruction.UNKNOWN_COMMAND_3E);
        FLOW_INSTRUCTIONS.put((byte) 0x3F, FlowInstruction.UNKNOWN_COMMAND_3F);
        FLOW_INSTRUCTIONS.put((byte) 0x44, FlowInstruction.UNKNOWN_COMMAND_44);
        FLOW_INSTRUCTIONS.put((byte) 0x45, FlowInstruction.UNKNOWN_COMMAND_45);
        FLOW_INSTRUCTIONS.put((byte) 0x47, FlowInstruction.UNKNOWN_COMMAND_47);
        FLOW_INSTRUCTIONS.put((byte) 0x4B, FlowInstruction.open_save_menu);
        FLOW_INSTRUCTIONS.put((byte) 0x4C, FlowInstruction.UNKNOWN_COMMAND_4C);
        FLOW_INSTRUCTIONS.put((byte) 0x4d, FlowInstruction.wait);
        FLOW_INSTRUCTIONS.put((byte) 0x4f, FlowInstruction.UNKNOWN_COMMAND_4F);
        FLOW_INSTRUCTIONS.put((byte) 0x54, FlowInstruction.player_option);
        FLOW_INSTRUCTIONS.put((byte) 0x55, FlowInstruction.ld_text);
        FLOW_INSTRUCTIONS.put((byte) 0x56, FlowInstruction.UNKNOWN_COMMAND_56);
        FLOW_INSTRUCTIONS.put((byte) 0x58, FlowInstruction.UNKNOWN_COMMAND_58);
        FLOW_INSTRUCTIONS.put((byte) 0x60, FlowInstruction.open_dialog);
        FLOW_INSTRUCTIONS.put((byte) 0x61, FlowInstruction.close_dialog);
        FLOW_INSTRUCTIONS.put((byte) 0x64, FlowInstruction.pose);
        FLOW_INSTRUCTIONS.put((byte) 0x65, FlowInstruction.fx);
        FLOW_INSTRUCTIONS.put((byte) 0x66, FlowInstruction.clr_char);
        FLOW_INSTRUCTIONS.put((byte) 0x67, FlowInstruction.ld_portrait);
        FLOW_INSTRUCTIONS.put((byte) 0x68, FlowInstruction.close_portrait);
        FLOW_INSTRUCTIONS.put((byte) 0x69, FlowInstruction.emote);
        FLOW_INSTRUCTIONS.put((byte) 0x6C, FlowInstruction.screen_fx);
        FLOW_INSTRUCTIONS.put((byte) 0x6D, FlowInstruction.UNKNOWN_COMMAND_6D);
        FLOW_INSTRUCTIONS.put((byte) 0x6E, FlowInstruction.plan_char_mov);
        FLOW_INSTRUCTIONS.put((byte) 0x74, FlowInstruction.UNKNOWN_COMMAND_74);
        FLOW_INSTRUCTIONS.put((byte) 0x76, FlowInstruction.fade_char);
        FLOW_INSTRUCTIONS.put((byte) 0x78, FlowInstruction.follow_char);
        FLOW_INSTRUCTIONS.put((byte) 0x79, FlowInstruction.UNKNOWN_COMMAND_79);
        FLOW_INSTRUCTIONS.put((byte) 0x7a, FlowInstruction.clr_emote);
        FLOW_INSTRUCTIONS.put((byte) 0x7B, FlowInstruction.do_planned_moves);
        FLOW_INSTRUCTIONS.put((byte) 0x7c, FlowInstruction.tp_char);
        FLOW_INSTRUCTIONS.put((byte) 0x80, FlowInstruction.play_song);
        FLOW_INSTRUCTIONS.put((byte) 0x81, FlowInstruction.play_sfx);

        PARAM_NUM = new HashMap<>();
        PARAM_NUM.put(FlowInstruction.NOTHING, (byte)0);
        PARAM_NUM.put(FlowInstruction.ret, (byte)0);
        PARAM_NUM.put(FlowInstruction.jump, (byte)1);
        PARAM_NUM.put(FlowInstruction.UNKNOWN_COMMAND_24, (byte)0);
        PARAM_NUM.put(FlowInstruction.UNKNOWN_COMMAND_25, (byte)0);
        PARAM_NUM.put(FlowInstruction.jump_if, (byte)1);
        PARAM_NUM.put(FlowInstruction.UNKNOWN_COMMAND_27, (byte)0);
        PARAM_NUM.put(FlowInstruction.battle, (byte)0);
        PARAM_NUM.put(FlowInstruction.ld_world_map, (byte)1);
        PARAM_NUM.put(FlowInstruction.ld_file, (byte)1);
        PARAM_NUM.put(FlowInstruction.ld_3d_map, (byte)1);
        PARAM_NUM.put(FlowInstruction.play_MV, (byte)0);
        PARAM_NUM.put(FlowInstruction.UNKNOWN_COMMAND_30, (byte)1);
        PARAM_NUM.put(FlowInstruction.UNKNOWN_COMMAND_31, (byte)0);
        PARAM_NUM.put(FlowInstruction.UNKNOWN_COMMAND_3C, (byte)1);
        PARAM_NUM.put(FlowInstruction.UNKNOWN_COMMAND_3D, (byte)1);
        PARAM_NUM.put(FlowInstruction.UNKNOWN_COMMAND_3E, (byte)2);
        PARAM_NUM.put(FlowInstruction.UNKNOWN_COMMAND_3F, (byte)1);
        PARAM_NUM.put(FlowInstruction.UNKNOWN_COMMAND_44, (byte)1);
        PARAM_NUM.put(FlowInstruction.UNKNOWN_COMMAND_45, (byte)1);
        PARAM_NUM.put(FlowInstruction.UNKNOWN_COMMAND_47, (byte)1);
        PARAM_NUM.put(FlowInstruction.open_save_menu, (byte)0);
        PARAM_NUM.put(FlowInstruction.UNKNOWN_COMMAND_4C, (byte)0);
        PARAM_NUM.put(FlowInstruction.wait, (byte)0);
        PARAM_NUM.put(FlowInstruction.UNKNOWN_COMMAND_4F, (byte)0);
        PARAM_NUM.put(FlowInstruction.player_option, (byte)1);
        PARAM_NUM.put(FlowInstruction.ld_text, (byte)1);
        PARAM_NUM.put(FlowInstruction.UNKNOWN_COMMAND_56, (byte)0);
        PARAM_NUM.put(FlowInstruction.UNKNOWN_COMMAND_58, (byte)1);
        PARAM_NUM.put(FlowInstruction.open_dialog, (byte)0);
        PARAM_NUM.put(FlowInstruction.close_dialog, (byte)0);
        PARAM_NUM.put(FlowInstruction.pose, (byte)2);
        PARAM_NUM.put(FlowInstruction.fx, (byte)2);
        PARAM_NUM.put(FlowInstruction.clr_char, (byte)0);
        PARAM_NUM.put(FlowInstruction.ld_portrait, (byte)0);
        PARAM_NUM.put(FlowInstruction.close_portrait, (byte)0);
        PARAM_NUM.put(FlowInstruction.emote, (byte)0);
        PARAM_NUM.put(FlowInstruction.screen_fx, (byte)0);
        PARAM_NUM.put(FlowInstruction.UNKNOWN_COMMAND_6D, (byte)0);
        PARAM_NUM.put(FlowInstruction.plan_char_mov, (byte)1);
        PARAM_NUM.put(FlowInstruction.UNKNOWN_COMMAND_74, (byte)0);
        PARAM_NUM.put(FlowInstruction.fade_char, (byte)0);
        PARAM_NUM.put(FlowInstruction.follow_char, (byte)0);
        PARAM_NUM.put(FlowInstruction.UNKNOWN_COMMAND_79, (byte)1);
        PARAM_NUM.put(FlowInstruction.clr_emote, (byte)0);
        PARAM_NUM.put(FlowInstruction.do_planned_moves, (byte)0);
        PARAM_NUM.put(FlowInstruction.tp_char, (byte)1);
        PARAM_NUM.put(FlowInstruction.play_song, (byte)0);
        PARAM_NUM.put(FlowInstruction.play_sfx, (byte)0);

        TEXT_INSTRUCTIONS = new HashMap<>();
        TEXT_INSTRUCTIONS.put((short)0xff01, TextInstruction.END);
        TEXT_INSTRUCTIONS.put((short)0xff02, TextInstruction.AWAITING_INPUT);
        TEXT_INSTRUCTIONS.put((short)0xff03, TextInstruction.LINE_BREAK);
        TEXT_INSTRUCTIONS.put((short)0xff04, TextInstruction.CONTINUE);
        TEXT_INSTRUCTIONS.put((short)0xff05, TextInstruction.WAIT);
        TEXT_INSTRUCTIONS.put((short)0xff07, TextInstruction.PLAYER_NAME);
        TEXT_INSTRUCTIONS.put((short)0xff0E, TextInstruction.SHOW_OPTIONS);
        TEXT_INSTRUCTIONS.put((short)0xff18, TextInstruction.SET_COLOR);
        TEXT_INSTRUCTIONS.put((short)0xff19, TextInstruction.PRINT_ICON);
        TEXT_INSTRUCTIONS.put((short)0xff1B, TextInstruction.CHARACTER_NAME);

        TEXT_CODES = new HashMap<>();
        TEXT_CODES.put((short)0x0000, " ");
        TEXT_CODES.put((short)0x0001, "、"); // is at , level in-game (on "floor")
        TEXT_CODES.put((short)0x0002, "。"); // doesn't actually show up in-game
        TEXT_CODES.put((short)0x0003, ","); // is at , level in-game (on "floor")
        TEXT_CODES.put((short)0x0004, ".");
        TEXT_CODES.put((short)0x0005, "・"); // looks like a middle level , in-game
        TEXT_CODES.put((short)0x0006, ":");
        TEXT_CODES.put((short)0x0007, ";");
        TEXT_CODES.put((short)0x0008, "?");
        TEXT_CODES.put((short)0x0009, "!");
        TEXT_CODES.put((short)0x000A, "゛"); // looks like basic quotes, like 0028, in-game
        TEXT_CODES.put((short)0x000B, "゜"); // looks like a single quote in-game
        TEXT_CODES.put((short)0x000C, "´"); // looks the same as the above, but like 2 pixels higher
        TEXT_CODES.put((short)0x000D, "｀");
        TEXT_CODES.put((short)0x000E, "¨");
        TEXT_CODES.put((short)0x000F, "^");
        TEXT_CODES.put((short)0x0010, "￣");
        TEXT_CODES.put((short)0x0011, "＿");
        TEXT_CODES.put((short)0x0012, "ヽ");
        TEXT_CODES.put((short)0x0013, "ヾ");
        TEXT_CODES.put((short)0x0014, "ゝ");
        TEXT_CODES.put((short)0x0015, "ゞ");
        TEXT_CODES.put((short)0x0016, "〃");
        TEXT_CODES.put((short)0x0017, "仝");
        TEXT_CODES.put((short)0x0018, "々");
        TEXT_CODES.put((short)0x0019, "™");
        TEXT_CODES.put((short)0x001A, "〇");
        TEXT_CODES.put((short)0x001B, "ー");
        TEXT_CODES.put((short)0x001C, "―");
        TEXT_CODES.put((short)0x001D, "‐");
        TEXT_CODES.put((short)0x001E, "／");
        TEXT_CODES.put((short)0x001F, "＼");
        TEXT_CODES.put((short)0x0020, "～");
        TEXT_CODES.put((short)0x0021, "∥");
        TEXT_CODES.put((short)0x0022, "｜");
        TEXT_CODES.put((short)0x0023, "…"); // appears mid-level in-game
        TEXT_CODES.put((short)0x0024, "‥"); // appears mid-level in-game
        TEXT_CODES.put((short)0x0025, "‘"); // upside-down single quote in-game
        TEXT_CODES.put((short)0x0026, "’"); // up-right single quote in-game (actually ’)
        TEXT_CODES.put((short)0x0027, "“"); // upside-down double quote in-game (actually “)
        TEXT_CODES.put((short)0x0028, "”"); // up-right double quote in-game (actually ”)
        TEXT_CODES.put((short)0x0029, "(");
        TEXT_CODES.put((short)0x002A, ")");
        TEXT_CODES.put((short)0x002B, "〔");
        TEXT_CODES.put((short)0x002C, "〕");
        TEXT_CODES.put((short)0x002D, "[");
        TEXT_CODES.put((short)0x002E, "]");
        TEXT_CODES.put((short)0x002F, "{");
        TEXT_CODES.put((short)0x0030, "}");
        TEXT_CODES.put((short)0x0031, "〈");
        TEXT_CODES.put((short)0x0032, "〉");
        TEXT_CODES.put((short)0x0033, "《");
        TEXT_CODES.put((short)0x0034, "》");
        TEXT_CODES.put((short)0x0035, "「");
        TEXT_CODES.put((short)0x0036, "」");
        TEXT_CODES.put((short)0x0037, "『");
        TEXT_CODES.put((short)0x0038, "』");
        TEXT_CODES.put((short)0x0039, "【");
        TEXT_CODES.put((short)0x003A, "】");
        TEXT_CODES.put((short)0x003B, "+");
        TEXT_CODES.put((short)0x003C, "-");
        TEXT_CODES.put((short)0x003D, "±");
        TEXT_CODES.put((short)0x003E, "×");
        TEXT_CODES.put((short)0x003F, "　");
        TEXT_CODES.put((short)0x0040, "÷");
        TEXT_CODES.put((short)0x0041, "=");
        TEXT_CODES.put((short)0x0042, "≠");
        TEXT_CODES.put((short)0x0043, "<");
        TEXT_CODES.put((short)0x0044, ">");
        TEXT_CODES.put((short)0x0045, "≦");
        TEXT_CODES.put((short)0x0046, "≧");
        TEXT_CODES.put((short)0x0047, "∞");
        TEXT_CODES.put((short)0x0048, "∴");
        TEXT_CODES.put((short)0x0049, "♂");
        TEXT_CODES.put((short)0x004A, "♀");
        TEXT_CODES.put((short)0x004B, "°");
        TEXT_CODES.put((short)0x004C, "′");
        TEXT_CODES.put((short)0x004D, "″");
        TEXT_CODES.put((short)0x004E, "℃");
        TEXT_CODES.put((short)0x004F, "￥");
        TEXT_CODES.put((short)0x0050, "＄"); // doesn't actually show up in-game (seems to delete the next character)
        TEXT_CODES.put((short)0x0051, "￠");
        TEXT_CODES.put((short)0x0052, "￡");
        TEXT_CODES.put((short)0x0053, "%");
        TEXT_CODES.put((short)0x0054, "#");
        TEXT_CODES.put((short)0x0055, "&");
        TEXT_CODES.put((short)0x0056, "*"); // actually ＊
        TEXT_CODES.put((short)0x0057, "@");
        TEXT_CODES.put((short)0x0058, "§");
        TEXT_CODES.put((short)0x0059, "☆");
        TEXT_CODES.put((short)0x005A, "★");
        TEXT_CODES.put((short)0x005B, "○");
        TEXT_CODES.put((short)0x005C, "●");
        TEXT_CODES.put((short)0x005D, "◎");
        TEXT_CODES.put((short)0x005E, "◇");
        TEXT_CODES.put((short)0x005F, "◆");
        TEXT_CODES.put((short)0x0060, "□");
        TEXT_CODES.put((short)0x0061, "■"); // before ◆
        TEXT_CODES.put((short)0x0062, "△"); // before □
        TEXT_CODES.put((short)0x0063, "▲");
        TEXT_CODES.put((short)0x0064, "▽");
        TEXT_CODES.put((short)0x0065, "▼");
        TEXT_CODES.put((short)0x0066, "※");
        TEXT_CODES.put((short)0x0067, "〒");
        TEXT_CODES.put((short)0x0068, "→");
        TEXT_CODES.put((short)0x0069, "←");
        TEXT_CODES.put((short)0x006A, "↑");
        TEXT_CODES.put((short)0x006B, "↓");
        TEXT_CODES.put((short)0x006C, "〓");
        TEXT_CODES.put((short)0x0078, "∈");
        TEXT_CODES.put((short)0x0079, "∋");
        TEXT_CODES.put((short)0x007A, "⊆");
        TEXT_CODES.put((short)0x007B, "⊇");
        TEXT_CODES.put((short)0x007C, "⊂");
        TEXT_CODES.put((short)0x007D, "⊃");
        TEXT_CODES.put((short)0x007E, "∪");
        TEXT_CODES.put((short)0x007F, "∩");
        TEXT_CODES.put((short)0x0088, "∧");
        TEXT_CODES.put((short)0x0089, "∨");
        TEXT_CODES.put((short)0x008A, "￢");
        TEXT_CODES.put((short)0x008B, "⇒");
        TEXT_CODES.put((short)0x008C, "⇔");
        TEXT_CODES.put((short)0x008D, "∀");
        TEXT_CODES.put((short)0x008E, "∃");
        TEXT_CODES.put((short)0x009A, "∠");
        TEXT_CODES.put((short)0x009B, "⊥");
        TEXT_CODES.put((short)0x009C, "⌒");
        TEXT_CODES.put((short)0x009D, "∂");
        TEXT_CODES.put((short)0x009E, "∇");
        TEXT_CODES.put((short)0x009F, "≡");
        TEXT_CODES.put((short)0x00A0, "≒");
        TEXT_CODES.put((short)0x00A1, "≪");
        TEXT_CODES.put((short)0x00A2, "≫");
        TEXT_CODES.put((short)0x00A3, "√");
        TEXT_CODES.put((short)0x00A4, "∽");
        TEXT_CODES.put((short)0x00A5, "∝");
        TEXT_CODES.put((short)0x00A6, "∵");
        TEXT_CODES.put((short)0x00A7, "∫");
        TEXT_CODES.put((short)0x00A8, "∬");
        TEXT_CODES.put((short)0x00B0, "Å");
        TEXT_CODES.put((short)0x00B1, "‰");
        TEXT_CODES.put((short)0x00B2, "♯");
        TEXT_CODES.put((short)0x00B3, "♭");
        TEXT_CODES.put((short)0x00B4, "♪");
        TEXT_CODES.put((short)0x00B5, "†");
        TEXT_CODES.put((short)0x00B6, "‡");
        TEXT_CODES.put((short)0x00B7, "¶");
        TEXT_CODES.put((short)0x00BC, "◯");
        TEXT_CODES.put((short)0x00CF, "0");
        TEXT_CODES.put((short)0x00D0, "1");
        TEXT_CODES.put((short)0x00D1, "2");
        TEXT_CODES.put((short)0x00D2, "3");
        TEXT_CODES.put((short)0x00D3, "4");
        TEXT_CODES.put((short)0x00D4, "5");
        TEXT_CODES.put((short)0x00D5, "6");
        TEXT_CODES.put((short)0x00D6, "7");
        TEXT_CODES.put((short)0x00D7, "8");
        TEXT_CODES.put((short)0x00D8, "9");
        TEXT_CODES.put((short)0x00E0, "A");
        TEXT_CODES.put((short)0x00E1, "B");
        TEXT_CODES.put((short)0x00E2, "C");
        TEXT_CODES.put((short)0x00E3, "D");
        TEXT_CODES.put((short)0x00E4, "E");
        TEXT_CODES.put((short)0x00E5, "F");
        TEXT_CODES.put((short)0x00E6, "G");
        TEXT_CODES.put((short)0x00E7, "H");
        TEXT_CODES.put((short)0x00E8, "I");
        TEXT_CODES.put((short)0x00E9, "J");
        TEXT_CODES.put((short)0x00EA, "K");
        TEXT_CODES.put((short)0x00EB, "L");
        TEXT_CODES.put((short)0x00EC, "M");
        TEXT_CODES.put((short)0x00ED, "N");
        TEXT_CODES.put((short)0x00EE, "O");
        TEXT_CODES.put((short)0x00EF, "P");
        TEXT_CODES.put((short)0x00F0, "Q");
        TEXT_CODES.put((short)0x00F1, "R");
        TEXT_CODES.put((short)0x00F2, "S");
        TEXT_CODES.put((short)0x00F3, "T");
        TEXT_CODES.put((short)0x00F4, "U");
        TEXT_CODES.put((short)0x00F5, "V");
        TEXT_CODES.put((short)0x00F6, "W");
        TEXT_CODES.put((short)0x00F7, "X");
        TEXT_CODES.put((short)0x00F8, "Y");
        TEXT_CODES.put((short)0x00F9, "Z");
        TEXT_CODES.put((short)0x0101, "a");
        TEXT_CODES.put((short)0x0102, "b");
        TEXT_CODES.put((short)0x0103, "c");
        TEXT_CODES.put((short)0x0104, "d");
        TEXT_CODES.put((short)0x0105, "e");
        TEXT_CODES.put((short)0x0106, "f");
        TEXT_CODES.put((short)0x0107, "g");
        TEXT_CODES.put((short)0x0108, "h");
        TEXT_CODES.put((short)0x0109, "i");
        TEXT_CODES.put((short)0x010A, "j");
        TEXT_CODES.put((short)0x010B, "k");
        TEXT_CODES.put((short)0x010C, "l");
        TEXT_CODES.put((short)0x010D, "m");
        TEXT_CODES.put((short)0x010E, "n");
        TEXT_CODES.put((short)0x010F, "o");
        TEXT_CODES.put((short)0x0110, "p");
        TEXT_CODES.put((short)0x0111, "q");
        TEXT_CODES.put((short)0x0112, "r");
        TEXT_CODES.put((short)0x0113, "s");
        TEXT_CODES.put((short)0x0114, "t");
        TEXT_CODES.put((short)0x0115, "u");
        TEXT_CODES.put((short)0x0116, "v");
        TEXT_CODES.put((short)0x0117, "w");
        TEXT_CODES.put((short)0x0118, "x");
        TEXT_CODES.put((short)0x0119, "y");
        TEXT_CODES.put((short)0x011A, "z");
        TEXT_CODES.put((short)0x011F, "あ");
        // following short values display a standard ? in-game
        // values not in this map display as mid-level dots in-game


        // creating reverse maps
        FLOW_INSTRUCTIONS_REVERSE = new HashMap<>();
        for (Map.Entry<Byte, FlowInstruction> entry : FLOW_INSTRUCTIONS.entrySet()) {
            FLOW_INSTRUCTIONS_REVERSE.put(entry.getValue().name(), entry.getKey());
        }

        HashMap<String, Short> tempMapE = new HashMap<>();
        for (Map.Entry<Short, TextInstruction> entry : TEXT_INSTRUCTIONS.entrySet()) {
            tempMapE.put(entry.getValue().name(), entry.getKey());
        }
        TEXT_INSTRUCTIONS_REVERSE = tempMapE;

        TEXT_CODES_REVERSE = new HashMap<>();
        for (Map.Entry<Short, String> entry : TEXT_CODES.entrySet()) {
            TEXT_CODES_REVERSE.put(entry.getValue(), entry.getKey());
        }

        TEXT_COLORS = new HashMap<>();
        TEXT_COLORS.put((short) 0x0000, '_');
        TEXT_COLORS.put((short) 0x0001, 'W');
        TEXT_COLORS.put((short) 0x0002, 'B');
        TEXT_COLORS.put((short) 0x0003, 'P');
        TEXT_COLORS.put((short) 0x0004, 'G');
        TEXT_COLORS.put((short) 0x0005, 'Y');
        // 0006 also makes text blue, but it isn't used in P1 (at least in E0.BIN)
        TEXT_COLORS.put((short) 0x0007, 'R');

        TEXT_COLORS_REVERSE = new HashMap<>();
        for (Map.Entry<Short, Character> entry : TEXT_COLORS.entrySet()) {
            TEXT_COLORS_REVERSE.put(entry.getValue(), entry.getKey());
        }

        TEXT_COLORS_ANSI = new HashMap<>();
        TEXT_COLORS_ANSI.put((short) 0x0000, RESET_COLOR);
        TEXT_COLORS_ANSI.put((short) 0x0002, BLUE_COLOR);
        TEXT_COLORS_ANSI.put((short) 0x0003, PINK_COLOR);
        TEXT_COLORS_ANSI.put((short) 0x0005, YELLOW_COLOR);
        TEXT_COLORS_ANSI.put((short) 0x0007, RED_COLOR);

        PARAMETERIZED_TEXT_INSTRUCTIONS = new LinkedList<>();
        PARAMETERIZED_TEXT_INSTRUCTIONS.add(TextInstruction.WAIT.name());
        PARAMETERIZED_TEXT_INSTRUCTIONS.add(TextInstruction.SET_COLOR.name());
        PARAMETERIZED_TEXT_INSTRUCTIONS.add(TextInstruction.PRINT_ICON.name());

    }


    public enum EMOTES {
            exclamation, question, heart, awkward, zzz
    }

    public enum POSES {
        still, idle, walk, pain, fight, crouched, depressed, victory, dead
    }

    public enum EVENT_DIRS {
            NW, SE, SW, NE
    }

    public enum PORTRAIT_ORIENTATION {
        left, middle, right
    }

    public enum PORTRAIT_CHARS {
        MC, Maki, Mark, Nanjo, Yukino, Ayase, Brown, Elly, Reiji, Maki_sick, Maki_happy, Mai, Aki, Maki_masked, Maki_masked_SQQ, Setsuko,
        MC_alt1, Saeko, Saeko_Ice, Saeko_young, Nurse, Hanya, Ooishi, Yamaoka, Yosuke, Chisato, Chisato_corruptv1, Chisato_corruptv2, Chisato_corruptv3, MC_alt2, Tsutomu, Yuko,
        Yuko_smug, Toro, MC_alt3, Tadashi, Tamaki, Katsue_rich, Katsue_poor, Kandori, Kandori_mask, Takeda, MC_alt4, Nicholai, Tomomi, Tomomi_corrupt, MC_alt5, Kumi,
        Michiko, Yuriko, MC_alt6, MC_alt7, Night_Queen, Yin_Yang_clerk, Rosa_clerk, Weapon_clerk, Armor_clerk, Pharma_clerk, MC_alt8, Sweets_clerk, Turunkhamen, Club_coin_clerk, Diner_clerk, Doctor,
        Igor, Trish, Khamenturun, MC_alt9, Master, Club_yen_clurk, glitch
    }

    public static final String[] SCREEN_EFFECTS = {
            "stop current effect (only works for earthquake)",
            "fades out of black (quick)",
            "fades out of black (mid speed)",
            "fades out of black (slow)",
            "fades into black (quick)",
            "fades into black (mid speed)",
            "fades into black (slow)",
            "smaller screen shake (earthquake)",
            "medium screen shake (earthquake)",
            "bigger screen shake (earthquake)",
            "fades out of white (quick)",
            "fades out of white (mid speed)",
            "fades out of white (slow)",
            "fades into white (quick)",
            "fades into white (mid speed)",
            "fades into white (slow)",
            "screen flashes black (mid speed)",
            "screen flashes black (quick)"
    };

    public static final String[] BATTLES = {
            "first awakening",
            "Elly's awakening",
            "Maki's awakening",
            "Brown's awakening",
            "Ayase's awakening",
            "Takeda battle",
            "Reiji's awakening",
            "unknown battle",
            "Tesso battle",
            "Yog Sothoth Jr battle",
            "Harem Queen battle",
            "Mr. Bear battle",
            "Saurva battle",
            "Hariti battle",
            "Kandori battle",
            "Pandora fase 1",
            "Akuma monster battle",
            "Akuma monster battle variation?",
            "Hypnos 1 battle",
            "Hypnos 2 battle",
            "Hypnos 3 battle",
            "Hypnos 4 battle",
            "Nemesis 1 battle",
            "Nemesis 2 battle",
            "Nemesis 3 battle",
            "Nemesis 4 battle",
            "Nemesis 5 battle",
            "Nemesis 6 battle",
            "Thanatos 1 battle",
            "Thanatos 2 battle",
            "Snow Queen mask battle",
            "Queen Asura battle",
            "bad ending last battle",
            "Pandora fase 2"
    };

    public static final String[][] OPTIONS = {
            {"Yes", "No"}, // 0000
            {"Sure.", "No way."}, // 0001
            {"Yeah,", "No, I don't"}, // 0002
            {"Start game", "Check coins", "See explanations", "Stop playing"}, // 0003
            {"No", "Yes"}, // 0004
            {"Game rules", "Controls", "Winning hands", "Go back"}, // 0005
            {"Game rules", "Controls", "Tips", "Go back"}, // 0006
            {"Let them join", "Don't let them join"}, // 0007
            {"Help her", "Don't help her"}, // 0008
            {"Don't leave", "Leave"}, // 0009
            {"Don't open it", "Open it"}, // 000A
            {"Don't listen", "Listen"}, // 000B
            {"Create Persona", "Take on Persona", "Talk", "Leave"}, // 000C
            {"Stop hiding.", "Yes, it's safe here.", "That's true, but...", "I don't really know."}, // 000D
            {"For myself.", "Just 'cause.", "For everyone's sake.", "That's how it went."}, // 000E
            {"I don't really know.", "To find my reason."}, // 000F
            {"Press the red button", "Press the blue button."}, // 0010
            {"Heal us, please.", "Just dropping by."}, // 0011
            {"Fight Hariti", "Lower your weapons"}, // 0012
            {"Don't hide like that!", "Maybe you are..."}, // 0013
            {"Stay here", "Go to 8F", "Go to 4F", "Go to 1F"}, // 0014
            {"Manual Fusion", "Guided Fusion", "View cards", "Cancel"}, // 0015
            {"The Queen's is better.", "Maki's is better."}, // 0016
            {"Beginner tips", "Regular tips", "About Personas", "Advanced tips"}, // 0017
            {"Start game", "Check cards", "See explanations", "Cancel"}, // 0018
            {"Bet on Mark", "Bet on Brown"}, // 0019
            {"That's the plan.", "Not really."}, // 001A
            {"Yeah.", "That's"}, // 001B (HUH?)
            {"Yeah, I do.", "No, no one."}, // 001C
            {"A few.", "Not a one."}, // 001D
            {"I like the old way.", "I like the new way."}, // 001E
            {"Sure, put me down.", "Don't you dare."}, // 001F
            {"Buy", "Sell", "Equip", "Cancel"}, // 0020
            {"Trade for items", "Trade for incense", "Equip", "Cancel"}, // 0021
            {"Normal", "Beginner", "Expert"}, // 0022
            {"Yes, it was.", "On second thought..."}, // 0023 (LAST ONE)

    };

    public static String getSFXDescription(short val) {
        switch (val) {
            case 0x0:
            case 0x1:
                return "woosh";
            case 0x3:
                return "quick lightning";
            case 0x4:
                return "heal/reflect sound?";
            case 0x5:
                return "holy voice";
            case 0x8:
                return "something fell, a rock or smt";
            case 0x9:
                return "something falling intensely? Sounds kinda like lightning";
            case 0xA:
                return "little noises followed by weird woosh";
            case 0xB:
                return "water flowing, a lil bubbling";
            case 0xC:
            case 0xD:
                return "bird, followed by pecking";
            case 0xE:
                return "open door";
            case 0xF:
                return "unlock door";
            case 0x10:
                return "open gate";
            case 0x11:
                return "creaking";
            case 0x12:
                return "some other opening sound, gate or something? Resident Evil like";
            case 0x13:
                return "deep lightning, or something falling";
            case 0x14:
                return "same as before but more intense, metallic scrapping as well";
            case 0x15:
                return "quick opening of metal door";
            case 0x16:
                return "weird woosh";
            case 0x17:
                return "quick woosh";
            case 0x18:
                return "machine hum";
            case 0x19:
                return "heavy machine (moving?)";
            case 0x1A:
                return "quiet unlock";
            case 0x1B:
                return "ominous sound, like a debuff being cast (imagination)";
            case 0x1c:
                return "page turn?";
            case 0x1D:
                return "curtain pull?";
            case 0x1E:
                return "glass shatter";
            case 0x1F:
                return "ray gun";
            case 0x20:
                return "lightning_1";
            case 0x21:
                return "small crunch";
            case 0x22:
                return "ghostly sound, deep";
            case 0x23:
                return "mechanical door closing? or elevator stopping";
            case 0x24:
                return "BAM (closed window quickly)";
            case 0x25:
                return "light woosh, like page turning";
            case 0x26:
                return "open heavier door";
            case 0x27:
                return "heartbeat";
            case 0x28:
                return "punch";
            case 0x29:
                return "small ding";
            case 0x2A:
                return "window break";
            case 0x2B:
                return "minecraft cave noise";
            case 0x2C:
                return "Ice Queen music box";
            case 0x2D:
                return "teleporter sound";
            case 0x2E:
                return "healing like holy sound, revive vibes";
            case 0x2F:
                return "weird woosh, comes and goes";
            case 0x30:
                return "electronic woosh";
            case 0x31:
                return "deep sounding lightning?";
            case 0x32:
                return "big metal gate quick open";
            case 0x4D:
                return "lightning_2";

                default:
                    return "nothing";

        }
    }

}
