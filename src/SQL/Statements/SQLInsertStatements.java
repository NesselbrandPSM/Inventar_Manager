package SQL.Statements;

import SQL.SQLConnector;
import SQL.util.SQLStatement;

public class SQLInsertStatements {
    public SQLInsertStatements(SQLConnector connector) {
        this.connector = connector;
    }

    private SQLConnector connector;

    public void insert(String statement) {
        connector.query(new SQLStatement(statement));
    }

    public void insertTyp(String typ){
        connector.query(new SQLStatement(
                "insert into misctype (typ) values ('" + typ + "')"
        ));
    }

    public void inputPCEntry(String[] args) {
        SQLStatement s = new SQLStatement(
                "insert into pc (" +
                        "iv_number, s_number, current_status, dguv, note, category, manufacturer, modell, memory_ram_size_gb, memory_rom_size_gb, cpu, os, ip, last_update, inventory_company_key, inventory_user_key, purchase_date, purchase_price, warranty, c_status, c_note, esim_number, esim_pin) " +
                        "values (" +
                        "'" + args[0] + "', " +
                        "'" + args[7] + "', " +
                        "'" + args[2] + "', " +
                        "'" + args[18] + "', " +
                        "'" + args[19] + "', " +
                        "'" + args[1] + "', " +
                        "'" + args[5] + "', " +
                        "'" + args[6] + "', " +
                        "'" + args[9] + "', " +
                        "'" + args[10] + "', " +
                        "'" + args[8] + "', " +
                        "'" + args[11] + "', " +
                        "'" + args[12] + "', " +
                        "'" + args[13] + "', " +
                        "'" + args[20] + "', " +
                        "'" + args[21] + "', " +
                        "'" + args[22] + "', " +
                        "'" + args[23] + "', " +
                        "'" + args[24] + "', " +
                        "'" + args[25] + "', " +
                        "'" + args[26] + "', " +
                        "'" + args[27] + "', " +
                        "'" + args[28] + "'" +
                        ")"
        );
        System.out.println(s.getStatement());
        connector.query(s);
    }

    public void inputPREntry(String[] args) {
        SQLStatement s = new SQLStatement(
                "insert into printer (" +
                        "current_status, inventory_company_key, manufacturer, modell, s_number, eq_number, ip, purchase_date, purchase_price, warranty, dguv, iv_number, note, c_status, c_note" +
                        ") values (" +
                        "'" + args[0] + "', " +
                        "'" + args[1] + "', " +
                        "'" + args[2] + "', " +
                        "'" + args[3] + "', " +
                        "'" + args[4] + "', " +
                        "'" + args[5] + "', " +
                        "'" + args[6] + "', " +
                        "'" + args[7] + "', " +
                        "'" + args[8] + "', " +
                        "'" + args[9] + "', " +
                        "'" + args[10] + "', " +
                        "'" + args[11] + "', " +
                        "'" + args[12] + "', " +
                        "'" + args[13] + "', " +
                        "'" + args[14] + "'" +
                        ")"
        );

        System.out.println(s.getStatement());
        connector.query(s);
    }

    public void inputHDEntry(String[] args) {
        SQLStatement s = new SQLStatement(
                "insert into headset (" +
                        "iv_number, manufacturer, s_number, current_status, modell, dguv, inventory_company_key, purchase_date, purchase_price, warranty, inventory_user_key, note, c_status, c_note" +
                        ") values (" +
                        "'" + args[0] + "', " +
                        "'" + args[1] + "', " +
                        "'" + args[2] + "', " +
                        "'" + args[3] + "', " +
                        "'" + args[4] + "', " +
                        "'" + args[5] + "', " +
                        "'" + args[6] + "', " +
                        "'" + args[7] + "', " +
                        "'" + args[8] + "', " +
                        "'" + args[9] + "', " +
                        "'" + args[10] + "', " +
                        "'" + args[11] + "', " +
                        "'" + args[12] + "', " +
                        "'" + args[13] + "'" +
                        ")"
        );

        System.out.println(s.getStatement());
        connector.query(s);
    }

    public void inputSCEntry(String[] args) {
        SQLStatement s = new SQLStatement(
                "insert into scanner (" +
                        "iv_number, inventory_company_key, inventory_user_key, current_status, dguv, s_number, manufacturer, modell, ip, purchase_date, purchase_price, warranty, note, c_status, c_note" +
                        ") values (" +
                        "'" + args[0] + "', " +
                        "'" + args[1] + "', " +
                        "'" + args[2] + "', " +
                        "'" + args[3] + "', " +
                        "'" + args[4] + "', " +
                        "'" + args[5] + "', " +
                        "'" + args[6] + "', " +
                        "'" + args[7] + "', " +
                        "'" + args[8] + "', " +
                        "'" + args[9] + "', " +
                        "'" + args[10] + "', " +
                        "'" + args[11] + "', " +
                        "'" + args[12] + "', " +
                        "'" + args[13] + "'" +
                        ")"
        );

        System.out.println(s.getStatement());
        connector.query(s);
    }

    public void inputDSEntry(String[] args) {
        SQLStatement s = new SQLStatement(
                "insert into dockingstation (" +
                        "inventory_company_key, inventory_user_key, iv_number, manufacturer, current_status, modell, dguv, s_number, purchase_date, purchase_price, warranty, note, c_status, c_note" +
                        ") values (" +
                        "'" + args[0] + "', " +
                        "'" + args[1] + "', " +
                        "'" + args[2] + "', " +
                        "'" + args[3] + "', " +
                        "'" + args[4] + "', " +
                        "'" + args[5] + "', " +
                        "'" + args[6] + "', " +
                        "'" + args[7] + "', " +
                        "'" + args[8] + "', " +
                        "'" + args[9] + "', " +
                        "'" + args[10] + "', " +
                        "'" + args[11] + "', " +
                        "'" + args[12] + "', " +
                        "'" + args[13] + "'" +
                        ")"
        );

        System.out.println(s.getStatement());
        connector.query(s);
    }

