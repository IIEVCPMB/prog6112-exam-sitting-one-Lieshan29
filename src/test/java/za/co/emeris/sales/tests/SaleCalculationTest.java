package za.co.emeris.sales.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import za.co.emeris.sales.ProductCalculations;

/**
 * Unit tests for the sale calculations (Q.2.6).
 *
 * <p>The worked example of the brief is a price of 3500, which must give a VAT of
 * 525.0, a commission of 175.0 and a total of 4200.0.</p>
 *
 * @author Lieshan Valliadum (ST10439494)
 */
class SaleCalculationTest {

    private static final double PRICE = 3500.0;

    private final ProductCalculations calculation = new ProductCalculations(PRICE);

    @Test
    void GetVat_WithValidData_ReturnsCorrectValue() {
        assertEquals(525.0, calculation.GetVat(), 0.001,
                "15% of 3500 is 525.0.");
    }

    @Test
    void GetCommission_WithValidData_ReturnsCorrectValue() {
        assertEquals(175.0, calculation.GetCommission(), 0.001,
                "5% of 3500 is 175.0.");
    }

    @Test
    void GetTotal_WithValidData_ReturnsCorrectValue() {
        assertEquals(4200.0, calculation.GetTotal(), 0.001,
                "3500 plus 525 VAT plus 175 commission is 4200.0.");
    }

    @Test
    void CreateCalculations_WithNegativePrice_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new ProductCalculations(-3500),
                "A negative price must be rejected.");
    }
}
