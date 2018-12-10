package cn.langreal.john.testmtklog;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ftinc.kit.adapter.BetterRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.langreal.john.testmtklog.model.caseResultModel;

public class MTKLogCaseAdapter extends BetterRecyclerAdapter<caseResultModel, MTKLogCaseAdapter.LogViewHolder>
{


        @Override
        public MTKLogCaseAdapter.LogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item, parent, false);
        return new MTKLogCaseAdapter.LogViewHolder(view);
    }

        @Override
        public void onBindViewHolder(MTKLogCaseAdapter.LogViewHolder viewHolder, int i) {
        super.onBindViewHolder(viewHolder, i);
        caseResultModel caseModel = getItem(i);
        viewHolder.title.setText(caseModel.caseName);
        viewHolder.description.setText(GiantsConstans.SDCARD_PATH+caseModel.mtklog);
        if(caseModel.status.equals("success"))
            viewHolder.viewSum1.setBackgroundColor(Color.parseColor("#008577"));
        else if (caseModel.status.equals("failed"))
          viewHolder.viewSum1.setBackgroundColor(Color.parseColor("#FF4081"));
        else if (caseModel.status.equals("failed"))
          viewHolder.viewSum1.setBackgroundColor(R.color.yellowunKonw);
    }

        public static class LogViewHolder extends RecyclerView.ViewHolder{
            @BindView(R.id.title)         public TextView title;
            @BindView(R.id.description)   public TextView description;
            @BindView(R.id.viewSum1)   public View viewSum1;
            public LogViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    }


