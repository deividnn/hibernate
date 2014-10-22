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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * filtro que verifica se o usuario esta logado
 *
 * @author Deivid
 */
@WebFilter(filterName = "loginFilter", urlPatterns = {"/restrito/*"})
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        //se a sessao for nula ou o usuariologado for nulo
        if (session == null || session.getAttribute("usuarioLogado") == null) {
            String contextPath = httpServletRequest.getContextPath();
            String url = contextPath + "/conexao.jsf";
            //redireciona para a pagina de conexao
            httpServletResponse.sendRedirect(url);

        } else { //Caso ele esteja logado, apenas deixamos 
            //que o fluxo continue
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

}
