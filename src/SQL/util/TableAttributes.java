package SQL.util;

public enum TableAttributes {
    iv_number, company;

    public static String[] toStringArray(){
        String[] attrArray = new String[TableAttributes.values().length];
        TableAttributes[] unNormedArr = TableAttributes.values();
        for (int i = 0; i < attrArray.length; i++) {
            attrArray[i] = unNormedArr[i].toString();
        }
        return attrArray;
    }

    public static TableAttributes getFromGermanString(String s){
        TableAttributes attr = null;
        switch (s){
            case "Inventar Nummer" -> attr = iv_number;}
        return attr;
    }

    public String toGermanString(){
        String ret = null;
        switch (this){
            case iv_number -> ret = "Inventar Nummer";
            case company -> ret = "Firma";
        }
        return ret;
    }
}