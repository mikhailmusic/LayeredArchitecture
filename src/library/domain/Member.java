package library.domain;

import java.util.ArrayList;
import java.util.List;

public class Member {
    private String id;
    private String name;
    private List<Book> borrowedBooks;

    public Member(String id, String name) {
        this.id = id;
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Book> getBorrowedBooks() {
        return new ArrayList<>(borrowedBooks);
    }

    public void borrowBook(Book book) {
        if (borrowedBooks.contains(book)) {
            throw new IllegalStateException("Эта книга уже выдана данному читателю");
        }
        book.borrowBook();
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        if (!borrowedBooks.contains(book)) {
            throw new IllegalStateException("Эта книга не была выдана данному читателю");
        }
        book.returnBook();
        borrowedBooks.remove(book);
    }

    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", borrowedBooks=" + borrowedBooks.size() +
                '}';
    }
}
