/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

/**
 *
 * @author Administrator PC
 */
public class SysVar {
   // public static final String file_DbConfig = "dbconfig.properties";
   // public static final String file_Lang = "language.properties";
    public static final String driver_msSQL = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static final String driver_jdts = "net.sourceforge.jtds.jdbc.Driver";
    public static final String patern_email = "^[\\w-\\.]+@([\\w-]+[\\w-]{2,4}$)";
    public static final String value_nullSql = "$null$";
    
    public class Error{
        public static final int error_nullStoreOrText = -2;
        public static final int error_catchMethod = -1;
    
    }
    
}
