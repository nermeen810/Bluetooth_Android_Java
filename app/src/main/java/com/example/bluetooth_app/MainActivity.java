package com.example.bluetooth_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bluetooth_app.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MainActivity extends AppCompatActivity {
    private BluetoothAdapter bluetoothAdapter;
    private final static int REQUEST_ENABLE_BT=1;
    private  DevicesAdapter devicesAdapter;
    ArrayList<String> pairedDevicesNameList = new ArrayList();

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        setUpUi();
    }



    private void setUpUi() {
        binding.deviceNameValue.setText( getBluetoothName());
        setupRecycleView();
        binding.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    binding.constraint2.setVisibility(View.VISIBLE);
                    enableBlutooth();
                    getpairedDevices();
                    showMessage(pairedDevicesNameList.toString());
                    devicesAdapter.updateList(pairedDevicesNameList);

                }
                else {
                    binding.constraint2.setVisibility(View.GONE);
                    pairedDevicesNameList.add("item");
                    disableBlutooth();
                }
            }
        });
    }


    private void setupRecycleView() {
        devicesAdapter = new DevicesAdapter(pairedDevicesNameList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        binding.pairedDevicesRecycleView.setLayoutManager(linearLayoutManager);
        binding.pairedDevicesRecycleView.setAdapter(devicesAdapter);
    }

    private String getBluetoothName() {
        String bluetoothName = bluetoothAdapter.getName();
        if(bluetoothName == null){
            bluetoothName = bluetoothAdapter.getAddress();
        }
        return bluetoothName;
    }

    private void disableBlutooth() {
        if(isBluetoothSupported()){
        bluetoothAdapter.disable();
        showMessage("Bluetooth is disabled");
     }
    }

    private void enableBlutooth() {
        if(isBluetoothSupported())
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            showMessage("Bluetooth is enabled");
        }
    }

    private boolean isBluetoothSupported() {

        if (bluetoothAdapter == null) {
            showMessage("Bluetooth Not Supported");
            return false;
        }
        else {
            return true;
        }
    }

    private void getpairedDevices() {
        Set<BluetoothDevice> pairedDevicesSet = bluetoothAdapter.getBondedDevices();
     //   showMessage(pairedDevicesSet.toString());
        if (pairedDevicesSet.size() > 0) {
            String deviceName =null;
            for (BluetoothDevice device : pairedDevicesSet) {
                deviceName=null;
                deviceName = device.getName();
              //  showMessage(device.getName());
                if(deviceName==null){
                    deviceName=device.getAddress();
                //    showMessage(device.getAddress());
                }
                pairedDevicesNameList.add(deviceName);
                //  String deviceHardwareAddress = device.getAddress(); // MAC address
            }
        }
    }
   private void showMessage(String msg){
       Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();

   }
}