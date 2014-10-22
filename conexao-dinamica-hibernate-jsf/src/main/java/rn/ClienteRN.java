/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rn;

import dao.HibernateDAO;
import dao.ClienteDAO;
import entidade.Cliente;
import java.util.List;

/**
 * regras de negocio para gerencia um cliente
 *
 * @author Deivid
 */
public class ClienteRN {

    //dao com os metodos de persistencia
    private final HibernateDAO<Cliente> dao;

    public ClienteRN() {
        //inicializa o dao com a sessao e a entidade do cliente
        this.dao = new ClienteDAO();
    }

    /**
     * *
     * metodo que salva ou atualiza o cliente verificando a integridade e outras
     * regras de negocio
     *
     * @param cliente que sera persistido
     * @return true ou false
     */
    public boolean salvar(Cliente cliente) {
        //booleano que sera retornado
        boolean ok = false;
        //se o cliente tiver id nulo ele entra no modo de insercao
        if (cliente.getId() == null) {
            //hql que ira seleciona um cliente de mesmo nome do cliente que ira ser salvo
            String hql = "SELECT u FROM Cliente u"
                    + " WHERE u.nome='" + cliente.getNome() + "'";
            //armazena o cliente da consulta na entidade verifica
            Cliente verifica = this.dao.pegar(hql);

            //se verifica for nulo
            if (verifica == null) {
                //salva o cliente
                this.dao.salvar(cliente);
                //mostra a mensagem na pagina
                util.Util.criarMensagem("cliente salvo");
                //retorna true
                ok = true;

            } else {//se verifica for diferente de nulo
                //quer dizer que ja existe um cliente de mesmo nome
                //mostra mensagem
                util.Util.criarMensagem("cliente ja existe");
                //retorna false
                ok = false;
            }
        } else {//se o id do cliente for diferente de nulo ele entra no mode de atualizacao
            //hql que verifica se o cliente que esta tentando ser atualizado
            //ja existe,ignorando o proprio id do cliente
            String hql = "SELECT u FROM Cliente u"
                    + " WHERE u.nome='" + cliente.getNome() + "'"
                    + " AND u.id!=" + cliente.getId() + "";
            //armazena o cliente da consulta na entidade
            Cliente verifica = this.dao.pegar(hql);

            //se verifica for nulo
            if (verifica == null) {
                //atualiza o cliente
                this.dao.atualizar(cliente);
                //mostra mensagem
                util.Util.criarMensagem("cliente atualizado");
                //retorna verdadeiro
                ok = true;

            } else {//se verifica for diferente de nulo
                //que dizer que ja existe um cliente de mesmo nome
                //mostra mensagem
                util.Util.criarMensagem("cliente ja existe");
                //retorna false
                ok = false;
            }
        }
        //retorna o boleano ok
        return ok;
    }

    /**
     * *
     * exclui o cliente sem fazer verificacao de integridade ou outras regras de
     * negocio
     *
     * @param cliente que sera excluido
     */
    public void excluir(Cliente cliente) {
        //exclui o cliente
        this.dao.excluir(cliente);
        //mostra mensagem
        util.Util.criarMensagem("cliente excluido");
    }

    /**
     * *
     * consulta os clientes no banco de dados
     *
     * @return a lista de cliente
     */
    public List<Cliente> listar() {
        //consulta a tabela cliente em ordem decrescente pelo id
        return this.dao.listar("id");
    }

    /**
     * *
     * consulta um cliente usando uma instrucao hql
     *
     * @param hql comando hql
     * @return um cliente
     */
    public Cliente pegar(String hql) {
        return this.dao.pegar(hql);
    }
}
