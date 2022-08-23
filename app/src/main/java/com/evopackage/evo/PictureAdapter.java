package com.evopackage.evo;

import android.graphics.Picture;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureViewHolder> {
    private List<Uri> pictureList;
    private StorageReference mStorageRef;
    private String ev;
    private Activity ac;


    public PictureAdapter(List<Uri> pictureList, String _ev, Activity _ac)
    {
        this.pictureList = pictureList;
        this.ev = _ev;
        this.ac = _ac;
    }

    @NonNull
    @Override
    public PictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_pic_guest,
                parent, false);


        return new PictureViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull PictureViewHolder holder, int position) {
        Uri pi = pictureList.get(position);

        //FirebaseStorage storage = FirebaseStorage.getInstance();

       // mStorageRef = storage.getReference().child("/images").child(ev).child(ac.GetName());

                Glide.with(holder.itemView.getContext())
                        .load(pi)
                        .into(holder.picture);


    }

    @Override
    public int getItemCount() {
        return pictureList.size();
    }

    public class PictureViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView picture;


        public PictureViewHolder(@NonNull View itemView)
        {
            super(itemView);

            picture = (ImageView) itemView.findViewById(R.id.imageGuest);
        }


        public ImageView getPictureContents()
        {
            return picture;
        }
    }
}
