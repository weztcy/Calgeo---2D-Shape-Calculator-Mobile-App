package com.example.calgeo.model;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

public final class Shape {

    @NonNull
    private final ShapeType type;

    @NonNull
    private final String name;

    @DrawableRes
    private final int drawableResId;

    public Shape(
            @NonNull ShapeType type,
            @NonNull String name,
            @DrawableRes int drawableResId
    ) {
        this.type = type;
        this.name = name;
        this.drawableResId = drawableResId;
    }

    @NonNull
    public ShapeType getType() {
        return type;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @DrawableRes
    public int getDrawableResId() {
        return drawableResId;
    }
}