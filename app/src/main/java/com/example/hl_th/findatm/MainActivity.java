package com.example.hl_th.findatm;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity implements LocationListener, OnMapReadyCallback {

    private GoogleMap mMap;
    private ProgressDialog progressDialog;
    private final int ACCESS_ID_COURSE_FINE_LOCATION = 100;
    private MyLocation myLocation;
    private MyEventListener myEventListener;
    private LocationUtils locationUtils;
    private ActionBar actionBar;
    private Toolbar toolbar;
    private boolean isSearchOpened = false;
    private MenuItem mSearchAction;
    private EditText editSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Khởi tạo đối tượng

//        Tắt thanh action Bar
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.Grren));
        actionBar.hide();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Map loading");
        progressDialog.setMessage("Please wait ........");
        progressDialog.setCancelable(true);
        progressDialog.show();
        locationUtils = new LocationUtils(this);
        locationUtils.checkInternetConnection();
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.id_fragment_map);
        supportMapFragment.getMapAsync(this);


    }


    private void excute(MyLocation myLocation) {

        myEventListener = new MyEventListener(this, mMap, myLocation);
        myEventListener.myEvent();

    }

//    private void eventButton() {
//        btn_show_nearest_atm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ArrayList<LatLng> arrayLatLng = new ArrayList<LatLng>();
//                arrayLatLng.add(new LatLng(10.883860, 106.786184));
//                arrayLatLng.add(new LatLng(10.879767, 106.795956));
//                arrayLatLng.add(new LatLng(10.876238, 106.797649));
//                final MarkerOptions option = new MarkerOptions();
//                for (int i = 0; i < arrayLatLng.size(); i++) {
//                    option.title("My Location");
//                    option.snippet("Noi dung dia chi");
//                    option.position(arrayLatLng.get(i));
//                    option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
//                    final Marker currentMarker = mMap.addMarker(option);
//                    currentMarker.showInfoWindow();
//                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//                        @Override
//                        public void onInfoWindowClick(Marker marker) {
//                            Toast.makeText(getBaseContext(), option.getTitle(), Toast.LENGTH_LONG).show();
//                        }
//                    });
//
//                }
//
//            }
//        });
//
//
//    }


    /**
     * Hỏi quyền truy cập vị trí nếu API lớn hơn 23
     */
    private void askPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int access_coarse_location = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            int access_fine_location = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (access_coarse_location != PackageManager.PERMISSION_GRANTED || access_fine_location != PackageManager.PERMISSION_GRANTED) {
                String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
                ActivityCompat.requestPermissions(this, permissions, ACCESS_ID_COURSE_FINE_LOCATION);

                return;

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ACCESS_ID_COURSE_FINE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Grantes", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
//                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
//                startActivity(intent);
            case R.id.action_search:
                handleMenuSearch();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    protected void handleMenuSearch() {

        if (isSearchOpened) {
            actionBar.setDisplayShowCustomEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editSearch.getWindowToken(), 0);
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.close_search));
            isSearchOpened = false;
        } else {
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setCustomView(R.layout.search_bar);
            actionBar.setDisplayShowTitleEnabled(false);
            editSearch = (EditText) actionBar.getCustomView().findViewById(R.id.edtSearch);

            editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                    doSearch();
                    return true;
                }
            });
            editSearch.requestFocus();
            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editSearch, InputMethodManager.SHOW_IMPLICIT);
            //add the close icon
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.open_search));
            isSearchOpened = true;
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.mMap = googleMap;
        myLocation = new MyLocation(this, mMap, locationUtils);
        excute(myLocation);
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
//                Khi map da load xong
                progressDialog.dismiss();
//                Hỏi quyền truy cập vị trí

                askPermission();
                myLocation.showMyLocation();
            }
        });
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(isSearchOpened)
                {
                    isSearchOpened=false;
                    actionBar.show();
                }
                else
                {
                    isSearchOpened=true;
                    actionBar.hide();
                }

            }
        });

    }
}
