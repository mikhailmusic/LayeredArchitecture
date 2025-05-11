package library.presentation;

import library.application.LibraryService;
import library.domain.Book;
import library.domain.Member;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class LibraryConsoleUI {
    private final LibraryService libraryService;
    private final Scanner scanner;

    public LibraryConsoleUI(LibraryService libraryService) {
        this.libraryService = libraryService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        int choice;
        do {
            showMenu();
            choice = scanner.nextInt();
            scanner.nextLine(); // очистка буфера
            handleMenuChoice(choice);
        } while (choice != 0);
    }

    private void showMenu() {
        System.out.println("\n===== Библиотечная система =====");
        System.out.println("1. Добавить книгу");
        System.out.println("2. Добавить читателя");
        System.out.println("3. Показать все книги");
        System.out.println("4. Показать доступные книги");
        System.out.println("5. Показать всех читателей");
        System.out.println("6. Выдать книгу");
        System.out.println("7. Принять книгу");
        System.out.println("8. Информация о читателе");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    private void handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                addBook();
                break;
            case 2:
                addMember();
                break;
            case 3:
                showAllBooks();
                break;
            case 4:
                showAvailableBooks();
                break;
            case 5:
                showAllMembers();
                break;
            case 6:
                borrowBook();
                break;
            case 7:
                returnBook();
                break;
            case 8:
                showMemberInfo();
                break;
            case 0:
                System.out.println("Выход из программы...");
                break;
            default:
                System.out.println("Неверный выбор. Попробуйте снова.");
        }
    }

    private void addBook() {
        System.out.print("Введите ID книги: ");
        String id = scanner.nextLine();
        System.out.print("Введите название книги: ");
        String title = scanner.nextLine();
        System.out.print("Введите автора книги: ");
        String author = scanner.nextLine();

        try {
            libraryService.addBook(id, title, author);
            System.out.println("Книга успешно добавлена.");
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении книги: " + e.getMessage());
        }
    }

    private void addMember() {
        System.out.print("Введите ID читателя: ");
        String id = scanner.nextLine();
        System.out.print("Введите имя читателя: ");
        String name = scanner.nextLine();

        try {
            libraryService.addMember(id, name);
            System.out.println("Читатель успешно добавлен.");
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении читателя: " + e.getMessage());
        }
    }

    private void showAllBooks() {
        List<Book> books = libraryService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("В библиотеке нет книг.");
            return;
        }

        System.out.println("\n=== Все книги ===");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    private void showAvailableBooks() {
        List<Book> books = libraryService.getAvailableBooks();
        if (books.isEmpty()) {
            System.out.println("В библиотеке нет доступных книг.");
            return;
        }

        System.out.println("\n=== Доступные книги ===");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    private void showAllMembers() {
        List<Member> members = libraryService.getAllMembers();
        if (members.isEmpty()) {
            System.out.println("В библиотеке нет зарегистрированных читателей.");
            return;
        }

        System.out.println("\n=== Все читатели ===");
        for (Member member : members) {
            System.out.println(member);
        }
    }

    private void borrowBook() {
        System.out.print("Введите ID читателя: ");
        String memberId = scanner.nextLine();
        System.out.print("Введите ID книги: ");
        String bookId = scanner.nextLine();

        try {
            libraryService.borrowBook(memberId, bookId);
            System.out.println("Книга успешно выдана.");
        } catch (Exception e) {
            System.out.println("Ошибка при выдаче книги: " + e.getMessage());
        }
    }

    private void returnBook() {
        System.out.print("Введите ID читателя: ");
        String memberId = scanner.nextLine();
        System.out.print("Введите ID книги: ");
        String bookId = scanner.nextLine();

        try {
            libraryService.returnBook(memberId, bookId);
            System.out.println("Книга успешно возвращена.");
        } catch (Exception e) {
            System.out.println("Ошибка при возврате книги: " + e.getMessage());
        }
    }

    private void showMemberInfo() {
        System.out.print("Введите ID читателя: ");
        String memberId = scanner.nextLine();

        Optional<Member> optionalMember = libraryService.getMember(memberId);
        if (!optionalMember.isPresent()) {
            System.out.println("Читатель не найден.");
            return;
        }

        Member member = optionalMember.get();
        System.out.println("\n=== Информация о читателе ===");
        System.out.println("ID: " + member.getId());
        System.out.println("Имя: " + member.getName());

        List<Book> borrowedBooks = member.getBorrowedBooks();
        if (borrowedBooks.isEmpty()) {
            System.out.println("У читателя нет книг на руках.");
        } else {
            System.out.println("Книги на руках:");
            for (Book book : borrowedBooks) {
                System.out.println("- " + book.getTitle() + " (" + book.getAuthor() + ")");
            }
        }
    }
}