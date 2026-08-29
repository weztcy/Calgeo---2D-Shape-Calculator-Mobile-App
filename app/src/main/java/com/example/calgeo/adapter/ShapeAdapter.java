package com.example.calgeo.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calgeo.databinding.ItemShapeBinding;
import com.example.calgeo.model.Shape;

import java.util.List;

public class ShapeAdapter
        extends RecyclerView.Adapter<ShapeAdapter.ShapeViewHolder> {

    private final List<Shape> shapeList;
    private final OnShapeClickListener listener;

    public ShapeAdapter(
            @NonNull List<Shape> shapeList,
            @NonNull OnShapeClickListener listener
    ) {
        this.shapeList = shapeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ShapeViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        ItemShapeBinding binding =
                ItemShapeBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                );

        return new ShapeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ShapeViewHolder holder,
            int position
    ) {

        Shape shape = shapeList.get(position);

        holder.bind(shape);
    }

    @Override
    public int getItemCount() {
        return shapeList.size();
    }

    /**
     * Callback ketika pengguna memilih shape.
     */
    public interface OnShapeClickListener {

        void onShapeClick(@NonNull Shape shape);
    }

    /**
     * ViewHolder untuk setiap card shape.
     */
    class ShapeViewHolder
            extends RecyclerView.ViewHolder {

        private final ItemShapeBinding binding;

        ShapeViewHolder(
                @NonNull ItemShapeBinding binding
        ) {
            super(binding.getRoot());

            this.binding = binding;
        }

        void bind(@NonNull Shape shape) {

            // =====================================================
            // SHAPE ICON
            // =====================================================

            binding.imageShape.setImageResource(
                    shape.getDrawableResId()
            );


            // =====================================================
            // SHAPE NAME
            // =====================================================

            binding.textShapeName.setText(
                    shape.getName()
            );


            // =====================================================
            // ACCESSIBILITY
            // =====================================================

            String contentDescription =
                    shape.getName() + " shape";

            binding.imageShape.setContentDescription(
                    contentDescription
            );

            binding.cardShape.setContentDescription(
                    contentDescription
            );


            // =====================================================
            // CLICK EVENT
            // =====================================================

            binding.cardShape.setOnClickListener(
                    view -> {

                        int position =
                                getBindingAdapterPosition();

                        if (position
                                != RecyclerView.NO_POSITION) {

                            listener.onShapeClick(
                                    shapeList.get(position)
                            );
                        }
                    }
            );
        }
    }
}