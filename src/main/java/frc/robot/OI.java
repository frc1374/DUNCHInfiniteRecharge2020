package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;


public class OI {
    private static final XboxController Driver = new XboxController(0);
    private static final XboxController Operator = new XboxController(1);

    // driver
    public static double getDriverSpeed() {
        final double triggerDiff = Driver.getTriggerAxis(Hand.kLeft) - Driver.getTriggerAxis(Hand.kRight);
        if (triggerDiff > 0.15 || triggerDiff < -0.15) return triggerDiff;
        else return 0.0;
    }
    public static double getSteer() {
        final double joystick = Driver.getX(Hand.kLeft);
        if (joystick > 0.15 || joystick < -0.15) {return joystick;}
        else return 0.0;

    }
    public static double getShootManual() {
        final double joystick = Operator.getY(Hand.kLeft);
        if (joystick > 0.15 || joystick < -0.15) return joystick;
        else return 0.0;
    }
    public static double getSpinManual() {
        final double joystick = Operator.getY(Hand.kRight);
        if (joystick > 0.15 || joystick < -0.15) return joystick;
        else if(Operator.getXButton()) {
            return -.15;
        }
        else return 0.0;
    }
    //public static boolean getGyro() { return Driver.getXButton(); }
    //public static boolean getAlignment() { return Driver.getYButton(); }
    public static boolean light(){return Driver.getAButton();}
    public static boolean getGearUp() { return Driver.getBButton(); }
    public static boolean getGearDown() { return Driver.getAButton(); }
    public static boolean indexf(){return Operator.getYButton();}
    public static boolean indexb(){return Operator.getBButton();}
    public static boolean ski(){return Operator.getBumperPressed(Hand.kRight);}
    public static boolean spin(){return Operator.getBumperPressed(Hand.kLeft);}
    public static boolean shoot(){return Operator.getAButton();}
    public static boolean emergencyOpen(){return Operator.getStartButton();}
    public static int getBallState = 0;
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
