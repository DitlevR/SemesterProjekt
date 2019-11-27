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

    public static BookFacade getBookFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BookFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
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
    
    public List<Book> searchForBook(String search) throws NotFoundException {
        
        List<Book> all = getAllBooks();
        List<Book> result = new ArrayList<>();
        for(int i = 0; i < all.size(); i++) {
            if(all.get(i).toString().contains(search)) {
                result.add(all.get(i));
            } 
            
        }
        return result;
    
        
    }
    
    public Book getBook(long id) throws NotFoundException {
         EntityManager em = getEntityManager();
         Book book;
         try {
             
             book = em.find(Book.class, id);
             
             if(book == null) {
                 throw new NotFoundException("Book not found");
             }
             return book;
         } finally {
             em.close();
         }
    }
    
    public Book setBookToLoaned(long id) {
         EntityManager em = getEntityManager();
         Book book;
         try {
             em.getTransaction().begin();
             book = em.find(Book.class, id);
             book.setStatus(false);
             em.persist(book);
             em.getTransaction().commit();
             return book;
             
         } finally {
             em.close();
         }
    }
    
    

}
