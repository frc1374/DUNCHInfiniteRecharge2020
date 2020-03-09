package frc.robot.commands;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
public class AutonomousBallHuntCommand extends CommandGroup {
  double Start, End, Time, Distance;
  boolean done = false;
  boolean stopAim = false;
  public AutonomousBallHuntCommand() {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(1);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.DriveSubsystem);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(1);
    Robot.DriveSubsystem.lefte.setPosition(0);
    Robot.DriveSubsystem.righte.setPosition(0);
    Start = System.currentTimeMillis();
    End = System.currentTimeMillis();
    Robot.DriveSubsystem.arcadeDrive(0, 0);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(1);//keep limelight searching
    Robot.ShooterSubsystem.lightPist.set(Value.kReverse);
    if(Robot.x == 0){
      Robot.ShooterSubsystem.aim(-.35);//turn right untill it sees something
      if(System.currentTimeMillis() - Start > 2750){//incase it sees nothing(pneumatinc being broke)
        done = true;
      }
    }
    else if(Math.abs(Robot.x)<2){//if yyou are within 2degree
      done = true;
    }
    else if(Math.abs(Robot.x)>.1 && !stopAim){
      Robot.ShooterSubsystem.aim(((Robot.x+5)/360)*-5);//turn to limlight being off tilt 5degree, turn to the center in a curve(slow as it approches as to not overshoot)
    }


    //Robot.DriveSubsystem.arcadeDrive(Speed, Turn);
    End = System.currentTimeMillis();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (done) {
      return true;
    }
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.DriveSubsystem.arcadeDrive(0, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
