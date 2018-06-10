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

import com.example.ftn.showbook.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private  DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private FragmentManager fragmentManager;
    private String notification;

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
        fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.main_container);

        notification = getIntent().getStringExtra("notification");

        if (fragment == null && notification == null) {
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
                                Bundle bundleReserved = new Bundle();
                                bundleReserved.putString("parent", "main");
                                fragment.setArguments(bundleReserved);
                                break;
                            case R.id.nav_interested:
                                fragment = new Tab3Interested();
                                Bundle bundleInterested = new Bundle();
                                bundleInterested.putString("parent", "main");
                                fragment.setArguments(bundleInterested);
                                break;
                            case R.id.nav_seen:
                                fragment = new SeenShowsFragment();
                                break;
                            case R.id.nav_settings:
                                fragment = new SettingsFragment();
                                break;
                            case R.id.nav_logout:
                                removeUserToken(getIntent().getStringExtra("drawerUsername"));
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

        fragmentManager.addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                            Fragment currentFragment = fragmentManager.findFragmentById(R.id.main_container);
                            if(currentFragment instanceof HomeFragment){
                                mNavigationView.getMenu().getItem(0).setChecked(true);
                                getSupportActionBar().setTitle(getResources().getString(R.string.nav_map_label));
                            }
                            else if(currentFragment instanceof Tab2Reserved){
                                mNavigationView.getMenu().getItem(1).setChecked(true);
                                getSupportActionBar().setTitle(getResources().getString(R.string.nav_reserved_label));
                            }
                            else if(currentFragment instanceof Tab3Interested){
                                mNavigationView.getMenu().getItem(2).setChecked(true);
                                getSupportActionBar().setTitle(getResources().getString(R.string.nav_interested_label));
                            }
                            else if(currentFragment instanceof SeenShowsFragment){
                                mNavigationView.getMenu().getItem(3).setChecked(true);
                                getSupportActionBar().setTitle(getResources().getString(R.string.nav_seen_label));

                            }
                            else if(currentFragment instanceof SettingsFragment){
                                mNavigationView.getMenu().getItem(4).setChecked(true);
                                getSupportActionBar().setTitle(getResources().getString(R.string.nav_settings_label));
                            } else if(currentFragment instanceof ShowDetailsFragment){
                                getSupportActionBar().setTitle(getResources().getString(R.string.show_fragment_details_title));
                            } else if(currentFragment instanceof ReservedShowDetailsFragment){
                                getSupportActionBar().setTitle(getResources().getString(R.string.reserved_show_fragment_title));
                            } else if(currentFragment instanceof SeatReserveFragment){
                                getSupportActionBar().setTitle(getResources().getString(R.string.seat_reserve_fragment_title));
                            } else if(currentFragment instanceof ShowCommentsFragment){
                                getSupportActionBar().setTitle(getResources().getString(R.string.show_comments_title));
                            } else if(currentFragment instanceof RepertoireFragment){
                                getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
                            } else if(currentFragment instanceof TimetableFragment){
                                getSupportActionBar().setTitle(getResources().getString(R.string.timetable_fragment_title));
                            }
                            else if(currentFragment instanceof CommentFragment){
                                getSupportActionBar().setTitle(getResources().getString(R.string.leave_comment_title));
                            }

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
                }
                else if(currentFragment instanceof Tab2Reserved){
                    mNavigationView.getMenu().getItem(1).setChecked(true);
                }
                else if(currentFragment instanceof Tab3Interested){
                    mNavigationView.getMenu().getItem(2).setChecked(true);
                }
                else if(currentFragment instanceof SeenShowsFragment){
                    mNavigationView.getMenu().getItem(3).setChecked(true);
                }
                else if(currentFragment instanceof SettingsFragment) {
                    mNavigationView.getMenu().getItem(4).setChecked(true);
                }
            } else {
                super.onBackPressed();
                finish();
            }
        }

    }


    public void removeUserToken(String username) {

        Call<User> call = ServiceUtils.pmaService.removeUserToken(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                System.out.println("Uspesno obrisan token!");

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println("Neuspesno obrisan token!");

            }

        });
    }

    @Override
    public void onDestroy() {
        if (notification == null) {
            removeUserToken(getIntent().getStringExtra("drawerUsername"));
        }
        super.onDestroy();

    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (notification != null) {
            if (notification.equals("commentsFragment")) {
                ShowCommentsFragment showCommentsFragment = new ShowCommentsFragment();
                Bundle scArgs = new Bundle();
                scArgs.putLong("showId", getIntent().getLongExtra("showId",0L));
                scArgs.putString("showName", getIntent().getStringExtra("showName"));
                showCommentsFragment.setArguments(scArgs);

                fragmentManager.beginTransaction()
                        .replace(R.id.main_container, showCommentsFragment)
                        .commit();
            }
        }
    }





}
