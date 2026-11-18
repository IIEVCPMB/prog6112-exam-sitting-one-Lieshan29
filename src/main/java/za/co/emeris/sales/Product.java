package za.co.emeris.sales;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Sales report model for the mattress company (Q.1.2).
 *
 * <p>Product names are held in a one-dimensional array, the monthly sales in a
 * two-dimensional array ({@code [product][month]}) and the total sales of each
 * product in a second one-dimensional array that is worked out once in the
 * constructor.</p>
 *
 * @author Lieshan Valliadum (ST10439494)
 */
public class Product implements IProduct {

    /** Every product in the report has three months of sales. */
    public static final int MONTHS = 3;

    private final String[] names;
    private final int[][] monthlySales;
    private final int[] productTotals;

    /**
     * @param names        the product names, one per product
     * @param monthlySales the three monthly sales figures of each product
     */
    public Product(String[] names, int[][] monthlySales) {
        validate(names, monthlySales);

        this.names = Arrays.copyOf(names, names.length);
        this.monthlySales = new int[monthlySales.length][MONTHS];
        for (int row = 0; row < monthlySales.length; row++) {
            this.monthlySales[row] = Arrays.copyOf(monthlySales[row], MONTHS);
        }
        this.productTotals = buildProductTotals();
    }

    /**
     * Rejects report data that cannot produce a meaningful report.
     */
    private static void validate(String[] names, int[][] monthlySales) {
        if (names == null || monthlySales == null) {
            throw new IllegalArgumentException("The report needs product names and monthly sales.");
        }
        if (names.length == 0 || names.length != monthlySales.length) {
            throw new IllegalArgumentException("Each product needs exactly one row of monthly sales.");
        }
        for (int row = 0; row < names.length; row++) {
            if (names[row] == null || names[row].trim().isEmpty()) {
                throw new IllegalArgumentException("The product name at position " + row + " is missing.");
            }
            if (monthlySales[row] == null || monthlySales[row].length != MONTHS) {
                throw new IllegalArgumentException(names[row] + " needs sales for " + MONTHS + " months.");
            }
            for (int figure : monthlySales[row]) {
                if (figure < 0) {
                    throw new IllegalArgumentException("Sales may not be negative.");
                }
            }
        }
    }

    /**
     * Adds up the monthly figures of every product once.
     *
     * @return the total sales per product
     */
    private int[] buildProductTotals() {
        int[] totals = new int[monthlySales.length];
        for (int row = 0; row < monthlySales.length; row++) {
            totals[row] = IntStream.of(monthlySales[row]).sum();
        }
        return totals;
    }

    @Override
    public int GetTotalProductsSold() {
        return IntStream.of(productTotals).sum();
    }

    @Override
    public double GetAverageProductSold() {
        int figuresInReport = names.length * MONTHS;
        return (double) GetTotalProductsSold() / figuresInReport;
    }

    @Override
    public String GetMostPopularProduct() {
        return names[positionOfBestSeller()];
    }

    /**
     * @return the total sales of the most popular product
     */
    public int GetBestSellerSales() {
        return productTotals[positionOfBestSeller()];
    }

    /**
     * Finds the position of the product with the largest total.
     *
     * @return index into the product arrays
     */
    private int positionOfBestSeller() {
        int best = 0;
        for (int row = 1; row < productTotals.length; row++) {
            if (productTotals[row] > productTotals[best]) {
                best = row;
            }
        }
        return best;
    }

}
