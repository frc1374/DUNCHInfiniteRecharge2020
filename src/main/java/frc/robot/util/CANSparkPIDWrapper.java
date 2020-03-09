package frc.robot.util;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
/*
thank god Rico made this the giga mind man
idk how this works, but look at waterwheel subsystem, and command for a example of it implemented
*/

public class CANSparkPIDWrapper {
    // Spark Max motor controller connected via a CAN bus
    CANSparkMax SparkMax;
    // Encoder that will be read off the SparkMax
    public CANEncoder Encoder;
    // PID Controller that will be read off the SparkMax
    CANPIDController PIDController;

    // Default controller with an integer channel input (only with Bruhsless
    // motors). It also comes with the specification of the mode to operate PID
    // with: position, velocity and Smart Motion (0, 1, 2 respectively)
    public CANSparkPIDWrapper(int channel, int mode) {

        // Initialize the SparkMax and all subsequent global variables
        SparkMax = new CANSparkMax(channel, MotorType.kBrushless);

        // Read the Encoder and PIDControllers off the newly initialized SparkMax
        Encoder = SparkMax.getEncoder();
        PIDController = SparkMax.getPIDController();

        // Specific PID mode configurations
        if (mode == 0) {
            // Mode 0, relative PID position control - Reset encoder values to 0
            Encoder.setPosition(0);
        }

    }

    // This void is called to set the PID Tuning values of the SparkMax's
    // PIDController. This includes kP, kI, kD and kFF. It also allows for a
    // conversion factor for positional control.
    public void setPIDValues(double kP, double kI, double kD, double kFF, double kPositionalConversionFactor) {

        // Set the input tune values to the PIDController
        PIDController.setP(kP);
        PIDController.setI(kI);
        PIDController.setD(kD);
        PIDController.setFF(kFF);

        // If kPositionalConversionFactor is 0, then there is no configuration.
        // Otherwise, convert via the factor
        if (kPositionalConversionFactor != 0) {
            Encoder.setPositionConversionFactor(kPositionalConversionFactor);
        }

    }

    // This void method sets the output range of the PID Controller. It takes in the
    // double variables kMinOutput and kMaxOutput for reference
    public void setPIDOutputRange(double kMinOutput, double kMaxOutput) {
        // Set the output range of the PID Closed Loop control feedback system
        PIDController.setOutputRange(kMinOutput, kMaxOutput);
    }
    // This void method sets a positional setpoint on the PID Controller (PID slot
    // index is assumed to be 0)
    public void setPIDPosition(double setpoint) {
        // Call the PID set position mode reference function
        PIDController.setReference(setpoint, ControlType.kPosition);
    }

    // This void method sets a velocity setpoint on the PID Controller (PID slot
    // index is assumed to be 0)
    public void setPIDVelocity(double setpoint) {
        // Call the PID set velocity mode reference function
        PIDController.setReference(setpoint, ControlType.kVelocity);
    }

    // This void method runs percent input into the current SparkMax
    public void setPercentOutput(double output) {
        SparkMax.set(output);
    }

    // This double method returns the current position of the PID Controller
    public double getPosition() {
        return Encoder.getPosition();
    }

    // This double method returns the current velocity (in rpm) of the PID
    // Controller
    public double getVelocity() {
        return Encoder.getVelocity();
    }

}