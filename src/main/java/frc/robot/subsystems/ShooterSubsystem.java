package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import java.lang.Math; 

public class ShooterSubsystem extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public CANSparkMax shoot1 = new CANSparkMax(RobotMap.shoot1, MotorType.kBrushless);
    public CANEncoder shoote = shoot1.getEncoder();
    public boolean aimed = false;

    @Override
    public void initDefaultCommand() {

        shoote.setPositionConversionFactor(4906);
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    public void shootAim() {
        if (Robot.x > 1) {
            Robot.DriveSubsystem.arcadeDrive(0, 0.1);
            aimed = false;
        } else if (Robot.x < -1) {
            Robot.DriveSubsystem.arcadeDrive(0, -0.1);
            aimed = false;
        } else {
            aimed = true;
        }
        // if (Robot.x > 1) Robot.DriveSubsystem.arcadeDrive(0, 0.1);
        // else if (Robot.x < -1) Robot.DriveSubsystem.arcadeDrive(0, -0.1);
        // else Robot.DriveSubsystem.arcadeDrive(-0.1, 0);
    }
    public void actuallyFire(){
        double multiplyer = 1.0;
        double distance = 82.5/Math.tan(Math.toRadians(Robot.y));
        double speedForDistance = distance*multiplyer;
        if(aimed){
            shoot1.set(speedForDistance);
        }
        else{//this can be set to aim, or if drivers feel they are actually aimed just lob it
            shootAim();
        }
    }

}
