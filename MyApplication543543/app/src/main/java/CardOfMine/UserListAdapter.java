package CardOfMine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication543543.R;
import com.example.myapplication543543.User;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {

    //  private Context context;
    private ArrayList<User> users;
    private  UserData userData;
    private  UserListActionInterface userListActionInterface;
    public UserListAdapter(ArrayList<User> userData,UserListActionInterface userListActionInterface) {
        this.userListActionInterface=userListActionInterface;
        this.users = userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = this.users.get(position);
        holder.tvName.setText(user.fullName);
        holder.rootLayout.setOnClickListener((v -> {
            if(userListActionInterface!=null)
                userListActionInterface.shareCutaway(user,userData);
        }));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView photoImageView;
        ConstraintLayout rootLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.myText2);
            rootLayout=itemView.findViewById(R.id.rootLayoyt);
        }
    }
    interface UserListActionInterface{
        void shareCutaway(User user,UserData userData);
    }
}
