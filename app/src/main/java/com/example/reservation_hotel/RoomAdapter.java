package com.example.reservation_hotel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private List<Room> roomList;

    public RoomAdapter(List<Room> roomList) {
        this.roomList = roomList;
    }

    @Override
    public RoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoomViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.roomNumberTextView.setText("Room Number: " + room.getRoomNumber());
        holder.typeTextView.setText("Type: " + room.getType());
        holder.availableTextView.setText("Available: " + (room.isAvailable()? "Yes" : "No"));
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    static class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView roomNumberTextView;
        TextView typeTextView;
        TextView availableTextView;

        public RoomViewHolder(View itemView) {
            super(itemView);
            roomNumberTextView = itemView.findViewById(R.id.roomNumberTextView);
            typeTextView = itemView.findViewById(R.id.typeTextView);
            availableTextView = itemView.findViewById(R.id.availableTextView);
        }
    }
}
