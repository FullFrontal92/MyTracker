package com.example.adambenyahia.mytracker;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyTrackers extends AppCompatActivity {
    ArrayList<AdapterItems>     listnewsData = new ArrayList<AdapterItems>();
    MyCustomAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trackers);
        listnewsData.add(new AdapterItems("John","00012185"));
        myadapter=new MyCustomAdapter(listnewsData);
        ListView lsNews=(ListView)findViewById(R.id.listView);
        lsNews.setAdapter((myadapter));
        Refresh();
    }

    void Refresh(){
        listnewsData.clear();
        for (Map.Entry m:GlobalInfo.MyTrackers.entrySet()){
            listnewsData.add(new AdapterItems(  m.getValue().toString(), m.getKey().toString()));

        }
        myadapter.notifyDataSetChanged();

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
                CheckUserPermsions();
                PickContact();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    void CheckUserPermsions(){
        if ( Build.VERSION.SDK_INT >= 23){
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED  ){
                requestPermissions(new String[]{
                                android.Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return ;
            }
        }

        PickContact();

    }

    //get acces to location permsion
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PickContact();
                } else {
                    // Permission Denied
                    Toast.makeText( this,"your message" , Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //Get last location
    public  void getLastLocation(){
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Location myLocation=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (myLocation == null)
        {
            myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

        }
    }

    void PickContact(){
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }

    // Declare
    static final int PICK_CONTACT=1;
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {

                    Uri contactData = data.getData();
                    Cursor c =  getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {


                        String id =c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone =c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        String cNumber="No number";
                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,
                                    null, null);
                            phones.moveToFirst();
                            cNumber =GlobalInfo.FormatPhoneNumber (phones.getString(phones.getColumnIndex("data1")));
                            System.out.println("number is:"+cNumber);
                        }
                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                        GlobalInfo.MyTrackers.put(cNumber,name);
                        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
                        mDatabase.child("Users").child(cNumber).child("Finders").child(GlobalInfo.PhoneNumber).setValue(true);

                        Refresh();
                        //update firebase and
                        //update list
                        //update database
                    }
                }
                break;
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
