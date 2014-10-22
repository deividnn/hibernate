/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rn;

import dao.CaoDAO;
import dao.HibernateDAO;
import entidades.Cao;
import java.util.List;
import util.Util;

/**
 *
 * @author Deivid
 */
public class CaoRN {

    private final HibernateDAO<Cao> dao;

    //instancia a entidade Cao com as operacoes da interface DAO
    public CaoRN() {
        this.dao = new CaoDAO();
    }

    //salva um Cao 
    public boolean salvar(Cao cao) {
        try {
            if (cao.getId() == null) {
                this.dao.salvar(cao);
                //cria uma mensagem 
                Util.criarMensagem("cao salvo");
            } else {
                this.dao.atualizar(cao);
                //cria uma mensagem 
                Util.criarMensagem("cao atualizado");
            }
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean excluir(Cao cao) {
        try {
            this.dao.excluir(cao);
            Util.criarMensagem("cao excluido");
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public List<Cao> listar() {
        return this.dao.listar("id");
    }

}
