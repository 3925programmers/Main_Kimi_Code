// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.SpinMotor;
import frc.robot.subsystems.NeoMotor;
//import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.Trigger;


public class RobotContainer {

  private final CommandXboxController joystick = new CommandXboxController(0);
  public final NeoMotor neoMotor = new NeoMotor();
  private final DigitalInput magSwitch = new DigitalInput(0);
  private final Trigger magTriggered = new Trigger(() -> !magSwitch.get());
  private final DigitalInput microSwitch = new DigitalInput(3);
  private final Trigger microPressed = new Trigger(() -> microSwitch.get());
  private boolean m_latchOn = false;
  private final Trigger latchActive = new Trigger(() -> m_latchOn && !microSwitch.get() && magSwitch.get());
  private final Trigger microStop = new Trigger(() -> m_latchOn && microSwitch.get());
  private final Trigger magStop = new Trigger(() -> m_latchOn && !magSwitch.get());

  public RobotContainer() {
    ConfigureBindings();
  }

  private void ConfigureBindings() {
    joystick.a().whileTrue(new SpinMotor(neoMotor));
    joystick.x().toggleOnTrue(new SpinMotor(neoMotor));
    joystick.y().onTrue(Commands.runOnce(() -> m_latchOn = !m_latchOn));
    joystick.b().onTrue(Commands.runOnce(neoMotor::toggleInverted, neoMotor));
    magTriggered.whileTrue(new SpinMotor(neoMotor));
    microPressed.whileTrue(new SpinMotor(neoMotor));
    latchActive.whileTrue(new SpinMotor(neoMotor));
    microStop.onTrue(Commands.runOnce(neoMotor::stop, neoMotor));
    magStop.onTrue(Commands.runOnce(neoMotor::stop, neoMotor));
   
  }
  
}
