package com.egormoroz.schooly.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.egormoroz.schooly.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

import static com.egormoroz.schooly.ui.main.Authorization.setCurrentFragment;

public class RegisrtationstartFragment extends Fragment {
    public static RegisrtationstartFragment newInstance(){return new RegisrtationstartFragment();}
    Button Registration;
    Button Enter;
    GoogleSignInOptions gso;
    GoogleSignInClient signInClient;
    FirebaseAuth AuthBase;
    sendAuthData SendData;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_reg, container, false);
        BottomNavigationView bnv = getActivity().findViewById(R.id.bottomNavigationView);
        bnv.setVisibility(bnv.GONE);
        AppBarLayout abl = getActivity().findViewById(R.id.AppBarLayout);
        abl.setVisibility(abl.GONE);


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        signInClient = GoogleSignIn.getClient(getActivity(), gso);
        AuthBase = FirebaseAuth.getInstance();


        Registration = root.findViewById(R.id.registr);
        Enter = root.findViewById(R.id.enter);
        Registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistrationMethod(view);
            }
        });
        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterMethod(view);
            }
        });


        return root;
    }
    public interface sendAuthData{
        void sendData(GoogleSignInOptions gso, GoogleSignInClient signInClient, FirebaseAuth authBase, String type);
    }
    void RegistrationMethod(View view){
        setCurrentFragment(RegFragment.newInstance());
        SendData.sendData(gso, signInClient, AuthBase, "RegistrationFragment");
    }
    void EnterMethod(View view){
        setCurrentFragment(EnterFragment.newInstance());
        SendData.sendData(gso, signInClient, AuthBase, "EnterFragment");
    }
    ////// I have no fucking clue what i am doing !!!!!!!
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            SendData = (sendAuthData) getActivity();
        }
        catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }
    public void setCurrentFragment(Fragment fragment) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame, fragment);
        ft.commit();
    }
}
