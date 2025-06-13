package com.example.chatapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "chatapp.db";
    private static final int DB_VERSION = 2; // 🔼 Increased to trigger onUpgrade

    public DatabaseHelper(Context context) {
        super(context.getApplicationContext(), DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create users table
        db.execSQL("CREATE TABLE users(uname TEXT PRIMARY KEY, pwd TEXT)");

        // Insert sample users
        db.execSQL("INSERT INTO users VALUES('srm', 'ap')");
        db.execSQL("INSERT INTO users VALUES('user1', 'pass1')");
        db.execSQL("INSERT INTO users VALUES('user2', 'pass2')");

        // Create messages table with timestamp column
        db.execSQL("CREATE TABLE messages(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "sender TEXT, " +
                "receiver TEXT, " +
                "message TEXT, " +
                "timestamp TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Only add the timestamp column if upgrading from version 1
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE messages ADD COLUMN timestamp TEXT");
        }
    }

    // Check if user credentials are valid
    public boolean checkUser(String uname, String pwd) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM users WHERE uname=? AND pwd=?", new String[]{uname, pwd});
        return c.getCount() > 0;
    }

    // Insert a new user
    public void insertUser(String uname, String pwd) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("uname", uname);
        cv.put("pwd", pwd);
        db.insert("users", null, cv);
    }

    // Insert a new message with timestamp
    public void insertMessage(String sender, String receiver, String message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("sender", sender);
        cv.put("receiver", receiver);
        cv.put("message", message);
        cv.put("timestamp", new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date()));
        db.insert("messages", null, cv);
    }

    // Get messages exchanged between two users (both directions)
    public Cursor getMessagesBetween(String user1, String user2) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM messages WHERE (sender=? AND receiver=?) OR (sender=? AND receiver=?) ORDER BY id ASC",
                new String[]{user1, user2, user2, user1}
        );
    }

    // Get all users except the current one
    public Cursor getAllUsersExcept(String currentUser) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT uname FROM users WHERE uname != ?", new String[]{currentUser});
    }
}
