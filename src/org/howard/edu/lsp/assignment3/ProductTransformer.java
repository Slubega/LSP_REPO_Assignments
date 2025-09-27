package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/** Applies the A2 transform rules in the required order. */
public class ProductTransformer {

    public int transform(List<Product> products) {
        int transformed = 0;
        for (Product p : products) {
            // (1) Uppercase name
            p.setName(p.getName().toUpperCase());

            // (2) 10% discount for Electronics (based on ORIGINAL category)
            BigDecimal price = round2(p.getPrice());
            if (p.getOriginalCategory().equalsIgnoreCase("Electronics")) {
                price = round2(price.multiply(new BigDecimal("0.90")));
            }
            p.setPrice(price);

            // (3) Recategorize if final price > 500 and original category was Electronics
            if (price.compareTo(new BigDecimal("500.00")) > 0 &&
                p.getOriginalCategory().equalsIgnoreCase("Electronics")) {
                p.setCategory("Premium Electronics");
            } else {
                p.setCategory(p.getOriginalCategory());
            }

            // (4) PriceRange from FINAL price
            p.setPriceRange(priceRange(price));

            transformed++;
        }
        return transformed;
    }

    private static BigDecimal round2(BigDecimal x) {
        return x.setScale(2, RoundingMode.HALF_UP);
    }

    private static String priceRange(BigDecimal price) {
        int c10 = price.compareTo(new BigDecimal("10.00"));
        int c100 = price.compareTo(new BigDecimal("100.00"));
        int c500 = price.compareTo(new BigDecimal("500.00"));

        if (c10 <= 0) return "Low";
        if (c100 <= 0) return "Medium";
        if (c500 <= 0) return "High";
        return "Premium";
    }
}
