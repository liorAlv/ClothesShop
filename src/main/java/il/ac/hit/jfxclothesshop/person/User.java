package il.ac.hit.jfxclothesshop.person;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;
import org.apache.commons.codec.digest.DigestUtils;

//@AllArgsConstructor
@Data
@NoArgsConstructor
@DatabaseTable(tableName = "users")
@ToString

public class User{
    @DatabaseField(columnName = "username", id = true)
    private String userName;
    @DatabaseField
    @ToString.Exclude
    private String password;
    @DatabaseField(dataType = DataType.ENUM_STRING)
    private UserType userType;
    @DatabaseField()
    protected String name;
    @DatabaseField(unique = true)
    private String phone;
    @DatabaseField(unique = true)
    private String idPerson;
    @DatabaseField
    private String accountNumber;
    @DatabaseField
    private String branch;
    @DatabaseField
    private String type;


    @Builder
    public User(String username, String password, UserType userType, String name, String idPerson, String phone, String accountNumber, String branch, String type){
        this.name=name;
        this.idPerson=idPerson;
        this.phone=phone;
        this.userType=userType;
        this.userName=username;
        this.password=(DigestUtils.sha512Hex(password));   //change the password to sha512 so the password won't be on the code
        this.accountNumber=accountNumber;
        this.branch=branch;
        this.type=type;
    }


    public String getInfo() {
        return null;
    }
//    public static User buildUser(String username, String password, UserType userType1) {
//
//        return User.builder()
//                .password(DigestUtils.sha512Hex(password)) //change the password to sha512 so the password won't be on the code
//                .userName(username)
//                .userType(userType1)
//
//                .build();
//    }

    public enum UserType {
        MANAGER, SALESPERSON
    }


}
