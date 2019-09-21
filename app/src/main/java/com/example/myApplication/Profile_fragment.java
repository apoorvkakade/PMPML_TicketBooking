package com.example.myApplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class Profile_fragment extends Fragment {


    Profile_fragment()
    {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v=inflater.inflate(R.layout.fragment_profile_fragment,container,false);

        Button logout_btn=(Button)v.findViewById(R.id.logout_button);


        FirebaseFirestore db=FirebaseFirestore.getInstance();
        DocumentReference docref=db.collection("User").document(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
        docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful())
                {

                    DocumentSnapshot document=task.getResult();
                    if(document.exists())
                    {
                        TextView name=(TextView)v.findViewById(R.id.name_value);
                        TextView email=(TextView)v.findViewById(R.id.email_value);
                        TextView tokens=(TextView)v.findViewById(R.id.token_value);
                        TextView phone=(TextView)v.findViewById(R.id.phone_value);
                        name.setText(document.getString("name"));
                        email.setText(document.getString("email"));

                        int tokens1=document.getLong("tokens").intValue();

                        tokens.setText(Integer.toString(tokens1));

                        phone.setText(document.getId());
                    }
                }
                else
                {
                    TextView name=(TextView)v.findViewById(R.id.name_value);
                    TextView email=(TextView)v.findViewById(R.id.email_value);
                    TextView tokens=(TextView)v.findViewById(R.id.token_value);
                    TextView phone=(TextView)v.findViewById(R.id.phone_value);
                    name.setText("Fetching from db");
                    email.setText(" ");

//                    int tokens1=document.getLong("tokens").intValue();

                    tokens.setText("");

                    phone.setText("");
                }
            }
        });
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });


        //return inflater.inflate(R.layout.fragment_profile_fragment, container, false);
        return v;
    }

    public void logout()
    {
        FirebaseAuth firebaseAuth;
        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null){
                    //Do anything here which needs to be done after signout is complete
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }
                else {
                }
            }
        };

//Init and attach
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.addAuthStateListener(authStateListener);

//Call signOut()
        firebaseAuth.signOut();



    }




}
