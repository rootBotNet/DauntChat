package za.co.dauntless.dauntchat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_Activity extends AppCompatActivity {

    //Widgets.
    EditText username, password;
    Button signInBtn, registerBtn;

    //Firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //Checking for user existence: Saving the current user.
        if (firebaseUser != null) {
            Intent intent = new Intent(Login_Activity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        //Initialize Widgets
        //Get information from view.
        //Set view information to Widgets.
        username = findViewById(R.id.usernameLogin);
        password = findViewById(R.id.passwordLogin);
        signInBtn = findViewById(R.id.signInbuttonLogin);
        registerBtn = findViewById(R.id.signUpButtonLogin);

        //Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        //Sign Up Button
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Activity.this, RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        //Adding Event Listener to Button Register
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameText = username.getText().toString();
                String passwordText = password.getText().toString();

                if (TextUtils.isEmpty(usernameText) || TextUtils.isEmpty(passwordText)) {
                    Toast.makeText(Login_Activity.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(usernameText, passwordText);
                }
            }
        });
    }

    //User login
    private void loginUser(String usernameText, String passwordText) {
        firebaseAuth.signInWithEmailAndPassword(usernameText, passwordText)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Login_Activity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}