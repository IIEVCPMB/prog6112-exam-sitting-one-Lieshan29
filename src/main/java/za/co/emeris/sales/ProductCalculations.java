package za.co.emeris.sales;

/**
 * Sale calculations for one product price (Q.2.5).
 *
 * <p>VAT is 15% of the price, commission is 5% of the price and the total is the
 * price plus VAT plus commission. The percentages are kept as whole numbers and
 * divided by 100 when used.</p>
 *
 * @author Lieshan Valliadum (ST10439494)
 */
public class ProductCalculations implements IProductCalculations {

    /** VAT percentage charged on the price. */
    public static final double VAT_PERCENTAGE = 15.0;

    /** Commission percentage earned on the price. */
    public static final double COMMISSION_PERCENTAGE = 5.0;

    private final double productPrice;

    /**
     * @param productPrice the price of the mattress that is being sold
     */
    public ProductCalculations(double productPrice) {
        if (Double.isNaN(productPrice) || productPrice < 0) {
            throw new IllegalArgumentException("A product price of zero or more is required.");
        }
        this.productPrice = productPrice;
    }

    @Override
    public double GetVat() {
        return productPrice * VAT_PERCENTAGE / 100.0;
    }

    @Override
    public double GetCommission() {
        return productPrice * COMMISSION_PERCENTAGE / 100.0;
    }

    @Override
    public double GetTotal() {
        return productPrice + GetVat() + GetCommission();
    }
}
