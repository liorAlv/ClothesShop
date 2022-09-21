package il.ac.hit.jfxclothesshop.person;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

@Data
@DatabaseTable(tableName = "client")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Client extends AbstractPerson {



    @DatabaseField
    private String type;


    @Builder
    public Client(String name, String phone, String idPerson, String type) {
        super(name, phone, idPerson);
        this.type=type;
    }

    @Override
    public String getInfo() {
        return this.toString();
    }
}
