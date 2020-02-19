package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.WaterWheelSubsystem;

public class WaterWheelCommand extends Command {
  boolean flagf = false;
  boolean flagb = false;
  boolean flagski = false;
  boolean flagspin = false;
  boolean flagshoot = false;
  public WaterWheelCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.WaterWheelSubsystem);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.WaterWheelSubsystem.wheelTurnTo = Robot.WaterWheelSubsystem.getPos();
    Robot.WaterWheelSubsystem.waterWheelSki(Value.kReverse);
    Robot.WaterWheelSubsystem.waterWheelSpin(Value.kForward);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    System.out.println(Robot.WaterWheelSubsystem.checkSpeed());
    if(OI.shoot()&&flagshoot){
      Robot.WaterWheelSubsystem.wheelTurnTo = Robot.WaterWheelSubsystem.getPos()+9999999;
      Robot.ShooterSubsystem.tempFire(1);
      if(Robot.WaterWheelSubsystem.checkBall()&&Robot.WaterWheelSubsystem.checkSpeed() >= 5500){
        System.out.println("xd");
        Robot.WaterWheelSubsystem.wheelTurnTo = Robot.WaterWheelSubsystem.getPos()+(((4096.0*36.0)/(22.0/18.0))*4);
        Robot.WaterWheelSubsystem.waterWheelSki(Value.kForward);
        Robot.WaterWheelSubsystem.waterWheelSpin(Value.kReverse);
        if(Robot.WaterWheelSubsystem.checkSpeed() <2000){
          Robot.WaterWheelSubsystem.wheelTurnTo = Robot.WaterWheelSubsystem.getPos();
          Robot.WaterWheelSubsystem.waterWheelSki(Value.kReverse);
          Robot.WaterWheelSubsystem.waterWheelSpin(Value.kReverse);
          flagshoot = false;
        }
      }
    }
    else if(!OI.shoot()){
      flagshoot = true;
      Robot.WaterWheelSubsystem.wheelTurnTo = Robot.WaterWheelSubsystem.getPos();
    }

    Robot.WaterWheelSubsystem.index(OI.getSpinManual());
    if(OI.indexf() && flagf){//chris the toggle god made this
      System.out.println("index");
      flagf = false;
      Robot.WaterWheelSubsystem.wheelTurnTo +=(((4096.0*36.0)/(22.0/18.0))*2);
    }
    else if(!OI.indexf()){
      flagf = true;
    }
    if(OI.indexb() && flagb){//chris the toggle god made this
      System.out.println("indexb");
      flagb = false;
      Robot.WaterWheelSubsystem.wheelTurnTo -= (((4096.0*36.0)/(22.0/18.0))*2);
    }
    else if(!OI.indexb()){
      flagb = true;
    }
    if(OI.ski() && flagski){//chris the toggle god made this
      System.out.println("skibool");
      flagski = false;
      if(Robot.WaterWheelSubsystem.ski.get() == Value.kForward){
        Robot.WaterWheelSubsystem.ski.set(Value.kReverse);
      }
      else{
        Robot.WaterWheelSubsystem.ski.set(Value.kForward);
      }
      
    }
    else if(!OI.ski()){
      flagski = true;
    }
    if(OI.spin() && flagspin){//chris the toggle god made this
      System.out.println("spinbool");
      flagspin = false;
      if(Robot.WaterWheelSubsystem.spin.get() == Value.kForward){
        Robot.WaterWheelSubsystem.spin.set(Value.kReverse);
      }
      else{
        Robot.WaterWheelSubsystem.spin.set(Value.kForward);
      }
          }
    else if(!OI.spin()){
      flagspin = true;
    }
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
