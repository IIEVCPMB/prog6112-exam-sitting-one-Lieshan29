package za.co.emeris.sales;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the sales report model (Q.1.4).
 *
 * @author Lieshan Valliadum (ST10439494)
 */
class SalesReportTest {

    private static final String[] PRODUCT_NAMES = {
        "Cozy Nights Monarch",
        "Restonic Barbados",
        "Sealy Phuket"
    };

    private static final int[][] SALES = {
        {30, 20, 25},
        {12, 14, 18},
        {22, 16, 14}
    };

    private Product report;

    @BeforeEach
    void createReport() {
        report = new Product(PRODUCT_NAMES, SALES);
    }

    @Test
    void GetTotalProductsSales_WithValidData_ReturnsCorrectValue() {
        int expectedTotal = 171; // 75 + 44 + 52
        assertEquals(expectedTotal, report.GetTotalProductsSold(),
                "The total products sold was calculated incorrectly.");
    }

    @Test
    void GetAverageSales_WithValidData_ReturnsCorrectValue() {
        double expectedAverage = 19.0; // 171 / 9 figures
        assertEquals(expectedAverage, report.GetAverageProductSold(), 0.001,
                "The average products sold was calculated incorrectly.");
    }

    @Test
    void GetMostPopularProduct_WithValidData_ReturnsBestSeller() {
        assertEquals("Cozy Nights Monarch", report.GetMostPopularProduct(),
                "The most popular product was identified incorrectly.");
        assertEquals(75, report.GetBestSellerSales(),
                "The sales of the most popular product are incorrect.");
    }

    @Test
    void CreateProduct_WithIncompleteMonths_ThrowsException() {
        int[][] incompleteSales = {{5, 6, 7}, {8, 9}};
        assertThrows(IllegalArgumentException.class,
                () -> new Product(new String[]{"First product", "Second product"}, incompleteSales));
    }
}
