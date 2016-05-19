package workshop.syntaxleiden.nl.ledcontrol.colorpicker;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.net.*;

import workshop.syntaxleiden.nl.ledcontrol.ColorPickerActivity;
import workshop.syntaxleiden.nl.ledcontrol.R;

public class UdpColorListener implements ColorChangeListener {
    final int port;
    String ip;
    int count;
    final ColorPickerActivity view;

    public UdpColorListener(String ip, int port, int count, ColorPickerActivity view) {
        this.port = port;
        this.ip = ip;
        this.count = count;
        this.view = view;
    }

    @Override
    public void colorDidChange(int color) {
        byte red = (byte) (Color.red(color)),
             green = (byte) (Color.green(color) ),
             blue = (byte) (Color.blue(color) );

        byte[] message = new byte[count * 3];

        for(int i = 0; i < message.length; i += 3) {
            message[i] = green;
            message[i+1] = red;
            message[i+2] = blue;
        }

        Log.v("Color changed!", "" + Color.red(color) + ", " + Color.red(green) + ", " + Color.red(blue));

        EditText ipField = (EditText) view.findViewById(R.id.ipTextView);
        EditText ledField = (EditText) view.findViewById(R.id.ledTextView);
        this.ip = ipField.getText().toString();
        this.count = Integer.parseInt(ledField.getText().toString());

        Log.d("leds", ledField.getText().toString());

        AsyncTask<byte[], Integer, Void> task = new SendColorPacketTask();
        if (Build.VERSION.SDK_INT >= 11)
            task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, message);
        else
            task.execute(message);
    }

    private class SendColorPacketTask extends AsyncTask<byte[], Integer, Void> {
        @Override
        protected Void doInBackground(byte[]... params) {
            try {
               byte[] message = params[0];

                DatagramSocket socket = new DatagramSocket();
                InetAddress address = InetAddress.getByName(ip);

                DatagramPacket p = new DatagramPacket(message, message.length, address, port);
                socket.send(p);
            } catch (SocketException e) {
                e.printStackTrace(); // FIXME
            } catch (UnknownHostException e) {
                e.printStackTrace(); // FIXME
            } catch (IOException e) {
                e.printStackTrace(); // FIXME
            }
            return null;
        }
    }
}
