/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.bean.AltaBean;
import com.bean.Cliente;
import com.bean.Direccion;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Mickey
 */
public class AltaDAO {
    
    Connection con;
    
    public AltaDAO(Connection con) {
        this.con = con;
    }
    
    
    public List<Cliente> buscarClientes() {
        
        List<Cliente> list = new ArrayList<>();
        Cliente cl;
        
        try {
            
            String sql = "select * from cliente";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                cl = new Cliente();
                cl.setId_cliente(rs.getInt("id_cliente"));
                cl.setRazon(rs.getString("razonSocial"));
                cl.setRfcCliente(rs.getString("rfcCliente"));
                list.add(cl);
            }
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
        
    }
    
    public void editarProyCliente(AltaBean ab) {
        
        String fecha;
        SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
        
        try {
            
            con.setAutoCommit(false);
            
            String sql = "Update proyecto set proyecto=?, numContrato=?, tipoContrato=?, importeContto=?, formaDPago=?, anticipo=?, "
                    + "fechaInicio=?, fechaFin=?, cliente=?, pctGarantia=?, impGarantia=?, pctFianzaAntcpo=?, impFianzaAntcpo=?, "
                    + "pctCumplimiento=?, impCumplimiento=?, pctVicios=?, impVicios=?, pctRespCivil=?, impRespCivil=?, "
                    + "pctTerceros=?, impTerceros=?, otros=? where id_proyecto=?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ab.getProyecto());
            ps.setString(2, ab.getNumContrato());
            ps.setString(3, ab.getContrato());
            ps.setBigDecimal(4, ab.getImporteContto());
            ps.setString(5, ab.getFormaDPago());
            ps.setBigDecimal(6, ab.getAnticipo());
            fecha = sd.format(ab.getFechInicio());
            ps.setString(7, fecha);
            fecha = sd.format(ab.getFechFin());
            ps.setString(8, fecha);
            ps.setString(9, ab.getCliente());
            ps.setFloat(10, ab.getPctGarantia());
            ps.setBigDecimal(11, ab.getImpGarantia());
            ps.setFloat(12, ab.getPctFianzaAntcpo());
            ps.setBigDecimal(13, ab.getImpFianzaAntcpo());
            ps.setFloat(14, ab.getPctCumplimiento());
            ps.setBigDecimal(15, ab.getImpCumplimiento());
            ps.setFloat(16, ab.getPctVicios());
            ps.setBigDecimal(17, ab.getImpVicios());
            ps.setFloat(18, ab.getPctRespCivil());
            ps.setBigDecimal(19, ab.getImpRespCivil());
            ps.setFloat(20, ab.getPctTerceros());
            ps.setBigDecimal(21, ab.getImpTerceros());
            ps.setString(22, ab.getOtros());
            ps.setInt(23, ab.getIdProyecto());
            ps.executeUpdate();
            
            sql = "update cliente_proyecto set id_cliente=? where id_proyecto="+ab.getIdProyecto();
            ps = con.prepareStatement(sql);
            ps.setInt(1, ab.getId_cliente());
            ps.executeUpdate();
            
            con.commit();
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            try {
                con.rollback();
            }catch(SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
    }
    
    public void editar(AltaBean ab) {
        
        String fecha;
        SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
        
        try {
            
            
            String sql = "Update proyecto set proyecto=?, numContrato=?, tipoContrato=?, importeContto=?, formaDPago=?, anticipo=?, "
                    + "fechaInicio=?, fechaFin=?, cliente=?, pctGarantia=?, impGarantia=?, pctFianzaAntcpo=?, impFianzaAntcpo=?, "
                    + "pctCumplimiento=?, impCumplimiento=?, pctVicios=?, impVicios=?, pctRespCivil=?, impRespCivil=?, "
                    + "pctTerceros=?, impTerceros=?, otros=? where id_proyecto=?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ab.getProyecto());
            ps.setString(2, ab.getNumContrato());
            ps.setString(3, ab.getContrato());
            ps.setBigDecimal(4, ab.getImporteContto());
            ps.setString(5, ab.getFormaDPago());
            ps.setBigDecimal(6, ab.getAnticipo());
            fecha = sd.format(ab.getFechInicio());
            ps.setString(7, fecha);
            fecha = sd.format(ab.getFechFin());
            ps.setString(8, fecha);
            ps.setString(9, ab.getCliente());
            ps.setFloat(10, ab.getPctGarantia());
            ps.setBigDecimal(11, ab.getImpGarantia());
            ps.setFloat(12, ab.getPctFianzaAntcpo());
            ps.setBigDecimal(13, ab.getImpFianzaAntcpo());
            ps.setFloat(14, ab.getPctCumplimiento());
            ps.setBigDecimal(15, ab.getImpCumplimiento());
            ps.setFloat(16, ab.getPctVicios());
            ps.setBigDecimal(17, ab.getImpVicios());
            ps.setFloat(18, ab.getPctRespCivil());
            ps.setBigDecimal(19, ab.getImpRespCivil());
            ps.setFloat(20, ab.getPctTerceros());
            ps.setBigDecimal(21, ab.getImpTerceros());
            ps.setString(22, ab.getOtros());
            ps.setInt(23, ab.getIdProyecto());
            ps.executeUpdate();
            
            
            con.close();
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            
        }
        
    }
    
    public ArrayList<AltaBean> leerProyectos() {
        
        AltaBean ab;
        ArrayList<AltaBean> listaP = new ArrayList();
        
        SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
        Date fecha;
        String cad;
        
        try {
            
            String sql = "Select * from proyecto";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            
            while(rs.next()) {
                ab = new AltaBean();
                ab.setIdProyecto(rs.getInt("id_proyecto"));
                ab.setProyecto(rs.getString("proyecto"));
                ab.setNumContrato(rs.getString("numContrato"));
                ab.setContrato(rs.getString("tipoContrato"));
                ab.setImporteContto(rs.getBigDecimal("importeContto"));
                ab.setFormaDPago(rs.getString("formaDPago"));
                ab.setAnticipo(rs.getBigDecimal("anticipo"));
                if(ab.getAnticipo()==null) {
                    ab.setAnticipo(BigDecimal.ZERO);
                }
                
                ab.setFechInicio(sd.parse(rs.getString("fechaInicio")));
                fecha = sd.parse(rs.getString("fechaInicio"));
                cad = sd.format(fecha);
                ab.setFechaIniCad(cad);
                
                ab.setFechFin(sd.parse(rs.getString("fechaFin")));
                fecha = sd.parse(rs.getString("fechaFin"));
                cad = sd.format(fecha);
                ab.setFechaFinCad(cad);
                
                ab.setCliente(rs.getString("cliente"));
                ab.setPctGarantia(rs.getFloat("pctGarantia"));
                ab.setImpGarantia(rs.getBigDecimal("impGarantia"));
                if(ab.getImpGarantia()== null) {
                    ab.setImpGarantia(BigDecimal.ZERO);
                }
                ab.setPctFianzaAntcpo(rs.getFloat("pctFianzaAntcpo"));
                ab.setImpFianzaAntcpo(rs.getBigDecimal("impFianzaAntcpo"));
                if(ab.getImpFianzaAntcpo()== null) {
                    ab.setImpFianzaAntcpo(BigDecimal.ZERO);
                }
                ab.setPctCumplimiento(rs.getFloat("pctCumplimiento"));
                ab.setImpCumplimiento(rs.getBigDecimal("impCumplimiento"));
                if(ab.getImpCumplimiento() == null) {
                    ab.setImpCumplimiento(BigDecimal.ZERO);
                }
                ab.setPctVicios(rs.getFloat("pctVicios"));
                ab.setImpVicios(rs.getBigDecimal("impVicios"));
                if(ab.getImpVicios() == null) {
                    ab.setImpVicios(BigDecimal.ZERO);
                }
                ab.setPctRespCivil(rs.getFloat("pctRespCivil"));
                ab.setImpRespCivil(rs.getBigDecimal("impRespCivil"));
                if(ab.getImpRespCivil()== null) {
                    ab.setImpRespCivil(BigDecimal.ZERO);
                }
                ab.setPctTerceros(rs.getFloat("pctTerceros"));
                ab.setImpTerceros(rs.getBigDecimal("impTerceros"));
                if(ab.getImpTerceros()== null) {
                    ab.setImpTerceros(BigDecimal.ZERO);
                }
                ab.setOtros(rs.getString("otros"));
                ab.setOtrosRiesg(rs.getBigDecimal("otrosRiesgos"));
                if(ab.getOtrosRiesg()== null) {
                    ab.setOtrosRiesg(BigDecimal.ZERO);
                }
                ab.setId_cliente(obtenerIdCliente(ab.getCliente()));
                listaP.add(ab);
            }
            
            con.close();
            
        }catch(SQLException | ParseException e) {
            System.out.println(e.getMessage());
        }
        
        
        return listaP;
    }
    
    public int obtenerIdCliente(String cte) {
        
        int id = 0;
        
        try {
            
            String sql = "select id_cliente from cliente where razonSocial="+"\'"+cte+"\'";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                id = rs.getInt("id_cliente");
            }
            
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return id;
        
    }

    public void registrarProyecto(AltaBean ab, Direccion dir) {
        
        
        
        try {
            
            con.setAutoCommit(false);
            
            String sql = "Insert into proyecto (proyecto, numContrato, tipoContrato, importeContto, "
                    + "formaDPago, pctAnt, anticipo, fechaInicio, FechaFin, "
                    + "cliente, centroCostos, pctGarantia, impGarantia, pctFianzaAntcpo, impFianzaAntcpo, pctCumplimiento, "
                    + "impCumplimiento, pctVicios, impVicios, pctRespCivil, impRespCivil, pctTerceros, "
                    + "impTerceros, otros, otrosRiesgos) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            
            SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ab.getProyecto());
            //ps.setString(2, ab.getDireccion());
            ps.setString(2, ab.getNumContrato());
            ps.setString(3, ab.getContrato());
            ps.setBigDecimal(4, ab.getImporteContto());
            ps.setString(5, ab.getFormaDPago());
            ps.setBigDecimal(6, ab.getAnticipo());
            String fecha = sd.format(ab.getFechInicio());
            ps.setString(7, fecha);
            fecha = sd.format(ab.getFechFin());
            ps.setString(8, fecha);
            ps.setString(9, ab.getCliente());
            ps.setString(10, ab.getcCostos());
            ps.setFloat(11, ab.getPctGarantia());
            ps.setBigDecimal(12, ab.getImpGarantia());
            ps.setFloat(13, ab.getPctFianzaAntcpo());
            ps.setBigDecimal(14, ab.getImpFianzaAntcpo());
            ps.setFloat(15, ab.getPctCumplimiento());
            ps.setBigDecimal(16, ab.getImpCumplimiento());
            ps.setFloat(17, ab.getPctVicios());
            ps.setBigDecimal(18, ab.getImpVicios());
            ps.setFloat(19, ab.getPctRespCivil());
            ps.setBigDecimal(20, ab.getImpRespCivil());
            ps.setFloat(21, ab.getPctTerceros());
            ps.setBigDecimal(22, ab.getImpTerceros());
            ps.setString(23, ab.getOtros());
            ps.setBigDecimal(24, ab.getOtrosRiesg());
            ps.setFloat(25, ab.getPctAntcpo());
            ps.executeUpdate();
            
            String sql3 = "Select last_insert_id()";
            Statement s = con.createStatement();
            
            ResultSet rs = s.executeQuery(sql3);
            rs.first();
            int id = rs.getInt(1);
            System.out.println(id);
           
            String sql2 = "Insert into almacen (nombre, direccion, ciudad, telefono, noAlmacen, estatus, id_proyecto)values (?,?,?,?,?,?,?)";
            
            ps = con.prepareStatement(sql2);
            ps.setString(1, ab.getProyecto());
            //System.out.println(ab.getProyecto());
            ps.setString(2, dir.getDireccion());
            //System.out.println(dir.getDireccion());
            ps.setString(3, dir.getCiudad());
            //System.out.println(dir.getCiudad());
            ps.setString(4, dir.getTelefono());
            //System.out.println(dir.getTelefono());
            ps.setInt(5, 1);
            ps.setString(6, "1");
            ps.setInt(7, id);
            ps.executeUpdate();
            
            String sql4 = "insert into cliente_proyecto(id_cliente, id_proyecto)values (?,?)";
            ps = con.prepareStatement(sql4);
            ps.setInt(1, ab.getId_cliente());
            ps.setInt(2, id);
            ps.executeUpdate();
            
            con.commit();
            con.close();
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
    
    }
    
}
