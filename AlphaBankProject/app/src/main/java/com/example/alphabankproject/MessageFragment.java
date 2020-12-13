package com.example.alphabankproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment {

    View v;
    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    int images[] = {R.drawable.self_logo, R.drawable.self_logo, R.drawable.self_logo};
    int imagesQr[] = {R.drawable.qr_code, R.drawable.qr_code, R.drawable.qr_code};
    int imagesItems[] = {R.drawable.items, R.drawable.items, R.drawable.items};
    String s1[], s2[];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_message, container, false);
        s1 = getResources().getStringArray(R.array.card_user_name);
        s2 = getResources().getStringArray(R.array.card_description);
        recyclerView = v.findViewById(R.id.recyclerViewId);
        customAdapter = new CustomAdapter(getContext(), images, s1, s2, imagesQr, imagesItems);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }
}
