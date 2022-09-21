package il.ac.hit.jfxclothesshop.view;

import il.ac.hit.jfxclothesshop.JdbcDriverSetup;
import il.ac.hit.jfxclothesshop.person.User;
import il.ac.hit.jfxclothesshop.util.GraphicsUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class EmployeeListController {

    @FXML
    private TableView<User> dataTable;
    @FXML
    private TableColumn<User, String> numberCTableColumn;
    @FXML
    private TableColumn<User, Integer> idCTableColumn;
    @FXML
    private TableColumn<User, String> nameCTableColumn;
    @FXML
    private TableColumn<User, String> phoneCTableColumn;
    @FXML
    private TableColumn<User, String> bankAccountCTableColumn;
    @FXML
    private TableColumn<User, String> branchCTableColumn;
    @FXML
    private TableColumn<User, User.UserType> jobCTableColumn;

    private final ObservableList<User> userObservableList = FXCollections.observableArrayList();

    public void initialize(){

        try {
            //show the data on table view
            List<User> u= JdbcDriverSetup.getDao(User.class).queryForAll();
            numberCTableColumn.setCellValueFactory(new PropertyValueFactory<>("userNumber"));
            idCTableColumn.setCellValueFactory(new PropertyValueFactory<>("idPerson"));
            nameCTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            phoneCTableColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
            bankAccountCTableColumn.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));
            branchCTableColumn.setCellValueFactory(new PropertyValueFactory<>("branch"));
            jobCTableColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            userObservableList.setAll(u);
            dataTable.setUserData(userObservableList);


        }catch (SQLException e) {
            //todo: handle error
            e.printStackTrace();
        }
    }

    public void onBackButtonClick(ActionEvent event) { GraphicsUtils.openWindow(event, ItemsListController.class); }


    public void onAddNewUserButtonClick(ActionEvent event) {
        userObservableList.clear();  //That there will be no duplicates in data table
        GraphicsUtils.openWindow(event, AddEmployeeController.class); } //Move between pages
}
