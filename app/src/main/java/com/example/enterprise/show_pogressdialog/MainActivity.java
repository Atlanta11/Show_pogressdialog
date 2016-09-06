package com.example.enterprise.show_pogressdialog;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.os.Handler;
import android.app.ProgressDialog;


public class MainActivity extends Activity {
    private int progressStatus = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the widgets reference from XML layout
        final RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);
        final Button btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ProgressDialog(Context context)
                // Initialize a new instance of progress dialog
                final ProgressDialog pd = new ProgressDialog(MainActivity.this);

                /*
                    setTitle(CharSequence title)
                        Set the title text for this dialog's window.
                */
                // Set the title of progress dialog
                pd.setTitle("Title of Progress Dialog");


                // setMessage(CharSequence message)
                // Set the message of progress dialog
                pd.setMessage("Please wait until complete....");

                // setIndeterminate(boolean indeterminate)
                // Set the indeterminate mode
                pd.setIndeterminate(false);

                // setProgressStyle(int style)
                // Set the progress style STYLE_HORIZONTAL or STYLE_SPINNER
                pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

                /*
                    setCancelable(boolean flag)
                        Sets whether this dialog is cancelable with the BACK key.
                */
                // Set the progress dialog is cancelable or not
                pd.setCancelable(true);

                // setMax(int max)
                // Set the maximum progress
                pd.setMax(100);

                // Finally, show the progress dialog
                pd.show();

                // Set the progress status zero on each button click
                progressStatus = 0;

                // Start the lengthy operation in a background thread
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(progressStatus < 100){
                            // Update the progress status
                            progressStatus +=1;

                            // Try to sleep the thread for 20 milliseconds
                            try{
                                Thread.sleep(20);
                            }catch(InterruptedException e){
                                e.printStackTrace();
                            }

                            // Update the progress bar
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    // Update the progress status
                                    pd.setProgress(progressStatus);
                                    // If task execution completed
                                    if(progressStatus == 100){
                                        // Dismiss/hide the progress dialog
                                        pd.dismiss();
                                    }
                                }
                            });
                        }
                    }
                }).start(); // Start the operation
            }
        });
    }
}
