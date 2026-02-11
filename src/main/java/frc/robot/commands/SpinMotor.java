// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

public class SpinMotor extends Command {
    private final neoMotor motor;

    public SpinMotor(neoMotor motor) {
        this.motor = motor;
        addRequirements(this.motor);
    }

    @Override
    public void execute() {
        motor.rotate(1);
    }

    @Override
    public void end(boolean interrupted) {
        motor.stop();
    }
}
