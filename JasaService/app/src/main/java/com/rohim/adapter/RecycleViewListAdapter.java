package com.rohim.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.rohim.common.DatabaseHelper;
import com.rohim.fragment.AddTriggerJasaService;
import com.rohim.fragment.FragmentBeforeLogin;
import com.rohim.jasaservice.R;
import com.rohim.modal.RequestAccepted;
import com.rohim.modal.RequestDetail;
import com.rohim.modal.RequestOrder;
import com.rohim.modal.ServiceProvide;
import com.rohim.modal.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Nurochim on 08/10/2016.
 */

public class RecycleViewListAdapter extends BaseAdapter{

    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<String, String>();
    List<ServiceProvide> listData = new ArrayList<>();
    Spinner spinner;
    public static String selectedJasa = "";

    public RecycleViewListAdapter(Context context, List<ServiceProvide> listData, Spinner spinner) {
        this.context = context;
        this.listData = listData;
        this.spinner = spinner;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final TextView jasa;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.recycle_view_tambah_jasa, parent, false);
        jasa = (TextView) itemView.findViewById(R.id.text_jasa);

        // get item data
        final ServiceProvide jasaData = listData.get(position);
        jasa.setText(jasaData.getServiceName());



        jasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Hapus Item")
                        .setMessage("Anda yakin akan mengahapus "+jasaData.getServiceName()+" ?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (Iterator iterator = AddTriggerJasaService.data.iterator(); iterator.hasNext();) {
                                    ServiceProvide deleteRow = (ServiceProvide) iterator.next();
                                    if(deleteRow.getServiceName().equalsIgnoreCase(jasaData.getServiceName())){
                                        doDelete(deleteRow.getIdServiceProvide());
                                        AddTriggerJasaService.refreshAdapter();
                                    }
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();

//                if(jasaData.getServiceName().equalsIgnoreCase("Tambah Jasa....")) {
//                    spinner.performClick();
//                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//                        @Override
//                        public void onItemSelected(AdapterView<?> parent, View view,
//                                                   int position, long id) {
//                                 selectedJasa = parent.getItemAtPosition(position).toString();
//                                 jasa.setText(selectedJasa);
//                                 for (TambahJasa js : AddTriggerJasaService.data){
//                                    if(js.getServiceName().equalsIgnoreCase(jasaData.getServiceName())) {
//                                        js.setServiceName(selectedJasa);
//                                 }}
//                                TambahJasa item2 = new TambahJasa();
//                                item2.setServiceName("Tambah Jasa....");
//                                AddTriggerJasaService.data.add(item2);
//                                AddTriggerJasaService.refreshAdapter();
//
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parent) {
//
//                        }
//                    });
//                }

                /*for (TambahJasa js : listData){
                    if(js.getServiceName().equalsIgnoreCase(jasaData.getServiceName())){
                        js.setServiceName(selectedJasa);
                    }
                }*/
                /*CharSequence text = "Click item !";
                Toast.makeText(context,text, Toast.LENGTH_SHORT).show();*/
            }
        });
        return itemView;
    }


    private void doDelete(String id){
        DatabaseHelper dbh = new DatabaseHelper(context);
        SQLiteDatabase db = dbh.getWritableDatabase();
        try {
            db.execSQL("DELETE FROM "+ ServiceProvide.tbl_service_provice +" WHERE "+ServiceProvide.clm_id_service_provide+" = '"+id+"'");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.close();
        }
    }
}
