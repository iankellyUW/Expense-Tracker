package com.example.expensetracker3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    //This database helper interacts with the SQL database and the ExpenseData

    //These strings are used in the SQL commands.
    public static final String EXPENSE_TABLE = "EXPENSE_TABLE";
    public static final String COLUMN_NAME = "COLUMN_NAME";
    public static final String COLUMN_CATEGORY = "COLUMN_CATEGORY";
    public static final String COLUMN_DATE = "COLUMN_DATE";
    public static final String COLUMN_AMOUNT = "COLUMN_AMOUNT";
    public static final String COLUMN_NOTE = "COLUMN_NOTE";
    public static final String COLUMN_ID = "ID";


    //Constructor takes contexts, works on the expenses.db file
    public DataBaseHelper(@Nullable Context context) {
        super(context, "expenses.db", null, 1);
    }

    //called the first time you access a database object
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creates an object with the proper elements
        String createTableStatement = "CREATE TABLE " + EXPENSE_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                        COLUMN_NAME + " TEXT, " + COLUMN_CATEGORY + " TEXT, " + COLUMN_DATE + " TEXT, " +
                                        COLUMN_AMOUNT + " DECIMAL, " + COLUMN_NOTE + " TEXT)";

        db.execSQL(createTableStatement);
    }

    //will be called when a version of database changes
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //function adds new expenses to database
    public boolean addExpense(ExpenseData expenseData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, expenseData.getName());
        cv.put(COLUMN_CATEGORY, expenseData.getCategory());
        cv.put(COLUMN_DATE, expenseData.getDate());
        cv.put(COLUMN_AMOUNT, expenseData.getAmount());
        cv.put(COLUMN_NOTE, expenseData.getNote());

        long insert = db.insert(EXPENSE_TABLE, null, cv);

        if (insert == -1) {
            return false;
        }
        else {
            return true;
        }
    }


    //function edits existing database entries
    public boolean editExpense(ExpenseData expenseData) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryName = "UPDATE " + EXPENSE_TABLE + " SET "
                + COLUMN_NAME + " = '" + expenseData.getName() + "', "
                + COLUMN_CATEGORY + " = '" + expenseData.getCategory() + "', "
                + COLUMN_DATE + " = '" + expenseData.getDate() + "', "
                + COLUMN_AMOUNT + " = '" + expenseData.getAmount() + "',"
                + COLUMN_NOTE + " = '" + expenseData.getNote() + "'"
                + "WHERE " + COLUMN_ID + " = '" +
                expenseData.get_id() + "'";

        Log.d("TAG", "updateData: " + queryName);
        db.execSQL(queryName);

        return true;
    }


    // function deletes entries from database
    public boolean deleteExpense(Long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryName = "DELETE FROM " + EXPENSE_TABLE + " WHERE " +
                COLUMN_ID + " = '" + id + "'";
        db.execSQL(queryName);
        return true;
    }


    //function returns all data in database as a list
    public List<ExpenseData> getAllExpenses() {
        List<ExpenseData> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + EXPENSE_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            //  loop through cursor and create new expense objects for each element
            do {
                int expenseID = cursor.getInt(0);
                String expenseName = cursor.getString(1);
                String expenseCategory = cursor.getString(2);
                String expenseDate = cursor.getString(3);
                Float expenseAmount = cursor.getFloat(4);
                String expenseNote = cursor.getString(5);

                ExpenseData newExpenseData = new ExpenseData(expenseName, expenseCategory, expenseDate, expenseAmount, expenseNote);
                newExpenseData.set_id(expenseID);
                returnList.add(newExpenseData);
            } while (cursor.moveToNext());

        }
        else {
            //  Do not add to list.
        }

        cursor.close();
        db.close();

        return returnList;
    }

}
