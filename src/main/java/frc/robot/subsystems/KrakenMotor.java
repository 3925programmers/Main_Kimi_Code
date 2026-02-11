package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.neoMotor;

public class KrakenMotor extends SubsystemBase implements neoMotor {
    private final TalonFX mainMotor = new TalonFX(Constants.KrakenMotorConstants.MAIN_ID);
    private final DutyCycleOut dutyCycleRequest = new DutyCycleOut(0);

    public KrakenMotor() {
        applyConfig();
        SmartDashboard.putBoolean("Kraken Inverted", Constants.KrakenMotorConstants.INVERTED);
    }

    private void applyConfig() {
        TalonFXConfiguration config = new TalonFXConfiguration();
        config.MotorOutput.NeutralMode = Constants.KrakenMotorConstants.NEUTRAL_MODE;
        config.MotorOutput.Inverted = Constants.KrakenMotorConstants.INVERTED
                ? InvertedValue.Clockwise_Positive
                : InvertedValue.CounterClockwise_Positive;
        config.CurrentLimits.SupplyCurrentLimitEnable = true;
        config.CurrentLimits.SupplyCurrentLimit = Constants.KrakenMotorConstants.CURRENT_LIMIT;
        mainMotor.getConfigurator().apply(config);
    }

    @Override
    public void rotate(int direction) {
        int invertedSign = Constants.KrakenMotorConstants.INVERTED ? -1 : 1;
        double output = 0.75 * direction * invertedSign;
        mainMotor.setControl(dutyCycleRequest.withOutput(output));
        SmartDashboard.putBoolean("Kraken Spinning", true);
    }

    @Override
    public void stop() {
        mainMotor.setControl(dutyCycleRequest.withOutput(0));
        SmartDashboard.putBoolean("Kraken Spinning", false);
    }

    @Override
    public void toggleInverted() {
        Constants.KrakenMotorConstants.INVERTED = !Constants.KrakenMotorConstants.INVERTED;
        applyConfig();
        SmartDashboard.putBoolean("Kraken Inverted", Constants.KrakenMotorConstants.INVERTED);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Kraken Velocity RPS", mainMotor.getVelocity().getValueAsDouble());
        SmartDashboard.putNumber("Kraken Rotor Position", mainMotor.getRotorPosition().getValueAsDouble());
    }
}
