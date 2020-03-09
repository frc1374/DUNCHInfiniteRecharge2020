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
 so you probably looking here to find out pid, well to be honest its kinda weird, and a lot is thanks to Rico for being a giga mind
 make sure you have Ricos CanSparkMaxWrapper because he made the pid work
 */
public class WaterWheelSubsystem extends Subsystem {

  CANSparkPIDWrapper Wheel;//make a Wheel wrapper
  public Compressor c = new Compressor(0);  
  public DoubleSolenoid ski = new DoubleSolenoid(0, 7);//forward is closed, and reverse is open
  //TalonFX intakeFar = new TalonFX(10);


  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  DigitalInput ballDetect =  new DigitalInput(0);
  // DigitalInput ballDetect = new DigitalInput(0);
  
  // public CANEncoder waterWheele = waterWheel.getEncoder();
  public double wheelTurnTo;
  public boolean skibool,spinbool;
  @Override
  public void initDefaultCommand() {
    Wheel=new CANSparkPIDWrapper(7,0);//make the proper motor and stuff
    Wheel.setPIDValues(2, 0, 5, 0, 4096);//pid values, search up how to tune it,biggest mistake is not setting a tollerance be careful
    //make sure that you set the last number to the ticks of the encoder
    Wheel.setPIDOutputRange(-.2, .2);//set the max and min voltage
    skibool = false;
    spinbool = false;
    
    wheelTurnTo = Wheel.getPosition();//set the current position
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


    if(OI.getSpinManual() <= .15&&OI.getSpinManual() >= -.15){//check to see if not manually turning
      if (Math.abs(wheelTurnTo - Wheel.getPosition() )>= 300) {//this is the tollerance
        //get the absolute difference of the two positions, and the check for tollerance level
        Wheel.setPIDPosition(wheelTurnTo);//set the position, and wait for magic to happen
      }
      else{
        Wheel.setPercentOutput(0);
      }
    }
    else{
      Wheel.setPercentOutput(-speed*.5);
      wheelTurnTo = getPos();
    }




  }


  public boolean checkBall() {
    return !ballDetect.get();
  }
  public double checkSpeed(){
    return Wheel.getVelocity();
  }
  public void ManualAuto(double speed){
    Wheel.setPercentOutput(-speed);
  }

}
