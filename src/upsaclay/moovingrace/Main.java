package upsaclay.moovingrace;

import upsaclay.moovingrace.utils.*;

public class Main {

    public static void main(String[] args) {
        Map map = new Map("classic");
        map.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.NORTH, 0, 0));
        map.getTracks().add(new Track(TrackType.TRACK_CLASSIC, TrackRotation.EAST, 1, 0));
        map.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.WEST, 2, 0));
        map.getTracks().add(new Track(TrackType.TRACK_CLASSIC, TrackRotation.SOUTH, 2, 1));
        map.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.SOUTH, 2, 2));
        map.getTracks().add(new Track(TrackType.TRACK_CLASSIC, TrackRotation.EAST, 1, 2));
        map.getTracks().add(new Track(TrackType.TRACK_SHIFT, TrackRotation.EAST, 0, 2));
        map.getTracks().add(new Track(TrackType.TRACK_CLASSIC, TrackRotation.SOUTH, 0, 1));
        MapManager.getInstance().getMaps().add(map);
        MoovingRaceWindow test = new MoovingRaceWindow();
        test.setVisible(true);
    }
}
