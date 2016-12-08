package com.mcjang.mynespresso.app.capsule.detail.fragments;

import android.content.Context;
import android.media.Rating;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mcjang.mynespresso.Heartbeat;
import com.mcjang.mynespresso.R;
import com.mcjang.mynespresso.app.capsule.CapsuleDetailActivity;
import com.mcjang.mynespresso.app.capsule.vo.Capsule;
import com.mcjang.mynespresso.app.capsule.vo.Review;
import com.mcjang.mynespresso.db.mysql.AsyncJobs;
import com.mcjang.mynespresso.db.mysql.Callback;
import com.mcjang.mynespresso.db.mysql.CallbackReturn;
import com.mcjang.mynespresso.util.HttpClient;

import java.util.ArrayList;
import java.util.List;

public class DetailCommentWriteFragment extends Fragment {
    private static final String ARG_PARAM1 = "capsule";

    private Capsule capsule;

    public DetailCommentWriteFragment() {

    }

    public static DetailCommentWriteFragment newInstance(Capsule capsule) {
        DetailCommentWriteFragment fragment = new DetailCommentWriteFragment();
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

    private RatingBar rbRating;
    private EditText etComment;
    private Button btnDone;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_comment_write, container, false);

        ((Heartbeat)getActivity()).heartbeat();

        rbRating = (RatingBar) view.findViewById(R.id.rbRating);
        etComment = (EditText) view.findViewById(R.id.etComment);
        btnDone = (Button) view.findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = capsule.getCapsuleDetail().getReviewUrl();
                id = id.substring(id.lastIndexOf("=") + 1);

                String comment = "점수-" + rbRating.getRating();
                comment += ":;:;:내용-" + etComment.getText().toString();

                AsyncJobs jobs = new AsyncJobs(getActivity());
                jobs.writeComment(id, comment, new CallbackReturn() {
                    @Override
                    public void callback(Context context, Object object) {
                        if ( object.toString().contains("오류") ) {
                            Toast.makeText(getActivity(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            etComment.setText("");
                            InputMethodManager imm= (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(etComment.getWindowToken(), 0);
                            ((CapsuleDetailActivity)getActivity()).noti();
                            ((CapsuleDetailActivity)getActivity()).pager.setCurrentItem(((CapsuleDetailActivity)getActivity()).FRAGMENT_ITEM_2);
                        }
                    }
                });

            }
        });

        return view;
    }

}
