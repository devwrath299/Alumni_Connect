package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.instagramclone.Fragments.HomeFragment;
import com.example.instagramclone.Fragments.JobListFragment;
import com.example.instagramclone.Fragments.NotificationFragment;
import com.example.instagramclone.Fragments.ProfileFragment;
import com.example.instagramclone.Fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Start_activity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Fragment selectorFragment;
    FloatingActionButton btnadd;
    public  String currentCollegeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        bottomNavigationView = findViewById(R.id.bottom);
        btnadd = findViewById(R.id.fab);
        bottomNavigationView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent));
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogBox();

            }
        });
        set_college_id();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.navhome:
                        selectorFragment= new HomeFragment();

                        break;
                    case R.id.navsearch:
                        selectorFragment= new SearchFragment();

                        break;
                    case R.id.navadd:
                        selectorFragment = null;


//                        startActivity( new Intent(Start_activity.this,PostActivity.class));


                        break;
                    case R.id.navheart:
//                        selectorFragment= new NotificationFragment();
                        selectorFragment = new JobListFragment();

                        break;
                    case R.id.navprofile:
//                        Intent intent = new Intent(Start_activity.this, Start_activity.class);
//                        intent.putExtra("PublisherId", FirebaseAuth.getInstance().getCurrentUser().getUid());
//                        startActivity(intent);
//                        finish();
                        getSharedPreferences("Profile",MODE_PRIVATE).edit().putString("ProfileId",FirebaseAuth.getInstance().getCurrentUser().getUid()).apply();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment()).commit();
//                        selectorFragment= new ProfileFragment();
                        selectorFragment = null;

                        break;
                }
               if (selectorFragment!=null){
                   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectorFragment).commit();
               }
               return true;
            }

        });
        Bundle intent = getIntent().getExtras();
        if (intent!=null){
            String profileid =intent.getString("PublisherId");
            getSharedPreferences("Profile",MODE_PRIVATE).edit().putString("ProfileId",profileid).apply();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment()).commit();

        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();


        }
    }

    private void set_college_id() {
        final String current_user_id=FirebaseAuth.getInstance().getCurrentUser().getUid();
          FirebaseDatabase.getInstance().getReference().child("Users").child(current_user_id).child("collegeId").addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot snapshot) {
                  currentCollegeId=snapshot.getValue(String.class);
                  setCollegeIdInSharedPref();
              }

              @Override
              public void onCancelled(@NonNull DatabaseError error) {

              }
          });
       
    }

    private void setCollegeIdInSharedPref() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("currentCollegeId", currentCollegeId);
        myEdit.commit();
    }

    private void showAlertDialogBox() {
        final AlertDialog ad =  new AlertDialog.Builder(this).create();
        ad.setTitle("Select Post Type");
//        ad.setMessage("Enter Your Email to recieve Reset link");
//        ad.setView(resetmail);
        ad.setButton(AlertDialog.BUTTON_NEUTRAL, "General Post",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity( new Intent(Start_activity.this,PostActivity.class));
                        dialogInterface.dismiss();
                    }
                });
        ad.setButton(AlertDialog.BUTTON_POSITIVE, "Job Opportunity",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity( new Intent(Start_activity.this,JobActivity.class));
                        dialogInterface.dismiss();
                    }
                });
        ad.setCanceledOnTouchOutside(false);

        ad.show();
    }
}