package com.example.splashactivity;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.ViewHolder> {

    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position, @NonNull MainModel mainModel) {

        holder.eventNamingInRecycleView.setText(mainModel.getEventName());
        holder.eventDateInRecycleView.setText(mainModel.getFromDate());
        holder.eventDetailsInRecycleView.setText(mainModel.getEventDetails());

        holder.deletingEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Do you want to delete this item?")
                        .setMessage("Once you delete, it will be permanently delte from our database")
                        .setCancelable(false)
                        .setNegativeButton("CANCEL", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .setPositiveButton("DELETE" , (dialog, which) -> {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference().child("eventDetailsForHost")
                                    .child(Objects.requireNonNull(getRef(holder.getBindingAdapterPosition()).getKey()));

                            reference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(v.getContext(), "Delete Permanently",Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(v.getContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            });
                        });
                AlertDialog dialog = builder.create();
                dialog.setOnShowListener(dialog1 -> {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                            .setTextColor(v.getContext().getResources().getColor(android.R.color.holo_red_dark));

                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                            .setTextColor(v.getContext().getResources().getColor(android.R.color.holo_blue_dark));
                });
                dialog.show();
            }
        });

    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
       return new ViewHolder(itemView);
    }




    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView eventNamingInRecycleView ,eventDetailsInRecycleView, eventDateInRecycleView;

        ImageView deletingEventButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            eventNamingInRecycleView = itemView.findViewById(R.id.eventNamingInRecycleView);
            eventDetailsInRecycleView = itemView.findViewById(R.id.eventDetailsInRecycleView);
            eventDateInRecycleView = itemView.findViewById(R.id.eventDateInRecycleView);
            deletingEventButton = itemView.findViewById(R.id.deletingEventButton);
        }
    }
}
