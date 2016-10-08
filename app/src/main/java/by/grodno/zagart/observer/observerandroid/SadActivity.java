package by.grodno.zagart.observer.observerandroid;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Sad activity. Born to be sad.
 */
public class SadActivity extends AppCompatActivity {

    public static final String TITLE = "So sad...";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sad_activity);
        this.setTitle(TITLE);
    }
}
