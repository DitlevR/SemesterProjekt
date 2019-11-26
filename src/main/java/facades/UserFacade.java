package facades;

import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import errorhandling.AuthenticationException;
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

//public User loanBook(String username, int id) {
//    EntityManager em = emf.createEntityManager();
//    User user;
//    
//    
//}
}
