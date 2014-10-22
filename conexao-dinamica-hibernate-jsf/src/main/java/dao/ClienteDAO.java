/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entidade.Cliente;

/**
 *
 * @author Deivid inicializa as operacoes do dao na classe cliente usando a
 * sessao atual
 */
public class ClienteDAO extends HibernateDAO<Cliente> {

    public ClienteDAO() {
        super(util.Util.pegaSessaoAtual(), Cliente.class);
    }

}
