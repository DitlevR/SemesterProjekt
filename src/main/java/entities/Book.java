package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "books")
@NamedQuery(name = "Book.deleteAllRows", query = "DELETE from Book")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "book_title")
    private String title;
    @Column(name = "book_description")
    private String description;
    @Column(name = "book_pagenumber")
    private int pageNumber;
    @Column(name = "book_year")
    private int year;
    @Column(name = "book_status")
    private boolean status;
    
    @JoinColumn(name = "book_author")
    @ManyToOne
    private Author author;
    
    public Book() {
    }

    public Book(String title, String description, int pageNumber, int year) {
        this.title = title;
        this.description = description;
        this.pageNumber = pageNumber;
        this.year = year;
        this.status = false;
    }

    public Book(String title, String description, int pageNumber, Author author, int year) {
        this.title = title;
        this.description = description;
        this.pageNumber = pageNumber;
        this.author = author;
        this.year = year;
        this.status = false;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isStatus() {
        return status;
    }  
    
    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", title=" + title + ", description=" + description + ", pageNumber=" + pageNumber + ", author=" + author + '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Author getAuthor() {
        return author;
    }
    
   
}
