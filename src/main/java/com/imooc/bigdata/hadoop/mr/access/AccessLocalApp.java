package com.imooc.bigdata.hadoop.mr.access;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Created by bbHead on 2020/1/15 00:46
 */
public class AccessLocalApp {
    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();

        Job job = Job.getInstance(configuration);

        job.setJarByClass(AccessLocalApp.class);

        job.setMapperClass(AccessMapper.class);
        job.setReducerClass(AccessReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Access.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Access.class);

        FileInputFormat.setInputPaths(job, new Path("access/input"));
        FileOutputFormat.setOutputPath(job, new Path("access/output"));

        job.waitForCompletion(true);
    }
}
