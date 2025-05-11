package inventory;

import inventory.application.InventoryService;
import inventory.domain.IInventoryRepository;
import inventory.domain.IProductRepository;
import inventory.domain.TemperatureMode;
import inventory.infrastructure.InMemoryInventoryRepository;
import inventory.infrastructure.InMemoryProductRepository;
import inventory.presentation.InventoryConsoleUI;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        IInventoryRepository inventoryRepository = new InMemoryInventoryRepository();
        IProductRepository productRepository = new InMemoryProductRepository();

        InventoryService inventoryService = new InventoryService(inventoryRepository, productRepository);

        inventoryService.newInventory("INV001", "Основной склад");
        inventoryService.newInventory("INV002", "Холодильник");


        LocalDate now = LocalDate.now();

        inventoryService.receiveProduct("INV001", "P001", "Рис", 100,
                now.plusMonths(6), TemperatureMode.ROOM_TEMPERATURE, 10);
        inventoryService.receiveProduct("INV001", "P002", "Гречка", 80,
                now.plusMonths(5), TemperatureMode.ROOM_TEMPERATURE, 8);
        inventoryService.receiveProduct("INV002", "P003", "Молоко", 20,
                now.plusDays(5), TemperatureMode.REFRIGERATED, 5);
        inventoryService.receiveProduct("INV002", "P004", "Сыр", 15,
                now.plusDays(15), TemperatureMode.REFRIGERATED, 3);
        inventoryService.receiveProduct("INV002", "P005", "Мороженое", 25,
                now.plusMonths(2), TemperatureMode.FROZEN, 5);

        InventoryConsoleUI ui = new InventoryConsoleUI(inventoryService);
        ui.start();
    }
}
