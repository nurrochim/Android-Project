package com.rohim.common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.rohim.enumeration.EnumInputService;
import com.rohim.modal.DropDownList;
import com.rohim.modal.HistoryRequest;
import com.rohim.modal.ReasonList;
import com.rohim.modal.RequestAccepted;
import com.rohim.modal.RequestDetail;
import com.rohim.modal.RequestOrder;
import com.rohim.modal.Service;
import com.rohim.modal.ServiceItem;
import com.rohim.modal.ServiceProvide;
import com.rohim.modal.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus on 24/10/2016.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME    = "JS.db";
    private static final int    DATABASE_VERSION = 4;
    private Dao<Service, String> serviceDao = null;
    private Dao<ServiceItem, Integer> serviceItemDao = null;
    private Dao<DropDownList, Integer> dropdownListDao = null;
    private Dao<RequestOrder, String> requestOrderDao = null;
    private Dao<RequestAccepted, String> requestAcceptedDao = null;
    private Dao<RequestDetail, String> requestDetailDao = null;
    private Dao<HistoryRequest, String> historyRequestDao = null;
    private Dao<ReasonList, Integer> reasonDao = null;
    private Dao<ServiceProvide, String> serviceProvideDao = null;
    private Dao<User, String> userDao = null;

    private RuntimeExceptionDao<Service, ?> mService;
    private RuntimeExceptionDao<ServiceItem, ?> mServiceItem;
    private RuntimeExceptionDao<DropDownList, ?> mDropdown;
    private RuntimeExceptionDao<RequestAccepted, ?> mRequestAccepted;
    private RuntimeExceptionDao<RequestOrder, ?> mRequestOrder;
    private RuntimeExceptionDao<RequestDetail, ?> mRequestDetail;
    private RuntimeExceptionDao<HistoryRequest, ?> mHistoryRequest;
    private RuntimeExceptionDao<ReasonList, ?> mReason;
    private RuntimeExceptionDao<ServiceProvide, ?> mServiceProvide;
    private RuntimeExceptionDao<User, ?> mUser;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Service.class);
            TableUtils.createTable(connectionSource, ServiceItem.class);
            TableUtils.createTable(connectionSource, DropDownList.class);
            TableUtils.createTable(connectionSource, RequestDetail.class);
            TableUtils.createTable(connectionSource, RequestOrder.class);
            TableUtils.createTable(connectionSource, RequestAccepted.class);
            TableUtils.createTable(connectionSource, HistoryRequest.class);
            TableUtils.createTable(connectionSource, ReasonList.class);
            TableUtils.createTable(connectionSource, ServiceProvide.class);
            TableUtils.createTable(connectionSource, User.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Service.class, true);
            TableUtils.dropTable(connectionSource, ServiceItem.class, true);
            TableUtils.dropTable(connectionSource, DropDownList.class, true);
            TableUtils.dropTable(connectionSource, RequestDetail.class, true);
            TableUtils.dropTable(connectionSource, RequestOrder.class, true);
            TableUtils.dropTable(connectionSource, RequestAccepted.class, true);
            TableUtils.dropTable(connectionSource, HistoryRequest.class, true);
            TableUtils.dropTable(connectionSource, ReasonList.class, true);
            TableUtils.dropTable(connectionSource, ServiceProvide.class, true);
            TableUtils.dropTable(connectionSource, User.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        super.close();
    }

    public Dao<Service, String> getServiceDao() throws SQLException {
        if (serviceDao == null) {
            serviceDao = getDao(Service.class);
        }
        mService = getRuntimeExceptionDao(Service.class);
        return serviceDao;
    }

    public Dao<ServiceItem, Integer> getServiceItemDao() throws SQLException {
        if (serviceItemDao == null) {
            serviceItemDao = getDao(ServiceItem.class);
        }
        mServiceItem = getRuntimeExceptionDao(ServiceItem.class);
        return serviceItemDao;
    }

    public Dao<DropDownList, Integer> getDropDownListDao() throws SQLException {
        if (dropdownListDao == null) {
            dropdownListDao = getDao(DropDownList.class);
        }
        mDropdown = getRuntimeExceptionDao(DropDownList.class);
        return dropdownListDao;
    }

    public Dao<RequestDetail, String> getRequestDetailDao() throws SQLException {
        if (requestDetailDao == null) {
            requestDetailDao = getDao(RequestDetail.class);
        }
        mRequestDetail = getRuntimeExceptionDao(RequestDetail.class);
        return requestDetailDao;
    }

    public Dao<RequestOrder, String> getRequestOrderDao() throws SQLException {
        if (requestOrderDao == null) {
            requestOrderDao = getDao(RequestOrder.class);
        }
        mRequestOrder = getRuntimeExceptionDao(RequestOrder.class);
        return requestOrderDao;
    }

    public Dao<RequestAccepted, String> getRequestAcceptedDao() throws SQLException {
        if (requestAcceptedDao == null) {
            requestAcceptedDao = getDao(RequestAccepted.class);
        }
        mRequestAccepted = getRuntimeExceptionDao(RequestAccepted.class);
        return requestAcceptedDao;
    }

    public Dao<HistoryRequest, String> getHistoryRequestDao() throws SQLException {
        if (historyRequestDao == null) {
            historyRequestDao = getDao(HistoryRequest.class);
        }
        mHistoryRequest = getRuntimeExceptionDao(HistoryRequest.class);
        return historyRequestDao;
    }

    public Dao<ReasonList, Integer> getReasonDao() throws SQLException {
        if (reasonDao == null) {
            reasonDao = getDao(ReasonList.class);
        }
        mReason = getRuntimeExceptionDao(ReasonList.class);
        return reasonDao;
    }

    public Dao<ServiceProvide, String> getServiceProvideDao() throws SQLException {
        if (serviceProvideDao == null) {
            serviceProvideDao = getDao(ServiceProvide.class);
        }
        mServiceProvide = getRuntimeExceptionDao(ServiceProvide.class);
        return serviceProvideDao;
    }

    public Dao<User, String> getUserDao() throws SQLException {
        if (userDao == null) {
            userDao = getDao(User.class);
        }
        mUser = getRuntimeExceptionDao(User.class);
        return userDao;
    }


    public void AddDataService() {
        try {
            // add sample data
            for (int i = 0; i < 6; i++) {
                Service item = new Service();
                switch (i) {
                    case 0:
                        item.setIdService("1");
                        item.setServiceName("Service AC");
                        item.setIcon("service_ac1");
                        break;
                    case 1:
                        item.setIdService("2");
                        item.setIcon("service_komputer");
                        item.setServiceName("Service Komputer");
                        break;
                    case 2:
                        item.setIdService("3");
                        item.setIcon("service_mobil");
                        item.setServiceName("Service Mesin Mobil");
                        break;
                    case 3:
                        item.setIdService("4");
                        item.setIcon("service_motor");
                        item.setServiceName("Service Mesin Motor");
                        break;
                    case 4:
                        item.setIdService("5");
                        item.setIcon("service_ban_mobil");
                        item.setServiceName("Service Ban Mobil");
                        break;
                    case 5:
                        item.setIdService("6");
                        item.setIcon("ban_motor");
                        item.setServiceName("Service Ban Motor");
                        break;
                }
                serviceDao.create(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void AddDataServiceItem() {
        try {
            ServiceItem si = new ServiceItem("1", "Lokasi", EnumInputService.Map.getVal());serviceItemDao.create(si);
            si = new ServiceItem("1", "Tipe Pelayanan", EnumInputService.SpinnerInput.getVal());serviceItemDao.create(si);
            si = new ServiceItem("1", "Tipe AC", EnumInputService.SpinnerInput.getVal());serviceItemDao.create(si);
            si = new ServiceItem("1", "Merk AC", EnumInputService.SpinnerInput.getVal());serviceItemDao.create(si);
            si = new ServiceItem("1", "Kondisi AC", EnumInputService.SpinnerInput.getVal());serviceItemDao.create(si);
            si = new ServiceItem("1", "Berapa Unit AC", EnumInputService.TextAutomatic.getVal());serviceItemDao.create(si);
            si = new ServiceItem("1", "Informasi Tambahan", EnumInputService.TextLong.getVal());serviceItemDao.create(si);

            si = new ServiceItem("2", "Lokasi", EnumInputService.Map.getVal());serviceItemDao.create(si);
            si = new ServiceItem("2", "Tipe Komputer", EnumInputService.SpinnerInput.getVal());serviceItemDao.create(si);//9
            si = new ServiceItem("2", "PC atau MAC", EnumInputService.SpinnerInput.getVal());serviceItemDao.create(si);//10
            si = new ServiceItem("2", "Masalah Komputer", EnumInputService.TextLong.getVal());serviceItemDao.create(si);
            si = new ServiceItem("2", "Apa yang perlu di perbaiki", EnumInputService.TextLong.getVal());serviceItemDao.create(si);
            si = new ServiceItem("2", "Manufactur Komputer", EnumInputService.SpinnerInput.getVal());serviceItemDao.create(si);//13
            si = new ServiceItem("2", "Perangkat perusahaan atau pribadi", EnumInputService.SpinnerInput.getVal());serviceItemDao.create(si);//14
            si = new ServiceItem("2", "Sistem Operasi", EnumInputService.SpinnerInput.getVal());serviceItemDao.create(si);//15
            si = new ServiceItem("2", "Informasi Tambahan", EnumInputService.TextLong.getVal());serviceItemDao.create(si);


            si = new ServiceItem("3", "Lokasi", EnumInputService.Map.getVal());serviceItemDao.create(si);
            si = new ServiceItem("3", "Tipe Mobil", EnumInputService.SpinnerInput.getVal());serviceItemDao.create(si);//18
            si = new ServiceItem("3", "Tipe Pelayanan", EnumInputService.SpinnerInput.getVal());serviceItemDao.create(si);//19
            si = new ServiceItem("3", "Informasi Tambahan", EnumInputService.TextLong.getVal());serviceItemDao.create(si);

            si = new ServiceItem("4", "Lokasi", EnumInputService.Map.getVal());serviceItemDao.create(si);
            si = new ServiceItem("4", "Tipe Motor", EnumInputService.SpinnerInput.getVal());serviceItemDao.create(si);//22
            si = new ServiceItem("4", "Tipe Pelayanan", EnumInputService.SpinnerInput.getVal());serviceItemDao.create(si);//23
            si = new ServiceItem("4", "Informasi Tambahan", EnumInputService.TextLong.getVal());serviceItemDao.create(si);

            si = new ServiceItem("5", "Lokasi", EnumInputService.Map.getVal());serviceItemDao.create(si);
            si = new ServiceItem("5", "Jenis Ban Mobil", EnumInputService.SpinnerInput.getVal());serviceItemDao.create(si);//26
            si = new ServiceItem("5", "Tipe Ban Mobil", EnumInputService.SpinnerInput.getVal());serviceItemDao.create(si);//27
            si = new ServiceItem("5", "Tipe Pelayanan", EnumInputService.SpinnerInput.getVal());serviceItemDao.create(si);//28
            si = new ServiceItem("5", "Informasi Tambahan", EnumInputService.TextLong.getVal());serviceItemDao.create(si);

            si = new ServiceItem("6", "Lokasi", EnumInputService.Map.getVal());serviceItemDao.create(si);
            si = new ServiceItem("6", "Jenis Ban Motor", EnumInputService.SpinnerInput.getVal());serviceItemDao.create(si);//31
            si = new ServiceItem("6", "Tipe Ban Motor", EnumInputService.SpinnerInput.getVal());serviceItemDao.create(si);//32
            si = new ServiceItem("6", "Tipe Pelayanan", EnumInputService.SpinnerInput.getVal());serviceItemDao.create(si);//33
            si = new ServiceItem("6", "Informasi Tambahan", EnumInputService.TextLong.getVal());serviceItemDao.create(si);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addDataDropdown(){
        try{
            DropDownList dp = new DropDownList(2, "Pembersihan");dropdownListDao.create(dp);
            dp = new DropDownList(2, "Perbaikan");dropdownListDao.create(dp);
            dp = new DropDownList(2, "Perawatan");dropdownListDao.create(dp);
            dp = new DropDownList(2, "Lainnya");dropdownListDao.create(dp);

            dp = new DropDownList(3, "Central AC");dropdownListDao.create(dp);
            dp = new DropDownList(3, "Split AC");dropdownListDao.create(dp);
            dp = new DropDownList(3, "Celling AC");dropdownListDao.create(dp);
            dp = new DropDownList(3, "Lainnya");dropdownListDao.create(dp);

            dp = new DropDownList(4, "Sharp");dropdownListDao.create(dp);
            dp = new DropDownList(4, "Daikin");dropdownListDao.create(dp);
            dp = new DropDownList(4, "Panasonic");dropdownListDao.create(dp);
            dp = new DropDownList(4, "Lainnya");dropdownListDao.create(dp);

            dp = new DropDownList(5, "Tidak dingin atau panas");dropdownListDao.create(dp);
            dp = new DropDownList(5, "Pendingin bocor");dropdownListDao.create(dp);
            dp = new DropDownList(5, "Mesin mati");dropdownListDao.create(dp);
            dp = new DropDownList(5, "Lainnya");dropdownListDao.create(dp);

            dp = new DropDownList(9, "Laptop");dropdownListDao.create(dp);
            dp = new DropDownList(9, "Desktop");dropdownListDao.create(dp);

            dp = new DropDownList(10, "PC");dropdownListDao.create(dp);
            dp = new DropDownList(10, "MAC");dropdownListDao.create(dp);

            dp = new DropDownList(13, "Asus");dropdownListDao.create(dp);
            dp = new DropDownList(13, "Lenovo");dropdownListDao.create(dp);
            dp = new DropDownList(13, "Fujitsu");dropdownListDao.create(dp);
            dp = new DropDownList(13, "Acer");dropdownListDao.create(dp);
            dp = new DropDownList(13, "Toshiba");dropdownListDao.create(dp);
            dp = new DropDownList(13, "Dell");dropdownListDao.create(dp);
            dp = new DropDownList(13, "Lainnya");dropdownListDao.create(dp);

            dp = new DropDownList(14, "Perusahaan");dropdownListDao.create(dp);
            dp = new DropDownList(14, "Pribadi");dropdownListDao.create(dp);

            dp = new DropDownList(15, "Linux");dropdownListDao.create(dp);
            dp = new DropDownList(15, "Windows 7");dropdownListDao.create(dp);
            dp = new DropDownList(15, "Windows 8");dropdownListDao.create(dp);
            dp = new DropDownList(15, "IOS");dropdownListDao.create(dp);
            dp = new DropDownList(15, "Lainnya");dropdownListDao.create(dp);

            dp = new DropDownList(18, "Toyota");dropdownListDao.create(dp);
            dp = new DropDownList(18, "Honda");dropdownListDao.create(dp);
            dp = new DropDownList(18, "Mitsubishi");dropdownListDao.create(dp);
            dp = new DropDownList(18, "Nissan");dropdownListDao.create(dp);
            dp = new DropDownList(18, "BMW");dropdownListDao.create(dp);
            dp = new DropDownList(18, "Lainnya");dropdownListDao.create(dp);

            dp = new DropDownList(19, "Pembersihan");dropdownListDao.create(dp);
            dp = new DropDownList(19, "Perbaikan");dropdownListDao.create(dp);
            dp = new DropDownList(19, "Perawatan");dropdownListDao.create(dp);
            dp = new DropDownList(19, "Lainnya");dropdownListDao.create(dp);

            dp = new DropDownList(22, "Yamaha");dropdownListDao.create(dp);
            dp = new DropDownList(22, "Honda");dropdownListDao.create(dp);
            dp = new DropDownList(22, "Zuzuki");dropdownListDao.create(dp);
            dp = new DropDownList(22, "Kawasaki");dropdownListDao.create(dp);

            dp = new DropDownList(23, "Perbaikan");dropdownListDao.create(dp);
            dp = new DropDownList(23, "Tune Up");dropdownListDao.create(dp);
            dp = new DropDownList(23, "Ganti Oli");dropdownListDao.create(dp);
            dp = new DropDownList(23, "Lainnya");dropdownListDao.create(dp);

            dp = new DropDownList(26, "Bias");dropdownListDao.create(dp);
            dp = new DropDownList(26, "Radial");dropdownListDao.create(dp);

            dp = new DropDownList(27, "Dunlop");dropdownListDao.create(dp);
            dp = new DropDownList(27, "GT Radial");dropdownListDao.create(dp);
            dp = new DropDownList(27, "Brighstone");dropdownListDao.create(dp);
            dp = new DropDownList(27, "Lainnya");dropdownListDao.create(dp);

            dp = new DropDownList(28, "Perbaikan Ban Bocor");dropdownListDao.create(dp);
            dp = new DropDownList(28, "Ganti Ban");dropdownListDao.create(dp);
            dp = new DropDownList(28, "Perawatan");dropdownListDao.create(dp);
            dp = new DropDownList(28, "Lainnya");dropdownListDao.create(dp);

            dp = new DropDownList(31, "Tubeless");dropdownListDao.create(dp);
            dp = new DropDownList(31, "Tubetype");dropdownListDao.create(dp);

            dp = new DropDownList(32, "IRC");dropdownListDao.create(dp);
            dp = new DropDownList(32, "FDR");dropdownListDao.create(dp);
            dp = new DropDownList(32, "Mizzle");dropdownListDao.create(dp);
            dp = new DropDownList(32, "Brighstone");dropdownListDao.create(dp);
            dp = new DropDownList(32, "Lainnya");dropdownListDao.create(dp);

            dp = new DropDownList(33, "Perbaikan Ban Bocor");dropdownListDao.create(dp);
            dp = new DropDownList(33, "Ganti Ban");dropdownListDao.create(dp);
            dp = new DropDownList(33, "Perawatan");dropdownListDao.create(dp);
            dp = new DropDownList(33, "Lainnya");dropdownListDao.create(dp);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
