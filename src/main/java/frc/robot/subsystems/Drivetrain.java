package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.rappers.config.motors.SparkMaxRapperConfig;
import frc.robot.rappers.motors.SparkMaxRapper;
import frc.robot.rappers.motors.SparkMaxRapper.DeviceType;
import frc.robot.Consts;

public class Drivetrain extends SubsystemBase {
    private SparkMaxRapper[] motors = null;

    public Drivetrain() {
        motors = new SparkMaxRapper[4];
        for (int i = 0; i < motors.length; i += 1) {
            motors[i] = new SparkMaxRapper(Consts.DriveMotorPorts[i], DeviceType.Brushless);
            SparkMaxRapperConfig config = new SparkMaxRapperConfig(motors[i]);

            config.inverse = i <= 2;
            config.brakeAtIdle = true;
            config.apply();
        }
    }

    public void setSides(double left, double right) {
        setLeft(left);
        setRight(right);
    }
    
    public void setLeft(double speed) {
        for (int i = 0; i < motors.length / 2; i += 1) motors[i].set(speed);
    }
    public void setRight(double speed) {
        for (int i = motors.length / 2; i < motors.length; i += 1) motors[i].set(speed);
    }

    public Command tankDrive(double left, double right) {
        return new InstantCommand(() -> this.setSides(left, right), this);
    }

    public Command disable() {
        return new InstantCommand(() -> this.setSides(0, 0), this);
    }
}
