/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.conexion.ConexionBD;
import com.model.Proveedor;
import com.services.ProveedoresBS;
import java.sql.Connection;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;


@ManagedBean
@RequestScoped
public class ProveedoresMB {

    @ManagedProperty(value = "#{formProveedorMB}")
    private FormProveedorMB formProveedor;
    
    public ProveedoresMB() {
    }
    
    public void nuevoProveedor() {
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        ProveedoresBS pbs = new ProveedoresBS(con);
        Proveedor p = new Proveedor();
        p.setCiudad(formProveedor.getCiudad());
        p.setClabe(formProveedor.getClabe());
        p.setDiasCredito(formProveedor.getDiasCredito());
        p.setDirFiscal(formProveedor.getDirFiscal());
        p.setEmail(formProveedor.getEmail());
        p.setEstatus(1);
        p.setFax(formProveedor.getFax());
        p.setNoAba(formProveedor.getNoAba());
        p.setNoCuenta(formProveedor.getNoCuenta());
        p.setNoCuentaInterbancaria(formProveedor.getNoCuentaInterbancaria());
        p.setNombreBanco(formProveedor.getNombreBanco());
        p.setNombreContacto(formProveedor.getNombreContacto());
        p.setNumNomPlaza(formProveedor.getNumNomPlaza());
        p.setNumNomSucursal(formProveedor.getNumNomSucursal());
        p.setRazonSocial(formProveedor.getRazonSocial());
        p.setRfc(formProveedor.getRfc());
        p.setTelefono(formProveedor.getTelefono());
        int idPro = pbs.insertarProveedor(p);
        if (idPro != 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OPERACIÓN CORRECTA ", "Se ha insertado Correctamente el Proveedor. \nIdentificador : " + idPro));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "No se puede procesar la petición intente más tarde. "));
        }
    }

    public String editarProveedor(int idProveedor) {
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        ProveedoresBS p = new ProveedoresBS(con);
        Proveedor proveedor = p.listaProveedorPorID(idProveedor);
        System.out.println("Pro: " + proveedor);

        formProveedor.setCiudad(proveedor.getCiudad());
        formProveedor.setClabe(proveedor.getClabe());
        formProveedor.setDiasCredito(proveedor.getDiasCredito());
        formProveedor.setDirFiscal(proveedor.getDirFiscal());
        formProveedor.setEmail(proveedor.getEmail());
        formProveedor.setEstatus(proveedor.getEstatus());
        formProveedor.setFax(proveedor.getFax());
        formProveedor.setIdProveedor(proveedor.getIdProveedor());
        formProveedor.setNoAba(proveedor.getNoAba());
        formProveedor.setNoCuenta(proveedor.getNoCuenta());
        formProveedor.setNoCuentaInterbancaria(proveedor.getNoCuentaInterbancaria());
        formProveedor.setNombreBanco(proveedor.getNombreBanco());
        formProveedor.setNombreContacto(proveedor.getNombreContacto());
        formProveedor.setNumNomPlaza(proveedor.getNumNomPlaza());
        formProveedor.setNumNomSucursal(proveedor.getNumNomSucursal());
        formProveedor.setRazonSocial(proveedor.getRazonSocial());
        formProveedor.setRfc(proveedor.getRfc().toUpperCase());
        formProveedor.setTelefono(proveedor.getTelefono());

        return "editarProveedor";

    }

    public void actualizar() {
        System.out.println("Entro-.......");
        System.out.println("desde MB id: " + formProveedor.getIdProveedor());

        System.out.println("desde MB RFC: " + formProveedor.getRfc());
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        ProveedoresBS pbs = new ProveedoresBS(con);
        Proveedor p = new Proveedor();
        p.setCiudad(formProveedor.getCiudad());
        p.setIdProveedor(formProveedor.getIdProveedor());
        p.setClabe(formProveedor.getClabe());
        p.setDiasCredito(formProveedor.getDiasCredito());
        p.setDirFiscal(formProveedor.getDirFiscal());
        p.setEmail(formProveedor.getEmail());
        p.setEstatus(1);
        p.setFax(formProveedor.getFax());
        p.setNoAba(formProveedor.getNoAba());
        p.setNoCuenta(formProveedor.getNoCuenta());
        p.setNoCuentaInterbancaria(formProveedor.getNoCuentaInterbancaria());
        p.setNombreBanco(formProveedor.getNombreBanco());
        p.setNombreContacto(formProveedor.getNombreContacto());
        p.setNumNomPlaza(formProveedor.getNumNomPlaza());
        p.setNumNomSucursal(formProveedor.getNumNomSucursal());
        p.setRazonSocial(formProveedor.getRazonSocial());
        p.setRfc(formProveedor.getRfc());
        p.setTelefono(formProveedor.getTelefono());

        System.out.println("idMNPROV: " + p.getIdProveedor());
        System.out.println("RazZOO: " + p.getRazonSocial());
        boolean res = pbs.actualizarProveedor(p);
        if (res) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OPERACIÓN CORRECTA ", "Se ha Actualizado Correctamente el Proveedor."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "No se puede procesar la petición intente más tarde. "));
        }
    }

    /**
     * @return the formProveedor
     */
    public FormProveedorMB getFormProveedor() {
        return formProveedor;
    }

    /**
     * @param formProveedor the formProveedor to set
     */
    public void setFormProveedor(FormProveedorMB formProveedor) {
        this.formProveedor = formProveedor;
    }
    
}
