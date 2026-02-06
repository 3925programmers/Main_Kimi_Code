// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.SpinMotor;
import frc.robot.subsystems.NeoMotor;
//import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
//import edu.wpi.first.wpilibj2.command.button.Trigger;


public class RobotContainer {

  private final CommandXboxController joystick = new CommandXboxController(0);
  public final NeoMotor neoMotor = new NeoMotor();

  public RobotContainer() {
    ConfigureBindings();
  }

  private void ConfigureBindings() {
    joystick.a().whileTrue(new SpinMotor(neoMotor));
   
  }
  
  
}
