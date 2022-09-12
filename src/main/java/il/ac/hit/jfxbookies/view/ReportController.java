package il.ac.hit.jfxbookies.view;

import il.ac.hit.jfxbookies.JdbcDriverSetup;
import il.ac.hit.jfxbookies.library.clothing.Clothing;
import il.ac.hit.jfxbookies.library.sales.SalesManager;
import il.ac.hit.jfxbookies.library.sales.Inventory;
import il.ac.hit.jfxbookies.person.Client;
import il.ac.hit.jfxbookies.util.GraphicsUtils;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
@FxmlView("reportPage.fxml")
public class ReportController {
    @FXML
    private Button backButton;
    @FXML
    private TableView<Clothing> dataTable;
    @FXML
    private TableColumn<Clothing, Integer> idTableColumn;
    @FXML
    private TableColumn<Clothing, String> titleTableColumn;
    @FXML
    private TableColumn<Clothing, String> authorTableColumn;
    @FXML
    private TableColumn<Clothing, String> categoryTableColumn;
    @FXML
    private TableColumn<Clothing, String> locationTableColumn;
    @FXML
    private TableColumn<Clothing, String> borrowByTableColumn;
    @FXML
    private Label numberOfBorrowedBooksLabel;
    @FXML
    private Label numberOfBooksLabel;

    @Autowired
    private FxWeaver fxWeaver;
    @Autowired
    private SalesManager bookBorrowManager;
    @Autowired
    private Inventory inventory;

    private final ObservableList<Clothing> bookObservableList = FXCollections.observableArrayList();

    public void initialize() {
        try {
            List<Clothing> books = JdbcDriverSetup.getDao(Clothing.class).queryForAll();//inventory.showInventory();

            //show the data on table view
            idTableColumn.setCellValueFactory(new PropertyValueFactory<>("sku"));
            titleTableColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            authorTableColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
            categoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
            locationTableColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            borrowByTableColumn.setCellValueFactory(cellData ->{
                var book = cellData.getValue();

                try {
                    Client activeClientForBook = bookBorrowManager.getActiveClientForBook(book.getSku());
                    if (activeClientForBook != null) {
                        return new ReadOnlyStringWrapper(Integer.toString(activeClientForBook.getId()));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            });
            bookObservableList.addAll(books);
            dataTable.setItems(bookObservableList);

            numberOfBooksLabel.setText(String.valueOf(inventory.getBooksCount()));    //number of books
            numberOfBorrowedBooksLabel.setText(String.valueOf(bookBorrowManager.getActiveBookBorrowsSize()));  //number of borrowed books

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Move between pages
    public void onBackButtonClick(ActionEvent event) {
        bookObservableList.clear();
        GraphicsUtils.openWindow(event, BooksListController.class);
    }


}
