package CardAddedByMe;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication543543.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import CardOfMine.UserData;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<CardData> cardData;
    private ConfirmListenerInterface confirmListenerInterface;
    private boolean showJustFavorite;
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

    public void setCardData(ArrayList<CardData> cardData) {
        this.cardData = cardData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.MyViewHolder holder, int position) {
        CardData userData = this.cardData.get(position);
        Picasso.get()
                .load(userData.getmImageUrl())
                .fit()
                .centerCrop()
                .into(holder.avatar);

        holder.tvName.setText(userData.getUserName());
        holder.tvDescription.setText(userData.getUserLastName());
        if(userData.isFavorite()) {
            holder.imgFavorite.setVisibility(View.VISIBLE);
            //holder.infolayout.setVisibility(View.VISIBLE);
        }
        else {
            holder.imgFavorite.setVisibility(View.GONE);
           // holder.infolayout.setVisibility(View.GONE);
        }
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
        System.out.println("needToConfirm is " + userData.needToConfirm);
        holder.rooLayout.setOnClickListener((v -> {
            if(confirmListenerInterface!=null) confirmListenerInterface.showItems(userData,position);
        }));

        holder.infolayout.setVisibility(View.GONE);
        holder.right_arrow.setRotation(90);

        holder.right_arrow.setOnClickListener((view)->{
            if(holder.infolayout.getVisibility()==View.GONE){
                holder.infolayout.setVisibility(View.VISIBLE);
                holder.right_arrow.setRotation(-90);
            }else {
                holder.infolayout.setVisibility(View.GONE);
                holder.right_arrow.setRotation(90);
            }

        });

        holder.organization.setText(userData.userOrganisation);
        holder.phoneNumb.setText(userData.userPhone);
        holder.emailAdrr.setText(userData.userEmail);
        holder.addrrr.setText(userData.getUserAddress());
        holder.vk_profile.setOnClickListener((v -> {
            if(confirmListenerInterface!=null) confirmListenerInterface.openLink(userData.userVK);
        }));
        holder.fcb_profile.setOnClickListener((v -> {
            if(confirmListenerInterface!=null) confirmListenerInterface.openLink(userData.userFB);
        }));
    }

    @Override
    public int getItemCount() {
        return cardData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDescription,tvDelete,tvConfirm;
        LinearLayout neetToConfirmLayout;
        ConstraintLayout rooLayout;
        ImageView imgFavorite,right_arrow;
        ConstraintLayout infolayout;
        EditText organization,phoneNumb,emailAdrr,addrrr;
        Button vk_profile,fcb_profile;
        ImageView avatar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.title);
            infolayout= itemView.findViewById(R.id.infolayout);
            neetToConfirmLayout=itemView.findViewById(R.id.neetToConfirmLayout);
            rooLayout=itemView.findViewById(R.id.rootLayout);
            tvDescription = itemView.findViewById(R.id.description);
            tvDelete = itemView.findViewById(R.id.tvDelete);
            tvConfirm = itemView.findViewById(R.id.tvConfirm);
            imgFavorite= itemView.findViewById(R.id.imgFavorite);
            right_arrow= itemView.findViewById(R.id.right_arrow);
            organization= itemView.findViewById(R.id.organization);
            phoneNumb= itemView.findViewById(R.id.phoneNumb);
            emailAdrr= itemView.findViewById(R.id.emailAdrr);
            addrrr= itemView.findViewById(R.id.addrrr);
            vk_profile= itemView.findViewById(R.id.vk_profile);
            fcb_profile= itemView.findViewById(R.id.fcb_profile);
            avatar = itemView.findViewById(R.id.imageCard);

        }
    }

    public boolean isShowJustFavorite() {
        return showJustFavorite;
    }

    public void setShowJustFavorite(boolean showJustFavorite) {
        this.showJustFavorite = showJustFavorite;
    }

    interface ConfirmListenerInterface{
        void confirmCardData(CardData cardData,int position);
        void deleteCardData(CardData cardData,int position);
        void showItems(CardData cardData,int position);
        void openLink(String uri);
    }
    public void removeItem(int position) {
        cardData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount() - position);
    }
}
