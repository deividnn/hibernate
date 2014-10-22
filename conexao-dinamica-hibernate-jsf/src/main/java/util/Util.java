/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.primefaces.context.RequestContext;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

/**
 *
 * @author Deivid
 *
 * metodos estaticos que podem ser usados em todas as classes da aplicacao
 */
public class Util {

    /**
     * retorna uma sessao gerada na fabrica de sessoe
     *
     * @return s
     */
    public static Session pegaSessaoAtual() {

        return HibernateUtil.getSessionFactory().getCurrentSession();
    }

    /**
     * limpa todos os campos de um formulari
     *
     * @param idForm idForm id do formulario
     */
    public static void resetarFormulario(String idForm) {
        RequestContext.getCurrentInstance().reset(idForm);
    }

    /**
     * cria uma mensagem que sera mostrada no p:growl do primeface
     *
     * @param texto texto é a mensagem
     */
    public static void criarMensagem(String texto) {
        FacesMessage mesagem = new FacesMessage(texto);
        FacesContext.getCurrentInstance().addMessage(texto, mesagem);
    }

    /**
     * *
     * executa um comando javascript na pagina jsf
     *
     * @param comando javascript
     */
    public static void executarJavascript(String comando) {
        RequestContext.getCurrentInstance().execute(comando);
    }

    /**
     * *
     * atualiza um formulario do jsf
     *
     * @param form id do formulario
     */
    public static void atualizarForm(String form) {
        RequestContext.getCurrentInstance().update(form);
    }

    /**
     * *
     * converte uma string em md5
     *
     * @param s string que sera convertida
     * @return string em md5
     */
    public static String converterStringEmMD5(String s) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(s.getBytes(), 0, s.length());
            BigInteger i = new BigInteger(1, m.digest());
            s = String.format("%1$032x", i);
        } catch (NoSuchAlgorithmException e) {
        }
        return s;
    }

    /**
     * *
     * cria uma sessao para o objeto e referencia com o nome
     *
     * @param obj objeto que sera armazenado na sessao
     * @param nome nome da sessao do objeto
     */
    public static void criarObjetoDeSessao(Object obj, String nome) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        session.setAttribute(nome, obj);
    }

    /**
     * *
     * retorna todas as classe de um pacote
     *
     * @param pacote nome do pacote que sera escaneado
     * @return lista de classes do pacote
     */
    public static Set<Class<?>> listarClassesDeUmPacote(String pacote) {
        Reflections ref = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false /* don't exclude Object.class */),
                        new ResourcesScanner())
                .setUrls(ClasspathHelper.forPackage(pacote))
                .filterInputsBy(new FilterBuilder().
                        include(FilterBuilder.prefix(pacote))));
        Set<Class<?>> classes = ref.getSubTypesOf(Object.class);
        return classes;
    }
    
   
}
