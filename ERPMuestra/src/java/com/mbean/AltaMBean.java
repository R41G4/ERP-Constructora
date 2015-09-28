/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.bean.AltaBean;
import com.bean.Direccion;
import com.bean.ProyectoBean;
import com.conexion.ConexionBD;
import com.dao.AltaDAO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


@ManagedBean
@ViewScoped
public class AltaMBean implements Serializable {

    
    public AltaMBean() {
    }
    
    private int idProyecto;
    private String proyecto;
    private String direccion;
    private String contrato;
    private String numContrato;
    private BigDecimal importeContto;
    private String importeFormat;
    private String formaDPago;
    private float pctAntcpo;
    private BigDecimal anticipo;
    private String cadAntcpo;
    private Date fechInicio;
    private Date fechFin;
    private String cliente;
    private float pctGarantia;
    private BigDecimal impGarantia;
    private String garantia;
    private float pctFianzaAntcpo;
    private BigDecimal impFianzaAntcpo;
    private String fianzaAntcpo;
    private float pctCumplimiento;
    private BigDecimal impCumplimiento;
    private String cumplimiento;
    private float pctVicios;
    private BigDecimal impVicios;
    private String vicios;
    private float pctRespCivil;
    private BigDecimal impRespCivil;
    private String respCivil;
    private float pctTerceros;
    private BigDecimal impTerceros;
    private String terceros;
    private BigDecimal otrosRiesg;
    private String otros;
    private String centroC;
    
    private String fechIniCad;
    private String fechFinCad;
    
    private ArrayList<AltaBean> listaProyectos = new ArrayList<AltaBean>();
    private ProyectoBean proySelect;
    private AltaBean proySelec;
    
    private BigDecimal sumaFianzas;
    private String sumaFianzasCad;
    private BigDecimal sumaRiesgos;
    private String sumaRiesCad;
    
    private String serie;
    private String yearCntrt;
    private String monthCntr;
    
    //Datos para dirección
    private String calle;
    private String numExt;
    
    private String colonia;
    private String codPst;
    private String ciudad;
    private String telefono;
    
    
    public void generarCosto() {
        setCentroC(serie+yearCntrt+monthCntr);
    } 
    
    public void sumarRiesgos() {
        
        if(impRespCivil != null && impTerceros != null) {
            sumaRiesgos = impRespCivil.add(impTerceros);
            DecimalFormat formato = new DecimalFormat("###,###,###.##");
            setSumaRiesCad(formato.format(sumaRiesgos));
        }
            
    }
    
    public void sumarFianzas() {
        
        if(impFianzaAntcpo != null && impCumplimiento != null && impVicios != null) {
            sumaFianzas = (impFianzaAntcpo.add(impCumplimiento)).add(impVicios);
            DecimalFormat formato = new DecimalFormat("###,###,###.##");
            setSumaFianzasCad(formato.format(sumaFianzas));
        }
        
    }
    
    public void guardarCambios() {
        
        proySelec.setIdProyecto(getIdProyecto());
        proySelec.setProyecto(getProyecto());
        proySelec.setContrato(getContrato());
        proySelec.setNumContrato(getNumContrato());
        proySelec.setImporteContto(getImporteContto());
        proySelec.setFormaDPago(getFormaDPago());
        proySelec.setPctAntcpo(getPctAntcpo());
        proySelec.setAnticipo(getAnticipo());
        proySelec.setFechInicio(getFechInicio());
        proySelec.setFechaIniCad(getFechIniCad());
        proySelec.setFechFin(getFechFin());
        proySelec.setFechaFinCad(getFechFinCad());
        proySelec.setCliente(getCliente());
        proySelec.setPctGarantia(getPctGarantia());
        proySelec.setImpGarantia(getImpGarantia());
        proySelec.setPctFianzaAntcpo(getPctFianzaAntcpo());
        proySelec.setImpFianzaAntcpo(getImpFianzaAntcpo());
        proySelec.setPctCumplimiento(getPctCumplimiento());
        proySelec.setImpCumplimiento(getImpCumplimiento());
        proySelec.setPctVicios(getPctVicios());
        proySelec.setImpVicios(getImpVicios());
        proySelec.setPctRespCivil(getPctRespCivil());
        proySelec.setImpRespCivil(getImpRespCivil());
        proySelec.setPctTerceros(getPctTerceros());
        proySelec.setImpTerceros(getImpTerceros());
        proySelec.setOtros(getOtros());
        
        ConexionBD conexion = new ConexionBD();
        Connection con = conexion.getConexion();
        AltaDAO aDao = new AltaDAO(con);
        aDao.editar(proySelec);
        
        
    } 
    
