package za.co.emeris.sales;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Console entry point of the sales report (Q.1.1).
 *
 * <p>Design decision: the application only captures input and hands it to
 * {@link Product} and {@link PrintProduct}. Reading is looped inside
 * {@link #readFigure} so an invalid entry is retried without repeating the
 * prompt in several places.</p>
 *
 * @author Lieshan Valliadum (ST10439494)
 */
public final class SalesReportApp {

    /** The three products the report is built for, in the brief's order. */
    private static final String[] PRODUCTS = {
        "Cozy Nights Monarch",
        "Restonic Barbados",
        "Sealy Phuket"
    };

    /** The application is only started through {@link #main}. */
    private SalesReportApp() {
    }

    /**
     * Captures the nine sales figures and prints the report.
     *
     * @param args not used
     * @throws IOException when the keyboard input cannot be read
     */
    public static void main(String[] args) throws IOException {
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        int[][] sales = readAllSales(keyboard);
        Product report = new Product(PRODUCTS, sales);

        // The brief asks for a blank line after the last entry.
        System.out.println();

        PrintProduct printer = new PrintProduct(report);
        printer.PrintHeading();
        printer.PrintWholeReport();
    }

    /**
     * Reads three months of figures for each product.
     *
     * @param keyboard the reader for the typed input
     * @return the captured two-dimensional array of sales figures
     * @throws IOException when the input stream cannot be read
     */
    private static int[][] readAllSales(BufferedReader keyboard) throws IOException {
        int[][] sales = new int[PRODUCTS.length][Product.MONTHS];
        for (int product = 0; product < PRODUCTS.length; product++) {
            for (int month = 1; month <= Product.MONTHS; month++) {
                sales[product][month - 1] = readFigure(keyboard, PRODUCTS[product], month);
            }
        }
        return sales;
    }

    /**
     * Reads one figure and keeps asking until a whole, non-negative number is
     * entered.
     *
     * @param keyboard the reader for the typed input
     * @param product  the product being captured
     * @param month    the month being captured, counting from one
     * @return the number of sales that was entered
     * @throws IOException when the input stream cannot be read
     */
    private static int readFigure(BufferedReader keyboard, String product, int month)
            throws IOException {
        while (true) {
            System.out.print("Enter number of sales for month " + month
                    + " for " + product + " mattress: ");
            String entry = keyboard.readLine();
            if (entry == null) {
                throw new IOException("The input ended before all nine figures were entered.");
            }
            try {
                int figure = Integer.parseInt(entry.trim());
                if (figure < 0) {
                    System.out.println("Sales cannot be negative. Please enter the figure again.");
                    continue;
                }
                return figure;
            } catch (NumberFormatException notAWholeNumber) {
                System.out.println("Please enter the sales as a whole number, for example 25.");
            }
        }
    }
}
