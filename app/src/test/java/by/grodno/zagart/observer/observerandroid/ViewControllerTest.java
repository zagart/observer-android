package by.grodno.zagart.observer.observerandroid;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import by.grodno.zagart.observer.observerandroid.classes.ViewController;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ViewControllerTest {

    private static final long EVEN_TIME = 13L;
    private static final long ODD_TIME = 100500L;
    @Mock
    Date date;
    @Mock
    View view;
    private ViewController<View> mViewController;

    @Before
    public void init() {
        mViewController = new ViewController<>(view, date);
    }

    @Test
    public void viewController_isViewVisible_true() {
        when(date.getTime()).thenReturn(ODD_TIME);
        assertEquals(true, mViewController.isViewVisible());
    }

    @Test
    public void viewController_isViewVisible_false() {
        when(date.getTime()).thenReturn(EVEN_TIME);
        assertEquals(false, mViewController.isViewVisible());
    }
}
