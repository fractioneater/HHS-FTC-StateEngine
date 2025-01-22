package org.firstinspires.ftc.teamcode.teamcode.stateengine;

import org.firstinspires.ftc.teamcode.teamcode.hardware.RobotHardware;

public class ParallelStack extends StackList implements State {

    private boolean isDone = false;

    @Override
    public void init(RobotHardware rh) {
        super.initStack(rh);
    }

    @Override
    public void run() {
        boolean done = true;

        for (State state : getStack()) {
            if (!state.isDone()) {
                state.run();
                done = false;
            }
        }

        // when all of the states are done,
        // this stack will be done
        if (done) {
            isDone = true;
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isDone() {
        return isDone;
    }
}
