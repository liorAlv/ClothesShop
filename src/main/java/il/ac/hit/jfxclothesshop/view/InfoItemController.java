package il.ac.hit.jfxclothesshop.view;

import il.ac.hit.jfxclothesshop.JdbcDriverSetup;
import il.ac.hit.jfxclothesshop.shop.clothing.Clothing;
import il.ac.hit.jfxclothesshop.shop.sales.SalesManager;
import il.ac.hit.jfxclothesshop.shop.sales.Sales;
import il.ac.hit.jfxclothesshop.shop.sales.Inventory;
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
@FxmlView("infoItemPage.fxml")
public class InfoItemController {

    @FXML
    private Button removeItemButton;
    @FXML
    private Label skuLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label locationLabel;
    @FXML
    private Label amountLabel;
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
    private SalesManager itemBorrowManager;


    @Autowired
    private FxWeaver fxWeaver;

    public void initialize() {
        //Permissions for only manager on button
        removeItemButton.setVisible(User.UserType.SALESPERSON != getInstance().getCurrentUser().getUserType());

        //if the item is borrowed
        Clothing item = SessionContext.getInstance().getCurrentItem();
        Client activeClientForItem = null;
        try {
            activeClientForItem = itemBorrowManager.getActiveClientForItem(item.getSku());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //show the specific item data
        skuLabel.setText(String.valueOf(item.getSku()));
        titleLabel.setText(item.getTitle());
        priceLabel.setText(item.getPrice());
        categoryLabel.setText(item.getCategory());
        locationLabel.setText(item.getLocation());
        amountLabel.setText(item.getAmount());
        if (activeClientForItem != null) {     //borrowed item
            isBorrowedLabel.setText("Borrowed");
            clientLabel.setText(activeClientForItem.getInfo());
            borrowButton.setVisible(false);
            explanationText.setVisible(false);
            clientIdPhoneField.setVisible(false);
        } else {
            isBorrowedLabel.setText("In shop");
            returnButton.setVisible(false);
        }


    }

    //delete item
    public void onRemoveItemButtonClick(ActionEvent event) {
        Inventory inventory = new Inventory();
        try {
            inventory.remove(Integer.parseInt(skuLabel.getText()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onBackButtonClick(ActionEvent event) {
        SessionContext.getInstance().setCurrentItem(null);
        GraphicsUtils.openWindow(event, ItemsListController.class);//Move between pages
    }

    public void onBorrowButtonClick(ActionEvent event) {

        String idPhone = clientIdPhoneField.getText();
        try {
            Client client = JdbcDriverSetup.getDao(Client.class).queryForId(idPhone);
            if (client == null){
                clientDoesntExist.setText("This client does not exist");
            }
            else {

                itemBorrowManager.borrowItemByClient(SessionContext.getInstance().getCurrentItem(), client);
                onBackButtonClick(event);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    public void onReturnButtonClick(ActionEvent actionEvent) {
        Client activeClientForItem = null;
        Clothing item = null;
        try {
            item = SessionContext.getInstance().getCurrentItem();
            activeClientForItem = itemBorrowManager.getActiveClientForItem(item.getSku());
            Sales borrowItem = JdbcDriverSetup.getDao(Sales.class).queryBuilder()
                    .where()
                    .eq("item_id", SessionContext.getInstance().getCurrentItem().getSku())
                    .and()
                    .eq("client_id", activeClientForItem.getId())
                    .and()
                    .eq("active", true)
                    .queryForFirst();
            itemBorrowManager.deactivateItemBorrow(borrowItem);
            returnButton.setVisible(false);
            borrowButton.setVisible(true);
            explanationText.setVisible(true);
            clientIdPhoneField.setVisible(true);
            isBorrowedLabel.setText("In shop");
            clientLabel.setText("");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
