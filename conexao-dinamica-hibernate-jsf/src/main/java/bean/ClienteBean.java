/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entidade.Cliente;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import rn.ClienteRN;

/**
 *
 * @author Deivid
 *
 * Bean que gerencia a pagina de clientes
 */
@ManagedBean
@ViewScoped
public class ClienteBean implements Serializable {

    //cliente que sera gerenciado
    private Cliente cliente;
    //lista de clientes recuperado do banco
    private List<Cliente> lista;

    /**
     * inicializa o bean antes de chamar a pagina que usa ele
     *
     */
    @PostConstruct
    public void init() {
        //metodos que inicializa atributos do bean
        iniciarCliente();
    }

    /**
     * salva ou atualiza um cliente
     *
     */
    public void salvar() {
        //se a operacao retorna true        
        if (new ClienteRN().salvar(this.cliente)) {
            //instaciliza atributos do bean 
            iniciarCliente();
            //e limpa o formulario
            resetarForm();
        }
    }

    /**
     * exclui um cliente
     *
     * @param cliente
     */
    public void excluir(Cliente cliente) {
        //apenas exclui sem verificacao
        new ClienteRN().excluir(cliente);
        //instaciliza atributos do bean 
        iniciarCliente();
        //e limpa o formulario
        resetarForm();
    }

    /**
     * prepara o formulario para editar um cliente
     *
     * @param cliente
     */
    public void editar(Cliente cliente) {
        //seta o usuario do bean como o usuario pego da lista
        this.cliente = cliente;
        //e limpa o formulario
        resetarForm();
    }

    /**
     * limpa o formulario do cliente
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
        iniciarCliente();
        //e limpa o formulario
        resetarForm();
    }

    /**
     * instancializa os atributos do bean
     *
     */
    private void iniciarCliente() {
        this.cliente = new Cliente();
        this.lista = null;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Cliente> getLista() {
        //verifica se a lista de cliente esta nula
        if (this.lista == null) {
            //se estiver faz uma consulta no banco de dados
            this.lista = new ClienteRN().listar();

        }
        return lista;
    }

    public void setLista(List<Cliente> lista) {
        this.lista = lista;
    }

}
