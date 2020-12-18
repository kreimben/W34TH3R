package com.Kreimben;

import java.io.*;

public class WTIOManager {

    private static WTIOManager instance;

    public static WTIOManager getInstance() {
        if (instance == null) instance = new WTIOManager();
        return instance;
    }

    private WTIOManager() { }

    public String getSavedCityName() {
        String result = null;

        try {
            var file = new File("./conf.txt");
            var r = new FileReader(file);

            var br = new BufferedReader(r);

            result = br.readLine();

            br.close();
            r.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Configuration file is not found.");

            e.getStackTrace();
        }
        catch (IOException e) { e.getStackTrace(); }

        return result;
    }

    public void makeConfigurationFile(String cityName) {
        try {
            var stream = new FileWriter("./conf.txt");
            if (cityName != null) stream.write(cityName);
            stream.close();
        }
        catch (IOException e) { e.getStackTrace(); }
    }

}
