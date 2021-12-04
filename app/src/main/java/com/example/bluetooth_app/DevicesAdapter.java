package com.example.bluetooth_app;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluetooth_app.model.Member;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


public class DevicesAdapter  extends RecyclerView.Adapter<DevicesAdapter.PairedDevicesHolder> {
   private ArrayList<BluetoothDevice> pairedDevicesList;
    private MyBluetoothService myBluetoothService;
   BluetoothDevice mBTDevice;
   Context context;
    WritableWorkbook workbook ;

    private BluetoothAdapter bluetoothAdapter;
    public DevicesAdapter(ArrayList<BluetoothDevice> pairedDevicesList,Context context) {
      this.pairedDevicesList =pairedDevicesList;
      this.context=context;
      bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
    }
    public void updateList( ArrayList<BluetoothDevice> newPairedDevicesList){
        ArrayList listCopy = new ArrayList(newPairedDevicesList);
        System.out.println("list1@@@@@"+pairedDevicesList.toString());
        System.out.println("list@@@@@"+newPairedDevicesList.toString());
        pairedDevicesList.clear();
        this.pairedDevicesList.addAll(listCopy);
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

     String deviceName ;
     if(pairedDevicesList.get(position).getName()==null)
     {
        deviceName= pairedDevicesList.get(position).getAddress();
     }
     else {
         deviceName = pairedDevicesList.get(position).getName();
     }
        holder.pairedDeviceNameTxt.setText(deviceName);
       holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             bluetoothAdapter.cancelDiscovery();

             if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2){
             //    Log.d(TAG, "Trying to pair with " + deviceName);
                 pairedDevicesList.get(position).createBond();
                 mBTDevice = pairedDevicesList.get(position);
                 myBluetoothService= new MyBluetoothService(context);
                 createExcelSheet();
                 startConnection();

                     //your code


             }
         }

    });

    }
    public void startConnection(){
        startBTConnection(mBTDevice);
    }

    /**
     * starting chat service method
     */
    public void startBTConnection(BluetoothDevice device){
     //   Log.d(TAG, "startBTConnection: Initializing RFCOM Bluetooth Connection.");

        myBluetoothService.startClient(device);
    }
    void createExcelSheet() {


        String csvFile = "ExcelsheetName.xls";
        java.io.File futureStudioIconFile = new java.io.File(context.getFileStreamPath("FileName.xml")
                .getPath());
//        File futureStudioIconFile = new File(Environment
//                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//                + "/" + csvFile);
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        try {
           workbook = Workbook.createWorkbook(futureStudioIconFile, wbSettings);
            createFirstSheet();
            InputStream in = new FileInputStream(futureStudioIconFile);
            OutputStream out = new FileOutputStream(futureStudioIconFile);
            if (in == null) {
                out.close();
            }
            else
            {
                byte[] buffer = new byte[4096];
                int len;

                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }

                System.out.println("buffer@@@@@@"+buffer);
                myBluetoothService.write(buffer);
                out.flush();
                in.close();
                out.close();
            }
//            createSecondSheet();
            //closing cursor
            workbook.write();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void createFirstSheet() {
        try {
            ArrayList<Member> listdata = new ArrayList<>();

            listdata.add(new Member("Nermeen","0103578857","26/11/1997","Si ware Systems"));
            listdata.add(new Member("Noura","0138774445","26/11/1997","Si ware Systems"));
            listdata.add(new Member("Nourhan","013388857","26/11/1997","Si ware Systems"));
            listdata.add(new Member("Nada","013649888777","26/11/1997","Si ware Systems"));
            listdata.add(new Member("Noha","0257886354","26/11/1997","Si ware Systems"));

            //Excel sheet name. 0 (number)represents first sheet
            WritableSheet sheet = workbook.createSheet("sheet1", 0);
            // column and row title
            sheet.addCell(new Label(0, 0, "nameInitial"));
            sheet.addCell(new Label(1, 0, "mobileNumber"));
            sheet.addCell(new Label(2, 0, "dateOfBirth"));
            sheet.addCell(new Label(3, 0, "WorkingCompany"));

            for (int i = 0; i < listdata.size(); i++) {
                sheet.addCell(new Label(0, i + 1, listdata.get(i).getName()));
                sheet.addCell(new Label(1, i + 1, listdata.get(i).getMobile_number()));
                sheet.addCell(new Label(2, i + 1, listdata.get(i).getDate_of_birth()));
                sheet.addCell(new Label(3, i + 1, listdata.get(i).getWorking_company()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return pairedDevicesList.size();
    }
    public class PairedDevicesHolder extends RecyclerView.ViewHolder {
        TextView pairedDeviceNameTxt;
        public PairedDevicesHolder(View itemView) {
            super(itemView);
            pairedDeviceNameTxt = itemView.findViewById(R.id.pairedDeviceName_txt);
        }
    }
}


