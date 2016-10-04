package by.grodno.zagart.observer.observerandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Abstract activity class.
 */

public abstract class A extends AppCompatActivity {

    public static final String PROGRESS = "progress";
    public static final String NEXT = "Next";
    public static final String ACTIVITY_CREATED = "Activity created.";

    protected ArrayList<String> progress = new ArrayList<>();
    protected TextView progressView = null;
    protected Class nextActivity = null;
    protected String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.name = A.this.getClass().getSimpleName();
        if (savedInstanceState != null) {
            this.progress = savedInstanceState.getStringArrayList(PROGRESS);
        }
        addProgress(ACTIVITY_CREATED);
        LinearLayout layout = buildContentView();
        setContentView(layout);
    }

    protected LinearLayout buildContentView() {
        ViewGroup.LayoutParams params = new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        progressView = new TextView(this);
        progressView.setLayoutParams(params);
        refreshTextViewer();
        progressView.setTextSize(20);

        Button nextButton = new Button(this);
        nextButton.setText(NEXT);
        nextButton.setTextSize(40);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                A.this.createNextActivity();
            }
        });

        LinearLayout layout =  new LinearLayout(this);
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.BOTTOM);
        layout.addView(nextButton);
        layout.addView(progressView);
        return layout;
    }

    private void refreshTextViewer() {
        StringBuilder progress = new StringBuilder();
        for (String step : this.progress) {
            progress.append(step);
        }
        progressView.setText(progress);
    }
    protected void createNextActivity() {
        if (nextActivity != null) {
            Intent intent = new Intent(this, nextActivity);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    protected void setNextActivity(Class nextActivity) {
        this.nextActivity = nextActivity;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.progress = savedInstanceState.getStringArrayList(PROGRESS);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(PROGRESS, this.progress);
    }

    protected void addProgress(String progress) {
        this.progress.add(String.format("%s: %s\n", this.name, progress));
    }

}
