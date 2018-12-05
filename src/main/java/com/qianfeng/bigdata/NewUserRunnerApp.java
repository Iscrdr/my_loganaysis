/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: NewUserRunner
 * Author:   14751
 * Date:     2018/9/21 2:07
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间         版本号            描述
 */
package com.qianfeng.bigdata;


import com.qianfeng.bigdata.analysis.kv.key.StatsUserDimension;
import com.qianfeng.bigdata.analysis.kv.value.OutputMapWritable;
import com.qianfeng.bigdata.analysis.kv.value.TimeOutputValue;
import com.qianfeng.bigdata.analysis.mr.NewUserMapper;
import com.qianfeng.bigdata.analysis.mr.NewUserReducer;
import com.qianfeng.bigdata.analysis.outputformat.OutputToMysqlFormat;
import com.qianfeng.bigdata.common.GlobalConstants;
import com.qianfeng.bigdata.utils.TimeUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;


/**
 * @Description 用户模块map阶段输出的value类型（其他模块是否能够使用）
 * 如果配置文件中，配置了jobhistory,但是服务没有开启，运行mr任务会报错
 * 但是不影响mr的运行结果（如果未开启这个服务，在mr运行结束后，写日志到hdfs会报错）
 * mr-jobhistory-daemon.sh start historyserver
 * @Author cqh <caoqingghai@1000phone.com>
 * @Version V1.0
 * @Since 1.0
 * @Date 2018/11/9 02：24
 */
@SpringBootApplication(scanBasePackages = "com.qianfeng.bigdata")
public class NewUserRunnerApp  {
    private static final Logger logger = Logger.getLogger(NewUserRunnerApp.class);
    private static ApplicationContext applicationContext;

    public static void displayAllBeans() {
        String[] allBeanNames = applicationContext.getBeanDefinitionNames();
        for(String beanName : allBeanNames) {
            System.out.println(beanName);
        }
    }

    //主函数---入口
    public static void main(String[] args) throws IOException {

        applicationContext = SpringApplication.run(NewUserRunnerApp.class, args);
       // displayAllBeans();

        Configuration conf = new Configuration();
        conf.addResource("core-site.xml");
        conf.set("hadoop.home.dir", "D:\\software\\hadoop-2.7.7");
        //为结果表中的created赋值，设置到conf中,需要我们传递参数---一定要在job获取前设置参数
        setArgs(args, conf);
        Job job = Job.getInstance(conf, "NEW_USER TO MYSQL");
        job.setJarByClass(NewUserRunnerApp.class);

        //设置map相关参数
        job.setMapperClass(NewUserMapper.class);
        job.setMapOutputKeyClass(StatsUserDimension.class);
        job.setMapOutputValueClass(TimeOutputValue.class);
        //设置本地提交集群运行，要使用job.setJar()将打包好的jar包路径写入
        //job.setJar("D:\\javaProject\\GP1814Log_Analystic\\target\\analystic-1.0.jar");

        //设置reduce相关参数
        //设置reduce端的输出格式类
        job.setReducerClass(NewUserReducer.class);
        job.setOutputKeyClass(StatsUserDimension.class);
        job.setOutputValueClass(OutputMapWritable.class);
        job.setOutputFormatClass(OutputToMysqlFormat.class);

        //设置reduce task的数量
        job.setNumReduceTasks(1);

        //设置输入参数
        handleInputOutput(job);
        try {
            int result =  job.waitForCompletion(true)?1:0;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 参数处理,将接收到的日期存储在conf中，以供后续使用
     * @param args  如果没有传递日期，则默认使用昨天的日期
     * @param conf
     */
    private static void setArgs(String[] args, Configuration conf) {
        String date = null;
        for (int i = 0;i < args.length;i++){
            if(args[i].equals("-d")){
                if(i+1 < args.length){
                    date = args[i+1];
                    break;
                }
            }
        }
        //代码到这儿，date还是null，默认用昨天的时间
        if(date == null){
            date = TimeUtil.getYesterday();
        }
        //然后将date设置到时间conf中
        conf.set(GlobalConstants.RUNNING_DATE,date);
    }

    /**
     * 设置输入输出,_SUCCESS文件里面是空的，所以可以直接读取清洗后的数据存储目录
     * @param job
     */
    private static void handleInputOutput(Job job) {
        String[] fields = job.getConfiguration().get(GlobalConstants.RUNNING_DATE).split("-");
        String month = fields[1];
        String day = fields[2];
        month = "12";
        day="02";
        try {
            FileSystem fs = FileSystem.get(job.getConfiguration());
            Path inpath = new Path("hdfs://hadoop-02:9000/logs/ods/" + month + "/" + day );
            //Path inpath = new Path("hdfs://hadoop-02:9000/logs/ods/" + month );

            if(fs.exists(inpath)){
                FileInputFormat.addInputPath(job,inpath);
            }else{
                throw new RuntimeException("输入路径不存在inpath :" + inpath.toString());
            }
        } catch (IOException e) {
            logger.warn("设置输入输出路径异常！！！",e);
        }
    }
}
