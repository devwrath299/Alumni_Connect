package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instagramclone.model.College;
import com.example.instagramclone.model.Notification;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Singup_Activity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener  {
    private EditText username;
    private EditText name;
    private EditText email;
    private EditText password;
    private Button register;
    private TextView textView;
    private FirebaseAuth mAuth;
    ProgressDialog pd;

    List<String>college_list=new ArrayList<>();
    String currentCollegeName=null;
    List<College>collegeNewList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup_);
        username = findViewById(R.id.username);
        name = findViewById(R.id.Name);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        register = findViewById(R.id.registerbtn);
        textView = findViewById(R.id.textview);
        college_list.add("IIIT UNA");
        college_list.add("IIT Ropar");
        mAuth = FirebaseAuth.getInstance();
        Spinner spin = (Spinner) findViewById(R.id.spinnerCollege);
        spin.setOnItemSelectedListener(this);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Singup_Activity.this, Login_Activity.class);
                startActivity(intent);
                finish();

            }
        });
        String college_id=fetchCollegeid(currentCollegeName);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textUsername = username.getText().toString();
                String textName = name.getText().toString();
                String textEmail = email.getText().toString();
                String textPassword = password.getText().toString();
                if (TextUtils.isEmpty(textUsername) || TextUtils.isEmpty(textName) || TextUtils.isEmpty(textPassword) || TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(Singup_Activity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
                } else if (textPassword.length() < 6) {
                    Toast.makeText(Singup_Activity.this, "Password too Short", Toast.LENGTH_SHORT).show();
                }
                else if(currentCollegeName==null)
                {
                    Toast.makeText(Singup_Activity.this, "Please Select College", Toast.LENGTH_SHORT).show();
                }
                else {
                     pd = new ProgressDialog(Singup_Activity.this);
                    pd.setMessage("Registering User");
                    pd.setCancelable(false);
                    pd.show();
                    registeruser(textUsername, textName, textEmail, textPassword);
                }
            }


        });

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,college_list);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);


    }

    private String fetchCollegeid(final String currentCollegeName) {
        FirebaseDatabase.getInstance().getReference().child("college").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot np : snapshot.getChildren()){
                      collegeNewList.add(np.getValue(College.class));
                }

                for(College c:collegeNewList)
                {
                    if(c.getCollegeName()==currentCollegeName)
                        return c.getCollegeId();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void registeruser(final String Username, final String Name, final String Email, final String Password) {

        mAuth.createUserWithEmailAndPassword(Email,Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                String Uid = mAuth.getCurrentUser().getUid();
                HashMap<String,Object> map = new HashMap<>();
                map.put("Username",Username);
                map.put("Name",Name);
                map.put("Email",Email);
                map.put("Password",Password);
                map.put("UserId",Uid);
                map.put("bio","");
                map.put("ImageUrl","default");
                map.put("collegeId",getCollegeIdFromName(currentCollegeName));
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                myRef.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(map);
                Toast.makeText(Singup_Activity.this, "Registration Sucessfull", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                finish();
                startActivity( new Intent(Singup_Activity.this,Login_Activity.class));

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(Singup_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });



    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        currentCollegeName = college_list.get(i);
        Toast.makeText(this, college_list.get(i) + " Selected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}