package com.hoowe.locationprovider.util;

import android.widget.TextView;

import com.baidu.location.BDLocation;

import java.util.List;

import hoowe.locationmanagerlibrary.hoowe.HooweLocation;

/**
 * Created by chen.mingyao on 2017/9/7.
 */

public class LocationUtils {

    public static void displayLocation(HooweLocation location, TextView textView) {
        logMsg(analysisLocation(location).toString(), textView);
    }

    private static StringBuffer analysisLocation(HooweLocation location) {
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
        sb.append("\nDirection(not all devices have value): ");
        sb.append(location.getDirection());// 方向
        sb.append("\nlocationdescribe: ");
        sb.append(location.getLocationDescribe());// 位置语义化信息

        if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
            sb.append("\nspeed : ");
            sb.append(location.getSpeed());// 速度 单位：km/h
            sb.append("\nsatellite : ");
            sb.append(location.getSatelliteNumber());// 卫星数目
            sb.append("\nheight : ");
            sb.append(location.getAltitude());// 海拔高度 单位：米
            sb.append("\ndescribe : ");
            sb.append("gps定位成功");
        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
            // 运营商信息
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
        return sb;
    }

    /**
     * 显示请求字符串
     *
     * @param str
     */
    private static void logMsg(String str, final TextView textView) {
        final String s = str;
        try {
            if (textView != null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        textView.post(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText(s);
                            }
                        });

                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void displayLocation(List<HooweLocation> locationList, TextView textView) {
        String str = "";
        for (HooweLocation location : locationList) {
            str += analysisLocation(location).toString() + "\n \n";
        }
        logMsg(str, textView);
    }
}
