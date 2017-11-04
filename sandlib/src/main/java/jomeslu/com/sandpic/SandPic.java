/*
 *  Copyright 2017 jomeslu (luzhensheng72@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jomeslu.com.sandpic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by jomeslu on 17-3-28.
 */
public class SandPic {
    private static SandPic ourInstance = new SandPic();

    public static SandPic getInstance() {
        return ourInstance;
    }

    private SandPic() {
    }

    private static native int[] generate(int[] pixels, int width, int height, int threshold, int ponitNum);


    /**
     * 像素点转换
     * @param bitmap
     * @param threshold
     * @param ponitNum
     * @return
     */
    public Bitmap tramform(Bitmap bitmap,int threshold,int ponitNum ){
        int width =  bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap newImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newImage);
        Paint paint = new Paint();
        paint.setAntiAlias(false);
        paint.setStyle(Paint.Style.STROKE);
        int pixels[] = new int [width*height];
        bitmap.getPixels(pixels,0,width,0,0,width,height);
        int[] generate = generate(pixels, width, height, threshold, ponitNum);
        for (int i = 0, n = generate.length; i + 1 < n; i += 2) {
                int x = generate[i]>0? generate[i]:0;
                int y = generate[i+1] >0?generate[i+1]:0 ;
                int color = bitmap.getPixel(x,y);
                paint.setColor(color);
                canvas.drawCircle(x, y, 1, paint);
            }

        return newImage;
    }
    static {
        System.loadLibrary("sand-lib");
    }
}
