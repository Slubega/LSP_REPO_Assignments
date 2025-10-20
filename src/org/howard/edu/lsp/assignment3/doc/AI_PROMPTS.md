# AI_PROMPTS_A3
### 1. Project Structure Verification
**Prompt:**  
*"Can you confirm that my `assignment3` package and subfolders align with the expected Java package conventions and submission requirements?"*  

**AI Response (excerpt):**  
"Yes — your `.java` files should all be under `src/org/howard/edu/lsp/assignment3/`. The `doc/` folder is correct for markdown files like REFLECTION and AI_PROMPTS. This matches the rubric and ensures a clean package declaration (`org.howard.edu.lsp.assignment3`)."

---

### 2. Exception Handling Best Practices
**Prompt:**  
*"Is my current exception handling approach in `CsvExtractor` and `ETLPipelineApp` too broad, or should I be catching more specific exceptions?"*  

**AI Response (excerpt):**  
"Right now you are catching `Exception`, which is too general. Replace it with `IOException` for file handling and `NumberFormatException` or `ArrayIndexOutOfBoundsException` for parsing. This makes debugging easier and aligns with standard Java practices."

---

### 3. Javadoc Style Consistency
**Prompt:**  
*"Can you help refine my Javadocs so they follow the lecture and Atlassian standards (avoiding HTML tags and ensuring accuracy)?"*  

**AI Response (excerpt):**  
"Avoid `<li>` or `<ol>` — stick to plain text bullet points. Ensure every public method has accurate `@param` and `@return` tags. For example, your `transform` method should note that it applies all business rules in place and returns the number of transformed rows."

---

### 4. Reflection Drafting
**Prompt:**  
*"I need to compare my Assignment 2 and 3 implementations — can you suggest points about OO design improvements I should emphasize?"*  

**AI Response (excerpt):**  
"Assignment 2 is procedural, with logic in one class. Assignment 3 decomposes into multiple classes (`CsvExtractor`, `ProductTransformer`, `CsvLoader`), which shows better encapsulation and modularity. Mention that you tested by running the pipeline and confirming identical outputs."

---

### 5. Testing and Validation
**Prompt:**  
*"How can I confirm that Assignment 3 produces the same results as Assignment 2 to satisfy the rubric?"*  

**AI Response (excerpt):**  
"Run both pipelines with the same `products.csv` input and compare `transformed_products.csv`. If outputs match row for row, your refactor preserved correctness. You should also count rows read, transformed, and skipped — these metrics act as a validation checksum."

---

### 6. OO Design Heuristics
**Prompt:**  
*"Based on OO heuristics, does my current class decomposition make sense, or should I merge/split responsibilities?"*  

**AI Response (excerpt):**  
"Your separation is good — `CsvExtractor` handles input parsing, `ProductTransformer` applies business rules, `CsvLoader` manages output, and `ETLPipelineApp` orchestrates. Each class has a single clear responsibility, which matches OO heuristics like cohesion and separation of concerns."

---