package com.example.calgeo.data;

import com.example.calgeo.R;
import com.example.calgeo.model.InputField;
import com.example.calgeo.model.Shape;
import com.example.calgeo.model.ShapeType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ShapeRepository {

    private ShapeRepository() {
        // Prevent instantiation
    }

    /**
     * Returns all supported 2D shapes.
     */
    public static List<Shape> getShapes() {

        List<Shape> shapes = new ArrayList<>();

        shapes.add(new Shape(
                ShapeType.SQUARE,
                "Square",
                R.drawable.ic_square
        ));

        shapes.add(new Shape(
                ShapeType.RECTANGLE,
                "Rectangle",
                R.drawable.ic_rectangle
        ));

        shapes.add(new Shape(
                ShapeType.TRIANGLE,
                "Triangle",
                R.drawable.ic_triangle
        ));

        shapes.add(new Shape(
                ShapeType.CIRCLE,
                "Circle",
                R.drawable.ic_circle
        ));

        shapes.add(new Shape(
                ShapeType.PARALLELOGRAM,
                "Parallelogram",
                R.drawable.ic_parallelogram
        ));

        shapes.add(new Shape(
                ShapeType.TRAPEZOID,
                "Trapezoid",
                R.drawable.ic_trapezoid
        ));

        shapes.add(new Shape(
                ShapeType.RHOMBUS,
                "Rhombus",
                R.drawable.ic_rhombus
        ));

        shapes.add(new Shape(
                ShapeType.KITE,
                "Kite",
                R.drawable.ic_kite
        ));

        return Collections.unmodifiableList(shapes);
    }

    /**
     * Returns dynamic input fields based on the selected shape.
     */
    public static List<InputField> getInputFields(ShapeType shapeType) {

        if (shapeType == null) {
            return Collections.emptyList();
        }

        List<InputField> fields = new ArrayList<>();

        switch (shapeType) {

            case SQUARE:
                fields.add(new InputField(
                        "side",
                        "Side",
                        "Enter side length"
                ));
                break;

            case RECTANGLE:
                fields.add(new InputField(
                        "length",
                        "Length",
                        "Enter length"
                ));

                fields.add(new InputField(
                        "width",
                        "Width",
                        "Enter width"
                ));
                break;

            case TRIANGLE:
                fields.add(new InputField(
                        "base",
                        "Base",
                        "Enter base length"
                ));

                fields.add(new InputField(
                        "height",
                        "Height",
                        "Enter height"
                ));

                fields.add(new InputField(
                        "side_b",
                        "Side B",
                        "Enter side B"
                ));

                fields.add(new InputField(
                        "side_c",
                        "Side C",
                        "Enter side C"
                ));
                break;

            case CIRCLE:
                fields.add(new InputField(
                        "radius",
                        "Radius",
                        "Enter radius"
                ));
                break;

            case PARALLELOGRAM:
                fields.add(new InputField(
                        "base",
                        "Base",
                        "Enter base length"
                ));

                fields.add(new InputField(
                        "side",
                        "Side",
                        "Enter side length"
                ));

                fields.add(new InputField(
                        "height",
                        "Height",
                        "Enter height"
                ));
                break;

            case TRAPEZOID:
                fields.add(new InputField(
                        "base_a",
                        "Base A",
                        "Enter base A"
                ));

                fields.add(new InputField(
                        "base_b",
                        "Base B",
                        "Enter base B"
                ));

                fields.add(new InputField(
                        "side_c",
                        "Side C",
                        "Enter side C"
                ));

                fields.add(new InputField(
                        "side_d",
                        "Side D",
                        "Enter side D"
                ));

                fields.add(new InputField(
                        "height",
                        "Height",
                        "Enter height"
                ));
                break;

            case RHOMBUS:
                fields.add(new InputField(
                        "diagonal_1",
                        "Diagonal 1",
                        "Enter diagonal 1"
                ));

                fields.add(new InputField(
                        "diagonal_2",
                        "Diagonal 2",
                        "Enter diagonal 2"
                ));

                fields.add(new InputField(
                        "side",
                        "Side",
                        "Enter side length"
                ));
                break;

            case KITE:
                fields.add(new InputField(
                        "diagonal_1",
                        "Diagonal 1",
                        "Enter diagonal 1"
                ));

                fields.add(new InputField(
                        "diagonal_2",
                        "Diagonal 2",
                        "Enter diagonal 2"
                ));

                fields.add(new InputField(
                        "side_a",
                        "Side A",
                        "Enter side A"
                ));

                fields.add(new InputField(
                        "side_b",
                        "Side B",
                        "Enter side B"
                ));
                break;
        }

        return Collections.unmodifiableList(fields);
    }

    /**
     * Returns a shape object based on its ShapeType.
     */
    public static Shape getShapeByType(ShapeType shapeType) {

        if (shapeType == null) {
            return null;
        }

        for (Shape shape : getShapes()) {
            if (shape.getType() == shapeType) {
                return shape;
            }
        }

        return null;
    }
}