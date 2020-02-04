package frc.robot.commands;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
public class AutonomousBallHuntCommand extends CommandGroup {
  double Start, End, Time, Distance;
  boolean done = false;
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
    System.out.println(Robot.area);
    if(Robot.area >=8) done = true;
    else if (Robot.area >=4)Robot.DriveSubsystem.arcadeDrive(-0.1, Robot.x/360);
    else Robot.DriveSubsystem.arcadeDrive(-0.25, Robot.x/360);
    

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
