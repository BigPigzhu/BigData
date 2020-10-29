package com.shujia.mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Demo1TopN {

    public static class TopNMapper extends Mapper<LongWritable, Text, Text, LongWritable> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            String[] split = value.toString().split(",");
            String id = split[0];
            long pic = Long.parseLong(split[1]);

            context.write(new Text(id), new LongWritable(pic));
        }
    }

    public static class TopNReduce extends Reducer<Text, LongWritable, Text, NullWritable> {
        @Override
        protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {


            String id = key.toString();

            //组内偶爱徐取top

            ArrayList<Long> longs = new ArrayList<>();
            for (LongWritable value : values) {
                longs.add(value.get());
            }

            //排序
            longs.sort(new Comparator<Long>() {
                @Override
                public int compare(Long o1, Long o2) {
                    return (int) (o2 - o1);
                }
            });


            //取top
            for (int i = 0; i < 2; i++) {
                Long aLong = longs.get(i);

                String line = id + "," + aLong;

                //输出
                context.write(new Text(line), NullWritable.get());

            }
        }
    }


    public static void main(String[] args) throws Exception {


        Configuration configuration = new Configuration();

        Job job = Job.getInstance(configuration);

        job.setJobName("topn");
        job.setJarByClass(Demo1TopN.class);


        job.setMapperClass(TopNMapper.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);

        job.setReducerClass(TopNReduce.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        //输入路径
        FileInputFormat.addInputPath(job, new Path("/data/topn/"));

        //输出路径
        FileOutputFormat.setOutputPath(job, new Path("/data/out1"));


        job.waitForCompletion(true);

    }
}
