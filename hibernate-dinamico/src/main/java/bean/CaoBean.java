/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entidades.Cao;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import rn.CaoRN;

/**
 *
 * @author Deivid
 */
@ManagedBean
@ViewScoped
public class CaoBean implements Serializable {

    private Cao cao;
    private List<Cao> lista;

    @PostConstruct
    public void init() {
        this.cao = new Cao();
    }

    public void salvar() {
        try {

            if (new CaoRN().salvar(this.cao)) {
                iniciarParametros();
            }

        } catch (Exception e) {
        }
    }

    public void excluir(Cao cao) {
        try {

            if (new CaoRN().excluir(cao)) {
                iniciarParametros();
            }

        } catch (Exception e) {
        }
    }

    public void editar(Cao cao) {
        this.cao = cao;
        util.Util.resetarFormulario("form");

    }

    public void cancelar() {
        iniciarParametros();
    }

    private void iniciarParametros() {
        this.cao = new Cao();
        this.lista = null;
        util.Util.resetarFormulario("form");
    }

    public Cao getCao() {
        return cao;
    }

    public void setCao(Cao cao) {
        this.cao = cao;
    }

    public List<Cao> getLista() {
        if (this.lista == null) {
            this.lista = new CaoRN().listar();
        }
        return lista;
    }

}
