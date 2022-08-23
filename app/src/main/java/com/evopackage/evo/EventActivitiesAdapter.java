package com.evopackage.evo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventActivitiesAdapter extends RecyclerView.Adapter<EventActivitiesAdapter.viewH>{

    private ArrayList<Activity> list2;
    final EventActivitiesAdapter.OnItemClickListener ac;

    public EventActivitiesAdapter(ArrayList<Activity> l, EventActivitiesAdapter.OnItemClickListener listen){
        list2 = l;
        this.ac = listen;
    }

    public interface OnItemClickListener
    {
        void OnItemClick(Activity ac);


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
        holder.bindData(list2.get(position));


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
        }
        public void bindData(Activity activity) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ac.OnItemClick(activity);
                }
            });
        }

    }
}