    public void editarPorcentajes(float pct, int id) {
        //Recibe el porcentaje y calcula el nuevo importe segun el identificador
        
        float importeFl = proySelec.getImporteContto().floatValue();
        float importe;
        DecimalFormat formato = new DecimalFormat("###,###,###.##");
        
        //Anticipo
        if(id == 1) {
            importe = (pct/100)*importeFl;
            setAnticipo(BigDecimal.valueOf(importe));
            setCadAntcpo("$" + " " + formato.format(getAnticipo()));
        }
        if(id == 2) {
            importe = (pct/100)*importeFl;
            setImpGarantia(BigDecimal.valueOf(importe));
            setGarantia("$" + " " + formato.format(getImpGarantia()));
        }
        if(id == 3) {
            importe = (pct/100)*importeFl;
            setImpFianzaAntcpo(BigDecimal.valueOf(importe));
            setFianzaAntcpo("$" + " " + formato.format(getImpFianzaAntcpo()));
        }
        if(id == 4) {
            importe = (pct/100)*importeFl;
            setImpCumplimiento(BigDecimal.valueOf(importe));
            setCumplimiento("$" + " " + formato.format(getImpCumplimiento()));
        }
        if(id == 5) {
            importe = (pct/100)*importeFl;
            setImpVicios(BigDecimal.valueOf(importe));
            setVicios("$" + " " + formato.format(getImpVicios()));
        }
        if(id == 6) {
            importe = (pct/100)*importeFl;
            setImpRespCivil(BigDecimal.valueOf(importe));
            setRespCivil("$" + " " + formato.format(getImpRespCivil()));
        }
        if(id == 7) {
            importe = (pct/100)*importeFl;
            setImpTerceros(BigDecimal.valueOf(importe));
            setTerceros("$" + " " + formato.format(getImpTerceros()));
        }
    }
    
    public void seleccionarProyecto() {
        //System.out.println(proySelec.getIdProyecto());
        float antcpoFl = proySelec.getAnticipo().floatValue();
        //System.out.println(antcpoFl);
        float importFl = proySelec.getImporteContto().floatValue();
        
        setIdProyecto(proySelec.getIdProyecto());
        
        setProyecto(proySelec.getProyecto());
        setContrato(proySelec.getContrato());
        setNumContrato(proySelec.getNumContrato());
        setImporteContto(proySelec.getImporteContto());
        DecimalFormat formato = new DecimalFormat("###,###,###.##");
        setImporteFormat("$" + " " + formato.format(getImporteContto()));
        setFormaDPago(proySelec.getFormaDPago());
        float res = (antcpoFl*100)/importFl;
        res = Math.round(res); 
        setPctAntcpo(res);
        setAnticipo(proySelec.getAnticipo());
        setCadAntcpo("$" + " " + formato.format(getAnticipo()));
        setFechInicio(proySelec.getFechInicio());
        setFechFin(proySelec.getFechFin());
        setFechIniCad(proySelec.getFechaIniCad());
        setFechFinCad(proySelec.getFechaFinCad());
        setCliente(proySelec.getCliente());
        setPctGarantia(proySelec.getPctGarantia());
        setImpGarantia(proySelec.getImpGarantia());
        setGarantia("$" + " " + formato.format(getImpGarantia()));
        setPctFianzaAntcpo(proySelec.getPctFianzaAntcpo());
        setImpFianzaAntcpo(proySelec.getImpFianzaAntcpo());
        setFianzaAntcpo("$" + " " + formato.format(getImpFianzaAntcpo()));
        setPctCumplimiento(proySelec.getPctCumplimiento());
        setImpCumplimiento(proySelec.getImpCumplimiento());
        setCumplimiento("$" + " " + formato.format(getImpCumplimiento()));
        setPctVicios(proySelec.getPctVicios());
        setImpVicios(proySelec.getImpVicios());
        setVicios("$" + " " + formato.format(getImpVicios()));
        setPctRespCivil(proySelec.getPctRespCivil());
        setImpRespCivil(proySelec.getImpRespCivil());
        setRespCivil("$" + " " + formato.format(getImpRespCivil()));
        setPctTerceros(proySelec.getPctTerceros());
        setImpTerceros(proySelec.getImpTerceros());
        setTerceros("$" + " " + formato.format(getImpTerceros()));
        setOtros(proySelec.getOtros());
        setOtrosRiesg(proySelec.getOtrosRiesg());
        sumarFianzas();
        sumarRiesgos();
    }
    
