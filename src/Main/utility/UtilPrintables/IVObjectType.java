package Main.utility.UtilPrintables;

public enum IVObjectType {
    pc, monitor, headset, dockingstation, telephone, scanner, miscellaneous;

    public String[] getAttributes() {
        String[] ret = null;
        switch (this) {
            case monitor:
            case headset:
            case dockingstation:
            case scanner:
                ret = new String[]{"iv_number", "modell", "manufacturer", "s_number", "c_status", "c_note"};
                break;
            case pc:
                ret = new String[]{"iv_number", "modell", "manufacturer", "s_number", "c_status", "c_note", "esim_number", "esim_pin"};
                break;
            case telephone:
                ret = new String[]{"iv_number", "modell", "manufacturer", "s_number", "call_number", "tariff", "sim_pin", "display_pin"};
                break;
            case miscellaneous:
                ret = new String[]{"iv_number", "typ", "name", "c_status", "c_note"};
                break;
        }
        return ret;
    }

    public static String[] getAttributes(String table) {
        String[] ret = null;
        switch (table) {
            case "monitor":
            case "headset":
            case "dockingstation":
            case "scanner":
                ret = new String[]{"iv_number", "modell", "manufacturer", "s_number", "c_status", "c_note"};
                break;
            case "pc":
                ret = new String[]{"iv_number", "modell", "manufacturer", "s_number", "c_status", "c_note", "esim_number", "esim_pin"};
                break;
            case "telephone":
                ret = new String[]{"iv_number", "modell", "manufacturer", "s_number", "call_number", "tariff", "sim_pin", "display_pin"};
                break;
            case "miscellaneous":
                ret = new String[]{"iv_number", "typ", "name", "c_status", "c_note"};
                break;
        }
        return ret;
    }
}
