package il.ac.hit.jfxclothesshop.person.managing;

import il.ac.hit.jfxclothesshop.JdbcDriverSetup;
import il.ac.hit.jfxclothesshop.person.User;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserManager {


    public void addUser(User user) {
        try {
            JdbcDriverSetup.getDao(User.class).create(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSalesPerson(String id) throws SQLException {
        JdbcDriverSetup.getDao(User.class).deleteById(id);
    }

    public void updateSalesPerson(User user) throws SQLException {
        JdbcDriverSetup.getDao(User.class)
                .update(user);
    }

}