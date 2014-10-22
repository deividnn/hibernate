/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.List;

/**
 *
 * @author Deivid
 * @param <T>
 * 
 * interface com os principais metodos de cadastro,atualizacao,exclusao,busca
 * a interface é generica suportando qualquer entidade mapeada
 */
public interface DAO<T> {
    //metodo de salvar uma entidade
    public void salvar(T t);
    public void atualizar(T t);
    public void excluir(T t);
    public List<T> listar(String colunaOrder);
}
