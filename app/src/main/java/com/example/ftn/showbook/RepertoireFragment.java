package com.example.ftn.showbook;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class RepertoireFragment extends Fragment {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private RepertoireFragment.SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repertoire, container, false);
        getActivity().setTitle(getResources().getString(R.string.app_name));

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new RepertoireFragment.SectionsPagerAdapter(getActivity().getSupportFragmentManager());

        mViewPager = view.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        return view;
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_repertoire, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
//            startActivity(intent);


        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    getActivity().setTitle(getResources().getString(R.string.app_name));
                    return new Tab1Repertoire();
                case 1:
                    getActivity().setTitle(getResources().getString(R.string.nav_reserved_label));
                    Fragment tab2Reserved = new Tab2Reserved();
                    Bundle bundleReserved = new Bundle();
                    bundleReserved.putString("parent", "repertoire");
                    tab2Reserved.setArguments(bundleReserved);
                    return tab2Reserved;
                case 2:
                    getActivity().setTitle(getResources().getString(R.string.nav_interested_label));
                    Fragment tab3Interested = new Tab3Interested();
                    Bundle bundleInterested = new Bundle();
                    bundleInterested.putString("parent", "repertoire");
                    tab3Interested.setArguments(bundleInterested);
                    return tab3Interested;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "REPERTOIRE";
                case 1:
                    return "RESERVED";
                case 2:
                    return "INTERESTED";
                default:
                    return null;
            }
        }
    }
}
