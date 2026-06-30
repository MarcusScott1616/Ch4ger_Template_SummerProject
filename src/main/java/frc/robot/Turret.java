package frc.robot;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class Turret {

    private final SparkMax turretMotor = new SparkMax(4, MotorType.kBrushless);

    public Turret() {

    // setting up basic motor configuration
    SparkMaxConfig turretConfig = new SparkMaxConfig();
    
    //Keeping Brake mode active, so turret stops immediawnlty when Buttons are let go.
    turretConfig.idleMode(IdleMode.kBrake).smartCurrentLimit(20);

    turretMotor.configure(
        turretConfig,
        com.revrobotics.spark.SparkBase.ResetMode.kNoResetSafeParameters,
        com.revrobotics.spark.SparkBase.PersistMode.kNoPersistParameters
    );
    }

    public void setPower(double power) {
        turretMotor.set(power);
    }
}

