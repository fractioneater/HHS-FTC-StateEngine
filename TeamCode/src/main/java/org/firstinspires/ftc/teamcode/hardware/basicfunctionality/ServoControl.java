package org.firstinspires.ftc.teamcode.teamcode.hardware.basicfunctionality;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.teamcode.hardware.RobotHardware;

/// ServoControl is designed to emulate an encoder for a servo
/// and provide the ability to easily move to preset positions
public class ServoControl {

    private RobotHardware rh = null;
    private Servo servo = null;

    private String name = null;

    private double[] positions = null;
    private int positionsIndex = -1;

    private double position = 0.0;
    private double targetPosition = 0.0;

    private double stepSize = 0.15;   // This is used to simulate using an encoder for a servo
                                            // so that we can know if the servo is still turning
    //TODO:
    // Tune stepSize so that for your servo: if you made stepSize any smaller your servo would turn slower than normal
    // You want your servo to move exactly by the stepSize each hardware update cycle without stopping

    public ServoControl(RobotHardware rh, String servoName) {
        this.rh = rh;
        servo = rh.op.hardwareMap.get(Servo.class, servoName);
        name = servoName;
    }
    public ServoControl(RobotHardware rh, String servoName, double stepSize) {
        this.rh = rh;
        servo = rh.op.hardwareMap.get(Servo.class, servoName);
        name = servoName;
        this.stepSize = stepSize;
    }

    public void move() {
        if (Math.abs(targetPosition - position) <= stepSize) {

            // if distance to target is smaller than the step size
            // set position directly to target
            position = targetPosition;
        } else {

            // adjust the position by the step size
            // in the direction of the target
            position += Math.copySign(stepSize, targetPosition - position);
        }
        servo.setPosition(position);
    }

    public boolean isMoving() {
        return 0.0 != ( getCurrentPosition() - getTargetPosition() );
    }

    public void setPositions(double[] positions) { // should ONLY be called in initialization
        this.positions = positions;

        servo.setPosition(positions[0]);
        positionsIndex = 0;
        position = positions[0];
        targetPosition = positions[0];
    }

    public double getCurrentPosition() {
        return position;
    }

    public double getTargetPosition() {
        return targetPosition;
    }

    public int getPositionsIndex() {
        return positionsIndex;
    }

    public void goToPresetPosition(int index) {
        goToPosition(positions[index]);
        positionsIndex = index;
    }

    public void goToPosition(double newTargetPosition) {
        targetPosition = newTargetPosition;
        positionsIndex = -1;
    }

    public void telemetry() {

        rh.op.telemetry.addLine();

        rh.op.telemetry.addData("Servo", name);

        rh.op.telemetry.addLine();

        rh.op.telemetry.addLine("Min: 0.0");
        rh.op.telemetry.addLine("Max: 1.0");
        rh.op.telemetry.addData("Current Position", getCurrentPosition());
        rh.op.telemetry.addData("Target  Position", getTargetPosition());
        rh.op.telemetry.addData("Position", positionsIndex);
        rh.op.telemetry.addData("Moving", isMoving());
    }
}
