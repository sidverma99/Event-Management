package com.example.zavaso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private Context context;
    private List<Event> eventList;
    public EventAdapter(Context context, List<Event> eventList){
        this.context=context;
        this.eventList=eventList;
    }
    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.event_row,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder holder, int position) {
        Event event=eventList.get(position);
        holder.date.setText(event.getDate());
        holder.location.setText(event.getEventLocation());
        holder.name.setText(event.getEventName());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name,location,date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.event_name);
            location=itemView.findViewById(R.id.eventLocation);
            date=itemView.findViewById(R.id.eventDate);
        }
    }
}
