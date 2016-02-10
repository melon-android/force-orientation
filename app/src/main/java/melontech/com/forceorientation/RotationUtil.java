package melontech.com.forceorientation;

import android.content.Context;
import android.provider.Settings;
import android.view.Surface;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dpanayotov on 2/10/2016
 */
public class RotationUtil {

    private static final Map<Integer, String> DIRECTIONS = new HashMap<>();

    static {
        DIRECTIONS.put(0, "/\\");
        DIRECTIONS.put(1, ">");
        DIRECTIONS.put(2, "\\/");
        DIRECTIONS.put(3, "<");
    }

    public static void rotate(int direction, Context context) {
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
        makeRotationToast(rotation, context);
        Settings.System.putInt(context.getContentResolver(), Settings.System.USER_ROTATION, rotation);
    }

    public static void resetRotation(Context context) {
        Toast.makeText(context, "O", Toast.LENGTH_SHORT).show();
        Settings.System.putInt(context.getContentResolver(), Settings.System.USER_ROTATION, Surface.ROTATION_0);
    }

    public static void toggleAutoRotation(boolean toggle, Context context) {
        Settings.System.putInt(
                context.getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION,
                toggle ? 1 : 0
        );
    }

    public static int getCurrentRotation(Context context) throws Settings.SettingNotFoundException {
        return Settings.System.getInt(context.getContentResolver(), Settings.System.USER_ROTATION);
    }

    public static void makeRotationToast(int roation, Context context) {
        Toast.makeText(context, DIRECTIONS.get(roation), Toast.LENGTH_SHORT).show();
    }
}
