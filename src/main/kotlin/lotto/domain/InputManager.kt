package lotto.domain

import camp.nextstep.edu.missionutils.Console
import lotto.validation.CheckInputValidation
import lotto.validation.ExceptionMessageManager

class InputManager {
    private val checkInputValidation = CheckInputValidation()
    private val exceptionManager = ExceptionMessageManager()

    fun inputPurchaseCost(): Int? {
        return try {
            val userInput = getUserInput()
            val cost = makePurchaseCost(userInput)
            checkInputValidation.checkIsCorrectCost(cost)
            cost
        } catch (e: IllegalArgumentException) {
            exceptionManager.printErrorMessage(e.message)
            null
        }
    }

    fun inputLottoWinningNumber(): Set<Int>? {
        return try {
            val userInput = getUserInput()
            val lotto = splitUserInput(userInput)
            checkInputValidation.checkLottoCount(lotto)
            val numbers = makeLottoNumbers(lotto)
            numbers.toSet()
        } catch (e: IllegalArgumentException) {
            exceptionManager.printErrorMessage(e.message)
            null
        }
    }

    fun inputBonusNumber(winningNumber: Set<Int>): Int? {
        return try {
            val userInput = getUserInput()
            val bonusNumber = makeBonusNumber(userInput)
            checkInputValidation
                .checkBonusNumberDuplication(winningNumber, bonusNumber)
            bonusNumber
        } catch (e: IllegalArgumentException) {
            exceptionManager.printErrorMessage(e.message)
            null
        }
    }

    private fun getUserInput(): String {
        val userInput = Console.readLine()
        checkInputValidation.checkIsBlank(userInput)
        return userInput
    }

    private fun splitUserInput(
        userInput: String
    ): List<String> = userInput.split(SPLIT_SEPARATOR)

    private fun makePurchaseCost(
        userInput: String
    ): Int {
        checkInputValidation.apply {
            checkIsNumber(userInput)
            checkIsPositiveInteger(userInput)
            return userInput.toInt()
        }
    }

    private fun makeLottoNumbers(
        lotto: List<String>
    ): List<Int> {
        checkInputValidation.apply {
            val numbers = lotto.map { number ->
                checkIsNumber(number)
                checkIsLottoNumber(number)
                checkIsPositiveInteger(number)
                number.toInt()
            }
            checkDuplication(numbers)
            return numbers
        }
    }

    private fun makeBonusNumber(userInput: String): Int {
        checkInputValidation.apply {
            checkIsNumber(userInput)
            checkIsPositiveInteger(userInput)
        }
        return userInput.toInt()
    }

    companion object {
        private const val SPLIT_SEPARATOR = ","
    }
}