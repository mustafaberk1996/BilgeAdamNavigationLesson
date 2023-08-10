package com.example.navigationlesson.data.state

sealed class AdapterState {
    object Idle:AdapterState()
    class Added(val index: Int):AdapterState()
    class Changed(val index:Int):AdapterState()
    class Removed(val index:Int):AdapterState()
}
