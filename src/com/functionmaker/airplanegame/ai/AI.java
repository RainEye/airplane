package com.functionmaker.airplanegame.ai;

import java.util.Iterator;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;

import com.functionmaker.airplanegame.objects.Airplane;
import com.functionmaker.airplanegame.objects.Bullet;
import com.functionmaker.airplanegame.objects.Enemy;
import com.functionmaker.airplanegame.util.WindowSize;

public class AI
{
  public static int score = 0;
  
  public static void destroyDeal(Airplane paramAirplane, List<Enemy> paramList, Canvas paramCanvas, Paint paramPaint, Bitmap paramBitmap)
  {
  }
  
  public static void isGameOver(List<Enemy> paramList, Airplane paramAirplane, WindowSize paramWindowSize, Handler paramHandler)
  {
	  
  }
  
  public static boolean isHit(Bullet paramBullet, Enemy paramEnemy)
  {
    int i = paramBullet.getX();
    int j = paramBullet.getY();
    int k = paramEnemy.getX();
    int m = paramEnemy.getY();
    int n = paramEnemy.getWidth();
    int i1 = paramEnemy.getHeight();
    return (i > k) && (i < k + n) && (j <= m + i1);
  }
}
