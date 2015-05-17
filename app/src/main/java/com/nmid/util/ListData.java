package com.nmid.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Toryang on 2015/4/28.
 */
public class ListData {
    private   List<String> list ;
    private  Map<String,String> map;
    public void setDataList(List<String> list,Map<String,String> map){
        this.list = list;
        this.map = map;
    }
    public List<String> getList(){
        return list;
    }
    public Map<String,String> getMap(){
        return map;
    }
}
