# LocationProvider

[![platform](https://img.shields.io/badge/platform-android-brightgreen.svg)](https://developer.android.com/index.html)
[![](https://jitpack.io/v/dreamfish797/LocationProvider.svg)](https://jitpack.io/#dreamfish797/LocationProvider)
[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/LocationProvider/Lobby)
[![license](https://img.shields.io/badge/license-Apach2.0-green.svg)](https://github.com/dreamfish797/LocationProvider/blob/master/LICENSE.txt)
[![lib](https://img.shields.io/badge/lib-1.0.1-blue.svg)](https://github.com/dreamfish797/LocationProvider/releases/tag/1.0.1)

---
## 位置追踪库简介

位置追踪库，基于百度地图封装，让获取定位信息更简单、更优雅~！

这个库可以让你在开发工程中免去引入百度地图API的各种配置，你只需要配置一下你自己的 api_key 就可以使用了。

本库提供位置追踪功能，当你开启该功能后，应用会在你指定的频率下工作，记录你的设备移动轨迹并保存起来，当然你可以随时去查任意时间你的设备出现的位置。现阶段只提供两种位置查询方法：

1. 根据具体时间查询（例如获取 2017-09-01 11:11:11 时设备的位置）
2. 获取当前时间设备位置（定位）
3. 根据一个时间范围去查询你的运动轨迹点

> 后面我会继续维护，提供更多的功能接口，以满足更多需求

## 如何使用

Step 1. Add the JitPack repository to your build file( 项目最外面一层的 build.gradel )

```
	allprojects {
			repositories {
				...
				maven { url 'https://jitpack.io' }
			}
		}
```

Step 2. Add the dependency 

```
	dependencies {
	        compile 'com.github.dreamfish797:LocationProvider:1.0.1'
	}

```
Step 3. 在项目的 AndroidManifest.xml 中添加 Api_Key :
```
<meta-data
            android:name="com.baidu.lbsapi.API_KEY"
            android:value="请输入你的 Api_Key " />  <!--http://lbsyun.baidu.com/apiconsole/key-->
```
> **没有 Api_Key 的同学来来来，到这里报到** [注册百度地图 Api_Key ](http://lbsyun.baidu.com/index.php?title=androidsdk/guide/key)

### 功能使用

* 开启位置追踪
```
HooweLocationProvider.getInstance().startTracker(option, new OnLocationUpdatedListener() {

            @Override
            public void onReceiveLocation(HooweLocation location) {
                displayLocation(location, tvContent);
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
```

* 结束位置追踪
```
HooweLocationProvider.getInstance().endTracker();
```

* 获取当前时间设备位置

```
HooweLocationProvider.getInstance().getCurrentLocation(new OnLocationUpdatedListener() {
            @Override
            public void onReceiveLocation(HooweLocation location) {
                
            }

            @Override
            public void onReceiveLocation(List<HooweLocation> LocationList) {

            }

            @Override
            public void onLocationTrackerExist() {

            }

            @Override
            public void onLocationTrackerNotRun() {

            }
        });
```

* 获取具体时间设备位置

```
HooweLocationProvider.getInstance().getLocationByTime(“2017-09-01 11:11:11”, new OnLocationUpdatedListener() {
                        @Override
                        public void onReceiveLocation(HooweLocation location) {
                            displayLocation(location,tvContentTime);
                        }

                        @Override
                        public void onReceiveLocation(List<HooweLocation> LocationList) {

                        }

                        @Override
                        public void onLocationTrackerExist() {

                        }

                        @Override
                        public void onLocationTrackerNotRun() {

                        }
                    });
```

* 获取指定时间段内设备的运动轨迹点集合

```
HooweLocationProvider.getInstance().getLocationByPeriod("2017-09-01 11:11:11", "2017-09-01 12:12:12", new OnLocationUpdatedListener() {
            @Override
            public void onReceiveLocation(HooweLocation location) {
                
            }

            @Override
            public void onReceiveLocation(List<HooweLocation> LocationList) {

            }

            @Override
            public void onLocationTrackerExist() {

            }

            @Override
            public void onLocationTrackerNotRun() {

            }
        });
```

> 具体使用大家可以在项目中查看 demo ，有什么疑问或者测出 BUG 可以提 issues 或者邮件我[cmy797@126.com]()






