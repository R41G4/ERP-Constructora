/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import com.bean.InsumoContrat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mickey
 */
public class FiltrarLista {
    
    public List<InsumoContrat> filtrarConceptos(List<InsumoContrat> lista, String clave) {
        
        List<InsumoContrat> listaFilt = new ArrayList<InsumoContrat>();
        
        for(InsumoContrat aux: lista) {
            String claveCod = aux.getCodInsumo().substring(0, 3);
            if(claveCod.equals(clave)) {
                int ind = lista.indexOf(aux);
                listaFilt.add(lista.get(ind));
            }
        }
        
        return listaFilt;
    }
    
}
