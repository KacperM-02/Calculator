package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculator.databinding.ActivitySimpleCalculatorBinding

class SimpleCalculatorActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySimpleCalculatorBinding
    private var firstNumber: String = "0"
    private var secondNumber: String = "0"
    private var operation: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimpleCalculatorBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun numberButtonOnClickFunction(view: View)
    {
        if(view is Button)
        {
            if(view.text == ".")
            {
                if(operation == null)
                {
                    if(firstNumber.length >= 7) return
                    if(!firstNumber.contains("."))
                    {
                        binding.display.append(".")
                        firstNumber += "."
                    }

                    return
                }
                else
                {
                    if(secondNumber.length >= 7) return
                    if(secondNumber == "0")
                    {
                        binding.display.text = "0"
                    }
                    if(!secondNumber.contains("."))
                    {
                        binding.display.append(".")
                        secondNumber += "."
                    }
                }
            }
            else
            {
                if(operation == null)
                {
                    if(firstNumber.length >= 7) return
                    if(firstNumber == "0")
                    {
                        if(view.text == "0") return
                        binding.display.text = view.text
                        firstNumber = view.text.toString()
                        return
                    }
                    else
                    {
                        binding.display.append(view.text)
                        firstNumber += view.text.toString()
                        return
                    }
                }

                if(secondNumber.length >= 7) return
                if(secondNumber == "0")
                {
                    binding.display.text = view.text
                    secondNumber = view.text.toString()
                    return
                }

                binding.display.append(view.text)
                secondNumber += view.text.toString()
            }
        }
    }

    fun functionalButtonOnClickFunction(view: View)
    {
        if(view is Button)
        {
            if(view.text == "=") return
            if(operation != null)
            {
                if(view.text.toString() == operation)
                {
                    setFunctionalButtonUnclicked()
                    operation = null
                    return
                }
                setFunctionalButtonUnclicked()
            }

            view.setBackgroundResource(R.drawable.functional_button_clicked)
            operation = view.text.toString()
        }
    }

    fun allClearOnClickFunction(view: View)
    {
        setFunctionalButtonUnclicked()
        binding.display.text = "0"
        firstNumber = "0"
        secondNumber = "0"
        operation = null
    }

    private fun setFunctionalButtonUnclicked()
    {
        binding.division.setBackgroundResource(R.drawable.functional_button_unclicked)
        binding.multiplication.setBackgroundResource(R.drawable.functional_button_unclicked)
        binding.subtraction.setBackgroundResource(R.drawable.functional_button_unclicked)
        binding.addition.setBackgroundResource(R.drawable.functional_button_unclicked)
    }

    fun backSpaceOnClickFunction(view: View)
    {
        val length : Int

        if(operation != null)
        {
            length = secondNumber.length
            if(length > 1)
            {
                binding.display.text = binding.display.text.substring(0, length - 1)
                secondNumber = secondNumber.substring(0, length - 1)
            }
            else
            {
                binding.display.text = "0"
                secondNumber = "0"
            }

            return
        }

        length = firstNumber.length
        if(length > 1)
        {
            binding.display.text = binding.display.text.substring(0, length - 1)
            firstNumber = firstNumber.substring(0, length - 1)
        }
        else
        {
            binding.display.text = "0"
            firstNumber = "0"
        }
    }

    fun equalsAction(view: View)
    {
        setFunctionalButtonUnclicked()
        val result = getResult()
        binding.display.text = when(result)
        {
            "NaN", "+Infinity", "-Infinity", "Infinity" ->
            {
                firstNumber = "0"
                "Error"
            }
            else ->
            {
                firstNumber = result
                result
            }
        }

        secondNumber = "0"
        operation = null
    }

    private fun getResult(): String
    {
        var result: String

        result = when (operation)
        {
            "/" -> division()
            "x" -> (firstNumber.toFloat() * secondNumber.toFloat()).toString()
            "-" -> (firstNumber.toFloat() - secondNumber.toFloat()).toString()
            "+" -> (firstNumber.toFloat() + secondNumber.toFloat()).toString()
            else -> firstNumber
        }

        if(result.endsWith(".0"))
        {
            result = result.replace(".0","")
        }
        return result
    }

    private fun division(): String
    {
        if(secondNumber == "0" || operation == null)
        {
            return "Error"
        }

        return (firstNumber.toFloat() / secondNumber.toFloat()).toString()
    }

    fun plusMinusFunction(view: View)
    {
        if(operation == null)
        {
            if(firstNumber == "0") return
            firstNumber = if(firstNumber.startsWith("-")) {
                firstNumber.substring(1)
            } else {
                "-$firstNumber"
            }

            binding.display.text = firstNumber
        }
        else
        {
            if(secondNumber == "0") return
            secondNumber = if(secondNumber.startsWith("-")) {
                secondNumber.substring(1)
            } else {
                "-$secondNumber"
            }

            binding.display.text = secondNumber
        }
    }
}
