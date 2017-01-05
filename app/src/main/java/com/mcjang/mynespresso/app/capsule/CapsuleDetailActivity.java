package com.mcjang.mynespresso.app.capsule;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.mcjang.mynespresso.Heartbeat;
import com.mcjang.mynespresso.MainActivity;
import com.mcjang.mynespresso.R;
import com.mcjang.mynespresso.app.capsule.detail.fragments.DetailCapsuleFragment;
import com.mcjang.mynespresso.app.capsule.detail.fragments.DetailCommentFragment;
import com.mcjang.mynespresso.app.capsule.detail.fragments.DetailCommentWriteFragment;
import com.mcjang.mynespresso.app.capsule.detail.fragments.DetailTitleFragment;
import com.mcjang.mynespresso.app.capsule.vo.Capsule;
import com.mcjang.mynespresso.app.login.UserModel;
import com.mcjang.mynespresso.db.impl.CapsuleDao;
import com.mcjang.mynespresso.db.mysql.AsyncJobs;
import com.mcjang.mynespresso.db.mysql.CallbackReturn;

import java.io.File;
import java.util.List;

public class CapsuleDetailActivity extends AppCompatActivity implements Heartbeat {

    private ImageView ivBigImage;

    private TextView tvCapsuleName;
    private TextView tvCapsuleNameEng;
    private ImageView ivCapsuleCupSize0;
    private TextView tvCapsuleCupSize0;
    private ImageView ivCapsuleCupSize1;
    private TextView tvCapsuleCupSize1;
    private ImageView ivCapsuleCupSize2;
    private TextView tvCapsuleCupSize2;
    private ImageView ivCapsuleCupSize3;
    private TextView tvCapsuleCupSize3;
    private TextView tvIntensity;
    private TextView tvOrigin;
    private TextView tvProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capsule_detail);

