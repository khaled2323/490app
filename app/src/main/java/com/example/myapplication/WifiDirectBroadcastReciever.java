package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.widget.Toast;



public class WifiDirectBroadcastReciever extends BroadcastReceiver {

    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private MainActivity mActivity;

    public WifiDirectBroadcastReciever(WifiP2pManager mManager, WifiP2pManager.Channel mChannel,MainActivity mActivity){
        this.mManager = mManager;
        this.mChannel = mChannel;
        this.mActivity = mActivity;
        //         Toast.makeText(context, "wifi ON ", Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
//        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
//            // Determine if Wifi P2P mode is enabled or not, alert
//            // the Activity.
//            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
//            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
//                activity.setIsWifiP2pEnabled(true);
//            } else {
//                activity.setIsWifiP2pEnabled(false);
//            }
//        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
//
//            // The peer list has changed! We should probably do something about
//            // that.
//
//        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
//
//            // Connection state changed! We should probably do something about
//            // that.
//
//        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
//            DeviceListFragment fragment = (DeviceListFragment) activity.getFragmentManager()
//                    .findFragmentById(R.id.frag_list);
//            fragment.updateThisDevice((WifiP2pDevice) intent.getParcelableExtra(
//                    WifiP2pManager.EXTRA_WIFI_P2P_DEVICE));
//
//        }

//        String action = intent.getAction();
        Toast.makeText(context, "AFTER ACTION ", Toast.LENGTH_SHORT).show();


        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals((action))) {
            //check if wifi connection changed
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);

            Toast.makeText(context, "inside p2p state ", Toast.LENGTH_SHORT).show();


            if(state == WifiP2pManager.WIFI_P2P_STATE_ENABLED){
                Toast.makeText(context, "wifi ON ", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "wifi OFF ", Toast.LENGTH_SHORT).show();
            }
        }else if(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)){
            // check peer list changed
            if(mManager != null){
                mManager.requestPeers(mChannel,mActivity.peerListListener);

            }

        }else if(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            //check device config


        } else if(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)){
            // check if wifi p2p enabled

            }

        }
}
