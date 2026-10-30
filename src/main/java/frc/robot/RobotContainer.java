// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.Input;
import frc.robot.subsystems.Controllers;
import frc.robot.subsystems.Drivetrain;

public class RobotContainer {
  public Drivetrain drivetrain = null;
  public Controllers controllers = null;

  public RobotContainer() {
    drivetrain = new Drivetrain();
    controllers = new Controllers();
  }
  
  public Command getDisableCommand() {
    return drivetrain.disable();
  }

  public Command getAutonomousCommand() {
    return drivetrain.tankDrive(0, 0);
  }

  public Command getAutonomousEndCommand() {
    return this.getDisableCommand();
  }

  public Command getTeleopCommand() {
    return new Input(drivetrain, controllers);
  }
}
