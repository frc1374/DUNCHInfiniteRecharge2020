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
  public static final double gearRatio = (50.0/13.0)/(50.0/30.0)/(24.0/30.0);
  public static final double Fudgefactor = 1.0; 


  public double speedMultiplyer = 1;
  public final double distanceperpulse = Math.PI*wheelDiameter/pulsePerRevolution /encoderGearRatio/gearRatio * Fudgefactor;

  
  public void CompressorControl(){
    c.setClosedLoopControl(true);    
  }

  
  @Override
  public void initDefaultCommand() {
    speedMultiplyer = .6;
    // Set the default command for a subsystem here.
    lefte.setPosition(0);
    righte.setPosition(0);
    // setDefaultCommand(new MySpecialCommand());
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

}
