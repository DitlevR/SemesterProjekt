package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;


@Entity
@NamedQuery(name = "Book.deleteAllRows", query = "DELETE from Book")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private int pageNumber;
    
    @ManyToOne
    private Author author;
    
    public Book() {
    }

    public Book(String title, String description, int pageNumber) {
        this.title = title;
        this.description = description;
        this.pageNumber = pageNumber;
    }

    public Book(String title, String description, int pageNumber, Author author) {
        this.title = title;
        this.description = description;
        this.pageNumber = pageNumber;
        this.author = author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
        
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
   
}
