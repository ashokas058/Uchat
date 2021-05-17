package com.chat.uchat.Module_DataModel;



public class MDL_Configuration {
    private String label;
    private String  value;
    private int icon;

    public MDL_Configuration(String label, String value, int icon){
        this.label = label;
        this.value = value;
        this.icon = icon;
    }

    public String getLabel(){
        return this.label;
    }

    public String getValue(){
        return this.value;
    }

    public int getIcon(){
        return this.icon;
    }
}
