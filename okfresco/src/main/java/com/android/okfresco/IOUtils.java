package com.android.okfresco;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

/**
 * Copy from {@see org.apache.commons.io.IOUtils}
 *
 * @author KyoWang
 * @since 2019/3/15
 */
class IOUtils {

  public static int copy(InputStream input, OutputStream output) throws IOException {
    long count = copyLarge(input, output);
    return count > Integer.MAX_VALUE ? -1 : (int) count;
  }

  public static long copyLarge(InputStream input, OutputStream output) throws IOException {
    return copyLarge(input, output, new byte[4096]);
  }

  public static long copyLarge(InputStream input, OutputStream output, byte[] buffer)
      throws IOException {
    long count = 0L;

    int n;
    for (boolean var5 = false; -1 != (n = input.read(buffer)); count += (long) n) {
      output.write(buffer, 0, n);
    }

    return count;
  }

  public static void closeQuietly(Reader input) {
    closeQuietly((Closeable)input);
  }

  public static void closeQuietly(Closeable closeable) {
    try {
      if (closeable != null) {
        closeable.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
