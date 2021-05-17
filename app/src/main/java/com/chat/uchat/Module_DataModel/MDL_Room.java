package com.chat.uchat.Module_DataModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class MDL_Room {
    public ArrayList<String> member;
    public Map<String, String> groupInfo;

    public MDL_Room(){
        member = new ArrayList<>();
        groupInfo = new HashMap<String, String>();
    }
}
