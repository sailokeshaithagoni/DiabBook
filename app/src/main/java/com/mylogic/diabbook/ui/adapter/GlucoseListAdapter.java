package com.mylogic.diabbook.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mylogic.diabbook.R;
import com.mylogic.diabbook.data.ModelGlucoseData;

import java.util.List;

public class GlucoseListAdapter extends RecyclerView.Adapter<GlucoseListAdapter.ViewHolder>{
    private List<ModelGlucoseData> listdata;

    // RecyclerView recyclerView;
    public GlucoseListAdapter(List<ModelGlucoseData> listdata) {
        this.listdata = listdata;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ModelGlucoseData modelGlucoseData = listdata.get(position);
        holder.txtDate.setText(listdata.get(position).getTxtDateVal());
        holder.txtTime.setText(listdata.get(position).getTxtTimeVal());
        holder.txtFoodStatus.setText(listdata.get(position).getTxtFoodStatusVal());
        holder.txtGlucoseVal.setText(listdata.get(position).getTxtGlucoseVal());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"click on item: "+ modelGlucoseData.getTxtDateVal(),Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtDate;
        public TextView txtTime;
        public TextView txtFoodStatus;
        public TextView txtGlucoseVal;
        public LinearLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            this.txtTime = (TextView) itemView.findViewById(R.id.txtTime);
            this.txtFoodStatus =(TextView) itemView.findViewById(R.id.txtFoodStatus);
            this.txtGlucoseVal =(TextView) itemView.findViewById(R.id.txtGlucoseValue);
            relativeLayout = (LinearLayout) itemView.findViewById(R.id.relativeLayout);
        }
    }
}