/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.conexion.ConexionBD;
import com.model.InsumoRequisicion;
import com.services.RequisicionBS;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Mickey
 */
@ManagedBean
@ViewScoped
public class AutorizacionRequicisionMB implements Serializable {

    @ManagedProperty(value = "#{formProyectosMB}")
    private FormProyectosMB formProyectosMB;
    
    @ManagedProperty(value = "#{formRequisicionMB}")
    private FormRequisicionMB formRequisicionMB;
    
    private List<FormTablaRequisicionMB> selectedListRequisicion;
    private List<FormTablaRequisicionMB> listaRequisicion;
    private List<DetalleRequisicionTablaMB> detallesRequisicion;
    
    private int idReqSelected;

    
    public AutorizacionRequicisionMB() {
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        RequisicionBS rbs = new RequisicionBS(con);
        listaRequisicion = rbs.listarRequisiciones();
        detallesRequisicion = new ArrayList<>();
        selectedListRequisicion = new ArrayList<>();
    }
    
    public void listarDetallesRequisicion(int idRequisicion) {
        
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        RequisicionBS rbs = new RequisicionBS(con);
        List<InsumoRequisicion> list = rbs.detalleRequisicion(idRequisicion);
        detallesRequisicion.clear();
        System.out.println("Tamaño de insumos: " + list.size());
        for (InsumoRequisicion aux : list) {
            DetalleRequisicionTablaMB detalleInsumo = new DetalleRequisicionTablaMB();
            detalleInsumo.setCantidadSolicitada(aux.getCantidad());
            detalleInsumo.setIdInsumo(aux.getIdinsumoRequisicion());
            detalleInsumo.setIdRequisicion(aux.getRequisicion().getNoRequisicion());
            detalleInsumo.setNombreInsumo(aux.getExpInsumos().getDescripcion());
            detalleInsumo.setUnidades(aux.getExpInsumos().getUnidades());
            detalleInsumo.setClaveInsumo( aux.getExpInsumos().getCodInsumo());
            detallesRequisicion.add(detalleInsumo);
            idReqSelected = aux.getRequisicion().getNoRequisicion();
        }
        
    }
    
    public void autorizarRequisiciones() {
        List<Integer> list = new ArrayList<>();
        System.out.println("Requisiciones Seleccionadas: " + selectedListRequisicion.size());
        for (FormTablaRequisicionMB aux : selectedListRequisicion) {
            list.add(aux.getIdRequicision());
        }
        
        if(list.isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "AVISO", "Debe seleccionar al menos una Requisicion"));
            return;
        }
        
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        RequisicionBS rbs = new RequisicionBS(con);
        boolean resultado = rbs.autorizacionRequisicion(list, 2);
        if (resultado) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación Finalizada ", "Se han Autorizado correctamente las Requisiciones seleccionadas"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error del Sistema ", "No se puede procesar la petición"));
        }
        
        listaRequisicion = rbs.listarRequisiciones();
        selectedListRequisicion.clear();
    }
    
    public void cancelarRequisiciones() {
        List<Integer> list = new ArrayList<Integer>();
        System.out.println("Requisiciones Seleccionadas: " + selectedListRequisicion.size());
        for (FormTablaRequisicionMB aux : selectedListRequisicion) {
                list.add(aux.getIdRequicision());
        }
        
        if(list.isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "AVISO", "Debe seleccionar al menos una Requisicion"));
            return;
        }
        
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        RequisicionBS rbs = new RequisicionBS(con);
        boolean resultado = rbs.cancelarRequisicion(list);
        if (resultado) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación Finalizada ", "Se han Cancelado correctamente las Requisiciones seleccionadas"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error del Sistema ", "No se puede procesar la petición"));
        }
        listaRequisicion = rbs.listarRequisiciones();
        selectedListRequisicion.clear();
        
    }

    /**
     * @return the formProyectosMB
     */
    public FormProyectosMB getFormProyectosMB() {
        return formProyectosMB;
    }

    /**
     * @param formProyectosMB the formProyectosMB to set
     */
    public void setFormProyectosMB(FormProyectosMB formProyectosMB) {
        this.formProyectosMB = formProyectosMB;
    }

    /**
     * @return the formRequisicionMB
     */
    public FormRequisicionMB getFormRequisicionMB() {
        return formRequisicionMB;
    }

    /**
     * @param formRequisicionMB the formRequisicionMB to set
     */
    public void setFormRequisicionMB(FormRequisicionMB formRequisicionMB) {
        this.formRequisicionMB = formRequisicionMB;
    }

    /**
     * @return the selectedListRequisicion
     */
    public List<FormTablaRequisicionMB> getSelectedListRequisicion() {
        return selectedListRequisicion;
    }

    /**
     * @param selectedListRequisicion the selectedListRequisicion to set
     */
    public void setSelectedListRequisicion(List<FormTablaRequisicionMB> selectedListRequisicion) {
        this.selectedListRequisicion = selectedListRequisicion;
    }

    /**
     * @return the listaRequisicion
     */
    public List<FormTablaRequisicionMB> getListaRequisicion() {
        return listaRequisicion;
    }

    /**
     * @param listaRequisicion the listaRequisicion to set
     */
    public void setListaRequisicion(List<FormTablaRequisicionMB> listaRequisicion) {
        this.listaRequisicion = listaRequisicion;
    }

    /**
     * @return the detallesRequisicion
     */
    public List<DetalleRequisicionTablaMB> getDetallesRequisicion() {
        return detallesRequisicion;
    }

    /**
     * @param detallesRequisicion the detallesRequisicion to set
     */
    public void setDetallesRequisicion(List<DetalleRequisicionTablaMB> detallesRequisicion) {
        this.detallesRequisicion = detallesRequisicion;
    }

    /**
     * @return the idReqSelected
     */
    public int getIdReqSelected() {
        return idReqSelected;
    }

    /**
     * @param idReqSelected the idReqSelected to set
     */
    public void setIdReqSelected(int idReqSelected) {
        this.idReqSelected = idReqSelected;
    }
    
}
