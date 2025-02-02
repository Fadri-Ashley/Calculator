package project1.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import project1.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var firstnumber = ""
    private var currentNumber = ""
    private var currentOperator = ""
    private var result = ""
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        binding.apply {
            binding.LayoutMain.children.filterIsInstance<Button>().forEach { button ->
                button.setOnClickListener {
                    val buttonText = button.text.toString()
                    when{
                        buttonText.matches(Regex("[0-9]"))->{
                            if(currentOperator.isEmpty()){
                                firstnumber+=buttonText
                                tvResult.text = firstnumber
                            }else{
                                currentNumber+=buttonText
                                tvResult.text = currentNumber
                            }
                        }
                        buttonText.matches(Regex("[+\\-*/]"))->{
                            currentNumber = ""
                            if (tvResult.text.toString().isNotEmpty()){
                                currentOperator = buttonText
                                tvResult.text = ""
                            }
                        }
                        buttonText == "="->{
                            if (currentNumber.isNotEmpty() && currentOperator.isNotEmpty()){
                                tvFormula.text = "$firstnumber$currentOperator$currentNumber"
                                result = evaluateExpression(firstnumber,currentNumber,currentOperator)
                                firstnumber = result
                                tvResult.text = result
                            }
                        }
                        buttonText == "."->{
                            if(currentOperator.isEmpty()){
                                if (! firstnumber.contains(".")){
                                    firstnumber += if(firstnumber.isEmpty()) "0$buttonText" else buttonText
                                    tvResult.text = firstnumber
                                }
                            }else {
                                if (! currentNumber.contains(".")){
                                    currentNumber += if(currentNumber.isEmpty()) "0$buttonText" else buttonText
                                    tvResult.text = currentNumber
                                }
                            }
                        }
                        buttonText == "C"->{
                            currentNumber = ""
                            firstnumber = ""
                            currentOperator = ""
                            tvResult.text = "0"
                            tvFormula.text = ""
                        }
                    }
                }
            }

        }
    }
    private fun evaluateExpression(firstNumber: String, secondNumber: String, operator: String):String
    {
        val num1 = firstNumber.toDouble()
        val num2 = secondNumber.toDouble()
        return when(operator){
            "+"-> (num1+num2).toString()
            "-"-> (num1-num2).toString()
            "*"-> (num1*num2).toString()
            "/"-> (num1/num2).toString()
            else ->""
        }
    }


}

