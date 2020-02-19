package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.OI;
import frc.robot.util.CANSparkPIDWrapper;

import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator.Validity;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
/**
 * Add your docs here.
 */
public class WaterWheelSubsystem extends Subsystem {

  CANSparkPIDWrapper Wheel;
  public DoubleSolenoid ski = new DoubleSolenoid(0, 7);//forward is closed, and reverse is open
  public DoubleSolenoid spin =  new DoubleSolenoid(1,6);

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
    Wheel=new CANSparkPIDWrapper(7,1);
    Wheel.setPIDValues(2, 0, 5, 0, 4096);
    //Wheel.setPIDValues(2.1, .6 ,3.4, 0, 1); 
    Wheel.setPIDOutputRange(-1, 1);
    skibool = false;
    spinbool = false;
    
    // waterWheele.setPosition(0);
    // Set the default comm and for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    wheelTurnTo = Wheel.getPosition();
  }
  public double getPos(){
    return Wheel.getPosition();
  }
  public void waterWheelSki(Value activate){
      ski.set(activate);
  }
  public void waterWheelSpin(Value activate){
      spin.set(activate);
   
  }
  public void index(double speed) {
    //System.out.println(Wheel.getVelocity());//5800 aprox max velocity
    //Wheel.setPercentOutput(-speed);
    System.out.println(wheelTurnTo);
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

  public boolean checkBall() {
    return !ballDetect.get();
  }
  public double checkSpeed(){
    return Wheel.getVelocity();
  }


}
