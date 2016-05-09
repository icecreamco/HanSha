package com.icecreamco.hansha;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /*
     * 实例域
     */
    public static final int STATE_SENIORITY = 1;
    public static final int STATE_RECORDS = 2;
    public static final int STATE_ABOUT = 3;

    private long mTime;             // 按两次退出程序
    private Fragment recordsFragment;
    private Fragment senioryFragment;
    public int fragmentState;

    public static MainActivity mainActivity;
    /*
     * onCreate()
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = this;

        // View初始化
        initiaView();

        // Fragment管理
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.framelayout);
        if (fragment == null) {
            senioryFragment = fragment = new SeniorityFragment();
            fm.beginTransaction().add(R.id.framelayout, fragment).commit();
            fragmentState = STATE_SENIORITY;
        }

    }

    /*
     * View初始化
     */
    private void initiaView() {
        // ActionBar设置
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 侧滑栏设置
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // 侧滑栏导航设置
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_seniority);
    }

    /*
     * 后退键事件
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (fragmentState != STATE_SENIORITY) {
            // 若当前页面不是seniority，则返回seniority
            FragmentManager fm = getSupportFragmentManager();
            senioryFragment = new SeniorityFragment();
            fm.beginTransaction()
                    .remove(fm.findFragmentById(R.id.framelayout))
                    .add(R.id.framelayout, senioryFragment)
                    .commit();
            fragmentState = STATE_SENIORITY;
        } else {
            // 按两次退出程序
            long currentTime = System.currentTimeMillis();
            if ((currentTime - mTime) > 2000) {
                Toast.makeText(this, R.string.exit_toast, Toast.LENGTH_SHORT).show();
                mTime = currentTime;
            } else {
                finish();
            }
        }
    }

    /*
     *友盟数据统计
     */
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    /*
     *友盟数据统计
     */
    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * 侧滑栏监听
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fm = getSupportFragmentManager();
        switch(item.getItemId()) {
            case R.id.nav_seniority :
                senioryFragment = new SeniorityFragment();
                fm.beginTransaction()
                        .remove(fm.findFragmentById(R.id.framelayout))
                        .add(R.id.framelayout, senioryFragment)
                        .commit();
                fragmentState = STATE_SENIORITY;
                break;
            case R.id.nav_records :
                recordsFragment = new RecordsFragment();
                fm.beginTransaction()
                        .remove(fm.findFragmentById(R.id.framelayout))
                        .add(R.id.framelayout, recordsFragment)
                        .commit();
                fragmentState = STATE_RECORDS;
                break;
            case R.id.nav_clear :
                Records.getRecords(getApplicationContext()).clear();
                if (recordsFragment != null) {
                    ((RecordsFragment)recordsFragment).refreshView();
                }
                if (senioryFragment != null) {
                    ((SeniorityFragment) senioryFragment).clearRecord();
                }
                break;
            case R.id.nav_about :
                fm.beginTransaction()
                        .remove(fm.findFragmentById(R.id.framelayout))
                        .add(R.id.framelayout, new AboutFragment())
                        .commit();
                fragmentState = STATE_ABOUT;
                break;
            case R.id.nav_exit :
                finish();
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
     * setsenioryFragment
     */
    public void setSenioryFragment(SeniorityFragment fragment) {
        senioryFragment = fragment;
    }
    public void setRecordsFragment(RecordsFragment fragment) {
        recordsFragment = fragment;
    }

}
