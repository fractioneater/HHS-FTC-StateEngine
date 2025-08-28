package org.firstinspires.ftc.teamcode.hardware.basicfunctionality;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

public class MotorControl {

  private RobotHardware rh;
  private DcMotorEx motor;
  private String name;

  private int min = 0;
  private int max = 0;

  private int[] positions = null;

  // These separate increasing and decreasing speeds for cases such as
  // going slower down so that gravity doesn't make a lift system damage the robot
  private double increasingSpeed = 1.0;
  private double decreasingSpeed = 1.0;

  // This initializes the MotorControl regardless of constructor type
  private void construct(RobotHardware rh, String motorName, DcMotorSimple.Direction direction) {
    this.rh = rh;
    motor = rh.op.hardwareMap.get(DcMotorEx.class, motorName);
    name = motorName;

    motor.setTargetPosition(0);

    motor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    motor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    motor.setDirection(direction);
  }

  // Default ZeroPowerBehavior is BRAKE
  public MotorControl(RobotHardware rh, String motorName, DcMotorEx.Direction direction) {
    construct(rh, motorName, direction);
    motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
  }

  public MotorControl(RobotHardware rh, String motorName, DcMotorEx.Direction direction, DcMotorEx.ZeroPowerBehavior zeroPowerBehavior) {
    construct(rh, motorName, direction);
    motor.setZeroPowerBehavior(zeroPowerBehavior);
  }

  public void move() { motor.setPower(powerCurve()); }

  public boolean isMoving() { return 0.0 != (motor.getCurrentPosition() - motor.getTargetPosition()); }

  public void setRange(int min, int max) {
    this.min = min;
    this.max = max;
  }

  public void setPositions(int[] positions) {
    this.positions = positions;
  }

  public void goToPresetPosition(int index) {
    motor.setTargetPosition(positions[index]);
  }

  public void clampTargetInRange() {
    int target = Math.min(max, motor.getTargetPosition());
    target = Math.max(min, target);
    motor.setTargetPosition(target);
  }

  // --------------------------------------------------------------------------------------------

  public void goToPosition(int targetPosition) { motor.setTargetPosition(targetPosition); }

  public int getCurrentPosition() { return motor.getCurrentPosition(); }

  public int getTargetPosition() { return motor.getTargetPosition(); }

  // --------------------------------------------------------------------------------------------

  public void setSpeedControls(double speed) {
    this.increasingSpeed = speed;
    this.decreasingSpeed = speed;
  }

  public void setSpeedControls(double increasingSpeed, double decreasingSpeed) {
    this.increasingSpeed = increasingSpeed;
    this.decreasingSpeed = decreasingSpeed;
  }

  public void setPowerControls(int clampingDistance, int brakingDistance, double stretch) {
    this.clampingDistance = clampingDistance;
    this.brakingDistance = brakingDistance;
    this.stretch = stretch;
  }

  // variables only for the power curve method
  private final int base = 2;             // Exponential base, shouldn't be changed
  private int clampingDistance = 1000;    // Max speed is used above this distance
  private int brakingDistance = 30;       // Brake below this distance from target
  private double stretch = 100;           // For a base of 2 this is the distance at which 50% speed will be used
  // Above this distance it will approach 100% speed
  // Below this distance it will approach 0% speed with a deadzone near 0 distance

  // Please note that because this motor uses run to position we can treat all distances as positive
  // And only note the direction for the speed to be used in each direction
  public double powerCurve() {
    // find signed distance to target
    int distance = motor.getTargetPosition() - motor.getCurrentPosition();

    // set the correct speed for the direction of motion
    double speed = increasingSpeed;
    if (distance < 0) {
      speed = decreasingSpeed;
    }

    // make the distance positive
    distance = Math.abs(distance);

    // returns 0.0 when within braking distance
    if (distance <= brakingDistance) {
      return 0.0;
    }

    // returns speed when above clamping distance
    if (distance >= clampingDistance) {
      return speed;
    }

    // when inside clamping distance
    // use a decreasing speed for closer distances
    double power = Math.pow(base, -stretch / distance);
    return power * speed;

    // y = 2^( -s / x ) * v
    // where y is power, x is distance to target (+), s is the stretch factor, and v is the speed
  }

  public void telemetry() {
    rh.op.telemetry.addLine();

    rh.op.telemetry.addLine(String.format("Motor %s:", name));

    rh.op.telemetry.addLine(String.format("    MIN is %d, MAX is %d", min, max));
    rh.op.telemetry.addLine(String.format("    Positions: CURRENT %d, TARGET %d", motor.getCurrentPosition(), motor.getTargetPosition()));
    rh.op.telemetry.addLine(String.format("    %s moving%s", isMoving() ? "IS" : "NOT",
      isMoving() ? String.format(" at speed %d", powerCurve()) : "")); // Gross.
  }
}
