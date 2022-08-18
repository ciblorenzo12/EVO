package com.evopackage.evo;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.squareup.picasso.Picasso;

public class Event_Page extends AppCompatActivity implements View.OnClickListener {

    private TextView event_name;

    //fields
    private ImageButton eventpicture;
    private Uri images;
    private static String mName = "",
            mAdress = "",
            mDate = "",
            mQr = "",
            mUri = "";

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseUser user_ = FirebaseAuth.getInstance().getCurrentUser();
    private UploadTask MuploadTask;
    private StorageReference refer_storage = storage.getReference();
    private DatabaseReference reference_events = FirebaseDatabase.getInstance().getReference().child("events");
    private DatabaseReference reference_user_event = FirebaseDatabase.getInstance().getReference().child("users").child(user_.getUid()).child("My_Events");
    private static final int tiktok = 1287;

    public Event_Page() {
    }

    ;

    public Event_Page(String _mName, String _mAdress, String _mDate, String _mQr, String _mUri) {
        mName = _mName;
        mAdress = _mAdress;
        mDate = _mDate;
        mQr = _mQr;
        mUri = _mUri;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_page);

        eventpicture = findViewById(R.id.eventPicture);
        event_name = findViewById(R.id.Event_name);

        eventpicture.setOnClickListener(this);
        event_name.setText(create_event_popup.GetCurrent_Event());
        GenerateQr(EventDescription.GetClickedEvent());

        Event ev = (Event) getIntent().getSerializableExtra("Event_Page");
    }

    //create a qr code
    private void GenerateQr(String qr) {
        QRCodeWriter writer = new QRCodeWriter();

        try {
            BitMatrix bitMatrix = writer.encode(qr, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            ((ImageView) findViewById(R.id.qrvt)).setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }


    private void uploadpicture() {
        if (images == null) {

            Intent _intent = new Intent();
            _intent.setType("image/*");
            _intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(_intent, tiktok);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == tiktok || resultCode == RESULT_OK || data != null || data.getData() != null) {
                images = data.getData();
                update();
                Picasso.get().load(images).into(eventpicture);

                Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Select a file please" + e, Toast.LENGTH_SHORT).show();
        }

    }

    public String Getext(Uri uri_pic) {

        ContentResolver cont = getContentResolver();
        MimeTypeMap mmap = MimeTypeMap.getSingleton();
        return mmap.getExtensionFromMimeType(cont.getType(uri_pic));
    }

    private void update() {
        if (images != null) {

            StorageReference ref = refer_storage.child("images").child(create_event_popup.GetCurrent_EventID())
                    .child("profile" + "." + Getext(images));
            String code = ref.toString();
            Toast.makeText(this, code, Toast.LENGTH_SHORT).show();
            ref.delete();
            MuploadTask = ref.putFile(images);
            Task<Uri> urltask = MuploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return ref.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    Uri downl = task.getResult();
                    reference_events.child(create_event_popup.GetCurrent_EventID()).child("Event picture").setValue(downl.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                }
                            }, 100);
                        }
                    });

                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        uploadpicture();
    }
}
