/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.component.commandbutton.CommandButton;

/**
 *
 * @author Deivid
 */
public class NormalActionListenner implements PhaseListener {

    private static final long serialVersionUID = -7607159318721947672L;

    // The phase where the listener is going to be called
    private final PhaseId phaseId = PhaseId.RENDER_RESPONSE;

    /**
     * Recursively walk through the view tree,
     *
     * @param event
     */
    @Override
    public void beforePhase(PhaseEvent event) {

        boolean ok = processViewTree(event.getFacesContext().getViewRoot());
        String uri = ((HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest()).getRequestURI();
        String u = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (sessao != null) {
            if (ok == false
                    && !uri.equals("/conexao-dinamica/conexao.jsf")
                    && sessao.getAttribute("usuarioLogado") == null) {
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(u + "/conexao.jsf");
                } catch (IOException ex) {
                    Logger.getLogger(NormalActionListenner.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void afterPhase(PhaseEvent event) {
        //boolean ok = processViewTree(event.getFacesContext().getViewRoot());
    }

    @Override
    public PhaseId getPhaseId() {
        return phaseId;
    }

    private boolean processViewTree(UIComponent component) {
        // Go to every child
        String uri = ((HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest()).getRequestURI();
        PartialViewContext pvc = FacesContext.getCurrentInstance().getPartialViewContext();
        if (pvc.isAjaxRequest()) {

            if (uri.equals("/conexao-dinamica/conexao.jsf")) {
                login();
            }

        } 
        System.out.println(uri);
        for (UIComponent child : component.getChildren()) {
            // System.out.println("+ " + child.getId() + " [" + child.getClass() + "]");
            if (uri.equals("/conexao-dinamica/conexao.jsf")
                    && child.getClass().toString().equals("class org.primefaces.component.commandbutton.CommandButton")) {
                CommandButton botao = (CommandButton) child;
                botao.setAjax(false);
                botao.setUpdate("@all");
                System.out.println("atualizou botao");

            }
            processViewTree(child);
        }

        return false;
    }

    public String login() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException ex) {
            Logger.getLogger(NormalActionListenner.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/conexao?faces-redirect=true";
    }

}
