package workshop.syntaxleiden.nl.ledcontrol.colorpicker;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import java.io.IOException;
import java.net.*;

public class UdpColorListener implements ColorChangeListener {
    final int port;
    final String ip;
    final int count;

    public UdpColorListener(String ip, int port, int count) {
        this.port = port;
        this.ip = ip;
        this.count = count;
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
