package il.ac.hit.jfxclothesshop.view;

import il.ac.hit.jfxclothesshop.person.User;
import il.ac.hit.jfxclothesshop.person.managing.UserManager;
import il.ac.hit.jfxclothesshop.person.managing.UserUtils;
import il.ac.hit.jfxclothesshop.util.GraphicsUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;

public class AddEmployeeController {
    @FXML
    private TextField userNameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField accountNumberTextField;
    @FXML
    private TextField typeTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField idNumberTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField branchTextField;
    @FXML
    private TextField userTypeTextField;

    @FXML
    private Label responseTextFalseInformation;

    @Autowired
    private FxWeaver fxWeaver;

    @Autowired
    private UserManager userManager;



    public void onBackButtonClick(ActionEvent event) { GraphicsUtils.openWindow(event, EmployeeListController.class); }


    public void onAddClientButtonClick(ActionEvent event) {
        //if the field empty or phone number starting with 0
        try{
            if(UserUtils.isUserInputNotOkay(userNameTextField, passwordTextField,userTypeTextField, accountNumberTextField, branchTextField, phoneTextField, idNumberTextField, nameTextField, typeTextField)){
                responseTextFalseInformation.setText("Please check that you filled the information correctly");
            } else {      //add the information user to data
                User user=User
                        .builder()
                        .username(userNameTextField.getText())
                        .password(passwordTextField.getText())
                        .userType(User.UserType.SALESPERSON)
                        .accountNumber(accountNumberTextField.getText())
                        .branch(branchTextField.getText())
                        .phone(phoneTextField.getText())
                        .idPerson(idNumberTextField.getText())
                        .name(nameTextField.getText())
                        .type(typeTextField.getText())
                        .build();
                userManager.addUser(user);
                onBackButtonClick(event);  //Move between pages
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
