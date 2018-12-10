package cn.langreal.john.testmtklog.model;

import android.os.Parcel;
import android.os.Parcelable;

public class caseResultModel implements Parcelable {

    public String caseName;
    public String description;
  public String  mtklog;
    public String startTime;
    public String endTime;
    public String status;
    public caseResultModel(){}

    private caseResultModel(Parcel in){
      caseName = in.readString();
        startTime = in.readString();
      endTime = in.readString();
        description = in.readString();
      mtklog = in.readString();
        status= in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(caseName);

        dest.writeString(description);

        dest.writeString(startTime);
      dest.writeString(mtklog);
        dest.writeString(endTime);
        dest.writeString(status);
    }

    public static final Parcelable.Creator<caseResultModel> CREATOR = new Parcelable.Creator<caseResultModel>() {
        @Override
        public caseResultModel createFromParcel(Parcel source) {
            return new caseResultModel(source);
        }

        @Override
        public caseResultModel[] newArray(int size) {
            return new caseResultModel[size];
        }
    };

}

