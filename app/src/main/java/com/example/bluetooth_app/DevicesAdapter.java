package com.example.bluetooth_app;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DevicesAdapter  extends RecyclerView.Adapter<DevicesAdapter.PairedDevicesHolder> {
   private ArrayList<String> pairedDevicesNameList=null;

    public DevicesAdapter(ArrayList<String> pairedDevicesNameList) {
      this.pairedDevicesNameList=pairedDevicesNameList;
    }
    public void updateList( ArrayList<String> newPairedDevicesNameList){
        ArrayList listCopy = new ArrayList(newPairedDevicesNameList);
        newPairedDevicesNameList.clear();
        this.pairedDevicesNameList.addAll(listCopy);
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public DevicesAdapter.PairedDevicesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device_item, parent, false);
        return new DevicesAdapter.PairedDevicesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PairedDevicesHolder holder, int position) {

        holder.pairedDeviceNameTxt.setText(pairedDevicesNameList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return pairedDevicesNameList.size();
    }
    public class PairedDevicesHolder extends RecyclerView.ViewHolder {
        TextView pairedDeviceNameTxt;
        public PairedDevicesHolder(View itemView) {
            super(itemView);
            pairedDeviceNameTxt = itemView.findViewById(R.id.pairedDeviceName_txt);
        }
    }
}


