package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.net.wifi.aware.WifiAwareManager;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
Button wifi,discover,wifion;
VideoView video;
ListView listView;
EditText editText;
Button button;
WifiManager wifiManager;

WifiP2pManager mManager;
WifiP2pManager.Channel mchannel;
BroadcastReceiver mReciever;
IntentFilter  intentFilter = new IntentFilter();

List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>() ;
String[] deviceN;
WifiP2pDevice[] devices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

 //       video = (VideoView) findViewById(R.id.vid);
       // setRequestedOrientation(MainActivity.SCREEN_ORIENTATION_LANDSCAPE);
        String VideoPath = "android.resource://" + getPackageName() + "/" + R.raw.test_vid;
        Uri uri = Uri.parse(VideoPath);
 //       video.setVideoURI(uri);
//        video.start();
        MediaController mediaController = new MediaController(this);
       // video.setMediaController(mediaController);
       // mediaController.setAnchorView(video);
        listView = findViewById(R.id.peerList);
        listView.setVisibility(View.GONE);
        button = findViewById(R.id.enter);
        editText = findViewById(R.id.chat);
        //wifiManager.setWifiEnabled(false);
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);









//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE)
//                != PackageManager.PERMISSION_GRANTED) {
//            // Permission is not granted
//       //     Toast.makeText(getApplicationContext(), " permission NOT accepted", Toast.LENGTH_SHORT).show();
//        }else{
//        //   Toast.makeText(getApplicationContext(), "Permission accepted wifi change"+ mReciever, Toast.LENGTH_SHORT).show();
//        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            createPermissions();
    }

    }

 WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
    @Override
    public void onPeersAvailable(WifiP2pDeviceList peerList) {
        if(!peerList.getDeviceList().equals(peers)){
            peers.clear();

         //    peers.addAll(peerList.getDeviceList());
            peers.addAll(peerList.getDeviceList());
            deviceN = new String[peerList.getDeviceList().size()];
            devices = new WifiP2pDevice[peerList.getDeviceList().size()];
            int index =0;

            for(WifiP2pDevice device : peerList.getDeviceList()){
                deviceN[index] = device.deviceName;
                devices[index] = device;
                index++;
            }
            ArrayAdapter<String> adapt = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,deviceN);
            listView.setAdapter(adapt);
        }
        if(peers.size() ==0){

            Toast.makeText(getApplicationContext(), " no devices ", Toast.LENGTH_SHORT).show();

        }
    }
};
    public void createPermissions(){
        String permission = Manifest.permission.CHANGE_WIFI_STATE;
        if (ContextCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED){
            if(!ActivityCompat.shouldShowRequestPermissionRationale(new MainActivity(), permission)){
                Toast.makeText(getApplicationContext(), "Permission not  needed for WIFI P2P ", Toast.LENGTH_SHORT).show();
            }

        }
        Toast.makeText(getApplicationContext(), " wifi p2p accepted without permission" , Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mchannel = mManager.initialize(this,getMainLooper(),null );
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        //intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        //intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        //intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mReciever = new WifiDirectBroadcastReciever(mManager,mchannel,this);

        registerReceiver(mReciever,intentFilter);
      //  Toast.makeText(getApplicationContext(), "reciever registered"+ mReciever, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReciever);
        //Toast.makeText(getApplicationContext(), "ON PAUSE WORKED ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.wifi:
                if(wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(false);

                    //int state = getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
               //     registerReceiver(mReciever,intentFilter);
                   // Toast.makeText(getApplicationContext(), state, Toast.LENGTH_SHORT);
                    item.setIcon(ContextCompat.getDrawable(this,R.drawable.ic_signal_wifi_off_black_24dp));
                }else{
                    wifiManager.setWifiEnabled(true);
             //       registerReceiver(mReciever,intentFilter);

                    item.setIcon(ContextCompat.getDrawable(this,R.drawable.ic_wifi_black_24dp));
                }

                return true;
            case R.id.discover:

                mManager.discoverPeers(mchannel, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(), "discovering networks ", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int reason) {
                        Toast.makeText(getApplicationContext(), "failed to discover ", Toast.LENGTH_SHORT).show();
                    }
                });
               // Toast toasts = Toast.makeText(getApplicationContext(), "discover clicked ", Toast.LENGTH_SHORT);
              //  toasts.show();
                listView.setVisibility(View.VISIBLE);
                item.setIcon(ContextCompat.getDrawable(this,R.drawable.ic_phonelink_black_24dp));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }



    }






}
