package com.example.rajesh.organicfoods;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class AdminActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();/*
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Addproducts) {
            Intent i = new Intent(AdminActivity.this, Addproduct.class);
            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.View_Orders) {
            startActivity(new Intent(AdminActivity.this, ViewOrders.class));

        } else if (id == R.id.excel) {
            ExcelStore test = new ExcelStore();
            test.setOutputFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test.xls");
            try {
                test.write();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }
            System.out
                    .println("Please check the result file under c:/temp/lars.xls ");
            //startActivity(new Intent(AdminActivity.this,ExcelStore.class));

        } else if (id == R.id.View_delivered) {
            startActivity(new Intent(AdminActivity.this,DeliveredItems.class));

        } else if (id == R.id.Add_dealer) {
            startActivity( new Intent(AdminActivity.this,AddDealer.class));

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class ExcelStore {
        private WritableCellFormat timesBoldUnderline;
        private WritableCellFormat times;
        private String inputFile;

        public void setOutputFile(String inputFile) {
            this.inputFile = inputFile;
        }

        public void write() throws IOException, WriteException {
            File file = new File(inputFile);
            WorkbookSettings wbSettings = new WorkbookSettings();

            wbSettings.setLocale(new Locale("en", "EN"));

            WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
            workbook.createSheet("Transaction Details", 0);
            WritableSheet excelSheet = workbook.getSheet(0);
            createLabel(excelSheet);
            createContent(excelSheet);

            workbook.write();
            workbook.close();
        }

        private void createLabel(WritableSheet sheet)
                throws WriteException {
            // Lets create a times font
            WritableFont times10pt = new WritableFont(WritableFont.TIMES, 5);
            // Define the cell format
            times = new WritableCellFormat(times10pt);
            // Lets automatically wrap the cells
            times.setWrap(true);

            // create create a bold font with unterlines
            WritableFont times10ptBoldUnderline = new WritableFont(
                    WritableFont.TIMES, 5, WritableFont.BOLD, false,
                    UnderlineStyle.NO_UNDERLINE);
            timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
            // Lets automatically wrap the cells
            timesBoldUnderline.setWrap(true);

            CellView cv = new CellView();
            cv.setFormat(times);
            cv.setFormat(timesBoldUnderline);
            cv.setAutosize(true);

            // Write a few headers

            addCaption(sheet, 0, 0, "Customer Name");
            addCaption(sheet, 1, 0, "Mail id");
            addCaption(sheet, 2, 0, "Mobile Number");
            addCaption(sheet,3,0,"Address");
            addCaption(sheet,4,0,"Product Name");
            addCaption(sheet,5,0,"Product ID");
            addCaption(sheet,6,0,"Quantity Delivered");
            addCaption(sheet,7,0,"Price");

            addLabel(sheet,0,1,"Sai");
            addLabel(sheet,1,1,"vsai97@gmail.com");
            addLabel(sheet,2,1,"9677102277");
            addLabel(sheet,3,1,"No-27,Chrompet,Chennai");
            addLabel(sheet,4,1,"Milk");
            addLabel(sheet,5,1,"101");
            addLabel(sheet,6,1,"100");
            addLabel(sheet,7,1,"2100");



        }

        private void createContent(WritableSheet sheet) throws WriteException,
                RowsExceededException {
            // Write a few number
          /*  for (int i = 1; i < 10; i++) {
                // First column
                addNumber(sheet, 0, i, i + 10);
                // Second column
                addNumber(sheet, 1, i, i * i);
            }
            // Lets calculate the sum of it
            StringBuffer buf = new StringBuffer();
            buf.append("SUM(A2:A10)");
            Formula f = new Formula(0, 10, buf.toString());
            sheet.addCell(f);
            buf = new StringBuffer();
            buf.append("SUM(B2:B10)");
            f = new Formula(1, 10, buf.toString());
            sheet.addCell(f);

            // now a bit of text
            for (int i = 12; i < 20; i++) {
                // First column
                addLabel(sheet, 0, i, "Boring text " + i);
                // Second column
                addLabel(sheet, 1, i, "Another text");
            }*/
        }

        private void addCaption(WritableSheet sheet, int column, int row, String s)
                throws RowsExceededException, WriteException {
            Label label;
            label = new Label(column, row, s, timesBoldUnderline);
            sheet.addCell(label);
        }

        private void addNumber(WritableSheet sheet, int column, int row,
                               Integer integer) throws WriteException, RowsExceededException {
            Number number;
            number = new Number(column, row, integer, times);
            sheet.addCell(number);
        }

        private void addLabel(WritableSheet sheet, int column, int row, String s)
                throws WriteException, RowsExceededException {
            Label label;
            label = new Label(column, row, s, times);
            sheet.addCell(label);
        }

    }
}
