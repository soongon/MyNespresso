package com.mcjang.mynespresso.app.capsule.detail.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mcjang.mynespresso.Heartbeat;
import com.mcjang.mynespresso.R;
import com.mcjang.mynespresso.app.capsule.vo.Capsule;

public class DetailCapsuleFragment extends Fragment {
    private static final String ARG_PARAM1 = "capsule";

    private Capsule capsule;

    public DetailCapsuleFragment() {

    }

    public static DetailCapsuleFragment newInstance(Capsule capsule) {
        DetailCapsuleFragment fragment = new DetailCapsuleFragment();
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

    private TextView tvOriginDetail;
    private TextView tvRoasting;
    private TextView tvCoffeAromaProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_capsule, container, false);
        ((Heartbeat)getActivity()).heartbeat();

        tvOriginDetail = (TextView) view.findViewById(R.id.tvOriginDetail);
        tvRoasting = (TextView) view.findViewById(R.id.tvRoasting);
        tvCoffeAromaProfile = (TextView) view.findViewById(R.id.tvCoffeAromaProfile);

        tvOriginDetail.setText("원산지 : " + capsule.getCapsuleDetail().getOriginDetail());
        tvRoasting.setText("로스팅 : " + capsule.getCapsuleDetail().getRoasting());
        tvCoffeAromaProfile.setText("커피 아로파 프로필 : " + capsule.getCapsuleDetail().getCoffeAromaProfile());

        return view;
    }

}
