package com.android.okfresco;

import android.net.Uri;
import android.support.annotation.Nullable;

import com.facebook.common.internal.ImmutableList;
import com.facebook.common.internal.Supplier;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.backends.pipeline.info.ImageOriginListener;
import com.facebook.drawee.backends.pipeline.info.ImagePerfDataListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.controller.ControllerViewportVisibilityListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.drawable.DrawableFactory;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;

/**
 * Wrapper for {@link PipelineDraweeControllerBuilder}
 *
 * @author KyoWang
 * @since 2018/12/27
 */
public class OKFrescoControllerBuilderWrapper /* extends PipelineDraweeControllerBuilder */ {

  private PipelineDraweeControllerBuilder builder;

  public static OKFrescoControllerBuilderWrapper newWrapper(ImageRequest imageRequest) {
    return new OKFrescoControllerBuilderWrapper(imageRequest);
  }

  public static OKFrescoControllerBuilderWrapper newWrapper(ImageRequest[] imageRequests) {
    return new OKFrescoControllerBuilderWrapper(imageRequests);
  }

  private OKFrescoControllerBuilderWrapper(ImageRequest imageRequest) {
    builder = Fresco.newDraweeControllerBuilder();
    builder.setImageRequest(imageRequest);
  }

  private OKFrescoControllerBuilderWrapper(ImageRequest[] imageRequests) {
    builder = Fresco.newDraweeControllerBuilder();
    builder.setFirstAvailableImageRequests(imageRequests, false);
  }

  public OKFrescoControllerBuilderWrapper setUri(@Nullable Uri uri) {
    builder.setUri(uri);
    return this;
  }

  public OKFrescoControllerBuilderWrapper setUri(@Nullable String uriString) {
    builder.setUri(uriString);
    return this;
  }

  public OKFrescoControllerBuilderWrapper setCustomDrawableFactories(
      @Nullable ImmutableList<DrawableFactory> customDrawableFactories) {
    builder.setCustomDrawableFactories(customDrawableFactories);
    return this;
  }

  public OKFrescoControllerBuilderWrapper setCustomDrawableFactories(
      DrawableFactory... drawableFactories) {
    builder.setCustomDrawableFactories(drawableFactories);
    return this;
  }

  public OKFrescoControllerBuilderWrapper setCustomDrawableFactory(DrawableFactory drawableFactory) {
    builder.setCustomDrawableFactory(drawableFactory);
    return this;
  }

  public OKFrescoControllerBuilderWrapper setImageOriginListener(
      @Nullable ImageOriginListener imageOriginListener) {
    builder.setImageOriginListener(imageOriginListener);
    return this;
  }

  public OKFrescoControllerBuilderWrapper setPerfDataListener(
      @Nullable ImagePerfDataListener imagePerfDataListener) {
    builder.setPerfDataListener(imagePerfDataListener);
    return this;
  }

  public OKFrescoControllerBuilderWrapper setCallerContext(Object callerContext) {
    builder.setCallerContext(callerContext);
    return this;
  }

  public OKFrescoControllerBuilderWrapper setLowResImageRequest(ImageRequest lowResImageRequest) {
    builder.setLowResImageRequest(lowResImageRequest);
    return this;
  }

  public OKFrescoControllerBuilderWrapper setFirstAvailableImageRequests(
      ImageRequest[] firstAvailableImageRequests) {
    builder.setFirstAvailableImageRequests(firstAvailableImageRequests);
    return this;
  }

  public OKFrescoControllerBuilderWrapper setFirstAvailableImageRequests(
      ImageRequest[] firstAvailableImageRequests, boolean tryCacheOnlyFirst) {
    builder.setFirstAvailableImageRequests(firstAvailableImageRequests, tryCacheOnlyFirst);
    return this;
  }

  public OKFrescoControllerBuilderWrapper setDataSourceSupplier(
      @Nullable Supplier<DataSource<CloseableReference<CloseableImage>>> dataSourceSupplier) {
    builder.setDataSourceSupplier(dataSourceSupplier);
    return this;
  }

  public OKFrescoControllerBuilderWrapper setTapToRetryEnabled(boolean enabled) {
    builder.setTapToRetryEnabled(enabled);
    return this;
  }

  public OKFrescoControllerBuilderWrapper setRetainImageOnFailure(boolean enabled) {
    builder.setRetainImageOnFailure(enabled);
    return this;
  }

  public OKFrescoControllerBuilderWrapper setAutoPlayAnimations(boolean enabled) {
    builder.setAutoPlayAnimations(enabled);
    return this;
  }

  public OKFrescoControllerBuilderWrapper setControllerListener(
      ControllerListener<? super ImageInfo> controllerListener) {
    builder.setControllerListener(controllerListener);
    return this;
  }

  public OKFrescoControllerBuilderWrapper setControllerViewportVisibilityListener(
      @Nullable ControllerViewportVisibilityListener controllerViewportVisibilityListener) {
    builder.setControllerViewportVisibilityListener(controllerViewportVisibilityListener);
    return this;
  }

  public OKFrescoControllerBuilderWrapper setContentDescription(String contentDescription) {
    builder.setContentDescription(contentDescription);
    return this;
  }

  public OKFrescoControllerBuilderWrapper setOldController(@Nullable DraweeController oldController) {
    builder.setOldController(oldController);
    return this;
  }

  public void into(SimpleDraweeView view) {
    if (view == null) {
      return;
    }
    builder.setOldController(view.getController());
    view.setController(builder.build());
  }

  public void into(DraweeView view) {
    if (view == null) {
      return;
    }
    builder.setOldController(view.getController());
    view.setController(builder.build());
  }
}
