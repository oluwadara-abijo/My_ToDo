package com.dara.mytodo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ToDoViewHolder> {

    private final LayoutInflater mInflater;
    private List<ToDoItem> mItems;
    private ItemClickListener mItemClickListener;

    // Constructor
    ToDoListAdapter(Context context, List<ToDoItem> items, ItemClickListener itemClickListener) {
        mInflater = LayoutInflater.from(context);
        mItems = items;
        mItemClickListener = itemClickListener;
    }

    // Interface to handle click events
    interface ItemClickListener {
        void onItemClick(ToDoItem item, ListenerType listenerType);
    }

    //Class to handle click interactions
    enum ListenerType {
        EDIT, MARK_DONE, UNMARK_DONE
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
            holder.bind(currentItem);
        } else {
            // Covers the case of data not being ready yet.
            holder.titleTextView.setText("No Item");
        }
    }

    class ToDoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView titleTextView;
        private final TextView detailsTextView;
        private final CheckBox checkBox;

        private ToDoViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.tv_title);
            detailsTextView = itemView.findViewById(R.id.tv_details);
            checkBox = itemView.findViewById(R.id.checkbox_completed);
        }

        @Override
        public void onClick(View v) {
            ToDoItem itemClicked = mItems.get(getAdapterPosition());
            ListenerType listenerType = ListenerType.EDIT;
            if (v.getId() == R.id.checkbox_completed) {
                if (checkBox.isChecked()) {
                    Log.d("TAG>>>", "Checkbox is checked");
                    listenerType = ListenerType.MARK_DONE;
                    mItems.remove(itemClicked);
                    mItems.add(itemClicked);
                } else {
                    Log.d("TAG>>>", "Checkbox is not checked");
                    listenerType = ListenerType.UNMARK_DONE;
                }
            }
            mItemClickListener.onItemClick(itemClicked, listenerType);
        }

        private void bind(ToDoItem currentItem) {
            titleTextView.setText(currentItem.getTitle());
            detailsTextView.setText(currentItem.getDetails());
            if (currentItem.getCompleted()) {
                checkBox.setChecked(true);
            }
            if (currentItem.getDetails().isEmpty()) {
                detailsTextView.setVisibility(View.GONE);
            }
            checkBox.setOnClickListener(this);
            itemView.setOnClickListener(this);
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
