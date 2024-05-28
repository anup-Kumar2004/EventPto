package com.example.splashactivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainAdapter2 extends FirebaseRecyclerAdapter<MainModel, MainAdapter2.ViewHolder> {

    FirebaseAuth auth;
    DatabaseReference usersRef;

    public MainAdapter2(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
        auth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference().child("users");
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull MainModel mainModel) {
        holder.abc.setText(mainModel.getEventName());
        holder.cde.setText(mainModel.getFromDate());
        holder.def.setText(mainModel.getEventDetails());

        holder.joinEventButtonInJoinUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = auth.getCurrentUser();
                if (currentUser != null) {
                    String uid = currentUser.getUid();
                    usersRef.child(uid).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String userName = snapshot.getValue(String.class);
                                if (userName != null) {
                                    // Store the user's name under "participantsOfHost"
                                    String eventId = getRef(holder.getAdapterPosition()).getKey(); // Get the updated position
                                    if (eventId != null) {
                                        String participantKey = FirebaseDatabase.getInstance().getReference().push().getKey();


                                        FirebaseDatabase.getInstance().getReference().child("participantsOfHost").child(eventId).child(participantKey).setValue(userName);
                                        Toast.makeText(v.getContext(), "Joined event successfully!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(v.getContext(), "Failed to join event: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView abc, cde, def;
        Button joinEventButtonInJoinUser;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            abc = itemView.findViewById(R.id.abc);
            cde = itemView.findViewById(R.id.cde);
            def = itemView.findViewById(R.id.def);
            joinEventButtonInJoinUser = itemView.findViewById(R.id.joinEventButtonInJoinUser);
        }
    }
}
