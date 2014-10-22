/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import org.hibernate.SessionFactory;

/**
 *
 * @author Deivid
 * 
 * filtro que filtra todas as paginas jsf que usam a sessao criada pelo hibernate
 */
@WebFilter(filterName = "hibernateFilter",
        servletNames = { "Faces Servlet" },//servltes do jsf
        urlPatterns = "*.jsf")//apenas paginas com extensao jsf
public class HibernateFilter implements Filter {

    private SessionFactory sf;

    //primeiro é inicializado a fabrica de sessoes
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.sf = HibernateUtil.getSessionFactory();
        System.out.println("sessao iniciada");
    }

    //quando ocorre uma requisicao ou resposta
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            //inicia uma transacao
            this.sf.getCurrentSession().beginTransaction();
            chain.doFilter(request, response);
            //conclui a transacao
            this.sf.getCurrentSession().getTransaction().commit();

        } catch (Exception e) {

            try {
                //caso surge algum erro
                //verifica se a transacao esta ativa
                if (this.sf.getCurrentSession().getTransaction().isActive()) {
                    //se estiver volta toda a transacao inicial
                    this.sf.getCurrentSession().getTransaction().rollback();
                }

            } catch (Exception ee) {
            }
        }finally{
            //e por ultimo finaliza a sessao atual
            this.sf.getCurrentSession().close();
            System.out.println("sessao fechada");
            
        }
    }

    @Override
    public void destroy() {
        //se a aplicacao for finalizada destruir a fabrica de sessao
        this.sf.close();
        System.out.println("sessao destruida");
    }

}
