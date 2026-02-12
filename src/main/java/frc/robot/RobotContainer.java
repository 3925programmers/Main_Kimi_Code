// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.subsystems.KrakenMotor;
import frc.robot.subsystems.NeoMotor;
//import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
  private boolean m_neoReverseControls = false;
  private boolean m_krakenReverseControls = false;
  private boolean m_controlKraken = false;

  public RobotContainer() {
    SmartDashboard.putBoolean("Neo Reverse Controls", m_neoReverseControls);
    SmartDashboard.putBoolean("Kraken Reverse Controls", m_krakenReverseControls);
    SmartDashboard.putString("Active Motor", "NEO");
    ConfigureBindings();
  }

  private Command spinSelectedMotor() {
    return Commands.runEnd(
      () -> {
        if (m_controlKraken) {
          int krakenDirection = m_krakenReverseControls ? -1 : 1;
          krakenMotor.rotate(krakenDirection);
        } else {
          int neoDirection = m_neoReverseControls ? -1 : 1;
          neoMotor.rotate(neoDirection);
        }
      },
      this::stopBothMotorsNow,
      neoMotor, krakenMotor
    );
  }

  private void stopBothMotorsNow() {
    neoMotor.stop();
    krakenMotor.stop();
  }

  private Command stopSelectedMotor() {
    return Commands.runOnce(this::stopBothMotorsNow, neoMotor, krakenMotor);
  }

  private Command toggleSelectedDirectionMode() {
    return Commands.runOnce(() -> {
      if (m_controlKraken) {
        m_krakenReverseControls = !m_krakenReverseControls;
        SmartDashboard.putBoolean("Kraken Reverse Controls", m_krakenReverseControls);
      } else {
        m_neoReverseControls = !m_neoReverseControls;
        SmartDashboard.putBoolean("Neo Reverse Controls", m_neoReverseControls);
      }
    });
  }

  private Command toggleActiveMotor() {
    return Commands.runOnce(() -> {
      m_controlKraken = !m_controlKraken;
      SmartDashboard.putString("Active Motor", m_controlKraken ? "KRAKEN" : "NEO");
    });
  }

  private void ConfigureBindings() {
    aRun.whileTrue(spinSelectedMotor());
    aStop.onTrue(stopSelectedMotor());
    joystick.x().toggleOnTrue(spinSelectedMotor());
    joystick.y().onTrue(Commands.runOnce(() -> m_latchOn = !m_latchOn));
    joystick.b().onTrue(toggleSelectedDirectionMode());
    joystick.leftBumper().onTrue(toggleActiveMotor());
    magRun.whileTrue(spinSelectedMotor());
    microRun.whileTrue(spinSelectedMotor());
    latchActive.whileTrue(spinSelectedMotor());
    microStop.onTrue(stopSelectedMotor());
    magStop.onTrue(stopSelectedMotor());
   
  }
  
}
