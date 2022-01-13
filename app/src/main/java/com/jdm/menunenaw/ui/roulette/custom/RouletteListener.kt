package com.jdm.menunenaw.ui.roulette.custom

interface RouletteListener {
    /**돌림판 회전이 끝나고, 화살표가 가리키는 데이터 Name 리턴 */
    fun getRotateEndResult(result: String)

    /**돌림판 회전 중 계산 되는 데이터 Name 리턴*/
    fun getRotatingResult(data: String)
}