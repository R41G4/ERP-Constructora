
package com.mbean;

import com.bean.PagoBean;
import com.bean.PresupuestoBean;
import com.bean.ProyectoSimple;
import com.bo.CanalizaPago;
import com.conexion.ConexionBD;
import com.dao.FacturaDAO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "factura")
@ViewScoped
public class FacturaMBean implements Serializable {
    
    public FacturaMBean() {
    }
    
    private int id_proyecto;
    private int id_presup;
    private String proyecto;
    private String presupuesto;
    private String tipoPago;
    private BigDecimal importeEst;
    private BigDecimal importeAcum;
    private BigDecimal importeRest;
    private String origen;
    
    private BigDecimal amortEstim;
    private BigDecimal gtiaEstim;
    private BigDecimal amortAcum;
    private BigDecimal amortRest;
    
    
    private List<ProyectoSimple> listProy = new ArrayList<ProyectoSimple>();
    private ProyectoSimple ps;
    
    private ArrayList<PresupuestoBean> listaPres = new ArrayList<PresupuestoBean>();
    private PresupuestoBean presB;
    
    private List<PagoBean> listPagos = new ArrayList<PagoBean>();
    private PagoBean pb;
    
    private int id_pago;
    private BigDecimal importe;
    private BigDecimal amorAntcpo;
    private String contratista;
    private String tipo;
    private BigDecimal iva;
    private BigDecimal rtnRenta;
    private BigDecimal rtnFlete;
    private BigDecimal total;
    private float pctRetGtia;
    private float pctAmort;
    private BigDecimal retFdoGtia;
    private BigDecimal acumGtia;
    private BigDecimal retenRest;
    
    private String noFactura;
    private BigDecimal subtotal;
    
    private String dateNow;
    
    private boolean factura = false;
    
    private int solicitudPago;
    
    public void registrarFactura() {
        //Si el importe de la factura + lo facturado es igual al importe origen, la amort del antcpo debe ser 
        //el monto por amortizar
        if((importeAcum.add(importe)).floatValue() >= importeEst.floatValue()) {
            
            //Validar el número de la factura para el proveedor 
            //Escribir factura, noFactura, importeFactura, antcpoFactura, contratista, origenFactura, tipoFactura, 
            //ivaFactura, rtnFlete, rtnRenta, rtnGarantia, totalFactura, idForaneo = id_pago
            Connection con;
            ConexionBD conexion = new ConexionBD();
            con = conexion.getConexion();
            FacturaDAO fd = new FacturaDAO(con);

            if(fd.validarFactura(noFactura, contratista)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error! ", "La factura ya fue registrada"));
            }else {
                PagoBean pb = new PagoBean();
                pb.setId_pago(id_pago);
                pb.setImporteEstimacion(importe);
                pb.setAnticipoAmort(amorAntcpo);

                pb.setRetFdoGtia(retFdoGtia);

                pb.setSubtotal(subtotal);
                pb.setIva(iva);
                pb.setRetFlete(rtnFlete);
                pb.setRetRenta(rtnRenta);
                pb.setImporteTotal(total);
                pb.setFecha(dateNow);
                pb.setContratista(contratista);
                pb.setTipo(tipo);
                pb.setNoFactura(noFactura);
                pb.setEstatusFact("PENDIENTE");
                pb.setOrigen(tipoPago);

                CanalizaPago canal = new CanalizaPago();
                setSolicitudPago(canal.ubicarPago(pb));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto!", "La solicitud registrada es: "+solicitudPago));
            }
            
        }else {
            //Escribir factura, noFactura, importeFactura, antcpoFactura, contratista, origenFactura, tipoFactura, 
            //ivaFactura, rtnFlete, rtnRenta, rtnGarantia, totalFactura, idForaneo = id_pago
            Connection con;
            ConexionBD conexion = new ConexionBD();
            con = conexion.getConexion();
            FacturaDAO fd = new FacturaDAO(con);
            
            if(fd.validarFactura(noFactura, contratista)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error! ", "La factura ya fue registrada"));
            }else {
                PagoBean pb = new PagoBean();
                pb.setId_pago(id_pago);
                pb.setImporteEstimacion(importe);
                pb.setAnticipoAmort(amorAntcpo);

                pb.setRetFdoGtia(retFdoGtia);

                pb.setSubtotal(subtotal);
                pb.setIva(iva);
                pb.setRetFlete(rtnFlete);
                pb.setRetRenta(rtnRenta);
                pb.setImporteTotal(total);
                pb.setFecha(dateNow);
                pb.setContratista(contratista);
                pb.setTipo(tipo);
                pb.setNoFactura(noFactura);
                pb.setEstatusFact("PENDIENTE");
                pb.setOrigen(tipoPago);

                CanalizaPago canal = new CanalizaPago();
                setSolicitudPago(canal.ubicarPago(pb));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto!", "La solicitud registrada es: "+solicitudPago));
            }    
        }
        
        
    }
    
