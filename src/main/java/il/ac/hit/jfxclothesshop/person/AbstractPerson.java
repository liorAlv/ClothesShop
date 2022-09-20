package il.ac.hit.jfxclothesshop.person;

import com.j256.ormlite.field.DatabaseField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractPerson {
    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    protected int id;
    @Setter
    @DatabaseField()
    protected String name;
    @Setter
    @DatabaseField(unique = true)
    private String phone;
    @Setter
    @DatabaseField(unique = true)
    private String idPerson;



    public AbstractPerson(String name, String phone, String idPerson) {

        this.name = name;
        this.phone=phone;
        this.idPerson=idPerson;
    }

    public abstract String getInfo();

}

