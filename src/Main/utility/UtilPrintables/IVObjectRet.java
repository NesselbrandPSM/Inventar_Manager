package Main.utility.UtilPrintables;

public class IVObjectRet {
    public String[] values;
    public String[] objectTypes;

    public IVObjectRet(String[] values, String[] objectTypes) {
        this.values = values;
        this.objectTypes = objectTypes;
    }

    public static String[] getAttributes(String table) {
        String[] ret = null;
        switch (table) {
            case "monitor":
            case "headset":
            case "dockingstation":
            case "scanner":
                ret = new String[]{"iv_number", "modell", "manufacturer", "s_number", "c_status", "c_note", "new_c_status", "new_c_note"};
                break;
            case "pc":
                ret = new String[]{"iv_number", "modell", "manufacturer", "s_number", "c_status", "c_note", "esim_number", "esim_pin", "new_c_status", "new_c_note"};
                break;
            case "telephone":
                ret = new String[]{"iv_number", "modell", "manufacturer", "s_number", "call_number", "tariff", "sim_pin", "display_pin", "new_c_status", "new_c_note"};
                break;
            case "miscellaneous":
                ret = new String[]{"iv_number", "typ", "name", "c_status", "c_note", "new_c_status", "new_c_note"};
                break;
        }
        return ret;
    }
}
