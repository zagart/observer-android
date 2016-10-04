package by.grodno.zagart.observer.observerandroid.activities;

import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.TextView;

import by.grodno.zagart.observer.observerandroid.MainActivity;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class LastActivity extends A {

    public static final String NOTHING_TO_SHOW = "Nothing to show.";
    public static final int RESULT_TEXT_SIZE = 40;
    public static final int RESULT_CELL_MIN_WIDTH = 20;
    public static final String SEPARATOR = ":";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPreviousActivity(A5.class);
        setNextActivity(MainActivity.class);
        setContentView(buildContentView());
    }

    private GridLayout buildContentView() {
        GridLayout layout = new GridLayout(this);
        layout.setColumnCount(3);
        layout.setLayoutParams(getLayoutParams(WRAP_CONTENT, MATCH_PARENT));
        layout.setOrientation(GridLayout.HORIZONTAL);
        layout.setUseDefaultMargins(true);
        if (this.answers != null && !this.answers.isEmpty()) {
            showResult(layout);
        } else {
            layout.addView(createTextView(NOTHING_TO_SHOW, RESULT_TEXT_SIZE, RESULT_CELL_MIN_WIDTH));
        }
        return layout;
    }

    private void showResult(GridLayout layout) {
        for (String activityName : this.answers.keySet()) {
            TextView textView;
            layout.addView(createTextView(activityName, RESULT_TEXT_SIZE, RESULT_CELL_MIN_WIDTH));
            layout.addView(createTextView(SEPARATOR, RESULT_TEXT_SIZE, RESULT_CELL_MIN_WIDTH));
            if (this.answers.getBoolean(activityName)) {
                textView = createTextView(A.STRING_YES, RESULT_TEXT_SIZE, RESULT_CELL_MIN_WIDTH);
                layout.addView(textView);
            } else {
                textView = createTextView(A.STRING_NO, RESULT_TEXT_SIZE, RESULT_CELL_MIN_WIDTH);
                layout.addView(textView);
            }
        }
    }

}
