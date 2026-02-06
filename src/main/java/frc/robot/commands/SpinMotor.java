// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.NeoMotor;
import edu.wpi.first.wpilibj2.command.Command;

public class SpinMotor extends Command implements neoMotor{
    private NeoMotor m_NeoMotor;

    public SpinMotor(NeoMotor neoMotor) {
        m_NeoMotor = neoMotor;
        addRequirements(m_NeoMotor);
    }

    // public void SpinForwards(NeoMotor neoMotor) {

    // }

    @Override
    public void execute() {
        m_NeoMotor.rotate(1);
    }

    @Override
    public void end(boolean interrupted) {
        m_NeoMotor.stop();
    }
}