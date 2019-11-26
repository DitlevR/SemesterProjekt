/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Author;
import entities.Book;

/**
 *
 * @author Rumle
 */
public class BookDTO {
    private long id;
    private String title;
    private String description;
    private int pageNumber;
    private String author;
    private int year;
    private boolean status;
    
    public BookDTO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.description = book.getDescription();
        this.pageNumber = book.getPageNumber();
        this.author = book.getAuthor().getName();
        this.year = book.getYear();
        this.status = book.isStatus();
    }
    
    
    
}
