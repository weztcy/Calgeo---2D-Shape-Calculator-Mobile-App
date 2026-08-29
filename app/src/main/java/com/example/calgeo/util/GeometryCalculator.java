package com.example.calgeo.util;

import com.example.calgeo.model.CalculationResult;
import com.example.calgeo.model.ShapeType;

import java.util.Map;

public final class GeometryCalculator {

    private GeometryCalculator() {
        // Prevent instantiation
    }

    /**
     * Calculates the area and perimeter of the selected 2D shape.
     *
     * @param shapeType selected shape type
     * @param values    input values mapped by their field keys
     * @return calculation result containing area and perimeter
     */
    public static CalculationResult calculate(
            ShapeType shapeType,
            Map<String, Double> values
    ) {

        if (shapeType == null) {
            throw new IllegalArgumentException("Shape type cannot be null.");
        }

        if (values == null) {
            throw new IllegalArgumentException("Input values cannot be null.");
        }

        double area;
        double perimeter;

        switch (shapeType) {

            case SQUARE: {
                double side = getValue(values, "side");

                area = side * side;
                perimeter = 4 * side;

                break;
            }

            case RECTANGLE: {
                double length = getValue(values, "length");
                double width = getValue(values, "width");

                area = length * width;
                perimeter = 2 * (length + width);

                break;
            }

            case TRIANGLE: {
                double base = getValue(values, "base");
                double height = getValue(values, "height");
                double sideB = getValue(values, "side_b");
                double sideC = getValue(values, "side_c");

                area = 0.5 * base * height;
                perimeter = base + sideB + sideC;

                break;
            }

            case CIRCLE: {
                double radius = getValue(values, "radius");

                area = Math.PI * radius * radius;
                perimeter = 2 * Math.PI * radius;

                break;
            }

            case PARALLELOGRAM: {
                double base = getValue(values, "base");
                double side = getValue(values, "side");
                double height = getValue(values, "height");

                area = base * height;
                perimeter = 2 * (base + side);

                break;
            }

            case TRAPEZOID: {
                double baseA = getValue(values, "base_a");
                double baseB = getValue(values, "base_b");
                double sideC = getValue(values, "side_c");
                double sideD = getValue(values, "side_d");
                double height = getValue(values, "height");

                area = 0.5 * (baseA + baseB) * height;
                perimeter = baseA + baseB + sideC + sideD;

                break;
            }

            case RHOMBUS: {
                double diagonal1 = getValue(values, "diagonal_1");
                double diagonal2 = getValue(values, "diagonal_2");
                double side = getValue(values, "side");

                area = 0.5 * diagonal1 * diagonal2;
                perimeter = 4 * side;

                break;
            }

            case KITE: {
                double diagonal1 = getValue(values, "diagonal_1");
                double diagonal2 = getValue(values, "diagonal_2");
                double sideA = getValue(values, "side_a");
                double sideB = getValue(values, "side_b");

                area = 0.5 * diagonal1 * diagonal2;
                perimeter = 2 * (sideA + sideB);

                break;
            }

            default:
                throw new IllegalArgumentException(
                        "Unsupported shape type: " + shapeType
                );
        }

        return new CalculationResult(area, perimeter);
    }

    /**
     * Gets a numeric value from the map using the specified key.
     */
    private static double getValue(
            Map<String, Double> values,
            String key
    ) {

        Double value = values.get(key);

        if (value == null) {
            throw new IllegalArgumentException(
                    "Missing input value for: " + key
            );
        }

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException(
                    "Invalid input value for: " + key
            );
        }

        return value;
    }
}