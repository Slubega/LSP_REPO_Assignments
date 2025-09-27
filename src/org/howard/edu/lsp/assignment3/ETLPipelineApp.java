package org.howard.edu.lsp.assignment3;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Orchestrates the ETL pipeline (Extract -> Transform -> Load) and prints a summary.
 * Run from the project root so relative data paths resolve.
 */
public class ETLPipelineApp {

    /**
     * Runs the ETL pipeline using relative paths:
     * input data/products.csv and output data/transformed_products.csv.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        Path input  = Paths.get("data", "products.csv");
        Path output = Paths.get("data", "transformed_products.csv");

        CsvExtractor extractor = new CsvExtractor(input);
        ProductTransformer transformer = new ProductTransformer();
        CsvLoader loader = new CsvLoader(output);

        int rowsRead = 0, rowsTransformed = 0, rowsSkipped = 0;
        List<Product> products;

        // Extract
        try {
            CsvExtractor.Result r = extractor.extract();
            rowsRead = r.rowsRead;
            rowsSkipped = r.rowsSkipped;

            if (r.headerOnly) {
                // Header present but no data rows: still produce a header-only output file
                loader.writeHeaderOnly();
                printSummary(rowsRead, rowsTransformed, rowsSkipped, output.toString());
                return;
            }
            products = r.products;

        } catch (NoSuchFileException e) {
            System.err.println("ERROR: Missing input file '" + input + "'.");
            printSummary(rowsRead, rowsTransformed, rowsSkipped, output.toString());
            return;
        } catch (IOException e) {
            System.err.println("ERROR reading input: " + e.getMessage());
            printSummary(rowsRead, rowsTransformed, rowsSkipped, output.toString());
            return;
        }

        // Transform
        rowsTransformed = transformer.transform(products);

        // Load
        try {
            loader.writeAll(products);
        } catch (IOException e) {
            System.err.println("ERROR writing output: " + e.getMessage());
            printSummary(rowsRead, rowsTransformed, rowsSkipped, output.toString());
            return;
        }

        // Done
        printSummary(rowsRead, rowsTransformed, rowsSkipped, output.toString());
    }

    /**
     * Prints a run summary to standard output.
     *
     * @param read        rows read (data rows, excludes header)
     * @param transformed rows transformed
     * @param skipped     rows skipped as malformed
     * @param outPath     path to the written output CSV
     */
    private static void printSummary(final int read, final int transformed, final int skipped, final String outPath) {
        System.out.println("Summary:");
        System.out.println("  Rows read:         " + read);
        System.out.println("  Rows transformed:  " + transformed);
        System.out.println("  Rows skipped:      " + skipped);
        System.out.println("  Output written to: " + outPath);
    }
}
