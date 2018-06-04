package com.github.florent37.shapeofview.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ErrorActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shape_of_view_error);
        ButterKnife.bind(this);

        final MyAdapter adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setItems(generateItems(20));
    }

    private List<Object> generateItems(int number){
        final List<Object> items = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            items.add(new Object());
        }
        return items;
    }

    static class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private List<Object> items = new ArrayList<>();

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return MyViewHolder.buildFor(parent);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.bind(items.get(position));
        }

        public void setItems(List<Object> objects){
            items.addAll(objects);
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private static final int LAYOUT = R.layout.shape_of_view_viewholder;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public static MyViewHolder buildFor(ViewGroup viewGroup) {
            return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(LAYOUT, viewGroup, false));
        }

        @BindView(R.id.img_flyer_list)
        ImageView img_flyer_list;
        @BindView(R.id.tv_cell_title)
        TextView tv_cell_title;
        @BindView(R.id.tv_cell_description)
        TextView tv_cell_description;

        public void bind(Object o){
            img_flyer_list.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.shape_of_view_shoes));
            tv_cell_title.setText("My event");
            tv_cell_description.setText("My desc");
        }
    }
}
