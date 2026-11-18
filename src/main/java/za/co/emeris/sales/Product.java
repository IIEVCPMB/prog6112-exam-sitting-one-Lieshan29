package za.co.emeris.sales;

/**
 * The mattress sales report model (Q.1.2).
 *
 * <p>Data design: the three product names are held in a single-dimensional
 * array, the monthly figures in a two-dimensional array indexed
 * {@code [product][month]}, and a second single-dimensional array holds the
 * three-month total of every product. The totals are added up once in the
 * constructor so no figure is summed twice (efficient code).</p>
 *
 * @author Lieshan Valliadum (ST10439494)
 */
public class Product implements IProduct {

    /** Every product reports on the same three months. */
    public static final int MONTHS = 3;

    /** Product names, one per product (single-dimensional array). */
    private final String[] productNames;

    /** Sales figures per product per month (two-dimensional array). */
    private final int[][] monthlySales;

    /** Three-month total of each product (single-dimensional array). */
    private final int[] salesPerProduct;

    /**
     * @param productNames the three product names, in the order of the report
     * @param monthlySales the three monthly figures of each product
     * @throws IllegalArgumentException when the arrays are missing, the rows do
     *                                  not hold exactly three months, or a
     *                                  figure is negative
     */
    public Product(String[] productNames, int[][] monthlySales) {
        checkShape(productNames, monthlySales);
        this.productNames = copyNames(productNames);
        this.monthlySales = copySales(monthlySales);
        this.salesPerProduct = totalEachProduct();
    }

    /**
     * Rejects data that cannot produce the report.
     */
    private static void checkShape(String[] names, int[][] sales) {
        if (names == null || sales == null) {
            throw new IllegalArgumentException("Product names and monthly sales are both required.");
        }
        if (names.length == 0) {
            throw new IllegalArgumentException("The report needs at least one product.");
        }
        if (names.length != sales.length) {
            throw new IllegalArgumentException("Each product name needs one row of monthly sales.");
        }
    }

    /**
     * Copies the names so later changes to the caller's array cannot alter the
     * report, and refuses a name that is missing.
     */
    private static String[] copyNames(String[] names) {
        String[] copy = new String[names.length];
        for (int product = 0; product < names.length; product++) {
            if (names[product] == null || names[product].trim().isEmpty()) {
                throw new IllegalArgumentException("The product at position " + product + " has no name.");
            }
            copy[product] = names[product];
        }
        return copy;
    }

    /**
     * Copies the two-dimensional sales array, checking every row on the way.
     */
    private static int[][] copySales(int[][] sales) {
        int[][] copy = new int[sales.length][MONTHS];
        for (int product = 0; product < sales.length; product++) {
            int[] row = sales[product];
            if (row == null || row.length != MONTHS) {
                throw new IllegalArgumentException(
                        "Each product needs exactly " + MONTHS + " monthly figures.");
            }
            for (int month = 0; month < MONTHS; month++) {
                if (row[month] < 0) {
                    throw new IllegalArgumentException("Sales figures may not be negative.");
                }
                copy[product][month] = row[month];
            }
        }
        return copy;
    }

    /**
     * Adds up the three months of every product.
     *
     * @return the three-month total of each product
     */
    private int[] totalEachProduct() {
        int[] totals = new int[monthlySales.length];
        for (int product = 0; product < monthlySales.length; product++) {
            int total = 0;
            for (int month = 0; month < MONTHS; month++) {
                total += monthlySales[product][month];
            }
            totals[product] = total;
        }
        return totals;
    }

    @Override
    public int GetTotalProductsSold() {
        int total = 0;
        for (int productTotal : salesPerProduct) {
            total += productTotal;
        }
        return total;
    }

    @Override
    public double GetAverageProductSold() {
        // Interpretation choice 1 (documented in the README): the sample prints
        // 17 for a total of 153, which only works when the total is divided by
        // the nine captured figures and not by the three products (153 / 3 = 51).
        // The sample report therefore decides this question, not the wording.
        int figuresInReport = productNames.length * MONTHS;
        return (double) GetTotalProductsSold() / figuresInReport;
    }

    @Override
    public String GetMostPopularProduct() {
        return productNames[bestSeller()];
    }

    /**
     * The figure the 2026 sample report prints after the most popular product.
     *
     * <p>Interpretation choice 2 (documented in the README): the sample shows
     * {@code Cozy Nights Monarch(25)} for the figures 25, 18 and 20. That is the
     * best seller's first month, not its three-month total of 63, so the sample
     * is followed exactly and this method keeps the decision in one place.</p>
     *
     * @return the first month's figure of the most popular product
     */
    public int GetMostPopularProductSales() {
        return monthlySales[bestSeller()][0];
    }

    /**
     * @return the three-month total of the most popular product
     */
    public int GetBestSellerSales() {
        return salesPerProduct[bestSeller()];
    }

    /**
     * Finds the product whose three months add up to the highest figure.
     *
     * @return the position of that product in the arrays
     */
    private int bestSeller() {
        int best = 0;
        for (int product = 1; product < salesPerProduct.length; product++) {
            if (salesPerProduct[product] > salesPerProduct[best]) {
                best = product;
            }
        }
        return best;
    }
}
