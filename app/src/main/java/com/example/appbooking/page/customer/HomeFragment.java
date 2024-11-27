package com.example.appbooking.page.customer;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.example.appbooking.R;
import com.example.appbooking.page.admin.homeAdmin;

public class HomeFragment extends Fragment {

    Button a, btn3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize the buttons
        a = view.findViewById(R.id.button2);
        btn3 = view.findViewById(R.id.btn3);

        // Button event handling
        a.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Logout!", Toast.LENGTH_SHORT).show();
        });


        // Handle btn3 click event
        btn3.setOnClickListener(v -> {
            Intent in = new Intent(getContext(), homeAdmin.class);
            startActivity(in);
        });

        // Return the view after setting up listeners and logic
        return view;
    }
}
