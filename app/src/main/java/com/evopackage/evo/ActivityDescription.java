package com.evopackage.evo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class ActivityDescription extends AppCompatDialogFragment  {

    private Activity ac;
    private String ev;
    private TextView txtName;
    private RecyclerView picGuest;
    private TextView txtDes;
    private TextView txtDate;
    private ArrayList<Uri> imageGuest;
    StorageReference guest;
    PictureAdapter picAdapter;




    public ActivityDescription(Activity _ac, String _ev){
        ac = _ac;
        ev = _ev;
    }


    public ActivityDescription() {
        super();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
       // LayoutInflater cardIn = requireActivity().getLayoutInflater();
        View vr = inflater.inflate(R.layout.activity_description, null);
        txtName = vr.findViewById(R.id.txtActivityName);
        txtDate = vr.findViewById(R.id.txtActivityDate);
        picGuest = vr.findViewById(R.id.rvact2);
        txtDes = vr.findViewById(R.id.txtActivityDes);
        imageGuest = new ArrayList<>();
        picAdapter = new PictureAdapter(imageGuest, ev, ac);
        guest = FirebaseStorage.getInstance().getReference().child("/images").child(ev).child(ac.GetName());

        picGuest.setAdapter(picAdapter);
        picAdapter.notifyDataSetChanged();
        //guest.getDownloadUrl().
       guest.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into((Target) imageGuest);
            }
        });



        populateData();

        builder.setView(vr)
                .setTitle("Activity Description")
                .setPositiveButton("Close", (dialog, which) -> {


                });

        return builder.create();

    }

    public void populateData() {
        txtName.setText(ac.GetName());

        txtDate.setText(ac.GetTime());

        txtDes.setText(ac.GetAddress());


    }

}

