package il.ac.hit.jfxbookies.library.sales;


import il.ac.hit.jfxbookies.JdbcDriverSetup;
import il.ac.hit.jfxbookies.library.clothing.Clothing;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class Inventory {
    public static final String NULL_ERROR_FOR_FIELDS = "title, author, category and location cannot be null";

    public Inventory() {

    }

    public long getBooksCount() throws SQLException {
        return JdbcDriverSetup.getDao(Clothing.class).countOf();
    }

    public void add(Clothing book) throws SQLException {
        JdbcDriverSetup.getDao(Clothing.class).create(book);
    }

    public void remove(int sku) throws SQLException {
        JdbcDriverSetup.<Clothing, Integer>getDao(Clothing.class).deleteById(sku);

    }

    public boolean hasBook(Clothing book) throws SQLException {

        return JdbcDriverSetup.getDao(Clothing.class).queryBuilder()
                .where()
                .eq("id", book.getSku())
                .countOf() > 0;
    }

    public List<Clothing> getFilteredBooks(String keyword) throws SQLException {
        String likeKeyword = "%" + keyword + "%";
        return JdbcDriverSetup.getDao(Clothing.class)
                .queryBuilder()
                .where()
                .like("id", likeKeyword)
                .or()
                .like("title", likeKeyword)
                .or()
                .like("author", likeKeyword)
                .or()
                .like("category", likeKeyword)
                .or()
                .like("location", likeKeyword)
                .query();
    }

    public List<Clothing> getAllBooks() throws SQLException {
        return JdbcDriverSetup.getDao(Clothing.class).queryForAll();
    }
}
