package com.example.dt1_normalmode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;

public class ConnectFourView extends View {
    private Context context;
    private Paint mPaint0;
    private Paint mPaint1;
    private Paint mPaint2;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private int mBackgroundcolor;
    private int mwidth;
    private int mheight;
    private int mode;
    private int difficulty;
    private float mx, my;

    private int[][] gameArray;
    private int[] heightArray;
    private int[] gameStoreArray;
    int count=0;
    int win;

    public ConnectFourView(Context context, int width, int height, int mode, int difficulty) {
        super(context);
        this.context= context;
        this.mwidth= width;
        this.mheight= height;
        this.mode= mode;
        this.difficulty= difficulty;

        mBackgroundcolor= ResourcesCompat.getColor(getResources(), R.color.Backgroundcolor, null);
        int mP0color= ResourcesCompat.getColor(getResources(),R.color.P0color, null);
        int mP1color= ResourcesCompat.getColor(getResources(), R.color.P1color, null);
        int mP2color= ResourcesCompat.getColor(getResources(), R.color.P2color, null);

        mPaint0= new Paint();
        mPaint0.setColor(mP0color);
        mPaint0.setAntiAlias(true);

        mPaint1= new Paint();
        mPaint1.setColor(mP1color);
        mPaint1.setAntiAlias(true);

        mPaint2= new Paint();
        mPaint2.setColor(mP2color);
        mPaint2.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldwidth, int oldheight) {
        super.onSizeChanged(width, height, oldwidth, oldheight);
        mBitmap= Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas= new Canvas(mBitmap);
        mCanvas.drawColor(mBackgroundcolor);
        mx= width/(float)(2*mwidth);
        my= height/(float)(2*mheight);

        gameArray= new int[mwidth][mheight];
        heightArray= new int[mwidth];
        gameStoreArray= new int[mwidth*mheight];

        for (int i= 1; i< 2*mwidth; i+=2)
            for (int j=1; j< 2*height; j+=2)
                mCanvas.drawCircle(i*mx, j*my, (3*my)/4.0f, mPaint0);
    }

    @Override
    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0,null);

    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {
        float x= event.getX();

        if (event.getAction()==MotionEvent.ACTION_DOWN) {
            int i= (int) ((int)x/mx);
            int indexy;
            int j;

            if (i== 2*mwidth)
                i--;

            if (i%2== 0 && i<2*mwidth)
                i++;

            int indexx= ((i+1)/2)-1;

            if (heightArray[indexx]< mheight) {
                indexy= heightArray[indexx];
                j= (2*((mheight-1) - heightArray[indexx]))+1;
            }

            else return true;

            if (win!=1) {
                if(count%2== 0) {
                    mCanvas.drawCircle(i*mx, j*my, (3*my)/4.0f, mPaint1);
                    gameArray[indexx][indexy]=1;
                    heightArray[indexx]++;
                    gameStoreArray[count]= indexx+1;
                    check(indexx, indexy,1);
                    invalidate();
                }

                else if (count%2== 1) {
                    mCanvas.drawCircle(i*mx, j*my, (3*my)/4.0f, mPaint2);
                    gameArray[indexx][indexy]=2;
                    heightArray[indexx]++;
                    gameStoreArray[count]= indexx+1;
                    check(indexx, indexy,2);
                    invalidate();
                }
            }
        }
        return true;
    }

    private void check(int indexx, int indexy, int player) {
        int counter=0, xldiff, xrdiff, yddiff, yudiff, ddiff0, ddiff1, udiff0, udiff1;

        if (indexx -3>= 1)
            xldiff= -3;
        else xldiff= -indexx+1;

        if (indexx+3< mwidth-1)
            xrdiff= 3;
        else xrdiff= mwidth-indexx-2;

        if (indexy -3>= 1)
            yddiff= -3;
        else yddiff= -indexy+1;

        if (indexy+3< mheight-1)
            yudiff= 3;
        else yudiff= mheight-indexy-2;

        ddiff0= Math.max(xldiff, yddiff);
        ddiff1= Math.min(xrdiff, yudiff);

        udiff0= Math.max(xldiff, -yudiff);
        udiff1= Math.min(xrdiff, -yddiff);

        for (int i=xldiff; i<= xrdiff; i++) {
            if (gameArray[indexx+i][indexy]== player && gameArray[indexx+i+1][indexy]== player && gameArray[indexx+i-1][indexy]==player)
                counter++;
            if (counter==2) {
                winToast(player);
                return;
            }
        }
        counter=0;

        for (int i=yddiff; i<= yudiff; i++) {
            if (gameArray[indexx][indexy+i]== player && gameArray[indexx][indexy+i+1]== player && gameArray[indexx][indexy+i-1]== player)
                counter++;
            if (counter==2) {
                winToast(player);
                return;
            }
        }
        counter=0;

        for (int i=ddiff0; i<= ddiff1; i++) {
            if (gameArray[indexx+i][indexy+i]== player && gameArray[indexx+i+1][indexy+i+1]== player && gameArray[indexx+i-1][indexy+i-1]==player)
                counter++;
            if (counter==2) {
                winToast(player);
                return;
            }
        }
        counter=0;

        for (int i=udiff0; i<= udiff1; i++) {
            if (gameArray[indexx+i][indexy-i]== player && gameArray[indexx+i+1][indexy-i-1]== player && gameArray[indexx+i-1][indexy-i+1]== player)
                counter++;
            if (counter==2) {
                winToast(player);
                return;
            }
        }
        count++;
    }

    private void winToast(int player)
    {
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, "Player "+player+"is the Winner", duration);
        toast.show();

        count++;
        win= 1;
    }

    public boolean reverse() {
        win= 0;
        if (count > 0) {
            int i= gameStoreArray[--count]-1;
            int j= heightArray[i]-1;
            gameStoreArray[count]=0;
            heightArray[i]--;
            gameArray[i][j]=0;
            mCanvas.drawCircle(((2*i)+1)*mx, (2*(mheight-j-1)+1)*my, (3*my)/4.0f, mPaint0);
            invalidate();
            return true;
        }
        return false;
    }
}
