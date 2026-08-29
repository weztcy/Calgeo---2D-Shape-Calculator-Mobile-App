package com.example.calgeo.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.calgeo.R;
import com.example.calgeo.adapter.ShapeAdapter;
import com.example.calgeo.data.ShapeRepository;
import com.example.calgeo.databinding.FragmentHomeBinding;
import com.example.calgeo.model.Shape;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentHomeBinding.inflate(
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

        setupShapeRecyclerView();
        setupAboutButton();
    }

    /**
     * Menampilkan seluruh bangun datar dalam grid 2 kolom.
     */
    private void setupShapeRecyclerView() {

        List<Shape> shapes =
                ShapeRepository.getShapes();

        ShapeAdapter shapeAdapter =
                new ShapeAdapter(
                        shapes,
                        this::openCalculator
                );

        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(
                        requireContext(),
                        2
                );

        binding.recyclerShapes.setLayoutManager(
                gridLayoutManager
        );

        binding.recyclerShapes.setAdapter(
                shapeAdapter
        );

        /*
         * RecyclerView berada di dalam NestedScrollView.
         * Karena itu nested scrolling dimatikan agar scrolling
         * halaman terasa lebih halus.
         */
        binding.recyclerShapes.setNestedScrollingEnabled(
                false
        );

        /*
         * Ukuran jumlah item tetap hanya 8 shape.
         */
        binding.recyclerShapes.setHasFixedSize(
                false
        );

        /*
         * Menghilangkan animasi perubahan sederhana yang kadang
         * membuat card terlihat berkedip saat RecyclerView refresh.
         */
        if (binding.recyclerShapes.getItemAnimator() != null) {
            binding.recyclerShapes
                    .getItemAnimator()
                    .setChangeDuration(0);
        }
    }

    /**
     * Membuka halaman Calculator sesuai bangun datar
     * yang dipilih pengguna.
     */
    private void openCalculator(
            @NonNull Shape shape
    ) {

        Bundle bundle = new Bundle();

        bundle.putString(
                "shapeType",
                shape.getType().name()
        );

        NavHostFragment
                .findNavController(this)
                .navigate(
                        R.id.action_homeFragment_to_calculatorFragment,
                        bundle
                );
    }

    /**
     * Membuka halaman About.
     */
    private void setupAboutButton() {

        binding.buttonAbout.setOnClickListener(
                view ->
                        NavHostFragment
                                .findNavController(this)
                                .navigate(
                                        R.id.action_homeFragment_to_aboutFragment
                                )
        );
    }

    @Override
    public void onDestroyView() {

        /*
         * Lepaskan adapter agar RecyclerView tidak menahan
         * reference ke View Fragment.
         */
        binding.recyclerShapes.setAdapter(null);

        binding = null;

        super.onDestroyView();
    }
}