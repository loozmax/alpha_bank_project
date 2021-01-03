package CardAddedByMe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication543543.R;

import java.util.ArrayList;
import CardOfMine.UserData;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<CardData> cardData;

    public CardAdapter(Context c, ArrayList<CardData> cardData) {
        this.context = c;
        this.cardData = cardData;
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
    }

    @Override
    public int getItemCount() {
        return cardData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDescription;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.title);
            tvDescription = itemView.findViewById(R.id.description);

        }
    }
}
