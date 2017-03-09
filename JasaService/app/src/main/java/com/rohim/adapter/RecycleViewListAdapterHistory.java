package com.rohim.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.rohim.modal.RequestOrder;
import com.rohim.modal.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nurochim on 08/10/2016.
 */

public class RecycleViewListAdapterHistory extends RecyclerView.Adapter<RecycleViewListAdapterHistory.MyViewHolderItemRequest>{

    Context context;
    LayoutInflater inflater;
    List<RequestOrder> listData = new ArrayList<>();
    FragmentManager fragmentManager;
    public Dao<Service, String> serviceDAO = null;
    public DatabaseHelper dbh ;
    boolean enabled;

    public RecycleViewListAdapterHistory(Context context, List<RequestOrder> listData, FragmentManager fragmentManager, boolean enabled) {
        this.context = context;
        this.listData = listData;
        this.fragmentManager = fragmentManager;
        this.enabled = enabled;
    }
    @Override
    public MyViewHolderItemRequest onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.recycle_view_form_history, parent, false);
        return new MyViewHolderItemRequest(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolderItemRequest holder, int position) {
        RequestOrder requestOrder = listData.get(position);
        holder.textServiceName.setText(getServiceName(requestOrder.getFidService()));
        holder.textUserName.setText(requestOrder.getUserName());
        holder.textHasilService.setText(requestOrder.getHasilService());
        holder.textUlasan.setText(requestOrder.getFinishCommentUser());
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
        TextView textServiceName, textUserName, textHasilService, textUlasan;

        public MyViewHolderItemRequest(View itemView) {
            super(itemView);
            textServiceName = (TextView) itemView.findViewById(R.id.txt_service_name_history);
            textUserName = (TextView) itemView.findViewById(R.id.txt_user_history);
            textHasilService = (TextView) itemView.findViewById(R.id.txt_hasil_service_history);
            textUlasan = (TextView) itemView.findViewById(R.id.txt_ulasan_history);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }

    private String getServiceName(String idService){
        String serviceName = "";
        try {
            dbh = new DatabaseHelper(context);
            serviceDAO = dbh.getServiceDao();
            Service service = serviceDAO.queryForId(idService);
            serviceName = service.getServiceName();

        } catch (SQLException e) {
            Log.e("Insert Error", e.toString());
        }finally {
            dbh.close();
        }
        return serviceName;
    }
}
