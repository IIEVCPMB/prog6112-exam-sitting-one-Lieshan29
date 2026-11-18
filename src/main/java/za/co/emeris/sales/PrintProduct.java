package za.co.emeris.sales;

/**
 * Prints the figures of the sales report (Q.1.3).
 *
 * @author Lieshan Valliadum (ST10439494)
 */
public class PrintProduct {

    /** Rule printed under the report heading. */
    private static final String RULE = "==============================================";

    private final Product report;

    /**
     * @param report the report whose figures must be printed
     */
    public PrintProduct(Product report) {
        if (report == null) {
            throw new IllegalArgumentException("A report is needed before it can be printed.");
        }
        this.report = report;
    }

    /**
     * Prints the report heading and rule.
     */
    public void PrintHeading() {
        System.out.printf("%nPRODUCT SALES REPORT - 2026%n%n%s%n", RULE);
    }

    /**
     * Prints the total number of products sold.
     */
    public void PrintTotalProductSales() {
        System.out.printf("TOTAL PRODUCT SALES: %d%n", report.GetTotalProductsSold());
    }

    /**
     * Prints the average number of products sold.
     */
    public void PrintAverageProductSales() {
        System.out.printf("AVERAGE PRODUCT SALES: %.0f%n", report.GetAverageProductSold());
    }

    /**
     * Prints the most popular product with its total sales.
     */
    public void PrintMostPopularProduct() {
        System.out.printf("MOST POPULAR PRODUCT: %s(%d)%n",
                report.GetMostPopularProduct(), report.GetBestSellerSales());
    }

    /**
     * Prints all three report figures.
     */
    public void PrintWholeReport() {
        PrintTotalProductSales();
        PrintAverageProductSales();
        PrintMostPopularProduct();
    }
}