    public void validarAmortizacion() {
        
        if(amorAntcpo.floatValue() > amortEstim.floatValue()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error! ", "La amortización no puede ser mayor al total por amortizar."));
            actualizarImpFactura();
        }
        else {
            float ivaVar = 0.16f;
            
            
            
            retFdoGtia = (importe.multiply(BigDecimal.valueOf(pctRetGtia/100))).setScale(2, RoundingMode.DOWN);
            subtotal = (importe.subtract(amorAntcpo)).subtract(retFdoGtia);
            iva = (subtotal.multiply(BigDecimal.valueOf(ivaVar))).setScale(2, RoundingMode.UP);
            if(rtnRenta.floatValue() > 0.00) {
                setRtnRenta(((iva.divide(BigDecimal.valueOf(3))).multiply(BigDecimal.valueOf(2))).setScale(2, RoundingMode.UP));
            }
            if(rtnFlete.floatValue() > 0.00) {
                setRtnFlete((importe.multiply(BigDecimal.valueOf(.04))).setScale(2, RoundingMode.UP));
            }
            setTotal((((((importe.subtract(amorAntcpo)).subtract(retFdoGtia)).add(iva)).subtract(rtnRenta)).subtract(rtnFlete)).setScale(2, RoundingMode.UP));
            setFactura(true);
        }
    }
    
    
    
