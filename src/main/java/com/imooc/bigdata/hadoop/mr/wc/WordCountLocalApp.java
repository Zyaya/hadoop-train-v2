package com.imooc.bigdata.hadoop.mr.wc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Progressable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

/**
 * 使用MR统计HDFS上的文件对应的词频
 *
 * Driver:配置Mapper，Reducer的相关属性
 *
 * 提交到本地运行：开发过程中使用
 * 先本地运行成功，再提交到服务器上做性能调优
 */

public class WordCountLocalApp {
    public static void main(String[] args) throws Exception{

        //System.setProperty("HADOOP_USER_NAME", "hadoop");

        Configuration configuration = new Configuration();
        //configuration.set("dfs.client.use.datanode.hostname", "true");
        //configuration.set("fs.defaultFS","hdfs://39.108.114.91:8020");

        //创建一个job
        Job job = Job.getInstance(configuration);
        //设置Job对应的主类的参数
        job.setJarByClass(WordCountLocalApp.class);
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        //设置Job对应的参数：Mapper输出key和value的类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        //设置Job对应的参数：Reducer输出key和value的类型
        job.setOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);



        //设置Job对应的参数：作业输入和输出的类型
        FileInputFormat.setInputPaths(job, new Path("input"));

        FileOutputFormat.setOutputPath(job, new Path("output"));

        //提交job
        boolean result = job.waitForCompletion(true);

        System.exit(result ? 0 : -1);


    }
}
