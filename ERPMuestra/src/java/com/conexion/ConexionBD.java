/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexionBD {
    
    Connection con;
    
    public ConexionBD() {
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/erp_versiondos", "appl_erp", "erp2015!");
        }catch(ClassNotFoundException e) {
            e.printStackTrace();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    public Connection getConexion() {
        return con;
    }
    
    public void closeConexion() {
        try{
            con.close();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
}
