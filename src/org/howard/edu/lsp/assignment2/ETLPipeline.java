package org.howard.edu.lsp.assignment2;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class ETLPipeline {

    // ---------- Data model ----------
    static class Product {
        int id;
        String name;
        BigDecimal price;   // final price (after transform steps)
        String category;    // possibly recategorized
        String priceRange;

        // Keep some original fields for rules that depend on them
        String originalCategory;
        @SuppressWarnings("unused")
        BigDecimal originalPrice;

        Product(int id, String name, BigDecimal price, String category) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.category = category;
            this.originalCategory = category;
            this.originalPrice = price;
        }
    }

    public static void main(String[] args) {
        Path projectRoot = Paths.get("").toAbsolutePath();
        Path input = Paths.get("data", "products.csv");      // relative path
        Path output = Paths.get("data", "transformed_products.csv"); // relative path

        int rowsRead = 0, rowsTransformed = 0, rowsSkipped = 0;

        // 1) Extract
        List<Product> products = new ArrayList<>();
        if (!Files.exists(input)) {
            System.err.println("ERROR: Missing input file '" + input + "'. Run from the project root containing src/ and data/.");
            printSummary(rowsRead, rowsTransformed, rowsSkipped, output);
            return;
        }

        try (BufferedReader br = Files.newBufferedReader(input)) {
            String header = br.readLine();
            if (header == null) {
                // empty file (no header) – treat as missing data but still write header out
                writeOutputHeaderOnly(output);
                printSummary(rowsRead, rowsTransformed, rowsSkipped, output);
                return;
            }

            // Always create output with header even if no rows
            List<String[]> rawRows = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                rowsRead++;
                String[] parts = line.split(",", -1);
                if (parts.length != 4) { // malformed row
                    rowsSkipped++;
                    continue;
                }
                try {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    BigDecimal price = new BigDecimal(parts[2].trim());
                    String category = parts[3].trim();
                    rawRows.add(new String[] { String.valueOf(id), name, price.toPlainString(), category });
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException parseEx) {
                    rowsSkipped++;
                }
            }

            // Convert to products
            for (String[] r : rawRows) {
                products.add(new Product(
                    Integer.parseInt(r[0]),
                    r[1],
                    new BigDecimal(r[2]),
                    r[3]
                ));
            }

        } catch (IOException ioe) {
            System.err.println("ERROR reading input: " + ioe.getMessage());
            printSummary(rowsRead, rowsTransformed, rowsSkipped, output);
            return;
        }

        // If header only (no data rows), still write header
        if (products.isEmpty()) {
            writeOutputHeaderOnly(output);
            printSummary(rowsRead, rowsTransformed, rowsSkipped, output);
            return;
        }

        // (2) Transform  (order matters)
        // (1) uppercase name → (2) discount if Electronics → (3) recategorize → (4) price range from FINAL price
        for (Product p : products) {
            // (1) UPPERCASE name
            p.name = p.name.toUpperCase();

            // (2) 10% discount for Electronics
            if (p.originalCategory.equalsIgnoreCase("Electronics")) {
                p.price = round2(p.price.multiply(new BigDecimal("0.90")));
                // Keep category as-is for now; rule (3) can recategorize
            } else {
                p.price = round2(p.price); // ensure 2 decimals consistently
            }

            // (3) Recategorize if final price > 500 AND original category was Electronics
            if (p.price.compareTo(new BigDecimal("500.00")) > 0
                && p.originalCategory.equalsIgnoreCase("Electronics")) {
                p.category = "Premium Electronics";
            } else {
                // Keep original category (which might be "Electronics" or something else)
                p.category = p.originalCategory;
            }

            // (4) PriceRange based on FINAL price
            p.priceRange = computePriceRange(p.price);

            rowsTransformed++;
        }

        // 3) Load
        try {
            Files.createDirectories(output.getParent());
            try (BufferedWriter bw = Files.newBufferedWriter(output)) {
                // Header
                bw.write("ProductID,Name,Price,Category,PriceRange");
                bw.newLine();

                // Rows
                for (Product p : products) {
                    bw.write(
                        p.id + "," +
                        p.name + "," +
                        p.price.toPlainString() + "," +
                        p.category + "," +
                        p.priceRange
                    );
                    bw.newLine();
                }
            }
        } catch (IOException ioe) {
            System.err.println("ERROR writing output: " + ioe.getMessage());
            printSummary(rowsRead, rowsTransformed, rowsSkipped, output);
            return;
        }

        // Done
        printSummary(rowsRead, rowsTransformed, rowsSkipped, output);
        System.out.println("Run from: " + projectRoot);
    }

    // ---------- Helpers ----------
    private static BigDecimal round2(BigDecimal x) {
        return x.setScale(2, RoundingMode.HALF_UP);
    }

    private static String computePriceRange(BigDecimal price) {
        int cmp10 = price.compareTo(new BigDecimal("10.00"));
        int cmp100 = price.compareTo(new BigDecimal("100.00"));
        int cmp500 = price.compareTo(new BigDecimal("500.00"));

        if (cmp10 <= 0) return "Low";
        if (cmp100 <= 0) return "Medium";
        if (cmp500 <= 0) return "High";
        return "Premium";
    }

    private static void printSummary(int read, int transformed, int skipped, Path outPath) {
        System.out.println("Summary:");
        System.out.println("  Rows read:         " + read);
        System.out.println("  Rows transformed:  " + transformed);
        System.out.println("  Rows skipped:      " + skipped);
        System.out.println("  Output written to: " + outPath.toString());
    }

    private static void writeOutputHeaderOnly(Path output) {
        try {
            Files.createDirectories(output.getParent());
            try (BufferedWriter bw = Files.newBufferedWriter(output)) {
                bw.write("ProductID,Name,Price,Category,PriceRange");
                bw.newLine();
            }
        } catch (IOException ioe) {
            System.err.println("ERROR writing header-only output: " + ioe.getMessage());
        }
    }
}
