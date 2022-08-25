package com.evopackage.evo;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter_Recicleview extends RecyclerView.Adapter<Adapter_Recicleview.viewHolderEvents> {

    private List<Event> events;
    final Adapter_Recicleview.OnItemClickListener l;
    CardView cv;


    public Adapter_Recicleview(List<Event> event, Adapter_Recicleview.OnItemClickListener listen) {
        events = event;
        this.l = listen;
    }

    public interface OnItemClickListener
    {
        void OnItemClick(Event ev);


    }

    @NonNull
    @Override
    public viewHolderEvents onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_clicker,parent,false);
        viewHolderEvents holderEvents_ = new viewHolderEvents(v);
        return holderEvents_;



    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderEvents holder, int position) {
        holder.name.setText(events.get(position).GetName());
        holder.date.setText(events.get(position).GetDate());
        holder.category.setText(events.get(position).GetCategory());
        holder.bindData(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class viewHolderEvents extends RecyclerView.ViewHolder {
 TextView name,address,category,date,creator;
 ImageView image_;

        public viewHolderEvents(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Name_evt);
            category=itemView.findViewById(R.id.Category_evt);
            date= itemView.findViewById(R.id.Date_evt);
            cv = itemView.findViewById(R.id.cv);

            if(AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES){

                 int c = Color.parseColor("grey");

                if(cv != null){
                    cv.setBackgroundResource(R.color.black);}
                 name.setTextColor(c);
                 date.setTextColor(c);
                 category.setTextColor(c);

                }

        }

        public void bindData(Event event) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    l.OnItemClick(event);
                }

            });
        }
    }
}
