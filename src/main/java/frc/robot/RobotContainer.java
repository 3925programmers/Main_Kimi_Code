// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.SpinMotor;
import frc.robot.subsystems.KrakenMotor;
import frc.robot.subsystems.NeoMotor;
//import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.Trigger;


public class RobotContainer {

  private final CommandXboxController joystick = new CommandXboxController(0);
  public final NeoMotor neoMotor = new NeoMotor();
  public final KrakenMotor krakenMotor = new KrakenMotor();
  private final DigitalInput magSwitch = new DigitalInput(0);
  private final Trigger magTriggered = new Trigger(() -> !magSwitch.get());
  private final DigitalInput microSwitch = new DigitalInput(3);
  private final Trigger microPressed = new Trigger(() -> microSwitch.get());
  private boolean m_latchOn = false;
  private final Trigger latchActive = new Trigger(() ->
      m_latchOn && !microSwitch.get() && magSwitch.get() && !joystick.a().getAsBoolean());
  private final Trigger microRun = new Trigger(() -> !m_latchOn && microSwitch.get());
  private final Trigger magRun = new Trigger(() -> !m_latchOn && !magSwitch.get());
  private final Trigger microStop = new Trigger(() -> m_latchOn && microSwitch.get());
  private final Trigger magStop = new Trigger(() -> m_latchOn && !magSwitch.get());
  private final Trigger aRun = new Trigger(() -> !m_latchOn && joystick.a().getAsBoolean());
  private final Trigger aStop = new Trigger(() -> m_latchOn && joystick.a().getAsBoolean());

  public RobotContainer() {
    ConfigureBindings();
  }

  private Command spinBothMotors() {
    return Commands.parallel(new SpinMotor(neoMotor), new SpinMotor(krakenMotor));
  }

  private Command stopBothMotors() {
    return Commands.runOnce(() -> {
      neoMotor.stop();
      krakenMotor.stop();
    }, neoMotor, krakenMotor);
  }

  private Command toggleBothInverted() {
    return Commands.runOnce(() -> {
      neoMotor.toggleInverted();
      krakenMotor.toggleInverted();
    }, neoMotor, krakenMotor);
  }

  private void ConfigureBindings() {
    aRun.whileTrue(spinBothMotors());
    aStop.onTrue(stopBothMotors());
    joystick.x().toggleOnTrue(spinBothMotors());
    joystick.y().onTrue(Commands.runOnce(() -> m_latchOn = !m_latchOn));
    joystick.b().onTrue(toggleBothInverted());
    magRun.whileTrue(spinBothMotors());
    microRun.whileTrue(spinBothMotors());
    latchActive.whileTrue(spinBothMotors());
    microStop.onTrue(stopBothMotors());
    magStop.onTrue(stopBothMotors());
   
  }
  
}