    public String irAProyectos() {
        
        System.out.println("listar");
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        AltaDAO aDAO =  new AltaDAO(con);
        setListaProyectos(aDAO.leerProyectos());
        
        return "listaProyectos.xhtml";
    }
    
    public void enlistarProyectos() {
        
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        AltaDAO aDAO =  new AltaDAO(con);
        setListaProyectos(aDAO.leerProyectos());
        
        
    }
    
    public void calcularAntcpo() {
        
        float importeF = getImporteContto().floatValue();
        
        if(importeF > 0) {
            
            float porcentaje = getPctAntcpo()/100;
            anticipo = BigDecimal.valueOf(importeF*porcentaje);
            DecimalFormat formato = new DecimalFormat("###,###,###.##");
            cadAntcpo = "$" + " " + formato.format(anticipo);
        }
         
    }
    
    public void calculaPorcentajes() {
        
        DecimalFormat formato = new DecimalFormat("###,###,###.##");
        importeFormat = "$"+ " " + formato.format(getImporteContto());
        
        
        float importeFl = importeContto.floatValue();
        
        if((getPctGarantia()> 0) && (importeFl > 0)) {
            float porcentaje = getPctGarantia()/100;
            impGarantia = BigDecimal.valueOf(importeFl*porcentaje);
            setGarantia(formato.format(impGarantia));
        }
        if((getPctFianzaAntcpo()>0) && (importeFl > 0)) {
            float porcentaje = getPctFianzaAntcpo()/100;
            impFianzaAntcpo = BigDecimal.valueOf(importeFl*porcentaje);
            setFianzaAntcpo(formato.format(impFianzaAntcpo));
            sumarFianzas();
        }
        if((getPctCumplimiento()>0) && (importeFl > 0)) {
            float porcentaje = getPctCumplimiento()/100;
            impCumplimiento = BigDecimal.valueOf(importeFl*porcentaje);
            setCumplimiento(formato.format(impCumplimiento));
            sumarFianzas();
        }
        if((getPctVicios()>0) && (importeFl > 0)) {
            float porcentaje = getPctVicios()/100;
            impVicios = BigDecimal.valueOf(importeFl*porcentaje);
            setVicios(formato.format(impVicios));
            sumarFianzas();
        }
        if((getPctRespCivil()>0) && (importeFl > 0)) {
            float porcentaje = getPctRespCivil()/100;
            impRespCivil = BigDecimal.valueOf(importeFl*porcentaje);
            setRespCivil(formato.format(impRespCivil));
            
        }
        if((getPctTerceros()>0) && (importeFl > 0)) {
            float porcentaje = getPctTerceros()/100;
            impTerceros = BigDecimal.valueOf(importeFl*porcentaje);
            setTerceros(formato.format(impTerceros));
            
        }
        
        
    }
    
        
    public void crearRegistro() {
        
        //System.out.println("registro");
        AltaBean altaBean = new AltaBean();
        ConexionBD conexion;
        Connection con;
        Direccion dir = new Direccion(calle, numExt, colonia, codPst, ciudad, telefono);
        
        FacesContext msj = FacesContext.getCurrentInstance();
        
        altaBean.setProyecto(getProyecto());
        altaBean.setDireccion(getDireccion());
        altaBean.setNumContrato(getNumContrato());
        altaBean.setContrato(getContrato());
        altaBean.setImporteContto(getImporteContto());
        altaBean.setFormaDPago(getFormaDPago());
        altaBean.setAnticipo(getAnticipo());
        altaBean.setFechInicio(getFechInicio());
        altaBean.setFechFin(getFechFin());
        altaBean.setCliente(getCliente());
        altaBean.setPctGarantia(getPctGarantia());
        altaBean.setImpGarantia(getImpGarantia());
        altaBean.setPctFianzaAntcpo(getPctFianzaAntcpo());
        altaBean.setImpFianzaAntcpo(getImpFianzaAntcpo());
        altaBean.setPctCumplimiento(getPctCumplimiento());
        altaBean.setImpCumplimiento(getImpCumplimiento());
        altaBean.setPctVicios(getPctVicios());
        altaBean.setImpVicios(getImpVicios());
        altaBean.setPctRespCivil(getPctRespCivil());
        altaBean.setImpRespCivil(getImpRespCivil());
        altaBean.setPctTerceros(getPctTerceros());
        altaBean.setImpTerceros(getImpTerceros());
        altaBean.setOtros(getOtros());
        altaBean.setOtrosRiesg(getOtrosRiesg());
        
        conexion = new ConexionBD();
        con = conexion.getConexion();
        AltaDAO alta = new AltaDAO(con);
        alta.registrarProyecto(altaBean, dir);
        
        msj.addMessage(null, new FacesMessage("Proyecto Registrado"));
        
    }
    
    
    

