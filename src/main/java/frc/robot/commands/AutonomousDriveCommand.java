package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class AutonomousDriveCommand extends Command {
  double Start, End, Time, Distance;
  boolean done = false;
  public AutonomousDriveCommand(double distance) {
    Distance = distance;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.DriveSubsystem);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.DriveSubsystem.lefte.setPosition(0);
    Robot.DriveSubsystem.righte.setPosition(0);
    Start = System.currentTimeMillis();
    End = System.currentTimeMillis();
    Robot.DriveSubsystem.arcadeDrive(0, 0);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double encoderReadL = Robot.DriveSubsystem.lefte.getPosition();
    double encoderReadR = Robot.DriveSubsystem.righte.getPosition();
    double error = encoderReadL - encoderReadR*-1;
    double turn_power = .2 * error;
    System.out.println(encoderReadL);
    Robot.DriveSubsystem.arcadeDrive(-.25, -turn_power);
    if(encoderReadL>Distance&&encoderReadR*-1 > Distance){
      //Robot.DriveSubsystem.left1.set
      Robot.DriveSubsystem.arcadeDrive(1,0);
      System.out.println("done!!!");
      done = true;
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
