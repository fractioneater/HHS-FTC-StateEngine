package org.firstinspires.ftc.teamcode.opmodes.teleop

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.stateengine.State
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

  fun thisIsReallyStupid(states6383: Array<State>, states15317: Array<State>): Array<State> {
    // Wait, I could've used try exceptions in the main body.
    return try {
      if (rh.op.hardwareMap.get("this-is-6383") != null) states6383
      else states15317
    } catch (_: Exception) {
      states15317
    }
  }

  override fun runOpMode() {
    rh.initialize()

    val states6383 = arrayOf(DriveTState(), TurretTState(), AimTState())
    val states15317 = arrayOf(DriveTState(), TurretTState(), AimTState(), SortTState())

    val states = thisIsReallyStupid(states6383, states15317)
    val stack = ParallelStack(states)

    stack.init(rh)

    rh.telemetry()

    // Wait for the game to start (driver presses PLAY)
    telemetry.addLine("initialized")
    telemetry.update()

    waitForStart()
    rh.runtime.reset()
    rh.sortH?.initializeLater()

    // Run until the end of the match (driver presses STOP)
    while (opModeIsActive()) {
      stack.run()
      rh.update()
      rh.controls.updateButtonControls()

      telemetry.addLine("runtime: ${rh.runtime}")
      rh.telemetry()
      telemetry.update()
    }
  }
}
