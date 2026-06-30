package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class Shooter {
    


    private final Compressor compressor = new Compressor(1, PneumaticsModuleType.CTREPCM);
    private final Solenoid firePiston = new Solenoid(1, PneumaticsModuleType.CTREPCM, 0);

    public void setFiring(boolean fire) {
        firePiston.set(fire);
    }

    public void startCompressor(){
        compressor.enableDigital();
    }

    public void stopCompressor() {
        compressor.disable();
    }

    public boolean isCompressorRunning() {
        return compressor.isEnabled();
    }



}
