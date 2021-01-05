package CardOfMine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication543543.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private ArrayList<UserData> userData;
    private UserActionInterface userActionInterface;
    private Context context;
    DatabaseReference getRef;

    public UserAdapter(Context context, ArrayList<UserData> userData, UserActionInterface userActionInterface) {
        this.context = context;
        this.userActionInterface=userActionInterface;
        this.userData = userData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_file, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final UserData userData = this.userData.get(position);

        holder.tvName.setText(userData.getUserName());
        holder.tvDescription.setText(userData.getUserLastName());
        holder.qrImageView.setOnClickListener((view)->{
            if(userActionInterface!=null)
                userActionInterface.showQrCode(userData);
            });
        holder.rootLayout.setOnClickListener((view)->{
            if(userActionInterface!=null)
                userActionInterface.showUsersForshareCutaway(userData);
                        });
        holder.items.setOnClickListener((view)->{
            if(userActionInterface!=null)
                userActionInterface.showItems(userData);
        });

        holder.redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RedoCard.class);
                intent.putExtra("delete", userData.getDelete());
                intent.putExtra("id", userData.getId());
                intent.putExtra("lastname", userData.getUserLastName());
                intent.putExtra("fullName", userData.getUserName());
                intent.putExtra("phone", userData.getUserPhone());
                intent.putExtra("appeal", userData.getUserAppeal());
                intent.putExtra("email", userData.getUserEmail());
                intent.putExtra("organisation", userData.getUserOrganisation());
                intent.putExtra("adres", userData.getUserAdres());
                intent.putExtra("vk", userData.getUserVK());
                intent.putExtra("fb", userData.getUserFB());
                intent.putExtra("otchestvo", userData.getUserOtchestvo());

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDescription;
        ImageView qrImageView,items, redo, delete;
        ConstraintLayout rootLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            qrImageView = itemView.findViewById(R.id.qr);
            items = itemView.findViewById(R.id.items);
            rootLayout =itemView.findViewById(R.id.rootLayoyt);
            tvName = itemView.findViewById(R.id.myText2);
            tvDescription = itemView.findViewById(R.id.myText1);
            redo = itemView.findViewById(R.id.redo);
            delete = itemView.findViewById(R.id.deleteThisCard);
        }
    }
    interface UserActionInterface{
        void showQrCode(UserData userData);
        void showUsersForshareCutaway(UserData userData);
        void showItems(UserData userData);
    }
}
