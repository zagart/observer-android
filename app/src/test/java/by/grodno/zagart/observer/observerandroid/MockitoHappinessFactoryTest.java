package by.grodno.zagart.observer.observerandroid;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import by.grodno.zagart.observer.observerandroid.classes.HappinessFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MockitoHappinessFactoryTest {

    private static final long EVEN_TIME = 13L;
    private static final long ODD_TIME = 100500L;
    private static final int POSITIVE_INT = 10;
    @Mock
    Date date;
    private HappinessFactory<TextView> mFactory;
    private Activity mActivity;

    @Test
    public void happinessFactory_happinessRemaining_returnsPositiveInt() {
        final int happinessRemaining = HappinessFactory.happinessRemaining(mActivity);
        assertTrue(happinessRemaining >= 0);
    }

    @Test
    public void happinessFactory_isHappyTime_returnsFalseWhenEvenTime() {
        when(date.getTime()).thenReturn(EVEN_TIME);
        final boolean happyTime = mFactory.isHappyTime();
        assertEquals(false, happyTime);
    }

    @Test
    public void happinessFactory_isHappyTime_returnsTrueWhenOddTime() {
        when(date.getTime()).thenReturn(ODD_TIME);
        final boolean happyTime = mFactory.isHappyTime();
        assertEquals(true, happyTime);
    }

    @Before
    public void init() {
        mActivity = Mockito.spy(new AppCompatActivity());
        final Intent intent = Mockito.spy(new Intent());
        when(mActivity.getIntent()).thenReturn(intent);
        when(intent.getIntExtra(HappinessFactory.HAPPINESS_COUNTER, 0)).thenReturn(POSITIVE_INT);
        mFactory = new HappinessFactory<>(date);
    }
}
