package com.imooc.bigdata.hadoop.mr.access;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by bbHead on 2020/1/13 00:09
 */
public class AccessMapper extends Mapper<LongWritable, Text, Text, Access>{
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        super.map(key, value, context);
    }
}
