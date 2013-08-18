package zombyes.android.AndroidZombyes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 07.07.13
 * Time: 18:38
 * To change this template use File | Settings | File Templates.
 */
public class MainMenuView extends SurfaceView
{
    /**Загружаемая картинка*/
    private Bitmap bmp;

    /**Наше поле рисования*/
    private SurfaceHolder holder;

    //конструктор
    public MainMenuView(Context context)
    {
        super(context);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback()
        {
            public void surfaceDestroyed(SurfaceHolder holder)
            {
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder)
            {
                Canvas canvas = holder.lockCanvas(null);
                onDraw(canvas);
                holder.unlockCanvasAndPost(canvas);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
            {
            }
        });
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
    }

    //Рисуем нашу картинку на черном фоне
    protected void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(bmp, 10, 10, null);
    }
}