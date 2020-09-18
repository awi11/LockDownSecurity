package com.ucsd.lds;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class MessagingActivity extends AppCompatActivity {
    String TAG = MessagingActivity.class.getSimpleName();

    private final String COLLECTION_KEY = "conversationThread";
    private final String DOCUMENT_KEY = "chat1";
    private final String MESSAGES_KEY = "messages";
    private final String FROM_KEY = "from";
    private final String TEXT_KEY = "text";
    private final String TIMESTAMP_KEY = "timestamp";
    private SharedPreferences sp;

    CollectionReference conversation;

    // temporarily hardcode user
    String sender = "User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        sp = getSharedPreferences("fire_chats", Context.MODE_PRIVATE);

        conversation = FirebaseFirestore.getInstance()
                .collection(COLLECTION_KEY)
                .document(DOCUMENT_KEY)
                .collection(MESSAGES_KEY);

        initMessageUpdateListener();
        findViewById(R.id.btn_send).setOnClickListener(view -> sendMessage());
    }

    private void sendMessage() {
        EditText message = findViewById(R.id.text_message);
        Map<String, String> newMessage = new HashMap<>();;
        newMessage.put(FROM_KEY, sender);
        newMessage.put(TEXT_KEY, message.getText().toString());
        conversation.add(newMessage);
        message.setText("");
    }

    private void initMessageUpdateListener() {
        // listen to realtime changes in multiple docs
        conversation.orderBy(TIMESTAMP_KEY, Query.Direction.ASCENDING)
        .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot values, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.e(TAG, "Listen failed.", e);
                    return;
                }
                if (values != null && !values.isEmpty()) {
                    StringBuilder str = new StringBuilder();
                    List<DocumentChange> docChanges = values.getDocumentChanges();
                    docChanges.forEach(new Consumer<DocumentChange>() {
                        @Override
                        public void accept(DocumentChange change) {
                            QueryDocumentSnapshot document = change.getDocument();
                            str.append(document.get(FROM_KEY));
                            str.append(":\n");
                            str.append(document.get(TEXT_KEY));
                            str.append("\n");
                        }
                    });

                    TextView chatView = MessagingActivity.this.findViewById(R.id.chat);
                    chatView.append(str.toString());
                }
            }
        });
    }
}

