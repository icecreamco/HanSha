package com.icecreamco.hansha;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by icecreamco on 2016/1/11.
 */
public class RecordsFragment extends Fragment {

    private RelationListAdapter adapter;        // list adapter
    private ListView listView;                  // listview
    private TextView listText;
    private Button record_num;                // 显示历史记录的总数

    /*
     * onCreate
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /*
     * oncreateView
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_records, container, false);

        // 添加新纪录按钮
        Button add = (Button)view.findViewById(R.id.add_record);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 关闭此fragment并开启seniorityfragment
                FragmentManager fm = getActivity().getSupportFragmentManager();
                SeniorityFragment fragment = new SeniorityFragment();
                fm.beginTransaction()
                        .remove(fm.findFragmentById(R.id.framelayout))
                        .add(R.id.framelayout, fragment)
                        .commit();
                // 偷懒的做法，不太好，将生成的fragment传入mainactivity中用来在清空历史记录时刷新视图
                MainActivity.mainActivity.setSenioryFragment(fragment);
                MainActivity.mainActivity.fragmentState = MainActivity.STATE_SENIORITY;
                // 修改侧滑栏状态
                NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
                navigationView.setCheckedItem(R.id.nav_seniority);
            }
        });

        // 设置记录数的显示
        record_num = (Button)view.findViewById(R.id.record_num);
        record_num.setText("记录数 : " + Records.getRecords(getActivity()).getRecordsNum());

        // listview的设置
        listView= (ListView)view.findViewById(R.id.listview);
        adapter = new RelationListAdapter(Records.getRecords(getActivity()).getRecordlist());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 点击item，进入seniorityfragment并传入相应数据
                int[] record = (int[]) parent.getAdapter().getItem(position);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                SeniorityFragment fragment = SeniorityFragment.newInstance(record);
                fm.beginTransaction()
                        .remove(fm.findFragmentById(R.id.framelayout))
                        .add(R.id.framelayout, fragment)
                        .commit();
                // 偷懒的做法，不太好，将生成的fragment传入mainactivity中用来在清空历史记录时刷新视图
                MainActivity.mainActivity.setSenioryFragment(fragment);
                MainActivity.mainActivity.fragmentState = MainActivity.STATE_SENIORITY;
                // 设置侧滑栏
                NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
                navigationView.setCheckedItem(R.id.nav_seniority);
            }
        });
        // 为listview登记上下文菜单
        registerForContextMenu(listView);

        // 若list为空，显示文字
        listText = (TextView)view.findViewById(R.id.list_empty_text);
        if (adapter.isEmpty()) {
            listText.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else {
            listText.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }

        // 设置上下文菜单，用来删除历史记录
//        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);        // 多选模式
//        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
//            @Override
//            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
//            }
//
//            @Override
//            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//                // 填充菜单
//                MenuInflater inflater = mode.getMenuInflater();
//                inflater.inflate(R.menu.menu_context, menu);
//                return true;
//            }
//
//            @Override
//            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//                return false;
//            }
//
//            @Override
//            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.menu_delete :
//                        int l = listView.getCount();
//                        for (int i = 0; i < l; i++) {
//                            if (listView.isItemChecked(i)) {
//                                Records.getRecords(getActivity()).delete(i);
//                            }
//                        }
//                        mode.finish();
//                        // 刷新视图
//                        refreshView();
//                        break;
//                    default:
//                        break;
//                }
//                return false;
//            }
//
//            @Override
//            public void onDestroyActionMode(ActionMode mode) {
//
//            }
//        });

        return view;
    }

    /*
     * onresume
     */
    @Override
    public void onResume() {
        super.onResume();
        // 刷新视图
        adapter.notifyDataSetChanged();
        refreshView();
    }

    // 上下文删除菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
         getActivity().getMenuInflater().inflate(R.menu.menu_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int position = info.position;
        switch (item.getItemId()) {
            case R.id.menu_delete:
                Records.getRecords(getActivity()).delete(position);
                refreshView();
                break;
            default:
                break;
        }
        return true;
    }

    /*
             * refreshview
             */
    public void refreshView() {
        adapter.notifyDataSetChanged();
        if (adapter.isEmpty()) {
            listText.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else {
            listText.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }
        record_num.setText("记录数 : " + Records.getRecords(getActivity()).getRecordsNum());
    }

    /*
     * listAdapter
     */
    private class RelationListAdapter extends ArrayAdapter<int[]> {

        /*
         * 构造函数
         */
        public RelationListAdapter(LinkedList<int[]> records) {
            super(getActivity(), 0, records);
        }

        /*
         * getView
         */

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item, null);
            }

            int[] record = getItem(position);
            TextView name = (TextView)convertView.findViewById(R.id.list_name);
            name.setText(RelationMap.NAMES[record[19] - 1]);
            TextView relation = (TextView)convertView.findViewById(R.id.list_relation);
            String s = "";
            s += "我";
            for (int r : record) {
                if (r == 0)
                    break;
                s += "的";
                s += RelationMap.RELATIONS[r - 1];
            }
            relation.setText(s);

            return convertView;
        }
    }
}
