package by.grodno.zagart.observer.observerandroid.util;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

/**
 * Makeup utility class.
 */
public class MakeupUtil {

    public static Button createButton(String name,
                                      int textSize,
                                      AppCompatActivity activity) {
        Button button = new Button(activity);
        button.setText(name);
        button.setTextSize(textSize);
        return button;
    }

    public static TextView createTextView(String text,
                                          int textSize,
                                          int minWidth,
                                          AppCompatActivity activity) {
        TextView textView = new TextView(activity);
        textView.setText(text);
        textView.setTextSize(textSize);
        textView.setMinWidth(minWidth);
        return textView;
    }

}
