package GUI.util;

public class ColumNames {

    public static String[] getColumnNamesList(String iv_number) {
        String[] ret = null;
        switch (iv_number.substring(0, 2).toLowerCase()) {
            case "pc" -> ret = allAttributesPC;
            case "pr" -> ret = allAttributesPR;
            case "sc" -> ret = allAttributesSC;
            case "mo" -> ret = allAttributesMO;
            case "te" -> ret = allAttributesTE;
            case "hd" -> ret = allAttributesHD;
            case "ds" -> ret = allAttributesDS;
        }
        return ret;
    }

    public static final String[] columnNamesStandardView = new String[]{
            "Inventar Nummer", "Firma", "p_key"
    };

    public static final String[] allAttributesPC = new String[]{
            "Primärschlüssel", "Inventar Nummer", "Firma", "Hersteller", "Modell", "Kaufdatum", "KaufPreis", "Arbeitsspeicher",
            "Festplattenspeicher", "cpu", "Betriebssystem", "IP-Addresse", "Letztes Update",
            "Nutzer", "Serien Nummer", "Status", "DGUV", "Notiz", "Kategorie"
    };

    public static final String[] allAttributesPR = new String[]{
            "Primärschlüssel", "Inventar Nummer", "Hersteller", "Modell", "Firma", "Kaufdatum", "KaufPreis",
            "Equipment Nummer", "Serien Nummer", "Status", "DGUV", "IP-Addresse"
    };

    public static final String[] allAttributesSC = new String[]{
            "Primärschlüssel", "Inventar Nummer", "Hersteller", "Firma", "Modell", "Kaufdatum", "KaufPreis", "Status", "DGUV", "Serien Nummer", "IP-Addresse"
    };

    public static final String[] allAttributesMO = new String[]{
            "Primärschlüssel", "Inventar Nummer", "Hersteller", "Firma", "Modell", "Auflösung", "Kaufdatum", "KaufPreis", "Status", "DGUV", "Serien Nummer"
    };

    public static final String[] allAttributesTE = new String[]{
            "Primärschlüssel", "Inventar Nummer", "Hersteller", "Firma", "Modell", "Kaufdatum", "KaufPreis", "Status", "DGUV", "Serien Nummer", "IP-Addresse"
    };

    public static final String[] allAttributesHD = new String[]{
            "Primärschlüssel", "Inventar Nummer", "Hersteller", "Firma", "Modell", "Kaufdatum", "KaufPreis", "Status", "DGUV", "Serien Nummer"
    };

    public static final String[] allAttributesDS = new String[]{
            "Primärschlüssel", "Inventar Nummer", "Hersteller", "Firma", "Modell", "Kaufdatum", "KaufPreis", "Status", "DGUV", "Serien Nummer"
    };
}