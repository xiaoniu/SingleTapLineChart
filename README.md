# 基于MPAndroidChart自定义的LineChart，添加了单击图表刷新数据的功能

效果图（每次单击图表，对应的数据都会改变，图表也会重新绘制）：

![](http://upload-images.jianshu.io/upload_images/1849253-b9654186c42eff02.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

github里gif怎么不会动，不嫌麻烦的话，文章最下方移步博客看可以动的。
> 注意：基于MPAndroidChart开发，使用前项目要导入[MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)

# 使用方法
## 1.导入SingleTapLineChart.java文件
## 2.在布局文件添加控件
```xml
    <com.example.ast.singletapchart.SingleTapLineChart
        android:id="@+id/test_lineChart"
        android:layout_width="match_parent"
        android:layout_height="360dp"
        android:padding="5dp" />
```
## 3.初始化图表，添加数据
```java
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
```
## 4.添加监听器
```java
mChart.setOnSingleTapListener(new SingleTapLineChart.OnSingleTapListener() {
            @Override
            public void onSingleTap(int x, float y) {
                Log.i(TAG,"x = "+x+" y = "+y);
            }
        });
```
# 更详细的介绍博客
[基于MPAndroidChart的自定义LineChart（二）----添加单击事件的处理](http://www.jianshu.com/p/8bce70785b2f)