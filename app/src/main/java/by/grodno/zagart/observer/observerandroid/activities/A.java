package by.grodno.zagart.observer.observerandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import static by.grodno.zagart.observer.observerandroid.util.AnswersUtil.restoreAnswers;
import static by.grodno.zagart.observer.observerandroid.util.MakeupUtil.createButton;
import static by.grodno.zagart.observer.observerandroid.util.MakeupUtil.createTextView;

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
    /*
     * Launch mode single top - in difference with standart launch mode,
     * every new starting activity won't create new instance if there
     * is already exists one.
     */
    private int defaultFlag = Intent.FLAG_ACTIVITY_SINGLE_TOP;
    private String name = "";

    public void setDefaultFlag(int defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }
        this.name = A.this.getClass().getSimpleName();
        this.answers = restoreAnswers(this, savedInstanceState);
        setContentView(buildContentView());
    }


    private LinearLayout buildContentView() {
        answerView = createTextView("", RESULT_TEXT_SIZE, ANSWER_MIN_WIDTH, this);

        Button nextButton = createButton(STRING_NEXT, NEXT_TEXT_SIZE, this);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                A.this.startActivity(A.this.nextActivity);
            }
        });

        Button buttonYes = createButton(STRING_YES, ANSWER_TEXT_SIZE, this);
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                A.this.acceptAnswer(YES);
            }
        });

        Button buttonNo = createButton(STRING_NO, ANSWER_TEXT_SIZE, this);
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
        return configureRootLayout(footer);
    }

    private void startActivity(Class activity) {
        if (nextActivity != null) {
            Intent intent = new Intent(this, activity);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.addFlags(this.defaultFlag);
            intent.putExtra(STRING_ANSWERS, this.answers);
            startActivity(intent);
        }
    }

    private void acceptAnswer(boolean answer) {
        if (answers.containsKey(name)) {
            answers.remove(name);
        }
        answers.putBoolean(name, answer);
        getIntent().putExtra(STRING_ANSWERS, answers);
        refreshAnswerView();
    }

    private LinearLayout configureFooterLayout(Button nextButton, Button buttonYes, Button buttonNo) {
        LinearLayout footer = new LinearLayout(this);
        footer.setOrientation(LinearLayout.HORIZONTAL);
        footer.setGravity(Gravity.BOTTOM);
        footer.addView(buttonNo);
        footer.addView(nextButton);
        footer.addView(buttonYes);
        return footer;
    }

    private LinearLayout configureRootLayout(LinearLayout footer) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
        layout.setGravity(Gravity.TOP);
        layout.addView(answerView);
        layout.addView(footer);
        return layout;
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(STRING_ANSWERS, this.answers);
    }

    protected void setNextActivity(Class nextActivity) {
        this.nextActivity = nextActivity;
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.getIntent().putExtra(A.STRING_ANSWERS, this.answers);
    }

    /**
     * This event becomes available when launch mode is single top.
     *
     * @param intent saved instance state
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.answers = restoreAnswers(this, intent.getBundleExtra(STRING_ANSWERS));
        refreshAnswerView();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.answers = savedInstanceState.getBundle(STRING_ANSWERS);
        refreshAnswerView();
    }

}
