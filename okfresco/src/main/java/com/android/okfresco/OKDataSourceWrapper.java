package com.android.okfresco;

import java.util.concurrent.Executor;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;

/**
 * Wrapper for {@link DataSource}
 *
 * @author KyoWang
 * @since 2018/12/27
 */
public final class OKDataSourceWrapper<T> implements DataSource<T> {

  private DataSource<T> dataSource;

  public static <T> OKDataSourceWrapper<T> newDataSourceWrapper(@NonNull DataSource<T> dataSource) {
    return new OKDataSourceWrapper(dataSource);
  }

  private OKDataSourceWrapper(@NonNull DataSource<T> dataSource) {
    this.dataSource = dataSource;
  }

  public DataSource<T> getOriginDataSource() {
    return dataSource;
  }

  @Override
  public boolean isClosed() {
    return dataSource.isClosed();
  }

  @Nullable
  @Override
  public T getResult() {
    return dataSource.getResult();
  }

  @Override
  public boolean hasResult() {
    return dataSource.hasResult();
  }

  @Override
  public boolean hasMultipleResults() {
    return dataSource.hasMultipleResults();
  }

  @Override
  public boolean isFinished() {
    return dataSource.isFinished();
  }

  @Override
  public boolean hasFailed() {
    return dataSource.hasFailed();
  }

  @Nullable
  @Override
  public Throwable getFailureCause() {
    return dataSource.getFailureCause();
  }

  @Override
  public float getProgress() {
    return dataSource.getProgress();
  }

  @Override
  public boolean close() {
    return dataSource.close();
  }

  @Override
  public void subscribe(DataSubscriber<T> dataSubscriber, Executor executor) {
    dataSource.subscribe(dataSubscriber, executor);
  }

  /**
   * 默认主线程.
   *
   * @param dataSubscriber
   */
  public void subscribe(DataSubscriber<T> dataSubscriber) {
    subscribeMainThread(dataSubscriber);
  }

  /**
   * 异步线程.
   *
   * @param dataSubscriber
   */
  public void subscribeAsyncThread(DataSubscriber<T> dataSubscriber, Executor executor) {
    subscribe(dataSubscriber, executor);
  }

  /**
   * 主线程.
   *
   * @param dataSubscriber
   */
  public void subscribeMainThread(DataSubscriber<T> dataSubscriber) {
    subscribe(dataSubscriber, UiThreadImmediateExecutorService.getInstance());
  }
}
