package facades;

import entities.Author;
import entities.Book;
import entities.DateOfLoan;
import entities.User;
import errorhandling.MissingInputException;
import errorhandling.NotFoundException;
import io.restassured.RestAssured;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class UserFacadeTest {

    private static EntityManagerFactory emf;
    private static UserFacade facade;
    private static User u1, u2;

    public UserFacadeTest() {
    }

    //@BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/startcode_test",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
        facade = UserFacade.getUserFacade(emf);
    }

    /*   **** HINT **** 
        A better way to handle configuration values, compared to the UNUSED example above, is to store those values
        ONE COMMON place accessible from anywhere.
        The file config.properties and the corresponding helper class utils.Settings is added just to do that. 
        See below for how to use these files. This is our RECOMENDED strategy
     */
    @BeforeAll
    public static void setUpClassV2() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
        facade = UserFacade.getUserFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        u1 = new User("John", "qwe123");
        u2 = new User("Hans", "kwhu11");
        Author a3 = new Author("Gro Steinsland");
        Book b1 = new Book("Nordisk mytologi", "Beskrivelse af gammel nordisk mytologi og samfund", 502, a3, 1990);
        Book b2 = new Book("Runer", "Om runernes funktion og oprindelse", 332, a3, 2008);
        u1.loanBook(b2);

        try {

            em.getTransaction().begin();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();

            em.persist(u1);
            em.persist(u2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    public void testGetUserName() throws NotFoundException {
        assertEquals(u1.getUserName(), facade.getUser(u1.getUserName()).getUserName());
    }

    @Test
    public void testGetLoanedBooksFromUser() throws NotFoundException {
        assertEquals(1, facade.getUser(u1.getUserName()).getBooklist().size());
    }

    @Test
    public void testCreateUser() throws NotFoundException, MissingInputException {
        
        
        User user = facade.createUser("Claus", "qweqwe");
        
        Assertions.assertNotNull(user);

    }

}
