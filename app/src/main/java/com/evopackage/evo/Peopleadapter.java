package com.evopackage.evo;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Peopleadapter {

    private ArrayList<Profile_Page> list3;

    public Peopleadapter(ArrayList<Profile_Page> l){
          list3 = l;
    }

    public class viewH extends RecyclerView.ViewHolder{
        TextView name;
        ImageView im;

        public viewH(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.peoplename);
            im = itemView.findViewById(R.id.peoplepicture);
        }


    }

}




