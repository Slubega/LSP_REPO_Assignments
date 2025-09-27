/**
 * Product represents an item flowing through the ETL pipeline.
 * It stores the product ID, name, price, category, and derived price range.
 * Original category and price are also stored for transformation rules.
 *
 * Responsibilities:
 * - Acts as a data model (DTO) for the ETL pipeline.
 * - Provides getters and setters for mutable fields (name, price, category, priceRange).
 * - Serializes itself into a CSV row format.
 */
package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a product flowing through the ETL pipeline.
 * Stores current fields (name, price, category, priceRange) and the original
 * values used by transformation rules.
 */
public class Product {
    private final int id;
    private String name;
    private BigDecimal price;      // final price after transforms
    private String category;       // may be recategorized
    private String priceRange;     // derived from final price

    // originals used by rules
    private final String originalCategory;
    private final BigDecimal originalPrice;

    /**
     * Constructs a product with original attributes.
     *
     * @param id       the product identifier
     * @param name     the original product name
     * @param price    the original product price
     * @param category the original product category
     */
    public Product(final int id, final String name, final BigDecimal price, final String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.originalCategory = category;
        this.originalPrice = price;
    }

    /** @return the product identifier */
    public int getId() { return id; }

    /** @return the (possibly transformed) product name */
    public String getName() { return name; }

    /** @return the (possibly transformed) price */
    public BigDecimal getPrice() { return price; }

    /** @return the (possibly recategorized) category */
    public String getCategory() { return category; }

    /** @return the derived price range label */
    public String getPriceRange() { return priceRange; }

    /** @return the original category prior to transforms */
    public String getOriginalCategory() { return originalCategory; }

    /** @return the original price prior to transforms */
    public BigDecimal getOriginalPrice() { return originalPrice; }

    /**
     * Sets the product name (used by the transform step).
     * @param name uppercase product name
     */
    public void setName(final String name) { this.name = name; }

    /**
     * Sets the product price (used by the transform step).
     * @param price price after discount/rounding
     */
    public void setPrice(final BigDecimal price) { this.price = price; }

    /**
     * Sets the product category (used by the transform step).
     * @param category possibly recategorized value
     */
    public void setCategory(final String category) { this.category = category; }

    /**
     * Sets the derived price range (used by the transform step).
     * @param priceRange Low, Medium, High, or Premium
     */
    public void setPriceRange(final String priceRange) { this.priceRange = priceRange; }

    /**
     * Serializes this product as a CSV row in the required column order.
     *
     * @return CSV row: ProductID,Name,Price,Category,PriceRange
     */
    public String toCsvRow() {
        return id + "," + name + "," + price.toPlainString() + "," + category + "," + priceRange;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Product)) return false;
        Product p = (Product) o;
        return id == p.id
                && Objects.equals(name, p.name)
                && Objects.equals(price, p.price)
                && Objects.equals(category, p.category)
                && Objects.equals(priceRange, p.priceRange);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, category, priceRange);
    }
}
