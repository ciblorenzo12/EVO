package com.evopackage.evo;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.Viewh> {

    ArrayList<Event> list;

    public MyAdapter(ArrayList<Event> list1) {
        list = list1;
    }



    @NonNull
    @Override
    public Viewh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item,parent,false);
        Viewh h = new Viewh(v);

        return h;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewh holder, int position) {

        holder.cat.setText(list.get(position).GetCategory());
        holder.date.setText(list.get(position).GetDate());
        holder.name.setText(list.get(position).GetName());
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewh  extends RecyclerView.ViewHolder {


        TextView name;
        TextView cat;
        TextView date;

        public Viewh(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.sseventname);
            cat = itemView.findViewById(R.id.sseventcategory);
            date = itemView.findViewById(R.id.sseventdate);


        }
    }
}
