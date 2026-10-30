// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import java.util.ArrayList;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.rappers.motors.SparkMaxRapper;

public class Robot extends TimedRobot {
  private ArrayList<Command> activeCommands = new ArrayList<>();

  private final RobotContainer robotContainer;

  private void scheduleNewCommands(Command command) {
    clearCommands();
    if (command != null) {
      activeCommands.add(command);
      CommandScheduler.getInstance().schedule(command);
    }
  }
  private void scheduleNewCommandsWithoutClearingCommands(Command command) {
    if (command != null) {
      activeCommands.add(command);
      CommandScheduler.getInstance().schedule(command);
    }
  }

  private void clearCommands() {
    for (int i = 0; i < activeCommands.size(); i += 1) activeCommands.get(i).cancel();
    activeCommands.clear();
  }

  public Robot() {
    robotContainer = new RobotContainer();
  }

  @Override
  public void robotInit() {}

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void simulationPeriodic() {
    SparkMaxRapper.update();
  }

  @Override
  public void disabledInit() {
    scheduleNewCommands(robotContainer.getDisableCommand());
  }

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {
    scheduleNewCommandsWithoutClearingCommands(null);
  }

  @Override
  public void autonomousInit() {
    //scheduleNewCommands(robotContainer.getAutonomousCommand());
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {
    scheduleNewCommandsWithoutClearingCommands(robotContainer.getAutonomousEndCommand());
  }

  @Override
  public void teleopInit() {
    scheduleNewCommands(robotContainer.getTeleopCommand());
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void teleopExit() {
    scheduleNewCommandsWithoutClearingCommands(null);
  }

  @Override
  public void testInit() {
    scheduleNewCommands(null);
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {
    scheduleNewCommandsWithoutClearingCommands(null);
  }
}
