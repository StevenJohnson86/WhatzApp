package sjohnsoncf.whatzapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateConvoActivity extends AppCompatActivity {
    //TODO: REMOVE THIS ACTY AND ADD MEANINGFUL CONVO CREATION IN HOMEACTY
    //ESPECIALLY IF ALL IT ENTAILS IS CREATING A CONVO TITLE
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFbDb;
    private DatabaseReference mDbRef;
    @BindView(R.id.create_convo_title)
    EditText mConvoTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_convo);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    //signed in
                } else {
                    //not signed in
                    finish();
                }
            }
        };
        mFbDb = FirebaseDatabase.getInstance();
        mDbRef = mFbDb.getReference("Convos");
        //listeners?

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

    @OnClick(R.id.btn_create_convo_submit)
    public void createConvo(){
        String title = mConvoTitle.getText().toString();
        if(title.length() < 3){
            Toast.makeText(this, "Title must be 3 or more characters. Try again.", Toast.LENGTH_LONG).show();
            return;
        }
        //create firebase record?
        mDbRef.child(String.valueOf(title.hashCode())).child("title").setValue(title);
        //send user back to home?
        finish();
    }
}
