package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import java.lang.Math;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class ClimberSubsystem extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public TalonSRX climber = new TalonSRX(16);
    public TalonSRX climberUp1 = new TalonSRX(17);
    //public TalonSRX climberUp2 = new TalonSRX(15);

    @Override
    public void initDefaultCommand() {

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    public void pullDown(double speed){

        climber.set(ControlMode.PercentOutput, speed);
    }
    public void Return(double speed){
        climberUp1.set(ControlMode.PercentOutput,speed);
    }
    public void goUp(double speed){
        
        climberUp1.set(ControlMode.PercentOutput,speed);
        //climberUp2.set(ControlMode.PercentOutput,speed);
    }
  


}

