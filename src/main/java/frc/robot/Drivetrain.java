package frc.robot;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drivetrain {

    private final PWMVictorSPX leftMotor1= new PWMVictorSPX(0);
    private final PWMVictorSPX leftMotor2= new PWMVictorSPX(2);
    private final PWMVictorSPX rightMotor1= new PWMVictorSPX(1);
    private final PWMVictorSPX rightMotor2= new PWMVictorSPX(3);
    private final DifferentialDrive m_drive = new DifferentialDrive( leftMotor1,rightMotor1);
    
    public Drivetrain() {
        //invert right side main motor so it drives straight
        rightMotor1.setInverted(true);
    }

    public void drive(double speed, double turn) {
        m_drive.arcadeDrive(speed,turn);

        leftMotor2.set(leftMotor1.get());
        rightMotor2.set(rightMotor1.get());
    }
}
