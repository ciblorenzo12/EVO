//package com.evopackage.evo;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//public class EventActivitiesAdapter extends RecyclerView.Adapter<EventActivitiesAdapter.viewH>{
//
//    private List<Activity> list2;
//    final EventActivitiesAdapter.OnItemClickListener l;
//
//    public EventActivitiesAdapter(List<Activity> l, EventActivitiesAdapter.OnItemClickListener listen2){
//        list2 = l;
//        this.l = listen2;
//
//    }
//
//    @NonNull
//    @Override
//    public viewH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.activities_item,parent,false);
//        EventActivitiesAdapter.viewH holderEvents_ = new EventActivitiesAdapter.viewH(v);
//        return holderEvents_;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull viewH holder, int position) {
//
//        holder.category.setText(list2.get(position).GetTheme());
//        holder.name.setText(list2.get(position).GetName());
//        holder.date.setText(list2.get(position).GetTime());
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return list2.size();
//    }
//
//    public interface OnItemClickListener
//    {
//        void OnItemClick(Activity act);
//    }
//
//
//    public class viewH extends RecyclerView.ViewHolder{
//
//        TextView name, date, category;
//
//        public viewH(@NonNull View itemView) {
//            super(itemView);
//
//            name = itemView.findViewById(R.id.actname);
//            date = itemView.findViewById(R.id.actdate);
//            category = itemView.findViewById(R.id.actcat);
//        }
//
//        public void bindData(Activity act) {
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    l.OnItemClick(act);
//                }
//            });
//        }
//
//    }
//}
