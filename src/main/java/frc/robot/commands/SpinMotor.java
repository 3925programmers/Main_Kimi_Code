// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.IntSupplier;

import edu.wpi.first.wpilibj2.command.Command;

public class SpinMotor extends Command {
    private final neoMotor motor;
    private final IntSupplier directionSupplier;

    public SpinMotor(neoMotor motor) {
        this(motor, () -> 1);
    }

    public SpinMotor(neoMotor motor, int direction) {
        this(motor, () -> direction);
    }

    public SpinMotor(neoMotor motor, IntSupplier directionSupplier) {
        this.motor = motor;
        this.directionSupplier = directionSupplier;
        addRequirements(this.motor);
    }

    @Override
    public void execute() {
        motor.rotate(directionSupplier.getAsInt());
    }

    @Override
    public void end(boolean interrupted) {
        motor.stop();
    }
}
