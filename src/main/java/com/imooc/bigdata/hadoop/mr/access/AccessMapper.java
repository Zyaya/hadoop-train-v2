package com.imooc.bigdata.hadoop.mr.access;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 自定义mapper处理类
 */
public class AccessMapper extends Mapper<LongWritable, Text, Text, Access>{
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String[] lines = value.toString().split("\t");
        String phone = lines[1]; //取出手机号
        long up = Long.parseLong(lines[lines.length-2]);
        long down = Long.parseLong(lines[lines.length-3]);

        context.write(new Text(phone), new Access(phone, up, down));

    }
}
