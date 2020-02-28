package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import java.lang.Math;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class IntakeSubsystem extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public DoubleSolenoid intakeArm=  new DoubleSolenoid(1,6);

    TalonFX intakeClose = new TalonFX(9);
    TalonFX intakeFar = new TalonFX(14);

    @Override
    public void initDefaultCommand() {
        intakeArm.set(Value.kReverse);
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    public void intakeArm(Value state){
        intakeArm.set(state);
    }

    public void closeIntakeSpin(double speed){
        intakeClose.set(ControlMode.PercentOutput, speed);
    }
    public void farIntakeSpin(double speed){
        intakeFar.set(ControlMode.PercentOutput, speed);
    }

}

