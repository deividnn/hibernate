/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidade;

import java.io.Serializable;

/**
 *
 * @author Deivid
 */
public class Conexao implements Serializable {

    public String banco;

    public Conexao() {
    }

    public Conexao(String banco) {
        this.banco = banco;

    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

}
