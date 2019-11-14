package com.kyo.lib.okfresco;

import android.app.Application;

import com.android.okfresco.OKFresco;

/**
 * @author KyoWang
 * @since 2019-11-14
 */
public class OKFrescoApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    OKFresco.initialize(this);
  }

}
