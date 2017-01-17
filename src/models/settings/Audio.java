package models.settings;

final class Audio {
    
    private int volume;
    
    public Audio() {
        this.volume = 50;
    }
    
    public int getVolumeLevel() {
        return this.volume;
    }
    
    public void setVolumeLevel(int newVolume) {
        this.volume = Math.max(0, Math.min(100, newVolume));
    }
    
    public void mute() {
        this.volume = -1;
    }
    
    public boolean isMuted() {
        return this.volume == -1;
    }
}