    public void inputMOEntry(String[] args) {
        SQLStatement s = new SQLStatement(
                "insert into monitor (" +
                        "inventory_company_key, inventory_user_key, manufacturer, s_number, current_status, dguv, resolution, iv_number, modell, purchase_date, purchase_price, warranty, hdmi, dp, vga, dvi, note, c_status, c_note" +
                        ") values (" +
                        "'" + args[0] + "', " +
                        "'" + args[1] + "', " +
                        "'" + args[2] + "', " +
                        "'" + args[3] + "', " +
                        "'" + args[4] + "', " +
                        "'" + args[5] + "', " +
                        "'" + args[6] + "', " +
                        "'" + args[7] + "', " +
                        "'" + args[8] + "', " +
                        "'" + args[9] + "', " +
                        "'" + args[10] + "', " +
                        "'" + args[11] + "', " +
                        "'" + args[12] + "', " +
                        "'" + args[13] + "', " +
                        "'" + args[14] + "', " +
                        "'" + args[15] + "', " +
                        "'" + args[16] + "', " +
                        "'" + args[17] + "', " +
                        "'" + args[18] + "'" +
                        ")"
        );

        System.out.println(s.getStatement());
        connector.query(s);
    }

    public void inputTEEntry(String[] args) {
        SQLStatement s = new SQLStatement(
                "insert into telephone (" +
                        "iv_number, inventory_company_key, inventory_user_key, manufacturer, s_number, current_status, dguv, modell, ip, purchase_date, purchase_price, warranty, note, c_status, c_note, call_number, sim_number, sim_pin, display_pin, tariff" +
                        ") values (" +
                        "'" + args[0] + "', " +
                        "'" + args[1] + "', " +
                        "'" + args[2] + "', " +
                        "'" + args[3] + "', " +
                        "'" + args[4] + "', " +
                        "'" + args[5] + "', " +
                        "'" + args[6] + "', " +
                        "'" + args[7] + "', " +
                        "'" + args[8] + "', " +
                        "'" + args[9] + "', " +
                        "'" + args[10] + "', " +
                        "'" + args[11] + "', " +
                        "'" + args[12] + "', " +
                        "'" + args[13] + "', " +
                        "'" + args[14] + "', " +
                        "'" + args[15] + "', " +
                        "'" + args[16] + "', " +
                        "'" + args[17] + "', " +
                        "'" + args[18] + "', " +
                        "'" + args[19] + "'" +
                        ")"
        );

        System.out.println(s.getStatement());
        connector.query(s);
    }

    public void inputDKEntry(String[] args) {
        SQLStatement s = new SQLStatement(
                "insert into desk (" +
                        "inventory_company_key, inventory_user_key, current_status, room_nb, desk_share, floor, iv_number, modell, manufacturer, s_number, te_iv_number, sc_iv_number, hd_iv_number, ds_iv_number, pc_iv_number, has_mouse, has_keyboard, mo_iv_number_1, mo_iv_number_2, purchase_date, purchase_price, warranty, note, c_status, c_note" +
                        ") values (" +
                        "'" + args[0] + "', " +
                        "'" + args[1] + "', " +
                        "'" + args[2] + "', " +
                        "'" + args[3] + "', " +
                        "'" + args[4] + "', " +
                        "'" + args[5] + "', " +
                        "'" + args[6] + "', " +
                        "'" + args[7] + "', " +
                        "'" + args[8] + "', " +
                        "'" + args[9] + "', " +
                        "'" + args[10] + "', " +
                        "'" + args[11] + "', " +
                        "'" + args[12] + "', " +
                        "'" + args[13] + "', " +
                        "'" + args[14] + "', " +
                        "'" + args[15] + "', " +
                        "'" + args[16] + "', " +
                        "'" + args[17] + "', " +
                        "'" + args[18] + "', " +
                        "'" + args[19] + "', " +
                        "'" + args[20] + "', " +
                        "'" + args[21] + "', " +
                        "'" + args[22] + "', " +
                        "'" + args[23] + "', " +
                        "'" + args[24] + "'" +
                        ")"
        );

        System.out.println(s);
        connector.query(s);
    }

    public void inputMCEntry(String[] args){
        SQLStatement s = new SQLStatement(
                "insert into miscellaneous (" +
                        "iv_number, inventory_user_key, inventory_company_key, current_status, c_status, c_note, typ, name, note" +
                        ") values (" +
                        "'" + args[0] + "', " +
                        "'" + args[1] + "', " +
                        "'" + args[2] + "', " +
                        "'" + args[3] + "', " +
                        "'" + args[4] + "', " +
                        "'" + args[5] + "', " +
                        "'" + args[6] + "', " +
                        "'" + args[7] + "', " +
                        "'" + args[8] + "'" +
                        ")"
        );

        System.out.println(s.getStatement());
        connector.query(s);
    }

    public void inputUDStatement(String[] args){
        SQLStatement s = new SQLStatement(
                "insert into udmapping (" +
                        "user, iv_number, l_date, r_date" +
                        ") values (" +
                        "'" + args[0] + "', " +
                        "'" + args[1] + "', " +
                        "'" + args[2] + "', " +
                        "'" + "-1" + "'" +
                        ")"
        );

        System.out.println(s.getStatement());
        connector.query(s);
    }
}
