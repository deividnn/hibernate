/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import util.HibernateUtil;

/**
 *
 * @author Deivid implementacao dos metodos de persistencia da interface
 * @param <T> entidade
 */
public class HibernateDAO<T> implements DAO<T> {

    //sessao atual
    private Session sessao;
    //classe da entidade
    private final Class<T> classe;

    //pelo construtor a sessao e classe da entidade sera inicializada
    public HibernateDAO(Session sessao, Class<T> classe) {
        super();
        this.sessao = sessao;
        this.classe = classe;
    }

    @Override
    public void salvar(T t) {
        Transaction tx = null;
        try {
            //recupera a sessao atual
            this.sessao = HibernateUtil.getSessionFactory().getCurrentSession();
            //inicia uma transacao
            tx = this.sessao.beginTransaction();
            //salva a entidade
            this.sessao.save(t);
            //atualiza a sessao
            this.sessao.flush();
            //conclui a transacao
            tx.commit();
        } catch (HibernateException e) {
            //se ocorrer algum erro faz rollback da transacao evitando a persitencia de dados 
            //invalidos
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public void atualizar(T t) {

        Transaction tx = null;
        try {
            //recupera a sessao atual
            this.sessao = HibernateUtil.getSessionFactory().getCurrentSession();
            //inicia uma transacao
            tx = this.sessao.beginTransaction();
            //atualiza a entidade
            this.sessao.merge(t);
            //atualiza a sessao
            this.sessao.flush();
            //conclui a transacao
            tx.commit();
        } catch (HibernateException e) {
            //se ocorrer algum erro faz rollback da transacao evitando a persitencia de dados 
            //invalidos
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public void excluir(T t) {

        Transaction tx = null;
        try {
            //recupera a sessao atual
            this.sessao = HibernateUtil.getSessionFactory().getCurrentSession();
            //inicia uma transacao
            tx = this.sessao.beginTransaction();
            //exclui  a entidade
            this.sessao.delete(t);
            //atualiza a sessao
            this.sessao.flush();
            //conclui a transacao
            tx.commit();
        } catch (HibernateException e) {
            //se ocorrer algum erro faz rollback da transacao evitando a persitencia de dados 
            //invalidos
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public List<T> listar(String orderColuna) {

        Transaction tx = null;
        try {
            //recupera a sessao atual
            this.sessao = HibernateUtil.getSessionFactory().getCurrentSession();
            //inicia uma transacao
            tx = this.sessao.beginTransaction();
            //usa createcriteria para fazer um select e o orderColuna para ordenacao decrescente
            List<T> l = this.sessao.createCriteria(classe).addOrder(Order.desc(orderColuna)).list();
            //conclui a transacao
            tx.commit();
            //retorna uma lista do tipo da entidade
            return l;
        } catch (HibernateException e) {
            throw e;
        }

    }

    @Override
    public T pegar(String hql) {

        Transaction tx = null;
        try {
            //recupera a sessao atual
            this.sessao = HibernateUtil.getSessionFactory().getCurrentSession();
            //inicia uma transacao
            tx = this.sessao.beginTransaction();
            //usa createquery para seleciona o registro no banco
            T t = (T) this.sessao.createQuery(hql).uniqueResult();
            //conclui a transacao
            tx.commit();
            //retorna a entidade
            return t;

        } catch (HibernateException e) {
            throw e;
        }
    }

}
