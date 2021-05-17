package com.chat.uchat.Module_DataModel;



public class MDL_User {
    public String name;
    public String email;
    public String avata;
    public MDL_Status MDLStatus;
    public MDL_Message MDLMessage;


    public MDL_User(){
        MDLStatus = new MDL_Status();
        MDLMessage = new MDL_Message();
        MDLStatus.isOnline = false;
        MDLStatus.timestamp = 0;
        MDLMessage.idReceiver = "0";
        MDLMessage.idSender = "0";
        MDLMessage.text = "";
        MDLMessage.timestamp = 0;
    }
}