    /**
     * @return the proyecto
     */
    public String getProyecto() {
        return proyecto;
    }

    /**
     * @param proyecto the proyecto to set
     */
    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    /**
     * @return the contrato
     */
    public String getContrato() {
        return contrato;
    }

    /**
     * @param contrato the contrato to set
     */
    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    /**
     * @return the numContrato
     */
    public String getNumContrato() {
        return numContrato;
    }

    /**
     * @param numContrato the numContrato to set
     */
    public void setNumContrato(String numContrato) {
        this.numContrato = numContrato;
    }

    /**
     * @return the importeContto
     */
    public BigDecimal getImporteContto() {
        return importeContto;
    }

    /**
     * @param importeContto the importeContto to set
     */
    public void setImporteContto(BigDecimal importeContto) {
        this.importeContto = importeContto;
        
    }

    /**
     * @return the formaDPago
     */
    public String getFormaDPago() {
        return formaDPago;
    }

    /**
     * @param formaDPago the formaDPago to set
     */
    public void setFormaDPago(String formaDPago) {
        this.formaDPago = formaDPago;
    }

    /**
     * @return the anticipo
     */
    public BigDecimal getAnticipo() {
        return anticipo;
    }

    /**
     * @param anticipo the anticipo to set
     */
    public void setAnticipo(BigDecimal anticipo) {
        this.anticipo = anticipo;
    }

    /**
     * @return the pctGarantia
     */
    public float getPctGarantia() {
        return pctGarantia;
    }

    /**
     * @param pctGarantia the pctGarantia to set
     */
    public void setPctGarantia(float pctGarantia) {
        this.pctGarantia = pctGarantia;
    }

    /**
     * @return the impGarantia
     */
    public BigDecimal getImpGarantia() {
        return impGarantia;
    }

    /**
     * @param impGarantia the impGarantia to set
     */
    public void setImpGarantia(BigDecimal impGarantia) {
        this.impGarantia = impGarantia;
    }

    /**
     * @return the pctFianzaAntcpo
     */
    public float getPctFianzaAntcpo() {
        return pctFianzaAntcpo;
    }

    /**
     * @param pctFianzaAntcpo the pctFianzaAntcpo to set
     */
    public void setPctFianzaAntcpo(float pctFianzaAntcpo) {
        this.pctFianzaAntcpo = pctFianzaAntcpo;
    }

    /**
     * @return the impFianzaAntcpo
     */
    public BigDecimal getImpFianzaAntcpo() {
        return impFianzaAntcpo;
    }

    /**
     * @param impFianzaAntcpo the impFianzaAntcpo to set
     */
    public void setImpFianzaAntcpo(BigDecimal impFianzaAntcpo) {
        this.impFianzaAntcpo = impFianzaAntcpo;
    }

    /**
     * @return the pctCumplimiento
     */
    public float getPctCumplimiento() {
        return pctCumplimiento;
    }

    /**
     * @param pctCumplimiento the pctCumplimiento to set
     */
    public void setPctCumplimiento(float pctCumplimiento) {
        this.pctCumplimiento = pctCumplimiento;
    }

    /**
     * @return the impCumplimiento
     */
    public BigDecimal getImpCumplimiento() {
        return impCumplimiento;
    }

    /**
     * @param impCumplimiento the impCumplimiento to set
     */
    public void setImpCumplimiento(BigDecimal impCumplimiento) {
        this.impCumplimiento = impCumplimiento;
    }

    /**
     * @return the pctVicios
     */
    public float getPctVicios() {
        return pctVicios;
    }

    /**
     * @param pctVicios the pctVicios to set
     */
    public void setPctVicios(float pctVicios) {
        this.pctVicios = pctVicios;
    }

