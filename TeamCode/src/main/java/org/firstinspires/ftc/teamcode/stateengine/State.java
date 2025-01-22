package org.firstinspires.ftc.teamcode.teamcode.stateengine;

import org.firstinspires.ftc.teamcode.teamcode.hardware.RobotHardware;

public interface State {

    void init(RobotHardware rh);
    
    void run();
    
    void stop();
    
    boolean isDone();

}
