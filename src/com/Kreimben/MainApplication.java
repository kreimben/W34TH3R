package com.Kreimben;

public class MainApplication {
    public static void main(String[] argv) {
        var frame = new WTFrameController();

        var thread = new Thread(frame);

        thread.run();
    }
}
