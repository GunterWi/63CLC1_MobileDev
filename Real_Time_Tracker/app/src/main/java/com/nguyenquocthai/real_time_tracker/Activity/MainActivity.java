package com.nguyenquocthai.real_time_tracker.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.nguyenquocthai.real_time_tracker.AcceptShare;
import com.nguyenquocthai.real_time_tracker.Adapter.NotificationsAdapter;
import com.nguyenquocthai.real_time_tracker.Fragments.JoinCircleFragment;
import com.nguyenquocthai.real_time_tracker.Fragments.ProfileFragment;
import com.nguyenquocthai.real_time_tracker.Model.NotificationItem;
import com.nguyenquocthai.real_time_tracker.R;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Initiation();
        setupToolbarAndDrawer();
        getProfile();
        setupNotificationsButton();
        setupMap();
        callpermissionlistener();
        initLocationFab();
        getFMCToken();
        if(getIntent().getExtras()!=null){
            showAlertDialog("Hey there! "+getIntent().getExtras().getString("name")+" would like to share their location with you ",getIntent().getExtras().getString("userID"));
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("com.yourapp.FCM_MESSAGE"));
        getNotification();
    }
    private void showAlertDialog(String message,String userID) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Log.d("cac3",userID);
                        AcceptShare acceptShare = new AcceptShare(userID,MainActivity.this);
                        acceptShare.Execute();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };
        if (alertDialogBuilder == null) {
            alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            alertDialogBuilder.setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener);
        }
        alertDialogBuilder.setMessage(message).show();
    }
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            showAlertDialog("Hey there! "+getIntent().getExtras().getString("name")+" would like to share their location with you ",getIntent().getExtras().getString("userID"));
        }
    };
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
        View header = navigationView.getHeaderView(0);
        nameView = header.findViewById(R.id.textview_name);
        emailView = header.findViewById(R.id.textview_email);
        avatar = header.findViewById(R.id.image_avatar);
    }
    private void getProfile(){
        databaseReference.child(current_uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("ProfileFragment", "Data changed");
                if (snapshot.exists()) {
                    String firstname = snapshot.child("firstname").getValue(String.class);
                    String lastname = snapshot.child("lastname").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    String image = snapshot.child("image_url").getValue(String.class);
                    emailView.setText(email);
                    nameView.setText(lastname+" "+firstname);
                    if(image!="null"){
                        Picasso.get().load(image).into(avatar);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    void getFMCToken(){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
           if(task.isSuccessful()){
               String token = task.getResult();
               databaseReference.child(current_uid).child("fcmToken").setValue(token);
           }
        });
    }
    void getNotification(){

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
                Random r = new Random();
                int intcode = 1 + r.nextInt(3);
                NotificationItem cc = new NotificationItem(""+intcode, "12:30", R.drawable.ic_email); // Use a proper drawable resource here
                addNotification(cc);
                adapter.setOnNotificationItemClickListener(new NotificationsAdapter.OnNotificationItemClickListener() {
                    @Override
                    public void onNotificationItemClick(NotificationItem item) {
                        if (item.getMessage().equals("1") ) {
                            // Thực hiện hành động cho item cụ thể này
                            showToast("Item đặc biệt được chọn");
                        } else {
                            // Hành động cho các item khác
                            showToast("Item thông thường: "+item.getMessage());

                        }
                    }
                });
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

    private void showToast(String message){
        if (currentToast != null) {
            currentToast.cancel(); // Tắt thông báo cũ nếu có
        }
        currentToast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        currentToast.show();
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
                        showToast("Location not found");
                    }
                }
            }, getMainLooper());
        } else {
            callpermissionlistener();
        }
    }


    private void initLocationFab() {
        fab = findViewById(R.id.fab_current_location);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mMap != null && latLng != null){
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15F));
                } else {
                    showToast("Still waiting for current location...");
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
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
            fab.setVisibility(View.VISIBLE);
            finish();
        } else if (itemId == R.id.nav_profile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
            fab.setVisibility(View.GONE);
        } else if (itemId == R.id.nav_joiningc) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new JoinCircleFragment()).commit();
            fab.setVisibility(View.GONE);
        } else if (itemId == R.id.nav_invite) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/lain");
            i.putExtra(Intent.EXTRA_TEXT,"https://www.google.com/maps/@"+latLng.latitude+","+latLng.longitude+",17z");
            startActivity(i.createChooser(i,"Share using: "));
        } else if(itemId==R.id.nav_share){

        } else if (itemId == R.id.nav_logout) {
            if (auth.getCurrentUser() != null){
                FirebaseMessaging.getInstance().deleteToken().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        auth.signOut();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    }
                });

            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    private void Initiation() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        current_uid = user != null ? user.getUid() : "";
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
    }
    private DrawerLayout drawerLayout;
    private TextView nameView,emailView;
    private CircleImageView avatar;
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
    private FloatingActionButton fab;
    private Toast currentToast = null; // Biến để lưu trạng thái của thông báo hiện tại
    private ImageButton notificationButton;
    private PopupWindow notificationPopup;
    private NotificationsAdapter adapter;
    private RecyclerView recyclerView;
    private AlertDialog.Builder alertDialogBuilder;

}