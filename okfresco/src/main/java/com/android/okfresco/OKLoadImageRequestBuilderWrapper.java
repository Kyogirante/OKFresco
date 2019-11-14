package com.android.okfresco;

import android.net.Uri;
import android.support.annotation.DrawableRes;

import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;



/**
 * Wrapper for loading picture into {@link SimpleDraweeView}
 *
 * @author KyoWang
 * @since 2018/12/27
 */
public class OKLoadImageRequestBuilderWrapper extends OKImageRequestBuilderWrapper<OKLoadImageRequestBuilderWrapper> {

  public static OKLoadImageRequestBuilderWrapper newRequest(Uri uri) {
    return new OKLoadImageRequestBuilderWrapper(uri);
  }

  public static OKLoadImageRequestBuilderWrapper newRequest(@DrawableRes int resId) {
    return new OKLoadImageRequestBuilderWrapper(resId);
  }

  private OKLoadImageRequestBuilderWrapper(Uri uri) {
    builder = ImageRequestBuilder.newBuilderWithSource(uri);
    wrapperRequest(builder);
  }

  private OKLoadImageRequestBuilderWrapper(@DrawableRes int resId) {
    builder = ImageRequestBuilder.newBuilderWithResourceId(resId);
    wrapperRequest(builder);
  }

  public ImageRequest getImageRequest() {
    return builder.build();
  }

  public OKFrescoControllerBuilderWrapper buildRequest() {
    return OKFresco.load(getImageRequest());
  }

  public void into(SimpleDraweeView view) {
    OKFresco.load(getImageRequest()).into(view);
  }
}
