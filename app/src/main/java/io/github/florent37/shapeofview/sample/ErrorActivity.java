package io.github.florent37.shapeofview.sample;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.github.florent37.shapeofview.sample.R;

public class ErrorActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shape_of_view_error);

        recyclerView = findViewById(R.id.recyclerView);

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

            img_flyer_list = itemView.findViewById(R.id.img_flyer_list);
            tv_cell_title = itemView.findViewById(R.id.tv_cell_title);
            tv_cell_description = itemView.findViewById(R.id.tv_cell_description);
        }

        public static MyViewHolder buildFor(ViewGroup viewGroup) {
            return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(LAYOUT, viewGroup, false));
        }

        ImageView img_flyer_list;
        TextView tv_cell_title;
        TextView tv_cell_description;

        public void bind(Object o){
            img_flyer_list.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.shape_of_view_shoes));
            tv_cell_title.setText("My event");
            tv_cell_description.setText("My desc");
        }
    }
}
