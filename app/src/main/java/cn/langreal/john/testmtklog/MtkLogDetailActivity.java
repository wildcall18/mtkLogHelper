package cn.langreal.john.testmtklog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ftinc.kit.util.SizeUtils;
import com.ftinc.kit.util.Utils;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.langreal.john.testmtklog.model.caseResultModel;

public class MtkLogDetailActivity extends AppCompatActivity {
    public static final String EXTRA_RESULT = "extra_result";




    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.description) TextView mDescription;
    @BindView(R.id.startTime) TextView mStartTime;
  @BindView(R.id.endTime) TextView mEndTime;
  @BindView(R.id.mtkLog) TextView mMtkLog;
    @BindView(R.id.status) TextView mStatus;
  @BindView(R.id.color1) View mColor1;
  @BindView(R.id.color2) View mColor2;
  @BindView(R.id.color3) View mColor3;
  @BindView(R.id.color4) View mColor4;
    private caseResultModel mResult;
    private SlidrConfig mConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mtk_log_detail); ButterKnife.bind(this);

        // Get the status bar colors to interpolate between
        int primary = getResources().getColor(R.color.primaryDark);
        int secondary = getResources().getColor(R.color.red_500);




        mConfig = new SlidrConfig.Builder()
                .primaryColor(primary)
                .secondaryColor(secondary)
                .position(SlidrPosition.VERTICAL)
                .velocityThreshold(2400)
//                .distanceThreshold(.25f)
//                .edge(true)
                .touchSize(SizeUtils.dpToPx(this, 32))
                .build();

        // Attach the Slidr Mechanism to this activity
        Slidr.attach(this, mConfig);

     //   setSupportActionBar(mToolbar);
      //  getSupportActionBar().setTitle("");
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mResult = getIntent().getParcelableExtra(EXTRA_RESULT);
        if(savedInstanceState != null) mResult = savedInstanceState.getParcelable(EXTRA_RESULT);

        // Set layout contents
        mTitle.setText(mResult.caseName);
       mDescription.setText(mResult.description);
        mStartTime.setText(mResult.startTime);

      mEndTime.setText(mResult.endTime);
      mMtkLog.setText(mResult.mtklog);
      mStatus.setText(mResult.status);

      if(mResult.status.equals("success"))
      {
        mColor1.setBackgroundColor(Color.parseColor("#008577"));
        mColor2.setBackgroundColor(Color.parseColor("#689F38"));
        mColor3.setBackgroundColor(Color.parseColor("#8BC34A"));
        mColor4.setBackgroundColor(Color.parseColor("#15dd18"));
      }
      else if(mResult.status.equals("failed"))
      {

      }
    }

    @OnClick({R.id.color1, R.id.color2, R.id.color3, R.id.color4})
    void onColorClicked(View v){
        int color = ((ColorDrawable)v.getBackground()).getColor();
        getWindow().setStatusBarColor(color);
        mConfig.setColorSecondary(color);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_RESULT, mResult);
    }
}

