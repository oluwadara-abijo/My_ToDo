package com.dara.mytodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ToDoViewHolder> {

    class ToDoViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView detailsTextView;
        private final CheckBox checkBox;

        private ToDoViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.tv_title);
            detailsTextView = itemView.findViewById(R.id.tv_details);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }

    private final LayoutInflater mInflater;
    private List<ToDoItem> mItems;

    ToDoListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NotNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.to_do_item, parent, false);
        return new ToDoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull ToDoViewHolder holder, int position) {
        if (mItems != null) {
            ToDoItem currentItem = mItems.get(position);
            holder.titleTextView.setText(currentItem.getTitle());
            holder.detailsTextView.setText(currentItem.getDetails());
            if (currentItem.isCompleted()) {
                holder.checkBox.isChecked();
            }

            holder.checkBox.setOnClickListener(v -> {
                boolean checked = ((CheckBox) v).isChecked();
                if (checked) {
                    currentItem.isCompleted();
                }
            });
        } else {
            // Covers the case of data not being ready yet.
            holder.titleTextView.setText("No Item");
        }
    }

    void setItems(List<ToDoItem> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mItems != null)
            return mItems.size();
        else return 0;
    }
}
