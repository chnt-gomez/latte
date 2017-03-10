package com.go.kchin.util.utilities;

import android.app.Activity;
import android.view.View;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.go.kchin.R;
import com.go.kchin.interfaces.TutorialService;

/**
 * Created by MAV1GA on 09/03/2017.
 */

public class TutorialBuilder implements View.OnClickListener{

    private ShowcaseView sv;
    private TutorialService view;
    private int step = 0;

    public TutorialBuilder(TutorialService view){
        this.view = view;
    }

    public void start(Activity activity){
        sv = new ShowcaseView.Builder(activity)
        .withMaterialShowcase()
                .setStyle(R.style.CustomShowcaseTheme).build();
        view.onMoveToStep(step, sv);
    }


    @Override
    public void onClick(View v) {
        step++;
        view.onMoveToStep(step, this.sv);
    }
}
