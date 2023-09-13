package Main.utility;

import java.util.ArrayList;

public class Utils {
    public static String[][] convertArrayList_ArrayList_StringTo2DArray(ArrayList<ArrayList<String>> in) {
        String[][] ret = new String[in.size()][];

        int i = 0;
        for (ArrayList<String> arr : in) {
            ret[i] = arr.toArray(new String[0]);
            i++;
        }

        return ret;
    }

    public static String toDataBaseAttributeName(String s, String type){
        String ret = null;
        switch (s){
            case "Primärschlüssel" -> ret = type + "_key";
        }
        return ret;
    }

    public static String toDataBaseAttributeName(String s){
        String ret = null;
        switch (s){
            case "Inventar Nummer" -> ret = "iv_number";
            case "Firma" -> ret = "company";
            case "Kaufdatum" -> ret = "purchase_date";
            case "KaufPreis" -> ret = "purchase_price";
            case "Arbeitsspeicher" -> ret = "memory_ram_size_gb";
            case "Festplattenspeicher" -> ret = "memory_rom_size_gb";
            case "cpu" -> ret = "cpu";
            case "Betriebssystem" -> ret = "os";
            case "IP-Addresse" -> ret = "ip";
            case "Letztes Update" -> ret = "last_update";
            case "Nutzer" -> ret = "inventory_user_key";
            case "Serien Nummer" -> ret = "s_number";
            case "Status" -> ret = "current_status";
            case "DGUV" -> ret = "dguv";
            case "Notiz" -> ret = "note";
            case "Bemerkung" -> ret = "note";
            case "Kategorie" -> ret = "category";
            case "Equipment Nummer" -> ret = "eq_number";
            case "Hersteller" -> ret = "manufacturer";
            case "Modell" -> ret = "modell";
            case "Auflösung" -> ret = "resolution";
            case "Raum Nummer" -> ret = "room_nb";
            case "Etage" -> ret = "floor";
            case "Desk Sharing" -> ret = "desk_share";
            case "Nutzer Email" -> ret = "mail";
            case "HDMI" -> ret = "hdmi";
            case "DisplayPort" -> ret = "dp";
            case "VGA" -> ret = "vga";
            case "DVI" -> ret = "dvi";
            case "Garantie" -> ret = "warranty";
            case "PC-IV-Nummer" -> ret = "pc_iv_number";
            case "HD-IV-Nummer" -> ret = "hd_iv_number";
            case "TE-IV-Nummer" -> ret = "te_iv_number";
            case "DS-IV-Nummer" -> ret = "ds_iv_number";
            case "SC-IV-Nummer" -> ret = "sc_iv_number";
            case "MO1-IV-Nummer" -> ret = "mo_iv_number_1";
            case "MO2-IV-Nummer" -> ret = "mo_iv_number_2";
            case "Maus" -> ret = "has_mouse";
            case "Tastatur" -> ret = "has_keyboard";
        }
        return ret;
    }

    public static String filterBoxTextToAccordingDataTable(String s){
        String ret = "";
        switch (s){
            case "PCs" -> ret = "pc";
            case "Drucker" -> ret = "printer";
            case "Scanner" -> ret = "scanner";
            case "Desks" -> ret = "desk";
            case "Telefone" -> ret = "telephone";
            case "Monitore" -> ret = "monitor";
            case "Headsets" -> ret = "headset";
            case "Dockingstationen" -> ret = "dockingstation";
        }
        return ret;
    }

    public static String getShortCutFromTable(String table){
        String ret = "";
        switch (table){
            case "pc" -> ret = "pc";
            case "monitor" -> ret = "mo";
            case "headset" -> ret = "hd";
            case "dockingstation" -> ret = "ds";
            case "telephone" -> ret = "te";
            case "scanner" -> ret = "sc";
            case "printer" -> ret = "pr";
            case "desk" -> ret = "dk";
        }
        return ret;
    }
}