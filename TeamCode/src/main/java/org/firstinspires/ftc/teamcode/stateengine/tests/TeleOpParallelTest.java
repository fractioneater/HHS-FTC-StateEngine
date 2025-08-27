package org.firstinspires.ftc.teamcode.stateengine.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.stateengine.ParallelStack;
import org.firstinspires.ftc.teamcode.stateengine.State;
import org.firstinspires.ftc.teamcode.states.teleop.examples.DriveTeleop;


@TeleOp(name="Parallel Test", group="Testing")
public class TeleOpParallelTest extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private RobotHardware rh = new RobotHardware(this);


    @Override
    public void runOpMode() {

        rh.initialize();

        ParallelStack stack = new ParallelStack();
        State[] states = {
                new DriveTeleop(),
        };
        stack.createStack(states);

        stack.init(rh);

        // Wait for the game to start (driver presses PLAY)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            stack.run();

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }
    }}
