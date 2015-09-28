/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbean;

import com.bean.Catalogo;
import com.bean.Concepto;
import com.bean.FamConcepto;
import com.bean.FamiliaMat;
import com.bean.InsumoBean;
import com.bean.Nivel;
import com.bean.PartidaBean;
import com.bean.PresupuestoBean;
import com.bean.ProyectoSimple;
import com.bean.Unidad;
import com.bo.CrearCodigo;
import com.conexion.ConexionBD;
import com.dao.PresupuestoDAO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;




@ManagedBean
@ViewScoped
public class CrearConcepto implements Serializable {

    private ArrayList<ProyectoSimple> listaProy = new ArrayList<ProyectoSimple>();
    private ProyectoSimple proySel;
    private String proyecto;
    private int id_proyecto;
    
    private ArrayList<PresupuestoBean> listaPres = new ArrayList<PresupuestoBean>();
    private PresupuestoBean presB;
    private int id_presupuesto;
    private String presupuesto;
    
    private String codPrtda;
    private String subPrtda;
    private String tipoConcepto;
    private String codConcepto;
    private String descConc;
    
    private String unidad;
    private String unidConcepto;
    private BigDecimal cantidad;
    private BigDecimal presUnit;
    private BigDecimal importe;
    
    private String tipoIns;
    private String familia;
    private List<String> familias;
    
    private List<FamiliaMat> listaFam;
    private FamiliaMat selecFam;
    
    private List<InsumoBean> listaIns;
    
    private List<Unidad> listUnid;
    
    private String descIns;
    private String unitSel;
    private BigDecimal cantIns;
    private BigDecimal presUnitIns;
    private BigDecimal impIns;
    
    private List<Catalogo> catalogo = new ArrayList<Catalogo>();
    private Catalogo cat;
    
    private List<String> catString;
    
    private List<String> listSubFam;
    
    private String cveInsumo;
    
    private String consecutivo;
    
    private List<Concepto> listConcep;
    
    private List<FamConcepto> listFamConc;
    private String famConcepto; 
    private List<String> listaStFamConc;
    
    private List<PartidaBean> listaPB;
    private PartidaBean partSel;
    
    private List<PartidaBean> listSubPrt;
    private PartidaBean subPrtSel;
    
    private String creaPrtCve;
    private String creaPrtDesc;
    
    private String creaSubPrtCve;
    private String creaSubPrtDesc;
    
    private String cveNivel;
    private String nivel;
    private int id_nivel;
    
    private List<Nivel> listNvl;
    private Nivel selNvl;
    
    public CrearConcepto() {
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        listUnid = new ArrayList<Unidad>();
        PresupuestoDAO pd = new PresupuestoDAO(con);
        listUnid = pd.listaUnidades();
        tipoIns = "";
        listaIns =  new ArrayList<InsumoBean>();
        listaStFamConc =  new ArrayList<String>();
        listaStFamConc = listarFamConc(pd.listarFamiliasConceptos());
        
    }
    
    public void guardarConcepto() {
        
        CrearCodigo crea = new CrearCodigo();
        Concepto conc = new Concepto();
        conc.setNumConcepto(crea.crearConsecutivo(id_proyecto, id_proyecto));
        conc.setCodConcepto(getCodConcepto());
        conc.setDescripcion(getDescConc());
        conc.setUnidad(getUnidConcepto());
        conc.setCantidad(getCantidad());
        conc.setpUnitario(getPresUnit());
        conc.setImporte(getImporte());
        conc.setId_partida(subPrtSel.getId_partida());
        conc.setPartida(subPrtSel.getNivel());
        
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        PresupuestoDAO pd = new PresupuestoDAO(con);
        pd.guardarConceptoNuevo(conc, listaIns);
        limpiarCampos();
        limpiarCamposInsumo();
    }
    
    @PostConstruct
    public void limpiarCampos() {
        setId_proyecto(0);
        setProyecto(null);
        setId_presupuesto(0);
        setPresupuesto(null);
        setCodPrtda(null);
        setSubPrtda(null);
        setFamConcepto(null);
        setCodConcepto(null);
        setDescConc(null);
        setUnidConcepto(null);
        setCantidad(null);
        setPresUnit(null);
        setImporte(null);
        listaIns.clear();
        setTipoIns(null);
        setFamilia(null);
        setCveInsumo(null);
        setDescIns(null);
        setUnidad(null);
        setCantIns(null);
        setPresUnitIns(null);
        setImpIns(null);
        //System.out.println("Limpio");
    }
    
    
    public void limpiarCamposInsumo() {
        setTipoIns(null);
        setFamilia(null);
        setCveInsumo(null);
        setDescIns(null);
        setUnidad(null);
        setCantIns(null);
        setPresUnitIns(null);
        setImpIns(null);
    }
    
