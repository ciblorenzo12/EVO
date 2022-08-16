package com.evopackage.evo;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.Inflater;

import javax.xml.transform.Result;

public class create_activity_popup extends AppCompatDialogFragment {

    private EditText txtName;
    private EditText txtLoc;
    private EditText txtDate;
    private ImageView imageView;
    private ImageButton addImage;
    LinearLayout cardIm;
    private Uri fileImage;
    private Thread thread;
    private ArrayList<Uri> filePath = new ArrayList<>();
    private static final int GET_FROM_GALLERY = 3;
    private String eventKey;
    FirebaseStorage storage;
    StorageReference storageReference;
    LinearLayout.LayoutParams viewParamsCenter = new LinearLayout.LayoutParams(
            200  , LinearLayout.LayoutParams.MATCH_PARENT);
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        LayoutInflater cardIn = requireActivity().getLayoutInflater();
        View vr = inflater.inflate(R.layout.activity_dialog, null);
        cardIm = (LinearLayout) vr.findViewById(R.id.linerImage);
        txtName = vr.findViewById(R.id.editName);
        txtLoc = vr.findViewById(R.id.editLoc);

        txtDate = vr.findViewById(R.id.editDate);
        addImage = vr.findViewById(R.id.addImage);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        addImage.setOnClickListener(v -> openGallary());
        builder.setView(vr)
                .setTitle("Creating Activity")
                .setNegativeButton("cancel",(dialog, which) -> {

                })
                .setPositiveButton("Create", (dialog, which) -> {
                    DatabaseReference firebaseActivity = FirebaseDatabase.getInstance().getReference().child("events").child(eventKey).child("activities").child(txtName.getText().toString());
                    firebaseActivity.child("location").setValue(txtLoc.getText().toString());
                    firebaseActivity.child("time").setValue(txtDate.getText().toString());
                    // psuedo
                    // store in the storage path "events/eventKey/txtName.getText().toString()"
                    uploadImage();
                });
        return builder.create();
    }

    private void openGallary() {
        imageView = new ImageView(getActivity());
        Intent _intent = new Intent();
        _intent.setType("image/*");
        _intent.setAction(Intent.ACTION_PICK);
        startActivityForResult( Intent.createChooser(
                        _intent,
                        "Select Image from here..."),
                GET_FROM_GALLERY);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_FROM_GALLERY
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            fileImage = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContext().getContentResolver(),
                                fileImage);
                
                imageView.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }

            imageView.setLayoutParams(viewParamsCenter);
            cardIm.addView(imageView);

            filePath.add(fileImage);

        }
    }
    // UploadImage method
    private void uploadImage()  {

        if (filePath != null) {
            for (int i = 0; i < filePath.size(); i++) {
            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child("images").child(eventKey).child(txtName.getText().toString()).child(UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            // Progress Listener for loading
// percentage on the dialog box

                UploadTink(ref, filePath.get(i), progressDialog);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void UploadTink(StorageReference _ref,  Uri _filePath, ProgressDialog _progress) {
        _ref.putFile(_filePath)
                .addOnSuccessListener(
                        taskSnapshot -> {

                            // Image uploaded successfully
                            // Dismiss dialog
                            _progress.dismiss();
                            Toast
                                    .makeText(getContext(),
                                            "Image Uploaded!!",
                                            Toast.LENGTH_SHORT)
                                    .show();
                        })

                .addOnFailureListener(e -> {

                    // Error, Image not uploaded
                    _progress.dismiss();
                    Toast
                            .makeText(getContext(),
                                    "Failed " + e.getMessage(),
                                    Toast.LENGTH_SHORT)
                            .show();
                })
                .addOnProgressListener(
                        taskSnapshot -> {
                            double progress
                                    = (100.0
                                    * taskSnapshot.getBytesTransferred()
                                    / taskSnapshot.getTotalByteCount());
                            _progress.setMessage(
                                    "Uploaded "
                                            + (int) progress + "%");
                        });
    }

    public void setEventKey(String key)
    {
        eventKey = key;
    }
}
