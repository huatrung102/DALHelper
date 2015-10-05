/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Administrator PC
 */
public class StringHelper {
    public static <T extends HashMap<String,String>> String getStringByMap(T listMap){
        String list = "";
        for(Map.Entry<String,String> entry: listMap.entrySet()){
            if(list.equals(""))
                list += "'";
            
            if(list.equals("'"))
                list +=   entry.getValue();
            else
                list += "," + entry.getValue(); 
        }
        if(list.equals(""))
            list = "''";
        else
            list += "'";
        return list;
    }
    
   public static String getStrQueryStore(String cmdText,Object... params){
        StringBuilder strSql = new StringBuilder();
        strSql.append("{call ").append(cmdText);            
            if(params != null && params.length > 0){
               addParam(strSql,params);
            }            
            strSql.append("}");
        return strSql.toString();
    }
   public static String getStrQueryStoreReturn(String cmdText, Object... params){
        StringBuilder strSql = new StringBuilder();
        strSql.append("{? = call ").append(cmdText);
            
            if(params != null && params.length > 0){
               addParam(strSql,params);               
            }            
            strSql.append("}");
        return strSql.toString();
    }
   
   private static void addParam(StringBuilder strSql,Object... params){
        int t = 0;
        
        if(params != null && params.length > 0){
            for(;t < params.length;t++){
                if(t== 0)   strSql.append("(");
                else        strSql.append(",");
                
                if(params[t] instanceof String)         strSql.append("'").append(CastHelper.castToPrimitiveType(params[t])).append("'");                    
                                       
                else if(params[t] instanceof Boolean)   strSql.append(CastHelper.castToPrimitiveType(params[t])?1:0);                    
                else if(params[t] instanceof Integer || 
                        params[t] instanceof Float || 
                        params[t] instanceof Double)    strSql.append(CastHelper.castToPrimitiveType(params[t]));                  
                                        
                 //dung de xu ly truyen chuoi co cau truc a,b,c,d vao 1 bien va xu ly trong sql rieng
                 //thuong dung de xu ly batch multi value
                else if(params[t] instanceof HashMap)   strSql.append(StringHelper.getStringByMap(CastHelper.castToArrayType(params[t])));
                if(t == (params.length -1))             strSql.append(")");
            }        
        }        
    } 
   
}
