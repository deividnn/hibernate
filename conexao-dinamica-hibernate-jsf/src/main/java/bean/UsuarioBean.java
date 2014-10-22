/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entidade.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import rn.UsuarioRN;

/**
 *
 * @author Deivid
 *
 * Bean que gerencia a pagina de usuarios
 */
@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable {

    //usuario que sera gerenciado
    private Usuario usuario;
    //lista de usuarios recuperado do banco
    private List<Usuario> lista;
    
    /**
     * inicializa o bean antes de chamar a pagina que usa ele
     *
     */
    @PostConstruct
    public void init() {
        //metodos que inicializa atributos do bean
        iniciarUsuario();
    }

    /**
     * salva ou atualiza um usuario
     *
     */
    public void salvar() {
        //se a operacao retorna true        
        if (new UsuarioRN().salvar(this.usuario)) {
            //instaciliza atributos do bean 
            iniciarUsuario();
            //e limpa o formulario
            resetarForm();
        }
    }

    /**
     * exclui um usuario
     *
     * @param usuario
     */
    public void excluir(Usuario usuario) {
        //apenas exclui sem verificacao
        new UsuarioRN().excluir(usuario);
        //instaciliza atributos do bean 
        iniciarUsuario();
        //e limpa o formulario
        resetarForm();
    }

    /**
     * prepara o formulario para editar um usuario
     *
     * @param usuario
     */
    public void editar(Usuario usuario) {
        //seta o usuario do bean como o usuario pego da lista
        this.usuario = usuario;
        //e limpa o formulario
        resetarForm();
    }

    /**
     * limpa o formulario do usuario
     *
     */
    private void resetarForm() {
        //metodo que limpa o formulario de acordo o id dele
        util.Util.resetarFormulario("form");
    }

    /**
     * retira o formulario do modo de edicao
     *
     */
    public void cancelar() {
        //instaciliza atributos do bean 
        iniciarUsuario();
        //e limpa o formulario
        resetarForm();
    }

    /**
     * instancializa os atributos do bean
     *
     */
    private void iniciarUsuario() {
        this.usuario = new Usuario();
        this.lista = null;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getLista() {
        //verifica se a lista de usuario esta nula
        if (this.lista == null) {
            //se estiver faz uma consulta no banco de dados
            this.lista = new UsuarioRN().listar();

        }
        return lista;
    }

    public void setLista(List<Usuario> lista) {
        this.lista = lista;
    }

}
