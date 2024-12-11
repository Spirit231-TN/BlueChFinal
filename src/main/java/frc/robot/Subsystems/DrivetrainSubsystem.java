// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import java.util.function.DoubleSupplier;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DrivetrainSubsystem extends SubsystemBase {

  DrivetrainIOInputsAutoLogged inputs = new DrivetrainIOInputsAutoLogged();
  DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(new Rotation2d(), 0, 0);
  DrivetrainSubsystemSim io = new DrivetrainSubsystemSim();

  public Command setVoltagesArcadeCommand(DoubleSupplier drive, DoubleSupplier steer) {
    return new RunCommand(() -> {
      var speeds = DifferentialDrive.arcadeDriveIK(drive.getAsDouble(), steer.getAsDouble(), false);
      this.setVoltages(speeds.left * 12, speeds.right * 12);
    }, this);
  }

  private void setVoltages(double left, double right) {
  }

  /** Creates a new DrivetrainSubsystem. */
  public DrivetrainSubsystem() {
  }

  @Override
  public void periodic() {
    inputs.robotPose = odometry.update(
      odometry.getPoseMeters().getRotation() 
      .plus(Rotation2d.fromRadians(inputs.leftVelocityMetersPerSecond - inputs.rightVelocityMetersPerSecond)), inputs.leftPositionMeters, inputs.rightPositionMeters);
      io.updateInputs(inputs);
      Logger.processInputs("drivetrain", inputs);
      Logger.recordOutput("drivetrain pose", odometry.getPoseMeters());
      Logger.processInputs("Drivetrain", inputs);
      odometry.getPoseMeters().getRotation()
    .plus(
        Rotation2d.fromRadians(
            (inputs.leftVelocityMetersPerSecond - inputs.rightVelocityMetersPerSecond)
             * 0.020 / Units.inchesToMeters(26)));
    // This method will be called once per scheduler run
  }
}
