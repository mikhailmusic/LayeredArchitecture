package library.domain;

import java.util.List;
import java.util.Optional;

public interface IBookRepository {
    void addBook(Book book);
    Optional<Book> findById(String id);
    List<Book> findAllBooks();
    List<Book> findAvailableBooks();
}