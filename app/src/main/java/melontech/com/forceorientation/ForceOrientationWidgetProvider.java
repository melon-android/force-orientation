package melontech.com.forceorientation;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by dpanayotov on 2/10/2016
 */
public class ForceOrientationWidgetProvider extends AppWidgetProvider {

    private static final String ACTION_FORCE_ORIENTATION_CW = "com.melontech.ACTION_FORCE_ORIENTATION_CW";

    private static final String ACTION_FORCE_ORIENTATION_AUTO = "com.melontech.ACTION_FORCE_ORIENTATION_AUTO";

    private static final String ACTION_FORCE_ORIENTATION_CCW = "com.melontech.ACTION_FORCE_ORIENTATION_CCW";

    RemoteViews remoteViews;

    private void initRemoteViews(Context context) {
        if (remoteViews == null) {
            PendingIntent pendingIntentCW = PendingIntent
                    .getBroadcast(context, 0, new Intent(ACTION_FORCE_ORIENTATION_CW),
                            PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent pendingIntentAuto = PendingIntent
                    .getBroadcast(context, 0, new Intent(ACTION_FORCE_ORIENTATION_AUTO),
                            PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent pendingIntentCCW = PendingIntent
                    .getBroadcast(context, 0, new Intent(ACTION_FORCE_ORIENTATION_CCW),
                            PendingIntent.FLAG_UPDATE_CURRENT);

            remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            remoteViews.setOnClickPendingIntent(R.id.cw, pendingIntentCW);
            remoteViews.setOnClickPendingIntent(R.id.auto, pendingIntentAuto);
            remoteViews.setOnClickPendingIntent(R.id.ccw, pendingIntentCCW);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        initRemoteViews(context);
        for (int appWidgetId : appWidgetIds) {
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case ACTION_FORCE_ORIENTATION_CW:
                RotationUtil.rotate(1, context);
                break;
            case ACTION_FORCE_ORIENTATION_CCW:
                RotationUtil.rotate(-1, context);
                break;
            case ACTION_FORCE_ORIENTATION_AUTO:
                RotationUtil.resetRotation(context);
                RotationUtil.toggleAutoRotation(true, context);
                break;
            default:
                super.onReceive(context, intent);
        }
    }
}
