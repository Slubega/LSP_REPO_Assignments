"""# Reflection: Assignment 2 vs Assignment 3

## Differences in Design
Assignment 2 was written as a **single monolithic class** (`ETLPipeline`) that performed extraction, transformation, and loading all in one place. While it produced the correct outputs, the code was tightly coupled, harder to maintain, and less extensible.

Assignment 3, in contrast, decomposes the ETL process into **separate classes with clear responsibilities**:
- `CsvExtractor` – handles reading and validating CSV input.
- `ProductTransformer` – applies transformation rules (uppercasing names, applying discounts, recategorization, and assigning price ranges).
- `CsvLoader` – writes the transformed results to the output CSV.
- `Product` – models the data flowing through the pipeline, encapsulating fields like `id`, `name`, `price`, `category`, and `priceRange`.
- `ETLPipelineApp` – acts as the orchestrator that coordinates extractor, transformer, and loader.

This design makes the system **modular** and easier to reason about. Each class now has a single responsibility, adhering to the **Single Responsibility Principle (SRP)**.

---

## How Assignment 3 Is More Object-Oriented
Assignment 3 is more object-oriented because it breaks the ETL process into collaborating objects. Instead of one class doing everything, responsibility is distributed: extractor objects produce `Product` instances, transformer objects modify them, and loader objects persist them. This makes the program more aligned with real-world modeling, where distinct roles handle different stages of a pipeline.

Additionally, by separating concerns, Assignment 3 allows for **reuse and extension**. For example, if a new transformation rule is added, it belongs in `ProductTransformer` and doesn’t affect the extractor or loader.

---

## Object-Oriented Concepts Used
- **Object**: The `Product` class encapsulates both data and behavior, representing a single unit of information in the pipeline.
- **Class**: Each stage of the ETL process (`CsvExtractor`, `ProductTransformer`, `CsvLoader`) is encapsulated as its own class, following modular design.
- **Encapsulation**: Fields in `Product` are private, accessed via getters and setters, ensuring controlled modification.
- **Inheritance**: Not explicitly required for this project, but the design could be extended with specialized loaders or transformers through subclassing.
- **Polymorphism**: While not implemented here, the class structure supports it — for instance, multiple transformer implementations could be swapped in without changing the pipeline orchestration.

---

## Testing and Verification
To confirm Assignment 3 produces the same results as Assignment 2, I ran both programs against the same input file (`data/products.csv`). In both cases, the pipeline produced the required output file (`data/transformed_products.csv`) with identical transformations:
- Names converted to uppercase.
- 10% discount applied to Electronics.
- Electronics over $500 recategorized as Premium Electronics.
- Price ranges correctly assigned (`Low`, `Medium`, `High`, `Premium`).

Row counts in the summary (rows read, transformed, skipped) also matched across both versions, confirming functional equivalence.

---

## Conclusion
Assignment 2 achieved correctness but lacked modularity. Assignment 3 applies **object-oriented decomposition**, resulting in cleaner, more maintainable, and extensible code. By encapsulating logic into separate classes and introducing a domain model (`Product`), Assignment 3 demonstrates stronger alignment with OO design principles while still preserving the correctness of the ETL pipeline.
"""
