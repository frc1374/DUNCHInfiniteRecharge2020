package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;

public class Autoshootdrive extends CommandGroup {
  /**
   * Add your docs here.
   */
  public Autoshootdrive() {
    // Add Commands here:
    // e.g. addSequential(new Command1());
    // addSequential(new Command2());
    // these will run in order.
    //addSequential(new AutonomousDriveCommand(0));
    addSequential(new AutonomousBallHuntCommand());
    addSequential(new AutonomousShootCommand());
    addSequential(new AutonomousDriveTime(.3,0,3000));//first number speed -1,1, second number turn -1,1 -1 being left, third numer time miliseconds
    //addSequential(new AutonomousBreak(100));
    // To run multiple commands at the same time,
    // use addParallel()
    // e.g. addParallel(new Command1());
    // addSequential(new Command2());
    // Command1 and Command2 will run in parallel.

    // A command group will require all of the subsystems that each member
    // would require.
    // e.g. if Command1 requires chassis, and Command2 requires arm,
    // a CommandGroup containing them would require both the chassis and the
    // arm.
  }
}
