/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.mbean.FormListProveedoresMB;
import com.mbean.FormProveedorMB;
import com.model.Proveedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ProveedoresBS {
    
    Connection con;
    
    public ProveedoresBS(Connection con) {
        this.con = con;
    }
    
    
    
    public int insertarProveedor(Proveedor prov) {
        
        
        Integer id = 0;
        try {
            
            con.setAutoCommit(false);
            
            String sql = "insert into proveedor(razonSocial, dirFiscal, rfc, telefono, email, diasCredito, "
                    + "fax, nombreContacto, nombreBanco, numNomPlaza, noCuenta, numNomSucursal, numCuentaInterbancaria, "
                    + "clabe, ciudad, noAba, estatus, tipo)values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, prov.getRazonSocial());
            ps.setString(2, prov.getDirFiscal());
            ps.setString(3, prov.getRfc());
            ps.setString(4, prov.getTelefono());
            ps.setString(5, prov.getEmail());
            ps.setInt(6, prov.getDiasCredito());
            ps.setString(7, prov.getFax());
            ps.setString(8, prov.getNombreContacto());
            ps.setString(9, prov.getNombreBanco());
            ps.setString(10, prov.getNumNomPlaza());
            ps.setString(11, prov.getNoCuenta());
            ps.setString(12, prov.getNumNomSucursal());
            ps.setString(13, prov.getNoCuentaInterbancaria());
            ps.setString(14, prov.getClabe());
            ps.setString(15, prov.getCiudad());
            ps.setString(16, prov.getNoAba());
            ps.setInt(17, prov.getEstatus());
            ps.setInt(18, prov.getTipo());
            ps.executeUpdate();
            
            String sql2 = "select last_insert_id()";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            id = rs.getInt(1);
            
            con.commit();
            
        }catch(SQLException exe) {
            id = 0;
            exe.printStackTrace();
            try {
                con.rollback();
            }catch(SQLException e) {
                e.printStackTrace();
            }
        } 
        return id;
    }
    
    public boolean actualizarProveedor(Proveedor prov){
        boolean result = false;
        
        System.out.println("id Prov UPD: " + prov.getIdProveedor());
        try{
            
            String sql = "update proveedor set razonSocial=?, dirFiscal=?, rfc=?, telefono=?, email=?, diasCredito=?, "
                    + "fax=?, nombreContacto=?, nombreBanco=?, numNomPlaza=?, noCuenta=?, numNomSucursal=?, numCuentaInterbancaria=?, "
                    + "clabe=?, ciudad=?, noAba=?, estatus=?, tipo=? where idProveedor=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, prov.getRazonSocial());
            ps.setString(2, prov.getDirFiscal());
            ps.setString(3, prov.getRfc());
            ps.setString(4, prov.getTelefono());
            ps.setString(5, prov.getEmail());
            ps.setInt(6, prov.getDiasCredito());
            ps.setString(7, prov.getFax());
            ps.setString(8, prov.getNombreContacto());
            ps.setString(9, prov.getNombreBanco());
            ps.setString(10, prov.getNumNomPlaza());
            ps.setString(11, prov.getNoCuenta());
            ps.setString(12, prov.getNumNomSucursal());
            ps.setString(13, prov.getNoCuentaInterbancaria());
            ps.setString(14, prov.getClabe());
            ps.setString(15, prov.getCiudad());
            ps.setString(16, prov.getNoAba());
            ps.setInt(17, prov.getEstatus());
            ps.setInt(18, prov.getTipo());
            ps.setInt(19, prov.getIdProveedor());
            ps.executeUpdate();
            con.close();
            
            result = true;
        } catch(Exception e){
            
            e.printStackTrace();
        }
        
        return result;
    }
    
    public List<FormProveedorMB> listarProveedoresForm() {
        
        List<Proveedor> list = new ArrayList<>();
        Proveedor p;
        List<FormProveedorMB> listaProvedores = new ArrayList<FormProveedorMB>();
        
        try {
            String sql = "select * from proveedor";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                p = new Proveedor();
                p.setIdProveedor(rs.getInt("idProveedor"));
                p.setRazonSocial(rs.getString("razonSocial"));
                p.setDirFiscal(rs.getString("dirFiscal"));
                list.add(p);
            }
            
            for(Proveedor aux: list){
                FormProveedorMB item = new FormProveedorMB();
                item.setIdProveedor(aux.getIdProveedor());
                item.setRazonSocial(aux.getRazonSocial());
                item.setDirFiscal(aux.getDirFiscal());
                listaProvedores.add(item);
            }

            System.out.println("Num de Proveedores: " + list.size());
        
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return listaProvedores;
    }

    public List<FormListProveedoresMB> listarProveedores() {
        List<FormListProveedoresMB> list = new ArrayList<>();
        List<Proveedor> listaProv = new ArrayList<>();
        Proveedor p;

        try {
            String sql = "select * from proveedor where estatus ="+1;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                p = new Proveedor();
                p.setIdProveedor(rs.getInt("idProveedor"));
                p.setRazonSocial(rs.getString("razonSocial"));
                p.setDirFiscal(rs.getString("dirFiscal"));
                p.setRfc(rs.getString("rfc"));
                p.setTelefono(rs.getString("telefono"));
                p.setEmail(rs.getString("email"));
                p.setDiasCredito(rs.getInt("diasCredito"));
                p.setFax(rs.getString("fax"));
                p.setNombreContacto(rs.getString("nombreContacto"));
                p.setNombreBanco(rs.getString("nombreBanco"));
                p.setNumNomPlaza(rs.getString("numNomPlaza"));
                p.setNoCuenta(rs.getString("noCuenta"));
                p.setNumNomSucursal(rs.getString("numNomSucursal"));
                p.setNoCuentaInterbancaria(rs.getString("noCuentaInterbancaria"));
                p.setClabe(rs.getString("clabe"));
                p.setCiudad(rs.getString("ciudad"));
                p.setNoAba(rs.getString("noAba"));
                p.setEstatus(rs.getInt("estatus"));
                p.setTipo(rs.getInt("tipo"));
                listaProv.add(p);
            }
            
            for (Proveedor aux : listaProv) {
                FormListProveedoresMB item = new FormListProveedoresMB();
                item.setIdProveedor(aux.getIdProveedor());
                item.setRazonSocial(aux.getRazonSocial());
                item.setRfc(aux.getRfc().toUpperCase());
                list.add(item);
            }
        
        
            System.out.println("Tam Prov: " + list.size());
            
        }catch(SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public Proveedor listaProveedorPorID(int idProveedor) {
        
        Proveedor p = new Proveedor();
        
        try {
            
            String sql = "select * from proveedor where estatus="+1+" and idProveedor="+idProveedor;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            rs.first();
            p.setIdProveedor(rs.getInt("idProveedor"));
            p.setRazonSocial(rs.getString("razonSocial"));
            p.setDirFiscal(rs.getString("dirFiscal"));
            p.setRfc(rs.getString("rfc"));
            p.setTelefono(rs.getString("telefono"));
            p.setEmail(rs.getString("email"));
            p.setDiasCredito(rs.getInt("diasCredito"));
            p.setFax(rs.getString("fax"));
            p.setNombreContacto(rs.getString("nombreContacto"));
            p.setNombreBanco(rs.getString("nombreBanco"));
            p.setNumNomPlaza(rs.getString("numNomPlaza"));
            p.setNoCuenta(rs.getString("noCuenta"));
            p.setNumNomSucursal(rs.getString("numNomSucursal"));
            p.setNoCuentaInterbancaria(rs.getString("noCuentaInterbancaria"));
            p.setClabe(rs.getString("clabe"));
            p.setCiudad(rs.getString("ciudad"));
            p.setNoAba(rs.getString("noAba"));
            p.setEstatus(rs.getInt("estatus"));
            p.setTipo(rs.getInt("tipo"));
            
            con.close();
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        
        return p;
    }
    
}
