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


public class JoinAllEventsHomeScreen extends Fragment {


    RecyclerView recyclerView2;
    MainAdapter2 mainAdapter2;
    FirebaseAuth auth;


    public JoinAllEventsHomeScreen() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_join_all_events_home_screen, container, false);

        auth = FirebaseAuth.getInstance();
        recyclerView2 = view.findViewById(R.id.recyclerView2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseRecyclerOptions<MainModel> options = new FirebaseRecyclerOptions.Builder<MainModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("eventDetailsForHost"),MainModel.class)
                .build();

        mainAdapter2 = new MainAdapter2(options);
        recyclerView2.setAdapter(mainAdapter2);

        return view;
    }

    @Override
    public void onStart() {
        mainAdapter2.startListening();
        super.onStart();
    }

    @Override
    public void onStop() {
        mainAdapter2.stopListening();
        super.onStop();
    }
}