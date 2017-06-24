package com.example.rajesh.organicfoods;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProductDisplay extends AppCompatActivity {
    String s, cartq, productid, pName, pPrice, pQuantity;
    ProgressDialog progress;
    int leapyear=0;
    PopupWindow pw;
    Map map=new HashMap<>();
    ListView ProductView;
    ProductAdapter pAdt;
    ArrayList<ProductClass> productList;
    public static String URL;
    TextView invalid,fromdate, tenddate, productname, productprice, outofstock;
    EditText productquantity;
    Button addcart, startdate, enddate;
    int flag = 0, check = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_display);
        ProductView = (ListView) findViewById(R.id.Productimage);
        productList = new ArrayList<ProductClass>();

        // GetXMLTask task = new GetXMLTask();
        // Execute the task
        map.put("1","31");
        map.put("3","31");
        map.put("4","30");
        map.put("5","31");
        map.put("6","30");
        map.put("7","31");
        map.put("8","31");
        map.put("9","30");
        map.put("10","31");
        map.put("11","30");
        map.put("12","31");
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        if(mYear%4!=0)
        {leapyear=0;map.put("2","28");}
        else if(mYear%100!=0)
        {leapyear=1;map.put("2","29");}
        else if(mYear%400==0)
        {leapyear=1;map.put("2","29");}
        else
        {leapyear=0;map.put("2","28");}

        progress = new ProgressDialog(ProductDisplay.this);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.child("Products").getChildren()) {
                    Product q = postSnapshot.getValue(Product.class);
                    System.out.println(q.Productimage + " " + q.ProductName + " " + q.ProductId + " " + q.ProductQuantity + " " + q.Productprice + " " + q.Productimage + "\n");
                    URL = q.Productimage;
                    productList.add(new ProductClass(q.ProductName, q.ProductId, q.ProductQuantity, q.Productprice, q.Productimage));


                }
                flag = 1;
                pAdt = new ProductAdapter(ProductDisplay.this, R.layout.productlayout, productList);
                //ProductView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                ProductView.setAdapter(pAdt);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //task.execute(new String[] { URL });
        if (flag == 1)
            new CountDownTimer(3 * productList.size() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    progress.setMessage("Loading Products...");
                }

                @Override
                public void onFinish() {
                    progress.dismiss();
                }
            }.start();

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot postSnapshot : dataSnapshot.child("Products").getChildren()) {
                    Product q = postSnapshot.getValue(Product.class);
                    System.out.println(q.Productimage + " " + q.ProductName + " " + q.ProductId + " " + q.ProductQuantity + " " + q.Productprice + " " + q.Productimage + "\n");
                    URL = q.Productimage;
                    System.out.println(q);
                    productList.add(new ProductClass(q.ProductName, q.ProductId, q.ProductQuantity, q.Productprice, q.Productimage));
                    //songAdt = new ProductDisplayAdapter(ProductDisplay.this, productList);


                }
                ProductView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                ProductView.setAdapter(pAdt); //Product q = dataSnapshot.getValue(Product.class);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        ProductView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                initiatePopupWindow(productList.get(position).ProductId, productList.get(position).ProductName, productList.get(position).Productprice, productList.get(position).ProductQuantity);
                // Toast.makeText(getApplicationContext(),productList.get(position).ProductName+productList.get(position).Productprice+productList.get(position).ProductQuantity, Toast.LENGTH_SHORT).show();

            }
        });


    }

    private class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            for (String url : urls) {
                map = downloadImage(url);
            }
            return map;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            //ProductImage.setImageBitmap(result);
        }

        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }

        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }
    }

    private void initiatePopupWindow(String pid, String pname, String pprice, String pquantity) {
        try {
            productid = pid;
            pName = pname;
            pPrice = pprice;
            pQuantity = pquantity;
            LayoutInflater inflater = (LayoutInflater) ProductDisplay.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup,
                    (ViewGroup) findViewById(R.id.popup_element));
            pw = new PopupWindow(layout, 900, 950, true);
            // display the popup in the center
            pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
            productname = (TextView) layout.findViewById(R.id.productName);
            fromdate = (TextView) layout.findViewById(R.id.fromdate);
            tenddate = (TextView) layout.findViewById(R.id.enddate);
            startdate = (Button) layout.findViewById(R.id.startdate);
            enddate = (Button) layout.findViewById(R.id.enddatebutton);
            productprice = (TextView) layout.findViewById(R.id.productPrice);
            productquantity = (EditText) layout.findViewById(R.id.productquantity);
            outofstock = (TextView) layout.findViewById(R.id.outofstock);
            addcart = (Button) layout.findViewById(R.id.addcart);
            invalid=(TextView)layout.findViewById(R.id.invalid);
            productname.setText(pname);
            productprice.setText(pprice);
            s = pquantity;
            startdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);
                    System.out.println("the selected " + mDay);
                    DatePickerDialog dialog = new DatePickerDialog(ProductDisplay.this,
                            new mDateSetListener1(), mYear, mMonth, mDay);
                    dialog.show();
                }
            });
            enddate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);
                    System.out.println("the selected " + mDay);
                    DatePickerDialog dialog = new DatePickerDialog(ProductDisplay.this,
                            new mDateSetListener2(), mYear, mMonth, mDay);
                    dialog.show();
                }
            });
            addcart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartq = productquantity.getText().toString();

                    if (cartq.length() != 0) {
                        int c = Integer.parseInt(cartq);
                        int d = Integer.parseInt(s);
                        if (c <= d) {
                            outofstock.setText(" ");
                            int f = c * Integer.parseInt(pPrice);
                            String g = String.valueOf(f);
                            System.out.println("Working...");
                            CartDb cartdb = new CartDb(getApplicationContext());
                            Cursor cf = cartdb.getDb();
                            if (cf == null) {
                                cartdb.addItem(productid, pName, g, productquantity.getText().toString());
                                Toast.makeText(getApplicationContext(), "Item added", Toast.LENGTH_SHORT).show();
                            } else {
                                System.out.println("Product id is" + cf.getString(0) + "    " + productid);
                                if (productid.equals(cf.getString(0))) {
                                    cartq = String.valueOf(Integer.parseInt(cartq) + Integer.parseInt(cf.getString(3)));
                                    pPrice = String.valueOf(f + Integer.parseInt(cf.getString(2)));
                                    cartdb.UpdateItem(cartq, pPrice, productid);
                                    Toast.makeText(getApplicationContext(), productid + " item Updated...", Toast.LENGTH_SHORT).show();
                                    check = 1;

                                }
                                while (cf.moveToNext()) {
                                    if (productid.equals(cf.getString(0))) {
                                        System.out.println("Product id is" + cf.getString(0) + "    " + productid);

                                        cartq = String.valueOf(Integer.parseInt(cartq) + Integer.parseInt(cf.getString(3)));
                                        cartdb.UpdateItem(cartq, pPrice, productid);
                                        Toast.makeText(getApplicationContext(), productid + " item Updated...", Toast.LENGTH_SHORT).show();
                                        check = 1;
                                        //System.out.println("flag from while "+flag);
                                    }
                                }
                                if (check == 0) {
                                    cartdb.addItem(productid, pName, g, productquantity.getText().toString());
                                    Toast.makeText(getApplicationContext(), "Item added", Toast.LENGTH_SHORT).show();

                                }
                                System.out.println("flag is " + flag);
                            }
                            //Toast.makeText(getApplicationContext(), productname.getText() + "  Added to the Cart", Toast.LENGTH_SHORT).show();
                        } else
                            outofstock.setText("Out of Stock,Add lesser quantity");
                    } else
                        Toast.makeText(getApplicationContext(), "Enter some quantity", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }

    class mDateSetListener1 implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            // getCalender();
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;
            System.out.println(new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mDay).append("/").append(mMonth + 1).append("/")
                    .append(mYear).append(" "));
            fromdate.setText(new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mDay).append("/").append(mMonth + 1).append("/")
                    .append(mYear).append(" "));

            Calendar h=Calendar.getInstance();
            int day=h.get(Calendar.DAY_OF_MONTH);
            int month=h.get(Calendar.MONTH);
            int y=h.get(Calendar.YEAR);
            String s = fromdate.getText().toString();
            String[] dh = s.split("/");
            int z=Integer.parseInt(dh[2].trim());
            System.out.println(dh[0] + " " + dh[1] + " " + dh[2]);
            if(y==z)
            {
                if(month<=Integer.parseInt(dh[1]))
                {
                    if(day<=Integer.parseInt(dh[0])) {
                        fromdate.setText(new StringBuilder()
                                // Month is 0 based so add 1
                                .append(mDay).append("/").append(mMonth + 1).append("/")
                                .append(mYear).append(" "));
                        System.out.println("from inner if");
                        invalid.setText(" ");

                    }
                        else {
                        fromdate.setText("");
                        invalid.setText("*Select different start Date");
                    }
                }
                else {
                    fromdate.setText("");
                    invalid.setText("*Select different start Date");
                }
            }
            else if(y<z){
                System.out.println("from y<z");
                fromdate.setText(new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mDay).append("/").append(mMonth + 1).append("/")
                        .append(mYear).append(" "));
                invalid.setText(" ");

            }
            else{
                fromdate.setText("");
                invalid.setText("*Select different start Date");}


        }
    }

    class mDateSetListener2 implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            // getCalender();
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;
            System.out.println(new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mDay).append("/").append(mMonth + 1).append("/")
                    .append(mYear).append(" "));
            String s = fromdate.getText().toString();
            String[] dh = s.split("/");
            System.out.println(dh[0] + " " + dh[1] + " " + dh[2]);
            int z=Integer.parseInt(dh[2].trim());

            String h = tenddate.getText().toString().trim();
            String[]gh=new StringBuilder().append(mDay).append("/").append(mMonth + 1).append("/").append(mYear).append(" ").toString().split("/");



            if (z ==mYear) {
                if (Integer.parseInt(dh[1]) == (mMonth+1)  )
                {
                    if (mDay-Integer.parseInt(dh[0]) >= 15) {
                        tenddate.setText(new StringBuilder()
                                .append(mDay).append("/").append(mMonth + 1).append("/").append(mYear).append(" "));
                        invalid.setText("");
                    }
                        else
                    {   System.out.println("here...");
                       // tenddate.setError("Select new date");
                        invalid.setText("*Select different end date");

                    }
                }
                else if( (mMonth+1)-Integer.parseInt(dh[1])==1   ) {
                    System.out.println(dh[1]+"  "+map.get("1")+"    "+map.get(dh[1])+"      "+mDay);
                    int d1=Integer.parseInt(String.valueOf(map.get(dh[1])))-Integer.parseInt(dh[0]);
                    System.out.println("the difference is"+d1+" " + Integer.parseInt(dh[1]));
                    if (d1 + Integer.parseInt(gh[0]) >= 15) {
                        tenddate.setText(new StringBuilder()
                                .append(mDay).append("/").append(mMonth + 1).append("/").append(mYear).append(" "));
                        invalid.setText("");

                    }
                        else {
                        System.out.println("here...");
                        invalid.setText("*Select different end date");
                        //tenddate.setError("Select new date");
                    }
                }
                else if((mMonth+1)-Integer.parseInt(dh[1])>1){
                    tenddate.setText(new StringBuilder()
                            .append(mDay).append("/").append(mMonth + 1).append("/").append(mYear).append(" "));
                    invalid.setText("");
                }
                else
                    invalid.setText("*Select different end date");
            }

            //tenddate.setError("Select new date");
        }
    }
}