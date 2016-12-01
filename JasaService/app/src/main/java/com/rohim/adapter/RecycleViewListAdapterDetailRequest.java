package com.rohim.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.rohim.common.DatabaseHelper;
import com.rohim.common.PopupInput;
import com.rohim.enumeration.EnumInputService;
import com.rohim.fragment.FragmentMapLocationCapture;
import com.rohim.jasaservice.R;
import com.rohim.modal.DropDownList;
import com.rohim.modal.RequestDetail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nurochim on 08/10/2016.
 */

public class RecycleViewListAdapterDetailRequest extends RecyclerView.Adapter<RecycleViewListAdapterDetailRequest.MyViewHolderItemRequest>{

    Context context;
    LayoutInflater inflater;
    List<RequestDetail> listData = new ArrayList<>();
    FragmentManager fragmentManager;
    public Dao<DropDownList, Integer> dropDownListDao = null;
    public DatabaseHelper dbh ;

    public RecycleViewListAdapterDetailRequest(Context context, List<RequestDetail> listData, FragmentManager fragmentManager) {
        this.context = context;
        this.listData = listData;
        this.fragmentManager = fragmentManager;
    }
    @Override
    public MyViewHolderItemRequest onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.recycle_view_form_detail, parent, false);
        return new MyViewHolderItemRequest(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolderItemRequest holder, int position) {
        RequestDetail requestDetail = listData.get(position);
        holder.textItemDetail.setText(requestDetail.getServiceItemName());
        holder.textItemValue.setText(requestDetail.getServiceItemValue());
        holder.textSatuan.setText(requestDetail.getSatuan());
        holder.requestDetail = requestDetail;
        if(requestDetail.getJenisInput().equals(EnumInputService.Map.getVal())){
            SharedPreferences prefs = context.getSharedPreferences("ReUse_Variable", Context.MODE_PRIVATE);
            holder.textItemValue.setText(prefs.getString("Address",""));
        }
        initDialogItem(requestDetail, holder.spinner);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class MyViewHolderItemRequest extends RecyclerView.ViewHolder {
        TextView textItemDetail, textItemValue, textSatuan;
        Spinner spinner;
        RequestDetail requestDetail;
        PopupInput popupInput;

        public MyViewHolderItemRequest(View itemView) {
            super(itemView);
            textItemDetail = (TextView) itemView.findViewById(R.id.text_detail);
            textItemValue = (TextView) itemView.findViewById(R.id.text_value);
            textSatuan = (TextView) itemView.findViewById(R.id.text_satuan);
            spinner = (Spinner) itemView.findViewById(R.id.spinner_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(requestDetail.getJenisInput().equals(EnumInputService.SpinnerInput.getVal())) {
                        spinner.performClick();
                    }
                    else if(requestDetail.getJenisInput().equals(EnumInputService.TextLong.getVal())
                            ||requestDetail.getJenisInput().equals(EnumInputService.TextAutomatic.getVal())) {
                        popupInput = new PopupInput();
                        popupInput.setParam(context, textItemValue, requestDetail);
                        popupInput.show(fragmentManager,"");
                    }if(requestDetail.getJenisInput().equals(EnumInputService.Map.getVal())){
                        FragmentMapLocationCapture mapLocationCapture = new FragmentMapLocationCapture();
                        mapLocationCapture.setmLocationText(textItemValue);
                        FragmentTransaction fragmentTrans = fragmentManager.beginTransaction();
                        fragmentTrans.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                        fragmentTrans.replace(R.id.content_main, mapLocationCapture);
                        fragmentTrans.addToBackStack(null).commit();
                    }

                }
            });
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(parent.getItemAtPosition(position)!=null) {
                        String selectedSpinner = parent.getItemAtPosition(position).toString();
                        textItemValue.setText(selectedSpinner);
                        requestDetail.setServiceItemValue(selectedSpinner);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    private void initDialogItem(RequestDetail requestDetail, Spinner spinner){
        if(requestDetail.getJenisInput().equals(EnumInputService.SpinnerInput.getVal())){
            List<String> spinnerData = new ArrayList<>();
            CharSequence prompt = "";
            try {
                dbh = new DatabaseHelper(context);
                dropDownListDao = dbh.getDropDownListDao();
                List<DropDownList> listDropDown = dropDownListDao.queryForEq(DropDownList.clm_fid_service_item, requestDetail.getFidServiceItem());

                prompt = "Pilih " + requestDetail.getServiceItemName();
                for(DropDownList dw: listDropDown){
                    spinnerData.add(dw.getDescription());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            spinner.setPrompt(prompt);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, spinnerData);
            adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
            spinner.setAdapter(new NothingSelectedSpinnerAdapter(adapter,R.layout.spinner_not_selected,context));


        }
    }

}
