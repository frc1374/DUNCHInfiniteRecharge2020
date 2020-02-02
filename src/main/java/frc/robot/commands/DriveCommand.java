package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class DriveCommand extends Command {

  boolean flag = false;

  public DriveCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.DriveSubsystem);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double encoderOutput = Robot.DriveSubsystem.righte.getPosition();
    System.out.println(encoderOutput);
    Robot.DriveSubsystem.arcadeDrive(OI.getDriverSpeed(), OI.getSteer());
    if(OI.light() && flag){//chris the toggle god made this
      flag = false;
      if( Robot.pipe == 1.0){
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(0);
      }
      else {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(1);
      }
    }
    else if(!OI.light()){
      flag = true;
    }
    
    //if (Robot.x > 1) Robot.DriveSubsystem.arcadeDrive(0, 0.1);
    //else if (Robot.x < -1) Robot.DriveSubsystem.arcadeDrive(0, -0.1);
    //else Robot.DriveSubsystem.arcadeDrive(-0.1, 0);
    // else Robot.DriveSubsystem.arcadeDrive(OI.getDriverSpeed(), OI.getSteer());
    //Robot.DriveSubsystem.shiftGear(OI.getGearUp(), OI.getGearDown());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
