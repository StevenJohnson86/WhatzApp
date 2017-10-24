package sjohnsoncf.whatzapp;

import android.content.Context;
import android.hardware.input.InputManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import Model.Convo;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;

public class ConvoDetailActivity extends AppCompatActivity {
    private static final String TAG = "ConvoDetailActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFbDb;
    private DatabaseReference mDbRef;
    private ArrayList<String> mMessages;
    //using ArrayAdapter temporarily, because I am just displaying text at this point
    private ArrayAdapter<String> mAdapter;

    @BindView(R.id.listView_convo_messages)
    ListView mMessagesListView;
    @BindView(R.id.editText_new_message)
    EditText mNewMessage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convo_detail);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //user logged in, do nothing?
                } else {
                    finish();
                }
            }
        };

        mMessages = new ArrayList<>();
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mMessages);
        mFbDb = FirebaseDatabase.getInstance();
        mDbRef = mFbDb.getReference("Convos").child(Convo.mCurrentConvoHash).child("messages");
        mDbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Log.d(TAG, "onChildAdded: dataSnapshot message: " + dataSnapshot.getValue().toString());
                mMessages.add(dataSnapshot.getValue().toString());
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
        mMessagesListView.setAdapter(mAdapter);


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

    @OnClick(R.id.btn_send_message)
    public void sendMessage(){
        String msg = mNewMessage.getText().toString();
        mDbRef.push().setValue(msg);
        mNewMessage.setText("");
        mNewMessage.clearFocus();
        //dismiss keyboard
    }

    @OnFocusChange(R.id.editText_new_message)
    public void onFocusChange(boolean focus, View view){
        Toast.makeText(this, "Focus boolean: " + focus, Toast.LENGTH_SHORT).show();
        if(!focus) {
        InputMethodManager inputmngr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputmngr.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
