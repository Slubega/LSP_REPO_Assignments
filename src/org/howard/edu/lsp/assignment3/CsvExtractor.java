package org.howard.edu.lsp.assignment3;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/** Reads products from data/products.csv using a relative path (A2 rule). */
public class CsvExtractor {
    private final Path inputPath;

    public CsvExtractor(Path inputPath) {
        this.inputPath = inputPath;
    }

    /** Returns products and counts [rowsRead, rowsSkipped]. Always tolerates empty/malformed rows. */
    public Result extract() throws IOException {
        int rowsRead = 0, rowsSkipped = 0;
        List<Product> products = new ArrayList<>();

        if (!Files.exists(inputPath)) {
            // caller handles error/summary printing; we just signal missing file
            throw new NoSuchFileException("Missing input file: " + inputPath);
        }

        try (BufferedReader br = Files.newBufferedReader(inputPath)) {
            String header = br.readLine();
            if (header == null) {
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
                } catch (Exception e) { rowsSkipped++; }
            }
        }
        return new Result(products, rowsRead, rowsSkipped, false);
    }

    /** Simple tuple for extractor result. */
    public static class Result {
        public final List<Product> products;
        public final int rowsRead;
        public final int rowsSkipped;
        public final boolean headerOnly;

        public Result(List<Product> products, int rowsRead, int rowsSkipped, boolean headerOnly) {
            this.products = products;
            this.rowsRead = rowsRead;
            this.rowsSkipped = rowsSkipped;
            this.headerOnly = headerOnly;
        }
    }
}
