package il.ac.hit.jfxclothesshop.person;

public class SalesPerson extends User{
    private User user;

    public SalesPerson(String username, String password, UserType userType, String name, String phone, String idPerson, String accountNumber, String branch, String type) {
        super(username,password,userType, name, phone, idPerson,accountNumber, branch, type);
    }

    public SalesPerson() {
        super();
    }

    @Override
    public String getInfo() {
        return this.toString();
    }
}
