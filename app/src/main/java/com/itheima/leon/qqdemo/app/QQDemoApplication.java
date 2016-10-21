package com.itheima.leon.qqdemo.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.itheima.leon.qqdemo.BuildConfig;
import com.itheima.leon.qqdemo.R;
import com.itheima.leon.qqdemo.adpater.EMMessageListenerAdapter;

import java.util.Iterator;
import java.util.List;

import cn.bmob.v3.Bmob;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/16 9:51
 * 描述：    TODO
 */
public class QQDemoApplication extends Application {
    public static final String TAG = "QQDemoApplication";
    private int mDuanSound;
    private int mYuluSound;
    private SoundPool mSoundPool;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        initHuanXin();
        initBmob();
        initSoundPool();
        EMClient.getInstance().chatManager().addMessageListener(mEMMessageListenerAdapter);
    }

    private void initSoundPool() {
        mSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        mDuanSound = mSoundPool.load(this, R.raw.duan, 1);
        mYuluSound = mSoundPool.load(this, R.raw.yulu, 1);
    }

    private void initBmob() {
        Bmob.initialize(this, "df5a02bd4c7875549784ceb033407cc4");
    }

    private void initHuanXin() {
        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(true);
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
// 如果APP启用了远程的service，此application:onCreate会被调用2次
// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
// 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null ||!processAppName.equalsIgnoreCase(getPackageName())) {
            Log.e(TAG, "enter the service process!");
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }

//初始化
        EMClient.getInstance().init(getApplicationContext(), options);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        if (BuildConfig.DEBUG) {
            EMClient.getInstance().setDebugMode(true);
        }
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    private EMMessageListenerAdapter mEMMessageListenerAdapter = new EMMessageListenerAdapter() {

        @Override
        public void onMessageReceived(List<EMMessage> list) {
            if (isForeground()) {
                mSoundPool.play(mDuanSound, 1, 1, 0, 0, 1);
            } else {
                mSoundPool.play(mYuluSound, 1, 1, 0, 0, 1);
            }
        }
    };

    public boolean isForeground() {
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo info :runningAppProcesses) {
            if (info.processName.equals(getPackageName()) && info.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }
}
