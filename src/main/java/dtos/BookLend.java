/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

/**
 *
 * @author Rumle
 */
public class BookLend {
    private String username;
    private long book_id;

    public BookLend(String user_id, long book_id) {
        this.username = username;
        this.book_id = book_id;
    }

    public BookLend() {
    }

    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public long getBook_id() {
        return book_id;
    }

    public void setBook_id(long book_id) {
        this.book_id = book_id;
    }
    
    
    
}
