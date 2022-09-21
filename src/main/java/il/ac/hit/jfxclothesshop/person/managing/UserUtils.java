package il.ac.hit.jfxclothesshop.person.managing;

import il.ac.hit.jfxclothesshop.person.User;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

public final class UserUtils {

    private static UserManager userManager;

    private UserUtils (UserManager userManager){ UserUtils.userManager=userManager;}



    public static boolean isUserInputNotOkay(TextField userNumber,
                                             TextField password,
                                             TextField userType,
                                             TextField accountNumber,
                                             TextField branch,
                                             TextField phone,
                                             TextField idNumber,
                                             TextField name,
                                             TextField type) throws SQLException{
        return userNumber.getText().isBlank()||
                password.getText().isBlank()||
                userType.getText().isBlank()||
                accountNumber.getText().isBlank()||
                branch.getText().isBlank()||
                !phone.getText().matches("^0\\d+$") ||
                phone.getText().isBlank() ||
                idNumber.getText().isBlank() ||
                name.getText().isBlank()||
                type.getText().isBlank();
    }

}
