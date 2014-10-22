/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entidades.Cao;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author Deivid
 */
public class HibernateUtil {

    /**fabrica de sessao do hibernate*/
    private static final SessionFactory sessionFactory;

    static {
        try {
            // configura o hibernate dinamicamente

            sessionFactory = new AnnotationConfiguration()
                    .addPackage("entidades")//pacote das entidades
                    .addAnnotatedClass(Cao.class)//entidade mapeada
                    .setProperty("hibernate.session_factory_name", "hibernateSessionFactory")//nome da fabrica de sessao
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")//dialeto do banco de dados
                    .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")//driver do banco de dados
                    .setProperty("hibernate.connection.url", "jdbc:postgresql://192.168.0.9:5432/teste?charSet=UTF-8")//url da conexao com o banco de dados
                    .setProperty("hibernate.connection.username", "postgres")//usuario do banco de dados
                    .setProperty("hibernate.connection.password", "123")//senha do banco de dados
                    .setProperty("hibernate.current_session_context_class", "thread")//estrategia do scopo da sessao atual como thread(varias sessoes em paralelo)
                    .setProperty("hibernate.cache.use_second_level_cache", "true")//usar  cache de segundo nivel
                    .setProperty("hibernate.transaction.auto_close_session", "true")//fechar a transacao automatico
                    .setProperty("hibernate.auto_close_session", "true")//fechar a sessao automatico
                    .setProperty("hibernate.hbm2ddl.auto", "update")//cria as tabelas mapeadas no banco de dados caso nao existem
                    .setProperty("hibernate.show_sql", "true")//mostra no console de saida os comandos sql
                    .setProperty("hibernate.format_sql", "true")//formata os comandos sql
                    .buildSessionFactory();//retorna uma fabrica de sessao
        } catch (HibernateException ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
