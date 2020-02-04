package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
public class AutonomousBreak extends CommandGroup {
  
	double Start, End, Time, Speed, Turn;

  public AutonomousBreak(double time) {
      // Use requires() here to declare subsystem dependencies
      // eg. requires(chassis);
    requires(Robot.DriveSubsystem);
    Time = time;
  }


  // Called just before this Command runs the first time
  protected void initialize() {
    // Subsystems.DRIVE_SUBSYSTEM.right1.set(ControlMode.Position, Distance);
    // Subsystems.DRIVE_SUBSYSTEM.left1.set(ControlMode.Position, Distance);

    Start = System.currentTimeMillis();
    End = System.currentTimeMillis();

    Robot.DriveSubsystem.arcadeDrive(0, 0);

    /*
    Subsystems.INTAKE_SUBSYSTEM.openArmwheel(false, false);
    Subsystems.INTAKE_SUBSYSTEM.intakefb(false, false, true);
    Subsystems.INTAKE_SUBSYSTEM.intakeArmfb(false, false, true);
    */
  }

  // Called repeatedly when this Command is scheduled to run
  protected void execute() {
    /* if (Math.abs(Subsystems.DRIVE_SUBSYSTEM.right1.getClosedLoopError(0)) < 10) {
      End = System.currentTimeMillis();
    }
    else {
      Start = System.currentTimeMillis();
    }
    // System.out.println(End - Start); */
    Robot.DriveSubsystem.arcadeDrive(0.20, 0);
    End = System.currentTimeMillis();
  }

  // Make this return true when this Command no longer needs to run execute()
  protected boolean isFinished() {
    if(End - Start > Time) {
        return true;
      }
    return false;
  }

  // Called once after isFinished returns true
  protected void end() {
    Robot.DriveSubsystem.arcadeDrive(0, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  protected void interrupted() {
  }
}
