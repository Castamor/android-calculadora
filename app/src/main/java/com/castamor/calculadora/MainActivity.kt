package com.castamor.calculadora

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.castamor.calculadora.databinding.ActivityMainBinding
import com.ezylang.evalex.Expression


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnCero = binding.btnCero
        val btnUno = binding.btn1
        val btnDos = binding.btn2
        val btnTres = binding.btn3
        val btnCuatro = binding.btn4
        val btnCinco = binding.btn5
        val btnSeis = binding.btn6
        val btnSiete = binding.btn7
        val btnOcho = binding.btn8
        val btnNueve = binding.btn9
        val btnPunto = binding.btnPunto

        val btnSuma = binding.btnSumar
        val btnResta = binding.btnMenos
        val btnMult = binding.btnMultiplicar
        val btnDividir = binding.btnDividir
        val btnIgual = binding.btnIgual

        val btnLimpiar = binding.btnLimpiar
        val btnEliminar = binding.btnEliminar

        val operaciones = binding.operacion
        val resultado = binding.resultado

        val btnRaiz = binding.btnRaiz
        val btnFactorial = binding.btnFactorial
        val btnExponente = binding.btnExponente

        var opAvanzada = false

        fun eventoClick(componente: Button) {
            componente.setOnClickListener {
                var texto: String

                if (!opAvanzada) {
                    if (textoDelView(operaciones).contains("( )")) {
                        opAvanzada = true
                        val indexInicio = textoDelView(operaciones).lastIndexOf("(")+1
                        texto = textoDelView(operaciones).reemplazarCaracterEnIndex(indexInicio, texto(componente))
                    } else if (textoDelView(operaciones).contains("( , 2)")) {
                        opAvanzada = true
                        val indexInicio = textoDelView(operaciones).lastIndexOf("(")+1
                        texto = textoDelView(operaciones).reemplazarCaracterEnIndex(indexInicio, texto(componente))
                    } else {
                        texto = textoDelView(operaciones) + texto(componente)
                    }
                } else {
                    val indexFinal = textoDelView(operaciones).lastIndexOf(",")-1
                    texto = textoDelView(operaciones).reemplazarCaracterEnIndex(indexFinal, texto(componente) + " ")
                }

                operaciones.setText(texto)
            }
        }

        fun eventoClickOperador(componente: Button) {
            fun agregarOperador(operador: String) {
                // Si hay resultados, eliminarlos y pasarlos a la sección de operaciones
                if (textoDelView(resultado).isNotEmpty()) {
                    operaciones.setText("" + textoDelView(resultado))
                    resultado.setText("")
                }

                // Tiene que haber algo antes si no, no agrega ningún operador
                if (textoDelView(operaciones).isNotEmpty()) {
                    opAvanzada = false
                    // Agregar el operador
                    operaciones.setText(textoDelView(operaciones) + "${operador
                        .agregarCaracterInicio(" ")
                        .agregarCaracterFinal(" ")
                    }"
                    )
                }
            }

            componente.setOnClickListener { agregarOperador(textoDelView(componente)) }
        }

        fun eventoClickOperadorAvanzado(componente: Button, texto: String = "") {

                componente.setOnClickListener {
                    if (textoDelView(operaciones).isEmpty() || textoDelView(operaciones).endsWith(" ")) {

                        if (texto == "Raiz") {
                            operaciones.setText(textoDelView(operaciones) + "$texto( , 2)")
                            opAvanzada = true
                        } else {
                            operaciones.setText(textoDelView(operaciones) + "$texto( )")
                        }
                    }
                }
        }

        fun eventoClickEliminarCaracter(componente: Button) {
            componente.setOnClickListener {

                if(textoDelView(operaciones).length > 0) {
                    opAvanzada = false
                    if (textoDelView(operaciones).endsWith(")")) {
                        val i = textoDelView(operaciones).lastIndexOf(" ")
                        if (i == -1) {
                            operaciones.setText("")
                        } else {
                            operaciones.setText(textoDelView(operaciones).eliminarCaracteres(i))
                        }
                    } else if (textoDelView(operaciones).endsWith(" ")) {
                        operaciones.setText(textoDelView(operaciones).eliminarCaracteres(3))
                    } else {
                        operaciones.setText(textoDelView(operaciones).eliminarCaracteres(1))
                    }
                }
            }
        }

        fun eventoClickLimpiar(componente: Button) {
            componente.setOnClickListener {
                opAvanzada = false
                operaciones.setText("")
                resultado.setText("")
            }
        }

        fun eventoClickIgual(componente: Button) {

            componente.setOnClickListener {

                if (
                    (textoDelView(operaciones).isNotEmpty() ||
                    textoDelView(operaciones).endsWith(")")) &&
                    !textoDelView(operaciones).endsWith(" ") &&
                    !textoDelView(operaciones).contains("/ 0") &&
                    !textoDelView(operaciones).contains("( )") &&
                    !textoDelView(operaciones).contains("(.)") &&
                    !textoDelView(operaciones).contains("( ^ )") &&
                    !textoDelView(operaciones).contains("( , 2)") &&
                    !textoDelView(operaciones).contains("(. , 2)")
                ) {
                    opAvanzada = false
                    val res = calcularResultado(textoDelView(operaciones))
                    resultado.setText(res)
                } else {
                    Notificacion("Operacion no válida.")
                }
            }
        }

        eventoClick(btnCero)
        eventoClick(btnUno)
        eventoClick(btnDos)
        eventoClick(btnTres)
        eventoClick(btnCuatro)
        eventoClick(btnCinco)
        eventoClick(btnSeis)
        eventoClick(btnSiete)
        eventoClick(btnOcho)
        eventoClick(btnNueve)
        eventoClick(btnPunto)

        eventoClickOperador(btnSuma)
        eventoClickOperador(btnResta)
        eventoClickOperador(btnMult)
        eventoClickOperador(btnDividir)
        eventoClickOperador(btnExponente)
        eventoClickOperadorAvanzado(btnRaiz, "Raiz")
        eventoClickOperadorAvanzado(btnFactorial, "Factorial")

        eventoClickEliminarCaracter(btnEliminar)
        eventoClickLimpiar(btnLimpiar)

        eventoClickIgual(btnIgual)

        binding.btnSalir.setOnClickListener { finish() }
    }
}