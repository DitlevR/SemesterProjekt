package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name = "users")
@NamedQuery(name = "User.deleteAllRows", query = "DELETE from User")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_name", length = 25)
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "user_pass")
    private String userPass;
    @JoinTable(name = "user_roles", joinColumns = {
        @JoinColumn(name = "user_name", referencedColumnName = "user_name")}, inverseJoinColumns = {
        @JoinColumn(name = "role_name", referencedColumnName = "role_name")})
    @ManyToMany
    private List<Role> roleList = new ArrayList();

//   @JoinTable(name = "book_lenders", 
//        joinColumns = {@JoinColumn(name = "user_name", referencedColumnName = "user_name")},
//        inverseJoinColumns = {@JoinColumn(name = "book_id", referencedColumnName = "ID")})
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Book> booklist = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<DateOfLoan> loans = new ArrayList();

    //, inverseJoinColumns = {  @JoinColumn(name = "date", referencedColumnName = "utilDate")}
//, @JoinColumn(name = "date", referencedColumnName = "date")   
//   @Temporal(TemporalType.TIMESTAMP)
//    private Date date;
//   @OneToMany(mappedBy = "user")
//    private DateOfLoan date;
    public List<String> getRolesAsStrings() {
        if (roleList.isEmpty()) {
            return null;
        }
        List<String> rolesAsStrings = new ArrayList();
        for (Role role : roleList) {
            rolesAsStrings.add(role.getRoleName());
        }
        return rolesAsStrings;
    }

    public User() {
        //this.date = new Date();
    }

    //TODO Change when password is hashed
    public boolean verifyPassword(String plainText, String pw) {
        return (BCrypt.checkpw(plainText, userPass));
    }

    public User(String userName, String userPass) {
        this.userName = userName;

        this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt(12));

        //this.date = new Date();
    }

    public void setDateManual(int year, int month, int day) {
        Calendar myCal = Calendar.getInstance();
        myCal.set(Calendar.YEAR, year);
        myCal.set(Calendar.MONTH, month);
        myCal.set(Calendar.DAY_OF_MONTH, day);
        Date theDate = myCal.getTime();

        //this.date = theDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return this.userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public void addRole(Role userRole) {
        roleList.add(userRole);
    }

    public List<Book> getBooklist() {
        return booklist;
    }

    public void setBooklist(List<Book> booklist) {
        this.booklist = booklist;
    }

    public void loanBook(Book book) {
        this.booklist.add(book);
        //this.date = new Date();
    }

    public void removeLoanedBook(Book book) {
        this.booklist.remove(book);
    }

//    public DateOfLoan getDate() {
//        return date;
//    }
//
//    public void setDate(DateOfLoan date) {
//        this.date = date;
//    }
    public List<DateOfLoan> getLoans() {
        return loans;
    }

    public void setLoans(List<DateOfLoan> loans) {
        this.loans = loans;
    }

}
