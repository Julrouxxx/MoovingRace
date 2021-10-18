package upsaclay.moovingrace;

import upsaclay.moovingrace.utils.*;

public class Main {


    public static void main(String[] args) {

        MoovingRaceWindow test = new MoovingRaceWindow();
        test.setVisible(true);
    }

    public static String getWorkingDirectory(){
        String workingDirectory;
        String OS = (System.getProperty("os.name")).toUpperCase();
        if (OS.contains("WIN"))
        {
            workingDirectory = System.getenv("AppData") + "/MoovingRace/";
        }
        else
        {
            workingDirectory = System.getProperty("user.home");
            workingDirectory += "/Library/Application Support/MoovingRace/";
        }
        return workingDirectory;
    }
}
