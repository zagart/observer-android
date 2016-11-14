package observer.zagart.by.client.utils;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

/**
 * @author zagart
 */
public class BroadcastUtil {
    public static void sendBroadcast(Context pContext, String pExtra, String pMessage) {
        Intent intent = new Intent(pExtra);
        intent.putExtra(pExtra, pMessage);
        LocalBroadcastManager.getInstance(pContext).sendBroadcast(intent);
    }
}
