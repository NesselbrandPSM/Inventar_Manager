package Main.utility.UtilPrintables;

public enum IVObjectType {
    pc, monitor, headset, dockingstation, telephone, scanner, desk;

    public String[] getAttributes(){
        String[] ret = null;
        switch (this){
            case pc -> ret = new String[]{};
            case monitor -> ret = new String[]{};
            case headset -> ret = new String[]{};
            case dockingstation -> ret = new String[]{};
            case telephone -> ret = new String[]{};
            case scanner -> ret = new String[]{};
            case desk -> ret = new String[]{};
        }
        return ret;
    }
}
