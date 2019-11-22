package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.BooksDTO;
import entities.Book;
import errorhandling.NotFoundException;
import utils.EMF_Creator;
import facades.BookFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import net.minidev.json.JSONArray;
import static org.eclipse.persistence.expressions.ExpressionOperator.count;

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
    public String getAllBooks() throws NotFoundException{
        return "{\"Books\":" + GSON.toJson(new BooksDTO(FACADE.getAllBooks())) + "}";  //Done manually so no need for a DTO
    }

}