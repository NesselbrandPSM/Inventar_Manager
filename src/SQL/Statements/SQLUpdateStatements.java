package SQL.Statements;

import SQL.SQLConnector;
import SQL.util.SQLStatement;

import java.util.ArrayList;

public class SQLUpdateStatements {
    private SQLConnector connector;

    public SQLUpdateStatements(SQLConnector connector) {
        this.connector = connector;
    }

    public void updateUserStatus(String name, int status) {
        connector.query(new SQLStatement(
                "update user SET current_status = " + status + " where name = " + "'" + name + "'"
        ));
    }

    public void updateUserData(String userName, String[] data) {
        StringBuilder sb = new StringBuilder();

        sb.append("update user set ");

        sb.append("address = '");
        sb.append(data[0]);
        sb.append("', ");

        sb.append("working_hours = '");
        sb.append(data[1]);
        sb.append("', ");

        sb.append("working_days = '");
        sb.append(data[2]);
        sb.append("', ");

        sb.append("homeoffice = ");
        sb.append(data[3]);
        sb.append(", ");

        sb.append("contractDate = '");
        sb.append(data[5]);
        sb.append("', ");

        sb.append("entrytransfer = ");
        sb.append(data[4]);

        sb.append(" where name = ");
        sb.append("'");
        sb.append(userName);
        sb.append("'");
        System.out.println(sb);

        connector.query(new SQLStatement(
                sb.toString()
        ));
    }

    public void updateEntryUserAndStatus(String userName, String iv_number, String status, String table) {
        connector.query(new SQLStatement(
                "update " + table + " SET inventory_user_key = '" + userName + "', current_status = '" + status + "' where iv_number = '" + iv_number + "'"
        ));
    }

    public void changeCurrentStatus(String newStatus, String table, String iv_number) {
        connector.query(new SQLStatement(
                "update " + table + " SET current_status = '" + newStatus + "' where iv_number = '" + iv_number + "'"
        ));
    }

    public void updateSetting(String sp_name, String sp_value) {
        connector.query(new SQLStatement(
                "update settings SET sp_value = '" + sp_value + "' where sp_name = '" + sp_name + "'"
        ));
    }

    public void updatePC(String[] args, String iv_number) {
        connector.query(new SQLStatement(
                "update pc SET " +
                        "category = '" + args[1] + "', " +
                        "current_status = '" + args[2] + "', " +
                        "manufacturer = '" + args[5] + "', " +
                        "modell = '" + args[6] + "', " +
                        "s_number = '" + args[7] + "', " +
                        "cpu = '" + args[8] + "', " +
                        "memory_ram_size_gb = '" + args[9] + "', " +
                        "memory_rom_size_gb = '" + args[10] + "', " +
                        "os = '" + args[11] + "', " +
                        "ip = '" + args[12] + "', " +
                        "last_update = '" + args[13] + "', " +
                        "dguv = '" + args[18] + "', " +
                        "note = '" + args[19] + "', " +
                        "inventory_company_key = '" + args[20] + "', " +
                        "purchase_date = '" + args[22] + "', " +
                        "purchase_price = '" + args[23] + "', " +
                        "warranty = '" + args[24] + "', " +
                        "c_status = '" + args[25] + "', " +
                        "c_note = '" + args[26] + "', " +
                        "esim_number = '" + args[27] + "', " +
                        "esim_pin = '" + args[28] + "' " +
                        "where iv_number = '" + iv_number + "'"
        ));
    }

    public void updateDesk(String[] args, String iv_number) {
        connector.query(new SQLStatement(
                "update desk SET " +
                        "inventory_company_key = '" + args[0] + "', " +
                        "current_status = '" + args[2] + "', " +
                        "room_nb = '" + args[3] + "', " +
                        "desk_share = '" + args[4] + "', " +
                        "floor = '" + args[5] + "', " +
                        "modell = '" + args[7] + "', " +
                        "manufacturer = '" + args[8] + "', " +
                        "s_number = '" + args[9] + "', " +
                        "te_iv_number = '" + args[10] + "', " +
                        "sc_iv_number = '" + args[11] + "', " +
                        "hd_iv_number = '" + args[12] + "', " +
                        "ds_iv_number = '" + args[13] + "', " +
                        "pc_iv_number = '" + args[14] + "', " +
                        "has_mouse = '" + args[15] + "', " +
                        "has_keyboard = '" + args[16] + "', " +
                        "mo_iv_number_1 = '" + args[17] + "', " +
                        "mo_iv_number_2 = '" + args[18] + "', " +
                        "purchase_date = '" + args[19] + "', " +
                        "purchase_price = '" + args[20] + "', " +
                        "warranty = '" + args[21] + "', " +
                        "note = '" + args[22] + "', " +
                        "c_status = '" + args[23] + "', " +
                        "c_note = '" + args[24] + "' " +
                        "where iv_number = '" + iv_number + "'"
        ));
    }

