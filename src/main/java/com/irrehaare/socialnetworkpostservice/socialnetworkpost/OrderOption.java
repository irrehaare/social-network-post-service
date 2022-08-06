package com.irrehaare.socialnetworkpostservice.socialnetworkpost;

public enum OrderOption {
    ID("id"),
    DATE("post_date");

    public final String columnName;

    OrderOption(String columnName){
        this.columnName = columnName;
    }
}
