package by.grodno.zagart.observer.observerandroid.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridLayout;
import android.widget.TextView;

import static by.grodno.zagart.observer.observerandroid.util.AnswersUtil.restoreAnswers;
import static by.grodno.zagart.observer.observerandroid.util.MakeupUtil.createTextView;

public class LastActivity extends AppCompatActivity {

    public static final String NOTHING_TO_SHOW = "Nothing to show.";
    public static final int RESULT_TEXT_SIZE = 40;
    public static final int RESULT_CELL_MIN_WIDTH = 20;
    public static final String SEPARATOR = ":";
    private Bundle answers = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.answers = restoreAnswers(this, savedInstanceState);
        setContentView(buildContentView());
    }

    private GridLayout buildContentView() {
        GridLayout layout = new GridLayout(this);
        layout.setColumnCount(3);
        layout.setOrientation(GridLayout.HORIZONTAL);
        layout.setUseDefaultMargins(true);
        if (this.answers != null && !this.answers.isEmpty()) {
            showResult(layout);
        } else {
            layout.addView(createTextView(
                    NOTHING_TO_SHOW,
                    RESULT_TEXT_SIZE,
                    RESULT_CELL_MIN_WIDTH,
                    this)
            );
        }
        return layout;
    }

    private void showResult(GridLayout layout) {
        for (String activityName : this.answers.keySet()) {
            TextView textView;
            layout.addView(createTextView(
                    activityName,
                    RESULT_TEXT_SIZE,
                    RESULT_CELL_MIN_WIDTH,
                    this)
            );
            layout.addView(createTextView(
                    SEPARATOR,
                    RESULT_TEXT_SIZE,
                    RESULT_CELL_MIN_WIDTH,
                    this)
            );
            if (this.answers.getBoolean(activityName)) {
                textView = createTextView(
                        A.STRING_YES,
                        RESULT_TEXT_SIZE,
                        RESULT_CELL_MIN_WIDTH,
                        this
                );
                layout.addView(textView);
            } else {
                textView = createTextView(
                        A.STRING_NO,
                        RESULT_TEXT_SIZE,
                        RESULT_CELL_MIN_WIDTH,
                        this
                );
                layout.addView(textView);
            }
        }
    }

}
