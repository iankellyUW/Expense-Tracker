package com.example.expensetracker3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NewExpenseDialogue.NewExpenseDialogueListener {
    RecyclerView mRecyclerView;
    myAdapter mAdapter;

    // when the main activity is created, it will refresh the recyclerview
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshViewHolder();
    }


    // this creates the dropdown to add a new expense
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_expense_menu, menu);
        return true;
    }

    // when the new expense dropdown is selected, it will open the new expense dialogue
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_expense_button:
                openDialogue();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // this creates a dialogue to enter a new expense, then shows it.
    public void openDialogue() {
        NewExpenseDialogue expenseDialogue = new NewExpenseDialogue();
        expenseDialogue.show(getSupportFragmentManager(), "New Expense");
    }


    // this function is called when a new expense is added. It creates a new expense data object,
    // then the database helper adds it to the database
    @Override
    public void applyTexts(String Name, String Category, String Date, Float Amount, String Note) {
        ExpenseData data = new ExpenseData(Name, Category, Date, Amount, Note);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
        boolean success = dataBaseHelper.addExpense(data);
        Toast.makeText(MainActivity.this, "Success= " + success, Toast.LENGTH_SHORT);
        Log.d("TAG", "Expense Tracker: " + data.toString());
        refreshViewHolder();
    }

    // this function refreshes the RecyclerView and the cards inside it. It makes a new adapter with every entry in the database to display.
    public void refreshViewHolder() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
        List<ExpenseData> viewAll = dataBaseHelper.getAllExpenses();

        Log.d("TAG", viewAll.toString());


        //setup the RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //and default animator
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //setup the adapter, which is myAdapter, see the code.
        mAdapter = new myAdapter(viewAll, R.layout.fragment_expense_list, this);
        //add the adapter to the recyclerview
        mRecyclerView.setAdapter(mAdapter);
    }

}