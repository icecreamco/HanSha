package com.icecreamco.hansha;

import android.content.Context;

import java.util.LinkedList;

/**
 * Created by icecreamco on 2016/1/11.
 */
public class Records {

    public static final String RECORDS = "records";

    private static Records records;
    private Context context;
    private LinkedList<int[]> recordlist;
    private JsonSaveAndRead jsonSaveAndRead;

    /*
     * 构造函数
     */
    private Records(Context c) {
        context = c;
        jsonSaveAndRead = new JsonSaveAndRead(c, "records");
        LinkedList<int[]> list;
        if ((list = jsonSaveAndRead.read()) != null) {
            recordlist = list;
        } else {
            recordlist = new LinkedList<>();
        }
    }

    /*
     * get单例
     */
    public static Records getRecords(Context c) {
        if (records == null) {
            records = new Records(c.getApplicationContext());
        }
        return records;
    }

    /*
     * add list
     */
    public void add(int[] record) {
        recordlist.addFirst(record);
        jsonSaveAndRead.save(recordlist);
    }

    /*
     * delete
     */
    public void delete(int index) {
        recordlist.remove(index);
        jsonSaveAndRead.save(recordlist);
    }
    public void delete(int[] record) {
        for (int[] exist : recordlist) {
            if (record[19] == exist[19]) {
                int i = 0;
                for (i = 0; i < 19; i++) {
                    if (record[i] != exist[i])
                        break;
                }
                if (i == 19) {
                    recordlist.remove(exist);
                    break;
                }
            }
        }
        jsonSaveAndRead.save(recordlist);
    }

    /*
     * clear
     */
    public void clear() {
        recordlist.clear();
        jsonSaveAndRead.save(recordlist);
    }

    /*
     * isExist()
     */
    public boolean isExist(int[] record) {
        for (int[] exist : recordlist) {
            if (record[19] == exist[19]) {
                int i = 0;
                for (i = 0; i < 19; i++) {
                    if (record[i] != exist[i])
                        break;
                }
                if (i == 19) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * getrecordsnum
     */
    public int getRecordsNum() {
        return recordlist.size();
    }

    /*
     * getrecordslist
     */
    public LinkedList<int[]> getRecordlist() {
        return recordlist;
    }
}
