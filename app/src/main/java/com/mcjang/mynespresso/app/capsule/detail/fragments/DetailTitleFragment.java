package com.mcjang.mynespresso.app.capsule.detail.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mcjang.mynespresso.Heartbeat;
import com.mcjang.mynespresso.R;
import com.mcjang.mynespresso.app.capsule.vo.Capsule;

public class DetailTitleFragment extends Fragment {
    private static final String ARG_PARAM1 = "capsule";

    private Capsule capsule;

    public DetailTitleFragment() {

    }

    public static DetailTitleFragment newInstance(Capsule capsule) {
        DetailTitleFragment fragment = new DetailTitleFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, capsule);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            capsule = (Capsule) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    private TextView tvDetailSubject;
    private TextView tvDetailDescription;
    private TextView tvProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_title, container, false);

        ((Heartbeat)getActivity()).heartbeat();

        tvDetailSubject = (TextView) view.findViewById(R.id.tvDetailSubject);
        tvDetailDescription = (TextView) view.findViewById(R.id.tvDetailDescription);
        tvProfile = (TextView) view.findViewById(R.id.tvProfile);

        tvDetailSubject.setText(capsule.getCapsuleDetail().getDetailSubject());
        tvDetailDescription.setText(capsule.getCapsuleDetail().getDetailDescription());
        tvProfile.setText("향 : " + capsule.getCapsuleDetail().getProfile());

        return view;
    }

}
