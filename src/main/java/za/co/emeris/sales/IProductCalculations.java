package za.co.emeris.sales;

/**
 * Operations for the VAT, commission and total sale amount (Q.2.5).
 *
 * @author Lieshan Valliadum (ST10439494)
 */
public interface IProductCalculations {

    /**
     * @return the VAT that must be charged
     */
    double GetVat();

    /**
     * @return the commission that is earned
     */
    double GetCommission();

    /**
     * @return the total amount the customer pays
     */
    double GetTotal();
}
