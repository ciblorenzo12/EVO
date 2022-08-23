package com.evopackage.evo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_Messangers extends RecyclerView.Adapter<Adapter_Messangers.viewHolderEvents> {
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private List<Messenges> messenges;
   private Context context;


   private StorageReference refStorage = FirebaseStorage.getInstance().getReference().child("user_profile_pics");
    public Adapter_Messangers(Context context, List<Messenges> messenges ) {
        this.messenges = messenges;
        this.context = context;
    }


    @NonNull
    @Override
    public Adapter_Messangers.viewHolderEvents onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.messanging_chat,parent,false);

        return new viewHolderEvents(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Messangers.viewHolderEvents holder, int position) {
       StorageReference file  = refStorage.child(user.getUid()).child("profile.png");
        Picasso.get().load(String.valueOf(file)).into(holder.profile);
     holder.name.setText(messenges.get(position).getSender());
     holder.date.setText(messenges.get(position).getDate());
     holder.messenger.setText((messenges.get(position).getMessenger()));

     if (messenges.get(position).getSender().contains(user.getDisplayName())) {
         holder.card.setCardBackgroundColor(Color.GREEN);
         holder.card.setRadius(40);
     }else {
         holder.card.setCardBackgroundColor(Color.CYAN);
         holder.card.setRadius(40);
     }
    }

    @Override
    public int getItemCount() {
        return messenges.size();
    }

    public class viewHolderEvents extends RecyclerView.ViewHolder {

        TextView name,date,messenger;
        CardView card;
        ImageView profile;
        public viewHolderEvents(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Name_msg);
            date  = itemView.findViewById(R.id.message_date);
            messenger = itemView.findViewById(R.id.messange_msg);
            card = itemView.findViewById(R.id.Category_events);
            profile = itemView.findViewById(R.id.profile_evt);



        }
    }
}
