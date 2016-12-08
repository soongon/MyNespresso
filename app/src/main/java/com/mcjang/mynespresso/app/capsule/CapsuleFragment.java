package com.mcjang.mynespresso.app.capsule;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mcjang.mynespresso.Heartbeat;
import com.mcjang.mynespresso.R;
import com.mcjang.mynespresso.app.capsule.vo.Capsule;
import com.mcjang.mynespresso.app.capsule.vo.CapsuleDetail;
import com.mcjang.mynespresso.app.capsule.vo.CapsuleList;
import com.mcjang.mynespresso.app.capsule.vo.CapsuleRange;
import com.mcjang.mynespresso.app.capsule.vo.Capsules;
import com.mcjang.mynespresso.db.mysql.CallbackReturn;
import com.mcjang.mynespresso.db.mysql.AsyncJobs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CapsuleFragment extends Fragment {
    public CapsuleFragment() {
        // Required empty public constructor
    }
    public static CapsuleFragment newInstance() {
        CapsuleFragment fragment = new CapsuleFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private ListView lvCapsules;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_capsule, container, false);
        lvCapsules = (ListView) view.findViewById(R.id.lvCapsules);

        ((Heartbeat)getActivity()).heartbeat();

        AsyncJobs mysql = new AsyncJobs(getActivity());
        mysql.getItemInfo(new CallbackReturn() {
            @Override
            public void callback(Context context, Object object) {
                lvCapsules.setAdapter(new CapsuleAdapter( (Capsules) object ));
            }
        });
        return view;
    }

    private class CapsuleAdapter extends BaseAdapter {

        private Capsules capsules;
        private List<Capsule> capsuleList = null;

        public CapsuleAdapter(Capsules capsules) {
            this.capsules = capsules;
            capsuleList = new ArrayList<Capsule>();

            CapsuleList capsuleRange = null;
            for (int i = 0; i < this.capsules.getCapsuleRange().size(); i++) {
                capsuleRange = this.capsules.getCapsuleRange().get(i);

                Capsule cap = new Capsule();
                CapsuleRange range = new CapsuleRange();
                range.setRangeCode(capsuleRange.getRangeCode());
                range.setRangeName(capsuleRange.getRangeName());
                cap.setCapsuleRange(range);
                capsuleList.add(cap);

                for (int j = 0; j < capsuleRange.getCapsuleList().size(); j++) {
                    capsuleList.add(capsuleRange.getCapsuleList().get(j));
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            Capsule cap = (Capsule) getItem(position);

            if ( cap.getName() == null || cap.getName().length() == 0 ) {
                return -1;
            }

            return 0;
        }

        @Override
        public int getCount() {
            return capsuleList.size();
        }

        @Override
        public Object getItem(int position) {
            return capsuleList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        private CapsuleHolder holder = null;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            int viewType = getItemViewType(position);

            File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), getContext().getString(R.string.localPath));

            if(convertView == null) {
                holder = new CapsuleHolder();
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                if ( viewType == 0 ) {
                    convertView = inflater.inflate(R.layout.item_capsule, parent, false);
                    holder.ivCapsuleImage = (ImageView) convertView.findViewById(R.id.ivCapsuleImage);
                    holder.tvCapsuleName = (TextView) convertView.findViewById(R.id.tvCapsuleName);
                    holder.tvCapsuleNameEng = (TextView) convertView.findViewById(R.id.tvCapsuleNameEng);
                    holder.ivCapsuleCupSize0 = (ImageView) convertView.findViewById(R.id.ivCapsuleCupSize0);
                    holder.ivCapsuleCupSize1 = (ImageView) convertView.findViewById(R.id.ivCapsuleCupSize1);
                    holder.ivCapsuleCupSize2 = (ImageView) convertView.findViewById(R.id.ivCapsuleCupSize2);
                    holder.ivCapsuleCupSize3 = (ImageView) convertView.findViewById(R.id.ivCapsuleCupSize3);
                    holder.tvCapsuleCupSize0 = (TextView) convertView.findViewById(R.id.tvCapsuleCupSize0);
                    holder.tvCapsuleCupSize1 = (TextView) convertView.findViewById(R.id.tvCapsuleCupSize1);
                    holder.tvCapsuleCupSize2 = (TextView) convertView.findViewById(R.id.tvCapsuleCupSize2);
                    holder.tvCapsuleCupSize3 = (TextView) convertView.findViewById(R.id.tvCapsuleCupSize3);
                    holder.tvIntensity = (TextView) convertView.findViewById(R.id.tvIntensity);
                }
                else {
                    convertView = inflater.inflate(R.layout.item_title, parent, false);
                    holder.tvCapsuleRange = (TextView) convertView.findViewById(R.id.tvCapsuleRange);
                }

                convertView.setTag(holder);
            }
            else {
                if ( viewType == 0 ) {
                    holder = (CapsuleHolder) convertView.getTag();
                }
            }

            final Capsule capsules = (Capsule) getItem(position);
            if ( viewType == 0 ) {
                CapsuleDetail capsuleDetail = capsules.getCapsuleDetail();

//            holder.tvCapsuleName.setText(capsules.getName() + "\n" + capsules.getCode() );
                holder.tvCapsuleName.setText(capsules.getName());
                holder.tvCapsuleNameEng.setText(capsules.getNesOAName());

                File imgFile = new File(f.getAbsolutePath() + File.separator + capsules.getMediaQuickOrder().getRealfilename());
                if (imgFile.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    holder.ivCapsuleImage.setImageBitmap(myBitmap);
                }

                holder.ivCapsuleCupSize0.setVisibility(View.GONE);
                holder.tvCapsuleCupSize0.setVisibility(View.GONE);
                holder.ivCapsuleCupSize1.setVisibility(View.GONE);
                holder.tvCapsuleCupSize1.setVisibility(View.GONE);
                holder.ivCapsuleCupSize2.setVisibility(View.GONE);
                holder.tvCapsuleCupSize2.setVisibility(View.GONE);
                holder.ivCapsuleCupSize3.setVisibility(View.GONE);
                holder.tvCapsuleCupSize3.setVisibility(View.GONE);

                if (capsuleDetail != null && capsuleDetail.getCupSize() != null) {
                    //holder.llCupSizeLayout.removeAllViews();

                    List<String> cupsSize = capsuleDetail.getCupSize();
                    String cupSize = "";

                    for (int i = 0; i < cupsSize.size(); i++) {
                        cupSize = cupsSize.get(i);

                        Log.d("NESPRESSO", cupSize + "-=-=-=" + capsules.getName() + " / " + capsuleDetail.getProfile());
                        if (cupSize.endsWith("레시피")) {
                            if (i == 0) {
                                holder.ivCapsuleCupSize0.setImageDrawable(getResources().getDrawable(R.drawable.receip));
                                holder.tvCapsuleCupSize0.setText(cupSize);
                                holder.ivCapsuleCupSize0.setVisibility(View.VISIBLE);
                                holder.tvCapsuleCupSize0.setVisibility(View.VISIBLE);
                                holder.ivCapsuleCupSize1.setVisibility(View.GONE);
                                holder.tvCapsuleCupSize1.setVisibility(View.GONE);
                                holder.ivCapsuleCupSize2.setVisibility(View.GONE);
                                holder.tvCapsuleCupSize2.setVisibility(View.GONE);
                                holder.ivCapsuleCupSize3.setVisibility(View.GONE);
                                holder.tvCapsuleCupSize3.setVisibility(View.GONE);
                            } else if (i == 1) {
                                holder.ivCapsuleCupSize1.setImageDrawable(getResources().getDrawable(R.drawable.cup_size_40));
                                holder.tvCapsuleCupSize1.setText(cupSize);
                                holder.ivCapsuleCupSize1.setVisibility(View.VISIBLE);
                                holder.tvCapsuleCupSize1.setVisibility(View.VISIBLE);
                                holder.ivCapsuleCupSize2.setVisibility(View.GONE);
                                holder.tvCapsuleCupSize2.setVisibility(View.GONE);
                                holder.ivCapsuleCupSize3.setVisibility(View.GONE);
                                holder.tvCapsuleCupSize3.setVisibility(View.GONE);
                            } else if (i == 2) {
                                holder.ivCapsuleCupSize2.setImageDrawable(getResources().getDrawable(R.drawable.cup_size_40));
                                holder.tvCapsuleCupSize2.setText(cupSize);
                                holder.ivCapsuleCupSize2.setVisibility(View.VISIBLE);
                                holder.tvCapsuleCupSize2.setVisibility(View.VISIBLE);
                                holder.ivCapsuleCupSize3.setVisibility(View.GONE);
                                holder.tvCapsuleCupSize3.setVisibility(View.GONE);
                            } else if (i == 3) {
                                holder.ivCapsuleCupSize3.setImageDrawable(getResources().getDrawable(R.drawable.cup_size_40));
                                holder.tvCapsuleCupSize3.setText(cupSize);
                                holder.ivCapsuleCupSize3.setVisibility(View.VISIBLE);
                                holder.tvCapsuleCupSize3.setVisibility(View.VISIBLE);
                            }
                        } else if (cupSize.startsWith("40")) {
                            if (i == 0) {
                                holder.ivCapsuleCupSize0.setImageDrawable(getResources().getDrawable(R.drawable.receip));
                                holder.tvCapsuleCupSize0.setText(cupSize);
                                holder.ivCapsuleCupSize0.setVisibility(View.VISIBLE);
                                holder.tvCapsuleCupSize0.setVisibility(View.VISIBLE);
                                holder.ivCapsuleCupSize1.setVisibility(View.GONE);
                                holder.tvCapsuleCupSize1.setVisibility(View.GONE);
                                holder.ivCapsuleCupSize2.setVisibility(View.GONE);
                                holder.tvCapsuleCupSize2.setVisibility(View.GONE);
                                holder.ivCapsuleCupSize3.setVisibility(View.GONE);
                                holder.tvCapsuleCupSize3.setVisibility(View.GONE);
                            } else if (i == 1) {
                                holder.ivCapsuleCupSize1.setImageDrawable(getResources().getDrawable(R.drawable.cup_size_40));
                                holder.tvCapsuleCupSize1.setText(cupSize);
                                holder.ivCapsuleCupSize1.setVisibility(View.VISIBLE);
                                holder.tvCapsuleCupSize1.setVisibility(View.VISIBLE);
                                holder.ivCapsuleCupSize2.setVisibility(View.GONE);
                                holder.tvCapsuleCupSize2.setVisibility(View.GONE);
                                holder.ivCapsuleCupSize3.setVisibility(View.GONE);
                                holder.tvCapsuleCupSize3.setVisibility(View.GONE);
                            } else if (i == 2) {
                                holder.ivCapsuleCupSize2.setImageDrawable(getResources().getDrawable(R.drawable.cup_size_40));
                                holder.tvCapsuleCupSize2.setText(cupSize);
                                holder.ivCapsuleCupSize2.setVisibility(View.VISIBLE);
                                holder.tvCapsuleCupSize2.setVisibility(View.VISIBLE);
                                holder.ivCapsuleCupSize3.setVisibility(View.GONE);
                                holder.tvCapsuleCupSize3.setVisibility(View.GONE);
                            } else if (i == 3) {
                                holder.ivCapsuleCupSize3.setImageDrawable(getResources().getDrawable(R.drawable.cup_size_40));
                                holder.tvCapsuleCupSize3.setText(cupSize);
                                holder.ivCapsuleCupSize3.setVisibility(View.VISIBLE);
                                holder.tvCapsuleCupSize3.setVisibility(View.VISIBLE);
                            }
                        } else if (cupSize.startsWith("25")) {
                            if (i == 0) {
                                holder.ivCapsuleCupSize0.setImageDrawable(getResources().getDrawable(R.drawable.receip));
                                holder.tvCapsuleCupSize0.setText(cupSize);
                                holder.ivCapsuleCupSize0.setVisibility(View.VISIBLE);
                                holder.tvCapsuleCupSize0.setVisibility(View.VISIBLE);
                                holder.ivCapsuleCupSize1.setVisibility(View.GONE);
                                holder.tvCapsuleCupSize1.setVisibility(View.GONE);
                                holder.ivCapsuleCupSize2.setVisibility(View.GONE);
                                holder.tvCapsuleCupSize2.setVisibility(View.GONE);
                                holder.ivCapsuleCupSize3.setVisibility(View.GONE);
                                holder.tvCapsuleCupSize3.setVisibility(View.GONE);
                            } else if (i == 1) {
                                holder.ivCapsuleCupSize1.setImageDrawable(getResources().getDrawable(R.drawable.cup_size_25));
                                holder.tvCapsuleCupSize1.setText(cupSize);
                                holder.ivCapsuleCupSize1.setVisibility(View.VISIBLE);
                                holder.tvCapsuleCupSize1.setVisibility(View.VISIBLE);
                                holder.ivCapsuleCupSize2.setVisibility(View.GONE);
                                holder.tvCapsuleCupSize2.setVisibility(View.GONE);
                                holder.ivCapsuleCupSize3.setVisibility(View.GONE);
                                holder.tvCapsuleCupSize3.setVisibility(View.GONE);
                            } else if (i == 2) {
                                holder.ivCapsuleCupSize2.setImageDrawable(getResources().getDrawable(R.drawable.cup_size_25));
                                holder.tvCapsuleCupSize2.setText(cupSize);
                                holder.ivCapsuleCupSize2.setVisibility(View.VISIBLE);
                                holder.tvCapsuleCupSize2.setVisibility(View.VISIBLE);
                                holder.ivCapsuleCupSize3.setVisibility(View.GONE);
                                holder.tvCapsuleCupSize3.setVisibility(View.GONE);
                            } else if (i == 3) {
                                holder.ivCapsuleCupSize3.setImageDrawable(getResources().getDrawable(R.drawable.cup_size_25));
                                holder.tvCapsuleCupSize3.setText(cupSize);
                                holder.ivCapsuleCupSize3.setVisibility(View.VISIBLE);
                                holder.tvCapsuleCupSize3.setVisibility(View.VISIBLE);
                            }
                        } else if (cupSize.startsWith("110")) {
                            if (i == 0) {
                                holder.ivCapsuleCupSize0.setImageDrawable(getResources().getDrawable(R.drawable.receip));
                                holder.tvCapsuleCupSize0.setText(cupSize);
                                holder.ivCapsuleCupSize0.setVisibility(View.VISIBLE);
                                holder.tvCapsuleCupSize0.setVisibility(View.VISIBLE);
                                holder.ivCapsuleCupSize1.setVisibility(View.GONE);
                                holder.tvCapsuleCupSize1.setVisibility(View.GONE);
                                holder.ivCapsuleCupSize2.setVisibility(View.GONE);
                                holder.tvCapsuleCupSize2.setVisibility(View.GONE);
                                holder.ivCapsuleCupSize3.setVisibility(View.GONE);
                                holder.tvCapsuleCupSize3.setVisibility(View.GONE);
                            } else if (i == 1) {
                                holder.ivCapsuleCupSize1.setImageDrawable(getResources().getDrawable(R.drawable.cup_size_110));
                                holder.tvCapsuleCupSize1.setText(cupSize);
                                holder.ivCapsuleCupSize1.setVisibility(View.VISIBLE);
                                holder.tvCapsuleCupSize1.setVisibility(View.VISIBLE);
                                holder.ivCapsuleCupSize2.setVisibility(View.GONE);
                                holder.tvCapsuleCupSize2.setVisibility(View.GONE);
                                holder.ivCapsuleCupSize3.setVisibility(View.GONE);
                                holder.tvCapsuleCupSize3.setVisibility(View.GONE);
                            } else if (i == 2) {
                                holder.ivCapsuleCupSize2.setImageDrawable(getResources().getDrawable(R.drawable.cup_size_110));
                                holder.tvCapsuleCupSize2.setText(cupSize);
                                holder.ivCapsuleCupSize2.setVisibility(View.VISIBLE);
                                holder.tvCapsuleCupSize2.setVisibility(View.VISIBLE);
                                holder.ivCapsuleCupSize3.setVisibility(View.GONE);
                                holder.tvCapsuleCupSize3.setVisibility(View.GONE);
                            } else if (i == 3) {
                                holder.ivCapsuleCupSize3.setImageDrawable(getResources().getDrawable(R.drawable.cup_size_110));
                                holder.tvCapsuleCupSize3.setText(cupSize);
                                holder.ivCapsuleCupSize3.setVisibility(View.VISIBLE);
                                holder.tvCapsuleCupSize3.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }

                if (capsuleDetail != null && capsuleDetail.getIntensity() != null && capsuleDetail.getIntensity().length() > 0) {
                    holder.tvIntensity.setText("강도(Intensity) : " + capsuleDetail.getIntensity());
                } else {
                    holder.tvIntensity.setText("");
                }

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), CapsuleDetailActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("capsule", capsules);
                        startActivity(intent);
                    }
                });

            }
            else {
                holder.tvCapsuleRange.setText(capsules.getCapsuleRange().getRangeName());
            }

            return convertView;
        }
    }

    private class CapsuleHolder {
        public ImageView ivCapsuleImage;
        public TextView tvCapsuleName;
        public TextView tvCapsuleNameEng;
        public ImageView ivCapsuleCupSize0;
        public TextView tvCapsuleCupSize0;
        public ImageView ivCapsuleCupSize1;
        public TextView tvCapsuleCupSize1;
        public ImageView ivCapsuleCupSize2;
        public TextView tvCapsuleCupSize2;
        public ImageView ivCapsuleCupSize3;
        public TextView tvCapsuleCupSize3;
        public TextView tvIntensity;
        public TextView tvCapsuleRange;
    }
}
