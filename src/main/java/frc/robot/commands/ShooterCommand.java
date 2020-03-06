package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class ShooterCommand extends Command {
  boolean flag =false;
  boolean manualSwap = false;
  boolean flagLightArm = false;
  public Value currentVal = Value.kReverse;
  public ShooterCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.ShooterSubsystem);
  }
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(0);
    Robot.ShooterSubsystem.lightPiston(true);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    Robot.ShooterSubsystem.tempFire(OI.getShootManual());
    Robot.IntakeSubsystem.closeIntakeSpin(OI.getShootManual());
    if(Math.abs(OI.getOperatorSpeed()) >=.15){
      double speed = OI.getOperatorSpeed() *.1;
      Robot.ShooterSubsystem.aim(speed);
    }
    else{
      Robot.ShooterSubsystem.aim(0);
    }
    if(OI.AIM()){
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(1);
      Robot.ShooterSubsystem.lightPist.set(Value.kReverse);
      if(Math.abs(Robot.x)>.1){
        Robot.ShooterSubsystem.aim((Robot.x/360)*-3);
      }
      // else if(Robot.x >0){
      //   Robot.ShooterSubsystem.aim(-.05);
      // }
      // else if(Robot.x<0){
      //   Robot.ShooterSubsystem.aim(.05);
      // }
      
      
    }
    else{
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(0);


    }
    //System.out.println(Robot.ShooterSubsystem.findDistance());
    if(OI.lightPiston() && flag){
      flag = false;
      if (Robot.ShooterSubsystem.lightPist.get() == Value.kForward) {
        Robot.ShooterSubsystem.lightPist.set(Value.kReverse);
      } else {
        Robot.ShooterSubsystem.lightPist.set(Value.kForward);
      }

    }
    else if (!OI.lightPiston()){
      flag = true;
    }


    //put in here
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
