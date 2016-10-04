package by.grodno.zagart.observer.observerandroid.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Abstract activity class.
 */

public abstract class A extends AppCompatActivity {

    public static final boolean YES = true;
    public static final boolean NO = false;
    public static final String STRING_YES = "Yes";
    public static final String STRING_NO = "No";
    public static final String STRING_ANSWERS = "Answers";
    public static final String STRING_NEXT = "Next";
    public static final int ANSWER_TEXT_SIZE = 20;
    public static final int NEXT_TEXT_SIZE = 30;
    public static final int RESULT_TEXT_SIZE = 60;
    public static final int ANSWER_MIN_WIDTH = 50;

    protected Bundle answers = new Bundle();
    private TextView answerView = null;
    private Class nextActivity = null;
    private Class previousActivity = null;
    private String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        (getSupportActionBar()).hide();
        this.name = A.this.getClass().getSimpleName();
        this.answers = restoreAnswers(this, savedInstanceState);
        setContentView(buildContentView());
    }

    private static Bundle restoreAnswers(Activity activity,
                                      Bundle savedInstanceState) {
        Bundle answers = new Bundle();
        if (activity.getIntent().getExtras() != null) {
            answers = activity.getIntent().getExtras().getBundle(STRING_ANSWERS);
        }
        if (savedInstanceState != null) {
            answers = savedInstanceState.getBundle(STRING_ANSWERS);
        }
        return answers;
    }

    private LinearLayout buildContentView() {
        answerView = createTextView("", RESULT_TEXT_SIZE, ANSWER_MIN_WIDTH);
        answerView.setLayoutParams(getLayoutParams(MATCH_PARENT, WRAP_CONTENT));
        refreshAnswerView();

        Button nextButton = createButton(STRING_NEXT, NEXT_TEXT_SIZE);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                A.this.startActivity(A.this.nextActivity);
            }
        });

        Button buttonYes = createButton(STRING_YES, ANSWER_TEXT_SIZE);
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                A.this.acceptAnswer(YES);
            }
        });

        Button buttonNo = createButton(STRING_NO, ANSWER_TEXT_SIZE);
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                A.this.acceptAnswer(NO);
            }
        });

        LinearLayout footer = configureFooterLayout(
                nextButton,
                buttonYes,
                buttonNo
        );

        LinearLayout layout = configureRootLayout(footer);
        return layout;
    }

    private LinearLayout configureFooterLayout(Button nextButton, Button buttonYes, Button buttonNo) {
        LinearLayout footer = new LinearLayout(this);
        footer.setOrientation(LinearLayout.HORIZONTAL);
        footer.setLayoutParams(getLayoutParams(MATCH_PARENT, MATCH_PARENT));
        footer.setGravity(Gravity.BOTTOM);
        footer.addView(buttonNo);
        footer.addView(nextButton);
        footer.addView(buttonYes);
        return footer;
    }

    @NonNull
    protected ViewGroup.LayoutParams getLayoutParams(int width, int height) {
        return new ActionBar.LayoutParams(width, height);
    }

    private LinearLayout configureRootLayout(LinearLayout footer) {
        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(getLayoutParams(MATCH_PARENT, MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
        layout.setGravity(Gravity.TOP);
        layout.addView(answerView);
        layout.addView(footer);
        return layout;
    }

    protected Button createButton(String name, int textSize) {
        Button button = new Button(this);
        button.setText(name);
        button.setTextSize(textSize);
        return button;
    }

    protected TextView createTextView(String text, int textSize, int minWidth) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextSize(textSize);
        textView.setMinWidth(minWidth);
        return textView;
    }

    private void refreshAnswerView() {
        if (this.answers.containsKey(this.name)) {
            if (this.answers.getBoolean(this.name)) {
                this.answerView.setText(String.format("%s : %s", this.name, STRING_YES));
            } else {
                this.answerView.setText(String.format("%s : %s", this.name, STRING_NO));
            }
        }
    }


    private void startActivity(Class activity) {
        if (nextActivity != null) {
            Intent intent = new Intent(this, activity);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            /**
             * Launch mode single top - in difference with standart launch mode,
             * every new starting activity won't create new instance if there
             * is already exists one.
             */
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra(STRING_ANSWERS, this.answers);
            startActivity(intent);
        }
    }

    private void acceptAnswer(boolean answer) {
        if (answers.containsKey(name)) {
            answers.remove(name);
        }
        answers.putBoolean(name, answer);
        refreshAnswerView();
    }

    protected void setNextActivity(Class nextActivity) {
        this.nextActivity = nextActivity;
    }

    protected void setPreviousActivity(Class previousActivity) {
        this.previousActivity = previousActivity;
    }

    /**
     * This event becomes available when launch mode is single top.
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.answers = restoreAnswers(this, intent.getBundleExtra(STRING_ANSWERS));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.answers = savedInstanceState.getBundle(STRING_ANSWERS);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(STRING_ANSWERS, this.answers);
    }

    @Override
    public void onBackPressed() {
        startActivity(previousActivity);
    }

}
