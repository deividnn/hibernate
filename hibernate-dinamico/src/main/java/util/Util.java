/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Deivid
 * 
 * metodos estaticos que podem ser usados em todas as classes da aplicacao
 */
public class Util {
    
    /**retorna uma sessao gerada na fabrica de sessoe
     * @return s*/
    public static Session pegaSessaoAtual(){    
      return HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    /**limpa todos os campos de um formulari
     * @param idForm
     *  idForm  id do formulario*/
    public static void resetarFormulario(String idForm){
        RequestContext.getCurrentInstance().reset(idForm);
    }
    
    /**cria uma mensagem que sera mostrada no p:growl do primeface
     * @param texto
     * texto é a mensagem*/
    public static void criarMensagem(String texto){
        FacesMessage mesagem = new FacesMessage(texto);
        FacesContext.getCurrentInstance().addMessage(texto, mesagem);
    }
}
