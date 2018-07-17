package com.example.adambenyahia.mytracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyTrackers extends AppCompatActivity {
    ArrayList<AdapterItems>     listnewsData = new ArrayList<AdapterItems>();
    MyCustomAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trackers);
        listnewsData.add(new AdapterItems("",""));
        myadapter=new MyCustomAdapter(listnewsData);
        ListView lsNews=(ListView)findViewById(R.id.listView);
        lsNews.setAdapter((myadapter));
    }

    void Refresh(){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contact_list, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.goback:
                finish();
                return true;
            case R.id.add:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class MyCustomAdapter extends BaseAdapter {
        public  ArrayList<AdapterItems>  listnewsDataAdapter ;

        public MyCustomAdapter(ArrayList<AdapterItems> listnewsDataAdapter) {
            this.listnewsDataAdapter=listnewsDataAdapter;
        }


        @Override
        public int getCount() {
            return listnewsDataAdapter.size();
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

            final   AdapterItems s = listnewsDataAdapter.get(position);

            TextView tv_user_name=(TextView)myView.findViewById(R.id.tv_user_name);
            tv_user_name.setText(s.UserName);

            TextView tv_phone=(TextView)myView.findViewById(R.id.tv_phone);
            tv_phone.setText(s.PhoneNumber);

            return myView;
        }

    }
}
