/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entidades.Cao;
import util.Util;

/**
 *
 * @author Deivid
 */
//extensao para a entidade Cao
public class CaoDAO extends HibernateDAO<Cao>{

    //atribui a entidade a sessao atual
    public CaoDAO() {
        super(Util.pegaSessaoAtual(),Cao.class);
    }
    
}
