package com.chat.uchat.Module_Service;

import android.content.Context;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;

import com.chat.uchat.Module_DataModel.MDL_Friend;
import com.chat.uchat.Module_DataStore.SharedPreferenceHelper;
import com.chat.uchat.Module_DataStore.StaticConfig;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.chat.uchat.Module_DataModel.MDL_ListFriend;

import java.util.HashMap;



public class ServiceUtils {

    private static ServiceConnection connectionServiceFriendChatForStart = null;
    private static ServiceConnection connectionServiceFriendChatForDestroy = null;







    public static void updateUserStatus(Context context){
        if(isNetworkConnected(context)) {
            String uid = SharedPreferenceHelper.getInstance(context).getUID();
            if (!uid.equals("")) {
                FirebaseDatabase.getInstance().getReference().child("user/" + uid + "/MDLStatus/isOnline").setValue(true);
                FirebaseDatabase.getInstance().getReference().child("user/" + uid + "/MDLStatus/timestamp").setValue(System.currentTimeMillis());
            }
        }
    }

    public static void updateFriendStatus(Context context, MDL_ListFriend MDLListFriend){
        if(isNetworkConnected(context)) {
            for (MDL_Friend MDLFriend : MDLListFriend.getListMDLFriend()) {
                final String fid = MDLFriend.id;
                FirebaseDatabase.getInstance().getReference().child("user/" + fid + "/MDLStatus").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            HashMap mapStatus = (HashMap) dataSnapshot.getValue();
                            if ((boolean) mapStatus.get("isOnline") && (System.currentTimeMillis() - (long) mapStatus.get("timestamp")) > StaticConfig.TIME_TO_OFFLINE) {
                                FirebaseDatabase.getInstance().getReference().child("user/" + fid + "/MDLStatus/isOnline").setValue(false);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
    }

    public static boolean isNetworkConnected(Context context) {
        try{
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo() != null;
        }catch (Exception e){
            return true;
        }
    }
}