    public void guardarNuevaSubPrtda() {
        PartidaBean pb = new PartidaBean();
        pb.setNivel(getCreaSubPrtCve());
        pb.setEsSubNivelDe(partSel.getId_partida());
        pb.setDescripcion(getCreaSubPrtDesc());
        pb.setId_presupuesto(getId_presupuesto());
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        PresupuestoDAO pd = new PresupuestoDAO(con);
        pd.guardarNuevaSubPrtda(pb);
        listarSubPartidas();
    }
    
    public void guardarNivel() {
        Nivel nvl = new Nivel();
        nvl.setNivel(getCveNivel());
        nvl.setEsSubNivelDe(0);
        nvl.setDescripcion(getNivel());
        nvl.setId_presupuesto(getId_presupuesto());
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        PresupuestoDAO pd = new PresupuestoDAO(con);
        pd.guardarNuevoNivel(nvl);
        listarNivel();
    }
    
    public void listarNivel() {
        
        ConexionBD c  =  new ConexionBD();
        Connection con = c.getConexion();
        PresupuestoDAO pd = new PresupuestoDAO(con);
        setListNvl(pd.listarNiveles(id_proyecto, id_presupuesto));
    }
    
    public void obtenerNivel() {
        setCveNivel(selNvl.getNivel());
        setNivel(selNvl.getDescripcion());
        setId_nivel(selNvl.getId_partida());
        System.out.println(id_nivel);
        buscarPartida();
    }
    
    public void guardarNuevaPartida() {
        
        PartidaBean pb = new PartidaBean();
        pb.setNivel(getCreaPrtCve());
        pb.setEsSubNivelDe(0);
        pb.setDescripcion(getCreaPrtDesc());
        pb.setId_presupuesto(getId_presupuesto());
        
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        PresupuestoDAO pd = new PresupuestoDAO(con);
        pd.guardarNuevaPartida(pb);
        buscarPartida();
    }
    
    public void buscarPartida() {
        ConexionBD c  =  new ConexionBD();
        Connection con = c.getConexion();
        PresupuestoDAO pd = new PresupuestoDAO(con);
        setListaPB(pd.listarPartidaBean(id_proyecto, id_presupuesto));
        
    }
    
    public void obtenerPartida() {
        setCodPrtda(partSel.getNivel());
        listarSubPartidas();
    }
    
    public void listarSubPartidas() {
        ConexionBD c  =  new ConexionBD();
        Connection con = c.getConexion();
        PresupuestoDAO pd = new PresupuestoDAO(con);
        setListSubPrt(pd.listarSubPartidas(id_proyecto, id_presupuesto, partSel.getId_partida()));
    }
    
    public void obtenerSubPartida() {
        setSubPrtda(subPrtSel.getNivel());
    }
    
    public List<String> listarFamConc(List<FamConcepto> lista) {
        
        List<String> listAux = new ArrayList<String>();
        
        for(FamConcepto aux:lista) {
            listAux.add(aux.getClaveFam()+"-"+aux.getSunFamilia());
        }
        
        return listAux;
    }
    
    public void crearCodigo() {
        CrearCodigo cc = new CrearCodigo();
        System.out.println(famConcepto);
        System.out.println(id_proyecto);
        System.out.println(id_presupuesto);
        setCodConcepto(cc.codificarConcepto(id_proyecto, famConcepto, id_presupuesto));
    }
    
    public void agregarInsumo() {
        
        InsumoBean ib = new InsumoBean();
        if(tipoIns.equals("sc")) {
            ib.setCuenta("SUBCONTRATO");
            ib.setCodInsumo(cveInsumo);
            ib.setDescripIns(descIns);
            ib.setUnidadIns(unidad);
            ib.setCantIns(cantIns);
            ib.setCantInsCtrl(cantIns);
            ib.setCostoIns(presUnitIns);
            ib.setCostoInsCtrl(presUnitIns);
            ib.setImporteIns(impIns);
            ib.setImpInsCtrl(impIns);
        }
        if(tipoIns.equals("mat")) {
            ib.setCuenta("MATERIALES");
            ib.setCodInsumo(cveInsumo.substring(0, cveInsumo.indexOf("-")));
            ib.setDescripIns(descIns);
            ib.setUnidadIns(unidad);
            ib.setCantIns(cantIns);
            ib.setCantInsCtrl(cantIns);
            ib.setCostoIns(presUnitIns);
            ib.setCostoInsCtrl(presUnitIns);
            ib.setImporteIns(impIns);
            ib.setImpInsCtrl(impIns);
        } 
        listaIns.add(ib);
        setListaIns(listaIns);
        limpiarCamposInsumo();
    }
    
