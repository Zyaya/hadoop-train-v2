package com.imooc.bigdata.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Map;
import java.util.Set;

/**
 * 使用HDFS API 完成wordcount统计
 *
 * 需求：统计HDFS上的文件的wc，将统计结构输出到HDFS中
 *
 * 功能拆解：
 * 1）读取HDFS上的文件 ==> HDFS API
 * 2）业务处理（词频统计）：对文件中的每一行数据都要进行业务处理（按照分隔符分割）==> Mapper
 * 3）将处理结果缓存 ==> Context
 * 4）将结果输出到HDFS ==> HDFS API
 */
public class HDFSWCApp02 {
    public static void main(String[] args) throws Exception{

        // 1)读取HDFS上的文件 ==> HDFS API
        Path input = new Path(("/hdfsapi/test/hello.txt"));
        // 获取到要操作的文件系统
        Configuration configuration = new Configuration();
        configuration.set("dfs.client.use.datanode.hostname","true");
        FileSystem fs = FileSystem.get(new URI("hdfs://39.108.114.91:8020"), configuration,"hadoop");
        RemoteIterator<LocatedFileStatus> files = fs.listFiles(input, false);

        //ImoocMapper mapper = new WordCountMapper();
        ImoocMapper mapper = new WordCountMapper();
        ImoocContext context = new ImoocContext();
        while (files.hasNext()){
            LocatedFileStatus file = files.next();
            FSDataInputStream in = fs.open(file.getPath());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line = "";
            while ((line = reader.readLine()) != null){

                // 2)业务处理，词频统计 （hello，3）
                //TODO...   在业务逻辑处理完之后将结果写到Cache中去
                mapper.map(line, context);
            }

            reader.close();
            in.close();

        }

        // TODO... 3)将处理结果缓存 ==> Context 思考 Map能否将其当作缓存，因为是个键值对，有的话就输出get，没有就丢进去

        Map<Object, Object> contextMap = context.getCacheMap();
        //Map<Object, Object> contextMap = new HashMap<Object, Object>();


        // 4）将结果输出到HDFS ==> HDFS API
        Path output = new Path("/hdfsapi/output/");
        //创建文件
        FSDataOutputStream out = fs.create(new Path(output,"wc2.out"));

        // TODO 将第三步缓存中的内容输出到out中去
        Set<Map.Entry<Object, Object>> entries = contextMap.entrySet();
        for (Map.Entry<Object, Object> entry : entries){
            out.writeUTF(entry.getKey().toString() + "\t" + entry.getValue() + "\n");
        }

        out.close();
        fs.close();

        System.out.println("yalin的统计词频实现成功");
    }
}
