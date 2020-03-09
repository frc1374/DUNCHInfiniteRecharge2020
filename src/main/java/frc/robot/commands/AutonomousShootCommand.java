package frc.robot.commands;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;


public class AutonomousShootCommand extends CommandGroup {
  double Start, End, Time, Distance;
  boolean done = false;
  boolean stopAim = false;
  public AutonomousShootCommand() {

    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.DriveSubsystem);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

    double maxx = 20*12;
    double minn = 0;
    double dist = Robot.ShooterSubsystem.findDistance();
    double normalized = (dist-(minn*dist))/((maxx*dist)-(minn*dist));//take the max and min and normalize to 0-1
    double fireSpeed = normalized;
    Start = System.currentTimeMillis();
    End = System.currentTimeMillis();
    Robot.DriveSubsystem.arcadeDrive(0, 0);
    Robot.ShooterSubsystem.aim(0);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(1);

    //Robot.IntakeSubsystem.closeIntakeSpin(1.0);
    Robot.ShooterSubsystem.aim(0);
    Robot.ShooterSubsystem.tempFire(1);//shooter fire speed
    Robot.IntakeSubsystem.farIntakeSpin(-1);
    if(System.currentTimeMillis() - Start > 3000){
      Robot.WaterWheelSubsystem.ski.set(Value.kForward);
      Robot.IntakeSubsystem.intakeArm.set(Value.kReverse);
      Robot.IntakeSubsystem.closeIntakeSpin(1);
      Robot.WaterWheelSubsystem.ManualAuto(-.2);
      Robot.WaterWheelSubsystem.wheelTurnTo = Robot.WaterWheelSubsystem.getPos(); 
      //Robot.WaterWheelSubsystem.wheelTurnTo = Robot.WaterWheelSubsystem.getPos()
      //+ ((4096.0 * 36.0)  * 4);
      if(System.currentTimeMillis() - Start > 5500){
        done = true;
      }
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
    Robot.WaterWheelSubsystem.waterWheelSki(Value.kForward);
    Robot.IntakeSubsystem.intakeArm.set(Value.kForward);

    Robot.DriveSubsystem.arcadeDrive(0, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
