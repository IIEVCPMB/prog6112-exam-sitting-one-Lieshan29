# Mattress Sales Report

PROG6112 exam submission of Lieshan Valliadum (ST10439494). Two entry points share one set
of model classes:

| Application | Class to run | What it does |
|---|---|---|
| Question 1 - console sales report | `za.co.emeris.sales.SalesReportApp` | Reads nine sales figures (three mattresses, three months) and prints the total, the average and the most popular product. |
| Question 2 - mattress sales form | `za.co.emeris.sales.ProductApp` | Swing form that works out VAT (15%), commission (5%) and the total sale amount, shows them in a `Message` dialogue and appends the sale to `data.txt`. |

## Build and run

Needs a JDK 17 or newer and Maven.

```bash
mvn clean test                                          # run the unit tests
mvn package                                             # build target/mattress-sales-report-1.0.jar
java -jar target/mattress-sales-report-1.0.jar           # console report
mvn exec:java -Dexec.mainClass=za.co.emeris.sales.ProductApp   # sales form
```

Or start the form with `java -cp target/classes za.co.emeris.sales.ProductApp`.

## Sample input and expected output (Question 1)

Typed in this order: 25, 18, 20 / 15, 10, 12 / 18, 15, 20

```
PRODUCT SALES REPORT - 2026

***************************************************
TOTAL PRODUCT SALES: 153
AVERAGE PRODUCT SALES: 17
MOST POPULAR PRODUCT: Cozy Nights Monarch(25)
```

## Interpretation choices

1. **The average divides by 9, not 3.** The wording says "per product", which would give
   153 / 3 = 51, but the sample prints 17. 17 is 153 / 9, so the report follows the sample
   and divides the total by the nine captured month entries (three products x three months).
2. **The figure after the most popular product is its first month, not its total.** The
   sample prints `Cozy Nights Monarch(25)` for the figures 25, 18 and 20. 25 is that
   product's month 1; its three-month total is 63. The sample is followed exactly so the
   console output matches the brief. The model still exposes the true total through
   `Product.GetBestSellerSales()`, which the unit tests check.

## Question 2 worked example

Price 3500 gives VAT `R 525.0`, commission `R 175.0` and total `R 4200.0`. The message
dialogue shows:

```
Customer Name: Joe Bloggs
Product: Cozy Nights Monarch
VAT: R 525.0
COMMISSION: R 175.0
TOTAL: R 4200.0
```

`data.txt` receives the same lines between two asterisk rules, under a `DATA LOG` heading
that is written once; later sales are appended so the file stays a log.

## AI Declaration

I declare that I used an AI assistant, **Buffy (the Codebuff coding agent)**, to help write,
structure, document and test this project. The design, my understanding of the code and the
final submission are my own, and I can explain every part of it.
