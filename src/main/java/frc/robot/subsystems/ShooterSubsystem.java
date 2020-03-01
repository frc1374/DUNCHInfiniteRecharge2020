package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import java.lang.Math;
import frc.robot.util.CANSparkPIDWrapper;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
public class ShooterSubsystem extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    CANSparkPIDWrapper Turret;
    TalonFX Shoot = new TalonFX(8);
    //TalonFX Shoot2 = new TalonFX(12);
    public DoubleSolenoid intakeArm=  new DoubleSolenoid(1,6);

    public boolean aimed = false;

    @Override
    public void initDefaultCommand() {
        Turret=new CANSparkPIDWrapper(11,1);//turret is 133t, 18t on motor, 36/1 gearbox
        Turret.setPIDValues(2, 0, 5, 0, 4096);
        Turret.setPIDOutputRange(-.5, .5);
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    public void tempFire(double speed){
        Shoot.set(ControlMode.PercentOutput, speed);
        //Shoot2.set(ControlMode.PercentOutput, -speed);
    }
    public void aim(double speed){
        Turret.setPercentOutput(speed);
    }
    public double findDistance(){
        return (82.5-29)/Math.tan(Math.toRadians(Robot.y));
    }
    public void actuallyFire(){
        double multiplyer = 1.0;
        double distance = (82.5-29)/Math.tan(Math.toRadians(Robot.y));
        int max = 1;
        int min = 0;
        double speedForDistance = distance*multiplyer;
        double speedNorm = (max-min)/(max-min)*(speedForDistance-max)+max;

        if(aimed){
        }
        else{//this can be set to aim, or if drivers feel they are actually aimed just lob it
        }
    }
    public void lightPiston(Value state){
        //lightPiston(state);
    }

}
