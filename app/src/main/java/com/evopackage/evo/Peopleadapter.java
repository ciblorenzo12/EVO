//package com.evopackage.evo;
//
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//public class Peopleadapter {
//
//    private List<Profile_Page> list3;
//    private final Peopleadapter.OnItemClickListener l;
//
//    public Peopleadapter(List<Profile_Page> l, Peopleadapter.OnItemClickListener listen2){
//          list3 = l;
//          this.l = listen2;
//
//    }
//
//    public class viewH extends RecyclerView.ViewHolder{
//
//        TextView name;
//        ImageView im;
//
//        public viewH(@NonNull View itemView) {
//            super(itemView);
//
//            name = itemView.findViewById(R.id.peoplename);
//            im = itemView.findViewById(R.id.peoplepicture);
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
//    public interface OnItemClickListener
//    {
//        void OnItemClick(Activity act);
//    }
//}




