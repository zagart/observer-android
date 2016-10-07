package by.grodno.zagart.observer.observerandroid;
import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import by.grodno.zagart.observer.observerandroid.classes.HappyButton;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HappyButtonTest {

    public static final long EVEN_TIME = 1000L;
    private HappyButton mHappyButton;
    @Mock
    private Context mContext;
    @Mock
    private Date date;

    @Before
    public void setUp() {
        mHappyButton = new HappyButton(mContext, date);
    }

    @Test
    public void happyButton_isVisible_true() {
        when(date.getTime()).thenReturn(EVEN_TIME);
        assertEquals(true, mHappyButton.isVisible());
    }

    @Test
    public void happyButton_isVisible_false() {
        when(date.getTime()).thenReturn(EVEN_TIME);
        assertNotEquals(false, mHappyButton.isVisible());
    }
}
