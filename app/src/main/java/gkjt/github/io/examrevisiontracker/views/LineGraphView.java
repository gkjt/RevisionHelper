package gkjt.github.io.examrevisiontracker.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by User on 24/11/2015.
 */
public class LineGraphView extends View {
    final float tickSize = 3;
    double minX, maxX, minY, maxY;
    Point[] points;
    int x, y;

    //startX, startY, stopX, stopY
    float[] xAxis = new float[4], yAxis = new float[4];


    public LineGraphView(Context context){
        super(context);
    }

    public LineGraphView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);

    }

    public LineGraphView(Context context, double minX, double maxX, double minY, double maxY, Point[] points){
        super(context);
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.points = points;
    }

    @Override
    public void onDraw(Canvas canvas){
        drawAxes(canvas);
    }

    private void drawAxes(Canvas canvas){
        canvas.drawLines(xAxis, new Paint(Paint.ANTI_ALIAS_FLAG));
        canvas.drawLines(yAxis, new Paint(Paint.ANTI_ALIAS_FLAG));
        canvas.drawText(((Double) maxY).toString(), 0, 0, new Paint());
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh){
        x = w;
        y = h;

        Rect bounds = new Rect();

        TextPaint txtPnt = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        String text = ((Double) maxY).toString();
        txtPnt.getTextBounds(text, 0, text.length(), bounds);

        yAxis[0] = getPaddingLeft() + bounds.width()*1.2f;
        yAxis[1] = y - getPaddingBottom();
        yAxis[2] = yAxis[0];
        yAxis[3] = getPaddingTop();

        xAxis[0] = getPaddingLeft();
        xAxis[1] = y - (getPaddingBottom() + bounds.height()*1.2f);
        xAxis[2] = x - getPaddingRight();
        xAxis[3] = xAxis[1];
    }
}
