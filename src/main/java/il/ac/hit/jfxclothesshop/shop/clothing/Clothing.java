package il.ac.hit.jfxclothesshop.shop.clothing;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import il.ac.hit.jfxclothesshop.JdbcDriverSetup;
import lombok.*;

import java.sql.SQLException;

@DatabaseTable(tableName = "items")
@NoArgsConstructor
@Data
public class Clothing {
    @DatabaseField(columnName = "id",generatedId = true, allowGeneratedIdInsert = true)
    @Setter(AccessLevel.NONE)
    private int sku;
    @DatabaseField
    private String title;
    @DatabaseField
    private String price;
    @DatabaseField
    private String amount;
    @DatabaseField
    private String category;
    @DatabaseField
    private String location;

    //Constructors
    @Builder
    public Clothing(String title, String price, String amount, String category, String location){
        this.title=title;
        this.price=price;
        this.amount=amount;
        this.category=category;
        this.location=location;

    }

    // taking data of item from DB
    public Clothing showItemInfo(String sku){
        try {
            return JdbcDriverSetup.getDao(Clothing.class).queryForId(sku);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;


    }
}

