package org.firstinspires.ftc.teamcode.teamcode.hardware.examples;

import org.firstinspires.ftc.teamcode.teamcode.hardware.*;
import org.firstinspires.ftc.teamcode.teamcode.hardware.basicfunctionality.Hardware;
import org.firstinspires.ftc.teamcode.teamcode.hardware.basicfunctionality.ServoControl;

public class ExampleClawHardware implements Hardware {

    // Declare
    private RobotHardware rh = null;

    // Declare motors
    private ServoControl clawServo = null;

    public ExampleClawHardware(RobotHardware rh) {
        this.rh = rh;
    }

    public void initialize() {

        // init servo
        clawServo = new ServoControl(rh, "Claw");

        clawServo.setPositions(new double[]{1.0, 0.0});
    }

    public void update() {

        clawServo.move();
    }

    public boolean clawMoving() {
        return clawServo.isMoving();
    }

    public void setClaw(boolean closed) {
        if (closed) {
            clawServo.goToPosition(0);
        } else {
            clawServo.goToPosition(1);
        }
    }

    public boolean isClawClosed() {
        return clawServo.getPositionsIndex() == 0;
    }

    public void telemetry() {
        rh.op.telemetry.addLine("Pixel Claw Hardware:");

        rh.op.telemetry.addLine();

        clawServo.telemetry();
    }
}
