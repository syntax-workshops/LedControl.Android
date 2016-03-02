package workshop.syntaxleiden.nl.ledcontrol.colorpicker;

import android.graphics.Color;
import android.util.Log;

public class PrintColorListener implements ColorChangeListener {
    @Override
    public void colorDidChange(int color) {
        int red = Color.red(color),
            green = Color.green(color),
            blue = Color.blue(color);

        Log.v("Color changed!", "" + red + ", " + green + ", " + blue);
    }
}
