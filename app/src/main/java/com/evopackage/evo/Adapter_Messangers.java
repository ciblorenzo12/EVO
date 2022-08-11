package com.evopackage.evo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter_Messangers extends RecyclerView.Adapter<Adapter_Messangers.viewHolderEvents> {

    private List<main_messenges> messenges;


    public Adapter_Messangers(List<main_messenges> messages_) {
        messenges = messages_;
    }

    @NonNull
    @Override
    public viewHolderEvents onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.messanging_chat,parent,false);
        viewHolderEvents holderEvents_ = new viewHolderEvents(v);
        return holderEvents_;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderEvents holder, int position) {


        holder.name.setText(messenges.get(position).getSender());
        holder.messange.setText(messenges.get(position).getMessenger());
        holder.date.setText(messenges.get(position).getDate());


    }

    @Override
    public int getItemCount() {
        return messenges.size();
    }

    public class viewHolderEvents extends RecyclerView.ViewHolder {
 TextView name,messange,date;

        public viewHolderEvents(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Name_msg);
           messange = itemView.findViewById((R.id.messange_msg));
            date= itemView.findViewById(R.id.message_date);

        }
    }
}
