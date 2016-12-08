package com.mcjang.mynespresso;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mcjang.mynespresso.app.capsule.CapsuleFragment;
import com.mcjang.mynespresso.app.community.CommunityFragment;
import com.mcjang.mynespresso.app.info.InfoFragment;
import com.mcjang.mynespresso.app.login.LoginFragment;
import com.mcjang.mynespresso.app.login.UserModel;
import com.mcjang.mynespresso.db.impl.CapsuleDao;
import com.mcjang.mynespresso.db.mysql.AsyncJobs;
import com.mcjang.mynespresso.db.mysql.CallbackReturn;
import com.mcjang.mynespresso.util.PermissionRequester;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentChangeagle, Heartbeat {

    private OnKeyBackPressedListener onKeyBackPressedListener;

    public void setOnKeyBackPressedListener(OnKeyBackPressedListener listener) {
        onKeyBackPressedListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        int result = new PermissionRequester.Builder(MainActivity.this)
                .setTitle("외부 메모리 사용 요청")
                .setMessage("데이터 과다 사용을 방지하기 위해 이미지 파일을 다운로드 하려합니다. 허용하시겠습니까?")
                .setPositiveButtonName("네")
                .setNegativeButtonName("아니요.")
                .create()
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, 1000 , new PermissionRequester.OnClickDenyButtonListener() {
                    @Override
                    public void onClick(Activity activity) {
                        Toast.makeText(MainActivity.this, "인터넷을 사용합니다.", Toast.LENGTH_SHORT).show();
                    }
                });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportFragmentManager().addOnBackStackChangedListener(getListener());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MenuItem item = (MenuItem) findViewById(R.id.nav_login);
        heartbeat();

        changeFragment(CapsuleFragment.newInstance(), false);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(onKeyBackPressedListener != null) {
                onKeyBackPressedListener.onKeyBackPressed();
            }
            else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void changeFragment(Fragment fragment, boolean isBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, fragment);
        if ( isBackStack )
            transaction.addToBackStack(null);
        transaction.commit();
    }

    private FragmentManager.OnBackStackChangedListener getListener() {
        FragmentManager.OnBackStackChangedListener result = new FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {
                FragmentManager manager = getSupportFragmentManager();
                if (manager != null) {
                    int backStackEntryCount = manager.getBackStackEntryCount();
                    if (backStackEntryCount == 0) {
                        finish();
                        return;
                    }
                    Fragment fragment = manager.getFragments().get(backStackEntryCount - 1);
                    fragment.onResume();
                }
            }
        };
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_capsule_list) {
            changeFragment(CapsuleFragment.newInstance(), true);
        }
        else if (id == R.id.nav_community) {
            changeFragment(CommunityFragment.newInstance(), true);
        }
        else if (id == R.id.nav_visit_site) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.nespresso.com/kr/ko/home"));
            startActivity(intent);
        }
        else if (id == R.id.nav_login) {
            changeFragment(LoginFragment.newInstance(), true);
        }
        else if (id == R.id.nav_app_info) {
            changeFragment(InfoFragment.newInstance(), true);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    public interface OnKeyBackPressedListener {
        public void onKeyBackPressed();
    }



}
