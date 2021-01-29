package com.cegep.epicure;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cegep.epicure.list.StepsAdapter;
import com.cegep.epicure.list.callback.RemoveItemClickListener;
import com.cegep.epicure.model.Category;
import com.cegep.epicure.model.Ingredient;
import com.cegep.epicure.model.PreparationStep;
import com.cegep.epicure.model.Recipe;
import com.cegep.epicure.network.NetworkInteractor;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Response;

public class CreateRecipeFragment extends Fragment implements RemoveItemClickListener {

    private static final String[] REQUIRED_PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private static final int PERMISSION_REQUEST_CODE = 123;

    private static final int CHOOSE_IMAGE_REQUEST_CODE = 485;

    public static CreateRecipeFragment newInstance() {
        return new CreateRecipeFragment();
    }

    private Chip addImageChip;

    private ImageView recipeImageView;

    private StepsAdapter stepsAdapter;

    private final List<String> ingredients = new ArrayList<>();

    private final List<String> preparationSteps = new ArrayList<>();

    private String selectedCategory;

    private String selectedDuration;

    private String selectedImage;

    private Recipe recipe;

    private NewRecipeListener newRecipeListener;

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
        setupDuration(view);
        setupAddIngredientsChip(view);
        setupStepsList(view);

        view.findViewById(R.id.create_recipe_button).setOnClickListener(v -> {
            boolean formValid = validateForm();
            if (formValid) {
                if (!TextUtils.isEmpty(selectedImage)) {
                    uploadPhoto();
                } else {
                    new CreateRecipeAsyncTask().execute(recipe);
                }
            }
        });
    }

    private void setupCategories(View view) {
        final List<String> categories = new ArrayList<>(Category.values().length);
        for (Category category : Category.values()) {
            categories.add(category.getNiceName());
        }

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, categories);
        AutoCompleteTextView recipeCategoryTextView = view.findViewById(R.id.recipe_category);
        recipeCategoryTextView.setAdapter(categoryAdapter);

        recipeCategoryTextView.setOnItemClickListener((parent, view1, position, id) -> selectedCategory = categories.get(position));
    }

    private void setupDuration(View view) {
        final List<String> durations = new ArrayList<>();
        durations.add("hrs");
        durations.add("mins");

        ArrayAdapter<String> durationsAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, durations);
        AutoCompleteTextView durationsTextView = view.findViewById(R.id.time_type);
        durationsTextView.setAdapter(durationsAdapter);

        durationsTextView.setOnItemClickListener((parent, view1, position, id) -> selectedDuration = durations.get(position));
    }

    private void setupAddIngredientsChip(View view) {
        ChipGroup chipGroup = view.findViewById(R.id.ingredients_group);
        view.findViewById(R.id.add_ingredient_chip).setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle(R.string.add_ingredient);

            View view1 = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_text_input_layout, null, false);
            EditText inputEditText = view1.findViewById(R.id.input_text);
            builder.setView(view1);

            builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
                String ingredient = inputEditText.getText().toString();
                Chip chip = (Chip) LayoutInflater.from(requireContext()).inflate(R.layout.ingredient_chip, chipGroup, false);
                chip.setText(ingredient);
                chip.setOnCloseIconClickListener(v1 -> {
                    chipGroup.removeView(chip);
                    ingredients.remove(String.valueOf(chip.getText()));
                });

                int numOfChildren = chipGroup.getChildCount();

                chipGroup.addView(chip, numOfChildren - 1);

                ingredients.add(ingredient);
            });

            builder.setNegativeButton(android.R.string.cancel, null);
            AlertDialog dialog = builder.create();
            Window window = dialog.getWindow();
            if (window != null) {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            }
            dialog.show();
        });
    }

    private void setupStepsList(View view) {
        RecyclerView stepsList = view.findViewById(R.id.steps_recycler_view);
        stepsAdapter = new StepsAdapter(this);
        stepsList.setAdapter(stepsAdapter);

        view.findViewById(R.id.add_preparation_step).setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle(R.string.add_preparation_step);

            View view1 = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_text_input_layout, null, false);
            EditText inputEditText = view1.findViewById(R.id.input_text);
            builder.setView(view1);

            builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
                String preparationStep = inputEditText.getText().toString();
                preparationSteps.add(preparationStep);
                stepsAdapter.submitList(new ArrayList<>(preparationSteps));
            });

            builder.setNegativeButton(android.R.string.cancel, null);
            AlertDialog dialog = builder.create();
            Window window = dialog.getWindow();
            if (window != null) {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            }
            dialog.show();
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
            List<String> selectionData = Matisse.obtainPathResult(data);
            if (selectionData == null || selectionData.isEmpty()) {
                Toast.makeText(requireContext(), R.string.choose_image_error, Toast.LENGTH_SHORT).show();
            } else {
                selectedImage = selectionData.get(0);
                recipeImageView.setImageURI(Matisse.obtainResult(data).get(0));
                addImageChip.setVisibility(View.GONE);
            }
        } else {
            Toast.makeText(requireContext(), R.string.choose_image_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRemoveClick(String item, int position) {
        preparationSteps.remove(item);
        stepsAdapter.submitList(new ArrayList<>(preparationSteps));
    }

    private boolean validateForm() {
        recipe = new Recipe();

        String name = ((EditText) getView().findViewById(R.id.recipe_name_editText)).getText().toString();
        if (TextUtils.isEmpty(name)) {
            showErrorDialog(R.string.error_empty_recipe_name);
            return false;
        }
        recipe.setName(name);

        try {
            int servingSize = Integer.parseInt(((EditText) getView().findViewById(R.id.serving_size_editText)).getText().toString());
            recipe.setServingSize(servingSize);
        } catch (Exception e) {
            showErrorDialog(R.string.error_empty_recipe_serving_size);
            return false;
        }

        if (TextUtils.isEmpty(selectedCategory)) {
            showErrorDialog(R.string.error_empty_recipe_category);
            return false;
        }
        recipe.setCategory(selectedCategory);

        try {
            int time = Integer.parseInt(((EditText) getView().findViewById(R.id.recipe_time_editText)).getText().toString());
            if (TextUtils.isEmpty(selectedDuration)) {
                showErrorDialog(R.string.error_empty_recipe_duration);
                return false;
            }

            recipe.setDuration(selectedDuration.equals("hrs") ? time * 60 : time);
        } catch (Exception e) {
            showErrorDialog(R.string.error_empty_recipe_time);
            return false;
        }

        try {
            int calories = Integer.parseInt(((EditText) getView().findViewById(R.id.calorie_editText)).getText().toString());
            recipe.setCalories(calories);
        } catch (Exception e) {
            showErrorDialog(R.string.error_empty_recipe_calories);
            return false;
        }

        if (ingredients.isEmpty()) {
            showErrorDialog(R.string.error_empty_recipe_ingredients);
            return false;
        }

        recipe.setIngredients(ingredients);

        if (preparationSteps.isEmpty()) {
            showErrorDialog(R.string.error_empty_recipe_preparation_steps);
            return false;
        }
        recipe.setPreparationSteps(preparationSteps);

        return true;
    }

    private void showErrorDialog(@StringRes int msgRes) {
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.error)
                .setMessage(msgRes)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    private class CreateRecipeAsyncTask extends AsyncTask<Recipe, Void, Recipe> {

        @Override
        protected Recipe doInBackground(Recipe... recipes) {
            try {
                Recipe recipe = recipes[0];
                if (recipe.getImage() == null) {
                    recipe.setImage("");
                }
                Response<Recipe> recipeResponse = NetworkInteractor.getService().createRecipe(recipe).execute();
                if (recipeResponse.isSuccessful()) {
                    Recipe responseRecipe = recipeResponse.body();
                    int recipeId = responseRecipe.RecipeId;

                    for (String ingredient : ingredients) {
                        try {
                            NetworkInteractor.getService().createIngredient(new Ingredient(recipeId, ingredient)).execute();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    for (int i = 0; i < preparationSteps.size(); i++) {
                        try {
                            NetworkInteractor.getService().createPreparationStep(new PreparationStep(i + 1, recipeId, preparationSteps.get(i))).execute();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    return recipe;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Recipe recipe) {
            super.onPostExecute(recipe);
            if (recipe != null) {
                if (newRecipeListener != null) {
                    newRecipeListener.onNewRecipeAdded(recipe);
                }

                requireActivity().getSupportFragmentManager()
                        .popBackStack(CreateRecipeFragment.class.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            } else {
                Toast.makeText(requireContext(), "Failed to create recipe", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadPhoto() {
        String selectedImageName = selectedImage.substring(selectedImage.lastIndexOf("/") + 1);
        try {
            InputStream stream = new FileInputStream(new File(selectedImage));
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            final StorageReference imageStorageReference = storageReference.child(selectedImageName);

            UploadTask uploadTask = imageStorageReference.putStream(stream);
            uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                return imageStorageReference.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    recipe.setImage(task.getResult().toString());
                    new CreateRecipeAsyncTask().execute(recipe);
                }
            }).addOnFailureListener(e -> Toast.makeText(requireContext(), "Failed to upload image", Toast.LENGTH_SHORT).show());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Failed to create product", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            newRecipeListener = (NewRecipeListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        newRecipeListener = null;
    }
}