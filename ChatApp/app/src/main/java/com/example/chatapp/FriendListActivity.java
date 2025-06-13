package com.example.chatapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;

public class FriendListActivity extends AppCompatActivity {

    ListView friendListView;
    DatabaseHelper db;
    String loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.friendToolbar);
        setSupportActionBar(toolbar);

        db = new DatabaseHelper(this);
        friendListView = findViewById(R.id.friendList);
        loggedInUser = getIntent().getStringExtra("username");

        ArrayList<String> friends = new ArrayList<>();
        Cursor c = db.getAllUsersExcept(loggedInUser);

        while (c.moveToNext()) {
            friends.add(c.getString(0));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.friend_item, R.id.friendName, friends);
        friendListView.setAdapter(adapter);

        friendListView.setOnItemClickListener((adapterView, view, i, l) -> {
            String selectedFriend = friends.get(i);
            Intent intent = new Intent(FriendListActivity.this, MainActivity.class);
            intent.putExtra("username", loggedInUser);
            intent.putExtra("receiver", selectedFriend);
            startActivity(intent);
        });
    }

    // Inflate menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_friend_list, menu);
        return true;
    }

    // Handle menu clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            Toast.makeText(this, "Logged out!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        } else if (id == R.id.action_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
