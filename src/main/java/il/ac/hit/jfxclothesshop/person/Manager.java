package il.ac.hit.jfxclothesshop.person;

public class Manager extends SalesPerson {

    public Manager( String username, String password, UserType userType, String name, String phone, String idPerson, String accountNumber, String branch, String type) {
        super( username,password,userType, name, phone, idPerson, accountNumber, branch, type);
    }

    public String getInfo() {
        return this.toString();
    }
}
