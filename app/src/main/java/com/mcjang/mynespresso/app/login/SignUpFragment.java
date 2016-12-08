package com.mcjang.mynespresso.app.login;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mcjang.mynespresso.FragmentChangeagle;
import com.mcjang.mynespresso.R;
import com.mcjang.mynespresso.db.mysql.Callback;
import com.mcjang.mynespresso.db.mysql.AsyncJobs;

public class SignUpFragment extends Fragment {

    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private EditText etUserEmail;
    private EditText etUserName;
    private EditText etUserPassword;
    private Button btnRegist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);;
        etUserEmail = (EditText) view.findViewById(R.id.etUserEmail);
        etUserName = (EditText) view.findViewById(R.id.etUserName);
        etUserPassword = (EditText) view.findViewById(R.id.etUserPassword);
        btnRegist = (Button) view.findViewById(R.id.btnRegist);

        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserModel user = new UserModel();
                user.setUserEmail(etUserEmail.getText().toString());
                user.setUserName(etUserName.getText().toString());
                user.setUserPassword(etUserPassword.getText().toString());

                AsyncJobs mysql = new AsyncJobs(getActivity());
                mysql.signUp(user, new Callback() {
                    @Override
                    public void callback(Context context) {
                        ((FragmentChangeagle)getActivity()).changeFragment(LoginFragment.newInstance(), false);
                    }
                });
            }
        });

        return view;
    }


}
