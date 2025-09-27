package org.howard.edu.lsp.assignment3;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Orchestrates Extract -> Transform -> Load with the same behavior as A2.
 * Run from project root (folder containing src/ and data/).
 */
public class ETLPipelineApp {

    public static void main(String[] args) {
        Path input = Paths.get("data", "products.csv");               // relative path
        Path output = Paths.get("data", "transformed_products.csv");  // relative path

        CsvExtractor extractor = new CsvExtractor(input);
        CsvLoader loader = new CsvLoader(output);
        ProductTransformer transformer = new ProductTransformer();

        int rowsRead = 0, rowsTransformed = 0, rowsSkipped = 0;

        // Extract
        List<Product> products;
        try {
            CsvExtractor.Result r = extractor.extract();
            rowsRead = r.rowsRead;
            rowsSkipped = r.rowsSkipped;

            if (r.headerOnly) {             // empty input: still write header
                loader.writeHeaderOnly();
                printSummary(rowsRead, rowsTransformed, rowsSkipped, output.toString());
                return;
            }
            products = r.products;
        } catch (Exception e) {              // missing file or read error
            System.err.println("ERROR: " + e.getMessage());
            printSummary(rowsRead, rowsTransformed, rowsSkipped, output.toString());
            return;
        }

        // Transform (order matters)
        rowsTransformed = transformer.transform(products);

        // Load
        try {
            loader.writeAll(products);
        } catch (Exception e) {
            System.err.println("ERROR writing output: " + e.getMessage());
            printSummary(rowsRead, rowsTransformed, rowsSkipped, output.toString());
            return;
        }

        printSummary(rowsRead, rowsTransformed, rowsSkipped, output.toString());
    }

    private static void printSummary(int read, int transformed, int skipped, String outPath) {
        System.out.println("Summary:");
        System.out.println("  Rows read:         " + read);
        System.out.println("  Rows transformed:  " + transformed);
        System.out.println("  Rows skipped:      " + skipped);
        System.out.println("  Output written to: " + outPath);
    }
}
