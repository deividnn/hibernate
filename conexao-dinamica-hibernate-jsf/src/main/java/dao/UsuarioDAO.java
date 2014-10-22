/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entidade.Usuario;
import util.Util;

/**
 *
 * @author Deivid //inicializa as operacoes do dao na classe usuario usando a
 * sessao atual
 */
public class UsuarioDAO extends HibernateDAO<Usuario> {

    public UsuarioDAO() {
        super(Util.pegaSessaoAtual(), Usuario.class);
    }

}
