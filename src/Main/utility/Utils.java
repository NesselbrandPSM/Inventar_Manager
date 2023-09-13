package Main.utility;

import java.sql.ResultSet;
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
        }
        return ret;
    }
}