package il.ac.hit.jfxclothesshop.person;

public class Manager extends SalesPerson {

    public Manager( String username, String password, UserType userType, String name, String phone, String idPerson) {
        super( username,password,userType, name, phone, idPerson);
    }

    public String getInfo() {
        return this.toString();
    }
}
