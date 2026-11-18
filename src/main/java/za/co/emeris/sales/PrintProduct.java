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
}
