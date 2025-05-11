package inventory.presentation;

import inventory.domain.Product;

import java.util.Arrays;
import java.util.List;

public class TableConsoleUI {

    public static String renderTable(List<Product> products) {
        String[] headers = {"Product ID", "Name", "Quantity", "Expiry Date", "Temperature Mode", "Critical Level"};

        int[] widths = new int[headers.length];
        for (int i = 0; i < headers.length; i++) {
            widths[i] = headers[i].length();
        }

        for (Product p : products) {
            widths[0] = Math.max(widths[0], p.getId().length());
            widths[1] = Math.max(widths[1], p.getName().length());
            widths[2] = Math.max(widths[2], String.valueOf(p.getQuantity()).length());
            widths[3] = Math.max(widths[3], String.valueOf(p.getExpiryDate()).length());
            widths[4] = Math.max(widths[4], String.valueOf(p.getTemperatureMode()).length());
            widths[5] = Math.max(widths[5], String.valueOf(p.getCriticalLevel()).length());
        }

        String format = "| %-" + widths[0] + "s | %-" + widths[1] + "s | %" + widths[2] + "s | %-" +
                widths[3] + "s | %-" + widths[4] + "s | %" + widths[5] + "s |\n";

                int totalWidth = Arrays.stream(widths).sum() + 3 * widths.length + 1;
        String line = "-".repeat(totalWidth) + "\n";

        StringBuilder sb = new StringBuilder();
        sb.append(line);
        sb.append(String.format(format, (Object[]) headers));
        sb.append(line);

        for (Product p : products) {
            sb.append(String.format(format, p.getId(), p.getName(), p.getQuantity(), p.getExpiryDate(),
                    p.getTemperatureMode(), p.getCriticalLevel()));
        }

        sb.append(line);
        return sb.toString();
    }
}

