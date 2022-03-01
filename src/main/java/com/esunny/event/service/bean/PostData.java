package com.esunny.event.service.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostData {
    private String name;
    @SerializedName("ilist")
    private List<Integer> iList;
    @SerializedName("slist")
    private List<String> sList;
    private PostDict dict;

    class PostDict {
        private String key;
        @SerializedName("ilist")
        private List<Integer> iList;
    }
}
