package WaveManager.data;

public class WaveCounter {
    private int waveCounter;

    public int getWaveCounter() {
        return waveCounter;
    }

    public void setWaveCounter(int waveCounter) {
        this.waveCounter = waveCounter;
    }

    public void incrementWaveCounter(int waveCounter){
        this.waveCounter = waveCounter + 1;
    }

    public void decrementWaveCounter(int waveCounter){
        this.waveCounter = waveCounter - 1;
    }

}
