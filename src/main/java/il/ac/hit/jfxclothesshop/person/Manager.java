package il.ac.hit.jfxclothesshop.person;

public class Manager extends SalesPerson {

    public Manager(int id, String name, String phone, String idPerson) {
        super(id, name, phone, idPerson);
    }

    public String getInfo() {
        return this.toString();
    }
}
