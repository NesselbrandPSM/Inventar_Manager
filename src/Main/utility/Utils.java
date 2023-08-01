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
}