package library;

import library.application.LibraryService;
import library.domain.IBookRepository;
import library.domain.IMemberRepository;
import library.infrastructure.InMemoryBookRepository;
import library.infrastructure.InMemoryMemberRepository;
import library.presentation.LibraryConsoleUI;

public class Main {
    public static void main(String[] args) {
        // Создание инфраструктурного слоя (репозитории)
        IBookRepository bookRepository = new InMemoryBookRepository();
        IMemberRepository memberRepository = new InMemoryMemberRepository();

        // Создание слоя приложения (сервис)
        LibraryService libraryService = new LibraryService(bookRepository, memberRepository);

        // Добавление тестовых данных
        libraryService.addBook("B001", "Война и мир", "Лев Толстой");
        libraryService.addBook("B002", "Преступление и наказание", "Федор Достоевский");
        libraryService.addBook("B003", "Мастер и Маргарита", "Михаил Булгаков");

        libraryService.addMember("M001", "Иван Иванов");
        libraryService.addMember("M002", "Мария Петрова");

        // Создание слоя представления (UI)
        LibraryConsoleUI ui = new LibraryConsoleUI(libraryService);

        // Запуск приложения
        ui.start();
    }
}
