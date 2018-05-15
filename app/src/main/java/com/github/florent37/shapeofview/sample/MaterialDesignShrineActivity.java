package com.github.florent37.shapeofview.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MaterialDesignShrineActivity extends AppCompatActivity {

    @BindView(R.id.menu)
    View menu;

    @BindView(R.id.bottomSheet)
    View bottomSheet;
    @BindView(R.id.middleSheet)
    View middleSheet;
    @BindView(R.id.topSheet)
    View topSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_shrine);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.menu)
    public void onMenuClicked() {
        final boolean enabled = middleSheet.isEnabled();
        if(enabled){
            middleSheet.animate().translationY(bottomSheet.getHeight());
        } else {
            middleSheet.animate().translationY(0);
        }
        middleSheet.setEnabled(!enabled);
    }

    @OnClick(R.id.topSheet)
    void onTopSheetClicked() {

    }

    @OnClick(R.id.addToCart)
    void onAddToCartClicked() {

    }
}
