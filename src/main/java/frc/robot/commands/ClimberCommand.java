package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class ClimberCommand extends Command {

    Timer time = new Timer();
    public ClimberCommand() {
        
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.ClimberSubsystem);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        
        time.start();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        double currentTime = time.getMatchTime();
        if(currentTime >=120){
            if(OI.climbUp()){//a to go up
                Robot.ClimberSubsystem.goUp(.7);//change this to a negative if its the wrong way, its -1 to 1
            }
            if(OI.climbDown()){//b to go down
                Robot.ClimberSubsystem.pullDown(.7);//change this to a negative if its the wrong way, its -1 to 1
            }
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
