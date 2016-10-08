package by.grodno.zagart.observer.observerandroid;
import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import by.grodno.zagart.observer.observerandroid.classes.HappyButton;

@RunWith(MockitoJUnitRunner.class)
public class HappyButtonTest {

    private HappyButton mHappyButton;
    @Mock
    private Context mContext;

    @Before
    public void setUp() {
        mHappyButton = new HappyButton(mContext);
    }

    @Test
    public void test() {

    }
}
