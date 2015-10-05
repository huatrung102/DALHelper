/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Statement;


import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Administrator PC
 */
public class SqlHelper {
    //public static final String myConnectionDriver =DAL.SysVar.driver_msSQL;
    public enum commandType{
        Text,
        StoreProcedure
    }

    // <editor-fold defaultstate="collapsed" desc="execute non-query Store">
    /**
     * Hàm thực thi StoreProcedure|Text có hoặc không có trả về giá trị (tham số tùy chọn)
     * Sử dụng cho insert, update, delete
     * @param db (database config)
     * @param cmdText (string sql store)
     * @param returnResult (boolean store co trả về value hay không?)
     * @param cmdParams (optional có kèm tham số hay không?)
     * @return 
     */
    public static int executeNonQueryByStore(Db db,String cmdText,boolean returnResult, Object... cmdParams){
        CallableStatement stmt = getCallableStatement(db,cmdText,returnResult,cmdParams);
        if (stmt == null){
            return SysVar.Error.error_nullStoreOrText;
        }       
        try
        {
            
            return returnResult? stmt.getInt(1) : stmt.executeUpdate();
        } catch (SQLException ex){
            Logger.getLogger(SqlHelper.class.getName()).log(Level.SEVERE, null,ex);            
        } finally{
            closeConnection(stmt);
        }        
        return SysVar.Error.error_catchMethod;
    }
    
    public static int executeNonQueryByText(Db db,String cmdText ,Object... cmdParams){        
        PreparedStatement pstmt = getPreparedStatement(db,cmdText, cmdParams);
        if (pstmt == null){
            return SysVar.Error.error_nullStoreOrText;
        }
        try
        {
            return pstmt.executeUpdate();
        } catch (SQLException ex){
            Logger.getLogger(SqlHelper.class.getName()).log(Level.SEVERE, null, ex);
            closeConnection(pstmt);
        }
        return SysVar.Error.error_catchMethod;
    }
    // </editor-fold>
    
   
    
   
    public static ResultSet getResultSet(Db db,String cmdText,commandType type ,Object... cmdParams){
        if(type == commandType.StoreProcedure)
            cmdText = StringHelper.getStrQueryStore(cmdText,cmdParams);
        
        PreparedStatement pstmt = getPreparedStatement(db,cmdText, cmdParams);
        if (pstmt == null){
            return null;
        }
        try
        {
            return pstmt.executeQuery();
        } catch (SQLException ex){
            Logger.getLogger(SqlHelper.class.getName()).log(Level.SEVERE, null, ex);
            closeConnection(pstmt);
        }
        return null;
    }

    public static <T>T execScalar(Db db,String cmdText,commandType type, Object... cmdParams){
        ResultSet rs = getResultSet(db,cmdText,type ,cmdParams);
        T obj = getScalar(rs);
        closeConnection(rs);
        return obj;
    }
    

   // <editor-fold defaultstate="collapsed" desc="return private CallableStatement, PreparedStatement, Scalar">
    private static CallableStatement getCallableStatement(Db db,String cmdText,boolean returnResult, Object... cmdParams){
        
        CallableStatement cstmt = null;
        try {
             Connection conn = ManageConnection.getConnection(db);
             cmdText = returnResult? 
                     StringHelper.getStrQueryStoreReturn(cmdText,cmdParams) 
                     : StringHelper.getStrQueryStore(cmdText,cmdParams);
             
             cstmt = conn.prepareCall(cmdText);
             cstmt.registerOutParameter(1, java.sql.Types.INTEGER);

            return cstmt;
         }
         catch (Exception ex) {
            Logger.getLogger(SqlHelper.class.getName()).log(Level.SEVERE, null, ex);
             close(cstmt);
        }
        return null;
    
    } 
    
    private static PreparedStatement getPreparedStatement(Db db,String cmdText, Object... cmdParams){
        Connection conn = ManageConnection.getConnection(db);
        if (conn == null){
            return null;
        }
        PreparedStatement pstmt = null;
        try
        {            
             pstmt = conn.prepareStatement(cmdText,ResultSet.TYPE_SCROLL_SENSITIVE,
                                        ResultSet.CONCUR_UPDATABLE);
            if(cmdParams != null && cmdParams.length > 0){
               int i = 1;
                for (Object item : cmdParams){
                    pstmt.setObject(i, item);
                    i++;
                }
            }                        
            return pstmt;
        } catch (SQLException ex){
            Logger.getLogger(SqlHelper.class.getName()).log(Level.SEVERE, null, ex);
            close(pstmt);
        }
        return null;
    }
    private static <T>T getScalar(ResultSet rs){
        
        if (rs == null){
            return null;
        }
        T obj = null;
        try
        {
            if (rs.next()){
                obj = CastHelper.castToPrimitiveType(rs.getObject(1)) ;
            }
        } catch (SQLException ex){
            Logger.getLogger(SqlHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obj;
    }
    // </editor-fold>
  
   // <editor-fold defaultstate="collapsed" desc="close connection">
    private static void close(Object obj){
        if (obj == null){
            return;
        }
        try
        {
            if (obj instanceof Statement){
                ((Statement) obj).close();
            } else if (obj instanceof PreparedStatement){
                ((PreparedStatement) obj).close();
            } else if (obj instanceof ResultSet){
                ((ResultSet) obj).close();
            } else if (obj instanceof Connection){
                ((Connection) obj).close();
            }
        } catch (SQLException ex){
            Logger.getLogger(SqlHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public static void closeConnection(Object obj){
        if (obj == null){
            return;
        }
        try
        {
            if (obj instanceof Statement){
                ((Statement) obj).getConnection().close();
            } else if (obj instanceof PreparedStatement){
                ((PreparedStatement) obj).getConnection().close();
            } else if (obj instanceof ResultSet){
                ((ResultSet) obj).getStatement().getConnection().close();
            } else if (obj instanceof Connection){
                ((Connection) obj).close();
            }
        } catch (SQLException ex){
            Logger.getLogger(SqlHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
// </editor-fold>    
    
    
    
    
}
