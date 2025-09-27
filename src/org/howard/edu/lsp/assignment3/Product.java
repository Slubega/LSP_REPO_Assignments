package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Immutable-ish data model for a product flowing through the ETL pipeline.
 * Only transformation steps should mutate fields via setters.
 */
public class Product {
    private final int id;
    private String name;
    private BigDecimal price;          // final price after transforms
    private String category;           // may be recategorized
    private String priceRange;         // derived from final price

    // keep originals for rules that depend on them
    private final String originalCategory;
    private final BigDecimal originalPrice;

    public Product(int id, String name, BigDecimal price, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.originalCategory = category;
        this.originalPrice = price;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public String getCategory() { return category; }
    public String getPriceRange() { return priceRange; }

    public String getOriginalCategory() { return originalCategory; }
    public BigDecimal getOriginalPrice() { return originalPrice; }

    public void setName(String name) { this.name = name; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setCategory(String category) { this.category = category; }
    public void setPriceRange(String priceRange) { this.priceRange = priceRange; }

    /** CSV serialization in required column order (A2 spec). */
    public String toCsvRow() {
        return id + "," + name + "," + price.toPlainString() + "," + category + "," + priceRange;
    }

    @Override public boolean equals(Object o) {
        if (!(o instanceof Product)) return false;
        Product p = (Product) o;
        return id == p.id && Objects.equals(name, p.name) &&
               Objects.equals(price, p.price) && Objects.equals(category, p.category) &&
               Objects.equals(priceRange, p.priceRange);
    }
    @Override public int hashCode() { return Objects.hash(id, name, price, category, priceRange); }
}
