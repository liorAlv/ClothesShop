package il.ac.hit.jfxclothesshop.library.sales;

import il.ac.hit.jfxclothesshop.JdbcDriverSetup;
import il.ac.hit.jfxclothesshop.library.clothing.Clothing;
import il.ac.hit.jfxclothesshop.person.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

//taking data of borrowed book from DB
@Service
@Slf4j
public class SalesManager {
    public Client getActiveClientForBook(int id) throws SQLException {
        Sales borrowBook = JdbcDriverSetup
                .getDao(Sales.class)
                .queryBuilder()
                .where()
                .eq("book_id", id)
                .and()
                .eq("active", true)
                .queryForFirst();
        if (borrowBook != null) {
            return borrowBook.getClient();
        } else {
            return null;
        }
    }

    //taking data of how much borrowed book we have from DB
    public long getActiveBookBorrowsSize() throws SQLException {
        return JdbcDriverSetup.getDao(Sales.class)
                .queryBuilder()
                .where()
                .eq("active", true)
                .countOf();
    }

    //build the data of borrow book
    public Sales borrowBookByClient(Clothing book, Client client) throws SQLException {
        var borrow = Sales
                .builder()
                .book(book)
                .client(client)
                .active(true)
                .build();
        JdbcDriverSetup.getDao(Sales.class).create(borrow);
        return borrow;
    }

    //return book
    public void deactivateBookBorrow(Sales borrowBook) throws SQLException {
        borrowBook.setActive(false);
        JdbcDriverSetup.getDao(Sales.class)
                .update(borrowBook);
    }

    //delete from data
    public void deleteBookBorrowByClient(int clientID) throws SQLException{
        List<Sales> borrowBookList = JdbcDriverSetup.getDao(Sales.class).queryBuilder()
                        .where()
                        .eq("client_id", clientID).query();
        JdbcDriverSetup.getDao(Sales.class).delete(borrowBookList);
    }


    
}
