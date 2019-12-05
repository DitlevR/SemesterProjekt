/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import entities.Author;
import entities.Book;
import entities.DateOfLoan;
import entities.User;
import errorhandling.MissingInputException;
import facades.UserFacade;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
            EMF_Creator.Strategy.DROP_AND_CREATE);//DROP_AND_CREATE CREATE
    public static final UserFacade FACADE = UserFacade.getUserFacade(EMF);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MissingInputException {
        Persistence.generateSchema("pu", null);

        EntityManager em = EMF.createEntityManager();

//        createUser() fra UserFacade virker
//        FACADE.createUser("Anne", "blitter");

//        createUser() fra UserFacade virker
//        FACADE.createUser("Emner", "112itter");
//        FACADE.createUser("Ulrik", "1rtef5er");
        Author a1 = new Author("Hunter S. Thompson");
        Author a2 = new Author("Grethe Jacobsen");
        Author a3 = new Author("Gro Steinsland");
//        Author a4 = new Author("Jules Verne"); 

        Book b1 = new Book("Nordisk mytologi", "Beskrivelse af gammel nordisk mytologi og samfund", 502, a3, 1988);
        Book b2 = new Book("Runer", "Om runernes funktion og oprindelse", 332, a3, 2003);
        Book b3 = new Book("Frygt og lede i Las Vegas", "En journalist og hans advokat tager på stof drevet eventyr i lysenes by", 219, a1, 1971);
        Book b4 = new Book("Hekse i et kolonisamfund", "Om hekse i kolonisamfund", 127, a2, 1977);
        Book b5 = new Book("Adelskvinder og politik i renæssancens Danmark", "Danske adelskvinders involvering i renæssance politik", 203, a2, 1960);
        Book b6 = new Book("Hell's Angels", "Journalist lever blandt Hell's Angels i et helt år", 543, a1, 1967);
        Book b7 = new Book("Rom dagbogen", "En journalist flytter til Puerto Rico", 233, a1, 1998);
//        Book b8 = new Book("Rejsen til jordens midte", "En professor, hans nevø og deres guide forsøger at når jordens midte", 450, a4, 1867);
//        Book b9 = new Book("Rejsen til månen", "En gruppe mænd forsøger at rejse til månen", 480, a4, 1868);
//
//          List<Book> booklist3 = new ArrayList<>();
//          booklist3.add(b8);
//          booklist3.add(b9);
//          User u3 = new User("Freja", "linnedspinner");  
//          u3.setBooklist(booklist3);
//      
          
        //u2.setDateManual(1995, 4, 12);        
        User u1 = new User("Anne", "imorgeniaften");
        User u2 = new User("Sigurd", "ormiøje");
        List<Book> booklist1 = new ArrayList<>();
        List<Book> booklist2 = new ArrayList<>();
        
        booklist1.add(b1);        
        DateOfLoan dol1 = new DateOfLoan();
        dol1.setUser(u1);
        dol1.setBook(b1);
        
        booklist1.add(b2);
        DateOfLoan dol2 = new DateOfLoan();
        dol2.setUser(u1);
        dol2.setBook(b2);
        
        booklist1.add(b3);
        DateOfLoan dol3 = new DateOfLoan();
        dol3.setUser(u1);
        dol3.setBook(b3);
        
        booklist2.add(b4);
        DateOfLoan dol4 = new DateOfLoan();
        dol4.setUser(u2);
        dol4.setBook(b4);
        
        booklist2.add(b5);
        DateOfLoan dol5 = new DateOfLoan();
        dol5.setUser(u2);
        dol5.setBook(b5);
        
        u1.setBooklist(booklist1);
        u2.setBooklist(booklist2);

        try {
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
            em.persist(u1);
            em.persist(u2);
            em.persist(dol1);
            em.persist(dol2);
            em.persist(dol3);
            em.persist(dol4);
            em.persist(dol5);
            
//            em.persist(u3);
//            em.persist(a4);
//            em.persist(b8);
//            em.persist(b9);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

}
