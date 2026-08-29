package com.example.calgeo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.calgeo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         * Gunakan edge-to-edge secara konsisten.
         *
         * Background aplikasi tetap dapat menggambar sampai
         * tepi layar, tetapi konten penting akan diberi inset
         * agar tidak tertutup status bar/navigation bar.
         */
        WindowCompat.setDecorFitsSystemWindows(
                getWindow(),
                false
        );

        binding = ActivityMainBinding.inflate(
                getLayoutInflater()
        );

        setContentView(binding.getRoot());

        applySystemBarInsets();
    }

    /**
     * Memberikan safe padding secara otomatis berdasarkan:
     *
     * - Status bar
     * - Navigation bar
     * - Gesture navigation area
     * - Display cutout / notch
     *
     * Berlaku global untuk seluruh Fragment.
     */
    private void applySystemBarInsets() {

        final int originalPaddingLeft =
                binding.getRoot().getPaddingLeft();

        final int originalPaddingTop =
                binding.getRoot().getPaddingTop();

        final int originalPaddingRight =
                binding.getRoot().getPaddingRight();

        final int originalPaddingBottom =
                binding.getRoot().getPaddingBottom();

        ViewCompat.setOnApplyWindowInsetsListener(
                binding.getRoot(),
                (view, windowInsets) -> {

                    Insets safeInsets =
                            windowInsets.getInsets(
                                    WindowInsetsCompat.Type.systemBars()
                                            | WindowInsetsCompat.Type.displayCutout()
                            );

                    view.setPadding(
                            originalPaddingLeft + safeInsets.left,
                            originalPaddingTop + safeInsets.top,
                            originalPaddingRight + safeInsets.right,
                            originalPaddingBottom + safeInsets.bottom
                    );

                    /*
                     * Jangan consume insets.
                     * Fragment tetap bisa menggunakan inset
                     * sendiri nanti jika diperlukan.
                     */
                    return windowInsets;
                }
        );

        /*
         * Meminta Android mengirim WindowInsets
         * setelah View sudah terpasang.
         */
        ViewCompat.requestApplyInsets(
                binding.getRoot()
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}