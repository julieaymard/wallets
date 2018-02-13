package io.pax.cryptos.jpa;

import io.pax.cryptos.domain.User;
import io.pax.cryptos.domain.jpa.JpaUser;

import javax.persistence.EntityManager;

/**
 * Created by AELION on 13/02/2018.
 */
public class JpaUserDao {
    JpaConnector connector = new JpaConnector();


    public User createUser(String name) {
        JpaUser user = new JpaUser();
        user.setName(name);
        EntityManager em = connector.createEntityManager();
        em.getTransaction().begin();

        em.persist(user);

        em.getTransaction().commit();
        System.out.println("User id:"+user.getId());

        return user;
    }

    public static void main(String[] args) {
        JpaUserDao dao =  new JpaUserDao();

        dao.createUser("Arthur");

        //end of the program
        dao.connector.close();
    }
}
