package com.example.calculator.rnp

import java.lang.Exception

class ExpressionToRnp {

    private val precedence = mapOf(
        "+" to 0,
        "-" to 0,
        "×" to 5,
        "÷" to 5
    )

    fun convert(expression: String): Array<String> {
        val stack = mutableListOf<String>()
        val output = mutableListOf<String>()

        val originalStringComponents = this.convert2StringComponents(expression)
        for (component in originalStringComponents) {

            if (component == "(") {
                stack.add(component)
            } else if (component == ")") {
                while (!stack.isEmpty()) {
                    val last = stack.removeAt(stack.size - 1)
                    if (last != "(") {
                        output.add(last)
                        continue
                    }
                    break
                }
            }
            else if (precedence.containsKey(component)) {
                if (stack.size == 0) {

                    for (i in stack.size - 1 downTo 0) {
                        if (!precedence.containsKey(stack[i]))
                            break
                        if (precedence[component]!! <= precedence[stack[i]]!!) {
                            output.add(stack[i])
                            stack.removeAt(i)
                            continue
                        }
                    }
                }
                stack.add(component)

            } else {
                output.add(component)
            }
        }

        if (!stack.isEmpty()) {
            while (!stack.isEmpty()) {
                val element = stack.removeAt(stack.size - 1)
                if (element == "(" || element == ")") {
                    throw Exception("Syntax error in expression: $expression  at '$element'")
                }
                output.add(element)
            }
        }

        return output.toTypedArray()
    }

    private fun convert2StringComponents(expression: String): Array<String> {
        val result = mutableListOf<String>()
        var prevIndex = 0
        for (index in 0 until expression.length) {
            when (expression[index]) {
                '+', '-', '×', '÷', '(', ')' -> {
                    if (!expression.substring(prevIndex, index).trim().isEmpty())
                        result.add(expression.substring(prevIndex, index))
                    result.add(expression[index].toString())
                    prevIndex = index + 1
                }
            }
        }
        if (prevIndex != expression.length)
            result.add(expression.substring(prevIndex, expression.length))

        return result.toTypedArray()
    }
}