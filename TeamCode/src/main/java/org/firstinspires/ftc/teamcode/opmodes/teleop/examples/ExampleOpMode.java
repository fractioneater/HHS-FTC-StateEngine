package org.firstinspires.ftc.teamcode.opmodes.teleop.examples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.stateengine.ParallelStack;
import org.firstinspires.ftc.teamcode.stateengine.State;
import org.firstinspires.ftc.teamcode.states.teleop.examples.ClawTeleop;
import org.firstinspires.ftc.teamcode.states.teleop.examples.DriveTeleop;
import org.firstinspires.ftc.teamcode.states.teleop.examples.LiftTeleop;


@TeleOp(name="Example OpMode", group="Examples")
public class ExampleOpMode extends LinearOpMode {

    private RobotHardware rh = new RobotHardware(this);

    @Override
    public void runOpMode() {

        rh.initialize();

        ParallelStack stack = new ParallelStack();
        State[] states = {
                new DriveTeleop(),
                new ClawTeleop(),
                new LiftTeleop(),

        };
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

            telemetry.addData("Status", "Run Time: " + rh.runtime.toString());

            rh.telemetry();

            telemetry.update();
        }
    }}
