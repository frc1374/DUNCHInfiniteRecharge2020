package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class OI {
    private static final XboxController Driver = new XboxController(0);
    private static final XboxController Operator = new XboxController(1);

    // driver
    public static double getDriverSpeed() {
        double triggerDiff = Driver.getTriggerAxis(Hand.kLeft) - Driver.getTriggerAxis(Hand.kRight);
        if (triggerDiff > 0.15 || triggerDiff < -0.15) return triggerDiff;
        else return 0.0;
    }
    public static double getSteer() {
        double joystick = Driver.getX(Hand.kLeft);
        if (joystick > 0.15 || joystick < -0.15) return joystick;
        else return 0.0;

    }
    //public static boolean getGyro() { return Driver.getXButton(); }
    //public static boolean getAlignment() { return Driver.getYButton(); }
    public static boolean light(){return Driver.getAButton();}
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
