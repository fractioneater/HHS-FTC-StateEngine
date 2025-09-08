package org.firstinspires.ftc.teamcode.stateengine;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

public class ParallelStack extends StackList implements State {

  private boolean stackIsDone = false;

  @Override
  public void init(RobotHardware rh) { super.initStack(rh); }

  @Override
  public void run() {
    if (stackIsDone) return;
    boolean done = true;

    for (State state : getStack()) {
      if (!state.isDone()) {
        state.run();
        done = false;
      }
    }

    // when all of the states are done, this stack will be done
    if (done) {
      stackIsDone = true;
    }
  }

  @Override
  public void stop() {
    stackIsDone = true;
  }

  @Override
  public boolean isDone() { return stackIsDone; }
}
