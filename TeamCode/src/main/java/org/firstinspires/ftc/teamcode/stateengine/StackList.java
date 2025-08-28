package org.firstinspires.ftc.teamcode.stateengine;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

public abstract class StackList {

  private State[] stack;

  public void createStack(State[] stack) { this.stack = stack; }

  public State[] getStack() { return stack; }

  public void initStack(RobotHardware rh) {
    for (State state : getStack()) {
      state.init(rh);
    }
  }
}
