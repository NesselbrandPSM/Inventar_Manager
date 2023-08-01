package Main.utility;

import SQL.util.TableAttributes;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Utils {
    public static String[][] convertResultSetTo2DArray(ResultSet res) {
        ArrayList<ArrayList<String>> table = new ArrayList<>();
        try {
            int i = 0;
            while (res.next()) {
                table.add(new ArrayList<>());
                for (TableAttributes attr : TableAttributes.values()) {
                    table.get(i).add(String.valueOf(res.getObject(attr.toString())));
                }
                i++;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        String[][] retTable = new String[table.size()][];
        for (
                int i = 0; i < table.size(); i++) {
            retTable[i] = new String[table.get(i).size()];
            for (int j = 0; j < table.get(i).size(); j++) {
                retTable[i][j] = table.get(i).get(j);
            }
        }
        return retTable;
    }
}
