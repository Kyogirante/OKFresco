package com.android.okfresco;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.DraweeConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imageformat.ImageFormatChecker;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.request.ImageRequest;


/**
 * Fresco封装
 *
 * @author KyoWang
 * @since 2018/12/27
 */
public class OKFresco {

  /**
   * Initializes Fresco with the default config.
   *
   * @param context
   */
  public static void initialize(Application context) {
    initialize(context, null,  null);
  }

  /**
   * Initializes Fresco with the default Drawee config.
   *
   * @param context context
   * @param imagePipelineConfig config
   */
  public static void initialize(Context context, @Nullable ImagePipelineConfig imagePipelineConfig) {
    initialize(context, imagePipelineConfig, null);
  }

  /**
   * Initializes Fresco with the specified config.
   *
   * @param context context
   * @param imagePipelineConfig config
   * @param draweeConfig congfig
   */
  public static void initialize(Context context, @Nullable ImagePipelineConfig imagePipelineConfig, @Nullable DraweeConfig draweeConfig) {
    Fresco.initialize(context, imagePipelineConfig, draweeConfig);
  }

  /**
   * 加载图片到 {@link com.facebook.drawee.view.SimpleDraweeView}
   *
   * @param uri 资源URI
   * @return {@link OKLoadImageRequestBuilderWrapper}
   */
  public static OKLoadImageRequestBuilderWrapper load(Uri uri) {
    return OKLoadImageRequestBuilderWrapper.newRequest(uri);
  }

  /**
   * 加载图片到 {@link com.facebook.drawee.view.SimpleDraweeView}
   *
   * @param resId 资源id
   * @return {@link OKLoadImageRequestBuilderWrapper}
   */
  public static OKLoadImageRequestBuilderWrapper load(@DrawableRes int resId) {
    return OKLoadImageRequestBuilderWrapper.newRequest(resId);
  }

  /**
   * 加载图片到 {@link com.facebook.drawee.view.SimpleDraweeView}
   *
   * @param request 请求
   * @return {@link OKFrescoControllerBuilderWrapper}
   */
  public static OKFrescoControllerBuilderWrapper load(ImageRequest request) {
    return OKFrescoControllerBuilderWrapper.newWrapper(request);
  }

  /**
   * @param requests 请求
   * @return {@link OKFrescoControllerBuilderWrapper}
   */
  public static OKFrescoControllerBuilderWrapper load(ImageRequest[] requests) {
    return OKFrescoControllerBuilderWrapper.newWrapper(requests);
  }

  /**
   * 加载图片到 {@link android.widget.ImageView}
   * 如果是资源，使用 {@link com.facebook.common.util.UriUtil#getUriForResourceId(int)} 做转换
   *
   * @param uri 资源URI
   * @return {@link OKImageViewRequestBuilderWrapper}
   */
  public static OKImageViewRequestBuilderWrapper loadIntoImageView(Uri uri) {
    return OKImageViewRequestBuilderWrapper.newRequest(uri);
  }

  /**
   * 下载图片.
   *
   * @param uri 资源URI
   * @return {@link OKDownloadImageRequestBuilderWrapper}
   */
  public static OKDownloadImageRequestBuilderWrapper download(Uri uri) {
    return OKDownloadImageRequestBuilderWrapper.newRequest(uri);
  }

  /**
   * 获取下载图片处理类.
   *
   * @return {@link ImagePipeline}
   */
  public static ImagePipeline getImagePipeline() {
    return Fresco.getImagePipeline();
  }

  /**
   * 获取下载图片处理类工厂.
   *
   * @return {@link ImagePipelineFactory}
   */
  public static ImagePipelineFactory getImagePipelineFactory() {
    return Fresco.getImagePipelineFactory();
  }

  /**
   * 是否初始化.
   *
   * @return 是否初始化
   */
  public static boolean hasBeenInitialized() {
    return Fresco.hasBeenInitialized();
  }

  /**
   * 关闭.
   */
  public static void shutDown() {
    Fresco.shutDown();
  }


  /**
   * 获取图片的真实格式
   * @param picturePath
   * @return 图片格式
   */
  public static String getImageFormat(String picturePath) {
    try {
      ImageFormat imageFormat =
          ImageFormatChecker.getImageFormat(new FileInputStream(new File(picturePath)));
      return imageFormat.getFileExtension();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 获取缓存
   *
   * @param context context
   * @param url 网络url
   * @return 缓存文件
   */
  public static File getCachedBitmap(Context context, String url) {
    Uri uri = Uri.parse(url);
    DataSource<Boolean> dataSource = OKFresco.getImagePipeline().isInDiskCache(uri);
    if (dataSource == null
        || dataSource.getResult() == null
        || !dataSource.getResult()) {
      return null;
    }

    BinaryResource resource = OKFresco.getImagePipelineFactory()
        .getMainFileCache()
        .getResource(DefaultCacheKeyFactory.getInstance()
            .getEncodedCacheKey(ImageRequest.fromUri(uri), context));

    if (resource == null) {
      return null;
    }

    return ((FileBinaryResource) resource).getFile();
  }
}
