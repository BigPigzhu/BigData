package com.shujia.mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class Demo2Sort {


    public static class SortMapper extends Mapper<LongWritable, Text, County, NullWritable> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            String[] split = value.toString().split(",");
            String id = split[0];
            long x = Long.parseLong(split[1]);
            long y = Long.parseLong(split[2]);

            County county = new County(id, x, y);
            context.write(county, NullWritable.get());
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();

        Job job = Job.getInstance(configuration);

        job.setJobName("sort");
        job.setJarByClass(Demo2Sort.class);


        job.setMapperClass(SortMapper.class);

        job.setMapOutputKeyClass(County.class);
        job.setMapOutputValueClass(NullWritable.class);

        //输入路径
        FileInputFormat.addInputPath(job, new Path("/data/sort/"));

        //输出路径
        FileOutputFormat.setOutputPath(job, new Path("/data/out2"));


        job.waitForCompletion(true);
    }
}
