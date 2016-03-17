package com.example.android.sinusoidalgraph;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

public class MainActivity extends AppCompatActivity {

    final Handler mHandler = new Handler();

    LineGraphSeries<DataPoint> line_series;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GraphView linegraph = (GraphView)findViewById(R.id.graph);
        line_series = new LineGraphSeries<DataPoint>(new DataPoint[] {

        });
        linegraph.addSeries(line_series);
        linegraph.getViewport().setScalable(true);
        linegraph.getViewport().setScrollable(true);

        line_series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(MainActivity.this, "Series: On Data Point clicked: " + dataPoint, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public double freq()
    {
        double f;
        EditText fe=(EditText)findViewById(R.id.freq);
        f=Double.parseDouble(fe.getText().toString());
        return f;
    }

    public double amp()
    {
        double a;
        EditText ae=(EditText)findViewById(R.id.amp);
        a=Double.parseDouble(ae.getText().toString());
        return a;
    }

    public double time()
    {
        double f;
        EditText fe=(EditText)findViewById(R.id.dur);
        f=Double.parseDouble(fe.getText().toString());
        return f;
    }

    public void gocall(View view)
    {
        startTestThread();
    }

    public void startTestThread() {
        Thread t = new Thread() {
            public void run() {
                GraphView linegraph = (GraphView) findViewById(R.id.graph);
                Log.d("Inchoo tutorial", "My thread is running");
                double i;
                Log.d("Inchoo tutorial", "My thread is running 2");
                double tim =time()*1000;

                if(tim>0){
                    Log.d("Inchoo tutorial", "My thread is running 3");
                    int t = (int) tim;
                    Log.d("Inchoo tutorial", "My thread is running 4");
                    Log.d("Inchoo tutorial", "My thread is running 5");

                    line_series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                    });
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // change UI elements here
                            GraphView linegraph = (GraphView) findViewById(R.id.graph);
                            linegraph.removeAllSeries();
                        }
                    });

                    for (i = 0; i <= time(); i += 0.01) {
                        double y = amp() * Math.sin(2 * Math.PI * freq() * i);
                        line_series.appendData(new DataPoint(i, y), true, t);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // change UI elements here
                            GraphView linegraph = (GraphView) findViewById(R.id.graph);
                            linegraph.addSeries(line_series);
                        }
                    });
                }

                else
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // change UI elements here
                            Toast.makeText(MainActivity.this, "Time cannot be 0 or empty ", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }
        };t.start();
    }

    final Runnable mUpdateResults = new Runnable() {
        public void run() {

        }
    };
    public void clear(View view)
    {

         GraphView linegraph =  (GraphView)findViewById(R.id.graph);
            linegraph.removeAllSeries();
            line_series = new LineGraphSeries<DataPoint>(new DataPoint[] {

            });


    }
}
