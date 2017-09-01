package com.hoowe.locationprovider;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;

import java.util.List;

import hoowe.locationmanagerlibrary.hoowe.HooweLocation;
import hoowe.locationmanagerlibrary.hoowe.HooweLocationProvider;
import hoowe.locationmanagerlibrary.hoowe.HooweLocationTracker;
import hoowe.locationmanagerlibrary.hoowe.OnLocationUpdatedListener;
import hoowe.locationmanagerlibrary.utils.BaiduUtils;
import hoowe.locationmanagerlibrary.utils.TimeUtils;

public class MainActivity extends AppCompatActivity {

    private TextView tvContent;
    HooweLocationTracker mManager;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvContent = (TextView) findViewById(R.id.tv_content);
//        mManager = new HooweLocationTracker(this);
//        mManager.registerListener(new BDAbstractLocationListener() {
//            @Override
//            public void onReceiveLocation(BDLocation bdLocation) {
//                Log.d("hoowe", "onReceiveLocation");
//                tvContent.setText(bdLocation.getLocationDescribe());
//                mManager.unregisterListener(this);
//            }
//        });
//
//        mManager.setLocationOption(mManager.getDefaultLocationClientOption());
//
//        mManager.start();

//        HooweLocationProvider.getInstance().getCurrentLocation(HooweLocationProvider.getInstance().getDefaultLocationClientOption(), new OnLocationUpdatedListener() {
//            @Override
//            public void onReceiveLocation(BDLocation bdLocation) {
//                Log.d(TAG, "onReceiveLocation 1 Latitude = " + bdLocation.getLatitude());
//            }
//        });
//
//        HooweLocationProvider.getInstance().getCurrentLocation(HooweLocationProvider.getInstance().getDefaultLocationClientOption(), new OnLocationUpdatedListener() {
//            @Override
//            public void onReceiveLocation(BDLocation bdLocation) {
//                Log.d(TAG, "onReceiveLocation 2 Latitude = " + bdLocation.getLatitude());
//            }
//        });

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType(BaiduUtils.CoorType_GCJ02);
        option.setScanSpan(3000);
        option.setIsNeedAddress(true);

        HooweLocationProvider.getInstance().startTracker(option, new OnLocationUpdatedListener() {

            @Override
            public void onReceiveLocation(HooweLocation location) {
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append(location.getLocTime());
                sb.append("\nlocType : ");// 定位类型
                sb.append(location.getLocType());
                sb.append("\nlocType description : ");// *****对应的定位类型说明*****
                sb.append(location.getLocationDescribe());
                sb.append("\nlatitude : ");// 纬度
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");// 经度
                sb.append(location.getLongitude());
                sb.append("\nradius : ");// 半径
                sb.append(location.getRadius());
                sb.append("\nCountryCode : ");// 国家码
                sb.append(location.getCountryCode());
                sb.append("\nCountry : ");// 国家名称
                sb.append(location.getCountry());
                sb.append("\ncitycode : ");// 城市编码
                sb.append(location.getCityCode());
                sb.append("\ncity : ");// 城市
                sb.append(location.getCity());
                sb.append("\nDistrict : ");// 区
                sb.append(location.getDistrict());
                sb.append("\nStreet : ");// 街道
                sb.append(location.getStreet());
                sb.append("\naddr : ");// 地址信息
                sb.append(location.getAddrStr());
//                sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
//                sb.append(location.getUserIndoorState());
                sb.append("\nDirection(not all devices have value): ");
                sb.append(location.getDirection());// 方向
                sb.append("\nlocationdescribe: ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
//                sb.append("\nPoi: ");// POI信息
//                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
//                    for (int i = 0; i < location.getPoiList().size(); i++) {
//                        Poi poi = (Poi) location.getPoiList().get(i);
//                        sb.append(poi.getName() + ";");
//                    }
//                }
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 速度 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());// 卫星数目
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 海拔高度 单位：米
//                    sb.append("\ngps status : ");
//                    sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
//                    if (location.hasAltitude()) {// *****如果有海拔高度*****
//                        sb.append("\nheight : ");
//                        sb.append(location.getAltitude());// 单位：米
//                    }
                    sb.append("\noperationers : ");// 运营商信息
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                tvContent.setText(sb.toString());
            }

            @Override
            public void onReceiveLocation(List<HooweLocation> list) {

            }

            @Override
            public void onLocationTrackerExist() {
                Log.d(TAG, "onLocationTrackerExist 1");
            }

            @Override
            public void onLocationTrackerNotRun() {

            }
        });

        HooweLocationProvider.getInstance().startTracker(new OnLocationUpdatedListener() {
            @Override
            public void onReceiveLocation(HooweLocation location) {

            }

            @Override
            public void onReceiveLocation(List<HooweLocation> list) {

            }

            @Override
            public void onLocationTrackerExist() {
                Log.d(TAG, "onLocationTrackerExist 2");
            }

            @Override
            public void onLocationTrackerNotRun() {

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HooweLocationProvider.getInstance().endTracker();
    }
}
