/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eventsmanagement.technique;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Amine CHAKER
 */
public class MyConnection {
    public String url = "jdbc:mysql://localhost/eventdb";
    public String login = "root";
    public String pass = "2510";
    Connection cnn;
    public static MyConnection instance;
    
    private MyConnection(){
        try {
            cnn = DriverManager.getConnection(url,login,pass);
            System.out.println("connection success");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    public static MyConnection getInstance(){
        if (instance == null) {
            instance = new MyConnection();
        }
        return instance;
    }
    public Connection getConnection(){
        return cnn;
    }
}
