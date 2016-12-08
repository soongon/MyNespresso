package com.mcjang.mynespresso.app.capsule.detail.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mcjang.mynespresso.Heartbeat;
import com.mcjang.mynespresso.R;
import com.mcjang.mynespresso.app.capsule.vo.Capsule;
import com.mcjang.mynespresso.app.capsule.vo.Review;
import com.mcjang.mynespresso.db.mysql.AsyncJobs;
import com.mcjang.mynespresso.db.mysql.CallbackReturn;

import java.util.ArrayList;
import java.util.List;

public class DetailCommentFragment extends Fragment {
    private static final String ARG_PARAM1 = "capsule";

    private Capsule capsule;

    public DetailCommentFragment() {

    }

    public static DetailCommentFragment newInstance(Capsule capsule) {
        DetailCommentFragment fragment = new DetailCommentFragment();
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

    private ListView lvCommentList;
    private List<Review> reviews;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_comment, container, false);
        lvCommentList = (ListView) view.findViewById(R.id.lvCommentList);
        ((Heartbeat)getActivity()).heartbeat();


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        reviews = new ArrayList<Review>();
        AsyncJobs jobs = new AsyncJobs(getActivity());
        jobs.getReview(capsule.getCapsuleDetail().getReviewUrl(), new CallbackReturn() {
            @Override
            public void callback(final Context context, Object object) {
                reviews = (List<Review>) object;

                lvCommentList.setAdapter(new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return reviews.size();
                    }

                    @Override
                    public Object getItem(int position) {
                        return reviews.get(position);
                    }

                    @Override
                    public long getItemId(int position) {
                        return position;
                    }

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        Holder holder = null;
                        if(convertView == null) {
                            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            convertView = layoutInflater.inflate(R.layout.item_comment, parent, false);
                            holder = new Holder();
                            holder.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
                            holder.rbRating = (RatingBar) convertView.findViewById(R.id.rbRating);
                            holder.tvComment = (TextView) convertView.findViewById(R.id.tvComment);
                            holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
                            convertView.setTag(holder);
                        }
                        else {
                            holder = (Holder) convertView.getTag();
                        }

                        Review review = (Review) getItem(position);
                        holder.tvUserName.setText(review.getName());
                        holder.tvComment.setText(review.getComment());
                        holder.tvDate.setText(review.getDate());
                        holder.rbRating.setRating(review.getPoint());

                        return convertView;
                    }
                });
            }

            class Holder {
                public TextView tvUserName;
                public RatingBar rbRating;
                public TextView tvComment;
                public TextView tvDate;
            }
        });
    }
}
