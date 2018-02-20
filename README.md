# ShapeOfView

Give a custom shape to any android view

[![screen](https://raw.githubusercontent.com/florent37/ShapeOfView/master/medias/main_small.png)](https://www.github.com/florent37/ShapeOfView)

<a href="https://goo.gl/WXW8Dc">
  <img alt="Android app on Google Play" src="https://developer.android.com/images/brand/en_app_rgb_wo_45.png" />
</a>

# Download

<a href='https://ko-fi.com/A160LCC' target='_blank'><img height='36' style='border:0px;height:36px;' src='https://az743702.vo.msecnd.net/cdn/kofi1.png?v=0' border='0' alt='Buy Me a Coffee at ko-fi.com' /></a>

[ ![Download](https://api.bintray.com/packages/florent37/maven/shapeofview/images/download.svg) ](https://bintray.com/florent37/maven/shapeofview/_latestVersion)
```java
dependencies {
    compile 'com.github.florent37:shapeofview:1.0.0'
}
```

# Sample

What you can do with Shape Of View :

[![screen](https://raw.githubusercontent.com/florent37/ShapeOfView/master/medias/tab.gif)](https://www.github.com/florent37/ShapeOfView)

# Create you own shape

You can use custom shape to cut your view

# Using Drawable (no elevation)

[![screen](https://raw.githubusercontent.com/florent37/ShapeOfView/master/medias/custom.png)](https://www.github.com/florent37/ShapeOfView)

```xml
<com.github.florent37.shapeofview.ShapeOfView
        android:layout_width="100dp"
        android:layout_height="100dp"

        app:clip_drawable="@drawable/YOUR_DRAWABLE"
        >

    <!-- YOUR CONTENT -->

 </com.github.florent37.shapeofview.ShapeOfView>
```

# Using Path (with elevation)

This method generates also a **shadow path** (with Lollipop elevation API 21+)
Wrap your view with a `ShapeOfView`

```xml
<com.github.florent37.shapeofview.ShapeOfView
        android:id="@+id/myShape"
        android:layout_width="30dp"
        android:layout_height="15dp"
        android:elevation="6dp">

        <!-- YOUR CONTENT -->

 </com.github.florent37.shapeofview.ShapeOfView>
```

Then generate a path in your code :

```java
ShapeOfView shapeOfView = findViewById(R.id.myShape)
shapeOfView.setClipPathCreator(new ClipPathManager.ClipPathCreator() {
       @Override
       public Path createClipPath(int width, int height) {
           final Path path = new Path();

            //eg: triangle
           path.moveTo(0, 0);
           path.lineTo(0.5 * width, height);
           path.lineTo(width, 0);
           path.close();

           return path;
       }
});
```

# Use implemented shapes

ShapesOfView came with pre-created shapes :

## Triangle

[![screen](https://raw.githubusercontent.com/florent37/ShapeOfView/master/medias/triangle.png)](https://www.github.com/florent37/ShapeOfView)

```xml
<com.github.florent37.shapeofview.shapes.TriangleView
         android:layout_width="150dp"
         android:layout_height="150dp"
         android:elevation="4dp"

         app:triangle_percentBottom="0.5"
         app:triangle_percentLeft="0"
         app:triangle_percentRight="0">

            <!-- YOUR CONTENT -->

</com.github.florent37.shapeofview.shapes.TriangleView>
```

## Circle

[![screen](https://raw.githubusercontent.com/florent37/ShapeOfView/master/medias/circle.png)](https://www.github.com/florent37/ShapeOfView)

```xml
<com.github.florent37.shapeofview.shapes.CircleView
        android:layout_width="150dp"
        android:layout_height="150dp"

        android:elevation="4dp"
        app:circle_borderColor="@android:color/black"
        app:circle_borderWidth="2dp">

            <!-- YOUR CONTENT -->

</com.github.florent37.shapeofview.shapes.CircleView>
```

## RoundRect

[![screen](https://raw.githubusercontent.com/florent37/ShapeOfView/master/medias/roundrect.png)](https://www.github.com/florent37/ShapeOfView)

```xml
<com.github.florent37.shapeofview.shapes.RoundRectView
        android:layout_width="150dp"
        android:layout_height="100dp"
        android:elevation="4dp"
        app:roundRect_bottomLeftDiameter="10dp"
        app:roundRect_bottomRightDiameter="10dp"
        app:roundRect_topLeftDiameter="10dp"
        app:roundRect_topRightDiameter="10dp">


            <!-- YOUR CONTENT -->

</com.github.florent37.shapeofview.shapes.RoundRectView>
```

## ClipCorner

[![screen](https://raw.githubusercontent.com/florent37/ShapeOfView/master/medias/cut_corner.png)](https://www.github.com/florent37/ShapeOfView)

```xml
<com.github.florent37.shapeofview.shapes.CutCornerView
        android:id="@+id/clipCorner"
        android:layout_width="150dp"
        android:layout_height="100dp"
        android:elevation="4dp"
        app:cutCorner_bottomRightSize="20dp">

         <!-- YOUR CONTENT -->

</com.github.florent37.shapeofview.shapes.CutCornerView>
```

## Arc

[![screen](https://raw.githubusercontent.com/florent37/ShapeOfView/master/medias/arc.png)](https://www.github.com/florent37/ShapeOfView)

```xml
<com.github.florent37.shapeofview.shapes.ArcView
        android:layout_width="150dp"
        android:layout_height="100dp"
        android:elevation="4dp"
        app:arc_cropDirection="outside"
        app:arc_height="20dp"
        app:arc_position="bottom"
        >

         <!-- YOUR CONTENT -->

</com.github.florent37.shapeofview.shapes.ArcView>
```


## Diagonal

[![screen](https://raw.githubusercontent.com/florent37/ShapeOfView/master/medias/diagonal.png)](https://www.github.com/florent37/ShapeOfView)

```xml
<com.github.florent37.shapeofview.shapes.DiagonalView
        android:layout_width="150dp"
        android:layout_height="100dp"
        android:elevation="4dp"
        app:diagonal_angle="10"
        app:diagonal_direction="left"
        app:diagonal_position="bottom">

         <!-- YOUR CONTENT -->

</com.github.florent37.shapeofview.shapes.DiagonalView>
```

# Credits

Author: Florent Champigny [http://www.florentchampigny.com/](http://www.florentchampigny.com/)

Blog : [http://www.tutos-android-france.com/](http://www.tutos-android-france.com/)


<a href="https://goo.gl/WXW8Dc">
  <img alt="Android app on Google Play" src="https://developer.android.com/images/brand/en_app_rgb_wo_45.png" />
</a>

<a href="https://plus.google.com/+florentchampigny">
  <img alt="Follow me on Google+"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/gplus.png" />
</a>
<a href="https://twitter.com/florent_champ">
  <img alt="Follow me on Twitter"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/twitter.png" />
</a>
<a href="https://www.linkedin.com/in/florentchampigny">
  <img alt="Follow me on LinkedIn"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/linkedin.png" />
</a>


License
--------

    Copyright 2017 Florent37, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
