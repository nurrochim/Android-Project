package com.rohim.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rohim.fragment.FragmentInputRequestDetail;
import com.rohim.jasaservice.MainActivity;
import com.rohim.jasaservice.R;
import com.rohim.modal.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nurochim on 08/10/2016.
 */

public class RecycleViewListAdapterMenu extends RecyclerView.Adapter<RecycleViewListAdapterMenu.MyViewHolder>{

    Context context;
    LayoutInflater inflater;
    List<Service> listData = new ArrayList<>();
    public static String selectedJasa = "";
    public FragmentManager fragmentManager;

    public RecycleViewListAdapterMenu(Context context, List<Service> listData, FragmentManager fragmentManager) {
        this.context = context;
        this.listData = listData;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.recycle_view_menu, parent, false);
//        TextView jasaServiceMenus = (TextView) itemView.findViewById(R.id.text_jasa_service_item);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.jasaServiceMenu.setText(listData.get(position).getServiceName());
        holder.idService = listData.get(position).getIdService();
        switch (position){
            case 0 : holder.image.setImageResource(R.drawable.service_ac1);
                break;
            case 1 : holder.image.setImageResource(R.drawable.service_komputer);
                break;
            case 2 : holder.image.setImageResource(R.drawable.service_mobil);
                break;
            case 3 : holder.image.setImageResource(R.drawable.service_motor);
                break;
            case 4 : holder.image.setImageResource(R.drawable.service_ban_mobil);
                break;
            case 5 : holder.image.setImageResource(R.drawable.ban_motor);
                break;

        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView jasaServiceMenu;
        ImageView image;
        String idService = "";

        public MyViewHolder(View itemView){
            super(itemView);
            jasaServiceMenu = (TextView) itemView.findViewById(R.id.text_jasa_service_item);
            image = (ImageView) itemView.findViewById(R.id.image_jasa_service_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentInputRequestDetail fragment = new FragmentInputRequestDetail();
                    fragment.setIdService(idService);
                    FragmentTransaction fragmentTrans = fragmentManager.beginTransaction();
                    fragmentTrans.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                    fragmentTrans.replace(R.id.content_main, fragment);
                    fragmentTrans.addToBackStack(null).commit();
                    MainActivity.textTitle.setText(jasaServiceMenu.getText().toString());
                }
            });
        }

    }

}
