package io.github.florent37.shapeofview.sample;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ShapeOfViewMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shape_of_view_activity_main);

        findViewById(R.id.anim).setOnClickListener(view -> {
            startActivity(new Intent(this, ShapeOfViewAnimationActivity.class));
        });
        findViewById(R.id.jackman).setOnClickListener(view -> {
            startActivity(new Intent(this, JackmanActivity.class));
        });
        findViewById(R.id.star).setOnClickListener(view -> {
            startActivity(new Intent(this, ShapeOfViewStarActivity.class));
        });
        findViewById(R.id.round).setOnClickListener(view -> {
            startActivity(new Intent(this, ShapeOfViewRoundActivity.class));
        });
        findViewById(R.id.star_wars).setOnClickListener(view -> {
            startActivity(new Intent(this, StarWarsActivity.class));
        });
        findViewById(R.id.star_wars_animated).setOnClickListener(view -> {
            startActivity(new Intent(this, ShapeOfViewStarAnimatedActivity.class));
        });
        findViewById(R.id.shrine).setOnClickListener(view -> {
            startActivity(new Intent(this, MaterialDesignShrineActivity.class));
        });
        findViewById(R.id.shapes).setOnClickListener(view -> {
            startActivity(new Intent(this, ShapeOfViewStarActivity.class));
        });
        findViewById(R.id.dottedEdges).setOnClickListener(view -> {
            startActivity(new Intent(this, DottedEdgesActivity.class));
        });
    }
}
