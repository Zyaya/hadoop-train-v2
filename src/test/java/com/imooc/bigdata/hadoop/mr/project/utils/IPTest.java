package com.imooc.bigdata.hadoop.mr.project.utils;

import org.junit.Test;

public class IPTest {
    @Test
    public void TestIP(){
        IPParser.RegionInfo regionInfo = IPParser.getInstance().analyseIp("123.116.68.97");
        System.out.println(regionInfo.getCountry());
        System.out.println(regionInfo.getProvince());
        System.out.println(regionInfo.getCity());
    }
}
