package workshop.syntaxleiden.nl.ledcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import workshop.syntaxleiden.nl.ledcontrol.colorpicker.ColorPicker;
import workshop.syntaxleiden.nl.ledcontrol.colorpicker.UdpColorListener;

public class ColorPickerActivity extends AppCompatActivity {

    private ColorPicker colorPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);

        colorPicker = (ColorPicker) findViewById(R.id.colorPicker);
        colorPicker.onColorChangeListener = new UdpColorListener("192.168.1.154", 80, 70);
    }
}
