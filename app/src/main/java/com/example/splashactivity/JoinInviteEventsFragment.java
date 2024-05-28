package com.example.splashactivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class JoinInviteEventsFragment extends Fragment {

    RecyclerView recyclerView3;
    MainAdapter3 mainAdapter3;
    FirebaseAuth auth;


    public JoinInviteEventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_join_invite_events, container, false);
        auth = FirebaseAuth.getInstance();
        recyclerView3 =view.findViewById(R.id.recyclerViewForInviteCameFromHost);
        recyclerView3.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseRecyclerOptions<MainModel> options = new FirebaseRecyclerOptions.Builder<MainModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("eventDetailsForHost"),MainModel.class)
                .build();

        mainAdapter3 = new MainAdapter3(options);
        recyclerView3.setAdapter(mainAdapter3);


        return view;

    }

    @Override
    public void onStart() {
        mainAdapter3.startListening();
        super.onStart();
    }

    @Override
    public void onStop() {
        mainAdapter3.stopListening();
        super.onStop();
    }

}