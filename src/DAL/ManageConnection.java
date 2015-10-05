/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import DAL.SysVar;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator PC
 */
public class ManageConnection {
   

    private static String connectionString(Db db) {
        StringBuilder sb = new StringBuilder();
        List<String> st = new ArrayList<>();
        if (db != null) {
            if (db.getDriver().equalsIgnoreCase(SysVar.driver_msSQL)) {
                sb.append("jdbc:sqlserver://");
            } else  {
                sb.append("jdbc:jtds:sqlserver://");
            }
            sb.append(db.getServer())
                    .append(":").append(db.getPort());
            if (db.getDriver().equalsIgnoreCase(SysVar.driver_msSQL)) {
                sb.append(";DatabaseName=").append(db.getDatabase());
            } else {
                sb.append("/").append(db.getDatabase());
            }
            if (!db.getInstance().isEmpty()) {
                sb.append(";instance=").append(db.getInstance());
            }
            sb.append(";User=").append(db.getUsername())
                    .append(";Password=").append(db.getPassword());
        }

        return sb.toString();

    }

    public static Connection getConnection(Db db) {
        try {
            Class.forName(db.getDriver());
            
            return DriverManager.getConnection(connectionString(db));

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ManageConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ManageConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