//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        ivBigImage = (ImageView) findViewById(R.id.ivBigImage);
        tvCapsuleName = (TextView) findViewById(R.id.tvCapsuleName);
        tvCapsuleNameEng = (TextView) findViewById(R.id.tvCapsuleNameEng);
        ivCapsuleCupSize0 = (ImageView) findViewById(R.id.ivCapsuleCupSize0);
        ivCapsuleCupSize1 = (ImageView) findViewById(R.id.ivCapsuleCupSize1);
        ivCapsuleCupSize2 = (ImageView) findViewById(R.id.ivCapsuleCupSize2);
        ivCapsuleCupSize3 = (ImageView) findViewById(R.id.ivCapsuleCupSize3);
        tvCapsuleCupSize0 = (TextView) findViewById(R.id.tvCapsuleCupSize0);
        tvCapsuleCupSize1 = (TextView) findViewById(R.id.tvCapsuleCupSize1);
        tvCapsuleCupSize2 = (TextView) findViewById(R.id.tvCapsuleCupSize2);
        tvCapsuleCupSize3 = (TextView) findViewById(R.id.tvCapsuleCupSize3);
        tvIntensity = (TextView) findViewById(R.id.tvIntensity);
        tvOrigin = (TextView) findViewById(R.id.tvOrigin);
        tvProfile = (TextView) findViewById(R.id.tvProfile);

        Intent intent = getIntent();
        Capsule capsule = (Capsule) intent.getSerializableExtra("capsule");
        setTitle(capsule.getCapsuleDetail().getName());

        tvCapsuleName.setText(capsule.getCapsuleDetail().getName());
        tvCapsuleNameEng.setText(capsule.getNesOAName());
        setImage(capsule);
        setCupSize(capsule);
        setIntensity(capsule);
        tvOrigin.setText(capsule.getCapsuleDetail().getOrigin());
        tvProfile.setText(capsule.getCapsuleDetail().getProfile());

        setDetailFragments(capsule);
    }

    private void setCupSize(Capsule capsule) {

        ivCapsuleCupSize0.setVisibility(View.GONE);
        tvCapsuleCupSize0.setVisibility(View.GONE);
        ivCapsuleCupSize1.setVisibility(View.GONE);
        tvCapsuleCupSize1.setVisibility(View.GONE);
        ivCapsuleCupSize2.setVisibility(View.GONE);
        tvCapsuleCupSize2.setVisibility(View.GONE);
        ivCapsuleCupSize3.setVisibility(View.GONE);
        tvCapsuleCupSize3.setVisibility(View.GONE);

        if (capsule.getCapsuleDetail().getCupSize() != null) {
            //llCupSizeLayout.removeAllViews();

            List<String> cupsSize = capsule.getCapsuleDetail().getCupSize();
            String cupSize = "";

            for (int i = 0; i < cupsSize.size(); i++) {
                cupSize = cupsSize.get(i);

                if (cupSize.endsWith("레시피")) {
                    if (i == 0) {
                        ivCapsuleCupSize0.setImageDrawable(getResources().getDrawable(R.drawable.receip));
                        tvCapsuleCupSize0.setText(cupSize);
                        ivCapsuleCupSize0.setVisibility(View.VISIBLE);
                        tvCapsuleCupSize0.setVisibility(View.VISIBLE);
                        ivCapsuleCupSize1.setVisibility(View.GONE);
                        tvCapsuleCupSize1.setVisibility(View.GONE);
                        ivCapsuleCupSize2.setVisibility(View.GONE);
                        tvCapsuleCupSize2.setVisibility(View.GONE);
                        ivCapsuleCupSize3.setVisibility(View.GONE);
                        tvCapsuleCupSize3.setVisibility(View.GONE);
                    } else if (i == 1) {
                        ivCapsuleCupSize1.setImageDrawable(getResources().getDrawable(R.drawable.receip));
                        tvCapsuleCupSize1.setText(cupSize);
                        ivCapsuleCupSize1.setVisibility(View.VISIBLE);
                        tvCapsuleCupSize1.setVisibility(View.VISIBLE);
                        ivCapsuleCupSize2.setVisibility(View.GONE);
                        tvCapsuleCupSize2.setVisibility(View.GONE);
                        ivCapsuleCupSize3.setVisibility(View.GONE);
                        tvCapsuleCupSize3.setVisibility(View.GONE);
                    } else if (i == 2) {
                        ivCapsuleCupSize2.setImageDrawable(getResources().getDrawable(R.drawable.receip));
                        tvCapsuleCupSize2.setText(cupSize);
                        ivCapsuleCupSize2.setVisibility(View.VISIBLE);
                        tvCapsuleCupSize2.setVisibility(View.VISIBLE);
                        ivCapsuleCupSize3.setVisibility(View.GONE);
                        tvCapsuleCupSize3.setVisibility(View.GONE);
                    } else if (i == 3) {
                        ivCapsuleCupSize3.setImageDrawable(getResources().getDrawable(R.drawable.receip));
                        tvCapsuleCupSize3.setText(cupSize);
                        ivCapsuleCupSize3.setVisibility(View.VISIBLE);
                        tvCapsuleCupSize3.setVisibility(View.VISIBLE);
                    }
                } else if (cupSize.startsWith("40")) {
                    if (i == 0) {
                        ivCapsuleCupSize0.setImageDrawable(getResources().getDrawable(R.drawable.cup_size_40));
                        tvCapsuleCupSize0.setText(cupSize);
                        ivCapsuleCupSize0.setVisibility(View.VISIBLE);
                        tvCapsuleCupSize0.setVisibility(View.VISIBLE);
                        ivCapsuleCupSize1.setVisibility(View.GONE);
                        tvCapsuleCupSize1.setVisibility(View.GONE);
                        ivCapsuleCupSize2.setVisibility(View.GONE);
                        tvCapsuleCupSize2.setVisibility(View.GONE);
                        ivCapsuleCupSize3.setVisibility(View.GONE);
                        tvCapsuleCupSize3.setVisibility(View.GONE);
                    } else if (i == 1) {
                        ivCapsuleCupSize1.setImageDrawable(getResources().getDrawable(R.drawable.cup_size_40));
                        tvCapsuleCupSize1.setText(cupSize);
                        ivCapsuleCupSize1.setVisibility(View.VISIBLE);
                        tvCapsuleCupSize1.setVisibility(View.VISIBLE);
                        ivCapsuleCupSize2.setVisibility(View.GONE);
                        tvCapsuleCupSize2.setVisibility(View.GONE);
                        ivCapsuleCupSize3.setVisibility(View.GONE);
                        tvCapsuleCupSize3.setVisibility(View.GONE);
                    } else if (i == 2) {
                        ivCapsuleCupSize2.setImageDrawable(getResources().getDrawable(R.drawable.cup_size_40));
                        tvCapsuleCupSize2.setText(cupSize);
                        ivCapsuleCupSize2.setVisibility(View.VISIBLE);
                        tvCapsuleCupSize2.setVisibility(View.VISIBLE);
                        ivCapsuleCupSize3.setVisibility(View.GONE);
                        tvCapsuleCupSize3.setVisibility(View.GONE);
                    } else if (i == 3) {
                        ivCapsuleCupSize3.setImageDrawable(getResources().getDrawable(R.drawable.cup_size_40));
                        tvCapsuleCupSize3.setText(cupSize);
                        ivCapsuleCupSize3.setVisibility(View.VISIBLE);
                        tvCapsuleCupSize3.setVisibility(View.VISIBLE);
                    }
                } else if (cupSize.startsWith("25")) {
                    if (i == 0) {
                        ivCapsuleCupSize0.setImageDrawable(getResources().getDrawable(R.drawable.cup_size_25));
                        tvCapsuleCupSize0.setText(cupSize);
                        ivCapsuleCupSize0.setVisibility(View.VISIBLE);
                        tvCapsuleCupSize0.setVisibility(View.VISIBLE);
                        ivCapsuleCupSize1.setVisibility(View.GONE);
                        tvCapsuleCupSize1.setVisibility(View.GONE);
                        ivCapsuleCupSize2.setVisibility(View.GONE);
                        tvCapsuleCupSize2.setVisibility(View.GONE);
                        ivCapsuleCupSize3.setVisibility(View.GONE);
                        tvCapsuleCupSize3.setVisibility(View.GONE);
                    } else if (i == 1) {
                        ivCapsuleCupSize1.setImageDrawable(getResources().getDrawable(R.drawable.cup_size_25));
                        tvCapsuleCupSize1.setText(cupSize);
                        ivCapsuleCupSize1.setVisibility(View.VISIBLE);
                        tvCapsuleCupSize1.setVisibility(View.VISIBLE);
                        ivCapsuleCupSize2.setVisibility(View.GONE);
                        tvCapsuleCupSize2.setVisibility(View.GONE);
                        ivCapsuleCupSize3.setVisibility(View.GONE);
                        tvCapsuleCupSize3.setVisibility(View.GONE);
                    } else if (i == 2) {
                        ivCapsuleCupSize2.setImageDrawable(getResources().getDrawable(R.drawable.cup_size_25));
                        tvCapsuleCupSize2.setText(cupSize);
                        ivCapsuleCupSize2.setVisibility(View.VISIBLE);
                        tvCapsuleCupSize2.setVisibility(View.VISIBLE);
                        ivCapsuleCupSize3.setVisibility(View.GONE);
                        tvCapsuleCupSize3.setVisibility(View.GONE);
                    } else if (i == 3) {
                        ivCapsuleCupSize3.setImageDrawable(getResources().getDrawable(R.drawable.cup_size_25));
                        tvCapsuleCupSize3.setText(cupSize);
                        ivCapsuleCupSize3.setVisibility(View.VISIBLE);
                        tvCapsuleCupSize3.setVisibility(View.VISIBLE);
                    }
                } else if (cupSize.startsWith("110")) {
                    if (i == 0) {
                        ivCapsuleCupSize0.setImageDrawable(getResources().getDrawable(R.drawable.cup_size_110));
                        tvCapsuleCupSize0.setText(cupSize);
                        ivCapsuleCupSize0.setVisibility(View.VISIBLE);
                        tvCapsuleCupSize0.setVisibility(View.VISIBLE);
                        ivCapsuleCupSize1.setVisibility(View.GONE);
                        tvCapsuleCupSize1.setVisibility(View.GONE);
                        ivCapsuleCupSize2.setVisibility(View.GONE);
                        tvCapsuleCupSize2.setVisibility(View.GONE);
                        ivCapsuleCupSize3.setVisibility(View.GONE);
                        tvCapsuleCupSize3.setVisibility(View.GONE);
                    } else if (i == 1) {
                        ivCapsuleCupSize1.setImageDrawable(getResources().getDrawable(R.drawable.cup_size_110));
                        tvCapsuleCupSize1.setText(cupSize);
                        ivCapsuleCupSize1.setVisibility(View.VISIBLE);
                        tvCapsuleCupSize1.setVisibility(View.VISIBLE);
                        ivCapsuleCupSize2.setVisibility(View.GONE);
                        tvCapsuleCupSize2.setVisibility(View.GONE);
                        ivCapsuleCupSize3.setVisibility(View.GONE);
                        tvCapsuleCupSize3.setVisibility(View.GONE);
                    } else if (i == 2) {
                        ivCapsuleCupSize2.setImageDrawable(getResources().getDrawable(R.drawable.cup_size_110));
                        tvCapsuleCupSize2.setText(cupSize);
                        ivCapsuleCupSize2.setVisibility(View.VISIBLE);
                        tvCapsuleCupSize2.setVisibility(View.VISIBLE);
                        ivCapsuleCupSize3.setVisibility(View.GONE);
                        tvCapsuleCupSize3.setVisibility(View.GONE);
                    } else if (i == 3) {
                        ivCapsuleCupSize3.setImageDrawable(getResources().getDrawable(R.drawable.cup_size_110));
                        tvCapsuleCupSize3.setText(cupSize);
                        ivCapsuleCupSize3.setVisibility(View.VISIBLE);
                        tvCapsuleCupSize3.setVisibility(View.VISIBLE);
                    }
                }
            }

            if ( ivCapsuleCupSize0.getVisibility() == View.VISIBLE ) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ivCapsuleCupSize0.getLayoutParams();
                params.leftMargin = 0;
                ivCapsuleCupSize0.setLayoutParams(params);
            }
            else if ( ivCapsuleCupSize1.getVisibility() == View.VISIBLE ) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ivCapsuleCupSize1.getLayoutParams();
                params.leftMargin = 0;
                ivCapsuleCupSize1.setLayoutParams(params);
            }
            else if ( ivCapsuleCupSize2.getVisibility() == View.VISIBLE ) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ivCapsuleCupSize2.getLayoutParams();
                params.leftMargin = 0;
                ivCapsuleCupSize2.setLayoutParams(params);
            }
            else if ( ivCapsuleCupSize3.getVisibility() == View.VISIBLE ) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ivCapsuleCupSize3.getLayoutParams();
                params.leftMargin = 0;
                ivCapsuleCupSize3.setLayoutParams(params);
            }
        }
        
    }

    private void setIntensity(Capsule capsule) {
        if (capsule.getCapsuleDetail().getIntensity() != null && capsule.getCapsuleDetail().getIntensity().length() > 0) {
            tvIntensity.setText("강도(Intensity) : " + capsule.getCapsuleDetail().getIntensity());
        } else {
            tvIntensity.setText("");
        }
    }

    private void setImage(Capsule capsule) {
        File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), getString(R.string.localPath));
        File imgFile = new File(f.getAbsolutePath() + File.separator + capsule.getCapsuleDetail().getFileName());
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ivBigImage.setImageBitmap(myBitmap);
        }
    }

    private final int MAX_FRAGMENTS = 4;
    public final int FRAGMENT_ITEM_0 = 0;
    public final int FRAGMENT_ITEM_1 = 1;
    public final int FRAGMENT_ITEM_2 = 2;
    public final int FRAGMENT_ITEM_3 = 3;

    public ViewPager pager;
    private PagerSlidingTabStrip tabs;
    private String[] pageTitle = {"DETAIL", "ORIGINS", "COMMENT", "WRITE"};

    public void noti() {
        pager.getAdapter().notifyDataSetChanged();
    }

    private void setDetailFragments(final Capsule capsule) {
        pager = (ViewPager) findViewById(R.id.pager);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return pageTitle[position];
            }

            @Override
            public Fragment getItem(int position) {
                if(position == FRAGMENT_ITEM_0) {
                    return DetailTitleFragment.newInstance(capsule);
                }
                else if(position == FRAGMENT_ITEM_1) {
                    return DetailCapsuleFragment.newInstance(capsule);
                }
                else if(position == FRAGMENT_ITEM_2) {
                    return DetailCommentFragment.newInstance(capsule);
                }
                else if(position == FRAGMENT_ITEM_3) {
                    return DetailCommentWriteFragment.newInstance(capsule);
                }
                return null;
            }

            @Override
            public int getCount() {
                return MAX_FRAGMENTS;
            }


        });
        pager.setCurrentItem(FRAGMENT_ITEM_0);

        tabs.setViewPager(pager);
    }

    @Override
    public void heartbeat() {
        CapsuleDao dao = new CapsuleDao(this);
        UserModel user = dao.getUserModel();

        if ( user != null ) {
            AsyncJobs jobs = new AsyncJobs(this);

            jobs.loginGnuBoard(user.getUserEmail(), user.getUserPassword(), new CallbackReturn() {
                @Override
                public void callback(Context context, Object object) {

                }
            });
        }
    }
}
