package com.mcjang.mynespresso.app.login;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mcjang.mynespresso.FragmentChangeagle;
import com.mcjang.mynespresso.R;
import com.mcjang.mynespresso.app.capsule.CapsuleFragment;
import com.mcjang.mynespresso.db.impl.CapsuleDao;
import com.mcjang.mynespresso.db.mysql.Callback;
import com.mcjang.mynespresso.db.mysql.AsyncJobs;
import com.mcjang.mynespresso.db.mysql.CallbackReturn;

public class LoginFragment extends Fragment {

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private EditText etUserEmail;
    private EditText etUserPassword;
    private Button btnLogin;
    private Button btnRegist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        CapsuleDao dao = new CapsuleDao(getActivity());
        UserModel user = dao.getUserModel();

        if ( user != null ) {
            ((FragmentChangeagle) getActivity()).changeFragment(CapsuleFragment.newInstance(), false);
        }

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        etUserEmail = (EditText) view.findViewById(R.id.etUserEmail);
        etUserPassword = (EditText) view.findViewById(R.id.etUserPassword);
        btnLogin = (Button) view.findViewById(R.id.btnLogin);
        btnRegist = (Button) view.findViewById(R.id.btnRegist);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final UserModel user = new UserModel();
                user.setUserEmail(etUserEmail.getText().toString());
                user.setUserPassword(etUserPassword.getText().toString());

                AsyncJobs mysql = new AsyncJobs(getActivity());
                mysql.loginGnuBoard(etUserEmail.getText().toString(), etUserPassword.getText().toString(), new CallbackReturn() {
                    @Override
                    public void callback(Context context, Object object) {
                        Toast.makeText(context, object.toString().length() + "", Toast.LENGTH_SHORT).show();
                        if (object.toString().length() == 0) {

                            UserModel user = new UserModel();
                            user.setUserEmail(etUserEmail.getText().toString());
                            user.setUserPassword(etUserPassword.getText().toString());
                            user.setUserName("...");

                            CapsuleDao dao = new CapsuleDao(getActivity());
                            dao.saveUserAccount(user);

                            ((FragmentChangeagle) getActivity()).changeFragment(CapsuleFragment.newInstance(), false);
                        }
                    }
                });

            }
        });

        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "회원가입 페이지로 이동합니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://mcjang.ipdisk.co.kr:8000/apps/gnuboard5/bbs/register.php"));
                startActivity(intent);
            }
        });

        return view;
    }

}
