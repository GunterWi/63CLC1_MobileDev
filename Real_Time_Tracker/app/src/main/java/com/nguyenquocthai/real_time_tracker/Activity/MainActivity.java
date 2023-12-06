package com.nguyenquocthai.real_time_tracker.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.nguyenquocthai.real_time_tracker.Adapter.NotificationsAdapter;
import com.nguyenquocthai.real_time_tracker.Fragments.JoinCircleFragment;
import com.nguyenquocthai.real_time_tracker.Fragments.ProfileFragment;
import com.nguyenquocthai.real_time_tracker.Model.NotificationItem;
import com.nguyenquocthai.real_time_tracker.R;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Initiation();
        setupToolbarAndDrawer();
        setupNotificationsButton();
        setupMap();
        callpermissionlistener();
        initLocationFab();
    }
    private void setupToolbarAndDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
    void getFMCToken(){

    }
    private void setupMap() {
        //set up google map on container
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        mapFragment.getMapAsync(this);
    }
    private void setupNotificationsButton() {
        notificationButton = findViewById(R.id.button_notifications);
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleNotificationPopup();
                NotificationItem cc = new NotificationItem("cc", "12:30", R.drawable.ic_email); // Use a proper drawable resource here
                addNotification(cc);
            }
        });
    }
    private void toggleNotificationPopup() {
        int xOffset = notificationButton.getWidth() - getWidthPanelNotification(); // This assumes halfScreenWidth is already calculated
        if (notificationPopup != null) {
            if (notificationPopup.isShowing()) {
                notificationPopup.dismiss();
            } else {
                notificationPopup.showAsDropDown(notificationButton, xOffset, 0);
            }
        }

        // Inflate the custom notification panel layout
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.notification_panel_layout, null);

        // Initialize the PopupWindow if it's null
        if (notificationPopup == null) {
            notificationPopup = new PopupWindow(customView, getWidthPanelNotification(), LayoutParams.WRAP_CONTENT);
            notificationPopup.setElevation(5.0f);
            notificationPopup.setOutsideTouchable(true);
            notificationPopup.setFocusable(true);
            // Setup the RecyclerView inside the PopupWindow
            if (recyclerView == null) {
                // Find the RecyclerView and initialize it only once
                recyclerView = customView.findViewById(R.id.notificationsRecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                if (adapter == null) {
                    adapter = new NotificationsAdapter(getNotificationItems());
                }
                recyclerView.setAdapter(adapter);
            }
        }

        // Show the popup window anchored to the notification button
        ImageButton notificationButton = findViewById(R.id.button_notifications);
        notificationPopup.showAsDropDown(notificationButton, 0, 0);
    }
    private List<NotificationItem> getNotificationItems() {
        List<NotificationItem> items = new ArrayList<>();
        return items;
    }
    public void addNotification(NotificationItem newNotification) {
        if (adapter != null) { // Check that adapter is not null
            adapter.getNotificationItems().add(0, newNotification); // Add the new notification
            adapter.notifyItemInserted(0); // Notify the adapter
            recyclerView.scrollToPosition(0); // Scroll to the top of the RecyclerView
        } else {
            // Adapter is not initialized yet, handle this case
        }
    }
    private int getWidthPanelNotification(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;

        return screenWidth / 2-40;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        update_location();
    }


    private void update_location() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PermissionChecker.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PermissionChecker.PERMISSION_GRANTED) {

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

            locationRequest = new LocationRequest()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(3000) //update interval
                    .setFastestInterval(5000); //fastest interval

            // Request location updates with the created LocationRequest object
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult != null) {
                        if (mMap != null) {
                            mMap.clear(); // clear position
                        }
                        final double lat = locationResult.getLastLocation().getLatitude();
                        final double log = locationResult.getLastLocation().getLongitude();
                        latLng = new LatLng(lat, log);
                        if (mMap != null) {
                            mMap.addMarker(new MarkerOptions().position(latLng).title("Your current location"));
                            if (isFirstCameraMove) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15F));
                                isFirstCameraMove = false;
                            }
                        }
                        /*LatLng sydney = new LatLng(13.7528, 109.2084);
                        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));*/
                        // Update latitude and longitude
                        Map<String, Object> update = new HashMap<>();
                        update.put("latitude", lat);
                        update.put("longitude", log);
                        databaseReference.child(current_uid).updateChildren(update).addOnCompleteListener(task -> {
                             //Toast.makeText(MainActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                        });
                    } else {
                        Toast.makeText(MainActivity.this, "Location not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }, getMainLooper());
        } else {
            callpermissionlistener();
        }
    }


    private void initLocationFab() {
        FloatingActionButton fab = findViewById(R.id.fab_current_location);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mMap != null && latLng != null){
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15F));
                } else {
                    Toast.makeText(MainActivity.this, "Still waiting for current location...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void callpermissionlistener() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        String rationale = "Please provide location permission so that you can ...";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("location permission")
                .setSettingsDialogTitle("warrning");

        Permissions.check(this, permissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.fragment_container);
                mapFragment.getMapAsync(MainActivity.this);
                update_location();
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                callpermissionlistener();
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            backpressedwarrning();
        }
        //super.onBackPressed();
    }
    public void backpressedwarrning(){
        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("Exit");
        alert.show();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            startActivity(new Intent(MainActivity.this, MainActivity.class));
            finish();
        } else if (itemId == R.id.nav_profile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
        } else if (itemId == R.id.nav_joiningc) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new JoinCircleFragment()).commit();
        } else if (itemId == R.id.nav_invite) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/lain");
            i.putExtra(Intent.EXTRA_TEXT,"https://www.google.com/maps/@"+latLng.latitude+","+latLng.longitude+",17z");
            startActivity(i.createChooser(i,"Share using: "));
        } else if(itemId==R.id.nav_share){

        } else if (itemId == R.id.nav_logout) {
            if (auth.getCurrentUser() != null){
                auth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    private void Initiation() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        current_uid = user.getUid();
    }
    private DrawerLayout drawerLayout;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private GoogleMap mMap;
    private LocationRequest locationRequest;
    private AlertDialog.Builder builder;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LatLng latLng;
    private String current_uid;
    private boolean isFirstCameraMove = true;
    private ImageButton notificationButton;
    private PopupWindow notificationPopup;
    private NotificationsAdapter adapter;
    private RecyclerView recyclerView;

}