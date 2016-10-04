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

    protected Bundle answers = new Bundle();
    private TextView answerView = null;
    private Class nextActivity = null;
    private Class previousActivity = null;
    private String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        answerView = new TextView(this);
        answerView.setLayoutParams(getLayoutParams(MATCH_PARENT, MATCH_PARENT));
        refreshAnswerView();
        answerView.setTextSize(20);

        Button nextButton = createButton(STRING_NEXT);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                A.this.startActivity(A.this.nextActivity);
            }
        });

        Button buttonYes = createButton(STRING_YES);
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                A.this.acceptAnswer(YES);
            }
        });

        Button buttonNo = createButton(STRING_NO);
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                A.this.acceptAnswer(NO);
            }
        });

        LinearLayout layout =  new LinearLayout(this);
        configureLayout(getLayoutParams(
                MATCH_PARENT,
                MATCH_PARENT),
                nextButton,
                buttonYes,
                buttonNo,
                layout
        );
        return layout;
    }

    @NonNull
    protected ViewGroup.LayoutParams getLayoutParams(int width, int height) {
        return new ActionBar.LayoutParams(width, height);
    }

    private void configureLayout(ViewGroup.LayoutParams params,
                                 Button nextButton,
                                 Button buttonYes,
                                 Button buttonNo,
                                 LinearLayout layout) {
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.BOTTOM);
        layout.addView(nextButton);
        layout.addView(buttonYes);
        layout.addView(buttonNo);
        layout.addView(answerView);
    }

    protected Button createButton(String name) {
        Button button = new Button(this);
        button.setText(name);
        button.setTextSize(40);
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
