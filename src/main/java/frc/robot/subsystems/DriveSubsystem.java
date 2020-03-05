package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.Compressor;

public class DriveSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public Compressor c = new Compressor(RobotMap.compressor);
  public CANSparkMax left1 = new CANSparkMax(RobotMap.left1, MotorType.kBrushless);
  public CANSparkMax left2 = new CANSparkMax(RobotMap.left2, MotorType.kBrushless);
  public CANSparkMax left3 = new CANSparkMax(RobotMap.left3, MotorType.kBrushless);
  public CANSparkMax right1 = new CANSparkMax(RobotMap.right1, MotorType.kBrushless);
  public CANSparkMax right2 = new CANSparkMax(RobotMap.right2, MotorType.kBrushless);
  public CANSparkMax right3 = new CANSparkMax(RobotMap.right3, MotorType.kBrushless);
  public CANEncoder righte = right1.getEncoder();
  public CANEncoder lefte =  left1.getEncoder();
  AHRS Gyro = new AHRS(SPI.Port.kMXP);
	//Encoder Distance Constants
  public static final double wheelDiameter = 8;
  public static final double pulsePerRevolution = 4096;
  public static final double encoderGearRatio = 1;
  public static final double gearRatio =   2.88 ;  // (50.0/13.0)/(50.0/30.0)/(24.0/30.0);
  public static final double Fudgefactor = 1.6; //increase the speed when turn //previous was 1.0

//syed awase-> change this value to increase or decrease speed.
  public double speedMultiplyer = 1;
  public final double distanceperpulse = Math.PI*wheelDiameter/pulsePerRevolution /encoderGearRatio/gearRatio * Fudgefactor;  //45.479

//syed awase
private double leftSpeedSetpoint=0;
private double rightSpeedSetpoint=0;

private double leftSpeedValue=0;
private double rightSpeedValue=0;
 /** Max change in speed every 50th of a second 
   *  Example: a slew rate of .04 will go from stop to full speed in 0.5 seconds */
private static double SPEED_SLEW_RATE=0.03; //0.04 change, time to rampup //0.003


  public void CompressorControl(){
    c.setClosedLoopControl(true);    
  }

  
  @Override
  public void initDefaultCommand() {
    //syedawase=> modified the value to 0.15 earlier it was 0.6
    speedMultiplyer = .60;
    // Set the default command for a subsystem here.
    lefte.setPosition(0);
    righte.setPosition(0);
    // setDefaultCommand(new MySpecialCommand());
    System.out.println("current distance travelled per pulse:"+ distanceperpulse);
    righte.setPositionConversionFactor(distanceperpulse);
    lefte.setPositionConversionFactor(distanceperpulse);
  }

  public void tankDrive (double left, double right) {
    left1.set(left);
    left2.set(left);
    left3.set(left);
    right1.set(-right);
    right2.set(-right);
    right3.set(-right);
  }

  public void arcadeDrive(double speed, double turn) {
    speed = speed*speedMultiplyer;
    tankDrive(speed-turn, speed+turn);
  }

  public void updateDrive(){
    left1.set(leftSpeedValue);
    left2.set(leftSpeedValue);
    left3.set(leftSpeedValue);
    right1.set(-rightSpeedValue);
    right2.set(-rightSpeedValue);
    right3.set(-rightSpeedValue);
  }

  @Override
  public void periodic() {
	  
	  // The periodic method is called every loop
	  // Take a step toward the setpoint on each 
	  // loop through
	  if (Math.abs(leftSpeedSetpoint - leftSpeedValue) < SPEED_SLEW_RATE) {
		  leftSpeedValue = leftSpeedSetpoint;
	  }
	  else {
		  if (leftSpeedValue < leftSpeedSetpoint) {
			  leftSpeedValue += SPEED_SLEW_RATE;
		  }
		  else {
			  leftSpeedValue -= SPEED_SLEW_RATE;
		  }
	  }
	  
	  if (Math.abs(rightSpeedSetpoint - rightSpeedValue) < SPEED_SLEW_RATE) {
		  rightSpeedValue = rightSpeedSetpoint;
	  }
	  else {
		  if (rightSpeedValue < rightSpeedSetpoint) {
			  rightSpeedValue += SPEED_SLEW_RATE;
		  }
		  else {
			  rightSpeedValue -= SPEED_SLEW_RATE;
		  }
	  }
	  
	  updateDrive();
  }


}
