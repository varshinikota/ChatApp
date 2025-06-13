package com.example.chatapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.*;
import java.util.ArrayList;
import androidx.emoji2.widget.EmojiEditText;
import android.widget.ImageView; // 🔁 Make sure this import is present

public class MainActivity extends AppCompatActivity {

    EmojiEditText messageBox;
    ImageView sendBtn; // ✅ Changed from Button to ImageView
    RecyclerView messageRecycler;
    DatabaseHelper db;
    String senderUsername, receiverUsername;
    MessageAdapter adapter;
    ArrayList<Message> messageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_ChatApp); // force correct theme
        setContentView(R.layout.activity_main);

        // Setup toolbar safely
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DatabaseHelper(this);

        // UI bindings
        messageBox = findViewById(R.id.messageInput);
        sendBtn = findViewById(R.id.sendButton); // now matches ImageView
        messageRecycler = findViewById(R.id.messageRecycler);

        // Get sender and receiver from intent
        senderUsername = getIntent().getStringExtra("username");
        receiverUsername = getIntent().getStringExtra("receiver");

        if (senderUsername == null || receiverUsername == null) {
            Toast.makeText(this, "Missing chat participants!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        setTitle("Chat with " + receiverUsername);

        // Setup RecyclerView
        adapter = new MessageAdapter(messageList, senderUsername);
        messageRecycler.setLayoutManager(new LinearLayoutManager(this));
        messageRecycler.setAdapter(adapter);

        // Send message
        sendBtn.setOnClickListener(v -> {
            String msg = messageBox.getText().toString().trim();
            if (!msg.isEmpty()) {
                db.insertMessage(senderUsername, receiverUsername, msg);
                messageBox.setText("");
                loadChat();
            }
        });

        // Load past messages
        loadChat();
    }

    void loadChat() {
        messageList.clear();
        Cursor c = db.getMessagesBetween(senderUsername, receiverUsername);
        while (c.moveToNext()) {
            String sender = c.getString(c.getColumnIndexOrThrow("sender"));
            String message = c.getString(c.getColumnIndexOrThrow("message"));
            String time = c.getString(c.getColumnIndexOrThrow("timestamp"));
            messageList.add(new Message(sender, message, time));
        }
        adapter.notifyDataSetChanged();
        messageRecycler.scrollToPosition(messageList.size() - 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            Toast.makeText(this, "Logged out!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        } else if (id == R.id.action_profile) {
            // ✅ Launch ProfileActivity
            startActivity(new Intent(this, ProfileActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
