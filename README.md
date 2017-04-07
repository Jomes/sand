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
get new Bitmap from SandPic.class .
```
SandPic.getInstance().tramform(originalBitmap,thre,pointNum);
```
