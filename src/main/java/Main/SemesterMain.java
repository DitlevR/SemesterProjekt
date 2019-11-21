/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import entities.Author;
import entities.Book;
import facades.UserFacade;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import utils.EMF_Creator;

/**
 *
 * @author Ludvig
 */
public class SemesterMain {
    
     public static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/sem3project",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
     public static final UserFacade FACADE =  UserFacade.getUserFacade(EMF);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Persistence.generateSchema("pu", null);
        
        EntityManager em = EMF.createEntityManager();
        
        Author a1 = new Author("Hunter S. Thompson");
        Author a2 = new Author("Grethe Jacobsen");
        Author a3 = new Author("Gro Steinsland");
        
        
        Book b1 = new Book("Nordisk mytologi", "Beskrivelse af gammel nordisk mytologi og samfund", 502, a3);
        Book b2 = new Book("Runer", "Om runernes funktion og oprindelse", 332, a3);
        Book b3 = new Book("Frygt og lede i Las Vegas", "En journalist og hans advokat tager på stof drevet eventyr i lysenes by", 219, a1);
        Book b4 = new Book("Hekse i et kolonisamfund", "Om hekse i kolonisamfund", 127, a2);
        Book b5 = new Book("Adelskvinder og politik i renæssancens Danmark", "Danske adelskvinders involvering i renæssance politik", 203, a2);
        Book b6 = new Book("Hell's Angels", "Journalist lever blandt Hell's Angels i et helt år", 543, a1);
        Book b7 = new Book("Rom dagbogen", "En journalist flytter til Puerto Rico", 233, a1);
        
        //em.persist(b);
        try{
             em.getTransaction().begin();
             em.persist(a1);
             em.persist(a2);
             em.persist(a3);
             em.persist(b1);
             em.persist(b2);
             em.persist(b3);
             em.persist(b4);
             em.persist(b5);
             em.persist(b6);
             em.persist(b7);
             em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
}
