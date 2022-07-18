package com.evopackage.evo;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class Event_Page extends AppCompatActivity {

    private TextView event_name;

    //fields
    private static String mName,mAdress,mDate,mQr;
private DatabaseReference reference_events= FirebaseDatabase.getInstance().getReference().child("events");

    public Event_Page(String _mName,String _mAdress,String _mDate,String _mQr) {
        mName = _mName;
      mAdress= _mAdress;
       mDate = _mDate;
        mQr = _mQr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_page);

event_name = findViewById(R.id.Event_name);


            event_name.setText(mName);
            GenerateQr(create_event_popup.GetCurrent_EventID());





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
            ((ImageView) findViewById(R.id.EventQr)).setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public static void Pass_Event_(String _name,String _adress,String _date,String _qr){

        mName = _name;
        mAdress = _adress;
        mDate = _date;
        mQr = _qr;

    }
}