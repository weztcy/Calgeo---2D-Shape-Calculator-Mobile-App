package com.example.calgeo.model;

import androidx.annotation.NonNull;

public final class InputField {

    @NonNull
    private final String key;

    @NonNull
    private final String label;

    @NonNull
    private final String hint;

    public InputField(
            @NonNull String key,
            @NonNull String label,
            @NonNull String hint
    ) {
        this.key = key;
        this.label = label;
        this.hint = hint;
    }

    @NonNull
    public String getKey() {
        return key;
    }

    @NonNull
    public String getLabel() {
        return label;
    }

    @NonNull
    public String getHint() {
        return hint;
    }
}