package frc.robot;

import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public final class Constants {
  public static class NeoMotorConstants {
    public static final IdleMode IDLE_MODE = IdleMode.kBrake;
    public static boolean INVERTED = false;
    public static final int CURRENT_LIMIT = 60;
    public static final int MAIN_ID = 1;
  }
}
