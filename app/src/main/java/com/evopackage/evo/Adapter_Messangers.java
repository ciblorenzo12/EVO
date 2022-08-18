package com.evopackage.evo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter_Messangers extends RecyclerView.Adapter<Adapter_Messangers.viewHolderEvents> {

    private List<Messenges> messenges;
   private Context context;
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
     holder.name.setText(messenges.get(position).getSender());
     holder.date.setText(messenges.get(position).getDate());
     holder.messenger.setText((messenges.get(position).getMessenger()));
    }

    @Override
    public int getItemCount() {
        return messenges.size();
    }

    public class viewHolderEvents extends RecyclerView.ViewHolder {

        TextView name,date,messenger;
        public viewHolderEvents(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Name_msg);
            date  = itemView.findViewById(R.id.message_date);
            messenger = itemView.findViewById(R.id.messange_msg);



        }
    }
}
