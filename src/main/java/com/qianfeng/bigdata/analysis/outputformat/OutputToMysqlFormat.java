package com.qianfeng.bigdata.analysis.outputformat;

import com.qianfeng.bigdata.analysis.dimension.BaseDimension;
import com.qianfeng.bigdata.analysis.kv.value.StatsOutputValue;
import com.qianfeng.bigdata.services.NewUserService;
import com.qianfeng.bigdata.utils.SpringContextUtils;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class OutputToMysqlFormat extends OutputFormat<BaseDimension, StatsOutputValue>{

    //private NewUserService newUserService = (NewUserService)SpringContextUtils.getBean("newUserService");
    @Override
    public RecordWriter<BaseDimension, StatsOutputValue> getRecordWriter(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        return new OutputToMysqlRecordWriter();
    }

    @Override
    public void checkOutputSpecs(JobContext jobContext) throws IOException, InterruptedException {

    }

    @Override
    public OutputCommitter getOutputCommitter(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        return new FileOutputCommitter(FileOutputFormat.getOutputPath(taskAttemptContext),taskAttemptContext);
    }



    public static class OutputToMysqlRecordWriter extends RecordWriter<BaseDimension, StatsOutputValue> {


        private NewUserService newUserService = (NewUserService) SpringContextUtils.getBean("newUserService");

        public OutputToMysqlRecordWriter( ) {
        }

        @Override
        public void write(BaseDimension key, StatsOutputValue value) throws IOException, InterruptedException {
            newUserService.addDemission(key,value);
        }

        @Override
        public void close(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
            newUserService.insertDemission();
        }

    }
}
