package cn.langreal.john.testmtklog;

import android.content.Context;
import android.os.Environment;

public class GiantsConstans {
  public static final String VERSIONSTR = "YS LoggerUtil 0.7";

  /** sdcard目录 */
   public static final String SDCARD_PATH = Environment
          .getExternalStorageDirectory().getAbsolutePath() + "/";
  public static final String JASON_CASEFILE_NAME =  "qtlogs/caseresult.json";
  public static Context context;
}
