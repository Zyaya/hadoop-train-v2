package com.imooc.bigdata.hadoop.hdfs;

/**
 * 自定义wc实现类
 */
public class CaseIgnoreWordCountMapper implements ImoocMapper{
    public void map(String line, ImoocContext context){
        String[] words = line.toLowerCase().split(" ");
        for (String word : words){
            Object value = context.get(word);
            if (value == null){ //表示没有出现过该单词
                //词频统计就是每个单词给赋上一个1，然后做一个shuffle操作，将单词相同的分到一个地方去，然后把值加起来
                context.write(word, 1);
            } else{
                int v = Integer.parseInt(value.toString());
                context.write(word, v+1); //取出单词对应的次数+1
            }
        }
    }
}
