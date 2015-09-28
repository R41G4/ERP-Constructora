/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bo;

import com.conexion.ConexionBD;
import com.dao.PresupuestoDAO;
import java.sql.Connection;


public class CrearCodigo {
    
    public String codificarConcepto(int proy, String clave, int id_pres) {
        
        String cod = "";
        String proyec = ""+proy;
        int lng = proyec.length();
        for(int i=lng; i<4; i++) {
            cod = cod+"0";
        }
        cod = cod+proyec;
        
        
        //String ch = ""+clave.charAt(ind1);
        int ind2 = clave.indexOf("-");
        String cve = clave.substring(0, ind2);
        cod = cod+"-"+cve+"-";
        //System.out.println(cod);
        
        ConexionBD c =  new ConexionBD();
        Connection con = c.getConexion();
        PresupuestoDAO pd = new PresupuestoDAO(con);
        String consecutivo = pd.busarConsecutivoConcepto(proy, id_pres);
        String conAux = "";
        for(int i = consecutivo.length(); i<4; i++) {
            conAux = conAux+"0";
        }
        
        cod = cod+conAux+consecutivo;
        System.out.println(cod);
        //System.out.println(cod);
        //System.out.println(lng);
        return cod;
    }
    
    public String crearConsecutivo(int id_proy, int id_pres) {
        
        ConexionBD c =  new ConexionBD();
        Connection con = c.getConexion();
        PresupuestoDAO pd = new PresupuestoDAO(con);
        String consecutivo = pd.busarConsecutivoConcepto(id_proy, id_pres);
        String conAux = "";
        for(int i = consecutivo.length(); i<4; i++) {
            conAux = conAux+"0";
        }
        conAux = conAux + consecutivo;
        
        
        return conAux;
        
    }
    
}
