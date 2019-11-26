/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Book;
import entities.User;
import java.util.List;

/**
 *
 * @author Rumle
 */
public class UserDTO {

    private String name;
private List<String> roles;
    private List<BookDTO> booksLoaned;

    public UserDTO(User user) {
        this.name = user.getUserName();
        this.roles = user.getRolesAsStrings();
        this.booksLoaned = new BooksDTO(user.getBooklist()).getBooksDTO();
        
        
        
        
        

    }

}
