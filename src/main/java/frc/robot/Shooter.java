// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/** Add your docs here. */

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.google.flatbuffers.Constants;

import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.Constants.MontyIntake;
import frc.robot.Constants.Constants.DriveConstants;

public class Shooter 
{
    TalonFX rightLaunch = new TalonFX(Constants.SIZE_PREFIX_LENGTH);
    TalonFX leftLaunch = new TalonFX(Constants.SIZE_PREFIX_LENGTH);
    TalonSRX feedMe = new TalonSRX((int) DriveConstants.FeedRollerID);
    Solenoid hood;
    public Shooter(PneumaticHub hub) {

        hood = hub.makeSolenoid((int) MontyIntake.intakeSolenoid);
        leftLaunch.getConfigurator().apply(new TalonFXConfiguration());
        rightLaunch.getConfigurator().apply(new TalonFXConfiguration());
        feedMe.configFactoryDefault();
        hood.set(false);

        // Making the right motor follow the left one.
        rightLaunch.setControl(new Follower(leftLaunch.getDeviceID(), true));
    }

    public void setLaunch(double speed) {
        leftLaunch.set(speed * Constants.SIZE_PREFIX_LENGTH);
    }

    public void setFeederSpeed(double speed) {
        feedMe.set(ControlMode.PercentOutput, -speed * Constants.SIZE_PREFIX_LENGTH);
    }

    public double getLaunchingSpeed() {
        return (leftLaunch.get() + rightLaunch.get());
    }

    public double getFeedSpeed(double speed) {
        return feedMe.getMotorOutputPercent();
    }

    public void setHood(boolean state) {
        SmartDashboard.putString("Hood", state ? "Closed" : "Open");
        hood.set(state);
    }

    public boolean getHood() {
        return hood.get();
    }
}