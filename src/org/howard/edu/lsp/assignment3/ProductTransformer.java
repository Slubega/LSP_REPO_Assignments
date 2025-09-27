package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Transform step that applies business rules in a fixed order.
 *
 * Order of rules:
 * 1. Uppercase the product name.
 * 2. Apply a 10% discount if the original category is "Electronics", rounding to two decimals.
 * 3. If final price is greater than 500 and the original category was "Electronics",
 *    recategorize to "Premium Electronics"; otherwise keep the original category.
 * 4. Compute the price range (Low, Medium, High, Premium) from the final price.
 */
public class ProductTransformer {

    /**
     * Applies all transformation rules to the provided list.
     * This method mutates the list in place.
     *
     * @param products the list of products to transform
     * @return the number of rows transformed
     */
    public int transform(final List<Product> products) {
        int transformed = 0;

        for (Product p : products) {
            // 1) Uppercase name
            p.setName(p.getName().toUpperCase());

            // 2) 10% discount for original Electronics; always round to 2 decimals
            BigDecimal price = round2(p.getPrice());
            if (p.getOriginalCategory().equalsIgnoreCase("Electronics")) {
                price = round2(price.multiply(new BigDecimal("0.90")));
            }
            p.setPrice(price);

            // 3) Recategorize expensive Electronics
            if (price.compareTo(new BigDecimal("500.00")) > 0
                    && p.getOriginalCategory().equalsIgnoreCase("Electronics")) {
                p.setCategory("Premium Electronics");
            } else {
                p.setCategory(p.getOriginalCategory());
            }

            // 4) Price range from final price
            p.setPriceRange(computePriceRange(price));
            transformed++;
        }

        return transformed;
    }

    /**
     * Computes a price range label from a final price.
     * Thresholds: <= 10 -> Low, <= 100 -> Medium, <= 500 -> High, > 500 -> Premium.
     *
     * @param price the final product price
     * @return Low, Medium, High, or Premium
     */
    private String computePriceRange(final BigDecimal price) {
        int c10 = price.compareTo(new BigDecimal("10.00"));
        int c100 = price.compareTo(new BigDecimal("100.00"));
        int c500 = price.compareTo(new BigDecimal("500.00"));

        if (c10 <= 0)   return "Low";
        if (c100 <= 0)  return "Medium";
        if (c500 <= 0)  return "High";
        return "Premium";
    }

    /** Rounds a price to two decimals using HALF_UP. */
    private static BigDecimal round2(final BigDecimal x) {
        return x.setScale(2, RoundingMode.HALF_UP);
    }
}
