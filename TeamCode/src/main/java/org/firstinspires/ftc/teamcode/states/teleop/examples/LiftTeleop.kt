package org.firstinspires.ftc.teamcode.states.teleop.examples;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.stateengine.State;

public class LiftTeleop implements State {

  RobotHardware rh = null;

  @Override
  public void init(RobotHardware rh) { this.rh = rh; }

  @Override
  public void run() {
    rh.exampleLiftH.setLiftPosition(rh.controls.exampleLiftSpeed());
  }

  @Override
  public void stop() {
    // TODO.
  }

  @Override
  public boolean isDone() { return false; }
}
