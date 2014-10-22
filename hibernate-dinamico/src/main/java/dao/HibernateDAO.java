/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

//implementacao da interface ,o hibernateFilter é usado para iniciar
//a transacao,concluir e fechar a sessao
public class HibernateDAO<T> implements DAO<T>,Serializable {
 
    private final Session sessao;
    private final Class<T> classe;

    public HibernateDAO(Session sessao, Class<T> classe) {
        super();
        this.sessao = sessao;
        this.classe = classe;
    }
    
    
    
    @Override
    public void salvar(T t) {
        //metodo do hibernate para salvar uma entidade
        this.sessao.save(t);
    }

    @Override
    public void atualizar(T t) {
        this.sessao.merge(t);
    }

    @Override
    public void excluir(T t) {
        this.sessao.delete(t);
    }

    @Override
    public List<T> listar(String colunaOrder) {
        return this.sessao.createCriteria(classe)
                .addOrder(Order.desc(colunaOrder))
                .list();
    }
    
}
