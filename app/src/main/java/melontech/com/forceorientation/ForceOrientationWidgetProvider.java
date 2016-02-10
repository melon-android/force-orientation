package melontech.com.forceorientation;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by dpanayotov on 2/10/2016
 */
public class ForceOrientationWidgetProvider extends AppWidgetProvider {

    private static final String ACTION_FORCE_ORIENTATION_CW = "com.melontech.ACTION_FORCE_ORIENTATION_CW";
    private static final String ACTION_FORCE_ORIENTATION_AUTO = "com.melontech.ACTION_FORCE_ORIENTATION_AUTO";
    private static final String ACTION_FORCE_ORIENTATION_CCW = "com.melontech.ACTION_FORCE_ORIENTATION_CCW";

    RemoteViews remoteViews;

    private void initRemoteViews(Context context){
        if(remoteViews == null){
            PendingIntent pendingIntentCW = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_FORCE_ORIENTATION_CW), PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent pendingIntentAuto = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_FORCE_ORIENTATION_AUTO), PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent pendingIntentCCW = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_FORCE_ORIENTATION_CCW), PendingIntent.FLAG_UPDATE_CURRENT);

            remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            remoteViews.setOnClickPendingIntent(R.id.cw, pendingIntentCW);
            remoteViews.setOnClickPendingIntent(R.id.auto, pendingIntentAuto);
            remoteViews.setOnClickPendingIntent(R.id.ccw, pendingIntentCCW);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        initRemoteViews(context);
        for(int appWidgetId : appWidgetIds){
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_FORCE_ORIENTATION_CW.equals(intent.getAction()) ||
                ACTION_FORCE_ORIENTATION_AUTO.equals(intent.getAction()) ||
                ACTION_FORCE_ORIENTATION_CCW.equals(intent.getAction())) {
            Toast.makeText(context, intent.getAction(), Toast.LENGTH_SHORT).show();
        }
        super.onReceive(context, intent);
    }
}
