package org.howard.edu.lsp.midterm.question2;
    
public class Main {
    /*
    This program uses method overloading: multiple methods named 'area' have different parameter lists
    (double), (double,double), (int,int), and (int). The compiler chooses the correct overload based on
    the arguments at each call site. Overloading keeps one clean API. Using many different names 
    (circleArea, rectangleArea, etc.) would bloat the interface.
    */
    public static void main(String[] args) {
        double circle = AreaCalculator.area(3.0);
        double rectangle = AreaCalculator.area(5.0, 2.0);
        double triangle = AreaCalculator.area(10, 6);
        double square = AreaCalculator.area(4);

        System.out.println("Circle radius 3.0 -> area = " + circle);
        System.out.println("Rectangle 5.0 x 2.0 -> area = " + rectangle);
        System.out.println("Triangle base 10, height 6 -> area = " + triangle);
        System.out.println("Square side 4 -> area = " + square);

        // Triggers IllegalArgumentException and prints an error message to System.out
        try {
            AreaCalculator.area(-1.0); // invalid radius
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

