package com.android.okfresco;

import android.net.Uri;
import android.support.annotation.Nullable;

import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Wrapper for downloading pictures
 *
 * @author KyoWang
 * @since 2018/12/27
 */
public class OKDownloadImageRequestBuilderWrapper extends OKImageRequestBuilderWrapper<OKDownloadImageRequestBuilderWrapper> {

  public static OKDownloadImageRequestBuilderWrapper newRequest(Uri uri) {
    return new OKDownloadImageRequestBuilderWrapper(uri);
  }

  private OKDownloadImageRequestBuilderWrapper(Uri uri) {
    builder = ImageRequestBuilder.newBuilderWithSource(uri);
    wrapperRequest(builder);
  }

  private ImageRequest getImageRequest() {
    return builder.build();
  }

  public OKDataSourceWrapper<CloseableReference<PooledByteBuffer>> fetchEncodedImage() {
    return fetchEncodedImage(null);
  }

  public OKDataSourceWrapper<CloseableReference<PooledByteBuffer>> fetchEncodedImage(
      @Nullable RequestListener requestListener) {
    return fetchEncodedImage(null, null);
  }

  public OKDataSourceWrapper<CloseableReference<PooledByteBuffer>> fetchEncodedImage(
      Object callerContext,
      @Nullable RequestListener requestListener) {

    return OKDataSourceWrapper.newDataSourceWrapper(
        OKFresco.getImagePipeline().fetchEncodedImage(getImageRequest(), callerContext,
            requestListener));
  }

  public OKDataSourceWrapper<CloseableReference<CloseableImage>> fetchDecodedImage() {
    return fetchDecodedImage(null,
        ImageRequest.RequestLevel.FULL_FETCH, null);
  }

  public OKDataSourceWrapper<CloseableReference<CloseableImage>> fetchDecodedImage(
      Object callerContext,
      @Nullable RequestListener requestListener) {
    return fetchDecodedImage(callerContext,
        ImageRequest.RequestLevel.FULL_FETCH, requestListener);
  }

  public OKDataSourceWrapper<CloseableReference<CloseableImage>> fetchDecodedImage(
      Object callerContext,
      ImageRequest.RequestLevel lowestPermittedRequestLevelOnSubmit) {
    return fetchDecodedImage(callerContext,
        lowestPermittedRequestLevelOnSubmit, null);
  }

  public OKDataSourceWrapper<CloseableReference<CloseableImage>> fetchDecodedImage(
      Object callerContext,
      ImageRequest.RequestLevel lowestPermittedRequestLevelOnSubmit,
      @Nullable RequestListener requestListener) {
    return OKDataSourceWrapper.newDataSourceWrapper(OKFresco.getImagePipeline().fetchDecodedImage(
        getImageRequest(), callerContext, lowestPermittedRequestLevelOnSubmit, requestListener));
  }
}
