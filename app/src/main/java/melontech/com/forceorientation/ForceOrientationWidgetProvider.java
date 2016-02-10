package melontech.com.forceorientation;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.view.Surface;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by dpanayotov on 2/10/2016
 */
public class ForceOrientationWidgetProvider extends AppWidgetProvider {

    private static final String ACTION_FORCE_ORIENTATION_CW = "com.melontech.ACTION_FORCE_ORIENTATION_CW";

    private static final String ACTION_FORCE_ORIENTATION_AUTO = "com.melontech.ACTION_FORCE_ORIENTATION_AUTO";

    private static final String ACTION_FORCE_ORIENTATION_CCW = "com.melontech.ACTION_FORCE_ORIENTATION_CCW";

    private static final int MAX_VALUE = 4;

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
                rotate(1, context);
                break;
            case ACTION_FORCE_ORIENTATION_CCW:
                rotate(-1, context);
                break;
            case ACTION_FORCE_ORIENTATION_AUTO:
                resetRotation(context);
                toggleAutoRotation(true, context);
                break;
            default:
                super.onReceive(context, intent);
        }
    }

    private void rotate(int direction, Context context) {
        toggleAutoRotation(false, context);
        int rotation;
        try {
            rotation = getCurrentRotation(context);
        } catch (Settings.SettingNotFoundException e) {
            Toast.makeText(context, R.string.error_no_roation_setting, Toast.LENGTH_LONG).show();
            return;
        }
        rotation += direction;
        //check boundaries [0:3]
        rotation = rotation == 4 ? 0 : rotation;
        rotation = rotation == -1 ? 3 : rotation;
        Log.d("zxc", "" + rotation);
        Settings.System.putInt(context.getContentResolver(), Settings.System.USER_ROTATION, rotation);
    }

    private void resetRotation(Context context) {
        Settings.System.putInt(context.getContentResolver(), Settings.System.USER_ROTATION, Surface.ROTATION_0);

    }

    private void toggleAutoRotation(boolean toggle, Context context) {
        Settings.System.putInt(
                context.getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION,
                toggle ? 1 : 0
        );
    }

    private int getCurrentRotation(Context context) throws Settings.SettingNotFoundException {
        return Settings.System.getInt(context.getContentResolver(), Settings.System.USER_ROTATION);
    }
}
