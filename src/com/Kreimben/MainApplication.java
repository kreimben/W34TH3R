package com.Kreimben;

public class MainApplication {
    public static void main(String[] argv) {
        var frame = new WTMainViewController();

        var thread = new Thread(frame);

        thread.run();
    }
}
