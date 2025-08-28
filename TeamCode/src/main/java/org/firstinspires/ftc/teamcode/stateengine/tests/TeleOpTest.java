package org.firstinspires.ftc.teamcode.stateengine.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.stateengine.State;
import org.firstinspires.ftc.teamcode.states.teleop.examples.DriveTeleop;

@TeleOp(name = "TeleOp Test", group = "Testing")
public class TeleOpTest extends LinearOpMode {

  private final ElapsedTime runtime = new ElapsedTime();
  private final RobotHardware rh = new RobotHardware(this);

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

      telemetry.addData("Status", "Run Time: " + runtime.toString());
      telemetry.update();
    }
  }
}
