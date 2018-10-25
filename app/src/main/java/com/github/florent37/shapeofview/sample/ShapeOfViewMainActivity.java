package com.github.florent37.shapeofview.sample;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

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
    public void onAnimClicked() {
        startActivity(new Intent(this, ShapeOfViewAnimationActivity.class));
    }

    @OnClick(R.id.jackman)
    public void onJackmanClicked() {
        startActivity(new Intent(this, JackmanActivity.class));
    }

    @OnClick(R.id.star)
    public void onStarClicked() {
        startActivity(new Intent(this, ShapeOfViewStarActivity.class));
    }

    @OnClick(R.id.round)
    public void onRoundClicked() {
        startActivity(new Intent(this, ShapeOfViewRoundActivity.class));
    }

    @OnClick(R.id.star_wars)
    public void onStarWarsClicked() {
        startActivity(new Intent(this, StarWarsActivity.class));
    }

    @OnClick(R.id.shrine)
    public void onShrineClicked() {
        startActivity(new Intent(this, MaterialDesignShrineActivity.class));
    }

    @OnClick(R.id.shapes)
    public void onShapesClicked() {
        startActivity(new Intent(this, ShapeOfViewStarActivity.class));
    }
}