    public void updateTE(String[] args, String ivNumber) {
        connector.query(new SQLStatement(
                "update telephone SET " +
                        "inventory_company_key = '" + args[1] + "', " +
                        "manufacturer = '" + args[3] + "', " +
                        "s_number = '" + args[4] + "', " +
                        "current_status = '" + args[5] + "', " +
                        "dguv = '" + args[6] + "', " +
                        "modell = '" + args[7] + "', " +
                        "ip = '" + args[8] + "', " +
                        "purchase_date = '" + args[9] + "', " +
                        "purchase_price = '" + args[10] + "', " +
                        "warranty = '" + args[11] + "', " +
                        "note = '" + args[12] + "', " +
                        "c_status = '" + args[13] + "', " +
                        "c_note = '" + args[14] + "', " +
                        "call_number = '" + args[15] + "', " +
                        "sim_number = '" + args[16] + "', " +
                        "display_pin = '" + args[17] + "', " +
                        "tariff = '" + args[18] + "' " +
                        "where iv_number = '" + ivNumber + "'"
        ));
    }

    public void updateHD(String[] args, String ivNumber) {
        connector.query(new SQLStatement(
                "update headset SET " +
                        "manufacturer = '" + args[1] + "', " +
                        "s_number = '" + args[2] + "', " +
                        "current_status = '" + args[3] + "', " +
                        "modell = '" + args[4] + "', " +
                        "dguv = '" + args[5] + "', " +
                        "inventory_company_key = '" + args[6] + "', " +
                        "purchase_date = '" + args[7] + "', " +
                        "purchase_price = '" + args[8] + "', " +
                        "warranty = '" + args[9] + "', " +
                        "note = '" + args[11] + "', " +
                        "c_status = '" + args[12] + "', " +
                        "c_note = '" + args[13] + "' " +
                        "where iv_number = '" + ivNumber + "'"
        ));
    }

    public void updateTV(String[] args, String ivNumber) {
        connector.query(new SQLStatement(
                "update tv SET " +
                        "touchframe_vs = '" + args[1] + "', " +
                        "system_vs = '" + args[2] + "', " +
                        "android_vs = '" + args[3] + "', " +
                        "device_name = '" + args[4] + "', " +
                        "s_number = '" + args[5] + "', " +
                        "memory_ram_size_gb = '" + args[6] + "', " +
                        "memory_rom_size_gb = '" + args[7] + "', " +
                        "resolution = '" + args[8] + "', " +
                        "purchase_date = '" + args[9] + "', " +
                        "purchase_price = '" + args[10] + "', " +
                        "note = '" + args[11] + "', " +
                        "c_status = '" + args[12] + "', " +
                        "c_note = '" + args[13] + "', " +
                        "inventory_company_key = '" + args[14] + "', " +
                        "manufacturer = '" + args[15] + "', " +
                        "modell = '" + args[16] + "', " +
                        "current_status = '" + args[17] + "', " +
                        "dguv = '" + args[18] + "', " +
                        "warranty = '" + args[19] + "', " +
                        "room_nb = '" + args[20] + "', " +
                        "floor = '" + args[21] + "' " +
                        "where iv_number = '" + ivNumber + "'"
        ));
    }

    public void updateSC(String[] args, String ivNumber) {
        connector.query(new SQLStatement(
                "update scanner SET " +
                        "inventory_company_key = '" + args[1] + "', " +
                        "current_status = '" + args[3] + "', " +
                        "dguv = '" + args[4] + "', " +
                        "s_number = '" + args[5] + "', " +
                        "manufacturer = '" + args[6] + "', " +
                        "modell = '" + args[7] + "', " +
                        "ip = '" + args[8] + "', " +
                        "purchase_date = '" + args[9] + "', " +
                        "purchase_price = '" + args[10] + "', " +
                        "warranty = '" + args[11] + "', " +
                        "note = '" + args[12] + "', " +
                        "c_status = '" + args[13] + "', " +
                        "c_note = '" + args[14] + "' " +
                        "where iv_number = '" + ivNumber + "'"
        ));
    }

