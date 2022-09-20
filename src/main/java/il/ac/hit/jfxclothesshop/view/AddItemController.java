package il.ac.hit.jfxclothesshop.view;

import il.ac.hit.jfxclothesshop.library.clothing.Clothing;
import il.ac.hit.jfxclothesshop.library.sales.Inventory;
import il.ac.hit.jfxclothesshop.util.GraphicsUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.sql.SQLException;

@Component
@FxmlView("addBookPage.fxml")
public class AddBookController {

    @FXML
    private TextField titleTextField;
    @FXML
    private TextField authorTextField;
    @FXML
    private TextField categoryTextField;
    @FXML
    private TextField locationTextField;
    @FXML
    private Label errorLabel;


    @Autowired
    private Inventory inventory;

    //Move between pages
    public void onBackButtonClick(ActionEvent event) {
        GraphicsUtils.openWindow(event, BooksListController.class);
    }

    public void onAddBookButtonClick(ActionEvent event) {
        if(titleTextField.getText().isBlank() || authorTextField.getText().isBlank() || categoryTextField.getText().isBlank() || locationTextField.getText().isBlank()){
            errorLabel.setText("Please check that you filled the information correctly");
            //if the field empty
        } else {
            Clothing book = new Clothing(titleTextField.getText(), authorTextField.getText(), categoryTextField.getText(), locationTextField.getText());
            //add the information book to data

            try {
                inventory.add(book);
                onBackButtonClick(event); //Move between pages
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
