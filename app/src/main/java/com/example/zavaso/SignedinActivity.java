package com.example.zavaso;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SignedinActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FloatingActionButton addEvent;
    private DatabaseReference mDatabaseReference;
    private String userId;
    private Event mEvent;
    private List<Event> eventList;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private EventAdapter mEventAdapter;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signedin_activity);
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
        eventList=new ArrayList<>();
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addEvent=(FloatingActionButton)findViewById(R.id.add_event);
        mEventAdapter=new EventAdapter(getApplicationContext(),eventList);
        recyclerView.setAdapter(mEventAdapter);
        mLinearLayoutManager=new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventFragment eventFragment=new EventFragment();
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.signedIn_activity,eventFragment).addToBackStack("null").commit();
            }
        });
        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("event").child(userId);
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot:snapshot.getChildren()){
                    String key=childSnapshot.getKey();
                    mEvent=snapshot.child(key).getValue(Event.class);
                    Log.d("myTag",mEvent.getDate());
                    eventList.add(mEvent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Operation Failed"+error.getCode(),Toast.LENGTH_LONG).show();
            }
        });

    }

}