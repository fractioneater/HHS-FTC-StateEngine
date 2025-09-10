package org.firstinspires.ftc.teamcode.stateengine

abstract class StackList(private val stack: Array<State>) {
  fun initStack() {
    for (state in this.stack) {
      state.init()
    }
  }
}
