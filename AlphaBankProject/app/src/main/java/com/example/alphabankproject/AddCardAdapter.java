package com.example.alphabankproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddCardAdapter extends RecyclerView.Adapter<AddCardAdapter.MyViewHolder> {

    Context context;
    String data1[], data2[];
    int images[];

    public AddCardAdapter(Context ct, int img[], String s1[], String s2[]) {
        context = ct;
        images = img;
        data1 = s1;
        data2 = s2;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.add_new_card, parent, false);
        return new AddCardAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imageView.setImageResource(images[position]);
        holder.myText1.setText(data1[position]);
        holder.myText2.setText(data2[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView myText1, myText2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.descriptionPerson);
            myText2 = itemView.findViewById(R.id.namePerson);
            imageView = itemView.findViewById(R.id.personImg);
        }
    }
}
