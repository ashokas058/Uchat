package com.chat.uchat.Module_DataModel;



public class MDL_Group extends MDL_Room {
    public String id;
    public MDL_ListFriend MDLListFriend;

    public MDL_Group(){
        MDLListFriend = new MDL_ListFriend();
    }
}
