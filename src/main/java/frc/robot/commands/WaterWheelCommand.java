package frc.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

/*
Oh god this is some spaghetti, it might be a bit rough next year having to do pid, but its  aight, 
il comment the weird stuff, and you try to follow allong
*/
public class WaterWheelCommand extends Command {
  boolean setZeroed = false;//you need all these flags for toggles, it looks weird but it works
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
    
    if(OI.shootOne()&& shootOneFlag){//shoot one is never used dw about it
      shootOneFlag = false;
      Robot.WaterWheelSubsystem.wheelTurnTo = Robot.WaterWheelSubsystem.getPos()
      + (((4096.0 * 36.0)  * 2.0) / 5.1);
    }
    else if (OI.shootOne()){
      shootOneFlag = true;
    }
    if (OI.shoot() && flagshoot) {//ok shooting is interesing
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(1);
      double maxx = 20*12;
      double minn = 0;
      double dist = Robot.ShooterSubsystem.findDistance();
      double normalized = (dist-(minn*dist))/((maxx*dist)-(minn*dist));//the code above is for making distance tracking work,
      //doesent work so far so dont worry about it unless trying to do something similar
      
      double fireSpeed = .85;
      if(Robot.x == 0){
        fireSpeed = 1;
      }
      flagshootv2 = false;
      Robot.ShooterSubsystem.tempFire(fireSpeed);//shooter fire speed
      //set fire speed, would be the actual distance math to make it work, but .85 works for now
      Robot.IntakeSubsystem.farIntakeSpin(-1);//set the far intake to start spinning
      if(time.get() - start > 2){//wait 2 seconds
        Robot.IntakeSubsystem.intakeArm.set(Value.kReverse);//open arms
        Robot.IntakeSubsystem.closeIntakeSpin(1);//accelerator wheel
        Robot.WaterWheelSubsystem.wheelTurnTo = Robot.WaterWheelSubsystem.getPos() +(((4096.0 * 36.0)  * 4.0)) ;//add rotations to the waterwheel
        /*
        keep in mind this looks like it could be done by adding something like
                //Robot.WaterWheelSubsystem.ManualAuto(-.2);
        it works in auto, idk why not in manual, but this is jank enough to work properly, so dont touch it
        this works because it adds once, and once you relese A it sets the setpoint to wherever it currently is, and stops 
        the 2 rotations right there
        */
        
      }
      

      Robot.WaterWheelSubsystem.waterWheelSki(Value.kForward);//close the gates

    } else if (!OI.shoot()&&!flagshootv2) {//once you get go of A, this also get called once because of the flag
      flagshootv2 = true;
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(0);//sets the light off
      flagshoot = true;
      Robot.WaterWheelSubsystem.wheelTurnTo = Robot.WaterWheelSubsystem.getPos();//stop turning the wheel by resetting the position it goes to
    }

    if (OI.index() && flagindex) {//indexing is also jank watch out
      flagindex = false;
      readytoindex = !readytoindex;
    } else if (!OI.index()) {
      flagindex = true;
    }
    
    //this waits for the sensor to detect the ball to be tripped, and for the rotation to be in place, and not still spinning to the loction
    if (readytoindex && Robot.WaterWheelSubsystem.checkBall()&& Math.abs(Robot.WaterWheelSubsystem.wheelTurnTo - Robot.WaterWheelSubsystem.getPos() )<= 300) {
      Robot.IntakeSubsystem.closeIntakeSpin(-1);
      
      //sets position to add a fifth of a rotation
      Robot.WaterWheelSubsystem.wheelTurnTo = Robot.WaterWheelSubsystem.getPos()
          + (((4096.0 * 36.0)  * 2.0) / 5.0);
    }
    else if (readytoindex) {//if just ready to intake, setup for balls
      Robot.IntakeSubsystem.intakeArm.set(Value.kReverse);//open arms
      Robot.WaterWheelSubsystem.ski.set(Value.kReverse);//open gates
      Robot.ShooterSubsystem.tempFire(-.2);//intake everthing
      Robot.IntakeSubsystem.closeIntakeSpin(-.2);
      Robot.IntakeSubsystem.farIntakeSpin(-0.15);//make positive to intake through human player, - off ground

    }  else if(!OI.shoot()){//if you aint shooting
      start = time.get();//get the current time to get the 2 seconds to shoot
      Robot.IntakeSubsystem.intakeArm.set(Value.kForward);;//close and stop motors
      Robot.IntakeSubsystem.closeIntakeSpin(0);
      Robot.ShooterSubsystem.tempFire(0);//shooter fire speed
      Robot.IntakeSubsystem.farIntakeSpin(0);

    }
    Robot.WaterWheelSubsystem.index(OI.getSpinManual());//maual index motor for override and un jam

    if (OI.ski() && flagski) {//ski gate, this is a concise example of how toggles work
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
