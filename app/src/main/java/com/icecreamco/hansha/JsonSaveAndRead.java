package com.icecreamco.hansha;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by icecreamco on 2016/1/11.
 */
public class JsonSaveAndRead {

    public static final String TAG = "JsonSaveAndRead";
    public static final String RECORD = "record";

    private Context context;
    private String filename;

    /*
     * 构造函数
     */
    public JsonSaveAndRead(Context c, String s) {
        context = c;
        filename = s;
    }

    /*
     * save
     */
    public void save(LinkedList<int[]> records) {
        StringBuilder str = new StringBuilder();
        for (int[] record : records) {
            for (int x : record) {
                str.append(x);
                str.append(" ");
            }
        }

        Writer writer = null;
        try{
            OutputStream out = context.openFileOutput(filename, context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(str.toString());
        } catch (IOException e) {
            Log.i(TAG, "IOException");
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                Log.i(TAG, "close error");
            }
        }
    }

    /*
     * read
     */
    public LinkedList<int[]> read() {
        LinkedList<int[]> records = new LinkedList<>();
        BufferedReader reader = null;
        try {
            InputStream in = context.openFileInput(filename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder s = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null)
                s.append(line);
            String str = s.toString();
            // parse
            Scanner scan = new Scanner(str);
            while (scan.hasNextInt()) {
                int[] record = new int[20];
                for (int i = 0; i < 20; i++) {
                    record[i] = scan.nextInt();
                }
                records.add(record);
            }
        } catch (IOException e) {
            Log.i(TAG, "IO error");
        } finally {
            try{
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                Log.i(TAG, "close error");
            }
        }

        return records;
    }
}
