package org.firstinspires.ftc.teamcode.hardware.examples;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.hardware.basicfunctionality.Hardware;
import org.firstinspires.ftc.teamcode.hardware.basicfunctionality.MotorControl;

public class ExampleLiftHardware implements Hardware {

    private RobotHardware rh = null;

    // Declare motors
    private ElapsedTime runtime = new ElapsedTime();
    private MotorControl leftLift = null;
    private MotorControl rightLift = null;

    public ExampleLiftHardware(RobotHardware rh) {
        this.rh = rh;
    }

    public void initialize() {

        // init motors
        leftLift = new MotorControl(rh, "leftLift", DcMotor.Direction.FORWARD);
        rightLift = new MotorControl(rh, "rightLift", DcMotor.Direction.REVERSE);

        // init motor range
        leftLift.setRange(0, 1000);
        rightLift.setRange(0, 1000);

        // init motor positions
        int[] positions = {0, 200, 400, 600, 800, 1000}; // for autonomous
        leftLift.setPositions(positions);
        rightLift.setPositions(positions);

        // set speeds
        double increasingSpeed = 0.5;     // maximum speed for lift motors in + click direction
        double decreasingSpeed = 0.3;     // maximum speed for lift motors in - click direction
        leftLift.setSpeedControls(increasingSpeed, decreasingSpeed);
        rightLift.setSpeedControls(increasingSpeed, decreasingSpeed);
    }

    public void update() {
        leftLift.clampTargetInRange();
        rightLift.clampTargetInRange();

        leftLift.move();
        rightLift.move();
    }

    public void setLiftPosition(int position) {
        leftLift.goToPosition(position);
        rightLift.goToPosition(position);
    }

    public void adjustPosition(int adjustment) {
        leftLift.goToPosition(leftLift.getTargetPosition() + adjustment);
        rightLift.goToPosition(rightLift.getTargetPosition() + adjustment);
    }

    public int getHeight() {
        return leftLift.getTargetPosition();
    }

    public void telemetry() {
        rh.op.telemetry.addLine("Pixel Lift Hardware:");

        rh.op.telemetry.addLine();

        leftLift.telemetry();
        rightLift.telemetry();
    }
}
