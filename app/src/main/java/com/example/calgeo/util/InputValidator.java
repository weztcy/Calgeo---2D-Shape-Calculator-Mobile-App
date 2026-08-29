package com.example.calgeo.util;

import androidx.annotation.StringRes;

import com.example.calgeo.R;
import com.example.calgeo.model.ShapeType;

import java.util.Map;

public final class InputValidator {

    public static final int VALID = 0;

    private InputValidator() {
        // Prevent instantiation
    }

    /**
     * Validates a raw input from TextInputEditText.
     *
     * @param input input text entered by the user
     * @return 0 when valid, otherwise a string resource ID containing
     * the validation error message
     */
    @StringRes
    public static int validateInput(String input) {

        // Check empty input
        if (input == null || input.trim().isEmpty()) {
            return R.string.error_required;
        }

        double value;

        try {
            value = Double.parseDouble(input.trim());
        } catch (NumberFormatException e) {
            return R.string.error_invalid_number;
        }

        // Reject NaN and infinite values
        if (!Double.isFinite(value)) {
            return R.string.error_invalid_number;
        }

        // Geometric measurements must be positive
        if (value <= 0) {
            return R.string.error_positive_number;
        }

        return VALID;
    }

    /**
     * Validates geometric rules that apply to a selected shape.
     *
     * Currently, additional geometric validation is required
     * for triangles.
     *
     * @param shapeType selected shape
     * @param values    validated numeric input values
     * @return 0 when valid, otherwise a string resource ID
     */
    @StringRes
    public static int validateShape(
            ShapeType shapeType,
            Map<String, Double> values
    ) {

        if (shapeType == null || values == null) {
            return R.string.error_calculation_failed;
        }

        if (shapeType == ShapeType.TRIANGLE) {
            return validateTriangle(values);
        }

        return VALID;
    }

    /**
     * Checks the triangle inequality rule:
     *
     * a + b > c
     * a + c > b
     * b + c > a
     */
    @StringRes
    private static int validateTriangle(Map<String, Double> values) {

        Double base = values.get("base");
        Double sideB = values.get("side_b");
        Double sideC = values.get("side_c");

        if (base == null || sideB == null || sideC == null) {
            return R.string.error_calculation_failed;
        }

        if (!isPositiveFinite(base)
                || !isPositiveFinite(sideB)
                || !isPositiveFinite(sideC)) {

            return R.string.error_positive_number;
        }

        boolean validTriangle =
                base + sideB > sideC
                        && base + sideC > sideB
                        && sideB + sideC > base;

        if (!validTriangle) {
            return R.string.error_invalid_triangle;
        }

        return VALID;
    }

    /**
     * Converts validated input text to double.
     *
     * Call validateInput() before using this method.
     */
    public static double parseValue(String input) {

        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null.");
        }

        return Double.parseDouble(input.trim());
    }

    /**
     * Checks whether a numeric value is finite and greater than zero.
     */
    private static boolean isPositiveFinite(double value) {
        return Double.isFinite(value) && value > 0;
    }
}