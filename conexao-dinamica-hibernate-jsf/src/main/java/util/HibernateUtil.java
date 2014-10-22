/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entidade.Conexao;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * cria uma e retorna uma fabrica de sessao
 *
 * @author Deivid
 */
public class HibernateUtil implements Serializable {

    //fabrica de sessao
    public static SessionFactory sessionFactory;
    //conexao usada para acessar o banco
    public static Conexao conexao;
    public static boolean conectado;
    public static boolean status;

    public static boolean criarFabricaDeSessoes() {
        try {
            //nome do apcote que contem todas as classes de entidades
            String pacote = "entidade";
            //configuracao inicial mapeando as entidades
            AnnotationConfiguration anotacao = new AnnotationConfiguration()
            .addPackage(pacote);
            for (Class<?> entidade : Util.listarClassesDeUmPacote(pacote)) {
                anotacao.addAnnotatedClass(entidade);
                System.out.println(entidade + " mapeada");
            }
            // configura o hibernate dinamicamente
            SessionFactory sf = anotacao
                    .setProperty("hibernate.session_factory_name", "hibernateSessionFactory")//nome da fabrica de sessao
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")//dialeto do banco de dados
                    .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")//driver do banco de dados
                    .setProperty("hibernate.connection.url",
                            "jdbc:postgresql://192.168.0.9:5432/" + HibernateUtil.conexao.getBanco() + "?charSet=UTF-8")//url da conexao com o banco de dados
                    .setProperty("hibernate.connection.username", "postgres")//usuario do banco de dados
                    .setProperty("hibernate.connection.password", "123")//senha do banco de dados
                    .setProperty("hibernate.current_session_context_class", "thread")//estrategia do scopo da sessao atual como thread(varias sessoes em paralelo)
                    .setProperty("hibernate.cache.use_second_level_cache", "true")//usar  cache de segundo nivel
                    .setProperty("hibernate.transaction.auto_close_session", "true")//fechar a transacao automatico
                    .setProperty("hibernate.auto_close_session", "true")//fechar a sessao automatico
                    //.setProperty("hibernate.hbm2ddl.auto", "update")//cria as tabelas mapeadas no banco de dados caso nao existem
                    .setProperty("hibernate.show_sql", "true")//mostra no console de saida os comandos sql
                    //  .setProperty("hibernate.format_sql", "true")//formata os comandos sql     
                    .setProperty("hibernate.connection.pool_size", "20")//numero maximo de conexao disponiveis pelo hibernate
                    .buildSessionFactory();//retorna uma fabrica de sessao

            HibernateUtil.sessionFactory = sf;
            HibernateUtil.conectado = true;
            status = true;
            return true;
        } catch (HibernateException ex) {
            util.Util.criarMensagem("Verifique a conexao!");
            HibernateUtil.conectado = false;
            status = false;
            return false;
        }
    }

    /**
     * *
     * metodo que retorna a fabrica de sessao criada
     *
     * @return a fabrica de sessao
     */
    public static SessionFactory getSessionFactory() {
        //pega o caminho do contexto da aplicao
        String u = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        //se a fabrica de sessao for nula
        if (HibernateUtil.sessionFactory == null) {
            HibernateUtil.conectado = false;
            try {
                //redireciona para a pagina de conexao
                FacesContext.getCurrentInstance().getExternalContext().redirect(u + "/conexao.jsf");
            } catch (IOException ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //retorna a fabrica de sessao
        return sessionFactory;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        HibernateUtil.status = status;
    }

}
