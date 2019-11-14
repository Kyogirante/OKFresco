package com.android.okfresco;

import java.lang.ref.WeakReference;

import android.annotation.SuppressLint;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.DefaultDrawableFactory;
import com.facebook.imagepipeline.drawable.DrawableFactory;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Wrapper for loading image into {@link ImageView}
 *
 * @author KyoWang
 * @since 2019/1/30
 */
public class OKImageViewRequestBuilderWrapper extends OKImageRequestBuilderWrapper<OKImageViewRequestBuilderWrapper> {

  private Object callerContext;

  private ImageRequest.RequestLevel requestLevel = ImageRequest.RequestLevel.FULL_FETCH;

  private RequestListener requestListener;

  @DrawableRes
  private int loadingPlaceHolderResId = 0;

  @DrawableRes
  private int errorPlaceHolderResId = 0;

  private WeakReference<ImageView> imageViewRef;

  public static OKImageViewRequestBuilderWrapper newRequest(Uri uri) {
    return new OKImageViewRequestBuilderWrapper(uri);
  }

  private OKImageViewRequestBuilderWrapper(Uri uri) {
    builder = ImageRequestBuilder.newBuilderWithSource(uri);
    wrapperRequest(builder);
  }

  public OKImageViewRequestBuilderWrapper CallerContext(Object callerContext) {
    this.callerContext = callerContext;
    return this;
  }

  public OKImageViewRequestBuilderWrapper requestLevel(ImageRequest.RequestLevel requestLevel) {
    this.requestLevel = requestLevel;
    return this;
  }

  public OKImageViewRequestBuilderWrapper requestListener(RequestListener requestListener) {
    this.requestListener = requestListener;
    return this;
  }

  public OKImageViewRequestBuilderWrapper placeholderResId(@DrawableRes int resId) {
    this.loadingPlaceHolderResId = resId;
    return this;
  }

  public OKImageViewRequestBuilderWrapper errorPlaceholderResId(@DrawableRes int resId) {
    this.errorPlaceHolderResId = resId;
    return this;
  }

  private OKDataSourceWrapper<CloseableReference<CloseableImage>> fetchDecodedImage(
      Object callerContext,
      ImageRequest.RequestLevel lowestPermittedRequestLevelOnSubmit,
      @Nullable RequestListener requestListener) {
    return OKDataSourceWrapper.newDataSourceWrapper(OKFresco.getImagePipeline().fetchDecodedImage(
        builder.build(), callerContext, lowestPermittedRequestLevelOnSubmit, requestListener));
  }

  @SuppressLint("ResourceType")
  public void into(@NonNull ImageView imageView) {
    if (imageView == null) {
      return;
    }
    if (loadingPlaceHolderResId > 0) {
      imageView.setImageResource(loadingPlaceHolderResId);
    }
    imageViewRef = new WeakReference<>(imageView);
    fetchDecodedImage(callerContext, requestLevel, requestListener)
        .subscribeMainThread(new BaseDataSubscriber<CloseableReference<CloseableImage>>() {
          @Override
          protected void onNewResultImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
            if (dataSource == null || dataSource.getResult() == null || dataSource.getResult().get() == null) {
              onFailureImpl(dataSource);
              return;
            }

            if (imageViewRef.get() == null) {
              return;
            }

            ImageView view = imageViewRef.get();

            CloseableImage closeableImage = dataSource.getResult().get();

            Drawable drawable = null;
            DrawableFactory animatedDrawableFactory = OKFresco.getImagePipelineFactory().getAnimatedDrawableFactory(view.getContext());
            DrawableFactory drawableFactory = new DefaultDrawableFactory(view.getResources(), animatedDrawableFactory);

            drawable = drawableFactory.createDrawable(closeableImage);

            if (drawable != null) {
              if (drawable instanceof Animatable) {
                ((Animatable) drawable).start();
              }
              view.setImageDrawable(drawable);
            } else {
              onFailureImpl(dataSource);
            }
          }

          @Override
          protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
            if (imageViewRef.get() == null) {
              return;
            }

            if (errorPlaceHolderResId > 0) {
              ImageView view = imageViewRef.get();
              view.setImageResource(errorPlaceHolderResId);
            }
          }
    });
  }
}
