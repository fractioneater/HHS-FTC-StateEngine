package org.firstinspires.ftc.teamcode.teamcode.stateengine.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.teamcode.stateengine.State;
import org.firstinspires.ftc.teamcode.teamcode.states.teleop.examples.DriveTeleop;


@TeleOp(name="TeleOp Test", group="Testing")
public class TeleOpTest extends LinearOpMode {

    // Declare OpMode members for each of the 4 motors.
    private ElapsedTime runtime = new ElapsedTime();
    private RobotHardware rh = new RobotHardware(this);


    @Override
    public void runOpMode() {

        rh.initialize();

        State driveTest = new DriveTeleop();
        driveTest.init(rh);


        // Wait for the game to start (driver presses PLAY)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            if (!driveTest.isDone()) {
                driveTest.run();
            }
            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }
    }}
