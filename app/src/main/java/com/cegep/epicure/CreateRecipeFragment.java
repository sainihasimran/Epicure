package com.cegep.epicure;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.cegep.epicure.model.Category;
import com.google.android.material.chip.Chip;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import java.util.ArrayList;
import java.util.List;

public class CreateRecipeFragment extends Fragment {

    private static final String[] REQUIRED_PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private static final int PERMISSION_REQUEST_CODE = 123;

    private static final int CHOOSE_IMAGE_REQUEST_CODE = 485;

    public static CreateRecipeFragment newInstance() {
        return new CreateRecipeFragment();
    }

    private Chip addImageChip;

    private ImageView recipeImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_recipe, container, false);
        addImageChip = view.findViewById(R.id.add_image);
        recipeImageView = view.findViewById(R.id.recipe_image);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.add_image).setOnClickListener(v -> {
            String permission = REQUIRED_PERMISSIONS[0];
            if (ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED) {
                pickImage();
            } else {
                requestPermissions(REQUIRED_PERMISSIONS, PERMISSION_REQUEST_CODE);
            }
        });

        setupCategories(view);
    }

    private void setupCategories(View view) {
        final List<String> categories = new ArrayList<>(Category.values().length);
        for (Category category : Category.values()) {
            categories.add(category.toString().charAt(0) + category.toString().substring(1).toLowerCase().replace("_", " "));
        }

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, categories);
        AutoCompleteTextView recipeCategoryTextView = view.findViewById(R.id.recipe_category);
        recipeCategoryTextView.setAdapter(categoryAdapter);

        recipeCategoryTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: 12/01/21 replace this with actual implementation
                Toast.makeText(requireContext(), "Category selected: " + categories.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickImage();
        } else {
            Toast.makeText(requireContext(), R.string.storage_permission_declined, Toast.LENGTH_SHORT).show();
        }
    }

    private void pickImage() {
        Matisse.from(this)
                .choose(MimeType.ofImage())
                .maxSelectable(1)
                .forResult(CHOOSE_IMAGE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == CHOOSE_IMAGE_REQUEST_CODE && data != null) {
            List<Uri> selectionData = Matisse.obtainResult(data);
            if (selectionData == null || selectionData.isEmpty()) {
                Toast.makeText(requireContext(), R.string.choose_image_error, Toast.LENGTH_SHORT).show();
            } else {
                recipeImageView.setImageURI(selectionData.get(0));
                addImageChip.setVisibility(View.GONE);
            }
        } else {
            Toast.makeText(requireContext(), R.string.choose_image_error, Toast.LENGTH_SHORT).show();
        }
    }
}
