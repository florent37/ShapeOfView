package com.github.florent37.shapeofview.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShapeOfViewMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shape_of_view_activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.anim)
    public void onAnimClicked(){
        startActivity(new Intent(this, ShapeOfViewAnimationActivity.class));
    }

    @OnClick(R.id.shrine)
    public void onShrineClicked(){
        startActivity(new Intent(this, MaterialDesignShrineActivity.class));
    }

    @OnClick(R.id.shapes)
    public void onShapesClicked(){
        startActivity(new Intent(this, ShapeOfViewTabActivity.class));
    }
}
