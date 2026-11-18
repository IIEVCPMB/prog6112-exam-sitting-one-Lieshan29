package za.co.emeris.sales;

/**
 * All report and file text that both applications share (Q.1.1, Q.2.3).
 *
 * <p>Design decision: the console report, the {@code Message} dialogue and
 * {@code data.txt} all print the same asterisk rule and the same money format.
 * Keeping that text in one small class means the layout is written once and
 * cannot drift between the two applications (no duplicated formatting).</p>
 *
 * @author Lieshan Valliadum (ST10439494)
 */
public final class SaleFormat {

    /** The rule printed under the report heading and around each saved sale. */
    public static final String RULE = "***************************************************";

    /** Only the static helpers are used, so the class is never instantiated. */
    private SaleFormat() {
    }

    /**
     * Formats money the way the brief prints it, for example {@code R 525.0}.
     *
     * @param amount the money value to display
     * @return the amount with an {@code R } prefix and one decimal place
     */
    public static String money(double amount) {
        return String.format("R %.1f", amount);
    }

    /**
     * Builds the five lines that describe one sale.
     *
     * <p>The message dialogue shows exactly these lines and {@code data.txt}
     * stores exactly these lines, so the sale layout also lives in one place.</p>
     *
     * @param customerName the customer who bought the mattress
     * @param productName  the mattress that was sold
     * @param calculation  the figures worked out for this sale
     * @return the sale description, one field per line
     */
    public static String saleLines(String customerName, String productName,
            IProductCalculations calculation) {
        return String.join(System.lineSeparator(),
                "Customer Name: " + customerName,
                "Product: " + productName,
                "VAT: " + money(calculation.GetVat()),
                "COMMISSION: " + money(calculation.GetCommission()),
                "TOTAL: " + money(calculation.GetTotal()));
    }
}
