package library.application;

import library.domain.Book;
import library.domain.IBookRepository;
import library.domain.IMemberRepository;
import library.domain.Member;

import java.util.List;
import java.util.Optional;

public class LibraryService {
    private final IBookRepository bookRepository;
    private final IMemberRepository memberRepository;

    public LibraryService(IBookRepository bookRepository, IMemberRepository memberRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    public void addBook(String id, String title, String author) {
        Book book = new Book(id, title, author);
        bookRepository.addBook(book);
    }

    public void addMember(String id, String name) {
        Member member = new Member(id, name);
        memberRepository.addMember(member);
    }

    public void borrowBook(String memberId, String bookId) {
        Optional<Member> member = memberRepository.findById(memberId);
        Optional<Book> book = bookRepository.findById(bookId);

        if (!member.isPresent()) {
            throw new IllegalArgumentException("Читатель не найден");
        }
        if (!book.isPresent()) {
            throw new IllegalArgumentException("Книга не найдена");
        }

        member.get().borrowBook(book.get());
    }

    public void returnBook(String memberId, String bookId) {
        Optional<Member> member = memberRepository.findById(memberId);
        Optional<Book> book = bookRepository.findById(bookId);

        if (!member.isPresent()) {
            throw new IllegalArgumentException("Читатель не найден");
        }
        if (!book.isPresent()) {
            throw new IllegalArgumentException("Книга не найдена");
        }

        member.get().returnBook(book.get());
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAllBooks();
    }

    public List<Book> getAvailableBooks() {
        return bookRepository.findAvailableBooks();
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAllMembers();
    }

    public Optional<Member> getMember(String id) {
        return memberRepository.findById(id);
    }
}
