package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.stateengine.SeriesStack;
import org.firstinspires.ftc.teamcode.stateengine.State;


@Autonomous(name = "Sample Series OpMode", group = "Samples")
public class SampleOpMode extends LinearOpMode {

    private RobotHardware rh = new RobotHardware(this);

    @Override
    public void runOpMode() {

        rh.initialize();

        SeriesStack stack = new SeriesStack();
        State[] states = {
/*TODO
            Here you will setup all your teleop states in this list
            The list is here for the opmode to loop through

            ex)
                new SampleTeleop(),
*/};
        stack.createStack(states);

        stack.init(rh);

        rh.telemetry();

        // Wait for the game to start (driver presses PLAY)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        rh.runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            stack.run();
            rh.update();

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + rh.runtime.toString());

            rh.telemetry();

            telemetry.update();
        }
    }
}
