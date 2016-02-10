package melontech.com.forceorientation;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Surface;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    boolean isAuto = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.System.putInt(
                        getContentResolver(),
                        Settings.System.ACCELEROMETER_ROTATION,
                        isAuto ? 0 : 1
                );

                Settings.System.putInt(
                        getContentResolver(),
                        Settings.System.USER_ROTATION,
                        isAuto ? Surface.ROTATION_90 : Surface.ROTATION_0//Or a different ROTATION_ constant
                );

                isAuto = !isAuto;
            }
        });
    }
}
