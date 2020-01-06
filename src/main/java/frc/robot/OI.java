package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class OI {
    private static final XboxController Driver = new XboxController(0);
    private static final XboxController Operator = new XboxController(1);

    // driver
    public static double getDriverSpeed() { return Driver.getTriggerAxis(Hand.kLeft) - Driver.getTriggerAxis(Hand.kRight); }
    public static double getSteer() { return Driver.getX(Hand.kLeft); }
    //public static boolean getGyro() { return Driver.getXButton(); }
    //public static boolean getAlignment() { return Driver.getYButton(); }
    public static boolean getGearUp() { return Driver.getBButton(); }
    public static boolean getGearDown() { return Driver.getAButton(); }

    // operator

    /*                      Controls
    *
    * Driver
    * Right trigger = forwards
    * Left trigger = backwards
    * Left stick = steer
    * B = gear up
    * A = gear down
    * 
    * Operator
    * TBD
    */
}
