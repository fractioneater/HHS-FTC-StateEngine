package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Controls {

    private RobotHardware rh = null;
    private Gamepad gp1 = null;
    private Gamepad gp2 = null;

    private ElapsedTime runtime = new ElapsedTime();

    public Controls(RobotHardware rh) {
        this.rh = rh;
    }

    public void initialize() {
        gp1 = rh.op.gamepad1;
        gp2 = rh.op.gamepad2;
    }

    /**
     * Scales inputs between -1 and 1 to values between -1 and 1 such that
     * -1, 1, and 0 are deadzones, so that small changes to the input have
     * little to no change in the output around the deadzones
     * <p>
     * This uses the function:
     * y = 2.5x^3 - 1.5x^5
     * Where y is output and x is input
     */
    public double smoothInput(double gamepadInput) {
        return (5.0 / 2.0) * Math.pow(gamepadInput, 3.0) - (3.0 / 2.0) * Math.pow(gamepadInput, 5.0);
    }

    //TODO: This is where you will place code for accessing the gamepad inputs

    /**
     * All Controls functions need to test if the controller they are accessing exists
     * If the controller doesn't exist then return a default value
     * usually  false  for booleans and  0  for doubles
     */
    public boolean exampleDiscreteInput() {
        if (gp1 == null) {
            return false;
        }
        return gp1.a;
    }

    public double exampleContinuousInput() {
        if (gp2 == null) {
            return 0;
        }
        return gp2.right_trigger;
    }

    public double exampleSmoothedContinuousInput() {
        if (gp2 == null) {
            return 0;
        }
        return smoothInput(gp2.left_trigger);
    }


    // Example forwards movement control
    public double driveY() {
        if (gp1 == null) {
            return 0;
        }
        return smoothInput(gp1.left_stick_y);
    }

    // Example sideways movement control
    public double driveX() {
        if (gp1 == null) {
            return 0;
        }
        return smoothInput(gp1.left_stick_x);
    }

    // Example rotation movement control
    public double driveR() {
        if (gp1 == null) {
            return 0;
        }
        return smoothInput(gp1.right_stick_x);
    }

    // Example movement speed control
    public double exampleDriveSpeed() {
        if (gp1 == null) {
            return 0;
        }
        return 0.5 + (0.25 * smoothInput(gp1.right_trigger - gp1.left_trigger));
    }

    // Example motor speed control (designed for lift)
    // Turns doubles into ints because motor speeds have to be ints
    public int exampleLiftSpeed() {
        if (gp2 == null) {
            return 0;
        }
        return (int) (10.0 * smoothInput(gp2.right_trigger - gp2.left_trigger));
        // right is INCREASE
        // left is DECREASE
    }

    // Example lift adjustment
    public int exampleLiftAdjust() {
        if (gp2 == null) {
            return 0;
        }

        int adjust = 0;
        if (gp2.dpad_up) {
            adjust++;
        }
        if (gp2.dpad_down) {
            adjust--;
        }
        return (10 * (adjust));
    }

    // Example claw button
    public boolean clawButton() {
        if (gp1 == null) {
            return false;
        }
        return gp1.b;
    }
}
