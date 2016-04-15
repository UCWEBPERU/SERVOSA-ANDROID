package pe.servosa.android.util.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.List;

import pe.servosa.android.R;

/**
 * Created by ucweb02 on 08/04/2016.
 */
public class PyramidChart extends View {

    List<ChartData> data;

    private final int colors[] = {
            Color.rgb(175, 32, 32),
            Color.rgb(255, 110, 4),
            Color.rgb(255, 203, 4),
            Color.rgb(122, 180, 30),
            Color.rgb(24, 57, 114)
    };

    public PyramidChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int startTop = 50;
        int startLeft = 50;
        int endBottom = getHeight() - 50;
        int endRight = getWidth() - 50;
        int widthLabel, heightLabel;
        Log.e("WIDTH", getWidth() + "");
        Log.e("HEIGHT", getHeight() + "");
        RectF rectf = new RectF(startLeft, startTop, endRight, endBottom);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);

        Paint paintLabel = new Paint();
        paintLabel.setColor(Color.WHITE);
        paintLabel.setTextSize(getResources().getDimensionPixelSize(R.dimen.fontSizePyramid));
        Rect boundsLabel = new Rect();

        if (this.data != null) {
            for (int i = 0; i < this.data.size(); i++) {
                paint.setColor(colors[i]);
                int total = getTotal();
                Log.e("segment total", ""+total);
                Log.e("segment", "" + this.data.get(i).getPyramid_value());
                float segment = ((this.data.get(i).getPyramid_value()*100)/total);

                Log.e("segment", "" + segment);

                int bottom = startTop + (int)((endBottom*(int)segment)/100);
                Log.e("start and bottom", startTop + " " + bottom);

                Rect rect = new Rect(startLeft, startTop, endRight, bottom);

                canvas.drawRect(rect, paint);

                paintLabel.getTextBounds(this.data.get(i).getPyramidLabel(), 0, this.data.get(i).getPyramidLabel().length(), boundsLabel);
                widthLabel = boundsLabel.width();
                heightLabel = boundsLabel.height();
                Log.e("width text", "width: " + widthLabel + " height: " + heightLabel);

                canvas.drawText(this.data.get(i).getPyramidLabel(), (endRight / 2) - (widthLabel / 2), (startTop + ((bottom - startTop) / 2)) + heightLabel / 2, paintLabel);

                startTop = bottom;
            }
        }

        Path path = new Path();
        path.reset();

        path.moveTo(50, 50);
        path.lineTo(endRight / 2, 50);
        path.lineTo(50, endBottom + 50);
        path.moveTo(endRight / 2, 50);
        path.lineTo(endRight, endBottom + 50);
        path.lineTo(endRight, 50);
        path.close();

        paint.setColor(Color.rgb(242, 242, 242));
        // Clipping canvas to path and drawing small area
        canvas.save();
        canvas.clipPath(path);
        canvas.drawPath(path, paint);

        canvas.restore();
    }

    private int getTotal() {
        Log.e("data string", this.data.size()+"");
        int total = 0;
        for(int i = 0; i < this.data.size(); i++){

            total += this.data.get(i).getPyramid_value();
        }
        Log.e("data string", total + "");
        return total;
    }

    public void setData(List<ChartData> data) {
        this.data = data;
        invalidate();
    }

}