package org.firstinspires.ftc.teamcode.teamcode.stateengine;


import org.firstinspires.ftc.teamcode.teamcode.hardware.RobotHardware;

public class SeriesStack extends StackList implements State {

    private int stackIndex = 0;


    @Override
    public void init(RobotHardware rh) {
        super.initStack(rh);
    }

    @Override
    public void run() {

        // go to next State if current State is done
        if (getStack()[stackIndex].isDone()) {
            stackIndex++;
        }

        // run State
        if (!isDone()) {
            getStack()[stackIndex].run();
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isDone() {
        // when this stack has finished all States,
        // this stack will be done
        return stackIndex == getStack().length;
    }
}
