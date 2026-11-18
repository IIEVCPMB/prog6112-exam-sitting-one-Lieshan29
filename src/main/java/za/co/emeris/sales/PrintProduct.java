package za.co.emeris.sales;

/**
 * Prints the sales report (Q.1.3).
 *
 * <p>Design decision: printing is kept out of the model. {@code Product} works
 * the figures out and this class only decides what the report looks like, so a
 * change in layout never touches the calculations.</p>
 *
 * @author Lieshan Valliadum (ST10439494)
 */
public class PrintProduct {

    private final Product report;

    /**
     * @param report the report whose figures must be printed
     * @throws IllegalArgumentException when no report is supplied
     */
    public PrintProduct(Product report) {
        if (report == null) {
            throw new IllegalArgumentException("A report is needed before it can be printed.");
        }
        this.report = report;
    }

    /**
     * Prints the report heading and the asterisk rule under it.
     */
    public void PrintHeading() {
        System.out.println("PRODUCT SALES REPORT - 2026");
        System.out.println();
        System.out.println(SaleFormat.RULE);
    }

    /**
     * Prints the total number of products sold.
     */
    public void PrintTotalProductSales() {
        System.out.printf("TOTAL PRODUCT SALES: %d%n", report.GetTotalProductsSold());
    }

    /**
     * Prints the average number of products sold per month entry.
     */
    public void PrintAverageProductSales() {
        System.out.printf("AVERAGE PRODUCT SALES: %.0f%n", report.GetAverageProductSold());
    }

    /**
     * Prints the most popular product with the figure the sample report shows
     * (its first month, see the note on {@link Product#GetMostPopularProductSales()}).
     */
    public void PrintMostPopularProduct() {
        System.out.printf("MOST POPULAR PRODUCT: %s(%d)%n",
                report.GetMostPopularProduct(), report.GetMostPopularProductSales());
    }

    /**
     * Prints the three figures of the report in the order of the sample.
     */
    public void PrintWholeReport() {
        PrintTotalProductSales();
        PrintAverageProductSales();
        PrintMostPopularProduct();
    }
}
