package com.example.ast.singletapchart;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointD;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";
    private SingleTapLineChart mChart;
    private ArrayList<Entry> values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        values = new ArrayList<>();

        mChart = (SingleTapLineChart) findViewById(R.id.test_lineChart);

        values.add(new Entry(0, 22));
        values.add(new Entry(1, 43));
        values.add(new Entry(2, 34));
        values.add(new Entry(3, 13));
        values.add(new Entry(4, 24));
        values.add(new Entry(5, 13));
        values.add(new Entry(6, 31));
        values.add(new Entry(7, 25));
        values.add(new Entry(8, 34));

        mChart.setChartData(values);

        mChart.setOnSingleTapListener(new SingleTapLineChart.OnSingleTapListener() {
            @Override
            public void onSingleTap(int x, float y) {
                Log.i(TAG,"x = "+x+" y = "+y);
            }
        });
    }
}
