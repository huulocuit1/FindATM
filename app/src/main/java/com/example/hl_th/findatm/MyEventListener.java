package com.example.hl_th.findatm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

/**
 * Created by HL_TH on 12/4/2016.
 */

public class MyEventListener extends AppCompatActivity {

    private GoogleMap googleMap;
    private Activity activity;
    private Button btn_search;
    private Button btn_my_location;
    private Button btn_zoom_in;
    private Button btn_zoom_out;
    private MyLocation myLocation;




    public MyEventListener(Activity activity, GoogleMap googleMap, MyLocation myLocation) {
        this.activity = activity;
        this.googleMap=googleMap;
        this.myLocation=myLocation;
        initViewMainActivity();

    }


    public void initViewMainActivity() {
        this.btn_my_location = (Button) activity.findViewById(R.id.id_btn_location);
        this.btn_search=(Button)activity.findViewById(R.id.id_btn_search);
        this.btn_zoom_in=(Button)activity.findViewById(R.id.id_btn_zoom_in);
        this.btn_zoom_out=(Button)activity.findViewById(R.id.id_btn_zoom_out);


    }

    public void myEvent() {
        this.btn_my_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myLocation.showMyLocation();
            }
        });
        this.btn_zoom_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleMap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });
        this.btn_zoom_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                googleMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });
        this.btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchDialog searchDialog = new SearchDialog(activity);
                searchDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                searchDialog.show();
            }
        });

    }

    /***
     * Hiển thị dialog cho phép thiết lập mở kết nối GPS
     */
    public void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                activity.startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

    }
}
