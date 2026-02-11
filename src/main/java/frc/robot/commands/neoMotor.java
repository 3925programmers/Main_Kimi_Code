package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Subsystem;

public interface neoMotor extends Subsystem {
    void rotate(int direction);
    void stop();
    void toggleInverted();
}
