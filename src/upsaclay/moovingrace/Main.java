package upsaclay.moovingrace;

import upsaclay.moovingrace.utils.*;

public class Main {


    public static void main(String[] args) {
        Map simpleMax = new Map("simple", 64, 3);
        simpleMax.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.NORTH, 0, 0));
        simpleMax.getTracks().add(new Track(TrackType.TRACK_START, TrackRotation.EAST, 1, 0));
        simpleMax.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.WEST, 2, 0));
        simpleMax.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.SOUTH, 2, 1));
        simpleMax.getTracks().add(new Track(TrackType.TRACK_CLASSIC, TrackRotation.EAST, 1, 1));
        simpleMax.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.EAST, 0, 1));

        MapManager.getInstance().getMaps().add(simpleMax);
        Map complexMap = new Map("complex", 64, 3);
        complexMap.getTracks().add(new Track(TrackType.TRACK_START, TrackRotation.EAST, 1,0));
        complexMap.getTracks().add(new Track(TrackType.TRACK_CLASSIC, TrackRotation.EAST, 2,0));
        complexMap.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.WEST, 3,0));
        complexMap.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.EAST, 3,1));
        complexMap.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.WEST, 4,1));
        complexMap.getTracks().add(new Track(TrackType.TRACK_CLASSIC, TrackRotation.SOUTH, 4,2));
        complexMap.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.SOUTH, 4,3));
        complexMap.getTracks().add(new Track(TrackType.TRACK_CLASSIC, TrackRotation.WEST, 3,3));
        complexMap.getTracks().add(new Track(TrackType.TRACK_CLASSIC, TrackRotation.WEST, 2,3));
        complexMap.getTracks().add(new Track(TrackType.TRACK_CLASSIC, TrackRotation.WEST, 1,3));
        complexMap.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.EAST, 0,3));
        complexMap.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.NORTH, 0,2));
        complexMap.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.SOUTH, 1,2));
        complexMap.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.WEST, 1,1));
        complexMap.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.EAST, 0,1));
        complexMap.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.NORTH, 0,0));
        MapManager.getInstance().getMaps().add(complexMap);
        Map map = new Map("classic", 64, 3);
        map.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.NORTH, 0, 0));
        map.getTracks().add(new Track(TrackType.TRACK_START, TrackRotation.EAST, 1, 0));
        map.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.WEST, 2, 0));
        map.getTracks().add(new Track(TrackType.TRACK_CLASSIC, TrackRotation.SOUTH, 2, 1));
        map.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.SOUTH, 2, 2));
        map.getTracks().add(new Track(TrackType.TRACK_END, TrackRotation.EAST, 1, 2));
        map.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.EAST, 0, 2));
        map.getTracks().add(new Track(TrackType.TRACK_CLASSIC, TrackRotation.SOUTH, 0, 1));
        MapManager.getInstance().getMaps().add(map);
        MoovingRaceWindow test = new MoovingRaceWindow();
        test.setVisible(true);
    }
}
