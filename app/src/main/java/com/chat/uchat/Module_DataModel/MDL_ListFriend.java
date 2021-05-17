package com.chat.uchat.Module_DataModel;

import java.util.ArrayList;


public class MDL_ListFriend {
    private ArrayList<MDL_Friend> listMDLFriend;

    public ArrayList<MDL_Friend> getListMDLFriend() {
        return listMDLFriend;
    }

    public MDL_ListFriend(){
        listMDLFriend = new ArrayList<>();
    }

    public String getAvataById(String id){
        for(MDL_Friend MDLFriend : listMDLFriend){
            if(id.equals(MDLFriend.id)){
                return MDLFriend.avata;
            }
        }
        return "";
    }

    public void setListMDLFriend(ArrayList<MDL_Friend> listMDLFriend) {
        this.listMDLFriend = listMDLFriend;
    }
}
