/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import entidades.Dog;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import util.HibernateUtil;

/**
 *
 * @author Deivid
 */
public class TestarEntidadeDog {

    public TestarEntidadeDog() {
    }

    private Session sessao;

    @Before
    public void setUp() {
        this.sessao = HibernateUtil.getSessionFactory().getCurrentSession();
        this.sessao.beginTransaction();

    }

    @Test
    public void inserirDog() {

        Dog dog = new Dog();
        dog.setName("dog2");

        this.sessao.save(dog);
        this.sessao.getTransaction().commit();
        Assert.assertNotNull(dog.getId());
    }
 

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
