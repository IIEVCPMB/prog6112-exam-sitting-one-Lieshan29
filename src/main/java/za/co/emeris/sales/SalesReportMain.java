package za.co.emeris.sales;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Console entry point that captures the sales and prints the report (Q.1.1).
 *
 * @author Lieshan Valliadum (ST10439494)
 */
public class SalesReportMain {

    /** The three products the report is built for. */
    private static final List<String> PRODUCTS = List.of(
            "Cozy Nights Monarch",
            "Restonic Barbados",
            "Sealy Phuket");

    /**
     * Runs the sales report against the keyboard input.
     *
     * @param args not used
     * @throws IOException when the input stream cannot be read
     */
    public static void main(String[] args) throws IOException {
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

        int[][] sales = readAllSales(keyboard);
        Product report = new Product(PRODUCTS.toArray(new String[0]), sales);

        PrintProduct printer = new PrintProduct(report);
        printer.PrintHeading();
        printer.PrintWholeReport();
    }

    /**
     * Reads three months of sales for each product.
     *
     * @param keyboard the reader for the typed input
     * @return the captured two-dimensional array of sales
     * @throws IOException when the input stream cannot be read
     */
    private static int[][] readAllSales(BufferedReader keyboard) throws IOException {
        int[][] sales = new int[PRODUCTS.size()][Product.MONTHS];
        for (int product = 0; product < PRODUCTS.size(); product++) {
            for (int month = 1; month <= Product.MONTHS; month++) {
                sales[product][month - 1] = readFigure(keyboard, PRODUCTS.get(product), month);
            }
            System.out.println();
        }
        return sales;
    }

    /**
     * Reads a single sales figure and keeps asking until it is valid.
     *
     * @param keyboard the reader for the typed input
     * @param product  the product being captured
     * @param month    the month being captured
     * @return the number of sales entered
     * @throws IOException when the input stream cannot be read
     */
    private static int readFigure(BufferedReader keyboard, String product, int month) throws IOException {
        while (true) {
            System.out.print("Enter number of sales for month " + month
                    + " for " + product + " mattress: ");
            String answer = keyboard.readLine();
            if (answer == null) {
                throw new IOException("No more input was received.");
            }
            try {
                int figure = Integer.parseInt(answer.trim());
                if (figure >= 0) {
                    return figure;
                }
                System.out.println("Sales cannot be negative. Please enter the figure again.");
            } catch (NumberFormatException notANumber) {
                System.out.println("That is not a whole number. Please try again.");
            }
        }
    }
}
