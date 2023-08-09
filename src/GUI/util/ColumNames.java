package GUI.util;

public class ColumNames {
    public static final String[] columnNamesStandardView = new String[]{
            "Inventar Nummer", "Firma", "p_key"
    };

    public static final String[] allAttributesPC = new String[]{
            "Primärschlüssel", "Inventar Nummer", "Firma", "Hersteller", "Kaufdatum", "KaufPreis", "Arbeitsspeicher",
            "Festplattenspeicher", "cpu", "Betriebssystem", "IP-Addresse", "Letztes Update",
            "Nutzer", "Serien Nummer", "Status", "DGUV", "Notiz", "Kategorie"
    };


    public static final String[] allAttributesPR = new String[]{
            "Primärschlüssel", "Inventar Nummer", "Hersteller", "Firma", "Kaufdatum", "KaufPreis",
            "Equipment Nummer", "Serien Nummer", "Status", "DGUV"
    };

    public static final String[] allAttributesSC = new String[]{
            "Primärschlüssel", "Inventar Nummer", "Hersteller", "Firma", "Kaufdatum", "KaufPreis", "Status", "DGUV", "Serien Nummer"
    };

    public static final String[] allAttributesMO = new String[]{
            "Primärschlüssel", "Inventar Nummer", "Hersteller", "Firma", "Kaufdatum", "KaufPreis", "Status", "DGUV", "Serien Nummer"
    };
}