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
    private String title;
    private String description;
    private int pageNumber;
    private String author;
    
    public BookDTO(Book book) {
        this.title = book.getTitle();
        this.description = book.getDescription();
        this.pageNumber = book.getPageNumber();
        this.author = book.getAuthor().getName();
    }
    
    
    
}
