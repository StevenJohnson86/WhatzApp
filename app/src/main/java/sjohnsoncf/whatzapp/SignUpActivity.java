package sjohnsoncf.whatzapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @BindView(R.id.btn_signup)
    Button mBtnSignup;
    @BindView(R.id.signup_email)
    EditText mSignupEmail;
    @BindView(R.id.signup_password)
    EditText mSignupPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //user is signed in
                } else {
                    //user is signed out
                }
            }
        };

        ButterKnife.bind(this);
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

    @OnClick(R.id.btn_signup)
    public void userSignup(){

        createAccount(mSignupEmail.getText().toString(), mSignupPassword.getText().toString());
    }
    public void createAccount(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //task is successful?
                if(task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "auth success", Toast.LENGTH_LONG).show();
                }
                if(!task.isSuccessful()){
                    //task failed (auth failed)
                    Toast.makeText(SignUpActivity.this, "auth failure", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
