package za.co.emeris.sales.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.emeris.sales.Product;

/**
 * Unit tests for the sales report (Q.1.4).
 *
 * <p>The figures are the sample data of the brief: 25, 18, 20 / 15, 10, 12 /
 * 18, 15, 20, which must give a total of 153 and an average of 17.</p>
 *
 * @author Lieshan Valliadum (ST10439494)
 */
class SalesReportTest {

    private static final String[] PRODUCT_NAMES = {
        "Cozy Nights Monarch",
        "Restonic Barbados",
        "Sealy Phuket"
    };

    /** The sample figures of the brief. */
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

    @Test
    void GetMostPopularProduct_WithValidData_ReturnsBestSeller() {
        assertEquals("Cozy Nights Monarch", report.GetMostPopularProduct(),
                "The most popular product should be the one with the highest total.");
    }

    @Test
    void GetBestSellerSales_WithValidData_ReturnsThreeMonthTotal() {
        // 25 + 18 + 20 = 63. The report prints 25 (the first month) because that
        // is what the sample shows; this test pins the real total as well.
        assertEquals(63, report.GetBestSellerSales(),
                "The three-month total of the best seller should be 63.");
    }

    @Test
    void CreateProduct_WithMissingMonth_ThrowsException() {
        int[][] missingMonth = {{5, 6, 7}, {8, 9}};
        assertThrows(IllegalArgumentException.class,
                () -> new Product(new String[]{"First mattress", "Second mattress"}, missingMonth),
                "A product without three monthly figures must be rejected.");
    }

    @Test
    void CreateProduct_WithNegativeFigure_ThrowsException() {
        int[][] negativeFigure = {{5, 6, 7}, {8, -9, 10}};
        assertThrows(IllegalArgumentException.class,
                () -> new Product(new String[]{"First mattress", "Second mattress"}, negativeFigure),
                "Negative sales figures must be rejected.");
    }
}
