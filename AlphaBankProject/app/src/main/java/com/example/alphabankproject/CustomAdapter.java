package com.example.alphabankproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    Context context;
    String data1[], data2[];
    int images[];
    int imagesQr[];
    int imagesItems[];

    public CustomAdapter(Context ct, int img[], String s1[], String s2[], int imgQr[], int imgItem[]) {
        context = ct;
        images = img;
        data1 = s1;
        data2 = s2;
        imagesItems = imgItem;
        imagesQr = imgQr;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_file, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imageView.setImageResource(images[position]);
        holder.imagesItems.setImageResource(imagesItems[position]);
        holder.imagesQr.setImageResource(imagesQr[position]);
        holder.myText1.setText(data1[position]);
        holder.myText2.setText(data2[position]);

    }

    @Override
    public int getItemCount() {
        return imagesItems.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, imagesQr, imagesItems;
        TextView myText1, myText2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            imagesQr = itemView.findViewById(R.id.qr);
            imagesItems = itemView.findViewById(R.id.items);
            myText1 = itemView.findViewById(R.id.myText1);
            myText2 = itemView.findViewById(R.id.myText2);
        }
    }
}
