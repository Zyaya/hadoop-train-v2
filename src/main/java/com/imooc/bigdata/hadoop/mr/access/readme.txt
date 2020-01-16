自定义复杂类型

access.log
    第二个字段：手机号
    倒数第三字段：上行流量
    倒数第二字段：下行流量

需求：统计每个手机号的上行流量和、下行流量和、总的流量和（上行流量和+下行流量和）

Access.java
    手机号、上行流量、下行流量

既然要求和：根据手机号进行分组，然后把该手机号对应的上下行流量加起来

Mapper：把手机号、上行流量、下行流量拆开
    把手机号作为key，把Access作为value写出去

Reducer：（13632982692,<Access,Access>)

public class HashPartitioner<K2, V2> implements Partitioner<K2, V2> {

  public void configure(JobConf job) {}

  /** Use {@link Object#hashCode()} to partition. */
  public int getPartition(K2 key, V2 value,
                          int numReduceTasks) {
    return (key.hashCode() & Integer.MAX_VALUE) % numReduceTasks;
  }

}

numReduceTasks:你的作业所指定的reducer的个数，决定了reduce作业输出文件的个数
HashPartitioner是MapReducer默认的分区规则


reducer个数：3
1 % 3 = 1
2 % 3 = 2
3 % 3 = 3

需求：将统计结构按照手机号的前缀进行区分，并输出到不同的输出文件中去
    13* ==> ..
    15* ==> ..
    other ==> ..

Partitioner决定maptasks输出的数据交由哪个reducetask处理
**面试常考：默认实现：分发的key的hash值与reduce task个数取模