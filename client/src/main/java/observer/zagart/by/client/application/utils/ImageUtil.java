package observer.zagart.by.client.application.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Utility class for working with images.
 */
public class ImageUtil {

    public static Bitmap getResizedBitmap(final Bitmap pBitmap,
                                          final int pNewWidth,
                                          final int pNewHeight) {
        final int width = pBitmap.getWidth();
        final int height = pBitmap.getHeight();
        final float scaleWidth = ((float) pNewWidth) / width;
        final float scaleHeight = ((float) pNewHeight) / height;
        final Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(pBitmap, 0, 0, width, height, matrix, false);
    }
}
