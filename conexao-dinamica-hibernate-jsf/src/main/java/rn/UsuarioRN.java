/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rn;

import dao.HibernateDAO;
import dao.UsuarioDAO;
import entidade.Usuario;
import java.util.List;

/**
 * regras de negocio para gerencia um usuario
 *
 * @author Deivid
 */
public class UsuarioRN {

    //dao com os metodos de persistencia
    private final HibernateDAO<Usuario> dao;

    public UsuarioRN() {
        //inicializa o dao com a sessao e a entidade do usuario
        this.dao = new UsuarioDAO();
    }

    /**
     * *
     * metodo que salva ou atualiza o usuario verificando a integridade e outras
     * regras de negocio
     *
     * @param usuario que sera persistido
     * @return true ou false
     */
    public boolean salvar(Usuario usuario) {
        //booleano que sera retornado
        boolean ok = false;
        //se o usuario tiver id nulo ele entra no modo de insercao
        if (usuario.getId() == null) {
            //hql que ira seleciona um usuario de mesmo usuario do usuario que ira ser salvo
            String hql = "SELECT u FROM Usuario u"
                    + " WHERE u.usuario='" + usuario.getUsuario() + "'";
            //armazena o usuario da consulta na entidade verifica
            Usuario verifica = this.dao.pegar(hql);

            //se verifica for nulo
            if (verifica == null) {
                //salva o usuario
                this.dao.salvar(usuario);
                //mostra a mensagem na pagina
                util.Util.criarMensagem("usuario salvo");
                //retorna true
                ok = true;

            } else {//se verifica for diferente de nulo
                //quer dizer que ja existe um usuario de mesmo usuario
                //mostra mensagem
                util.Util.criarMensagem("usuario ja existe");
                //retorna false
                ok = false;
            }
        } else {//se o id do usuario for diferente de nulo ele entra no mode de atualizacao
            //hql que verifica se o usuario que esta tentando ser atualizado
            //ja existe,ignorando o proprio id do usuario
            String hql = "SELECT u FROM Usuario u"
                    + " WHERE u.usuario='" + usuario.getUsuario() + "'"
                    + " AND u.id!=" + usuario.getId() + "";
            //armazena o usuario da consulta na entidade
            Usuario verifica = this.dao.pegar(hql);

            //se verifica for nulo
            if (verifica == null) {
                //atualiza o usuario
                this.dao.atualizar(usuario);
                //mostra mensagem
                util.Util.criarMensagem("usuario atualizado");
                //retorna verdadeiro
                ok = true;

            } else {//se verifica for diferente de nulo
                //que dizer que ja existe um usuario de mesmo usuario
                //mostra mensagem
                util.Util.criarMensagem("usuario ja existe");
                //retorna false
                ok = false;
            }
        }
        //retorna o boleano ok
        return ok;
    }

    /**
     * *
     * exclui o usuario sem fazer verificacao de integridade ou outras regras de
     * negocio
     *
     * @param usuario que sera excluido
     */
    public void excluir(Usuario usuario) {
        //exclui o usuario
        this.dao.excluir(usuario);
        //mostra mensagem
        util.Util.criarMensagem("usuario excluido");
    }

    /**
     * *
     * consulta os usuarios no banco de dados
     *
     * @return a lista de usuario
     */
    public List<Usuario> listar() {
        //consulta a tabela usuario em ordem decrescente pelo id
        return this.dao.listar("id");
    }

    /**
     * *
     * consulta um usuario usando uma instrucao hql
     *
     * @param hql comando hql
     * @return um usuario
     */
    public Usuario pegar(String hql) {
        return this.dao.pegar(hql);
    }
}
