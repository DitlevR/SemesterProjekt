package facades;

import entities.Book;
import errorhandling.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class BookFacade {

    private static BookFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private BookFacade() {
    }
    
     public static BookFacade getBookFacade (EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BookFacade();
        }
        return instance;
    }

    public List<Book> getAllBooks() throws NotFoundException {
        EntityManager em = getEntityManager();
        List<Book> allBooks = new ArrayList<Book>();
        try {
            allBooks = em.createQuery("select b from Book b", Book.class).getResultList();

            if (allBooks.size() == 0 || allBooks == null) {
                throw new NotFoundException("No books found");
            }
        } finally {
            em.close();
        }
        return allBooks;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

}
