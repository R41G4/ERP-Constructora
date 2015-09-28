/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.math.BigDecimal;
import java.util.List;


public class Partida {
    
    private int id_partida;
    private String partida;
    private String cvePartida;
    private List<Concepto> listConcept;
    private BigDecimal totalesPart;
    private String totalesCad;
    

    /**
     * @return the id_partida
     */
    public int getId_partida() {
        return id_partida;
    }

    /**
     * @param id_partida the id_partida to set
     */
    public void setId_partida(int id_partida) {
        this.id_partida = id_partida;
    }

    /**
     * @return the partida
     */
    public String getPartida() {
        return partida;
    }

    /**
     * @param partida the partida to set
     */
    public void setPartida(String partida) {
        this.partida = partida;
    }

    /**
     * @return the cvePartida
     */
    public String getCvePartida() {
        return cvePartida;
    }

    /**
     * @param cvePartida the cvePartida to set
     */
    public void setCvePartida(String cvePartida) {
        this.cvePartida = cvePartida;
    }

    /**
     * @return the listConcept
     */
    public List<Concepto> getListConcept() {
        return listConcept;
    }

    /**
     * @param listConcept the listConcept to set
     */
    public void setListConcept(List<Concepto> listConcept) {
        this.listConcept = listConcept;
    }

    /**
     * @return the totalesPart
     */
    public BigDecimal getTotalesPart() {
        return totalesPart;
    }

    /**
     * @param totalesPart the totalesPart to set
     */
    public void setTotalesPart(BigDecimal totalesPart) {
        this.totalesPart = totalesPart;
    }

    /**
     * @return the totalesCad
     */
    public String getTotalesCad() {
        return totalesCad;
    }

    /**
     * @param totalesCad the totalesCad to set
     */
    public void setTotalesCad(String totalesCad) {
        this.totalesCad = totalesCad;
    }
    
}
