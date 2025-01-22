package org.firstinspires.ftc.teamcode.teamcode.states.teleop.examples;


import org.firstinspires.ftc.teamcode.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.teamcode.stateengine.State;

public class DriveTeleop implements State {

    RobotHardware rh = null;

    @Override
    public void init(RobotHardware rh) {
        this.rh = rh;
    }

    @Override
    public void run() {

        double max     =  rh.controls.exampleDriveSpeed();

        // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
        double axial   =  rh.controls.driveY();
        double lateral =  rh.controls.driveX();
        double yaw     =  rh.controls.driveR();

        rh.exampleDriveH.drive(axial, lateral, yaw, max);
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isDone() {
        return false;
    }
}
