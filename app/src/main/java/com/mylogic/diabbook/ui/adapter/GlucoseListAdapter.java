package com.mylogic.diabbook.ui.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.mylogic.diabbook.R;
import com.mylogic.diabbook.data.ModelGlucoseData;
import java.util.List;

public class GlucoseListAdapter extends RecyclerView.Adapter<GlucoseListAdapter.ViewHolder>{
    private List<ModelGlucoseData> listData;

    // RecyclerView recyclerView;
    public GlucoseListAdapter(List<ModelGlucoseData> listData) {
        this.listData = listData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_reading, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ModelGlucoseData modelGlucoseData = listData.get(position);
        holder.txtDate.setText(listData.get(position).getTxtDateVal());
        holder.txtTime.setText(listData.get(position).getTxtTimeVal());
        holder.txtMealStatus.setText(listData.get(position).getTxtMealStatusVal());
        holder.txtFoodStatus.setText(listData.get(position).getTxtFoodStatusVal());
        holder.txtGlucoseVal.setText(listData.get(position).getTxtGlucoseVal());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"click on item: "+ modelGlucoseData.getTxtDateVal(),Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Are you sure you want to delete this item?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Code to delete item goes here
                    }
                });
                builder.setNegativeButton("No", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtDate;
        public TextView txtTime;
        public TextView txtFoodStatus;
        public TextView txtMealStatus;
        public TextView txtGlucoseVal;
        public ConstraintLayout constraintLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            this.txtTime = (TextView) itemView.findViewById(R.id.txtTime);
            this.txtMealStatus = (TextView) itemView.findViewById(R.id.txtMealStatus);
            this.txtFoodStatus =(TextView) itemView.findViewById(R.id.txtFoodStatus);
            this.txtGlucoseVal =(TextView) itemView.findViewById(R.id.txtGlucoseValue);
            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.reading_layout);
        }
    }
}