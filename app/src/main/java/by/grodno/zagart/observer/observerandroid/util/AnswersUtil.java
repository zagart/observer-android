package by.grodno.zagart.observer.observerandroid.util;

import android.app.Activity;
import android.os.Bundle;

import static by.grodno.zagart.observer.observerandroid.activities.A.STRING_ANSWERS;

/**
 * Answers utility class.
 */
public class AnswersUtil {

    public static Bundle restoreAnswers(Activity activity,
                                        Bundle savedInstanceState) {
        Bundle answers = new Bundle();
        final Bundle extras = activity.getIntent().getExtras();
        if (extras != null) {
            answers = extras.getBundle(STRING_ANSWERS);
        } else if (savedInstanceState != null) {
                answers = savedInstanceState.getBundle(STRING_ANSWERS);
        }
        return answers;
    }

}
