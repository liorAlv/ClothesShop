package il.ac.hit.jfxclothesshop.view;

import il.ac.hit.jfxclothesshop.JdbcDriverSetup;
import il.ac.hit.jfxclothesshop.library.clothing.Clothing;
import il.ac.hit.jfxclothesshop.library.sales.SalesManager;
import il.ac.hit.jfxclothesshop.library.sales.Sales;
import il.ac.hit.jfxclothesshop.library.sales.Inventory;
import il.ac.hit.jfxclothesshop.person.Client;
import il.ac.hit.jfxclothesshop.person.User;
import il.ac.hit.jfxclothesshop.session.SessionContext;
import il.ac.hit.jfxclothesshop.util.GraphicsUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

import static il.ac.hit.jfxclothesshop.session.SessionContext.getInstance;


@Component
@FxmlView("infoBookPage.fxml")
public class InfoBookController {

    @FXML
    private Button removeBookButton;
    @FXML
    private Label skuLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label locationLabel;
    @FXML
    private Label isBorrowedLabel;
    @FXML
    private Label clientLabel;
    @FXML
    private TextField clientIdPhoneField;
    @FXML
    private Button borrowButton;
    @FXML
    private Button returnButton;
    @FXML
    private Label explanationText;
    @FXML
    private Label clientDoesntExist;

    @Autowired
    private SalesManager bookBorrowManager;


    @Autowired
    private FxWeaver fxWeaver;

    public void initialize() {
        //Permissions for only manager on button
        removeBookButton.setVisible(User.UserType.LIBRARIAN != getInstance().getCurrentUser().getUserType());

        //if the book is borrowed
        Clothing book = SessionContext.getInstance().getCurrentBook();
        Client activeClientForBook = null;
        try {
            activeClientForBook = bookBorrowManager.getActiveClientForBook(book.getSku());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //show the specific book data
        skuLabel.setText(String.valueOf(book.getSku()));
        titleLabel.setText(book.getTitle());
        authorLabel.setText(book.getAuthor());
        categoryLabel.setText(book.getCategory());
        locationLabel.setText(book.getLocation());
        if (activeClientForBook != null) {     //borrowed book
            isBorrowedLabel.setText("Borrowed");
            clientLabel.setText(activeClientForBook.getInfo());
            borrowButton.setVisible(false);
            explanationText.setVisible(false);
            clientIdPhoneField.setVisible(false);
        } else {
            isBorrowedLabel.setText("In library");
            returnButton.setVisible(false);
        }


    }

    //delete book
    public void onRemoveBookButtonClick(ActionEvent event) {
        Inventory inventory = new Inventory();
        try {
            inventory.remove(Integer.parseInt(skuLabel.getText()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onBackButtonClick(ActionEvent event) {
        SessionContext.getInstance().setCurrentBook(null);
        GraphicsUtils.openWindow(event, BooksListController.class);//Move between pages
    }

    public void onBorrowButtonClick(ActionEvent event) {

        String idPhone = clientIdPhoneField.getText();
        try {
            Client client = JdbcDriverSetup.getDao(Client.class).queryForId(idPhone);
            if (client == null){
                clientDoesntExist.setText("This client does not exist");
            }
            else {

                bookBorrowManager.borrowBookByClient(SessionContext.getInstance().getCurrentBook(), client);
                onBackButtonClick(event);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    public void onReturnButtonClick(ActionEvent actionEvent) {
        Client activeClientForBook = null;
        Clothing book = null;
        try {
            book = SessionContext.getInstance().getCurrentBook();
            activeClientForBook = bookBorrowManager.getActiveClientForBook(book.getSku());
            Sales borrowBook = JdbcDriverSetup.getDao(Sales.class).queryBuilder()
                    .where()
                    .eq("book_id", SessionContext.getInstance().getCurrentBook().getSku())
                    .and()
                    .eq("client_id", activeClientForBook.getId())
                    .and()
                    .eq("active", true)
                    .queryForFirst();
            bookBorrowManager.deactivateBookBorrow(borrowBook);
            returnButton.setVisible(false);
            borrowButton.setVisible(true);
            explanationText.setVisible(true);
            clientIdPhoneField.setVisible(true);
            isBorrowedLabel.setText("In library");
            clientLabel.setText("");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
