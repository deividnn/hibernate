/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entidade.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import rn.UsuarioRN;
import entidade.Conexao;
import util.HibernateUtil;

/**
 *
 * @author Deivid
 *
 * Bean que gerencia a pagina de conexao ao servidor de banco de dados
 */
@ManagedBean
@RequestScoped
public class ConexaoBean implements Serializable {

    //conexao que possui o banco que sera acessada
    private Conexao conexao;
    //mensagens em geral
    public String mensagem;
    //status da conexao ao banco de dados
    private boolean conectado;
    //dados do usuario que fara autenticacao no banco de dados ja conectado
    private Usuario usuarioLogin;

    /**
     * inicializa o bean antes de chamar a pagina que usa ele
     */
    @PostConstruct
    public void init() {
        FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        //desconecta do servidor atual
        desconectar();
        //instaciliza o usuario
        this.usuarioLogin = new Usuario();
        //abre o dialog da conexao para inserir dados do formulario
        util.Util.executarJavascript("PF('conexao').show();");
    }

    /**
     * metodo que desconecta do servidor atual
     */
    public void desconectar() {
        //atribui null a fabrica de sessoes
        HibernateUtil.sessionFactory = null;
        HibernateUtil.conectado = false;
        HibernateUtil.status = false;
        this.conexao = new Conexao();
        //banco padrao
        this.conexao.setBanco("teste");
        this.conectado = false;
        //atribui a conexao do bean a conexao da fabrica de sessoes
        HibernateUtil.conexao = this.conexao;

    }

    /**
     * conecta ao banco de dados
     *
     * @return pagina destino
     */
    public String conectarBancoDeDados() {
        //atribui conexao do bean a conexao da fabrica de sessoes
        HibernateUtil.conexao = this.conexao;

        //se a fabrica de sessoes for configurada com sucesso
        if (HibernateUtil.criarFabricaDeSessoes()) {
            //pega a fabrica de sessoes
            HibernateUtil.getSessionFactory();
            this.conectado = true;
            HibernateUtil.conectado = this.conectado;
            //e realiza a autenticacao do usuario no banco conectado
            //e se autenticacao for um sucesso redireciona para pagina destino
            return fazerLogin(this.usuarioLogin);

        } else {//se houver erro na conexao do banco de dados
            //mostra  a mensagem
            util.Util.criarMensagem("erro ao conectar o banco de dados");
            //seta o conectado como false
            this.conectado = false;
            HibernateUtil.conectado = this.conectado;
        }
        //permanece na mesma pagina
        return null;
    }

    /**
     * *
     * metodo que realiza autenticacao usando dados do usuario digitado no
     * formulario
     *
     * @param usuario digitado no formulario
     * @return pagina destino
     */
    public String fazerLogin(Usuario usuario) {
        try {
            //HQL usado para consultar um usuario no banco de dados conectado
            String hql = "SELECT u FROM Usuario u"
                    + " WHERE u.usuario='" + usuario.getUsuario() + "'"
                    //funcao que converte a senha do tipo string em md5
                    + " AND u.senha='" + util.Util.converterStringEmMD5(usuario.getSenha()) + "'";

            //consulta o usuario usando o hql
            Usuario usuarioEncontrado = new UsuarioRN().pegar(hql);

            //se o usuarioEncontrado for diferente de nulo
            if (usuarioEncontrado != null) {
                //verifica se ele esta liberado para o acesso
                if (usuarioEncontrado.isAtivo()) {
                    //guarda os dados do usuario na sessao
                    util.Util.criarObjetoDeSessao(usuarioEncontrado, "usuarioLogado");
                    //guarda os dados da conexao na sessao
                    util.Util.criarObjetoDeSessao(this.conexao, "conexao");
                    //redireciona para a pagina usuarios na pasta protegida restrito
                    return "/restrito/usuarios?faces-redirect=true";
                } else {//se o usuario estiver bloqueado

                    //mostra mensagens
                    util.Util.criarMensagem("usuario bloqueado!");
                    //abre dialog do formulario da conexao
                    util.Util.executarJavascript("PF('conexao').show();");
                    //permanece na mesma pagina
                    return null;
                }
            } else {//se o usuario nao for encontrado
                //mostra mensagens
                util.Util.criarMensagem("erro ao fazer login!");
                //abre dialog do formulario da conexao
                util.Util.executarJavascript("PF('conexao').show();");
                //permanece na mesma pagina
                return null;
            }
        } catch (Exception e) {
        }
        //permanece na mesma pagina
        return null;
    }

    /**
     * *
     * faz logout destruindo a sessao e desconectando do banco de dados
     */
    public void logout() {
        //primeiro desconecta do banco de dados
        desconectar();
        //destroi a sessao
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        String u = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        try {
            //redireciona para a pagina de conexao
            FacesContext.getCurrentInstance().getExternalContext().redirect(u + "/conexao.jsf");
        } catch (IOException ex) {
            Logger.getLogger(ConexaoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Conexao getConexao() {
        return conexao;
    }

    public void setConexao(Conexao conexao) {
        this.conexao = conexao;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public boolean isConectado() {
        return conectado;
    }

    public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }

    public Usuario getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(Usuario usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

}
