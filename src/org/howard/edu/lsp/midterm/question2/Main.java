package org.howard.edu.lsp.midterm.question2;
    
public class Main {
    /**
     * Overloading keeps one concept—"compute area"—under a single method name.
     * The compiler picks the right overload by parameters, which makes the API
     * easier to discover and use than many differently named methods.
     */
    public static void main(String[] args) {
        double circle = AreaCalculator.area(3.0);
        double rectangle = AreaCalculator.area(5.0, 2.0);
        double triangle = AreaCalculator.area(10, 6);
        double square = AreaCalculator.area(4);

        System.out.println("Circle radius 3.0 \u2192 area = " + circle);
        System.out.println("Rectangle 5.0 x 2.0 \u2192 area = " + rectangle);
        System.out.println("Triangle base 10, height 6 \u2192 area = " + triangle);
        System.out.println("Square side 4 \u2192 area = " + square);

        // Exception demo (any one method is fine)
        try {
            AreaCalculator.area(-1.0); // invalid radius -> throws
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

