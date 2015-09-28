/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.bo.Cantidades;
import com.conexion.ConexionBD;
import com.services.OrdenCompraBS;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Mickey
 */
@ManagedBean
@ViewScoped
public class AutorizacionOrdenCompraMB implements Serializable {

    private List<FormOrdenCompraMB> listOrdenCompra;
    private List<FormOrdenCompraMB> selectedOrdenCompra;
    private List<DetalleOrdenCompraMB> detallesOrdenCompra;
    private BigDecimal sumaDet;
    
    
    public AutorizacionOrdenCompraMB() {
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        listOrdenCompra = new OrdenCompraBS(con).listarOrdenesCompra();
        detallesOrdenCompra = new ArrayList<>();
    }
    
    public void listarDetalleOrdenCompra(int idOrdenCompra){
        detallesOrdenCompra.clear();
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        OrdenCompraBS ocbs = new OrdenCompraBS(con);
        detallesOrdenCompra =  ocbs.detallesOrdenCompra(idOrdenCompra);
        Cantidades cant = new Cantidades();
        setSumaDet(cant.sumarDetalleOrdenCompra(detallesOrdenCompra));
        System.out.println("Tamaño del detalle OC: " + detallesOrdenCompra.size());
    }
    
    public void autorizarOrdenCompra(){
       
        List <Integer> list = new ArrayList<Integer>();
        System.out.println("Ordenes de compra seleccionadas: " + selectedOrdenCompra.size());
        for(FormOrdenCompraMB aux: selectedOrdenCompra){
            list.add(aux.getNoOrdenCompra());
        }
        if(list.isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "AVISO", "Debe seleccionar al menos una Orden de Compra"));
            return;
        }
        
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        OrdenCompraBS ocbs = new OrdenCompraBS(con);
        boolean resultado = ocbs.autorizacionOrdenCompra(list, 2);
        if (resultado) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación Finalizada ", "Se han Autorizado correctamente las Ordenes de compra seleccionadas"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error del Sistema ", "No se puede procesar la petición"));
        }
        
        listOrdenCompra = ocbs.listarOrdenesCompra();
        selectedOrdenCompra.clear();
    }

    /**
     * @return the listOrdenCompra
     */
    public List<FormOrdenCompraMB> getListOrdenCompra() {
        return listOrdenCompra;
    }

    /**
     * @param listOrdenCompra the listOrdenCompra to set
     */
    public void setListOrdenCompra(List<FormOrdenCompraMB> listOrdenCompra) {
        this.listOrdenCompra = listOrdenCompra;
    }

    /**
     * @return the selectedOrdenCompra
     */
    public List<FormOrdenCompraMB> getSelectedOrdenCompra() {
        return selectedOrdenCompra;
    }

    /**
     * @param selectedOrdenCompra the selectedOrdenCompra to set
     */
    public void setSelectedOrdenCompra(List<FormOrdenCompraMB> selectedOrdenCompra) {
        this.selectedOrdenCompra = selectedOrdenCompra;
    }

    /**
     * @return the detallesOrdenCompra
     */
    public List<DetalleOrdenCompraMB> getDetallesOrdenCompra() {
        return detallesOrdenCompra;
    }

    /**
     * @param detallesOrdenCompra the detallesOrdenCompra to set
     */
    public void setDetallesOrdenCompra(List<DetalleOrdenCompraMB> detallesOrdenCompra) {
        this.detallesOrdenCompra = detallesOrdenCompra;
    }

    /**
     * @return the sumaDet
     */
    public BigDecimal getSumaDet() {
        return sumaDet;
    }

    /**
     * @param sumaDet the sumaDet to set
     */
    public void setSumaDet(BigDecimal sumaDet) {
        this.sumaDet = sumaDet;
    }
    
}
