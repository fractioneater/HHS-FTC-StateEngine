package org.firstinspires.ftc.teamcode.teamcode.states.teleop;


import org.firstinspires.ftc.teamcode.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.teamcode.stateengine.State;
public class SampleStateTeleop implements State {

    // creates a reference to the main RobotHardware object for easy access
    RobotHardware rh = null;

    @Override
    public void init(RobotHardware rh) {
        this.rh = rh; // makes rh reference the main opmode's rh
        // there probably won't be any need to init anything in the teleop states
    }

    @Override
    public void run() {
        // the functions that call hardware functions are put here

        //todo: DO NOT have code editing the hardware here!
        //      That should all be done by calling hardware functions!
    }


    /// teleop states will probably run for the entire teleop stage so they won't need to stop
    @Override
    public boolean isDone() {
        return false;
    }
}