    /**
     * @return the impVicios
     */
    public BigDecimal getImpVicios() {
        return impVicios;
    }

    /**
     * @param impVicios the impVicios to set
     */
    public void setImpVicios(BigDecimal impVicios) {
        this.impVicios = impVicios;
    }

    /**
     * @return the pctRespCivil
     */
    public float getPctRespCivil() {
        return pctRespCivil;
    }

    /**
     * @param pctRespCivil the pctRespCivil to set
     */
    public void setPctRespCivil(float pctRespCivil) {
        this.pctRespCivil = pctRespCivil;
    }

    /**
     * @return the impRespCivil
     */
    public BigDecimal getImpRespCivil() {
        return impRespCivil;
    }

    /**
     * @param impRespCivil the impRespCivil to set
     */
    public void setImpRespCivil(BigDecimal impRespCivil) {
        this.impRespCivil = impRespCivil;
    }

    /**
     * @return the pctTerceros
     */
    public float getPctTerceros() {
        return pctTerceros;
    }

    /**
     * @param pctTerceros the pctTerceros to set
     */
    public void setPctTerceros(float pctTerceros) {
        this.pctTerceros = pctTerceros;
    }

    /**
     * @return the impTerceros
     */
    public BigDecimal getImpTerceros() {
        return impTerceros;
    }

    /**
     * @param impTerceros the impTerceros to set
     */
    public void setImpTerceros(BigDecimal impTerceros) {
        this.impTerceros = impTerceros;
    }

    /**
     * @return the otros
     */
    public String getOtros() {
        return otros;
    }

    /**
     * @param otros the otros to set
     */
    public void setOtros(String otros) {
        this.otros = otros;
    }

    /**
     * @return the cliente
     */
    public String getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the importeFormat
     */
    public String getImporteFormat() {
        return importeFormat;
    }

    /**
     * @param importeFormat the importeFormat to set
     */
    public void setImporteFormat(String importeFormat) {
        this.importeFormat = importeFormat;
    }

    /**
     * @return the fianzaAntcpo
     */
    public String getFianzaAntcpo() {
        return fianzaAntcpo;
    }

    /**
     * @param fianzaAntcpo the fianzaAntcpo to set
     */
    public void setFianzaAntcpo(String fianzaAntcpo) {
        this.fianzaAntcpo = fianzaAntcpo;
    }

    /**
     * @return the garantia
     */
    public String getGarantia() {
        return garantia;
    }

    /**
     * @param garantia the garantia to set
     */
    public void setGarantia(String garantia) {
        this.garantia = garantia;
    }

    /**
     * @return the cumplimiento
     */
    public String getCumplimiento() {
        return cumplimiento;
    }

    /**
     * @param cumplimiento the cumplimiento to set
     */
    public void setCumplimiento(String cumplimiento) {
        this.cumplimiento = cumplimiento;
    }

    /**
     * @return the vicios
     */
    public String getVicios() {
        return vicios;
    }

    /**
     * @param vicios the vicios to set
     */
    public void setVicios(String vicios) {
        this.vicios = vicios;
    }

    /**
     * @return the respCivil
     */
    public String getRespCivil() {
        return respCivil;
    }

    /**
     * @param respCivil the respCivil to set
     */
    public void setRespCivil(String respCivil) {
        this.respCivil = respCivil;
    }

    /**
     * @return the terceros
     */
    public String getTerceros() {
        return terceros;
    }

    /**
     * @param terceros the terceros to set
     */
    public void setTerceros(String terceros) {
        this.terceros = terceros;
    }

    /**
     * @return the fechInicio
     */
    public Date getFechInicio() {
        return fechInicio;
    }

    /**
     * @param fechInicio the fechInicio to set
     */
    public void setFechInicio(Date fechInicio) {
        this.fechInicio = fechInicio;
    }

    /**
     * @return the fechFin
     */
    public Date getFechFin() {
        return fechFin;
    }

    /**
     * @param fechFin the fechFin to set
     */
    public void setFechFin(Date fechFin) {
        this.fechFin = fechFin;
    }

    /**
     * @return the pctAntcpo
     */
    public float getPctAntcpo() {
        return pctAntcpo;
    }

    /**
     * @param pctAntcpo the pctAntcpo to set
     */
    public void setPctAntcpo(float pctAntcpo) {
        this.pctAntcpo = pctAntcpo;
    }

