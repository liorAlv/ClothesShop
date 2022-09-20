package il.ac.hit.jfxclothesshop.shop.sales;

import il.ac.hit.jfxclothesshop.JdbcDriverSetup;
import il.ac.hit.jfxclothesshop.shop.clothing.Clothing;
import il.ac.hit.jfxclothesshop.person.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

//taking data of borrowed item from DB
@Service
@Slf4j
public class SalesManager {
    public Client getActiveClientForItem(int id) throws SQLException {
        Sales borrowItem = JdbcDriverSetup
                .getDao(Sales.class)
                .queryBuilder()
                .where()
                .eq("item_id", id)
                .and()
                .eq("active", true)
                .queryForFirst();
        if (borrowItem != null) {
            return borrowItem.getClient();
        } else {
            return null;
        }
    }

    //taking data of how much borrowed item we have from DB
    public long getActiveItemBorrowsSize() throws SQLException {
        return JdbcDriverSetup.getDao(Sales.class)
                .queryBuilder()
                .where()
                .eq("active", true)
                .countOf();
    }

    //build the data of borrow item
    public Sales borrowItemByClient(Clothing item, Client client) throws SQLException {
        var borrow = Sales
                .builder()
                .item(item)
                .client(client)
                .active(true)
                .build();
        JdbcDriverSetup.getDao(Sales.class).create(borrow);
        return borrow;
    }

    //return item
    public void deactivateItemBorrow(Sales borrowItem) throws SQLException {
        borrowItem.setActive(false);
        JdbcDriverSetup.getDao(Sales.class)
                .update(borrowItem);
    }

    //delete from data
    public void deleteItemBorrowByClient(int clientID) throws SQLException{
        List<Sales> borrowItemList = JdbcDriverSetup.getDao(Sales.class).queryBuilder()
                        .where()
                        .eq("client_id", clientID).query();
        JdbcDriverSetup.getDao(Sales.class).delete(borrowItemList);
    }


    
}
