# ShapeOfView

[![CircleCI](https://circleci.com/gh/florent37/ShapeOfView/tree/master.svg?style=svg)](https://circleci.com/gh/florent37/ShapeOfView/tree/master)

Give a custom shape to any android view
Useful for Material Design 2

[![screen](https://raw.githubusercontent.com/florent37/ShapeOfView/master/medias/main_small.png)](https://www.github.com/florent37/ShapeOfView)

### Example of screens you can build with ShapeOfView :

[![screen](https://raw.githubusercontent.com/florent37/ShapeOfView/master/medias/sample_diagonal.gif)](https://www.github.com/florent37/ShapeOfView)
[![screen](https://raw.githubusercontent.com/florent37/ShapeOfView/master/medias/sample_arc.gif)](https://www.github.com/florent37/ShapeOfView)
[![screen](https://raw.githubusercontent.com/florent37/ShapeOfView/master/medias/shrine.gif)](https://www.github.com/florent37/ShapeOfView)

<a href="https://goo.gl/WXW8Dc">
  <img alt="Android app on Google Play" src="https://developer.android.com/images/brand/en_app_rgb_wo_45.png" />
</a>

# Download

<a href='https://ko-fi.com/A160LCC' target='_blank'><img height='36' style='border:0px;height:36px;' src='https://az743702.vo.msecnd.net/cdn/kofi1.png?v=0' border='0' alt='Buy Me a Coffee at ko-fi.com' /></a>

[ ![Download](https://api.bintray.com/packages/florent37/maven/shapeofview/images/download.svg) ](https://bintray.com/florent37/maven/shapeofview/)
```java
dependencies {
    implementation 'com.github.florent37:shapeofview:(lastest version)'
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

        app:shape_clip_drawable="@drawable/YOUR_DRAWABLE"
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

         app:shape_triangle_percentBottom="0.5"
         app:shape_triangle_percentLeft="0"
         app:shape_triangle_percentRight="0">

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
        app:shape_circle_borderColor="@android:color/black"
        app:shape_circle_borderWidth="2dp">

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
        app:shape_roundRect_bottomLeftRadius="10dp"
        app:shape_roundRect_bottomRightRadius="10dp"
        app:shape_roundRect_topLeftRadius="10dp"
        app:shape_roundRect_topRightRadius="10dp"
        
        app:shape_roundRect_borderColor="@android:color/black"
        app:shape_roundRect_borderWidth="2dp"
        >


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
        app:shape_cutCorner_bottomRightSize="20dp">

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
        app:shape_arc_cropDirection="outside"
        app:shape_arc_height="20dp"
        app:shape_arc_position="bottom"
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
        app:shape_diagonal_angle="10"
        app:shape_diagonal_direction="left"
        app:shape_diagonal_position="bottom">

         <!-- YOUR CONTENT -->

</com.github.florent37.shapeofview.shapes.DiagonalView>
```

## Bubble

[![screen](https://raw.githubusercontent.com/florent37/ShapeOfView/master/medias/bubble.png)](https://www.github.com/florent37/ShapeOfView)

```xml
<com.github.florent37.shapeofview.shapes.BubbleView
        android:layout_width="150dp"
        android:layout_height="150dp"
        app:shape_bubble_arrowHeight="10dp"
        app:shape_bubble_arrowWidth="10dp"
        app:shape_bubble_arrowPosition="bottom"
        app:shape_bubble_borderRadius="20dp"
        >

         <!-- YOUR CONTENT -->

</com.github.florent37.shapeofview.shapes.BubbleView>
```

## Star

[![screen](https://raw.githubusercontent.com/florent37/ShapeOfView/master/medias/star_5_pointed.png)](https://www.github.com/florent37/ShapeOfView)
[![screen](https://raw.githubusercontent.com/florent37/ShapeOfView/master/medias/star_11_pointed.png)](https://www.github.com/florent37/ShapeOfView)

```xml
<com.github.florent37.shapeofview.shapes.StarView
        android:layout_width="150dp"
        android:layout_height="150dp"
        app:shape_star_noOfPoints="5">

         <!-- YOUR CONTENT -->

</com.github.florent37.shapeofview.shapes.StarView>
```

## Polygon

[![screen](https://raw.githubusercontent.com/florent37/ShapeOfView/master/medias/polygon.png)](https://www.github.com/florent37/ShapeOfView)

```xml
 <com.github.florent37.shapeofview.shapes.PolygonView
            android:layout_width="150dp"
            android:layout_height="100dp"
            app:shape_polygon_noOfSides="9"
            >
         <!-- YOUR CONTENT -->

</com.github.florent37.shapeofview.shapes.PolygonView>
```

## Animation

All shapes methods can be animated

For example, you can animate a RoundRect corner :

[![anim](https://raw.githubusercontent.com/florent37/ShapeOfView/master/medias/shapeofview_anim.gif)](https://www.github.com/florent37/ShapeOfView)

```kotlin
ValueAnimator.ofFloat(0f, 200f, 0f).apply {
     addUpdateListener { animation -> roundRect.bottomLeftRadius = (animation.animatedValue as Float).toInt() }
     duration = 800
     repeatCount = ValueAnimator.INFINITE
     repeatMode = ValueAnimator.REVERSE
}.start()
```


# Contribute

Feel free to fork this project, and add customs shapes

Then make a `merge-request` after updated the README with a sample of your shape, including a preview

# TODO

# HISTORY

**1.0.9** Added requiresShapeUpdate(), allowing animations to work, look at AnimationActivity

**1.0.8** Used arcTo instead of quads in RoundRect, added border to RoundRect

**1.0.7** Prefixed all attributes by `shape_`

**1.0.6** Updated roundrect implementation

**1.0.5** Enable hardware acceleration after clip view

**1.0.4** Added PolygonView

**1.0.2** Added StarView

**1.0.1** Added BubbleView

# Credits

Ed Sheeran, for the name of this project and his awesome songs <3

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
