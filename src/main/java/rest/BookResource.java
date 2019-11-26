package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.BookDTO;
import entities.Book;
import errorhandling.NotFoundException;
import utils.EMF_Creator;
import facades.BookFacade;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

//Todo Remove or change relevant parts before ACTUAL use
@Path("book")
public class BookResource {

    private static EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;
    private static final BookFacade FACADE = BookFacade.getBookFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String book() {

        return "{\"Book\":" + "}";
    }

    @Path("allbooks")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    //@RolesAllowed("user")
    public String getAllBooks() throws NotFoundException {
        //BooksDTO allbooks = new BooksDTO(FACADE.getAllBooks());
        List<BookDTO> allbooks = new ArrayList<>();
        List<Book> books = FACADE.getAllBooks();
        for (Book b : books) {
            allbooks.add(new BookDTO(b));
        }
        

        return GSON.toJson(allbooks);
    }

    @Path("{search}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String makeSearch(@PathParam("search") String search) throws NotFoundException {
        List<BookDTO> result = new ArrayList<>();
        List<Book> books = FACADE.searchForBook(search);
        
        for(Book b : books) {
            result.add(new BookDTO(b));
        }

        return GSON.toJson(result);
    }
    
    @Path("add/loan")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String setBookToLoan(String info) {
        //String id = GSON.fromJson(, );
                return "hej";
    }
    
    @Path("add/book")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String saveBook(String book) {
        BookDTO savebook = GSON.fromJson(book, BookDTO.class);
        return GSON.toJson(savebook);
    }

}
