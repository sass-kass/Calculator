package com.example.calculator

import com.example.calculator.rnp.ExpressionToRnp
import java.lang.Exception
import java.util.*

class RnpRunner(private val valueProvider: ValueProvider? = null) {

    private val expressionConverter = ExpressionToRnp()

    fun calculate(expression: String): Double? {
        if (expression.trim().isEmpty())
            return null
        val rpnArray = expressionConverter.convert(expression)
        val stack = Stack<Double>()
        var nextIndex = 1
        var negative: Boolean = false

        for (element in rpnArray) {

            when (element) {
                "+" -> {
                    if(negative){
                        val res = this.getElementNegativeValue(stack)
                        stack.push(res!!.second + res.first)
                        negative = false
                    } else {
                        val res = this.getElementValue(stack)
                        stack.push(res!!.second + res.first)
                    }
                }
                "-" -> {
                    if(nextIndex < rpnArray.size  && !stack.empty()
                        && (rpnArray[nextIndex] == "-" || rpnArray[nextIndex] == "×" || rpnArray[nextIndex] == "÷")) {
                        negative = true
                        nextIndex++
                        continue
                    }
                    if(negative){
                        val res = this.getElementNegativeValue(stack)
                        stack.push(res!!.second - res.first)
                        negative = false
                    } else {
                        val res = this.getElementValue(stack)
                        stack.push(res!!.second - res.first)
                    }
                }
                "×" -> {
                    if(negative){
                        val res = this.getElementNegativeValue(stack)
                        stack.push(res!!.second * res.first)
                        negative = false
                    } else {
                        val res = this.getElementValue(stack)
                        stack.push(res!!.second * res.first)
                    }
                }
                "÷" -> {
                    if(negative){
                        val res = this.getElementNegativeValue(stack)
                        stack.push(res!!.second / res.first)
                        negative = false
                    } else {
                        val res = this.getElementValue(stack)
                        stack.push(res!!.second / res.first)
                    }
                }
                else -> {
                    stack.push(this.getDoubleValue(element))
                }
            }
            nextIndex++
        }
        return stack.pop()
    }

    private fun getDoubleValue(element: String): Double {
        val value = doubleOrString(element)

        return if (value is Number) {
            value as Double
        } else {
            this.valueProvider?.getValue(value as String)!!
        }
    }

    private fun getElementValue(stack: Stack<Double>): Pair<Double, Double>? {
        if (stack.isEmpty())
            return null
        val first: Double?
        val second: Double?

        var value = doubleOrString(stack.pop())
        first = if (value is Number) {
            value as Double
        } else {
            this.valueProvider?.getValue(value as String)
        }

        value = doubleOrString(stack.pop())
        second = if (value is Number) {
            value as Double
        } else {
            this.valueProvider?.getValue(value as String)
        }

        return Pair(first!!, second!!)
    }

    private fun getElementNegativeValue(stack: Stack<Double>): Pair<Double, Double>? {
        if (stack.isEmpty())
            return null
        val first: Double?
        val second: Double?

        var value = doubleOrString(stack.pop())
        first = getNegativeValue(value.toString())

        value = doubleOrString(stack.pop())
        second = if (value is Number) {
            value as Double
        } else {
            this.valueProvider?.getValue(value as String)
        }

        return Pair(first!!, second!!)
    }

    private fun doubleOrString(element: Any) = try {
        element.toString().toDouble()
    } catch (e: NumberFormatException) {
        element
    }

    fun getNegativeValue(variableName: String): Double {
        var value = "-"
        value += variableName
        return value.toDouble()
    }

}

interface ValueProvider {
    fun getValue(variableName: String): Double
}