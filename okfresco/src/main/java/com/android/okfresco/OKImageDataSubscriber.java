package com.android.okfresco;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.memory.PooledByteBufferInputStream;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imageformat.ImageFormatChecker;

/**
 * subscriber of downloading picture
 *
 * @author KyoWang
 * @since 2018/12/27
 */
public abstract class OKImageDataSubscriber extends BaseDataSubscriber<CloseableReference<PooledByteBuffer>> {

  private String imagePath;
  private String imageName;

  public OKImageDataSubscriber(String imagePath, String imageName) {
    this.imagePath = imagePath;
    this.imageName = imageName;
  }

  @Override
  protected void onNewResultImpl(DataSource<CloseableReference<PooledByteBuffer>> dataSource) {
    InputStream is = null;
    OutputStream os = null;
    File imageFile = null;

    try {
      is = new PooledByteBufferInputStream(dataSource.getResult().get());

      imageFile = getImageFile(imagePath, is);

      if (imageFile.exists()) {
        onFileExist(imageFile);
        return;
      }

      os = new FileOutputStream(imageFile);
      IOUtils.copy(is, os);
      onSuccess(imageFile);
    } catch (Exception e) {
      e.printStackTrace();
      if (imageFile != null) {
        imageFile.delete();
      }
      onFail(e);
    } finally {
      dataSource.close();
      IOUtils.closeQuietly(is);
      IOUtils.closeQuietly(os);
    }
  }

  @Override
  protected void onFailureImpl(DataSource<CloseableReference<PooledByteBuffer>> dataSource) {
    dataSource.close();
    onFail(dataSource.getFailureCause());
  }

  private File getImageFile(String iamgePath, InputStream is) throws IOException {
    File parentFile = new File(imagePath);
    if (!parentFile.exists()) {
      parentFile.mkdirs();
    }
    String fileName = getFileName(is);

    return new File(imagePath, fileName);
  }

  @NonNull
  private String getFileName(InputStream is) throws IOException {
    String fileName;
    ImageFormat imageFormat = ImageFormatChecker.getImageFormat(is);
    if (!TextUtils.isEmpty(imageFormat.getFileExtension())) {
      fileName = imageName.concat(".").concat(imageFormat.getFileExtension());
    } else {
      fileName = imageName.concat(".jpg");
    }
    return fileName;
  }

  protected void onFileExist(File file) {}

  protected abstract void onSuccess(File file);

  protected abstract void onFail(Throwable throwable);
}
