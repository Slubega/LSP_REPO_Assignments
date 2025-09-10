# Assignment 2 — ETL Pipeline

This project implements an **Extract–Transform–Load (ETL)** pipeline in Java. The program reads product data from `data/products.csv`, applies a series of transformations, and writes the results to `data/transformed_products.csv`.  
It was developed for **Howard University Large Scale Programming, Fall 2025**.

---

## Assumptions
- Input file is always named `products.csv` and located in the `data/` folder.  
- Output file is written to `data/transformed_products.csv`.  
- If the input file is missing or empty, the program still produces an output file with only the header.  
- Input rows must have exactly four columns: `ProductID,Name,Price,Category`.  
- Rows with missing or malformed data are skipped.  

---

## Design Notes
The program follows a simple ETL pipeline:

1. **Extract**  
   - Reads the CSV line by line.  
   - Skips malformed rows while counting skipped records.  

2. **Transform (in order)**  
   - Converts product names to **UPPERCASE**.  
   - Applies a **10% discount** if the original category is *Electronics*.  
   - If the discounted price is greater than $500 and the original category was *Electronics*, recategorize as **Premium Electronics**.  
   - Assigns a **PriceRange** based on final price:  
     - ≤ $10 → Low  
     - ≤ $100 → Medium  
     - ≤ $500 → High  
     - > $500 → Premium  

3. **Load**  
   - Writes transformed data to `data/transformed_products.csv`.  
   - Includes header: `ProductID,Name,Price,Category,PriceRange`.  
   - Prints a run summary: rows read, transformed, skipped, and output file path.  

---

## How to Run
From the **repo root**, run:

```powershell
cd Assignment2
javac -d out src\org\howard\edu\lsp\assignment2\ETLPipeline.java
java -cp out org.howard.edu.lsp.assignment2.ETLPipeline

## Project Layout
Assignment2/
├─ data/
│  ├─ products.csv              # Input
│  └─ transformed_products.csv  # Output
└─ src/
   └─ org/howard/edu/lsp/assignment2/ETLPipeline.java

## Example

Input (products.csv):

ProductID,Name,Price,Category
1,Book,12.99,Education
2,Laptop,999.99,Electronics
3,Notebook,2.49,Stationery

Output (transformed_products.csv):

ProductID,Name,Price,Category,PriceRange
1,BOOK,12.99,Education,Medium
2,LAPTOP,899.99,Premium Electronics,Premium
3,NOTEBOOK,2.49,Stationery,Low

## AI Usage

I used ChatGPT to clarify assignment requirements, design the folder structure, and generate starter code for the ETL pipeline. Prompt example: “write a java ETL program that reads products.csv, transforms names, discounts electronics, recategorizes premium electronics, and assigns price ranges.” AI Response excerpt:

if (p.price.compareTo(new BigDecimal("500.00")) > 0
    && p.originalCategory.equalsIgnoreCase("Electronics")) {
    p.category = "Premium Electronics";
}


I integrated the suggested logic into my final program, tested with the provided CSV cases, and modified parts (like error handling and summary output) to meet assignment specifications.
