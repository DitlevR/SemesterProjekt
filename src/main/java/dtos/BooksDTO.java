/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Book;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rumle
 */
public class BooksDTO {
    private List<BookDTO> booksDTO = new ArrayList<BookDTO>();
    public BooksDTO(List<Book> books) {
        
        for(int i = 0; i < books.size(); i++) {
            booksDTO.add(new BookDTO(books.get(i)));
            
        }
    }
    
    
}