    public void listarConceptosConsecutivos() {
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        PresupuestoDAO pd = new PresupuestoDAO(con);
        setListConcep(pd.conceptosConsecutivos(id_proyecto, id_presupuesto));
    }
    
    public void filtrarSubFamilias() {
        
        if(tipoIns.equals("sc")) {
            List<String> catSubc = new ArrayList<String>();
            int ind2 = familia.indexOf("-");
            String sub;
            String id = ""+getId_proyecto();
            String idComp = "";
            for(int i = id.length(); i<3; i++) {
                idComp = idComp + "0";
            }
            idComp = idComp + id;
            sub = familia.substring(0, ind2)+"-"+idComp;
            ConexionBD c = new ConexionBD();
            Connection con = c.getConexion();
            PresupuestoDAO pd = new PresupuestoDAO(con);
            sub = sub+"-"+pd.buscarConsecutivoSC(familia.substring(0, ind2));
            catSubc.add(sub);
            setListSubFam(catSubc);
        }
        if(tipoIns.equals("mat")) {
            int ind2 = familia.indexOf("-");
            String cve = familia.substring(0, ind2);
            ConexionBD c = new ConexionBD();
            Connection con = c.getConexion();
            PresupuestoDAO pd = new PresupuestoDAO(con);
            setCatalogo(pd.listarClaves(cve));
            convertirCatSubFam(getCatalogo());
        }
    }
    
    public void convertirCatSubFam(List<Catalogo> lista) {
        
        List<String> listAux = new ArrayList<String>();
        
        for(Catalogo aux: lista) {
            listAux.add(aux.getClave()+"-"+aux.getDefinicion());
        }
        
        setListSubFam(listAux);
    }
    
    public void filtrarFamiliaIns() {
        ConexionBD c = new ConexionBD();
        Connection con = c.getConexion();
        PresupuestoDAO pd = new PresupuestoDAO(con);
        if(tipoIns.equals("sc")) {
            
            setCatalogo(pd.listarCatalogo(1));
            
        }
        if(tipoIns.equals("mat")) {
            
            setCatalogo(pd.listarCatalogo(2));
        }
        convertirCatalogo(getCatalogo());
    }
    
    public void convertirCatalogo(List<Catalogo> lista) {
        
        List<String> listaCatString = new ArrayList<String>();
        
        for(Catalogo aux:lista) {
            listaCatString.add(aux.getClave()+"-"+aux.getDefinicion());
        }
        
        setCatString(listaCatString);
        
    }
    
    public void buscarProyecto() {
        
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.getConexion();
        PresupuestoDAO presD = new PresupuestoDAO(con);
        setListaProy(presD.listarProyecto());      
    }
    
    public void obtenerProyecto() {
        setId_proyecto(proySel.getId_proyecto());
        setProyecto(proySel.getProyecto());
        buscarPresupuesto();
    }
    
    public void buscarPresupuesto() {
        
        if(proySel != null) {
            Connection con;
            ConexionBD conexion = new ConexionBD();
            con = conexion.getConexion();
            PresupuestoDAO presD = new PresupuestoDAO(con);
            
            setListaPres(presD.listarPresupuesto(proySel.getId_proyecto()));
            //System.out.println(listaPres.size());
        }
        
    }
    
    public void obtenerPresupuesto() {
        setId_presupuesto(presB.getId_presupuesto());
        setPresupuesto(presB.getPresupuesto());
        buscarPartida();
    }

    /**
     * @return the listaProy
     */
    public ArrayList<ProyectoSimple> getListaProy() {
        buscarProyecto();
        return listaProy;
    }

    /**
     * @param listaProy the listaProy to set
     */
    public void setListaProy(ArrayList<ProyectoSimple> listaProy) {
        this.listaProy = listaProy;
    }

    /**
     * @return the proySel
     */
    public ProyectoSimple getProySel() {
        return proySel;
    }

