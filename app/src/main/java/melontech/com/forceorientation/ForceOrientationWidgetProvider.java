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

    private static PendingIntent pendingIntentCW;
    private static PendingIntent pendingIntentAuto;
    private static PendingIntent pendingIntentCCW;

    private void initPendingIntents(Context context){
        if(pendingIntentCW ==null){
            Intent intent = new Intent();
            intent.setAction(ACTION_FORCE_ORIENTATION_AUTO);
            pendingIntentCW = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_FORCE_ORIENTATION_CW), PendingIntent.FLAG_UPDATE_CURRENT);
            pendingIntentAuto = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            pendingIntentCCW = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_FORCE_ORIENTATION_CCW), PendingIntent.FLAG_UPDATE_CURRENT);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        initPendingIntents(context);
        for(int appWidgetId : appWidgetIds){
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            views.setOnClickPendingIntent(R.id.cw, pendingIntentCW);
            views.setOnClickPendingIntent(R.id.auto, pendingIntentAuto);
            views.setOnClickPendingIntent(R.id.ccw, pendingIntentCCW);
            appWidgetManager.updateAppWidget(appWidgetId, views);
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
