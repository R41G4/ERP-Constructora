/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Mickey
 */
public class DireccionProyecto {
    
    Connection con;
    
    public DireccionProyecto() {
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/erp_versiondos", "appl_erp", "erp2015!");
            
        }catch(SQLException e) {
            e.printStackTrace();
        }catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        
    }
    
    public String obtenerDireccion(int id_proy) {
        
        String direccion ="";
        
        try {
            String sql = "Select direccion From almacen Where id_proyecto="+id_proy;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            rs.first();
            direccion = rs.getString("direccion");
            con.close();
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return direccion;
    }
    
}
