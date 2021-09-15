package upsaclay.moovingrace.components;

import upsaclay.moovingrace.utils.TrackRotation;
import upsaclay.moovingrace.utils.TrackType;

import javax.swing.*;

public class TrackTile extends JComponent {
    private TrackType type;
    private TrackRotation rotation;

    public TrackTile(TrackType type, TrackRotation rotation) {
        this.type = type;
        this.rotation = rotation;

    }
}
