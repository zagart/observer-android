package by.grodno.zagart.observer.observerandroid.interfaces;
import android.view.View;

/**
 * Implementations of this interface is able to draw pictures/images
 * at specified canvas.
 *
 * @author zagart
 */
public interface IDrawable<Canvas extends View, Source> {
    void draw(Canvas pCanvas, Source pSource);
}
