package Main.utility;

import SQL.SQLConnector;
import SQL.Statements.SQLDeleteStatements;
import SQL.Statements.SQLInsertStatements;
import SQL.Statements.SQLSelectStatements;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;


public class ADWrapper {

    private static DirContext ctx;

    public static void init() {
        String ldapURL = "ldap://10.10.1.14:389"; // Ändern Sie dies entsprechend Ihrer AD-Konfiguration
        String username = "admin";
        String password = "Apo22!save96155";

        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapURL);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, username);
        env.put(Context.SECURITY_CREDENTIALS, password);
        env.put(Context.REFERRAL, "ignore");

        try {
            ctx = new InitialDirContext(env);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public static void close(){
        try {
            ctx.close();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String[][] getUsers() {
        ArrayList<ArrayList<String>> users = new ArrayList<>();
        try {
            SearchControls searchControls = new SearchControls();
            searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE); // Subtree durchsuchen

            String searchBase = "DC=LCG,DC=LOCAL"; // Basis-DN des Active Directory
            String searchFilter = "(objectClass=person)"; // Filter für Benutzer

            NamingEnumeration<SearchResult> results = ctx.search(searchBase, searchFilter, searchControls);

            SearchResult searchResult;
            while ((searchResult = results.next()) != null) {
                Attributes attrs = searchResult.getAttributes();
                Attribute accountName = attrs.get("sAMAccountName");

                if (accountName.get().toString().substring(1, 2).equals(".")){
                    ArrayList<String> temp = new ArrayList<>();
                    temp.add(accountName.get().toString());
                    try {
                        Attribute mail = attrs.get("mail");
                        temp.add(mail.get().toString());
                    }
                    catch (Exception e){
                        temp.add("");
                    }
                    users.add(temp);
                }
            }
        } catch (Exception e) {
        }
        return Utils.convertArrayList_ArrayList_StringTo2DArray(users);
    }

    public static void syncDatabase() {
        SQLSelectStatements sqlSelectStatements = new SQLSelectStatements(new SQLConnector());
        SQLInsertStatements sqlInsertStatements = new SQLInsertStatements(new SQLConnector());
        SQLDeleteStatements sqlDeleteStatements = new SQLDeleteStatements(new SQLConnector());

        String[][] users = getUsers();
        ArrayList<ArrayList<String>> newUsers = new ArrayList<>();
        String[][] oldUsers = sqlSelectStatements.getAllUsersActive0();

        //alte user mergen
        for (int i = 0; i < oldUsers[0].length; i++) {
            for (String[] adUser : users){
                if (oldUsers[0][i].equals(adUser[0])){
                    ArrayList<String> temp = new ArrayList<>();
                    temp.add(oldUsers[0][i]);
                    temp.add(oldUsers[3][i]);
                    temp.add(oldUsers[2][i]);
                    temp.add(oldUsers[4][i]);
                    newUsers.add(temp);
                }
            }
        }

        //neue user mergen
        for (int i = 0; i < users.length; i++) {
            boolean is = false;
            for (ArrayList<String> userList : newUsers) {
                if (userList.get(0).equals(users[i][0])){
                    is = true;
                }
            }
            if (!is){
                ArrayList<String> temp = new ArrayList<>();
                temp.add(users[i][0]); //name
                temp.add(users[i][1]); //email
                temp.add("0"); //status
                temp.add("1"); //active
                newUsers.add(temp);
            }
        }

        //nicht vorhandener user eintrag
        boolean dummyPresent = false;
        for (int i = 0; i < oldUsers[0].length; i++) {
            if (oldUsers[0][i].equals("-1")){
                dummyPresent = true;
                break;
            }
        }
        if (!dummyPresent){
            ArrayList<String> temp = new ArrayList<>();
            temp.add("-1");
            temp.add(" - ");
            temp.add("1");
            temp.add("1");
            newUsers.add(temp);
        }

        sqlDeleteStatements.delete("user");

        for (ArrayList<String> list : newUsers) {
            sqlInsertStatements.insert("insert into user (name, mail, current_status, active) values (" +
                    "'" + list.get(0) + "', " +
                    "'" + list.get(1) + "', " +
                    "'" + list.get(2) + "', " +
                    "'" + list.get(3) + "'" +
                    ")");
        }

        System.out.println("user merged!");
    }
}
