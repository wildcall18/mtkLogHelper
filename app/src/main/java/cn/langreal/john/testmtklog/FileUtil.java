package cn.langreal.john.testmtklog;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/12/13.
 */

public class FileUtil {
  private static final String TAG = "FileUtil";

  public static String readFiles(String fileName) {
    StringBuilder sb = new StringBuilder();
    InputStream is = null;
    BufferedReader br = null;
    try {
      is = GiantsConstans.context.openFileInput(fileName);
      br = new BufferedReader(new InputStreamReader(is));
      String line = "";
      while ((line = br.readLine()) != null) {
        sb.append(line + "\r\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (Exception e) {
        }
      }
      if (br != null) {
        try {
          br.close();
        } catch (Exception e) {
        }
      }
    }
    return sb.toString().trim();
  }

  public static String readFilesFromExternalStorage(String fileName) {
    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fileName);
    StringBuilder sb = new StringBuilder();
    BufferedReader br = null;
    if(file.exists()) {
      try {
        br = new BufferedReader(new FileReader(file));
        String line = "";
        while ((line = br.readLine()) != null) {
          sb.append(line + "\r\n");
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        if (br != null) {
          try {
            br.close();
          } catch (Exception e) {
          }
        }

      }
    }
    return sb.toString().trim();
  }
  public static boolean writeFiles(String fileName, String value, int mode) {
    OutputStream os = null;
    try {
      os = GiantsConstans.context.openFileOutput(fileName, mode);
      os.write(value.getBytes());
      return true;

    } catch (FileNotFoundException e) {
      Log.d(TAG, "FileNotFoundException " + fileName);
      return false;
    } catch (IOException e) {
      Log.d(TAG, "IOException " + fileName);
      return false;
    } finally {
      if (os != null) {
        try {
          os.close();
        } catch (Exception e) {
        }
      }
    }
  }

  public static boolean writeFilesToExternalStorage(String fileName, String value) {
    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),fileName);
    BufferedWriter bw = null;
    try {
      bw = new BufferedWriter(new FileWriter(file, false));
      bw.write(value);
      bw.flush();
      return true;

    } catch (FileNotFoundException e) {
      Log.d(TAG, "FileNotFoundException " + fileName);
      return false;
    } catch (IOException e) {
      Log.d(TAG, "IOException " + fileName);
      return false;
    } finally {
      if (bw != null) {
        try {
          bw.close();
        } catch (Exception e) {
        }
      }
    }
  }

  public static String readwithTag(String fileFullName, String Tag) {
    StringBuilder sb = new StringBuilder();
    BufferedReader br = null;
    try {

      br = new BufferedReader(new FileReader(fileFullName));
      String line = "";
      while ((line = br.readLine()) != null) {
        if(Tag.equals("all"))
        {
          sb.append(line + "\r\n");
        }
        else if(line.contains(Tag))
        {
          sb.append(line + "\r\n");
        }
      }

    } catch (FileNotFoundException e) {
      Log.d(TAG, "FileNotFoundException " + fileFullName);
    } catch (IOException e) {
      Log.d(TAG, "IOException " + fileFullName);
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (Exception e) {
        }
      }
    }
    return sb.toString().trim();
  }

  public static String read(String fileFullName) {
    StringBuilder sb = new StringBuilder();
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader(fileFullName));
      String line = "";
      while ((line = br.readLine()) != null) {
        sb.append(line + "\r\n");
      }

    } catch (FileNotFoundException e) {
      Log.d(TAG, "FileNotFoundException " + fileFullName);
    } catch (IOException e) {
      Log.d(TAG, "IOException " + fileFullName);
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (Exception e) {
        }
      }
    }
    return sb.toString().trim();
  }
  public static boolean write(String fileFullName, String message,
                              boolean append) {
    FileWriter writer = null;
    try {
      writer = new FileWriter(fileFullName, append);
      writer.write(message);
      writer.flush();
      return true;

    } catch (FileNotFoundException e) {
      Log.d(TAG, "FileNotFoundException " + fileFullName);
      return false;
    } catch (IOException e) {
      Log.d(TAG, "IOException " + fileFullName);
      return false;
    } finally {
      try {
        if (writer != null) {
          writer.close();
        }
      } catch (IOException e) {
        Log.d(TAG, "IOException " + fileFullName);
      }
    }
  }

  public static List<String> getFiles(String value) {
    List<String> result = new ArrayList<String>();
    File path = new File(value);
    if (!path.exists()) {
      return result;
    }
    if (path.listFiles() != null) {
      for (File file : path.listFiles()) {
        if (file.isDirectory()) {
          result.addAll(getFiles(file.getPath()));
        } else {
          result.add(file.getAbsolutePath());
        }
      }
    }
    return result;
  }

  public static void removePathAsync(String path) {
    new FileDeleter().execute(path);
  }

  public static void removePathSync(String path) {
    removePath(path);
  }

  public static String filterName(String value) {
    String ret = value.replaceAll("[: /\\*?\"<>|]*", "");
    if ("".equals(ret)) {
      return "null";
    } else {
      return ret;
    }
  }

  private static void removePath(String value) {
    try {
      File path = new File(value);
      if (!path.exists()) {
        return;
      }
      if (path.listFiles() != null) {
        for (File file : path.listFiles()) {
          if (file.isDirectory()) {
            removePath(file.getPath());
          }
          file.delete();
        }
      }
      path.delete();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  static class FileDeleter extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... arg0) {
      removePath(arg0[0]);
      return null;
    }
  }

  public static void copyFile(String fileFromPath, String fileToPath)
    throws Exception {
    InputStream in = null;
    OutputStream out = null;
    try {
      in = new FileInputStream(fileFromPath);
      out = new FileOutputStream(fileToPath);
      int length = in.available();
      int len = (length % 1024 == 0) ? (length / 1024)
        : (length / 1024 + 1);
      byte[] temp = new byte[1024];
      for (int i = 0; i < len; i++) {
        in.read(temp);
        out.write(temp);
      }
    } finally {
      if (in != null)
        in.close();
      if (out != null)
        out.close();
    }
  }

  public static String getSuffix(String fileName) {
    int index = fileName.lastIndexOf('.');
    if (index == -1) {
      return "";
    }
    return fileName.substring(index + 1, fileName.length());
  }

  public static boolean isMediaFile(String fileName) {
    String suffix = getSuffix(fileName);
    return suffix.equalsIgnoreCase("mp3") || suffix.equalsIgnoreCase("jpg")
      || suffix.equalsIgnoreCase("jpeg")
      || suffix.equalsIgnoreCase("png");
  }

  public static boolean isAudio(String fileName) {
    String suffix = getSuffix(fileName);
    return suffix.equalsIgnoreCase("mp3");
  }

  public static boolean isImage(String fileName) {
    String suffix = getSuffix(fileName);
    return suffix.equalsIgnoreCase("png") || suffix.equalsIgnoreCase("jpg")
      || suffix.equalsIgnoreCase("jpeg");
  }

  public static String formatSize(long value) {
    double k = (double) value / 1024;
    if (k == 0) {
      return String.format("%dB", value);
    }
    double m = k / 1024;
    if (m < 1) {
      return String.format("%.1fK", k);
    }
    double g = m / 1024;
    if (g < 1) {
      return String.format("%.1fM", m);
    }
    return String.format("%.1fG", g);
  }
}
