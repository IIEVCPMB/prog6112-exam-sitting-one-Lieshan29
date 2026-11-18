# Mattress Sales Report

PROG6112 exam submission of Lieshan Valliadum (ST10439494). It has two parts: a
console sales report (Question 1) and a Swing mattress sale calculator
(Question 2).

- **Question 1** - `SalesReportMain` reads three months of sales for three
  mattresses from the keyboard and prints the total sold, the average sold and
  the most popular mattress.
- **Question 2** - `ProductSalesForm` takes a customer name, a mattress and a
  price, then works out VAT (15%), commission (5%) and the total, appends the
  sale to `data.txt` and shows it in a message box.

Built with Java 17 and Maven. Run the report with `mvn compile exec:java`, the
form with `mvn compile exec:java -Dexec.mainClass=za.co.emeris.sales.ProductSalesForm`
and the unit tests with `mvn test`.

## AI Declaration

I declare that I used an AI assistant, **Buffy (the Codebuff coding agent)**, to
help write, structure, document and test this project. The design, my
understanding of the code and the final submission are my own, and I can explain
every part of it.