    public void updateRT(String[] args, String ivNumber) {
        connector.query(new SQLStatement(
                "update router SET " +
                        "inventory_company_key = '" + args[1] + "', " +
                        "s_number = '" + args[2] + "', " +
                        "room_nb = '" + args[3] + "', " +
                        "floor = '" + args[4] + "', " +
                        "patchbox_nr = '" + args[5] + "', " +
                        "current_status = '" + args[6] + "', " +
                        "dguv = '" + args[7] + "', " +
                        "note = '" + args[8] + "', " +
                        "manufacturer = '" + args[9] + "', " +
                        "modell = '" + args[10] + "', " +
                        "ip = '" + args[11] + "', " +
                        "purchase_date = '" + args[12] + "', " +
                        "purchase_price = '" + args[13] + "', " +
                        "warranty = '" + args[14] + "', " +
                        "c_status = '" + args[15] + "', " +
                        "c_note = '" + args[16] + "' " +
                        "where iv_number = '" + ivNumber + "'"
        ));
    }

    public void updateDS(String[] args, String ivNumber) {
        connector.query(new SQLStatement(
                "update dockingstation SET " +
                        "inventory_company_key = '" + args[0] + "', " +
                        "manufacturer = '" + args[3] + "', " +
                        "current_status = '" + args[4] + "', " +
                        "modell = '" + args[5] + "', " +
                        "dguv = '" + args[6] + "', " +
                        "s_number = '" + args[7] + "', " +
                        "purchase_date = '" + args[8] + "', " +
                        "purchase_price = '" + args[9] + "', " +
                        "warranty = '" + args[10] + "', " +
                        "note = '" + args[11] + "', " +
                        "c_status = '" + args[12] + "', " +
                        "c_note = '" + args[13] + "' " +
                        "where iv_number = '" + ivNumber + "'"
        ));
    }

    public void updateMC(String[] args, String ivNumber) {
        connector.query(new SQLStatement(
                "update miscellaneous SET " +
                        "inventory_company_key = '" + args[2] + "', " +
                        "current_status = '" + args[3] + "', " +
                        "c_status = '" + args[4] + "', " +
                        "c_note = '" + args[5] + "', " +
                        "typ = '" + args[6] + "', " +
                        "name = '" + args[7] + "', " +
                        "note = '" + args[8] + "' " +
                        "where iv_number = '" + ivNumber + "'"
        ));
    }

    public void updateMO(String[] args, String ivNumber) {
        connector.query(new SQLStatement(
                "update monitor SET " +
                        "inventory_company_key = '" + args[0] + "', " +
                        "manufacturer = '" + args[2] + "', " +
                        "s_number = '" + args[3] + "', " +
                        "current_status = '" + args[4] + "', " +
                        "dguv = '" + args[5] + "', " +
                        "resolution = '" + args[6] + "', " +
                        "modell = '" + args[8] + "', " +
                        "purchase_date = '" + args[9] + "', " +
                        "purchase_price = '" + args[10] + "', " +
                        "warranty = '" + args[11] + "', " +
                        "hdmi = '" + args[12] + "', " +
                        "dp = '" + args[13] + "', " +
                        "vga = '" + args[14] + "', " +
                        "dvi = '" + args[15] + "', " +
                        "note = '" + args[16] + "', " +
                        "c_status = '" + args[17] + "', " +
                        "c_note = '" + args[18] + "' " +
                        "where iv_number = '" + ivNumber + "'"
        ));
    }

    public void updatePR(String[] args, String ivNumber) {
        connector.query(new SQLStatement(
                "update printer SET " +
                        "current_status = '" + args[0] + "', " +
                        "inventory_company_key = '" + args[1] + "', " +
                        "manufacturer = '" + args[2] + "', " +
                        "modell = '" + args[3] + "', " +
                        "s_number = '" + args[4] + "', " +
                        "eq_number = '" + args[5] + "', " +
                        "ip = '" + args[6] + "', " +
                        "purchase_date = '" + args[7] + "', " +
                        "purchase_price = '" + args[8] + "', " +
                        "warranty = '" + args[9] + "', " +
                        "dguv = '" + args[10] + "', " +
                        "note = '" + args[12] + "', " +
                        "c_status = '" + args[13] + "', " +
                        "c_note = '" + args[14] + "', " +
                        "room_nb = '" + args[15] + "', " +
                        "floor = '" + args[16] + "' " +
                        "where iv_number = '" + ivNumber + "'"
        ));
    }
}
