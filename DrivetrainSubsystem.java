// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Constants;

public class DrivetrainSubsystem extends SubsystemBase {
  TalonFX leftFalcon = new TalonFX(Constants.drivetrainLeftFalconID);
TalonFX rightFalcon = new TalonFX(Constants.drivetrainRightFalconID);

VoltageOut leftVoltage = new VoltageOut(0);
VoltageOut rightVoltage = new VoltageOut(0);

public Command setVoltagesArcadeCommand(DoubleSupplier drive, DoubleSupplier steer) {
  return new RunCommand(() -> {
    var speeds = DifferentialDrive.arcadeDriveIK(drive.getAsDouble(), steer.getAsDouble(), false);
    this.setVoltages(speeds.left * 12, speeds.right * 12);
  }, this);
}

private void setVoltages(double left, double right) {
  leftFalcon.setControl(leftVoltage.withOutput(left));
  rightFalcon.setControl(rightVoltage.withOutput(left));
}
  /** Creates a new DrivetrainSubsystem. */
  public DrivetrainSubsystem() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
