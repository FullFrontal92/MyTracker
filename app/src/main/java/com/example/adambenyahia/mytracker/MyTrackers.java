package com.example.adambenyahia.mytracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyTrackers extends AppCompatActivity {
    ArrayList<AdapterItems>     listnewsData = new ArrayList<AdapterItems>();
    MyCustomAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trackers);
        listnewsData.add(new AdapterItems("Adam","012345"));
        myadapter=new MyCustomAdapter(listnewsData);
        ListView lsNews=(ListView)findViewById(R.id.listView);
        lsNews.setAdapter((myadapter));
    }

    void Refresh(){

    }
    private class MyCustomAdapter extends BaseAdapter {
        public  ArrayList<AdapterItems>  listnewsDataAdpater ;

        public MyCustomAdapter(ArrayList<AdapterItems> listnewsDataAdpater) {
            this.listnewsDataAdpater=listnewsDataAdpater;
        }


        @Override
        public int getCount() {
            return listnewsDataAdpater.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater mInflater = getLayoutInflater();
            View myView = mInflater.inflate(R.layout.single_row_conact, null);

            final   AdapterItems s = listnewsDataAdpater.get(position);

            TextView tv_user_name=(TextView)myView.findViewById(R.id.tv_user_name);
            tv_user_name.setText(s.UserName);

            TextView tv_phone=(TextView)myView.findViewById(R.id.tv_phone);
            tv_phone.setText(s.PhoneNumber);

            return myView;
        }

    }
}
