package models.settings;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

final class Audio {

    private IntegerProperty volume = new SimpleIntegerProperty(50);
    private BooleanProperty mute = new SimpleBooleanProperty(false);

    public Audio(int volumeLevel, boolean isMute) {
        setMute(isMute);
        setVolumeLevel(volumeLevel);
    }

    public int getVolumeLevel() {
        return volume.getValue();
    }

    public void setVolumeLevel(int newVolume) {
        volume.setValue(Math.max(0, Math.min(100, newVolume)));
    }

    public void setMute(boolean isMute) {
        mute.set(isMute);
    }

    public boolean isMuted() {
        return mute.getValue();
    }
}
