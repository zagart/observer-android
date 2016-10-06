package by.grodno.zagart.observer.observerandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import by.grodno.zagart.observer.observerandroid.activities.A1;

/**
 * Activity without UI that runs at startup.
 */
public class ZeroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, A1.class));
    }


}
