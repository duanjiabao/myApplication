package com.panwrona.downloadprogressbar;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.panwrona.downloadprogressbar.library.DownloadProgressBar;

public class MainActivity extends AppCompatActivity
{

    private int val = 0;

    DownloadProgressBar downloadProgressView;

    private Handler handler = new Handler()
    {

        @Override
        public void handleMessage(Message msg)
        {

            super.handleMessage(msg);
            if (msg.what == 1)
            {
                val = val + 10;
                downloadProgressView.setProgress(val);
                handler.postDelayed(new Runnable()
                {

                    @Override
                    public void run()
                    {

                        handler.sendEmptyMessage(1);
                    }
                }, 1000);
            }


        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        downloadProgressView = (DownloadProgressBar) findViewById(R.id.dpv3);
        final TextView successTextView = (TextView) findViewById(R.id.success_text_view);
        successTextView.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {

                val = val + 10;
                downloadProgressView.setProgress(val);
                handler.sendEmptyMessage(1);
//                successTextView.setText(String.valueOf(val));
            }
        });
        Typeface robotoFont = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        successTextView.setTypeface(robotoFont);

        downloadProgressView.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                downloadProgressView.playManualProgressAnimation();
            }
        });
        downloadProgressView.setOnProgressUpdateListener(new DownloadProgressBar.OnProgressUpdateListener()
        {

            @Override
            public void onProgressUpdate(float currentPlayTime)
            {

//                successTextView.setText(String.valueOf(currentPlayTime));
            }

            @Override
            public void onAnimationStarted()
            {

                downloadProgressView.setEnabled(false);
            }

            @Override
            public void onAnimationEnded()
            {

                val = 0;
                successTextView.setText("Click to download");
                downloadProgressView.setEnabled(true);
            }

            @Override
            public void onAnimationSuccess()
            {

                successTextView.setText("Downloaded!");
            }

            @Override
            public void onAnimationError()
            {

                successTextView.setText("Aborted!");
            }

            @Override
            public void onManualProgressStarted()
            {

            }

            @Override
            public void onManualProgressEnded()
            {

            }
        });
    }
}
