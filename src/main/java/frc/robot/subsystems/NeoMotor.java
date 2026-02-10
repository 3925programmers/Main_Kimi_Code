// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;
//import com.revrobotics.spark.SparkBase.PersistMode;
//import com.revrobotics.spark.SparkBase.ResetMode;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class NeoMotor extends SubsystemBase {
  public SparkMax mainMotor = new SparkMax(Constants.NeoMotorConstants.MAIN_ID, MotorType.kBrushless);

    //@SuppressWarnings("removal")
    public void Spin() {
      SparkMaxConfig rotateConfig = new SparkMaxConfig();
        rotateConfig.idleMode(Constants.NeoMotorConstants.IDLE_MODE);
        rotateConfig.inverted(Constants.NeoMotorConstants.INVERTED);
        rotateConfig.smartCurrentLimit(Constants.NeoMotorConstants.CURRENT_LIMIT);

      //mainMotor.configure(rotateConfig, ResetMode.kResetSafeParameters , PersistMode.kPersistParameters);
    }
    //Parameters: direction is -1, 1
    public void rotate(int direction) {
      int invertedSign = Constants.NeoMotorConstants.INVERTED ? -1 : 1;
      mainMotor.set(0.75 * direction * invertedSign);

      SmartDashboard.putBoolean("Spinning", true);
    }

    public void stop() {
      mainMotor.set(0);

      SmartDashboard.putBoolean("Spinning", false);
    }

    public void toggleInverted() {
      Constants.NeoMotorConstants.INVERTED = !Constants.NeoMotorConstants.INVERTED;
      SmartDashboard.putBoolean("NeoMotor Inverted", Constants.NeoMotorConstants.INVERTED);
    }

    @Override
    public void periodic() {
      SmartDashboard.putNumber("Spin Output", mainMotor.getAppliedOutput());
      SmartDashboard.putNumber("SpinEncoder", mainMotor.getEncoder().getPosition());
    }
}
