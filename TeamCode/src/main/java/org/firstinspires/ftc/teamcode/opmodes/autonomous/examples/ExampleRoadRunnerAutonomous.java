package org.firstinspires.ftc.teamcode.teamcode.opmodes.autonomous.examples;

import com.acmerobotics.roadrunner.Pose2d;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.teamcode.stateengine.SeriesStack;
import org.firstinspires.ftc.teamcode.teamcode.stateengine.State;
import org.firstinspires.ftc.teamcode.teamcode.states.autonomous.*;


@Autonomous(name="RoadRunner Testing", group="Examples")
public class ExampleRoadRunnerAutonomous extends LinearOpMode {

    private RobotHardware rh = new RobotHardware(this);

    @Override
    public void runOpMode() {

        rh.initialize();

        SeriesStack stack = new SeriesStack();
        State[] states = {
                new SplinePathRR().createPath(
                        new Pose2d[] {
                                new Pose2d(0,0,0), // start Pose2d
                                new Pose2d(20,20,0),
                                new Pose2d(20,-20,0), // start Pose2d of next SplinePathRR
                        }
                ),
                new SplinePathRR().createPath(
                        new Pose2d[] {
                                new Pose2d(20,-20,0), // start Pose2d
                                new Pose2d(40,0,0),
                                new Pose2d(0,0,0), // start Pose2d of next SplinePathRR
                        }
                ),
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

            if (!stack.isDone()) {
                stack.run();
                rh.update();
            }

            telemetry.addData("Status", "Run Time: " + rh.runtime.toString());

            rh.telemetry();

            telemetry.update();
        }
    }}
