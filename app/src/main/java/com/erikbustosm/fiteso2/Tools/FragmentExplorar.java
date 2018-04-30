package com.erikbustosm.fiteso2.Tools;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erikbustosm.fiteso2.FragmentExplorarEjercicios;
import com.erikbustosm.fiteso2.FragmentExplorarRutinas;
import com.erikbustosm.fiteso2.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class FragmentExplorar extends Fragment {

    DatabaseReference firebaseDatabase;

    private static final int TOTAL_PAGES=2;
    private FragmentExplorarRutinas fragmentExplorarRutinas;
    private FragmentExplorarEjercicios fragmentExplorarEjercicios;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public FragmentExplorar() {

    }

    public static FragmentExplorar newInstance(String param1, String param2) {
        FragmentExplorar fragment = new FragmentExplorar();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_explorar, container, false);

        firebaseDatabase= FirebaseDatabase.getInstance().getReference();

        viewPager = view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = view.findViewById(R.id.activity_explorar_tabs);

        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPagerAdapter sectionsPagerAdapter= new SectionsPagerAdapter(getChildFragmentManager());
        sectionsPagerAdapter.addFragment(new FragmentExplorarRutinas(),"Rutinas");
        sectionsPagerAdapter.addFragment(new FragmentExplorarEjercicios(), "Ejercicios");
        viewPager.setAdapter(sectionsPagerAdapter);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case Constants.FRAGMENT_EXPLORAR_RUTINA:
                    if (fragmentExplorarRutinas == null)
                        fragmentExplorarRutinas = new FragmentExplorarRutinas();
                    return fragmentExplorarRutinas;
                case Constants.FRAGMENT_EXPLORAR_EJERCICIOS:
                    if (fragmentExplorarEjercicios == null)
                        fragmentExplorarEjercicios = new FragmentExplorarEjercicios();
                    return fragmentExplorarEjercicios;

                default:
                    return new FragmentExplorarRutinas();
            }
        }

        @Override
        public int getCount() {
            return TOTAL_PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case Constants.FRAGMENT_EXPLORAR_RUTINA:
                    return "Rutinas";
                case Constants.FRAGMENT_EXPLORAR_EJERCICIOS:
                    return "Ejercicios";
            }
            return null;
        }

        public void addFragment(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Constants.ACTIVITY_DETAIL:
                if(resultCode == Activity.RESULT_OK){
                    if(data.getExtras() != null) {
                        int fragment = data.getExtras().getInt(Constants.EXTRA_FRAGMENT);
                        switch (fragment) {
                            case Constants.FRAGMENT_EXPLORAR_RUTINA:
                                fragmentExplorarRutinas.onActivityResult(requestCode, resultCode, data);
                                break;
                            case Constants.FRAGMENT_EXPLORAR_EJERCICIOS:
                                fragmentExplorarEjercicios.onActivityResult(requestCode, resultCode, data);
                                break;

                        }
                    }
                }
                break;
        }
    }
}
