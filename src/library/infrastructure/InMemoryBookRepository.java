package library.infrastructure;

import library.domain.Book;
import library.domain.IBookRepository;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryBookRepository implements IBookRepository {
    private final Map<String, Book> books = new HashMap<>();

    @Override
    public void addBook(Book book) {
        books.put(book.getId(), book);
    }

    @Override
    public Optional<Book> findById(String id) {
        return Optional.ofNullable(books.get(id));
    }

    @Override
    public List<Book> findAllBooks() {
        return new ArrayList<>(books.values());
    }

    @Override
    public List<Book> findAvailableBooks() {
        return books.values().stream()
                .filter(Book::isAvailable)
                .collect(Collectors.toList());
    }
}