    /**
     * @param proySel the proySel to set
     */
    public void setProySel(ProyectoSimple proySel) {
        this.proySel = proySel;
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
     * @return the id_proyecto
     */
    public int getId_proyecto() {
        return id_proyecto;
    }

    /**
     * @param id_proyecto the id_proyecto to set
     */
    public void setId_proyecto(int id_proyecto) {
        this.id_proyecto = id_proyecto;
    }

    /**
     * @return the listaPres
     */
    public ArrayList<PresupuestoBean> getListaPres() {
        return listaPres;
    }

    /**
     * @param listaPres the listaPres to set
     */
    public void setListaPres(ArrayList<PresupuestoBean> listaPres) {
        this.listaPres = listaPres;
    }

    /**
     * @return the presB
     */
    public PresupuestoBean getPresB() {
        return presB;
    }

    /**
     * @param presB the presB to set
     */
    public void setPresB(PresupuestoBean presB) {
        this.presB = presB;
    }

    /**
     * @return the id_presupuesto
     */
    public int getId_presupuesto() {
        return id_presupuesto;
    }

    /**
     * @param id_presupuesto the id_presupuesto to set
     */
    public void setId_presupuesto(int id_presupuesto) {
        this.id_presupuesto = id_presupuesto;
    }

    /**
     * @return the presupuesto
     */
    public String getPresupuesto() {
        return presupuesto;
    }

    /**
     * @param presupuesto the presupuesto to set
     */
    public void setPresupuesto(String presupuesto) {
        this.presupuesto = presupuesto;
    }

    /**
     * @return the tipoConcepto
     */
    public String getTipoConcepto() {
        return tipoConcepto;
    }

    /**
     * @param tipoConcepto the tipoConcepto to set
     */
    public void setTipoConcepto(String tipoConcepto) {
        this.tipoConcepto = tipoConcepto;
    }

    /**
     * @return the codSubPrtda
     */
    public String getCodPrtda() {
        return codPrtda;
    }

    /**
     * @param codSubPrtda the codSubPrtda to set
     */
    public void setCodPrtda(String codPrtda) {
        this.codPrtda = codPrtda;
    }

    /**
     * @return the codPrtda
     */
    public String getSubPrtda() {
        return subPrtda;
    }

    /**
     * @param codPrtda the codPrtda to set
     */
    public void setSubPrtda(String subPrtda) {
        this.subPrtda = subPrtda;
    }

    /**
     * @return the codConcepto
     */
    public String getCodConcepto() {
        return codConcepto;
    }

    /**
     * @param codConcepto the codConcepto to set
     */
    public void setCodConcepto(String codConcepto) {
        this.codConcepto = codConcepto;
    }

    /**
     * @return the descConc
     */
    public String getDescConc() {
        return descConc;
    }

    /**
     * @param descConc the descConc to set
     */
    public void setDescConc(String descConc) {
        this.descConc = descConc;
    }

    /**
     * @return the unidad
     */
    public String getUnidad() {
        return unidad;
    }

    /**
     * @param unidad the unidad to set
     */
    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    /**
     * @return the cantidad
     */
    public BigDecimal getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    

    /**
     * @return the importe
     */
    public BigDecimal getImporte() {
        return importe;
    }

    /**
     * @param importe the importe to set
     */
    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    /**
     * @return the presUnit
     */
    public BigDecimal getPresUnit() {
        return presUnit;
    }

    /**
     * @param presUnit the presUnit to set
     */
    public void setPresUnit(BigDecimal presUnit) {
        this.presUnit = presUnit;
    }

    /**
     * @return the tipoIns
     */
    public String getTipoIns() {
        return tipoIns;
    }

    /**
     * @param tipoIns the tipoIns to set
     */
    public void setTipoIns(String tipoIns) {
        this.tipoIns = tipoIns;
    }

    /**
     * @return the familia
     */
    public String getFamilia() {
        return familia;
    }

    /**
     * @param familia the familia to set
     */
    public void setFamilia(String familia) {
        this.familia = familia;
    }

    /**
     * @return the descIns
     */
    public String getDescIns() {
        return descIns;
    }

    /**
     * @param descIns the descIns to set
     */
    public void setDescIns(String descIns) {
        this.descIns = descIns;
    }

    /**
     * @return the unitSel
     */
    public String getUnitSel() {
        return unitSel;
    }

    /**
     * @param unitSel the unitSel to set
     */
    public void setUnitSel(String unitSel) {
        this.unitSel = unitSel;
    }

    /**
     * @return the cantIns
     */
    public BigDecimal getCantIns() {
        return cantIns;
    }

    /**
     * @param cantIns the cantIns to set
     */
    public void setCantIns(BigDecimal cantIns) {
        this.cantIns = cantIns;
    }

    /**
     * @return the presUnitIns
     */
    public BigDecimal getPresUnitIns() {
        return presUnitIns;
    }

    /**
     * @param presUnitIns the presUnitIns to set
     */
    public void setPresUnitIns(BigDecimal presUnitIns) {
        this.presUnitIns = presUnitIns;
    }

    /**
     * @return the impIns
     */
    public BigDecimal getImpIns() {
        return impIns;
    }

    /**
     * @param impIns the impIns to set
     */
    public void setImpIns(BigDecimal impIns) {
        this.impIns = impIns;
    }

    /**
     * @return the listaIns
     */
    public List<InsumoBean> getListaIns() {
        return listaIns;
    }

    /**
     * @param listaIns the listaIns to set
     */
    public void setListaIns(List<InsumoBean> listaIns) {
        this.listaIns = listaIns;
    }

    /**
     * @return the familias
     */
    public List<String> getFamilias() {
        return familias;
    }

    /**
     * @param familias the familias to set
     */
    public void setFamilias(List<String> familias) {
        this.familias = familias;
    }

    /**
     * @return the listaFam
     */
    public List<FamiliaMat> getListaFam() {
        return listaFam;
    }

    /**
     * @param listaFam the listaFam to set
     */
    public void setListaFam(List<FamiliaMat> listaFam) {
        this.listaFam = listaFam;
    }

    /**
     * @return the selecFam
     */
    public FamiliaMat getSelecFam() {
        return selecFam;
    }

    /**
     * @param selecFam the selecFam to set
     */
    public void setSelecFam(FamiliaMat selecFam) {
        this.selecFam = selecFam;
    }

    /**
     * @return the listUnid
     */
    public List<Unidad> getListUnid() {
        return listUnid;
    }

    /**
     * @param listUnid the listUnid to set
     */
    public void setListUnid(List<Unidad> listUnid) {
        this.listUnid = listUnid;
    }

    /**
     * @return the catalogo
     */
    public List<Catalogo> getCatalogo() {
        return catalogo;
    }

    /**
     * @param catalogo the catalogo to set
     */
    public void setCatalogo(List<Catalogo> catalogo) {
        this.catalogo = catalogo;
    }

    /**
     * @return the cat
     */
    public Catalogo getCat() {
        return cat;
    }

    /**
     * @param cat the cat to set
     */
    public void setCat(Catalogo cat) {
        this.cat = cat;
    }

    /**
     * @return the catString
     */
    public List<String> getCatString() {
        return catString;
    }

    /**
     * @param catString the catString to set
     */
    public void setCatString(List<String> catString) {
        this.catString = catString;
    }

    /**
     * @return the listSubFam
     */
    public List<String> getListSubFam() {
        return listSubFam;
    }

    /**
     * @param listSubFam the listSubFam to set
     */
    public void setListSubFam(List<String> listSubFam) {
        this.listSubFam = listSubFam;
    }

    /**
     * @return the cveInsumo
     */
    public String getCveInsumo() {
        return cveInsumo;
    }

    /**
     * @param cveInsumo the cveInsumo to set
     */
    public void setCveInsumo(String cveInsumo) {
        this.cveInsumo = cveInsumo;
    }

    /**
     * @return the consecutivo
     */
    public String getConsecutivo() {
        return consecutivo;
    }

    /**
     * @param consecutivo the consecutivo to set
     */
    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    /**
     * @return the listConcep
     */
    public List<Concepto> getListConcep() {
        return listConcep;
    }

    /**
     * @param listConcep the listConcep to set
     */
    public void setListConcep(List<Concepto> listConcep) {
        this.listConcep = listConcep;
    }

    /**
     * @return the famConcepto
     */
    public String getFamConcepto() {
        return famConcepto;
    }

    /**
     * @param famConcepto the famConcepto to set
     */
    public void setFamConcepto(String famConcepto) {
        this.famConcepto = famConcepto;
    }

    /**
     * @return the listFamConc
     */
    public List<FamConcepto> getListFamConc() {
        return listFamConc;
    }

    /**
     * @param listFamConc the listFamConc to set
     */
    public void setListFamConc(List<FamConcepto> listFamConc) {
        this.listFamConc = listFamConc;
    }

    /**
     * @return the listaStFamConc
     */
    public List<String> getListaStFamConc() {
        return listaStFamConc;
    }

    /**
     * @param listaStFamConc the listaStFamConc to set
     */
    public void setListaStFamConc(List<String> listaStFamConc) {
        this.listaStFamConc = listaStFamConc;
    }

    /**
     * @return the unidConcepto
     */
    public String getUnidConcepto() {
        return unidConcepto;
    }

    /**
     * @param unidConcepto the unidConcepto to set
     */
    public void setUnidConcepto(String unidConcepto) {
        this.unidConcepto = unidConcepto;
    }

    /**
     * @return the listaPB
     */
    public List<PartidaBean> getListaPB() {
        
        return listaPB;
    }

    /**
     * @param listaPB the listaPB to set
     */
    public void setListaPB(List<PartidaBean> listaPB) {
        this.listaPB = listaPB;
    }

    /**
     * @return the partSel
     */
    public PartidaBean getPartSel() {
        return partSel;
    }

    /**
     * @param partSel the partSel to set
     */
    public void setPartSel(PartidaBean partSel) {
        this.partSel = partSel;
    }

    /**
     * @return the listSubPrt
     */
    public List<PartidaBean> getListSubPrt() {
        return listSubPrt;
    }

    /**
     * @param listSubPrt the listSubPrt to set
     */
    public void setListSubPrt(List<PartidaBean> listSubPrt) {
        this.listSubPrt = listSubPrt;
    }

    /**
     * @return the subPrtSel
     */
    public PartidaBean getSubPrtSel() {
        return subPrtSel;
    }

    /**
     * @param subPrtSel the subPrtSel to set
     */
    public void setSubPrtSel(PartidaBean subPrtSel) {
        this.subPrtSel = subPrtSel;
    }

    /**
     * @return the creaPrtCve
     */
    public String getCreaPrtCve() {
        return creaPrtCve;
    }

    /**
     * @param creaPrtCve the creaPrtCve to set
     */
    public void setCreaPrtCve(String creaPrtCve) {
        this.creaPrtCve = creaPrtCve;
    }

    /**
     * @return the creaPrtDesc
     */
    public String getCreaPrtDesc() {
        return creaPrtDesc;
    }

    /**
     * @param creaPrtDesc the creaPrtDesc to set
     */
    public void setCreaPrtDesc(String creaPrtDesc) {
        this.creaPrtDesc = creaPrtDesc;
    }

    /**
     * @return the creaSubPrtCve
     */
    public String getCreaSubPrtCve() {
        return creaSubPrtCve;
    }

    /**
     * @param creaSubPrtCve the creaSubPrtCve to set
     */
    public void setCreaSubPrtCve(String creaSubPrtCve) {
        this.creaSubPrtCve = creaSubPrtCve;
    }

    /**
     * @return the creaSubPrtDesc
     */
    public String getCreaSubPrtDesc() {
        return creaSubPrtDesc;
    }

    /**
     * @param creaSubPrtDesc the creaSubPrtDesc to set
     */
    public void setCreaSubPrtDesc(String creaSubPrtDesc) {
        this.creaSubPrtDesc = creaSubPrtDesc;
    }

    /**
     * @return the cveNivel
     */
    public String getCveNivel() {
        return cveNivel;
    }

    /**
     * @param cveNivel the cveNivel to set
     */
    public void setCveNivel(String cveNivel) {
        this.cveNivel = cveNivel;
    }

    /**
     * @return the nivel
     */
    public String getNivel() {
        return nivel;
    }

    /**
     * @param nivel the nivel to set
     */
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    /**
     * @return the id_nivel
     */
    public int getId_nivel() {
        return id_nivel;
    }

    /**
     * @param id_nivel the id_nivel to set
     */
    public void setId_nivel(int id_nivel) {
        this.id_nivel = id_nivel;
    }

    /**
     * @return the listNvl
     */
    public List<Nivel> getListNvl() {
        return listNvl;
    }

    /**
     * @param listNvl the listNvl to set
     */
    public void setListNvl(List<Nivel> listNvl) {
        this.listNvl = listNvl;
    }

    /**
     * @return the selNvl
     */
    public Nivel getSelNvl() {
        return selNvl;
    }

    /**
     * @param selNvl the selNvl to set
     */
    public void setSelNvl(Nivel selNvl) {
        this.selNvl = selNvl;
    }
}
