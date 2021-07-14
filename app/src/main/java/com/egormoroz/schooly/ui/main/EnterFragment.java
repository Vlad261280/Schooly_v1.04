package com.egormoroz.schooly.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.egormoroz.schooly.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.concurrent.Executor;

import static com.egormoroz.schooly.ui.main.Authorization.EnterThrowGoogle;
import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;

public class EnterFragment extends Fragment {
    FirebaseAuth AuthBase;
    public static EnterFragment newInstance(){return new EnterFragment();}
    EditText phoneEditText;
    EditText passwordEditText;
    GoogleSignInOptions gso;
    GoogleSignInClient signInClient;
    RelativeLayout GoogleEnter;
    int RC_SIGN_IN = 175;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_enter, container, false);
        BottomNavigationView bnv = getActivity().findViewById(R.id.bottomNavigationView);
        bnv.setVisibility(bnv.GONE);
        AppBarLayout abl = getActivity().findViewById(R.id.AppBarLayout);
        abl.setVisibility(abl.GONE);

        //////Testing////
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        signInClient = GoogleSignIn.getClient(getActivity(), gso);
        AuthBase = FirebaseAuth.getInstance();
        ////////////////

        phoneEditText = root.findViewById(R.id.egitnick);
        passwordEditText = root.findViewById(R.id.editpassworgenter);
        GoogleEnter = root.findViewById(R.id.GoogleEnter);
        GoogleEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInGoogle();
            }
        });

        return root;
    }
    private void signInGoogle() {
        Intent signInIntent = signInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("#####", "firebaseAuthWithGoogle:" + account.getId());
                EnterThrowGoogle(account.getIdToken(), AuthBase, getActivity(), getActivity().getSupportFragmentManager());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("#####", "Google sign in failed", e);
            }
        }
    }
    public void getData(GoogleSignInOptions gso, GoogleSignInClient signInClient, FirebaseAuth authBase){
        this.gso = gso;
        this.signInClient = signInClient;
        this.AuthBase = authBase;
    }
}
