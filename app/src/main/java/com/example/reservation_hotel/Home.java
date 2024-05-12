package com.example.reservation_hotel;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.EditText;
import android.widget.Button;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private RecyclerView recyclerViewRooms;
    private FloatingActionButton fabAddRoom;
    private List<Room> roomList = new ArrayList<>();
    private RoomAdapter roomAdapter;
    private DatabaseReference roomsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerViewRooms = findViewById(R.id.recyclerViewRooms);
        Button fabAddRoom = findViewById(R.id.button);
        EditText roomNumberEditText = findViewById(R.id.roomNumberEditText);
        EditText roomTypeEditText = findViewById(R.id.roomTypeEditText);
        EditText roomAvailabilityEditText= findViewById(R.id.roomAvailabilityEditText);
        recyclerViewRooms.setLayoutManager(new LinearLayoutManager(this));
        roomAdapter = new RoomAdapter(roomList);
        recyclerViewRooms.setAdapter(roomAdapter);

        roomsRef = FirebaseDatabase.getInstance().getReference("rooms");
        printRooms();
        fabAddRoom.setOnClickListener(v -> {
            // Retrieve values from EditText fields
            String roomNumber = roomNumberEditText.getText().toString();
            String roomType = roomTypeEditText.getText().toString();
            boolean isAvailable = roomAvailabilityEditText.getText().toString().equals("1");

            // Create a new Room object
            Room newRoom = new Room(roomNumber, roomType, isAvailable);

            // Add the new room to Firebase and update the local list

            addRoomToDatabase(newRoom);
            printRooms(); // Ensure the UI is updated after adding a room
        });
    }

    private void printRooms() {
        // Retrieve all rooms from the database
        roomsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                roomList.clear(); // Clear the current list
                for (DataSnapshot roomSnapshot : snapshot.getChildren()) {
                    Room room = roomSnapshot.getValue(Room.class);
                    roomList.add(room);
                }
                // Update the adapter with the new list
                roomAdapter.notifyDataSetChanged();
                // Display the list of rooms in the activity
                recyclerViewRooms.scrollToPosition(roomList.size() - 1); // Scroll to the last item
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
                Log.e("RoomListError", "Error retrieving room list: " + error.getMessage());
                Toast.makeText(Home.this, "Error retrieving room list", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addRoomToDatabase(Room newRoom) {
        // Generate a new unique ID for the room
        String roomId = roomsRef.push().getKey();
        DatabaseReference roomRef = roomsRef.child(roomId);

        // Set the room data in Firebase
        roomRef.setValue(newRoom, new DatabaseReference.CompletionListener() {
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
                    // Add the new room to the local list
                    roomList.add(newRoom);
                    // Notify the adapter that the data set has changed
                    roomAdapter.notifyDataSetChanged();
                    // Print the updated list of rooms in the activity
                    printRooms();
                }
            }
        });
    }
}