    /**
     * @return the cadAntcpo
     */
    public String getCadAntcpo() {
        return cadAntcpo;
    }

    /**
     * @param cadAntcpo the cadAntcpo to set
     */
    public void setCadAntcpo(String cadAntcpo) {
        this.cadAntcpo = cadAntcpo;
    }

    /**
     * @return the proySelect
     */
    public ProyectoBean getProySelect() {
        return proySelect;
    }

    /**
     * @param proySelect the proySelect to set
     */
    public void setProySelect(ProyectoBean proySelect) {
        this.proySelect = proySelect;
    }

    /**
     * @return the listaProyectos
     */
    public ArrayList<AltaBean> getListaProyectos() {
        enlistarProyectos();
        return listaProyectos;
    }

    /**
     * @param listaProyectos the listaProyectos to set
     */
    public void setListaProyectos(ArrayList<AltaBean> listaProyectos) {
        this.listaProyectos = listaProyectos;
    }

    /**
     * @return the idProyecto
     */
    public int getIdProyecto() {
        return idProyecto;
    }

    /**
     * @param idProyecto the idProyecto to set
     */
    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    /**
     * @return the pSelec
     */
    public AltaBean getProySelec() {
        return proySelec;
    }

    /**
     * @param pSelec the pSelec to set
     */
    public void setProySelec(AltaBean proySelec) {
        this.proySelec = proySelec;
    }

    /**
     * @return the fechIniCad
     */
    public String getFechIniCad() {
        return fechIniCad;
    }

    /**
     * @param fechIniCad the fechIniCad to set
     */
    public void setFechIniCad(String fechIniCad) {
        this.fechIniCad = fechIniCad;
    }

    /**
     * @return the fechFinCad
     */
    public String getFechFinCad() {
        return fechFinCad;
    }

    /**
     * @param fechFinCad the fechFinCad to set
     */
    public void setFechFinCad(String fechFinCad) {
        this.fechFinCad = fechFinCad;
    }

    public BigDecimal getSumaFianzas() {
        return sumaFianzas;
    }

    public void setSumaFianzas(BigDecimal sumaFianzas) {
        this.sumaFianzas = sumaFianzas;
    }

    public String getSumaFianzasCad() {
        return sumaFianzasCad;
    }

    public void setSumaFianzasCad(String sumaFianzasCad) {
        this.sumaFianzasCad = sumaFianzasCad;
    }

    public BigDecimal getSumaRiesgos() {
        return sumaRiesgos;
    }

    public void setSumaRiesgos(BigDecimal sumaRiesgos) {
        this.sumaRiesgos = sumaRiesgos;
    }

    public String getSumaRiesCad() {
        return sumaRiesCad;
    }

    public void setSumaRiesCad(String sumaRiesCad) {
        this.sumaRiesCad = sumaRiesCad;
    }

    public BigDecimal getOtrosRiesg() {
        return otrosRiesg;
    }

    public void setOtrosRiesg(BigDecimal otrosRiesg) {
        this.otrosRiesg = otrosRiesg;
    }

    public String getCentroC() {
        return centroC;
    }

    public void setCentroC(String centroC) {
        this.centroC = centroC;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getYearCntrt() {
        return yearCntrt;
    }

    public void setYearCntrt(String yearCntrt) {
        this.yearCntrt = yearCntrt;
    }

    public String getMonthCntr() {
        return monthCntr;
    }

    public void setMonthCntr(String monthCntr) {
        this.monthCntr = monthCntr;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the calle
     */
    public String getCalle() {
        return calle;
    }

    /**
     * @param calle the calle to set
     */
    public void setCalle(String calle) {
        this.calle = calle;
    }

    /**
     * @return the numExt
     */
    public String getNumExt() {
        return numExt;
    }

    /**
     * @param numExt the numExt to set
     */
    public void setNumExt(String numExt) {
        this.numExt = numExt;
    }


    /**
     * @return the colonia
     */
    public String getColonia() {
        return colonia;
    }

    /**
     * @param colonia the colonia to set
     */
    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    /**
     * @return the codPst
     */
    public String getCodPst() {
        return codPst;
    }

    /**
     * @param codPst the codPst to set
     */
    public void setCodPst(String codPst) {
        this.codPst = codPst;
    }

    /**
     * @return the ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * @param ciudad the ciudad to set
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
}
