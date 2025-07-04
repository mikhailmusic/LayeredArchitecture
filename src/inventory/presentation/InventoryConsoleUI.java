package inventory.presentation;

import inventory.application.InventoryService;
import inventory.domain.Inventory;
import inventory.domain.Product;
import inventory.domain.TemperatureMode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class InventoryConsoleUI {
    private final InventoryService inventoryService;
    private final Scanner scanner;

    public InventoryConsoleUI(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        int choice = -1;
        do {
            showMenu();
            try {
                choice = Integer.parseInt(scanner.nextLine());
                handleChoice(choice);
            } catch (Exception e) {
                System.out.println("Непредвиденная ошибка: " + e.getMessage());
            }
        } while (choice != 0);

    }

    private void showMenu() {
        System.out.println("\n===== Инвентаризация =====");
        System.out.println("1. Создать инвентарь");
        System.out.println("2. Показать все инвентари");
        System.out.println("3. Добавить продукт");
        System.out.println("4. Использовать продукт");
        System.out.println("5. Списать просроченные продукты");
        System.out.println("6. Пополнить продукт");
        System.out.println("7. Удалить продукт");
        System.out.println("8. Продукты ниже критического уровня");
        System.out.println("9. Отчет по инвентарю");
        System.out.println("10. Очистить нулевые запасы");
        System.out.println("11. Все продукты");
        System.out.println("12. Продукты по температурному режиму");
        System.out.println("13. Актуализировать продукт");
        System.out.println("14. Информация о продукте по ID");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    private void handleChoice(int choice) {
        try {
            switch (choice) {
                case 1 -> createInventory();
                case 2 -> showAllInventories();
                case 3 -> receiveProduct();
                case 4 -> useProduct();
                case 5 -> writeOffExpired();
                case 6 -> restockProduct();
                case 7 -> removeProduct();
                case 8 -> showCriticalProducts();
                case 9 -> generateReport();
                case 10 -> cleanupZeroQuantity();
                case 11 -> showAllProducts();
                case 12 -> productsByTemperatureMode();
                case 13 -> adjustInventory();
                case 14 -> showProductById();
                case 0 -> System.out.println("Выход из программы...");
                default -> System.out.println("Неверный выбор. Попробуйте снова.");
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void createInventory() {
        System.out.print("Введите ID инвентаря: ");
        String id = scanner.nextLine();
        System.out.print("Введите название инвентаря: ");
        String name = scanner.nextLine();

        try {
            inventoryService.newInventory(id, name);
            System.out.println("Инвентарь успешно добавлен");
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении инвернтаря: " + e.getMessage());
        }
    }

    private void showAllInventories() {
        List<Inventory> inventories = inventoryService.getAllInventories();
        if (inventories.isEmpty()) {
            System.out.println("Инвентари отсутствуют");
            return;
        }
        System.out.println("\n=== Все Инвентари ===");
        for (Inventory inv : inventories) {
            System.out.println("ID: " + inv.getId() + ", Название: " + inv.getName());
        }
    }

    private void receiveProduct() {
        System.out.print("ID инвентаря: ");
        String inventoryId = scanner.nextLine();
        System.out.print("ID продукта: ");
        String productId = scanner.nextLine();
        System.out.print("Название продукта: ");
        String name = scanner.nextLine();
        System.out.print("Количество: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Дата истечения (dd-MM-yyyy): ");
        LocalDate expiry = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        System.out.println("Температурный режим:");
        TemperatureMode[] modes = TemperatureMode.values();
        for (int i = 0; i < modes.length; i++) {
            System.out.printf("%d. %s%n", i + 1, modes[i]);
        }
        System.out.print("Выберите вариант (введите номер): ");
        int choice = Integer.parseInt(scanner.nextLine());
        if (choice < 1 || choice > modes.length) {
            throw new IllegalArgumentException("Некорректный выбор температурного режима.");
        }
        TemperatureMode mode = modes[choice - 1];

        System.out.print("Критический уровень: ");
        int criticalLevel = scanner.nextInt();
        scanner.nextLine();

        try {
            inventoryService.receiveProduct(inventoryId, productId, name, quantity, expiry, mode, criticalLevel);
            System.out.println("Продукт успешно добавлен");
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении продукта: " + e.getMessage());
        }
    }

    private void useProduct() {
        System.out.print("ID инвентаря: ");
        String inventoryId = scanner.nextLine();
        System.out.print("ID продукта: ");
        String productId = scanner.nextLine();
        System.out.print("Количество для использования: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        try {
            inventoryService.useProduct(inventoryId, productId, quantity);
            System.out.println("Продукт использован. Текущее количество: " + inventoryService.getProductOrThrow(productId).getQuantity());
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void writeOffExpired() {
        System.out.print("ID инвентаря: ");
        String inventoryId = scanner.nextLine();

        try {
            inventoryService.writeOffExpiredProducts(inventoryId);
            System.out.println("Просроченные продукты списаны");
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void restockProduct() {
        System.out.print("ID инвентаря: ");
        String inventoryId = scanner.nextLine();
        System.out.print("ID продукта: ");
        String productId = scanner.nextLine();
        System.out.print("Количество для добавления: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        try {
            inventoryService.restockProduct(inventoryId, productId, quantity);
            System.out.println("Продукт пополнен. Текущее количество: " + inventoryService.getProductOrThrow(productId).getQuantity());
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void removeProduct() {
        System.out.print("ID инвентаря: ");
        String inventoryId = scanner.nextLine();
        System.out.print("ID продукта: ");
        String productId = scanner.nextLine();

        try {
            inventoryService.removeProduct(inventoryId, productId);
            System.out.println("Продукт удалён");
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void showCriticalProducts() {
        System.out.print("ID инвентаря: ");
        String inventoryId = scanner.nextLine();
        List<Product> critical = inventoryService.getCriticalStockProducts(inventoryId);
        if (critical.isEmpty()) {
            System.out.println("Нет продуктов ниже критического уровня");
            return;
        }
        System.out.println("\n=== Критические запасы ===");
        System.out.println(TableConsoleUI.renderTable(critical));
    }

    private void generateReport() {
        System.out.print("ID инвентаря: ");
        String inventoryId = scanner.nextLine();
        Inventory inventory = inventoryService.generateInventoryReport(inventoryId);

        System.out.println("\n=== Отчет по инвентарю ===");
        System.out.println("Inventory: \"" + inventory.getName() + "\" (ID: " + inventory.getId() + ")");
        System.out.println(TableConsoleUI.renderTable(inventory.getProducts()));
    }

    private void cleanupZeroQuantity() {
        System.out.print("ID инвентаря: ");
        String inventoryId = scanner.nextLine();
        inventoryService.cleanupZeroQuantity(inventoryId);
        System.out.println("Продукты с нулевым количеством удалены");
    }

    private void showAllProducts() {
        List<Product> products = inventoryService.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("Нет продуктов");
            return;
        }
        System.out.println("\n=== Все продукты ===");
        System.out.println(TableConsoleUI.renderTable(products));
    }

    private void productsByTemperatureMode() {
        System.out.print("Температурный режим (FROZEN, REFRIGERATED, ROOM_TEMPERATURE): ");
        TemperatureMode mode = TemperatureMode.valueOf(scanner.nextLine());
        List<Product> products = inventoryService.getProductsTemperatureMode(mode);
        if (products.isEmpty()) {
            System.out.println("Нет продуктов с заданным температурным режимом");
            return;
        }
        System.out.println("\n=== Продукты в режиме " + mode.getDescription() + " ===");
        products.forEach(System.out::println);
    }

    private void adjustInventory() {
        System.out.print("ID инвентаря: ");
        String inventoryId = scanner.nextLine();
        System.out.print("ID продукта: ");
        String productId = scanner.nextLine();
        System.out.print("Фактическое количество: ");
        int actual = scanner.nextInt();
        scanner.nextLine();

        try {
            inventoryService.adjustInventory(inventoryId, productId, actual);
            System.out.println("Количество продукта скорректировано");
            System.out.println(inventoryService.getProductOrThrow(productId));
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void showProductById() {
        System.out.print("ID продукта: ");
        String productId = scanner.nextLine();
        try {
            Product product = inventoryService.getProductOrThrow(productId);
            System.out.println("\n=== Информация о продукте ===");
            System.out.println(product);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
