package com.example.alphabankproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChatFragment extends Fragment {

    View v;
    RecyclerView recyclerView;
    AddCardAdapter addCardAdapter;
    int images[] = {R.drawable.girl_selfie, R.drawable.girl_selfie, R.drawable.girl_selfie, R.drawable.girl_selfie};
    String s1[], s2[];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_chat, container, false);
        s1 = getResources().getStringArray(R.array.card_add_description);
        s2 = getResources().getStringArray(R.array.card_add_name);
        recyclerView = v.findViewById(R.id.recyclerViewAdd);
        addCardAdapter = new AddCardAdapter(getContext(), images, s1, s2);
        recyclerView.setAdapter(addCardAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ImageView next =  v.findViewById(R.id.buttonForm);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), ActivityForm.class);
                startActivityForResult(myIntent, 0);
            }
        });

        return v;
    }
}
