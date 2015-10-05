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
public class Db {
    String server;
    String port;
    String database;
    String username;
    String password;
    String instance;
    private String driver;
    
    public Db(){       
       this.database = "F21405";
        this.server = "localhost";
        this.instance = "";
        this.username = "sa";
        this.password = "abc@123";
        this.port = "1433";
        this.driver = DAL.SysVar.driver_msSQL;
    }
    
    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the driver
     */
    public String getDriver() {
        return driver;
    }

    /**
     * @param driver the driver to set
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    
    
    
}
