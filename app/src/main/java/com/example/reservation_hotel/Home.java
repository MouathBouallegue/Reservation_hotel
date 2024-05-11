package com.example.reservation_hotel;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.EditText;
// Ensure RecyclerView is imported
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private RecyclerView recyclerViewRooms;
    private FloatingActionButton fabAddRoom;
    private List<Room> roomList = new ArrayList<>();
    private RoomAdapter roomAdapter;
    private DatabaseReference roomsRef;
    private DatabaseReference messageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerViewRooms = findViewById(R.id.recyclerViewRooms);
        fabAddRoom = findViewById(R.id.fabAddRoom);
        EditText roomNumberEditText = findViewById(R.id.roomNumberEditText);
        EditText roomTypeEditText = findViewById(R.id.roomTypeEditText);
        EditText roomAvailabilityEditText= findViewById(R.id.roomAvailabilityEditText);
        recyclerViewRooms.setLayoutManager(new LinearLayoutManager(this));
        roomAdapter = new RoomAdapter(roomList);
        recyclerViewRooms.setAdapter(roomAdapter);

        roomsRef = FirebaseDatabase.getInstance().getReference("room");
        messageRef = FirebaseDatabase.getInstance().getReference("message");
        messageRef.setValue("hello world ");
        fabAddRoom.setOnClickListener(v -> {
            // Add room functionality
            // For example, add a new room
            // Retrieve values from EditText fields
            String roomNumber = roomNumberEditText.getText().toString();
            String roomType = roomTypeEditText.getText().toString();
            boolean isAvailable = roomAvailabilityEditText.getText().toString().equals("1");

            // Create a new Room object
            Room newRoom = new Room(roomNumber, roomType, isAvailable);
            roomsRef.setValue(newRoom, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError!= null) {
                        // Handle error
                        Log.e("RoomAddError", "Error adding room: " + databaseError.getMessage());
                        Toast.makeText(Home.this, "Error adding room", Toast.LENGTH_SHORT).show();
                    } else {
                        // Room added successfully
                        Log.d("RoomAddSuccess", "Room added successfully");
                        Toast.makeText(Home.this, "Room added", Toast.LENGTH_SHORT).show();
                        // Refresh the list to reflect the new room
                        roomList.add(newRoom);
                        roomAdapter.notifyDataSetChanged();
                    }
                }
            });
        });
    }
}
