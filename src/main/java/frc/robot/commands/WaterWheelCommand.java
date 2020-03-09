package frc.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;


public class WaterWheelCommand extends Command {
  boolean setZeroed = false;
  boolean flagf = false;
  boolean flagb = false;
  boolean flagski = false;
  boolean flagspin = false;
  boolean flagshoot = false;
  boolean flagindex = false;
  boolean flagshootv2 = false;
  boolean readytoindex = false;
  boolean shootOneFlag = false;
  int ballCount = 0;
  DigitalInput ballSensor1 = new DigitalInput(1);
  DigitalInput ballSensor2 = new DigitalInput(2);
  Timer time = new Timer();

  public WaterWheelCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.WaterWheelSubsystem);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    time.start();
    Robot.WaterWheelSubsystem.wheelTurnTo = Robot.WaterWheelSubsystem.getPos();
    Robot.WaterWheelSubsystem.waterWheelSki(Value.kReverse);
  }
  double start;
  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    
    // System.out.println(Robot.WaterWheelSubsystem.checkSpeed());
    if(OI.shootOne()&& shootOneFlag){
      shootOneFlag = false;
      Robot.WaterWheelSubsystem.wheelTurnTo = Robot.WaterWheelSubsystem.getPos()
      + (((4096.0 * 36.0)  * 2.0) / 5.1);
    }
    else if (OI.shootOne()){
      shootOneFlag = true;
    }
    if (OI.shoot() && flagshoot) {
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(1);
      double maxx = 20*12;
      double minn = 0;
      double dist = Robot.ShooterSubsystem.findDistance();
      double normalized = (dist-(minn*dist))/((maxx*dist)-(minn*dist));
      
      double fireSpeed = .85;
      if(Robot.x == 0){
        fireSpeed = 1;
      }
      flagshootv2 = false;
      //Robot.IntakeSubsystem.closeIntakeSpin(1.0);
      Robot.ShooterSubsystem.tempFire(fireSpeed);//shooter fire speed
      Robot.IntakeSubsystem.farIntakeSpin(-1);
      if(time.get() - start > 2){
        Robot.IntakeSubsystem.intakeArm.set(Value.kReverse);
        Robot.IntakeSubsystem.closeIntakeSpin(1);
        //Robot.WaterWheelSubsystem.ManualAuto(-.2);
        Robot.WaterWheelSubsystem.wheelTurnTo = Robot.WaterWheelSubsystem.getPos() +(((4096.0 * 36.0)  * 4.0)) ;
        
      }
      

      Robot.WaterWheelSubsystem.waterWheelSki(Value.kForward);
      /*if (Robot.WaterWheelSubsystem.checkSpeed() < 2000) {
        Robot.WaterWheelSubsystem.wheelTurnTo = Robot.WaterWheelSubsystem.getPos();
        Robot.WaterWheelSubsystem.waterWheelSki(Value.kReverse);
        Robot.WaterWheelSubsystem.waterWheelSpin(Value.kReverse);
        flagshoot = false;
      }*/
    } else if (!OI.shoot()&&!flagshootv2) {
      flagshootv2 = true;
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(0);
      flagshoot = true;
      Robot.WaterWheelSubsystem.wheelTurnTo = Robot.WaterWheelSubsystem.getPos();
    }

    if (OI.index() && flagindex) {
      flagindex = false;
      readytoindex = !readytoindex;
    } else if (!OI.index()) {
      flagindex = true;
    }
    if (readytoindex && Robot.WaterWheelSubsystem.checkBall()&& Math.abs(Robot.WaterWheelSubsystem.wheelTurnTo - Robot.WaterWheelSubsystem.getPos() )<= 300) {
      Robot.IntakeSubsystem.closeIntakeSpin(-1);
      
      //Robot.WaterWheelSubsystem.waterWheelSki(Value.kForward);

      
      ballCount++;
      //Robot.WaterWheelSubsystem.waterWheelSpin(Value.kForward);
      Robot.WaterWheelSubsystem.wheelTurnTo = Robot.WaterWheelSubsystem.getPos()
          + (((4096.0 * 36.0)  * 2.0) / 5.0);
    }
    else if (readytoindex) {
      Robot.IntakeSubsystem.intakeArm.set(Value.kReverse);
      Robot.WaterWheelSubsystem.ski.set(Value.kReverse);
      Robot.ShooterSubsystem.tempFire(-.2);
      Robot.IntakeSubsystem.closeIntakeSpin(-.2);
      Robot.IntakeSubsystem.farIntakeSpin(-0.15);//make positive to intake through human player, - off ground

    }  else if(!OI.shoot()){
      start = time.get();
      Robot.IntakeSubsystem.intakeArm.set(Value.kForward);;
      Robot.IntakeSubsystem.closeIntakeSpin(0);
      Robot.ShooterSubsystem.tempFire(0);//shooter fire speed
      Robot.IntakeSubsystem.farIntakeSpin(0);

    }
    Robot.WaterWheelSubsystem.index(OI.getSpinManual());

  /*  if(OI.indexf()){
      setZeroed = true;
    }
    if(setZeroed){
      Robot.WaterWheelSubsystem.Zero();
      if(Robot.WaterWheelSubsystem.Zero()){
        setZeroed = false;
      }
    }*/
    if (OI.ski() && flagski) {// chris the toggle god made this
      flagski = false;
      if (Robot.WaterWheelSubsystem.ski.get() == Value.kForward) {
        Robot.WaterWheelSubsystem.ski.set(Value.kReverse);
      } else {
        Robot.WaterWheelSubsystem.ski.set(Value.kForward);
      }

    } else if (!OI.ski()) {
      flagski = true;
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
