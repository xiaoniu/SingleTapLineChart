package com.example.ast.singletapchart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

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

/**
 * Created by xiaoniu on 2017/7/12.
 */

public class SingleTapLineChart extends LineChart implements OnChartGestureListener, OnChartValueSelectedListener {

    private static final String TAG = "SingleTapLineChart";

    private OnSingleTapListener onSingleTapListener;

    private float yTouchPostion;
    private double xValuePos;
    private double yValuePos;
    private int iEntry;
    private float valEntry;

    private ArrayList<Entry> values;

    public SingleTapLineChart(Context context) {
        super(context);
        initSingleTapLineChart();
    }

    public SingleTapLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSingleTapLineChart();
    }

    public SingleTapLineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initSingleTapLineChart();
    }

    private void initSingleTapLineChart() {

        values = new ArrayList<>();

        this.getDescription().setEnabled(false);
        this.setTouchEnabled(false);
        this.setDragEnabled(false);
        this.setScaleEnabled(false);
        this.setPinchZoom(false);
        this.setDrawBorders(false);

        // 设置是否可以触摸
        this.setTouchEnabled(true);
        // 监听触摸事件
        this.setOnChartGestureListener(this);
        this.setOnChartValueSelectedListener(this);
        // 是否可以拖拽
        this.setDragEnabled(true);
        // 是否可以缩放
        this.setScaleEnabled(false);

    }

    public void setChartData(ArrayList<Entry> values) {
        this.values = values;
        XAxis xAxis = this.getXAxis();
        xAxis.setAxisMinimum(-1f);
        xAxis.setAxisMaximum(9f);
        xAxis.setGranularity(1f);
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawLabels(true);
        xAxis.setLabelCount(11);
        YAxis leftAxis = this.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.setAxisMaximum(80f);
        leftAxis.setAxisMinimum(10f);
        leftAxis.setGranularity(10f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawLabels(true);
        this.getAxisRight().setEnabled(false);

        Legend l = this.getLegend();
        l.setForm(Legend.LegendForm.LINE);

        LineDataSet set1 = new LineDataSet(values, "曲线");

        set1.enableDashedLine(10f, 5f, 0f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.RED);
        set1.setDrawCircles(true);
        set1.setCircleColor(Color.RED);
        set1.setLineWidth(1f);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(false);
        set1.setFormLineWidth(1f);
        set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        set1.setFormSize(15.f);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);
        //最后调用MPAndroidChart提供的setData方法
        this.setData(data);
    }


    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        this.highlightValues(null);
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i(TAG, "onChartGestureEnd, lastGesture: " + lastPerformedGesture);
        if (lastPerformedGesture == ChartTouchListener.ChartGesture.SINGLE_TAP) {
            Log.i(TAG, "SingleTapped");
            yTouchPostion = me.getY();
            changeTouchEntry();
        }
        this.highlightValues(null);
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        // 获取Entry
        iEntry = (int) e.getX();
        valEntry = e.getY();
        Log.i(TAG, "e.getX() = " + iEntry + "     e.getY() = " + valEntry);
        // 获取选中value的坐标
        MPPointD p = this.getPixelForValues(e.getX(), e.getY(), YAxis.AxisDependency.LEFT);
        xValuePos = p.x;
        yValuePos = p.y;
        Log.i(TAG, "xValuePos = " + xValuePos + "     yValuePos = " + yValuePos);
    }

    @Override
    public void onNothingSelected() {
    }

    public void changeTouchEntry() {
        // 获取X轴和Y轴的0坐标的pixel值
        MPPointD p = this.getPixelForValues(0, 0, YAxis.AxisDependency.LEFT);
        double yAixs0 = p.y;
        // 修改TouchEntry的y的值
        Log.i(TAG, "计算过程");
        Log.i(TAG, "yAixs0: " + yAixs0);
        double y1 = yValuePos - yAixs0;
        double y2 = yTouchPostion - yAixs0;
        Log.i(TAG, "原来的y值所在的坐标减0点");
        Log.i(TAG, "yValuePos - yAixs0: " + y1);
        Log.i(TAG, "点击的y值所在的坐标减0点");
        Log.i(TAG, "yTouchPostion - yAixs0: " + y2);
        valEntry = (float) (valEntry * (y2 / y1));
        Log.i(TAG, "value");
        Log.i(TAG, "X: " + iEntry + "     Y: " + valEntry);
        values.set(iEntry, new Entry(iEntry, valEntry));

        MPPointD tapped = this.getPixelForValues(iEntry, valEntry, YAxis.AxisDependency.LEFT);
        xValuePos = tapped.x;
        yValuePos = tapped.y;

        this.notifyDataSetChanged();
        this.invalidate();

        if (onSingleTapListener != null){
            onSingleTapListener.onSingleTap(iEntry,valEntry);
        }
    }

    public ArrayList<Entry> getValues(){
        return values;
    }

    public void setOnSingleTapListener(OnSingleTapListener onSingleTapListener){
        this.onSingleTapListener = onSingleTapListener;
    }

    public interface OnSingleTapListener{
        void onSingleTap(int x,float y);
    }
}
