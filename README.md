## Assignment 2  ETL Pipeline

**How to run (from repo root):**
\\\powershell
cd Assignment2
javac -d out src\org\howard\edu\lsp\assignment2\ETLPipeline.java
java -cp out org.howard.edu.lsp.assignment2.ETLPipeline
\\\

**Layout**
\\\
Assignment1/
Assignment2/
 data/
   products.csv
   transformed_products.csv  (after running)
 src/
    org/howard/edu/lsp/assignment2/ETLPipeline.java
\\\

**Notes**
- Relative paths: data/products.csv  data/transformed_products.csv
- Transform order: uppercase  10% electronics discount  recategorize (> \  Premium Electronics)  price range from final price
- 2-decimal rounding (HALF_UP)
- Prints run summary
