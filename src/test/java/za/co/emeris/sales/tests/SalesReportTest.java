package za.co.emeris.sales.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.emeris.sales.Product;

/**
 * Unit tests for the sales report (Q.1.4).
 *
 * @author Lieshan Valliadum (ST10439494)
 */
class SalesReportTest {

    private static final String[] PRODUCT_NAMES = {
        "Cozy Nights Monarch",
        "Restonic Barbados",
        "Sealy Phuket"
    };

    /** The sample figures of the brief: 25, 18, 20 / 15, 10, 12 / 18, 15, 20. */
    private static final int[][] SAMPLE_SALES = {
        {25, 18, 20},
        {15, 10, 12},
        {18, 15, 20}
    };

    private Product report;

    @BeforeEach
    void createReport() {
        report = new Product(PRODUCT_NAMES, SAMPLE_SALES);
    }

    @Test
    void GetTotalProductSales_WithValidData_ReturnsCorrectValue() {
        assertEquals(153, report.GetTotalProductsSold(),
                "The total products sold should be 153 for the sample figures.");
    }

    @Test
    void GetAverageSales_WithValidData_ReturnsCorrectValue() {
        assertEquals(17.0, report.GetAverageProductSold(), 0.001,
                "The sample divides 153 by the nine month entries, so the average is 17.");
    }
}
