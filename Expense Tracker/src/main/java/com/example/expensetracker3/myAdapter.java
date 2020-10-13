package com.example.expensetracker3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * this adapter is very similar to the adapters used for listview, except a ViewHolder is required
 * see http://developer.android.com/training/improving-layouts/smooth-scrolling.html
 * except instead having to implement a ViewHolder, it is implemented within
 * the adapter.
 */

public class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {

    private List<ExpenseData> myList;
    private int rowLayout;
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView displayName, displayCategory, displayDate, displayAmount, displayNote;

        public ImageView Pic;

        public ViewHolder(View itemView) {
            super(itemView);
            displayName = (TextView) itemView.findViewById(R.id.name);
            displayCategory = (TextView) itemView.findViewById(R.id.category);
            displayDate = (TextView) itemView.findViewById(R.id.date);
            displayAmount = (TextView) itemView.findViewById(R.id.amount);
            displayNote = (TextView) itemView.findViewById(R.id.note);
        }
    }

    //constructor
    public myAdapter(List<ExpenseData> myList, int rowLayout, Context context) {
        this.myList = myList;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }


    // whenever a card is clicked somewhere, it will open a dialogue to edit that card.
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final String name = myList.get(i).getName();
        final String category = myList.get(i).getCategory();
        final String date = myList.get(i).getDate();
        final Float amount = myList.get(i).getAmount();
        final String note = myList.get(i).getNote();
        final Long id = myList.get(i).get_id();

        viewHolder.displayName.setText("Name: " + name);
        viewHolder.displayName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editEntry(id, name, category, date, amount, note);
            }
        });

        viewHolder.displayCategory.setText("Category: " + category);
        viewHolder.displayCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editEntry(id, name, category, date, amount, note);
            }
        });

        viewHolder.displayDate.setText("Date: " + date);
        viewHolder.displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editEntry(id, name, category, date, amount, note);
            }
        });


        viewHolder.displayAmount.setText("Amount: $" + amount.toString());
        viewHolder.displayAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editEntry(id, name, category, date, amount, note);
            }
        });


        viewHolder.displayNote.setText("Note: " + note);
        viewHolder.displayNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editEntry(id, name, category, date, amount, note);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myList == null ? 0 : myList.size();
    }


    // when editing an entry, the current data from that card is passed to a dialogue that can update the card.
    public void editEntry(Long id, String name, String category, String date, Float amount, String note) {
        FragmentManager manager = ((AppCompatActivity) mContext).getSupportFragmentManager();
        NewExpenseDialogue newExpenseDialogue = new NewExpenseDialogue();
        newExpenseDialogue.setDetails(id, name, category, date, amount, note);
        newExpenseDialogue.show(manager, null);
    }
}
