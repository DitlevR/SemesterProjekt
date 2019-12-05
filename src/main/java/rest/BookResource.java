package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.BookDTO;
import dtos.BookLend;
import dtos.UserDTO;
import entities.Book;
import entities.User;
import errorhandling.MissingInputException;
import errorhandling.NotFoundException;
import utils.EMF_Creator;
import facades.BookFacade;
import facades.UserFacade;
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
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPIDefinition(
        info = @Info(
                title = "Book API",
                version = "0.1",
                description = "Simple API to get info about Books.",
                contact = @Contact(name = "Ditlev Andersen", email = "cph-di22@cphbusiness.dk")
        ),
        tags = {
            @Tag(name = "Book", description = "API related to Books")

        },
        servers = {
            @Server(
                    description = "For Local host testing",
                    url = "http://localhost:8080/semesterprojekt"
            ),
            @Server(
                    description = "Server API",
                    url = "http://idon.dk/semesterprojekt"
            )

        }
)


@Path("book")
public class BookResource {

    private static EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;
    private static final BookFacade FACADE = BookFacade.getBookFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final UserFacade USERFACADE = UserFacade.getUserFacade(EMF);

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String book() {

        return "{\"Book\":" + "}";
    }

    @Path("allbooks")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Operation(summary = "Get allbooks",
            tags = {"person"},
            responses = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "All books"),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "No books found")})
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

        for (Book b : books) {
            result.add(new BookDTO(b));
        }

        return GSON.toJson(result);
    }

    @Path("loanbook/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String setBookToLoan(@PathParam("id") long id) {
        BookDTO dto = new BookDTO(FACADE.setBookToLoaned(id));
        return GSON.toJson(dto);
    }

    @Path("add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String saveBook(String book) throws MissingInputException {
        Book newbook = GSON.fromJson(book, Book.class);
        newbook = FACADE.saveBook(newbook.getTitle(), newbook.getDescription(), newbook.getPageNumber(), newbook.getYear());
        return GSON.toJson(newbook);
    }

    @Path("getbook/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getBook(@PathParam("id") long id) throws NotFoundException {
        BookDTO dto = new BookDTO(FACADE.getBook(id));
        return GSON.toJson(dto);
    }

    @Path("userLoanBook")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String userLoanBook(String json) throws NotFoundException {
        BookLend booklend = GSON.fromJson(json, BookLend.class);
        User user = USERFACADE.userloanBook(booklend.getUsername(), booklend.getBook_id());
        return GSON.toJson(new UserDTO(user));
        
    }
    
    @Path("userReturnBook")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String returnBook(String json) throws NotFoundException {
        BookLend booklend = GSON.fromJson(json, BookLend.class);
        User user = USERFACADE.userReturnBook(booklend.getUsername(), booklend.getBook_id());
        return GSON.toJson(new UserDTO(user));
    }

}
