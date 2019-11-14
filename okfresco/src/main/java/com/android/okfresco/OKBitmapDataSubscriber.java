package com.android.okfresco;


import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.facebook.animated.gif.GifImage;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.imagepipeline.animated.base.AnimatedImage;
import com.facebook.imagepipeline.animated.base.AnimatedImageFrame;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableAnimatedImage;
import com.facebook.imagepipeline.image.CloseableBitmap;
import com.facebook.imagepipeline.image.CloseableImage;

/**
 * Get bitmap from net picture url, if picture is gif, return first frame
 *
 * @author KyoWang
 * @since 2018/12/27
 */
public class OKBitmapDataSubscriber extends BaseBitmapDataSubscriber {

  @Override
  public void onNewResult(DataSource<CloseableReference<CloseableImage>> dataSource) {
    if (!dataSource.isFinished()) {
      return;
    }

    CloseableReference<CloseableImage> closeableImageRef = dataSource.getResult();
    Bitmap bitmap = null;
    try {
      if (closeableImageRef != null &&
          closeableImageRef.get() instanceof CloseableBitmap) {
        bitmap = ((CloseableBitmap) closeableImageRef.get()).getUnderlyingBitmap();
      } else if (closeableImageRef != null &&
          closeableImageRef.get() instanceof CloseableAnimatedImage) {
        AnimatedImage image = ((CloseableAnimatedImage) closeableImageRef.get()).getImage();
        if (image instanceof GifImage) {
          AnimatedImageFrame frame = image.getFrame(0);
          bitmap =
              Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.ARGB_8888);
          frame.renderFrame(image.getWidth(), image.getHeight(), bitmap);
        }
      }
      onNewResultImpl(bitmap);
    } catch (Exception e) {
      onFailureImpl(dataSource);
    } finally {
      CloseableReference.closeSafely(closeableImageRef);
    }
  }

  @Override
  protected void onNewResultImpl(@Nullable Bitmap bitmap) {

  }

  @Override
  protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {

  }
}
