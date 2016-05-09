package com.icecreamco.hansha;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

/**
 * Created by icecreamco on 2016/1/4.
 */
public class SeniorityFragment extends Fragment {

    public static final String TAG = "SeniorityFragment";
    public static final String ARGS = "arguments";

    private int[] record = new int[20];
    private int index;
    private TextView result;
    private TextView relation;
    private ImageButton fab;

    /*
     * onCreate()
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int[] args = getArguments().getIntArray(ARGS);
            if (args != null) {
                record = args.clone();
                int i;
                for (i = 0; i < 20; i++) {
                    if (record[i] == 0)
                        break;
                }
                index = i;
            }

        }
    }

    /*
     * new Instance
     */
    public static SeniorityFragment newInstance(int[] rec) {
        Bundle args = new Bundle();
        args.putIntArray(ARGS, rec);
        SeniorityFragment fragment = new SeniorityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /*
     * onCreateView()
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seniority, container, false);

        // 悬浮按钮
        fab = (ImageButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 加入记录
                boolean isRecorded = Records.getRecords(getActivity()).isExist(record);
                if (isRecorded) {
                    Records.getRecords(getActivity()).delete(record.clone());
                    fab.setImageResource(R.drawable.ic_add_white_48dp);
                    Toast.makeText(getActivity(), "已删除历史记录～", Toast.LENGTH_SHORT).show();
                } else {
                    Records.getRecords(getActivity()).add(record.clone());
                    fab.setImageResource(R.drawable.ic_star_white_48dp);
                    Toast.makeText(getActivity(), "已加入历史记录～", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 显示部分
        result = (TextView)view.findViewById(R.id.result_text);
        relation = (TextView)view.findViewById(R.id.relation_text);

        // 按钮
        Button button = (Button)view.findViewById(R.id.b_1);
        button.setOnClickListener(new ButtonListener());
        button = (Button)view.findViewById(R.id.b_2);
        button.setOnClickListener(new ButtonListener());
        button = (Button)view.findViewById(R.id.b_3);
        button.setOnClickListener(new ButtonListener());
        button = (Button)view.findViewById(R.id.b_4);
        button.setOnClickListener(new ButtonListener());
        button = (Button)view.findViewById(R.id.b_5);
        button.setOnClickListener(new ButtonListener());
        button = (Button)view.findViewById(R.id.b_6);
        button.setOnClickListener(new ButtonListener());
        button = (Button)view.findViewById(R.id.b_7);
        button.setOnClickListener(new ButtonListener());
        button = (Button)view.findViewById(R.id.b_8);
        button.setOnClickListener(new ButtonListener());
        button = (Button)view.findViewById(R.id.b_9);
        button.setOnClickListener(new ButtonListener());
        button = (Button)view.findViewById(R.id.b_10);
        button.setOnClickListener(new ButtonListener());
        button = (Button)view.findViewById(R.id.b_11);
        button.setOnClickListener(new ButtonListener());
        button = (Button)view.findViewById(R.id.b_12);
        button.setOnClickListener(new ButtonListener());
        button = (Button)view.findViewById(R.id.b_clear);
        button.setOnClickListener(new ButtonListener());
        button = (Button)view.findViewById(R.id.b_remove);
        button.setOnClickListener(new ButtonListener());

        return view;
    }

    /*
         * 结果显示
         */
    public void refreshView() {
        record[19] = RelationMap.getMap(getActivity()).query(record);
        // 更改显示
        result.setText(RelationMap.NAMES[record[19] - 1]);
        String s = "";
        s += "我";
        for (int r : record) {
            if (r == 0)
                break;
            s += "的";
            s += RelationMap.RELATIONS[r - 1];
        }
        relation.setText(s);
        // 更改悬浮按钮
        boolean isRecorded = Records.getRecords(getActivity()).isExist(record);
        if (isRecorded) {
            fab.setImageResource(R.drawable.ic_star_white_48dp);
        } else {
            fab.setImageResource(R.drawable.ic_add_white_48dp);
        }
    }

    /*
     * clearRecord
     */
    public void clearRecord() {
        Arrays.fill(record, 0);
        index = 0;
        refreshView();
    }

    /*
     * onResume()
     */

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, Arrays.toString(record));
        refreshView();
    }

    /*
         * buttonlistener
         */
    private class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.b_1:
                    if (index == 18)
                        break;
                    record[index++] = 1;
                    break;
                case R.id.b_2:
                    if (index == 18)
                        break;
                    record[index++] = 2;
                    break;
                case R.id.b_3:
                    if (index == 18)
                        break;
                    record[index++] = 3;
                    break;
                case R.id.b_4:
                    if (index == 18)
                        break;
                    record[index++] = 4;
                    break;
                case R.id.b_5:
                    if (index == 18)
                        break;
                    record[index++] = 5;
                    break;
                case R.id.b_6:
                    if (index == 18)
                        break;
                    record[index++] = 6;
                    break;
                case R.id.b_7:
                    if (index == 18)
                        break;
                    record[index++] = 7;
                    break;
                case R.id.b_8:
                    if (index == 18)
                        break;
                    record[index++] = 8;
                    break;
                case R.id.b_9:
                    if (index == 18)
                        break;
                    record[index++] = 9;
                    break;
                case R.id.b_10:
                    if (index == 18)
                        break;
                    record[index++] = 10;
                    break;
                case R.id.b_11:
                    if (index == 18)
                        break;
                    record[index++] = 11;
                    break;
                case R.id.b_12:
                    if (index == 18)
                        break;
                    record[index++] = 12;
                    break;
                case R.id.b_clear:
                    Arrays.fill(record, 0);
                    index = 0;
                    break;
                case R.id.b_remove:
                    if (index == 0)
                        break;
                    index--;
                    record[index] = 0;
                    break;
                default:
                    break;
            }
            // 刷新试图
            refreshView();
        }
    }
}
