package com.castamor.calculadora

import android.app.Activity
import android.content.Context
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.ezylang.evalex.Expression
import com.ezylang.evalex.bigmath.functions.bigdecimalmath.BigMathCosFunction
import com.ezylang.evalex.bigmath.functions.bigdecimalmath.BigMathFactorialFunction
import com.ezylang.evalex.bigmath.functions.bigdecimalmath.BigMathRootFunction
import com.ezylang.evalex.bigmath.functions.bigdecimalmath.BigMathSinFunction
import com.ezylang.evalex.bigmath.operators.bigdecimalmath.BigMathInfixPowerOfOperator
import com.ezylang.evalex.config.ExpressionConfiguration
import com.ezylang.evalex.functions.FunctionIfc
import com.ezylang.evalex.operators.OperatorIfc
import java.util.Map

fun Activity.Notificacion(texto: String) {
    Toast.makeText(this, texto, Toast.LENGTH_SHORT).show()
}

fun texto(componente: Button): String {
    return componente.text.toString()
}

fun textoDelView(componente: TextView): String {
    return componente.text.toString()
}

fun calcularResultado(string: String): String {
    val parametros = ExpressionConfiguration.defaultConfiguration()
        .withAdditionalFunctions(
            Map.entry<String, FunctionIfc>("Factorial", BigMathFactorialFunction()),
            Map.entry<String, FunctionIfc>("Raiz", BigMathRootFunction())
        )
        .withAdditionalOperators(
            Map.entry<String, OperatorIfc>("^", BigMathInfixPowerOfOperator())
        )

    var res = Expression(string, parametros).evaluate().getNumberValue().toString()

    if (res.contains('.')) {
        val float = res.toFloat()
        res = String.format("%.3f", float)
    }

    return res
}

fun String.agregarCaracterInicio(caracter: String): String {
    return "$caracter$this"
}

fun String.agregarCaracterFinal(caracter: String): String {
    return "$this$caracter"
}

fun String.eliminarCaracteres(cantidad: Int): String {
    return this.substring(0, this.length - cantidad)
}

fun String.reemplazarCaracterEnIndex(index: Int, nuevoCaracter: String): String {
    return substring(0, index) + nuevoCaracter + substring(index + 1, length)
}