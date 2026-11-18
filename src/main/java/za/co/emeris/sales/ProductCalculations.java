package za.co.emeris.sales;

/**
 * Sale calculations for one product price (Q.2.5).
 *
 * <p>VAT is 15% of the price, commission is 5% of the price and the total sale
 * amount is the price plus both. The two rates are named constants so the
 * percentages appear once and are never written as magic numbers.</p>
 *
 * <p>Design decision: both amounts are worked out once in the constructor and
 * then returned, so {@link #GetTotal()} does not recompute them.</p>
 *
 * @author Lieshan Valliadum (ST10439494)
 */
public class ProductCalculations implements IProductCalculations {

    /** VAT charged on the price, as a percentage. */
    public static final double VAT_PERCENTAGE = 15.0;

    /** Commission earned on the price, as a percentage. */
    public static final double COMMISSION_PERCENTAGE = 5.0;

    private final double productPrice;
    private final double vat;
    private final double commission;

    /**
     * @param productPrice the price of the mattress that is being sold
     * @throws IllegalArgumentException when the price is missing or negative
     */
    public ProductCalculations(double productPrice) {
        if (Double.isNaN(productPrice) || Double.isInfinite(productPrice) || productPrice < 0) {
            throw new IllegalArgumentException("A price of zero or more is required.");
        }
        this.productPrice = productPrice;
        this.vat = percentageOfPrice(VAT_PERCENTAGE);
        this.commission = percentageOfPrice(COMMISSION_PERCENTAGE);
    }

    /**
     * Works out a percentage of the price.
     *
     * @param percentage the percentage to take, for example 15.0
     * @return that percentage of the price
     */
    private double percentageOfPrice(double percentage) {
        return productPrice * percentage / 100.0;
    }

    @Override
    public double GetVat() {
        return vat;
    }

    @Override
    public double GetCommission() {
        return commission;
    }

    @Override
    public double GetTotal() {
        return productPrice + vat + commission;
    }
}
