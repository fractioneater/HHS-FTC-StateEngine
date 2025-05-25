package org.firstinspires.ftc.teamcode.teamcode.states.autonomous;


import org.firstinspires.ftc.teamcode.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.teamcode.stateengine.State;
public class SampleStateAutonomous implements State {

    // creates a reference to the main RobotHardware object for easy access
    RobotHardware rh = null;

    @Override
    public void init(RobotHardware rh) {
        this.rh = rh; // makes rh reference the main opmode's rh
        // init anything needed for this state
        // but this will likely not be used for most states
    }

    @Override
    public void run() {
        // the functions that call hardware functions are put here

        /*TODO: DO NOT have code directly editing or accessing the hardware here!
        * That should be done by calling hardware functions!
        */
    }

    @Override
    public boolean isDone() {
        return false; //TODO: Update this function to return true when the state has finished running
    }
}
