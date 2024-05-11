package com.example.reservation_hotel;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class HomeCopy extends AppCompatActivity {

    private RecyclerView recyclerViewRooms;
    private FloatingActionButton fabAddRoom;
    private List<Room> roomList = new ArrayList<>();
    private RoomAdapter roomAdapter;
    private FirebaseFirestore db; // Use FirebaseFirestore instead of DatabaseReference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_copy);

        recyclerViewRooms = findViewById(R.id.recyclerViewRooms);
        fabAddRoom = findViewById(R.id.fabAddRoom);
        EditText roomNumberEditText = findViewById(R.id.roomNumberEditText);
        EditText roomTypeEditText = findViewById(R.id.roomTypeEditText);
        EditText roomAvailabilityEditText = findViewById(R.id.roomAvailabilityEditText);
        recyclerViewRooms.setLayoutManager(new LinearLayoutManager(this));
        roomAdapter = new RoomAdapter(roomList);
        recyclerViewRooms.setAdapter(roomAdapter);

        db = FirebaseFirestore.getInstance(); // Initialize FirebaseFirestore
        fabAddRoom.setOnClickListener(v -> {
            // Add room functionality
            // Retrieve values from EditText fields
            String roomNumber = roomNumberEditText.getText().toString();
            String roomType = roomTypeEditText.getText().toString();
            boolean isAvailable = roomAvailabilityEditText.getText().toString().equals("1");

            // Create a new Room object
            Room newRoom = new Room(roomNumber, roomType, isAvailable);

            // Add the new room to Firestore
            DocumentReference roomRef = db.collection("rooms").document();
            roomRef.set(newRoom)
                    .addOnSuccessListener(aVoid -> {
                        Log.d("RoomAddSuccess", "Room added successfully");
                        Toast.makeText(HomeCopy.this, "Room added", Toast.LENGTH_SHORT).show();
                        // Refresh the list to reflect the new room
                        roomList.add(newRoom);
                        roomAdapter.notifyDataSetChanged();
                    })
                    .addOnFailureListener(e -> {
                        Log.e("RoomAddError", "Error adding room: ", e);
                        Toast.makeText(HomeCopy.this, "Error adding room", Toast.LENGTH_SHORT).show();
                    });
        });
    }
}
