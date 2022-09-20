package il.ac.hit.jfxclothesshop.person;

public class SalesPerson extends AbstractPerson{
    private User user;

    public SalesPerson(int id, String name, String phone, String idPerson) {
        super(id, name, phone, idPerson);
    }

    public SalesPerson() {
        super();
    }

    @Override
    public String getInfo() {
        return this.toString();
    }
}
