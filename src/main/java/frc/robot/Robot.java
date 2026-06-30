// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
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

 private final Drivetrain m_drivetrain = new Drivetrain();
 private final Turret m_turret = new Turret();
 private final Shooter m_shooter = new Shooter();
// Xbox controller on port 0
 private final XboxController m_controller = new XboxController(0);

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
  }

    

@Override
public void robotPeriodic(){
  //tracks Compressor- 50 times/ second

  SmartDashboard.putBoolean("Compressor active",m_shooter.isCompressorRunning());
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

    // DRIVETRAIN
    // sets the joysticks to turn and speed
    double speed = -m_controller.getRawAxis(1) * 0.8;
    double turn = m_controller.getRawAxis(4) * 0.3;
    m_drivetrain.drive(speed,turn);


/* if Robot is turning Right: left side will go forward, right side will go in reverse. Therfore, we need a way to sometimes
make one side go in reverse relative to the other. I did this through turn. If turn=0, both go straight.
If turn =1, left side spins forward, right side spins backward. If turn=-1, Left side goes backward, right side goes forward.
*/ 
  
//TURRET CONTROL
//Default Turret power set to 0- doesnt spin unless told to do so

//Check Which buttons we want. X is opposite of Y I think.IDK. 
double turretPower = 0.0;
if (m_controller.getYButton()) {
  turretPower = -0.4; // Y Button is Counter Clockwise (Might be reversed, I cant remember if the motor is upside down)
} else if (m_controller.getXButton()) {
  turretPower = 0.4; // X Button is Clockwise (Might be reversed, I cant remember if the motor is upside down)
}
//precision mode- allows for fine tuning. 
if(m_controller.getLeftBumper()) {
  turretPower *= 0.25;
}

m_turret.setPower(turretPower);

//PNEUMATICS CONTROL
// fires solenoid- cannon
m_shooter.setFiring(m_controller.getRightBumper());

if (m_controller.getLeftStickButton()) {
  m_shooter.startCompressor();
} else if (m_controller.getRightStickButton()){
  m_shooter.stopCompressor();
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
