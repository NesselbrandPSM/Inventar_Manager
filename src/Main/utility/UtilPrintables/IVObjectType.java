package Main.utility.UtilPrintables;

public enum IVObjectType {
    pc, monitor, headset, dockingstation, telephone, scanner, desk;

    public String[] getAttributes(){
        String[] ret = null;
        switch (this){
            case monitor, headset, dockingstation, scanner, desk -> ret = new String[]{"iv_number", "modell", "manufacturer", "s_number", "c_status", "c_note"};
            case pc -> ret = new String[]{"iv_number", "modell", "manufacturer", "s_number", "c_status", "c_note", "esim_number", "esim_pin"};
            case telephone -> ret = new String[]{"iv_number", "modell", "manufacturer", "s_number", "call_number", "tariff", "sim_pin", "display_pin"};
        }
        return ret;
    }
}
