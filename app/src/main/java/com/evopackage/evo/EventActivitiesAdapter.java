package com.evopackage.evo;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventActivitiesAdapter extends RecyclerView.Adapter<EventActivitiesAdapter.viewH>{

    private CardView c;
    private ArrayList<Activity> list2;
    public EventActivitiesAdapter(ArrayList<Activity> l){
        list2 = l;

    }

    @NonNull
    @Override
    public viewH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.activities_item,parent,false);
        EventActivitiesAdapter.viewH holderEvents_ = new EventActivitiesAdapter.viewH(v);
        return holderEvents_;
    }

    @Override
    public void onBindViewHolder(@NonNull viewH holder, int position) {

        holder.loc.setText(list2.get(position).GetAddress());
        holder.name.setText(list2.get(position).GetName());
        holder.date.setText(list2.get(position).GetTime());

    }

    @Override
    public int getItemCount() {
        return list2.size();
    }


    public class viewH extends RecyclerView.ViewHolder{

        TextView name, date, loc;

        public viewH(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.actname);
            date = itemView.findViewById(R.id.actdate);
            loc = itemView.findViewById(R.id.actlocation);

            c = itemView.findViewById(R.id.cvactiv);
            if(AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES){

                int ci = Color.parseColor("grey");
                if(c != null){
                c.setBackgroundResource(R.color.black);}
                name.setTextColor(ci);
                date.setTextColor(ci);
                loc.setTextColor(ci);


            }
        }


    }
}
