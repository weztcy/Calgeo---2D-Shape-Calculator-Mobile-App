package com.example.calgeo.ui.calculator;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.calgeo.R;
import com.example.calgeo.data.ShapeRepository;
import com.example.calgeo.databinding.FragmentCalculatorBinding;
import com.example.calgeo.databinding.ItemInputFieldBinding;
import com.example.calgeo.model.CalculationResult;
import com.example.calgeo.model.InputField;
import com.example.calgeo.model.Shape;
import com.example.calgeo.model.ShapeType;
import com.example.calgeo.util.GeometryCalculator;
import com.example.calgeo.util.InputValidator;
import com.google.android.material.snackbar.Snackbar;

import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CalculatorFragment extends Fragment {

    private FragmentCalculatorBinding binding;

    private ShapeType shapeType;

    private final Map<String, ItemInputFieldBinding> inputBindings =
            new LinkedHashMap<>();


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentCalculatorBinding.inflate(
                inflater,
                container,
                false
        );

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);

        if (!readShapeTypeArgument()) {
            return;
        }

        setupToolbar();
        setupShapeInformation();
        setupDynamicInputs();
        setupCalculateButton();
        setupResetButton();
        setupClearFocusBehavior();
    }


    /**
     * Membaca ShapeType yang dikirim dari HomeFragment.
     */
    private boolean readShapeTypeArgument() {

        Bundle arguments = getArguments();

        if (arguments == null) {
            handleInvalidShape();
            return false;
        }

        String shapeTypeValue =
                arguments.getString("shapeType");

        if (shapeTypeValue == null
                || shapeTypeValue.trim().isEmpty()) {

            handleInvalidShape();
            return false;
        }

        try {

            shapeType =
                    ShapeType.valueOf(shapeTypeValue);

            return true;

        } catch (IllegalArgumentException exception) {

            handleInvalidShape();
            return false;
        }
    }


    /**
     * Menangani argument Shape yang tidak valid.
     */
    private void handleInvalidShape() {

        if (binding == null) {
            return;
        }

        Snackbar.make(
                binding.getRoot(),
                R.string.error_calculation_failed,
                Snackbar.LENGTH_SHORT
        ).show();

        binding.getRoot().post(() -> {

            if (isAdded()) {

                NavHostFragment
                        .findNavController(this)
                        .navigateUp();
            }
        });
    }


    /**
     * Tombol kembali.
     */
    private void setupToolbar() {

        binding.toolbar.setNavigationOnClickListener(view ->
                NavHostFragment
                        .findNavController(this)
                        .navigateUp()
        );
    }


    /**
     * Menampilkan informasi Shape.
     */
    private void setupShapeInformation() {

        Shape shape =
                ShapeRepository.getShapeByType(
                        shapeType
                );

        if (shape == null) {
            handleInvalidShape();
            return;
        }

        // Nama Shape di Toolbar
        binding.toolbar.setTitle(
                shape.getName()
        );

        // Icon Shape
        binding.imageShape.setImageResource(
                shape.getDrawableResId()
        );

        binding.imageShape.setContentDescription(
                shape.getName()
        );

        // Deskripsi
        binding.textShapeDescription.setText(
                getShapeDescription(shapeType)
        );

        // Circle memakai istilah Circumference
        if (shapeType == ShapeType.CIRCLE) {

            binding.textPerimeterLabel.setText(
                    R.string.circumference
            );

        } else {

            binding.textPerimeterLabel.setText(
                    R.string.perimeter
            );
        }
    }


    /**
     * Membuat input secara dinamis.
     */
    private void setupDynamicInputs() {

        binding.dynamicInputContainer.removeAllViews();

        inputBindings.clear();

        List<InputField> inputFields =
                ShapeRepository.getInputFields(
                        shapeType
                );

        LayoutInflater inflater =
                LayoutInflater.from(
                        requireContext()
                );


        for (int i = 0;
             i < inputFields.size();
             i++) {

            InputField inputField =
                    inputFields.get(i);

            ItemInputFieldBinding fieldBinding =
                    ItemInputFieldBinding.inflate(
                            inflater,
                            binding.dynamicInputContainer,
                            false
                    );


            // Label
            fieldBinding.textInputLayout.setHint(
                    inputField.getLabel()
            );


            // Placeholder
            fieldBinding.textInputLayout.setPlaceholderText(
                    inputField.getHint()
            );


            // Accessibility
            fieldBinding.editTextInput.setContentDescription(
                    inputField.getLabel()
            );


            boolean isLastField =
                    i == inputFields.size() - 1;


            /*
             * NEXT untuk field biasa.
             * DONE untuk field terakhir.
             */
            if (isLastField) {

                fieldBinding.editTextInput.setImeOptions(
                        EditorInfo.IME_ACTION_DONE
                );

            } else {

                fieldBinding.editTextInput.setImeOptions(
                        EditorInfo.IME_ACTION_NEXT
                );
            }


            /*
             * Ketika tombol DONE pada keyboard ditekan,
             * keyboard ditutup dan field tidak lagi aktif.
             */
            if (isLastField) {

                fieldBinding.editTextInput
                        .setOnEditorActionListener(
                                (textView, actionId, event) -> {

                                    if (actionId
                                            == EditorInfo.IME_ACTION_DONE) {

                                        clearInputFocus();

                                        return true;
                                    }

                                    return false;
                                }
                        );
            }


            /*
             * Hilangkan error ketika pengguna kembali
             * mengaktifkan field.
             */
            fieldBinding.editTextInput
                    .setOnFocusChangeListener(
                            (inputView, hasFocus) -> {

                                if (hasFocus) {

                                    fieldBinding
                                            .textInputLayout
                                            .setError(null);
                                }
                            }
                    );


            binding.dynamicInputContainer.addView(
                    fieldBinding.getRoot()
            );


            inputBindings.put(
                    inputField.getKey(),
                    fieldBinding
            );
        }
    }


    /**
     * Klik area kosong untuk melepas focus dan
     * menutup keyboard.
     */
    private void setupClearFocusBehavior() {

        binding.getRoot().setOnClickListener(
                view -> clearInputFocus()
        );

        binding.contentContainer.setOnClickListener(
                view -> clearInputFocus()
        );
    }


    /**
     * Menghilangkan focus dari input aktif
     * dan menutup keyboard.
     */
    private void clearInputFocus() {

        if (binding == null) {
            return;
        }

        View focusedView =
                requireActivity().getCurrentFocus();


        if (focusedView != null) {

            focusedView.clearFocus();

            InputMethodManager inputMethodManager =
                    (InputMethodManager)
                            requireContext()
                                    .getSystemService(
                                            Context.INPUT_METHOD_SERVICE
                                    );


            if (inputMethodManager != null) {

                inputMethodManager.hideSoftInputFromWindow(
                        focusedView.getWindowToken(),
                        0
                );
            }
        }


        /*
         * Pindahkan focus ke root layout.
         * Karena root sudah focusableInTouchMode,
         * TextInputEditText akan kembali ke state normal.
         */
        binding.getRoot().requestFocus();
    }


    /**
     * Tombol Calculate.
     */
    private void setupCalculateButton() {

        binding.buttonCalculate.setOnClickListener(
                view -> {

                    /*
                     * Tutup keyboard sebelum menampilkan hasil.
                     */
                    clearInputFocus();

                    calculate();
                }
        );
    }


    /**
     * Tombol Reset.
     */
    private void setupResetButton() {

        binding.buttonReset.setOnClickListener(
                view -> {

                    clearInputFocus();

                    resetCalculator();
                }
        );
    }


    /**
     * Validasi dan perhitungan.
     */
    private void calculate() {

        binding.resultContainer.setVisibility(
                View.GONE
        );

        clearAllErrors();


        Map<String, Double> values =
                new LinkedHashMap<>();


        boolean allFieldsValid = true;

        ItemInputFieldBinding firstInvalidBinding =
                null;


        for (Map.Entry<String, ItemInputFieldBinding> entry
                : inputBindings.entrySet()) {

            String key =
                    entry.getKey();

            ItemInputFieldBinding fieldBinding =
                    entry.getValue();


            String input = "";

            if (fieldBinding
                    .editTextInput
                    .getText() != null) {

                input =
                        fieldBinding
                                .editTextInput
                                .getText()
                                .toString()
                                .trim();
            }


            int validationResult =
                    InputValidator.validateInput(
                            input
                    );


            if (validationResult
                    != InputValidator.VALID) {

                fieldBinding
                        .textInputLayout
                        .setError(
                                getString(
                                        validationResult
                                )
                        );


                allFieldsValid = false;


                if (firstInvalidBinding == null) {

                    firstInvalidBinding =
                            fieldBinding;
                }


                continue;
            }


            try {

                double value =
                        InputValidator.parseValue(
                                input
                        );


                values.put(
                        key,
                        value
                );


            } catch (NumberFormatException exception) {

                fieldBinding
                        .textInputLayout
                        .setError(
                                getString(
                                        R.string.error_invalid_number
                                )
                        );


                allFieldsValid = false;


                if (firstInvalidBinding == null) {

                    firstInvalidBinding =
                            fieldBinding;
                }
            }
        }


        if (!allFieldsValid) {

            focusFirstInvalidField(
                    firstInvalidBinding
            );

            return;
        }


        /*
         * Validasi rule geometris.
         */
        int shapeValidation =
                InputValidator.validateShape(
                        shapeType,
                        values
                );


        if (shapeValidation
                != InputValidator.VALID) {

            showShapeValidationError(
                    shapeValidation
            );

            return;
        }


        /*
         * Hitung.
         */
        try {

            CalculationResult result =
                    GeometryCalculator.calculate(
                            shapeType,
                            values
                    );


            displayResult(
                    result
            );


        } catch (IllegalArgumentException exception) {

            Snackbar.make(
                    binding.getRoot(),
                    R.string.error_calculation_failed,
                    Snackbar.LENGTH_SHORT
            ).show();
        }
    }


    /**
     * Menampilkan hasil.
     */
    private void displayResult(
            @NonNull CalculationResult result
    ) {

        String formattedArea =
                formatNumber(
                        result.getArea()
                );


        String formattedPerimeter =
                formatNumber(
                        result.getPerimeter()
                );


        binding.textAreaResult.setText(
                getString(
                        R.string.area_result_format,
                        formattedArea
                )
        );


        binding.textPerimeterResult.setText(
                getString(
                        R.string.perimeter_result_format,
                        formattedPerimeter
                )
        );


        if (shapeType == ShapeType.CIRCLE) {

            binding.textPerimeterLabel.setText(
                    R.string.circumference
            );

        } else {

            binding.textPerimeterLabel.setText(
                    R.string.perimeter
            );
        }


        binding.resultContainer.setVisibility(
                View.VISIBLE
        );


        /*
         * Scroll menuju hasil.
         */
        binding.resultContainer.post(() -> {

            if (binding != null) {

                binding.scrollContent.smoothScrollTo(
                        0,
                        binding.resultContainer.getBottom()
                );
            }
        });
    }


    /**
     * Maksimal 2 decimal.
     */
    @NonNull
    private String formatNumber(
            double value
    ) {

        NumberFormat numberFormat =
                NumberFormat.getNumberInstance(
                        Locale.getDefault()
                );


        numberFormat.setMinimumFractionDigits(0);

        numberFormat.setMaximumFractionDigits(2);

        numberFormat.setGroupingUsed(false);


        return numberFormat.format(
                value
        );
    }


    /**
     * Validasi khusus shape.
     */
    private void showShapeValidationError(
            @StringRes int errorResId
    ) {

        if (shapeType == ShapeType.TRIANGLE
                && errorResId
                == R.string.error_invalid_triangle) {

            setFieldError(
                    "base",
                    errorResId
            );

            setFieldError(
                    "side_b",
                    errorResId
            );

            setFieldError(
                    "side_c",
                    errorResId
            );


            ItemInputFieldBinding baseBinding =
                    inputBindings.get(
                            "base"
                    );


            focusFirstInvalidField(
                    baseBinding
            );

            return;
        }


        Snackbar.make(
                binding.getRoot(),
                errorResId,
                Snackbar.LENGTH_SHORT
        ).show();
    }


    /**
     * Memberi error pada input berdasarkan key.
     */
    private void setFieldError(
            @NonNull String key,
            @StringRes int errorResId
    ) {

        ItemInputFieldBinding fieldBinding =
                inputBindings.get(
                        key
                );


        if (fieldBinding != null) {

            fieldBinding
                    .textInputLayout
                    .setError(
                            getString(
                                    errorResId
                            )
                    );
        }
    }


    /**
     * Fokus ke field pertama yang error.
     */
    private void focusFirstInvalidField(
            @Nullable ItemInputFieldBinding fieldBinding
    ) {

        if (fieldBinding == null) {
            return;
        }


        fieldBinding.editTextInput.requestFocus();


        binding.scrollContent.post(() -> {

            if (binding != null) {

                binding.scrollContent.smoothScrollTo(
                        0,
                        fieldBinding
                                .getRoot()
                                .getTop()
                );
            }
        });
    }


    /**
     * Menghapus error.
     */
    private void clearAllErrors() {

        for (ItemInputFieldBinding fieldBinding
                : inputBindings.values()) {

            fieldBinding
                    .textInputLayout
                    .setError(null);
        }
    }


    /**
     * Reset calculator.
     */
    private void resetCalculator() {

        for (ItemInputFieldBinding fieldBinding
                : inputBindings.values()) {

            fieldBinding
                    .editTextInput
                    .setText("");

            fieldBinding
                    .textInputLayout
                    .setError(null);
        }


        binding.textAreaResult.setText(
                R.string.result_placeholder
        );


        binding.textPerimeterResult.setText(
                R.string.result_placeholder
        );


        binding.resultContainer.setVisibility(
                View.GONE
        );


        /*
         * Setelah Reset, jangan langsung fokus ke field pertama.
         * Ini membuat semua field kembali ke state tidak aktif.
         */
        binding.getRoot().requestFocus();


        binding.scrollContent.smoothScrollTo(
                0,
                0
        );
    }


    /**
     * Deskripsi Shape.
     */
    @StringRes
    private int getShapeDescription(
            @NonNull ShapeType type
    ) {

        switch (type) {

            case SQUARE:
                return R.string.square_description;

            case RECTANGLE:
                return R.string.rectangle_description;

            case TRIANGLE:
                return R.string.triangle_description;

            case CIRCLE:
                return R.string.circle_description;

            case PARALLELOGRAM:
                return R.string.parallelogram_description;

            case TRAPEZOID:
                return R.string.trapezoid_description;

            case RHOMBUS:
                return R.string.rhombus_description;

            case KITE:
                return R.string.kite_description;

            default:
                return R.string.measurement_description;
        }
    }


    @Override
    public void onDestroyView() {

        inputBindings.clear();

        binding = null;

        super.onDestroyView();
    }
}