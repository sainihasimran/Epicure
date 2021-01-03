package com.cegep.epicure;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.cegep.epicure.helper.CategoryUiHandler;
import com.cegep.epicure.model.Category;
import com.cegep.epicure.model.User;

public class HomeFragment extends Fragment implements CategoryUiHandler.CategorySelectedListener {

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        new CategoryUiHandler(view, this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO: 02/01/21 Remove hardcoded user
        User user = new User();

        TextView nameTextView = view.findViewById(R.id.name);
        nameTextView.setText(getString(R.string.greeting, user.firstName));
    }

    @Override
    public void onCategorySelected(Category category) {

    }
}