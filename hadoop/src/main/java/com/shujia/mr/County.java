package com.shujia.mr;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class County implements WritableComparable<County> {


    private String county;

    private Long x;
    private Long y;

    public County() {
    }

    public County(String county, Long x, Long y) {
        this.county = county;
        this.x = x;
        this.y = y;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public Long getY() {
        return y;
    }

    public void setY(Long y) {
        this.y = y;
    }

    @Override
    public int compareTo(County o) {
        int i = county.compareTo(o.getCounty());

        if (i == 0) {

            long l = x - o.getX();
            if (l == 0) {

                long l1 = o.getY() - y;
                return (int) l1;

            } else {
                return (int) l;
            }
        } else {
            return i;
        }


    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(county);
        dataOutput.writeLong(x);
        dataOutput.writeLong(y);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {

        county = dataInput.readUTF();
        x = dataInput.readLong();
        y = dataInput.readLong();
    }

    @Override
    public String toString() {
        return county + "," + x + "," + y;
    }
}
