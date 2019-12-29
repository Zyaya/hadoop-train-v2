package com.imooc.bigdata.hadoop.hdfs;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;

/*使用Java API操作HDFS文件系统*/
public class HDFSApp {

    public static final String HDFS_PATH = "hdfs://39.108.114.91:8020";
    //public static final String HDFS_PATH = "hdfs://hadoop000:8020";
    //public static final String HDFS_PATH = "hdfs://172.18.16.120:8020";
    FileSystem fileSystem = null;
    Configuration configuration = null;


    @Before
    public void setUp() throws Exception{

        System.out.println("-----setUp--------------");


        configuration = new Configuration();
        configuration.set("dfs.client.use.datanode.hostname","true");
        configuration.set("dfs.replication","1");

        /**
         * 构造一个访问指定HDFS系统的客户端对象
         * 第一个参数：HDFS的URI
         * 第二个参数：客户端指定的配置参数
         * 第三个参数：客户端的身份，说白了就是用户名
         */

        fileSystem = FileSystem.get(new URI(HDFS_PATH),configuration,"hadoop");
    }

    /**
     * 创建HDFS文件夹
     *
     */

    @Test //单元测试一定要加一个Test，否则不认
    public void mkdir() throws Exception{
        fileSystem.mkdirs(new Path("/hdfsapi"));
    }

    /**
     * 打开文件
     * @throws Exception
     */
    @Test
    public void text() throws Exception{
        FSDataInputStream in = fileSystem.open(new Path("/cdh_version.properties"));
        IOUtils.copyBytes(in, System.out, 1024);
    }

    /**
     *  创建文件
     */
    @Test
    public void create() throws Exception{
        FSDataOutputStream out = fileSystem.create(new Path("/hdfsapi/test/b.txt"));
        out.writeUTF("hello pk:replication 1");
        out.flush();
        out.close();

    }

    @Test
    public void testReplication(){
        System.out.println(configuration.get("dfs.replication"));
    }

    /**
     *
     * 重命名文件
     */
    @Test
    public void rename() throws Exception{
        Path oldPath = new Path("/hdfsapi/test/b.txt");
        Path newPath = new Path("/hdfsapi/test/c.txt");
        boolean result = fileSystem.rename(oldPath, newPath);
        System.out.println(result);
    }

    /**
     *
     * @拷贝小文件
     */
    @Test
    public void copyFromLocal() throws Exception{
        Path src = new Path("/Users/zhangyalin/Desktop/屏幕快照 2019-12-08 下午10.53.06.png");
        Path dst = new Path("/hdfsapi/test");
        fileSystem.copyFromLocalFile(src, dst);
    }

    /**
     *
     * @拷贝大文件到文件系统：带进度
     */
    @Test
    public void copyFromBigLocal() throws Exception{

        InputStream in = new BufferedInputStream(new FileInputStream((new File("/Users/zhangyalin/Downloads/MacDict.dmg"))));

        FSDataOutputStream out = fileSystem.create(new Path("/hdfsapi/test/MacDict.dmg"), new Progressable() {
            @Override
            public void progress() {
                System.out.print("*");
            }
        });

        IOUtils.copyBytes(in, out, 4096);
    }

    /**
     *
     * @从hdfs上拷贝小文件到本地
     */
    @Test
    public void copyToLocalFile() throws Exception{
        Path src = new Path("/hdfsapi/test/a.txt");
        Path dst = new Path("/Users/zhangyalin/Desktop");
        fileSystem.copyToLocalFile(src, dst);
    }

    @Test
    public void listFiles() throws Exception{
        FileStatus[] statuses = fileSystem.listStatus(new Path("/hdfsapi/test"));
        for(FileStatus file : statuses){
            String isDir = file.isDirectory() ? "文件夹" : "文件";
            String permission = file.getPermission().toString();
            long length = file.getLen();
            String path = file.getPath().toString();
            System.out.println(isDir + "\t" + permission + "\t" + length + "\t" + path);
        }
    }

    /**
     * 递归展示文件夹内容
     * @throws Exception
     */
    @Test
    public void listFileRecursive() throws Exception{
        RemoteIterator<LocatedFileStatus> files = fileSystem.listFiles(new Path("/hdfsapi/test"), true);
        while (files.hasNext()){
            LocatedFileStatus file = files.next();
            String isDir = file.isDirectory() ? "文件夹" : "文件";
            String permission = file.getPermission().toString();
            long length = file.getLen();
            String path = file.getPath().toString();
            System.out.println(isDir + "\t" + permission + "\t" + length + "\t" + path);
        }
    }

    /**
     * 得到文件系统上块的信息，工作中常用
     * @throws Exception
     */
    @Test
    public void getFileBlockLocations() throws Exception{
        FileStatus fileStatus = fileSystem.getFileStatus(new Path ("/jdk-8u162-linux-x64.tar.gz"));
        BlockLocation[] blocks = fileSystem.getFileBlockLocations(fileStatus, 0, fileStatus.getLen());
        for (BlockLocation block : blocks){
            for (String name: block.getNames()){
                for (String hosts : block.getHosts()){
                    System.out.println(name + " : " + block.getLength() + " : " + block.getOffset() + " : " + hosts);
                }
               // System.out.println(name + " : " + block.getLength() + " : " + block.getOffset() + " : " + block.getHosts());
            }
        }
    }

    /**
     * 删除文件系统的文件
     * @throws Exception
     */
    @Test
    public void delete() throws Exception{
        boolean result = fileSystem.delete(new Path ("/hdfsapi/test/MacDict.dmg"), true);
        System.out.println(result);
    }

    @After
    public void tearDown() throws Exception{
        configuration = null;
        fileSystem = null;
        System.out.println("-----tearDown--------------");
    }


 /*   public static void main(String[] args) throws Exception{

        Configuration configuration = new Configuration();
        //FileSystem fileSystem = FileSystem.get(new URI("hdfs://hadoop000:8020"),configuration);
        FileSystem fileSystem1 = FileSystem.get(new URI("hdfs://39.108.114.91:8020"),configuration,"hadoop");
        Path path = new Path("/hdfsapi/test");
        //boolean result = fileSystem.mkdirs(path);
        boolean resutl2 = fileSystem1.mkdirs(path);
        //System.out.println(result);
        System.out.println(resutl2);
    }*/
}
