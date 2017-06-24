package com.example.rajesh.organicfoods;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FeedBackActivity extends AppCompatActivity {
    Button feedback;
    EditText feed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        feed=(EditText)findViewById(R.id.feedback);
        feedback=(Button)findViewById(R.id.feedback_button);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            String username="organicdairyproduct@gmail.com";
                            GMailSender sender = new GMailSender("organicdairyproduct@gmail.com","organic123");
                            System.out.println("Started");
                            //sender.addAttachment(resume.getText().toString());
                            sender.sendMail("You have recieved  an feedback mail",feed.getText().toString(),
                                    "organicdairyproduct@gmail.com","rajeshkumaran1996@gmail.com");


                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                        }

                    }

                }).start();

            }
        });
    }

}
