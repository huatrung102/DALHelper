/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import java.util.HashMap;

/**
 *
 * @author TrungHTH
 */
public class CastHelper {
    public static <T>T castToPrimitiveType(Object value){
        T result = null;
        try {        
            Class typeClass =  value.getClass();
            result = (T)typeClass.cast(value);
        } catch (Exception e) {
        }
        return result;        
    }
    public static <T extends HashMap>T castToArrayType(Object value){
        T result = null;
        try {        
            Class typeClass =  value.getClass();
            result = (T)typeClass.cast(value);
        } catch (Exception e) {
        }
        return result;        
    }
}
