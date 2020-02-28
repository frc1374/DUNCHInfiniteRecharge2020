package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.OI;
import frc.robot.util.CANSparkPIDWrapper;

import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator.Validity;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
/**
 * Add your docs here.
 */
public class WaterWheelSubsystem extends Subsystem {

  CANSparkPIDWrapper Wheel;
  public Compressor c = new Compressor(0);  
  public DoubleSolenoid ski = new DoubleSolenoid(0, 7);//forward is closed, and reverse is open
  //TalonFX intakeFar = new TalonFX(10);


  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  // 36/1 for wheel (36 spin of motor = 1 spin of wheel)
  DigitalInput ballDetect =  new DigitalInput(0);
  // DigitalInput ballDetect = new DigitalInput(0);
  
  // public CANEncoder waterWheele = waterWheel.getEncoder();
  public double wheelTurnTo;
  public boolean skibool,spinbool;
  @Override
  public void initDefaultCommand() {
    Wheel=new CANSparkPIDWrapper(7,0);
    Wheel.setPIDValues(2, 0, 5, 0, 4096);
    //Wheel.setPIDValues(2.1, .6 ,3.4, 0, 1); 
    Wheel.setPIDOutputRange(-.2, .2);
    skibool = false;
    spinbool = false;
    
    // waterWheele.setPosition(0);
    // Set the default comm and for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    wheelTurnTo = Wheel.getPosition();
  }
  public void CompressorControl(){
    c.setClosedLoopControl(true);    
  }
  public double getPos(){
    return Wheel.getPosition();
  }
  public void waterWheelSki(Value activate){
      ski.set(activate);
  }

  public void index(double speed) {
    //System.out.println(Wheel.getVelocity());//5800 aprox max velocity
    //Wheel.setPercentOutput(-speed);
    //System.out.println(wheelTurnTo);
    if(OI.getSpinManual() <= .15&&OI.getSpinManual() >= -.15){
      if (Math.abs(wheelTurnTo - Wheel.getPosition() )>= 300) {
        //System.out.println("at " + Wheel.getPosition());
        //System.out.println("going to  " + wheelTurnTo);
        Wheel.setPIDPosition(wheelTurnTo);
      }
      else{
        Wheel.setPercentOutput(0);
      }
    }
    else{
      Wheel.setPercentOutput(-speed*.5);
      wheelTurnTo = getPos();
    }



    // if(Wheel.getPosition() > wheelTurnTo*.6){
    //   Wheel.setPercentOutput(-.5);
    // }
    //  else if (Wheel.getPosition() < wheelTurnTo*.6){
    //    Wheel.setPercentOutput(.5);
    //  }
  }


  public boolean Zero(){
    wheelTurnTo = getPos() + (((4096.0 * 36.0) / (22.0 / 18.0)) * 2);
    if(checkBall()){
      wheelTurnTo = getPos();
      Wheel.Encoder.setPosition(0);
      return true;
    }
    else{
      return false;
    }
    
  }
  public boolean checkBall() {
    return !ballDetect.get();
  }
  public double checkSpeed(){
    return Wheel.getVelocity();
  }


}
