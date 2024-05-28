package com.example.splashactivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MainAdapter3 extends FirebaseRecyclerAdapter<MainModel , MainAdapter3.ViewHolder> {
    public MainAdapter3(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MainAdapter3.ViewHolder holder, int position, @NonNull MainModel mainModel) {

        holder.item2ElementA.setText(mainModel.getEventName());
        holder.item2ElementB.setText(mainModel.getFromDate());
        holder.item2ElementC.setText(mainModel.getEventDetails());
        holder.item2ElementD.setText(mainModel.getName());

    }

    @NonNull
    @Override
    public MainAdapter3.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item2,parent,false));
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView item2ElementA, item2ElementB ,item2ElementC , item2ElementD ;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item2ElementA = itemView.findViewById(R.id.item2ElementA);
            item2ElementB = itemView.findViewById(R.id.item2ElementB);
            item2ElementC = itemView.findViewById(R.id.item2ElementC);
            item2ElementD = itemView.findViewById(R.id.item2ElementD);


        }
    }
}
