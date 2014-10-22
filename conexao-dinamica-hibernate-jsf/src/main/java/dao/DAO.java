/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;

/**
 *
 * @author Deivid interface declarando os metodos de operacao de persistencia
 * @param <T> entidade
 */
public interface DAO<T> {

    //salva uma entidade
    public void salvar(T t);

    //atualiza uma entidade
    public void atualizar(T t);

    //exclui uma entidade
    public void excluir(T t);

    /**
     * lista uma entidade
     *
     * @param orderColuna coluna que sera referencia para ordenacao dos registro
     * em decrescente
     * @return lista do tipo da entidade
     */
    public List<T> listar(String orderColuna);

    /**
     * pega uma entidade
     *
     * @param hql comando hql que sera usado para recuperar entidade
     * @return a entidade
     */
    public T pegar(String hql);

}
