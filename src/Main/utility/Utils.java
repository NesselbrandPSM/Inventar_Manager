package Main.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class Utils {
    public static String newConditionNote = "null";
    public static String newConditionStatus = "null";

    public static String[][] convertArrayList_ArrayList_StringTo2DArray(ArrayList<ArrayList<String>> in) {
        String[][] ret = new String[in.size()][];

        int i = 0;
        for (ArrayList<String> arr : in) {
            ret[i] = arr.toArray(new String[0]);
            i++;
        }

        return ret;
    }

    public static String toDataBaseAttributeName(String s, String type) {
        String ret = null;
        switch (s) {
            case "Primärschlüssel":
                ret = type + "_key";
                break;
        }
        return ret;
    }

    public static String toDataBaseAttributeName(String s) {
        String ret = null;
        switch (s) {
            case "Inventar Nummer":
                ret = "iv_number";
                break;
            case "Firma":
                ret = "company";
                break;
            case "Kaufdatum":
                ret = "purchase_date";
                break;
            case "KaufPreis":
                ret = "purchase_price";
                break;
            case "Arbeitsspeicher":
                ret = "memory_ram_size_gb";
                break;
            case "Festplattenspeicher":
                ret = "memory_rom_size_gb";
                break;
            case "cpu":
                ret = "cpu";
                break;
            case "Betriebssystem":
                ret = "os";
                break;
            case "IP-Addresse":
                ret = "ip";
                break;
            case "Letztes Update":
                ret = "last_update";
                break;
            case "Nutzer":
                ret = "inventory_user_key";
                break;
            case "Serien Nummer":
                ret = "s_number";
                break;
            case "Status":
                ret = "current_status";
                break;
            case "DGUV":
                ret = "dguv";
                break;
            case "Notiz":
                ret = "note";
                break;
            case "Bemerkung":
                ret = "note";
                break;
            case "Kategorie":
                ret = "category";
                break;
            case "Equipment Nummer":
                ret = "eq_number";
                break;
            case "Hersteller":
                ret = "manufacturer";
                break;
            case "Modell":
                ret = "modell";
                break;
            case "Auflösung":
                ret = "resolution";
                break;
            case "Raum Nummer":
                ret = "room_nb";
                break;
            case "Etage":
                ret = "floor";
                break;
            case "Desk Sharing":
                ret = "desk_share";
                break;
            case "Nutzer Email":
                ret = "mail";
                break;
            case "HDMI":
                ret = "hdmi";
                break;
            case "DisplayPort":
                ret = "dp";
                break;
            case "VGA":
                ret = "vga";
                break;
            case "DVI":
                ret = "dvi";
                break;
            case "Garantie":
                ret = "warranty";
                break;
            case "PC-IV-Nummer":
                ret = "pc_iv_number";
                break;
            case "HD-IV-Nummer":
                ret = "hd_iv_number";
                break;
            case "TE-IV-Nummer":
                ret = "te_iv_number";
                break;
            case "DS-IV-Nummer":
                ret = "ds_iv_number";
                break;
            case "SC-IV-Nummer":
                ret = "sc_iv_number";
                break;
            case "MO1-IV-Nummer":
                ret = "mo_iv_number_1";
                break;
            case "MO2-IV-Nummer":
                ret = "mo_iv_number_2";
                break;
            case "Maus":
                ret = "has_mouse";
                break;
            case "Tastatur":
                ret = "has_keyboard";
                break;
            case "Zustand":
                ret = "c_status";
                break;
            case "Zustandsbemerkung":
                ret = "c_note";
                break;
            case "E-Sim Nummer":
                ret = "esim_number";
                break;
            case "E-Sim Pin":
                ret = "esim_pin";
                break;
            case "Rufnummer":
                ret = "call_number";
                break;
            case "Display Pin":
                ret = "display_pin";
                break;
            case "Tarif":
                ret = "tariff";
                break;
            case "Sim Nummer":
                ret = "sim_number";
                break;
            case "Sim Pin":
                ret = "sim_pin";
                break;
            case "Letzte Änderung":
                ret = "last_modified";
                break;
            case "Bezeichner":
                ret = "name";
                break;
            case "Typ":
                ret = "typ";
                break;
            case "Name":
                ret = "device_name";
                break;
            case "Touchframe-Version":
                ret = "touchframe_vs";
                break;
            case "Systemversion":
                ret = "system_vs";
                break;
            case "Android-Version":
                ret = "android_vs";
                break;
            case "Patchbox-Nummer":
                ret = "patchbox_nr";
                break;
        }
        return ret;
    }

    public static String filterBoxTextToAccordingDataTable(String s) {
        String ret = "";
        switch (s) {
            case "PCs":
                ret = "pc";
                break;
            case "Drucker":
                ret = "printer";
                break;
            case "Scanner":
                ret = "scanner";
                break;
            case "Desks":
                ret = "desk";
                break;
            case "Telefone":
                ret = "telephone";
                break;
            case "Monitore":
                ret = "monitor";
                break;
            case "Headsets":
                ret = "headset";
                break;
            case "Dockingstationen":
                ret = "dockingstation";
                break;
            case "TVs":
                ret = "tv";
                break;
            case "Router":
                ret = "router";
                break;
        }
        return ret;
    }

    public static String getShortCutFromTable(String table) {
        String ret = "";
        switch (table) {
            case "pc":
                ret = "pc";
                break;
            case "monitor":
                ret = "mo";
                break;
            case "headset":
                ret = "hd";
                break;
            case "dockingstation":
                ret = "ds";
                break;
            case "telephone":
                ret = "te";
                break;
            case "scanner":
                ret = "sc";
                break;
            case "printer":
                ret = "pr";
                break;
            case "desk":
                ret = "dk";
                break;
            case "tv":
                ret = "tv";
                break;
            case "router":
                ret = "rt";
                break;
        }
        return ret;
    }

    public static String getTableFromShortCut(String shortCut) {
        String ret = "";
        switch (shortCut.toLowerCase()) {
            case "pc":
                ret = "pc";
                break;
            case "mo":
                ret = "monitor";
                break;
            case "sc":
                ret = "scanner";
                break;
            case "hd":
                ret = "headset";
                break;
            case "te":
                ret = "telephone";
                break;
            case "pr":
                ret = "printer";
                break;
            case "ds":
                ret = "dockingstation";
                break;
            case "dk":
                ret = "desk";
                break;
            case "mc":
                ret = "miscellaneous";
                break;
            case "tv":
                ret = "tv";
                break;
            case "rt":
                ret = "router";
                break;
        }
        return ret;
    }

    public static String ivObjectToDisplayable(String s) {
        String ret = "";
        switch (s) {
            case "modell":
                ret = "Modell:";
                break;
            case "manufacturer":
                ret = "Hersteller:";
                break;
            case "s_number":
                ret = "Seriennummer:";
                break;
            case "c_status":
                ret = "Zustand:";
                break;
            case "c_note":
                ret = "Zustandsbemerkung:";
                break;
            case "esim_number":
                ret = "E-Sim Nummer:";
                break;
            case "esim_pin":
                ret = "E-Sim Pin:";
                break;
            case "call_number":
                ret = "Rufnummer:";
                break;
            case "tariff":
                ret = "Tarif:";
                break;
            case "sim_pin":
                ret = "Simkarten Pin:";
                break;
            case "display_pin":
                ret = "Display Pin:";
                break;
            case "name":
                ret = "Bezeichner:";
                break;
            case "typ":
                ret = "Typ:";
                break;
        }
        return ret;
    }

    public static String ivObjectRetToDisplayable(String s) {
        String ret = "";
        switch (s) {
            case "modell":
                ret = "Modell:";
                break;
            case "manufacturer":
                ret = "Hersteller:";
                break;
            case "s_number":
                ret = "Seriennummer:";
                break;
            case "c_status":
                ret = "Alter Zustand:";
                break;
            case "c_note":
                ret = "Alte Zustandsbemerkung:";
                break;
            case "new_c_note":
                ret = "Neue Zustandsbemerkung";
                break;
            case "new_c_status":
                ret = "Neuer Zustand:";
                break;
            case "esim_number":
                ret = "E-Sim Nummer:";
                break;
            case "esim_pin":
                ret = "E-Sim Pin:";
                break;
            case "call_number":
                ret = "Rufnummer:";
                break;
            case "tariff":
                ret = "Tarif:";
                break;
            case "sim_pin":
                ret = "Simkarten Pin:";
                break;
            case "display_pin":
                ret = "Display Pin:";
                break;
            case "name":
                ret = "Bezeichner:";
                break;
            case "typ":
                ret = "Typ:";
                break;
        }
        return ret;
    }


    public static String getDateTimeNow() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static String getDateTimeNowDay() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static boolean contains(String c, String[] data){
        for (String s : data) {
            if (s.equals(c)){
                return true;
            }
        }
        return false;
    }


    //new Throwable().getStackTrace()[0].getLineNumber(

    public static void systemOutPrintln(ArrayList<ArrayList<String>> in){
        System.out.println(Arrays.deepToString(convertArrayList_ArrayList_StringTo2DArray(in)));
    }

    public static void systemOutPrintln(String out){
        System.out.println(out);
    }

    public static void systemErrPrintln(String err){
        System.err.println(err);
    }

    public static void systemOutPrintln(ArrayList<ArrayList<String>> in, int line){
        System.out.println(line + ": " + Arrays.deepToString(convertArrayList_ArrayList_StringTo2DArray(in)));
    }

    public static void systemOutPrintln(String out, int line){
        System.out.println(line + ": " + out);
    }

    public static void systemErrPrintln(String err, int line){
        System.err.println(line + ": " + err);
    }
}