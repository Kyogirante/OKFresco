package com.android.okfresco;

import android.net.Uri;
import android.support.annotation.Nullable;

import com.facebook.imagepipeline.common.BytesRange;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

/**
 * Wrapper for {@link ImageRequestBuilder}
 *
 * @author KyoWang
 * @since 2018/12/27
 */
class OKImageRequestBuilderWrapper<T extends OKImageRequestBuilderWrapper> /* extends ImageRequestBuilder */ {

  protected ImageRequestBuilder builder;

  protected void wrapperRequest(ImageRequestBuilder builder) {
    builder.setProgressiveRenderingEnabled(false);
  }

  public T setSource(Uri uri) {
    builder.setSource(uri);
    return (T) this;
  }

  public T setLowestPermittedRequestLevel(
      ImageRequest.RequestLevel requestLevel) {
    builder.setLowestPermittedRequestLevel(requestLevel);
    return (T)this;
  }

  public T setAutoRotateEnabled(boolean enabled) {
    builder.setAutoRotateEnabled(enabled);
    return (T)this;
  }

  public T setResizeOptions(@Nullable ResizeOptions resizeOptions) {
    builder.setResizeOptions(resizeOptions);
    return (T)this;
  }

  public T setRotationOptions(@Nullable RotationOptions rotationOptions) {
    builder.setRotationOptions(rotationOptions);
    return (T)this;
  }

  public T setBytesRange(@Nullable BytesRange bytesRange) {
    builder.setBytesRange(bytesRange);
    return (T)this;
  }

  public T setImageDecodeOptions(ImageDecodeOptions imageDecodeOptions) {
    builder.setImageDecodeOptions(imageDecodeOptions);
    return (T)this;
  }

  public T setCacheChoice(ImageRequest.CacheChoice cacheChoice) {
    builder.setCacheChoice(cacheChoice);
    return (T)this;
  }

  public T setProgressiveRenderingEnabled(boolean enabled) {
    builder.setProgressiveRenderingEnabled(enabled);
    return (T)this;
  }

  public T setLocalThumbnailPreviewsEnabled(boolean enabled) {
    builder.setLocalThumbnailPreviewsEnabled(enabled);
    return (T)this;
  }

  public T disableDiskCache() {
    builder.disableDiskCache();
    return (T)this;
  }

  public T disableMemoryCache() {
    builder.disableMemoryCache();
    return (T)this;
  }

  public T setRequestPriority(Priority requestPriority) {
    builder.setRequestPriority(requestPriority);
    return (T)this;
  }

  public T setPostprocessor(Postprocessor postprocessor) {
    builder.setPostprocessor(postprocessor);
    return (T)this;
  }

  public T setRequestListener(RequestListener requestListener) {
    builder.setRequestListener(requestListener);
    return (T)this;
  }

}
