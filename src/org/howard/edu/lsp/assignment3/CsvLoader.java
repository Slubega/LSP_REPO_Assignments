package org.howard.edu.lsp.assignment3;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

/** Writes header + rows to data/transformed_products.csv (relative path). */
public class CsvLoader {
    private final Path outputPath;

    public CsvLoader(Path outputPath) {
        this.outputPath = outputPath;
    }

    public void writeHeaderOnly() throws IOException {
        Files.createDirectories(outputPath.getParent());
        try (BufferedWriter bw = Files.newBufferedWriter(outputPath)) {
            bw.write("ProductID,Name,Price,Category,PriceRange");
            bw.newLine();
        }
    }

    public void writeAll(List<Product> products) throws IOException {
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
