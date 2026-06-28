// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard; // For the controls display


/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */

  private final PWMVictorSPX leftMotor1 = new PWMVictorSPX(0); 
  private final PWMVictorSPX leftMotor2 = new PWMVictorSPX(2); 
  private final PWMVictorSPX rightMotor1 = new PWMVictorSPX(1);
  private final PWMVictorSPX rightMotor2 = new PWMVictorSPX(3);
  
  private final XboxController Controller = new XboxController(0);
  private final SparkMax turretMotor1 = new SparkMax(4, MotorType.kBrushless);
  private final Compressor compressor = new Compressor(1, PneumaticsModuleType.CTREPCM);
  private final Solenoid firePiston = new Solenoid(1, PneumaticsModuleType.CTREPCM, 0);
  private final DifferentialDrive m_drive = new DifferentialDrive(leftMotor1, rightMotor1);
 
  @Override
  public void robotInit(){

    SmartDashboard.putString("---CH4GER CONTROLS---","READY TO LAUNCH");
    SmartDashboard.putString("Drive Control", "Left Stick Y (Speed) | Right Stick X (Turning)");
    SmartDashboard.putString("Turret Spin Counter-Clock wise", "Hold [ x ] Button");
    SmartDashboard.putString("Turret Spin CW", "Hold [ B ] Button");
    SmartDashboard.putString("Turret Fine Tuning", "Hold [ Left Bumper]");
    SmartDashboard.putString("FIRE CANNON", "Hold [ Right Bumper]");
    SmartDashboard.putString("Compressor ON"," Press Left Joy Stick Button");
    SmartDashboard.putString("Compressor OFF", "Press Right Joy stick Button");

    // setting limits to the Turret Motor (what the limits are)
      SparkMaxConfig turretConfig = new SparkMaxConfig();
      turretConfig.idleMode(IdleMode.kBrake)
      .smartCurrentLimit(20);

    // limits how far the motor can turn in each direction (10 times.) 
      // MUST TURN TURRET TO THE FRONT OF THE ROBOT EACH TIME IT IS USED.

      turretConfig.softLimit
    
      .forwardSoftLimitEnabled(true)
      .forwardSoftLimit(10.0)
      .reverseSoftLimitEnabled(true)
      .reverseSoftLimit(-10);
    // officially applies the limits to the motor
      turretMotor1.configure(turretConfig, com.revrobotics.spark.SparkBase.ResetMode.kNoResetSafeParameters, com.revrobotics.spark.SparkBase.PersistMode.kNoPersistParameters);

}

@Override
public void robotPeriodic(){
  //tracks Compressor- 50 times/ second
  boolean isCompressorRunning = compressor.isEnabled();
  SmartDashboard.putBoolean("Compressor active", isCompressorRunning);
}

  @Override
  public void teleopInit() {
// this code runs once when the robot is turned on after autonomous, TELEOP= human control. This robot doesn't use an autonomous, because a T-Shirt 
// cannon robot does not need an autonomous....
// Nothing to init, everything was initiallized already during robot init



}
  

  @Override
  public void teleopPeriodic() {
      SmartDashboard.putString("is this working?","Probably?");
    // Periodic code is run many times per second, IDK the number but its pretty insane. It will go untill the robot is disabled
    // These variables get data from the controller joysticks, and create values. It also slows it down-
    // 80 percent speed for moving forward, 30 percent speed while turning.
    // NOTE TO SELF CHECK THE AXIS ON JOYSTICK- dont want the wrong axis doing the wrong thing.
    double speed = -Controller.getRawAxis(1) * 0.8;
    double turn = Controller.getRawAxis(4) * 0.3;
// Variable for turret power. Set to 0, because if not, it will always continue running whatever the last command was...
    double Turretpower= 0.0;


/* if Robot is turning Right: left side will go forward, right side will go in reverse. Therfore, we need a way to sometimes
make one side go in reverse relative to the other. I did this through turn. If turn=0, both go straight.
If turn =1, left side spins forward, right side spins backward. If turn=-1, Left side goes backward, right side goes forward.
*/ 
   m_drive.arcadeDrive(speed, turn);
    leftMotor2.set(leftMotor1.get());
    rightMotor2.set(rightMotor1.get());
//Back to turret stuff:

//Check Which buttons we want. X is opposite of Y I think.IDK. 
if( Controller.getYButton()){
  Turretpower = -0.4; // spin counterclockwise, 40% speed
} else if( Controller.getXButton()){
  Turretpower = 0.4; // spin clockwise, 40% speed
}

if( Controller.getLeftBumper()){
  Turretpower = Turretpower*0.25; //For fine tuning, 1/4 of the power sent to the motor. Needs to be pressed while controlling....
}
turretMotor1.set(Turretpower); //actually output power to the motor equal to what we are doing.

// fires solenoid- cannon
if (Controller.getRightBumper()){
  firePiston.set(true); // hits fire on T-Shirt cannon when pressed
} else {
  firePiston.set(false); //retracts piston
}
// Compressor Manual Control- JOYSTICK CLIKIES :) FOR DAVID
if (Controller.getLeftStickButton()) {
  compressor.enableDigital(); // Click Left stick Turn On compressor
} else if (Controller.getRightStickButton()){
  compressor.disable(); //Click RIght Stick to turn OFF
}

  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
