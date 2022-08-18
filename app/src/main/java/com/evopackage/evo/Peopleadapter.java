package com.evopackage.evo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Peopleadapter extends RecyclerView.Adapter<Peopleadapter.viewH> {

    private ArrayList<String> list3;

    public Peopleadapter(ArrayList<String> l){
          list3 = l;
    }
    public viewH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.people_item,parent,false);
        Peopleadapter.viewH holderEvents_ = new Peopleadapter.viewH(v);
        return holderEvents_;
    }

    @Override
    public void onBindViewHolder(@NonNull viewH holder, int position) {
        holder.name.setText(list3.get(position));
    }


    public int getItemCount() {
        return list3.size();
    }


    public class viewH extends RecyclerView.ViewHolder{
        TextView name;

        public viewH(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.peoplename);
        }


    }

}




