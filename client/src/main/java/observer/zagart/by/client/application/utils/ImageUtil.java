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
        int width = pBitmap.getWidth();
        int height = pBitmap.getHeight();
        float scaleWidth = ((float) pNewWidth) / width;
        float scaleHeight = ((float) pNewHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(pBitmap, 0, 0, width, height, matrix, false);
    }
}
