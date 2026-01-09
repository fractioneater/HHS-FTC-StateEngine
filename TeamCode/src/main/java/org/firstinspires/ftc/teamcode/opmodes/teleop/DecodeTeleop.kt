package org.firstinspires.ftc.teamcode.opmodes.teleop

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.hardware.RobotHardware
import org.firstinspires.ftc.teamcode.stateengine.ParallelStack
import org.firstinspires.ftc.teamcode.states.teleop.AimTState
import org.firstinspires.ftc.teamcode.states.teleop.DriveTState
import org.firstinspires.ftc.teamcode.states.teleop.SortTState
import org.firstinspires.ftc.teamcode.states.teleop.TurretTState

@Suppress("unused")
@TeleOp(name = "2025-26 Decode TELEOP", group = "\"maybe the top?\"")
class DecodeTeleop : LinearOpMode() {
  private val rh = RobotHardware(this)

  override fun runOpMode() {
    rh.initialize()

    val states = arrayOf(DriveTState(), TurretTState(), AimTState(), SortTState())
    val stack = ParallelStack(states)

    stack.init(rh)

    rh.telemetry()

    // Wait for the game to start (driver presses PLAY)
    telemetry.addLine("initialized")
    telemetry.update()

    waitForStart()
    rh.runtime.reset()

    // Run until the end of the match (driver presses STOP)
    while (opModeIsActive()) {
      stack.run()
      rh.update()

      telemetry.addLine("runtime: ${rh.runtime}")
      rh.telemetry()
      telemetry.update()
    }
  }
}
