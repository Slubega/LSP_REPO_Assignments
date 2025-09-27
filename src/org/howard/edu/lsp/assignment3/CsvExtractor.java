package org.howard.edu.lsp.assignment3;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Extract step that reads products from a CSV file using a relative path.
 * Malformed rows are skipped and counted. Header-only files are supported.
 */
public class CsvExtractor {
    private final Path inputPath;

    /**
     * Creates a new extractor.
     *
     * @param inputPath path to the input CSV (for example, data/products.csv)
     */
    public CsvExtractor(final Path inputPath) {
        this.inputPath = inputPath;
    }

    /**
     * Reads and parses rows from the input CSV file into Product objects.
     *
     * Behavior:
     * 1. If the file is missing, throws NoSuchFileException.
     * 2. If the file contains only a header, returns a Result with headerOnly=true and no products.
     * 3. Malformed rows are skipped and counted in rowsSkipped.
     *
     * @return a Result containing:
     * - products: the successfully parsed products
     * - rowsRead: number of data rows read from the file
     * - rowsSkipped: number of malformed rows
     * - headerOnly: whether the file contained only the header row
     * @throws IOException if the file cannot be read
     */
    public Result extract() throws IOException {
        if (!Files.exists(inputPath)) {
            throw new NoSuchFileException(inputPath.toString());
        }

        List<Product> products = new ArrayList<>();
        int rowsRead = 0;
        int rowsSkipped = 0;

        try (BufferedReader br = Files.newBufferedReader(inputPath)) {
            String header = br.readLine();
            if (header == null) { // empty file (no header)
                return new Result(products, rowsRead, rowsSkipped, true);
            }

            String line;
            while ((line = br.readLine()) != null) {
                rowsRead++;
                String[] parts = line.split(",", -1);
                if (parts.length != 4) { rowsSkipped++; continue; }

                try {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    BigDecimal price = new BigDecimal(parts[2].trim());
                    String category = parts[3].trim();
                    products.add(new Product(id, name, price, category));
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
                    rowsSkipped++;
                }
            }
        }

        boolean headerOnly = (rowsRead == 0);
        return new Result(products, rowsRead, rowsSkipped, headerOnly);
    }

    /**
     * Result of extraction containing products and counters.
     */
    public static class Result {
        /** Parsed products. */
        public final List<Product> products;
        /** Number of data rows read (excludes header). */
        public final int rowsRead;
        /** Number of rows skipped due to malformed input. */
        public final int rowsSkipped;
        /** True if the file contained only a header row. */
        public final boolean headerOnly;

        /**
         * Constructs a result record.
         *
         * @param products    parsed products
         * @param rowsRead    number of data rows read
         * @param rowsSkipped number of rows skipped as malformed
         * @param headerOnly  whether the file had only a header row
         */
        public Result(final List<Product> products, final int rowsRead,
                      final int rowsSkipped, final boolean headerOnly) {
            this.products = products;
            this.rowsRead = rowsRead;
            this.rowsSkipped = rowsSkipped;
            this.headerOnly = headerOnly;
        }
    }
}
