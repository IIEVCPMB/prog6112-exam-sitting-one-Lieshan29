package za.co.emeris.sales;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the sale calculations (Q.2.6).
 *
 * @author Lieshan Valliadum (ST10439494)
 */
class SaleCalculationTest {

    private static final double PRICE = 2500.0;

    private final ProductCalculations calculation = new ProductCalculations(PRICE);

    @Test
    void GetVat_WithValidData_ReturnsCorrectValue() {
        double expectedVat = 375.0; // 15% of 2500
        assertEquals(expectedVat, calculation.GetVat(), 0.001,
                "The VAT amount is wrong.");
    }

    @Test
    void GetCommission_WithValidData_ReturnsCorrectValue() {
        double expectedCommission = 125.0; // 5% of 2500
        assertEquals(expectedCommission, calculation.GetCommission(), 0.001,
                "The commission amount is wrong.");
    }

    @Test
    void GetTotal_WithValidData_ReturnsCorrectValue() {
        double expectedTotal = 3000.0; // 2500 + 375 + 125
        assertEquals(expectedTotal, calculation.GetTotal(), 0.001,
                "The total sale amount is wrong.");
    }

    @Test
    void CreateCalculations_WithNegativePrice_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new ProductCalculations(-250));
    }
}
