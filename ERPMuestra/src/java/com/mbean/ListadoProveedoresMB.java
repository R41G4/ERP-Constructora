/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.conexion.ConexionBD;
import com.services.ProveedoresBS;
import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Mickey
 */
@ManagedBean
@RequestScoped
public class ListadoProveedoresMB implements Serializable {

    private List<FormListProveedoresMB> listaProveedores;
    private List<FormListProveedoresMB> selectedListaProveedores;
    
    public ListadoProveedoresMB() {
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        ProveedoresBS p = new ProveedoresBS(con);
        listaProveedores = p.listarProveedores();
    }

    /**
     * @return the listaProveedores
     */
    public List<FormListProveedoresMB> getListaProveedores() {
        return listaProveedores;
    }

    /**
     * @param listaProveedores the listaProveedores to set
     */
    public void setListaProveedores(List<FormListProveedoresMB> listaProveedores) {
        this.listaProveedores = listaProveedores;
    }

    /**
     * @return the selectedListaProveedores
     */
    public List<FormListProveedoresMB> getSelectedListaProveedores() {
        return selectedListaProveedores;
    }

    /**
     * @param selectedListaProveedores the selectedListaProveedores to set
     */
    public void setSelectedListaProveedores(List<FormListProveedoresMB> selectedListaProveedores) {
        this.selectedListaProveedores = selectedListaProveedores;
    }
    
}
