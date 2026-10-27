package org.insbaixcamp.bmicalculatorxml

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.slider.Slider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.Locale

class MainActivity : AppCompatActivity() {

    // Valors inicials
    private var height = 186
    private var weight = 82

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }


        // -----------------------------
        // REFERÈNCIES A LA VISTA
        // -----------------------------

        val tilName = findViewById<TextInputLayout>(R.id.tilName)
        val etName = findViewById<TextInputEditText>(R.id.etName)

        val tvHeight = findViewById<TextView>(R.id.tvHeight)
        val sliderHeight = findViewById<Slider>(R.id.sliderHeight)

        val tvWeight = findViewById<TextView>(R.id.tvWeight)
        val btnMinus = findViewById<MaterialButton>(R.id.btnMinus)
        val btnPlus = findViewById<MaterialButton>(R.id.btnPlus)

        val btnCalculate = findViewById<MaterialButton>(R.id.btnCalculate)

        val cardResult = findViewById<MaterialCardView>(R.id.cardResult)
        val tvResultTitle = findViewById<TextView>(R.id.tvResultTitle)
        val tvBMI = findViewById<TextView>(R.id.tvBMI)
        val tvBMIStatus = findViewById<TextView>(R.id.tvBMIStatus)


        // -----------------------------
        // AMAGUEM EL RESULTAT AL PRINCIPI
        // -----------------------------

        cardResult.visibility = View.GONE


        // -----------------------------
        // ALÇADA
        // -----------------------------

        sliderHeight.addOnChangeListener { _, value, _ ->

            height = value.toInt()

            tvHeight.text = height.toString()
        }


        // -----------------------------
        // PES
        // -----------------------------

        btnMinus.setOnClickListener {

            if (weight > 30) {
                weight--
                tvWeight.text = weight.toString()
            }
        }


        btnPlus.setOnClickListener {

            if (weight < 250) {
                weight++
                tvWeight.text = weight.toString()
            }
        }


        // -----------------------------
        // CALCULAR IMC
        // -----------------------------

        btnCalculate.setOnClickListener {

            val name = etName.text.toString().trim()


            // Comprovem que hi hagi nom
            if (name.isEmpty()) {

                tilName.error = "Introdueix el teu nom"

                return@setOnClickListener

            } else {

                tilName.error = null
            }


            // Convertim cm a metres
            val heightMeters = height / 100.0


            // Fórmula IMC
            val bmi = weight / (heightMeters * heightMeters)


            // Mostrem el resultat
            tvResultTitle.text = "IMC de $name"

            tvBMI.text = String.format(
                Locale.getDefault(),
                "%.1f",
                bmi
            )


            // Categoria de l'IMC
            when {

                bmi < 18.5 -> {

                    tvBMIStatus.text = "●  Baix pes"

                    tvBMIStatus.setTextColor(
                        Color.parseColor("#6EA8FE")
                    )

                    tvBMIStatus.backgroundTintList =
                        ColorStateList.valueOf(
                            Color.parseColor("#17345C")
                        )
                }


                bmi < 25 -> {

                    tvBMIStatus.text = "●  Pes saludable"

                    tvBMIStatus.setTextColor(
                        Color.parseColor("#54E69A")
                    )

                    tvBMIStatus.backgroundTintList =
                        ColorStateList.valueOf(
                            Color.parseColor("#193D31")
                        )
                }


                bmi < 30 -> {

                    tvBMIStatus.text = "●  Sobrepès"

                    tvBMIStatus.setTextColor(
                        Color.parseColor("#FFB84D")
                    )

                    tvBMIStatus.backgroundTintList =
                        ColorStateList.valueOf(
                            Color.parseColor("#4A3517")
                        )
                }


                else -> {

                    tvBMIStatus.text = "●  Obesitat"

                    tvBMIStatus.setTextColor(
                        Color.parseColor("#FF6B78")
                    )

                    tvBMIStatus.backgroundTintList =
                        ColorStateList.valueOf(
                            Color.parseColor("#4A2028")
                        )
                }
            }


            // Fem visible la targeta de resultat
            cardResult.visibility = View.VISIBLE
        }
    }
}