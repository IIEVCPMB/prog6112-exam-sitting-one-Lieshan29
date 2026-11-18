package za.co.emeris.sales;

/**
 * Operations the sales report must offer (Q.1.2).
 *
 * @author Lieshan Valliadum (ST10439494)
 */
public interface IProduct {

    /**
     * Adds up every sales figure in the report.
     *
     * @return the total number of products sold
     */
    int GetTotalProductsSold();

    /**
     * Works out the average number of products sold.
     *
     * @return the average number of products sold per product per month
     */
    double GetAverageProductSold();

    /**
     * Finds the product that sold the most units over the three months.
     *
     * @return the name of the most popular product
     */
    String GetMostPopularProduct();
}
