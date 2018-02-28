package com.github.florent37.shapeofview.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabbedMainActivity extends AppCompatActivity {
    private MapView mapView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    ListenableTabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.triangle)
    View triangle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabbed_activity_main);
        ButterKnife.bind(this);
        Mapbox.getInstance(this, "pk.eyJ1IjoiY2hpY2tpbm5pY2siLCJhIjoiY2pka3U2YTdiMDE1YTJ4cjA0YzVyYnpoMSJ9.xlyPakmrR_N4bNqIGe6AKg");
        viewPager.setAdapter(new FakeAdapter(getSupportFragmentManager()));

        tabLayout.setupWithViewPager(viewPager);

        TabIndicatorFollower.setupWith(tabLayout, triangle);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    public static class FakeAdapter extends FragmentStatePagerAdapter {

        public FakeAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

        @Override
        public Fragment getItem(int position) {
            return FakeFragment.newInstance();
        }

        @Override
        public int getCount() {
            return 10;
        }
    }

    public static class FakeFragment extends Fragment {
        public static FakeFragment newInstance() {
            final Bundle args = new Bundle();
            final FakeFragment fragment = new FakeFragment();
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment, container, false);
        }
    }
}
