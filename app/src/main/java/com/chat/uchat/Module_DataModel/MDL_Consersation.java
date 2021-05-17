package com.chat.uchat.Module_DataModel;

import java.util.ArrayList;



public class MDL_Consersation {
    private ArrayList<MDL_Message> listMDLMessageData;
    public MDL_Consersation(){
        listMDLMessageData = new ArrayList<>();
    }

    public ArrayList<MDL_Message> getListMDLMessageData() {
        return listMDLMessageData;
    }
}