    public void actualizarImpFactura() {
        
        //System.out.println(importe+"\t"+importeRest);
        
        if(importe.floatValue() <= importeRest.floatValue()) {
            float ivaVar = 0.16f;
            
            
            amorAntcpo = (importe.multiply(BigDecimal.valueOf(pctAmort/100)));
            retFdoGtia = (importe.multiply(BigDecimal.valueOf(pctRetGtia/100)));
            subtotal = (importe.subtract(amorAntcpo)).subtract(retFdoGtia);
            iva = (subtotal.multiply(BigDecimal.valueOf(ivaVar)));
            if(rtnRenta.floatValue() > 0.00) {
                float aux = iva.floatValue()/3;
                aux = aux*2;
                setRtnRenta(BigDecimal.valueOf(aux));
                //System.out.println(aux);
                //setRtnRenta(((iva.divide(BigDecimal.valueOf(3))).multiply(BigDecimal.valueOf(2))).setScale(2, RoundingMode.UP));
            }
            if(rtnFlete.floatValue() > 0.00) {
                float aux = iva.floatValue()*.04f;
                setRtnFlete(BigDecimal.valueOf(aux));
                //setRtnFlete((importe.multiply(BigDecimal.valueOf(.04))).setScale(2, RoundingMode.UP));
            }
            setTotal((((((importe.subtract(amorAntcpo)).subtract(retFdoGtia)).add(iva)).subtract(rtnRenta)).subtract(rtnFlete)));
            setFactura(true);
        }else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Importe Incorrecto"));
            setImporte(importeRest);
            actualizarImpFactura();
        }
        
        
    }
    
    public void obtenerRestante() {
        //System.out.println(importeEst+"\t"+importeAcum);
        setImporteRest((importeEst.subtract(importeAcum)).setScale(2, RoundingMode.UP));
        
        
        setImporte(importeRest);
    }
    
    public void acumuladoFactura() {
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        FacturaDAO fd = new FacturaDAO(con);
        //setImporteAcum(fd.sumarAcumulados(id_pago, tipo));
        BigDecimal[] acumulados = fd.sumarAcumulados(id_pago, tipo);
        setImporteAcum(acumulados[0].setScale(2, RoundingMode.UP));
        setAmortAcum(acumulados[1].setScale(2, RoundingMode.UP));
        setAcumGtia(acumulados[2].setScale(2, RoundingMode.UP));
        
    }
    
    public void generarFecha() {
        
        Date fecha = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        setDateNow(formato.format(fecha));
        
    }
    
    public void seleccionarPago() {
        
        if((tipoPago.equals("anticipo")) || (tipoPago.equals("estima"))) {
            setId_pago(pb.getId_pago());
            setImporte(pb.getImporteEstimacion().setScale(2, RoundingMode.UP));
            setImporteAcum(pb.getFacturado());
            //System.out.println(pb.getFacturado());
            setImporteEst(pb.getImporteEstimacion().setScale(2, RoundingMode.UP));
            setAmortEstim(pb.getAnticipoAmort().setScale(2, RoundingMode.UP));
            setPctAmort(pb.getPctAmort());
            setPctRetGtia(pb.getFondoGtiaPct());
            setAmorAntcpo(BigDecimal.valueOf(importe.floatValue()*getPctAmort()));
            setGtiaEstim(pb.getRetFdoGtia().setScale(2, RoundingMode.UP));
            setContratista(pb.getContratista());
            setTipo(pb.getTipo());
            setIva(pb.getIva().setScale(2, RoundingMode.UP));
            setRtnFlete(pb.getRetFlete().setScale(2, RoundingMode.UP));
            setRtnRenta(pb.getRetRenta().setScale(2, RoundingMode.UP));
            setTotal(pb.getImporteTotal().setScale(2, RoundingMode.UP));
            setFactura(factura);
            //acumuladoFactura();
            obtenerRestante();
            generarFecha();
            actualizarImpFactura();
            
        }
        
        if(tipoPago.equals("antOC")) {
            setId_pago(pb.getId_pago());
            setImporte(pb.getImporteEstimacion().setScale(2, RoundingMode.UP));
            setImporteAcum(BigDecimal.ZERO);
            //System.out.println(pb.getFacturado());
            setImporteEst(pb.getImporteEstimacion().setScale(2, RoundingMode.UP));
            setAmortEstim(BigDecimal.ZERO);
            setGtiaEstim(BigDecimal.ZERO);
            setContratista(pb.getContratista());
            setTipo(pb.getTipo());
            setIva(pb.getIva().setScale(2, RoundingMode.UP));
            setRtnFlete(BigDecimal.ZERO);
            setRtnRenta(BigDecimal.ZERO);
            setTotal(pb.getImporteTotal().setScale(2, RoundingMode.UP));
            //acumuladoFactura();
            obtenerRestante();
            generarFecha();
            actualizarImpFactura();
        }
        
        if(tipoPago.equals("compra")) {
            
            setId_pago(pb.getId_pago());
            setImporte(pb.getImporteEstimacion().setScale(2, RoundingMode.UP));
            setImporteAcum(pb.getFacturado());
            //System.out.println(pb.getFacturado());
            setImporteEst(pb.getImporteEstimacion().setScale(2, RoundingMode.UP));
            ConexionBD c = new ConexionBD();
            Connection con = c.getConexion();
            FacturaDAO f = new FacturaDAO(con);
            setPctAmort(f.obtenerPctAmort(pb.getId_pago()));
            
            setGtiaEstim(BigDecimal.ZERO);
            setContratista(pb.getContratista());
            setTipo(pb.getTipo());
            setIva(pb.getIva().setScale(2, RoundingMode.UP));
            setRtnFlete(BigDecimal.ZERO);
            setRtnRenta(BigDecimal.ZERO);
            setTotal(pb.getImporteTotal().setScale(2, RoundingMode.UP));
            //acumuladoFactura();
            obtenerRestante();
            generarFecha();
            actualizarImpFactura();
        }
        
        
    }
    
    public void buscarPagos() {
        
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        FacturaDAO fd = new FacturaDAO(con);
        
        if(tipoPago.equals("anticipo")) {
            setListPagos(fd.listarAnticipos(id_proyecto, id_presup));
        }
        if(tipoPago.equals("estima")) {
            setListPagos(fd.listarAvancesEstimados(id_proyecto, id_presup));
        }
        if(tipoPago.equals("compra")) {
            //setListPagos(fd.listarAvancesEstimados(id_proyecto, id_presup));
            setListPagos(fd.listarOrdenesCompra(id_presup));
        }
        if(tipoPago.equals("antOC")) {
            
            setListPagos(fd.listarOrdCompra(id_proyecto, id_presup));
        }
        
    }
    
    public void obtenerPresupuesto() {
        setId_presup(presB.getId_presupuesto());
        setPresupuesto(presB.getPresupuesto());
        
    }
    
    public void buscarPresupuesto() {
        
        if(ps != null) {
            Connection con;
            ConexionBD conexion = new ConexionBD();
            con = conexion.getConexion();
            FacturaDAO fd = new FacturaDAO(con);
            
            setListaPres(fd.listarPresupuesto(ps.getId_proyecto()));
            //System.out.println(listaPres.size());
        }
        
    }
    
    public void obtenerProyecto() {
        setId_proyecto(ps.getId_proyecto());
        setProyecto(ps.getProyecto());
        buscarPresupuesto();
    }
    
    public void consultarProyecto() {
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        FacturaDAO fd = new FacturaDAO(con);
        setListProy(fd.listarProyecto());
    }

    public int getId_proyecto() {
        return id_proyecto;
    }

    public void setId_proyecto(int id_proyecto) {
        this.id_proyecto = id_proyecto;
    }

    public int getId_presup() {
        return id_presup;
    }

    public void setId_presup(int id_presup) {
        this.id_presup = id_presup;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public String getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(String presupuesto) {
        this.presupuesto = presupuesto;
    }

    public List<ProyectoSimple> getListProy() {
        consultarProyecto();
        return listProy;
    }

    public void setListProy(List<ProyectoSimple> listProy) {
        this.listProy = listProy;
    }

    public ProyectoSimple getPs() {
        return ps;
    }

    public void setPs(ProyectoSimple ps) {
        this.ps = ps;
    }

    public ArrayList<PresupuestoBean> getListaPres() {
        return listaPres;
    }

    public void setListaPres(ArrayList<PresupuestoBean> listaPres) {
        this.listaPres = listaPres;
    }

    public PresupuestoBean getPresB() {
        return presB;
    }

    public void setPresB(PresupuestoBean presB) {
        this.presB = presB;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    

    

    public int getId_pago() {
        return id_pago;
    }

    public void setId_pago(int id_pago) {
        this.id_pago = id_pago;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public BigDecimal getAmorAntcpo() {
        return amorAntcpo;
    }

    public void setAmorAntcpo(BigDecimal amorAntcpo) {
        this.amorAntcpo = amorAntcpo;
    }

    public String getContratista() {
        return contratista;
    }

    public void setContratista(String contratista) {
        this.contratista = contratista;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getRtnRenta() {
        return rtnRenta;
    }

    public void setRtnRenta(BigDecimal rtnRenta) {
        this.rtnRenta = rtnRenta;
    }

    public BigDecimal getRtnFlete() {
        return rtnFlete;
    }

    public void setRtnFlete(BigDecimal rtnFlete) {
        this.rtnFlete = rtnFlete;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getNoFactura() {
        return noFactura;
    }

    public void setNoFactura(String noFactura) {
        this.noFactura = noFactura;
    }

    public BigDecimal getImporteAcum() {
        return importeAcum;
    }

    public void setImporteAcum(BigDecimal importeAcum) {
        this.importeAcum = importeAcum;
    }

    public BigDecimal getImporteRest() {
        return importeRest;
    }

    public void setImporteRest(BigDecimal importeRest) {
        this.importeRest = importeRest;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public List<PagoBean> getListPagos() {
        return listPagos;
    }

    public void setListPagos(List<PagoBean> listPagos) {
        this.listPagos = listPagos;
    }

    public PagoBean getPb() {
        return pb;
    }

    public void setPb(PagoBean pb) {
        this.pb = pb;
    }

    public BigDecimal getAmortAcum() {
        return amortAcum;
    }

    public void setAmortAcum(BigDecimal amortAcum) {
        this.amortAcum = amortAcum;
    }

    public BigDecimal getAmortRest() {
        return amortRest;
    }

    public void setAmortRest(BigDecimal amortRest) {
        this.amortRest = amortRest;
    }

    public BigDecimal getImporteEst() {
        return importeEst;
    }

    public void setImporteEst(BigDecimal importeEst) {
        this.importeEst = importeEst;
    }

    public float getPctRetGtia() {
        return pctRetGtia;
    }

    public void setPctRetGtia(float pctRetGtia) {
        this.pctRetGtia = pctRetGtia;
    }

    public BigDecimal getRetFdoGtia() {
        return retFdoGtia;
    }

    public void setRetFdoGtia(BigDecimal retFdoGtia) {
        this.retFdoGtia = retFdoGtia;
    }

    public BigDecimal getRetenRest() {
        return retenRest;
    }

    public void setRetenRest(BigDecimal retenRest) {
        this.retenRest = retenRest;
    }

    public BigDecimal getAcumGtia() {
        return acumGtia;
    }

    public void setAcumGtia(BigDecimal acumGtia) {
        this.acumGtia = acumGtia;
    }

    public float getPctAmort() {
        return pctAmort;
    }

    public void setPctAmort(float pctAmort) {
        this.pctAmort = pctAmort;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public String getDateNow() {
        return dateNow;
    }

    public void setDateNow(String dateNow) {
        this.dateNow = dateNow;
    }

    public BigDecimal getAmortEstim() {
        return amortEstim;
    }

    public void setAmortEstim(BigDecimal amortEstim) {
        this.amortEstim = amortEstim;
    }

    public BigDecimal getGtiaEstim() {
        return gtiaEstim;
    }

    public void setGtiaEstim(BigDecimal gtiaEstim) {
        this.gtiaEstim = gtiaEstim;
    }

    public boolean isFactura() {
        return factura;
    }

    public void setFactura(boolean factura) {
        this.factura = factura;
    }

    public int getSolicitudPago() {
        return solicitudPago;
    }

    public void setSolicitudPago(int solicitudPago) {
        this.solicitudPago = solicitudPago;
    }
    
}
