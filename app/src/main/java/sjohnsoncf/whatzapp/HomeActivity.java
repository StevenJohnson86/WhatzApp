package sjohnsoncf.whatzapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFbDb;
    private DatabaseReference mDbRef;

    @BindView(R.id.listView_convos)
    ListView mConvosListView;
    public ArrayList<String> mConvos;
    private TitleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        final Context ctx = this;

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //user is signed in


                } else {
                    //user is signed out
                    Intent loginIntent = new Intent(ctx, LoginActivity.class);
                    startActivity(loginIntent);
                }
            }
        };
        mConvos = new ArrayList<>();
        mFbDb = FirebaseDatabase.getInstance();
        mDbRef = mFbDb.getReference("Convos");
        mDbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildAdded: snapshot :" + dataSnapshot.child("title").getValue());
                mConvos.add(dataSnapshot.child("title").getValue().toString());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        loadConvos();
        mAdapter = new TitleAdapter(this, mConvos);
        mConvosListView.setAdapter(mAdapter);
        //TODO: need user object that contains convos they are a part of
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    @OnClick(R.id.btn_signOut)
    public void signOut(){
        mAuth.signOut();
    }

    @OnClick(R.id.btn_new_convo)
    public void newConvo(){
        Intent createConvoIntent = new Intent(this, CreateConvoActivity.class);
        startActivity(createConvoIntent);
    }

}
