//package com.imooc.bigdata.hadoop.Funtions.UDF;
//
//
//
//import java.util.Date;
//import java.text.SimpleDateFormat;
//import org.apache.hadoop.hive.ql.exec.UDF;
//
//
//public class UDFZodiacSign extends UDF {
//    private SimpleDateFormat df;
//
//    public UDFZodiacSign(){
//        df = new SimpleDateFormat("MM-dd-yyyy");
//    }
//
//    public String evaluate(Date bday){
//        return this.evaluate( bday.getMonth(),bday.getDay() );
//    }
//
//    public String evaluate(String bday){
//        Date date = null;
//        try {
//            date = df.parse(bday);
//        }catch(Exception ex){
//            return null;
//        }
//
//        return this.evaluate( date.getMonth()+1,date.getDay() );
//    }
//
//    public String  evaluate(Integer month,Integer day){
//        if(month==10){
//            return "hello zly";
//        }
//
//        if(month==12){
//            return "hello xiaozhu";
//        }
//
//        return null;
//    }
//
//}