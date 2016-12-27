package observer.zagart.by.client.application.interfaces;

import android.view.View;

/**
 * Implementations of this interface is able to draw pictures/images
 * at specified canvas.
 *
 * @author zagart
 */
public interface IDrawable<Canvas extends View, Source> {

    void draw(final Canvas pCanvas, final Source pSource, final boolean... pResize);
}
