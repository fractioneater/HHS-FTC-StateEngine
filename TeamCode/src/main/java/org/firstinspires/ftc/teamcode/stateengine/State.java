package org.firstinspires.ftc.teamcode.stateengine;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

public interface State {
  void init(RobotHardware rh);
  void run();
  void stop();
  boolean isDone();
}
