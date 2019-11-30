package facades;

import entities.Book;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import errorhandling.AuthenticationException;
import errorhandling.MissingInputException;
import errorhandling.NotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lam@cphbusiness.dk
 */
public class UserFacade {
  
    private static EntityManagerFactory emf;
    private static UserFacade instance;
    
    private UserFacade(){}
    
    /**
     * 
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade (EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }
    
    public User createUser(String username, String password) throws MissingInputException {
        EntityManager em = emf.createEntityManager();
        
        if(username == null || password == null) {
            throw new MissingInputException();
        }
        
        User user = new User(username, password);
        
        try{
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        }finally{
            em.close();
        }
        return user;
    }
    
    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password, user.getUserPass())) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }
    
    public List<User> getAllUsers() {
       EntityManager em = emf.createEntityManager();
       List<User> allusers = new ArrayList<>();
       try {
           em.getTransaction().begin();
           allusers = em.createQuery("select u from User u", User.class).getResultList();
           em.getTransaction().commit();
           return allusers;
       } finally {
           em.close();
       }
       
    }
public User getUser(String username) throws NotFoundException {
    EntityManager em = emf.createEntityManager();
    User user;
    try {
        user = em.find(User.class, username);
        if(user == null) {
            throw new NotFoundException("No user found");
        }
        return user;
    } finally {
        em.close();
    }
}

public User userloanBook(String username, long id) throws NotFoundException {
    EntityManager em = emf.createEntityManager();
    User user;
    Book book;
    
    try {
        em.getTransaction().begin();
        user = em.find(User.class, username);
        book = em.find(Book.class, id);
        user.LoanBook(book);
        em.merge(user);
        em.getTransaction().commit();
        if(user == null || book == null) {
            throw new NotFoundException("User or book not found");
        }
        return user;
        
        
    } finally {
        em.close();
        
    }
    
    
    
    
}

public User userReturnBook(String username, long book_id) throws NotFoundException {
    EntityManager em = emf.createEntityManager();
    User user;
    Book book;
    try {
        em.getTransaction().begin();
        user = em.find(User.class, username);
        book = em.find(Book.class, book_id);
        if(!user.getBooklist().contains(book)) {
             throw new NotFoundException("user has not that book as loaned");
        }
        user.removeLoanedBook(book);
        em.merge(user);
        em.getTransaction().commit();
         if(user == null || book == null) {
            throw new NotFoundException("User or book not found");
        }
        return user;
        
        
    } finally {
        em.close();
    }
}
}
