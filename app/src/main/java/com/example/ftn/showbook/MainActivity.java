package com.example.ftn.showbook;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private  DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.main_container);

        if(fragment == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.main_container, new HomeFragment())
                    .commit();
        }

        mNavigationView = findViewById(R.id.nav_view);
        View headerView = mNavigationView.getHeaderView(0);
        TextView drawerUsername = headerView.findViewById(R.id.drawer_username);
        drawerUsername.setText(getIntent().getStringExtra("drawerUsername"));

        mNavigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(MenuItem menuItem) {
                            // set item as selected to persist highlight
                            menuItem.setChecked(true);

                            // Set action bar title
                            setTitle(menuItem.getTitle());

                            // close drawer when item is tapped
                            mDrawerLayout.closeDrawers();

                            // Update the UI based on the item selected
                            // Swap UI fragments
                            Fragment fragment = new HomeFragment();
                            
                            switch (menuItem.getItemId()) {
                                default:
                                    fragment = new HomeFragment();
                                    break;
                                case R.id.nav_map:
                                    fragment = new HomeFragment();
                                    break;
                                case R.id.nav_reserved:
                                    fragment = new Tab2Reserved();
                                    break;
                                case R.id.nav_interested:
                                    fragment = new Tab3Interested();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("parent", "main");
                                    fragment.setArguments(bundle);
                                    break;
                                case R.id.nav_seen:
                                    fragment = new SeenShowsFragment();
                                    break;
                                case R.id.nav_settings:
                                    fragment = new SettingsFragment();
                                    break;
                                case R.id.nav_logout:
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // clears all previous activities task
                                    finish();
                                    startActivity(intent);
                                    break;

                            }
                            if (menuItem.getItemId() != R.id.nav_logout) {
                                fragmentManager.beginTransaction()
                                        .replace(R.id.main_container, fragment)
                                        .addToBackStack(null)
                                        .commit();
                            }

                            return true;
                        }
                    });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager manager = getSupportFragmentManager();
            if(manager.getBackStackEntryCount() > 0) {
                super.onBackPressed();
                Fragment currentFragment = manager.findFragmentById(R.id.main_container);
                if(currentFragment instanceof HomeFragment){
                    mNavigationView.getMenu().getItem(0).setChecked(true);
                    getSupportActionBar().setTitle(mNavigationView.getMenu().getItem(0).getTitle());
                }
                else if(currentFragment instanceof Tab2Reserved){
                    mNavigationView.getMenu().getItem(1).setChecked(true);
                    getSupportActionBar().setTitle(mNavigationView.getMenu().getItem(1).getTitle());
                }
                else if(currentFragment instanceof Tab3Interested){
                    mNavigationView.getMenu().getItem(2).setChecked(true);
                    getSupportActionBar().setTitle(mNavigationView.getMenu().getItem(2).getTitle());
                }
                else if(currentFragment instanceof SeenShowsFragment){
                    mNavigationView.getMenu().getItem(3).setChecked(true);
                    getSupportActionBar().setTitle(mNavigationView.getMenu().getItem(3).getTitle());

                }
                else if(currentFragment instanceof SettingsFragment){
                    mNavigationView.getMenu().getItem(4).setChecked(true);
                    getSupportActionBar().setTitle(mNavigationView.getMenu().getItem(4).getTitle());
                }
            } else {
                super.onBackPressed();
                finish();
            }
        }

    }

    //moved here from TimetableFragment
    public void timeClicked(View view) {
        TextView textView = (TextView)view;
        //        Toast.makeText(this, textView.getText(), Toast.LENGTH_LONG).show();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, new SeatReserveFragment())
                .addToBackStack(null)
                .commit();

    }

}
