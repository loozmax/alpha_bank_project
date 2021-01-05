package CardAddedByMe;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication543543.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import CardOfMine.RedoCard;
import CardOfMine.UserData;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<CardData> cardData;
    private ConfirmListenerInterface confirmListenerInterface;

    public CardAdapter(Context c, ArrayList<CardData> cardData,ConfirmListenerInterface confirmListenerInterface) {
        this.context = c;
        this.cardData = cardData;
        this.confirmListenerInterface=confirmListenerInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_example, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.MyViewHolder holder, int position) {
        CardData userData = this.cardData.get(position);
        holder.tvName.setText(userData.getUserName());
        holder.tvDescription.setText(userData.getUserLastName());
        if(userData.needToConfirm){
            holder.neetToConfirmLayout.setVisibility(View.VISIBLE);
            holder.tvDelete.setOnClickListener((v -> {
                if(confirmListenerInterface!=null) confirmListenerInterface.deleteCardData(userData,position);
            }));
            holder.tvConfirm.setOnClickListener((v -> {
                if(confirmListenerInterface!=null) confirmListenerInterface.confirmCardData(userData,position);
            }));
        }
        else holder.neetToConfirmLayout.setVisibility(View.GONE);
        System.out.println("needToConfirm is "+userData.needToConfirm);
        holder.rooLayout.setOnClickListener((v -> {
            if(confirmListenerInterface!=null) confirmListenerInterface.showItems(userData);
        }));

        holder.favButn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavData favData = new FavData(userData.getDelete(), userData.getId(), userData.getUserName(), userData.getUserLastName(), userData.getUserOtchestvo(), userData.getUserAppeal(), userData.getUserOrganisation(), userData.getUserPhone(), userData.getUserEmail(), userData.getUserVK(), userData.getUserFB());
                mDatabase.child("users").child(userId).setValue(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDescription,tvDelete,tvConfirm;
        LinearLayout neetToConfirmLayout;
        ConstraintLayout rooLayout;
        ImageView favButn;
        DatabaseReference mDatabase;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.title);
            neetToConfirmLayout=itemView.findViewById(R.id.neetToConfirmLayout);
            rooLayout=itemView.findViewById(R.id.rootLayout);
            tvDescription = itemView.findViewById(R.id.description);
            tvDelete = itemView.findViewById(R.id.tvDelete);
            tvConfirm = itemView.findViewById(R.id.tvConfirm);
            favButn = itemView.findViewById(R.id.favButn);
            

        }
    }
    interface ConfirmListenerInterface{
        void confirmCardData(CardData cardData,int position);
        void deleteCardData(CardData cardData,int position);
        void showItems(CardData cardData);
    }
    public void removeItem(int position) {
        cardData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount() - position);
    }
}
