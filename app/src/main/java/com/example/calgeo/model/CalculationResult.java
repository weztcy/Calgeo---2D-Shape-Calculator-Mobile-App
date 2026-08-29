package com.example.calgeo.model;

public final class CalculationResult {

    private final double area;
    private final double perimeter;

    public CalculationResult(double area, double perimeter) {
        this.area = area;
        this.perimeter = perimeter;
    }

    public double getArea() {
        return area;
    }

    public double getPerimeter() {
        return perimeter;
    }
}