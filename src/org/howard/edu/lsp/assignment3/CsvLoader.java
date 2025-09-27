package org.howard.edu.lsp.assignment3;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Load step that writes transformed products to a CSV output file
 * using a relative path such as data/transformed_products.csv.
 */
public class CsvLoader {
    private final Path outputPath;

    /**
     * Creates a new loader.
     *
     * @param outputPath the output file path
     */
    public CsvLoader(final Path outputPath) {
        this.outputPath = outputPath;
    }

    /**
     * Writes only the CSV header (used for header-only or empty input).
     *
     * @throws IOException if the file cannot be written
     */
    public void writeHeaderOnly() throws IOException {
        Files.createDirectories(outputPath.getParent());
        try (BufferedWriter bw = Files.newBufferedWriter(outputPath)) {
            bw.write("ProductID,Name,Price,Category,PriceRange");
            bw.newLine();
        }
    }

    /**
     * Writes the CSV header and all product rows.
     *
     * @param products the products to write
     * @throws IOException if the file cannot be written
     */
    public void writeAll(final List<Product> products) throws IOException {
        Files.createDirectories(outputPath.getParent());
        try (BufferedWriter bw = Files.newBufferedWriter(outputPath)) {
            bw.write("ProductID,Name,Price,Category,PriceRange");
            bw.newLine();
            for (Product p : products) {
                bw.write(p.toCsvRow());
                bw.newLine();
            }
        }
    }
}
