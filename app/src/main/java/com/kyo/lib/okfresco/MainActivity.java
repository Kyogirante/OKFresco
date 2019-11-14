package com.kyo.lib.okfresco;

import java.io.File;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.okfresco.OKFresco;
import com.android.okfresco.OKImageDataSubscriber;
import com.facebook.drawee.view.SimpleDraweeView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private ImageView imageView;

  private SimpleDraweeView simpleDraweeView;

  private String imageViewUrl = "https://hbimg.huabanimg.com/22a4bf8dbc393491975e3f173260a5709c418d6eaa99-4lO2yq_fw658";

  private String simpleDraweeViewUrl = "https://c-ssl.duitang.com/uploads/item/201609/20/20160920172653_tUWru.thumb.700_0.jpeg";

  private String path;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();

    imageView = findViewById(R.id.image_view);
    simpleDraweeView = findViewById(R.id.simple_draw_view);

    OKFresco.loadIntoImageView(Uri.parse(imageViewUrl))
        .into(imageView);

    OKFresco.load(Uri.parse(simpleDraweeViewUrl))
        .into(simpleDraweeView);

    imageView.setOnClickListener(this);
    simpleDraweeView.setOnClickListener(this);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    if (grantResults == null) {
      Toast.makeText(MainActivity.this, "开读写SD卡权限", Toast.LENGTH_SHORT).show();
      return;
    }
    boolean requestFail = false;
    for (int i : grantResults) {
      if (i != PackageManager.PERMISSION_GRANTED) {
        requestFail = true;
        break;
      }
    }
    if (requestFail) {
      Toast.makeText(MainActivity.this, "开读写SD卡权限", Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public void onClick(View v) {
    int id = v.getId();
    if (id == R.id.image_view) {
      downPicture(imageViewUrl, "imageViewUrl");
    } else if (id == R.id.simple_draw_view) {
      downPicture(simpleDraweeViewUrl, String.valueOf(System.currentTimeMillis()));
    }
  }

  private void downPicture(String url, String fileName) {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
      String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
      ActivityCompat.requestPermissions(this, permissions, 333);
      return;
    }

    OKFresco.download(Uri.parse(url)).fetchEncodedImage().subscribe(new OKImageDataSubscriber(path, fileName) {
      @Override
      protected void onSuccess(File file) {
        notifyGallery(file.getAbsolutePath());
        Toast.makeText(MainActivity.this, "Download success! File path : " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
      }

      @Override
      protected void onFileExist(File file) {
        super.onFileExist(file);
        Toast.makeText(MainActivity.this, "File exist!", Toast.LENGTH_SHORT).show();
      }

      @Override
      protected void onFail(Throwable throwable) {
        Toast.makeText(MainActivity.this, "fail", Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void notifyGallery(String imagePath) {
    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
    Uri contentUri = Uri.fromFile(new File(imagePath));
    mediaScanIntent.setData(contentUri);
    sendBroadcast(mediaScanIntent);
  }
}
