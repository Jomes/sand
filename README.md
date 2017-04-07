# sand
Sand is Android library that it  is using JNI to achieve Sobel operator image edge detection. it's easy to build a picture  like sand .
* [中文文档](http://www.jianshu.com/p/d5a551b058c8)
## Screenshot
![sand](./gif/sand.gif)
## Build

Step 1. Add the JitPack repository to your build file

add the JitPack maven to your project in root  build.gradle

```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

```
Step 2. Add module dependency build.gradle

```
 dependencies {
     	  compile 'com.github.Jomes:sand:v0.01'

 } 

```
That's it! 

## How to use
you can get Bitmap from SandPic class,you need to provide original Bitmap、threshold、pointNum。
```
Bitmap bitmap = SandPic.getInstance().tramform(originalBitmap,thre,pointNum);

```
there is a important tramform method in the SandPic class. it get pixels array from JNI.
```
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
```
## License
```
Copyright 2017 jomeslu luzhensheng72@gmail.com

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
