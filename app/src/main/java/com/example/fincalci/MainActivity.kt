package com.example.fincalci

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import java.util.Locale
import java.util.Stack

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = Color.Black) {
                CalculatorApp()
            }
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CalculatorApp() {
    val navController = rememberNavController()
    var standardHistory by remember { mutableStateOf(listOf<String>()) }
    var taxHistory by remember { mutableStateOf(listOf<String>()) }

    NavHost(navController = navController, startDestination = "standard") {
        composable("standard") {
            StandardCalculator(
                standardHistory = standardHistory,
                onHistoryUpdate = { updatedHistory -> standardHistory = updatedHistory },
                onNavigateToHistory = { navController.navigate("history/standard") },
                onNavigateToTax = { navController.navigate("tax") }
            )
        }
        composable("tax") {
            TaxCalculator(
                taxHistory = taxHistory,
                onHistoryUpdate = { updatedHistory -> taxHistory = updatedHistory },
                onNavigateToHistory = { navController.navigate("history/tax") },
                onNavigateToStandard = { navController.navigate("standard") }
            )
        }
        composable("history/{previousScreen}") { backStackEntry ->
            val previousScreen = backStackEntry.arguments?.getString("previousScreen")
            HistoryScreen(
                history = if (previousScreen == "standard") standardHistory else taxHistory,
                onNavigateBack = { navController.navigate(previousScreen!!) },
                onClearHistory = {
                    if (previousScreen == "standard") standardHistory = emptyList() else taxHistory = emptyList()
                }
            )
        }
    }
}
enum class CalculatorMode {
    Standard, Tax
}

@Composable
fun StandardCalculator(
    standardHistory: List<String>,
    onHistoryUpdate: (List<String>) -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToTax: () -> Unit
) {
    var displayText by remember { mutableStateOf("") }
    var resetDisplay by remember { mutableStateOf(false) }
    var lastResult by remember { mutableStateOf<String?>(null) }
    var currentEquation by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = displayText.ifEmpty { "0" },
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(16.dp),
            color = Color.White
        )

        val buttons = listOf(
            listOf("C", "⌫", "%", "/"),
            listOf("7", "8", "9", "*"),
            listOf("4", "5", "6", "-"),
            listOf("1", "2", "3", "+"),
            listOf("T", "0", ".", "=")
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            buttons.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    row.forEach { button ->
                        if (button.isNotEmpty()) {
                            if (button == "T") {
                                Button(
                                    onClick = onNavigateToTax,
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent,
                                        contentColor = MaterialTheme.colorScheme.onBackground
                                    ),
                                    shape = RectangleShape
                                ) {
                                    Text(
                                        fontSize = 24.sp,
                                        text = button,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                        color = Color.Yellow
                                    )
                                }
                            } else {
                                Button(
                                    onClick = {
                                        val (updatedText, shouldReset) = handleButtonClick(
                                            displayText,
                                            button,
                                            lastResult,
                                            resetDisplay
                                        )
                                        displayText = updatedText

                                        if (button != "=" && button != "C" && button != "⌫") {
                                            currentEquation += button
                                        }

                                        resetDisplay = shouldReset

                                        if (button == "=") {
                                            lastResult = updatedText
                                            if (updatedText != "Error") {
                                                onHistoryUpdate(standardHistory + "$currentEquation=$updatedText")
                                                currentEquation = ""
                                            } else {
                                                currentEquation = ""
                                            }
                                        } else if (button == "C") {
                                            currentEquation = ""
                                            displayText = ""
                                        } else if (button == "⌫"){
                                            if(currentEquation.isNotEmpty()){
                                                currentEquation = currentEquation.dropLast(1)
                                            }
                                            if(displayText.isNotEmpty()){
                                                displayText = displayText.dropLast(1)
                                            }
                                        }
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                        .animateContentSize(),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent,
                                        contentColor = MaterialTheme.colorScheme.onBackground
                                    ),
                                    shape = RectangleShape
                                ) {
                                    Text(
                                        fontSize = 24.sp,
                                        text = button,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White
                                    )
                                }
                            }
                        } else {
                            Spacer(modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f))
                        }
                    }
                }
            }
        }
        Button(onClick = onNavigateToHistory) {
            Text("View History")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField(income: String, onIncomeChange: (String) -> Unit) {
    OutlinedTextField(
        value = income,
        onValueChange = onIncomeChange,
        label = { Text("Enter your Income") },
        textStyle = TextStyle(color = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.Gray,
            cursorColor = Color.White,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.White
        )
    )
}

@Composable
fun TaxCalculator(
    taxHistory: List<String>,
    onHistoryUpdate: (List<String>) -> Unit,
    onNavigateToStandard: () -> Unit,
    onNavigateToHistory: () -> Unit
) {
    var income by remember { mutableStateOf("") }
    var tax by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Tax Calculator",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .animateContentSize(),
            color = Color.White
        )

        MyTextField(income = income, onIncomeChange = { newIncome ->
            income = newIncome.filter { char -> char.isDigit() || char == '.' }
        })

        Button(
            onClick = { tax = calculateTax(income.toDoubleOrNull() ?: 0.0)
                onHistoryUpdate(taxHistory + "Income: ₹$income, Tax: ₹$tax")
            },
            modifier = Modifier.padding(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary
            )
        ) {
            Text("Calculate Tax", style = MaterialTheme.typography.titleMedium)
        }

        AnimatedVisibility(
            visible = tax.isNotEmpty(),
            enter = fadeIn(animationSpec = tween(500)) + expandVertically(),
            exit = fadeOut(animationSpec = tween(500)) + shrinkVertically()
        ) {
            Text(
                text = "Tax: ₹$tax",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                color = Color.White
            )
        }

        OutlinedButton(
            onClick = onNavigateToStandard,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text("Switch to Standard Mode", style = MaterialTheme.typography.titleMedium)
        }
        Button(onClick = onNavigateToHistory) {
            Text("View History")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(history: List<String>, onNavigateBack: () -> Unit, onClearHistory: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("History", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onClearHistory) {
                        Icon(Icons.Default.Delete, contentDescription = "Clear History", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentColor = Color.White // Set default content color for the Scaffold
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.Black)
        ) {
            items(history) { entry ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors( // Use cardColors to set background and content color
                        containerColor =Color.Black, // or surface
                        contentColor = Color.White //or onSurface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        text = entry,
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}

fun calculateTax(income: Double): String {
    val tax: Double = when {
        income <= 300000 -> 0.0
        income <= 600000 -> 0.05 * (income - 300000)
        income <= 900000 -> 0.10 * (income - 600000) + 15000
        income <= 1200000 -> 0.15 * (income - 900000) + 45000
        income <= 1500000 -> 0.20 * (income - 1200000) + 90000
        else -> 0.30 * (income - 1500000) + 150000
    }
    return String.format(Locale.getDefault(), "%.2f", tax)
}

fun handleButtonClick(
    currentText: String,
    button: String,
    lastResult: String?,
    resetDisplay: Boolean
): Pair<String, Boolean> {
    return when (button) {
        "C" -> "" to false
        "⌫" -> if (currentText.isNotEmpty()) currentText.dropLast(1) to false else "" to false
        "%" -> {
            try {
                val lastNumber = currentText.takeLastWhile { it.isDigit() || it == '.' }
                if (lastNumber.isNotEmpty()) {
                    val percentage = lastNumber.toDouble() / 100
                    currentText.dropLast(lastNumber.length) + percentage.toString() to false
                } else {
                    currentText to false
                }
            } catch (e: Exception) {
                "Error" to false
            }
        }
        "=" -> {
            try {
                val result = evaluateExpression(currentText)
                val resultString = if (result % 1.0 == 0.0) result.toInt().toString() else result.toString()
                resultString to true
            } catch (e: Exception) {
                "Error" to false
            }
        }
        else -> {
            if (resetDisplay && button.all { it.isDigit() || it == '.' }) {
                button to false
            } else {
                (currentText + button) to (button == "=")
            }
        }
    }
}

fun evaluateExpression(expression: String): Double {
    val operators = Stack<Char>()
    val operands = Stack<Double>()

    var i = 0
    while (i < expression.length) {
        val char = expression[i]

        when {
            char.isDigit() || char == '.' -> {
                val sb = StringBuilder()
                while (i < expression.length && (expression[i].isDigit() || expression[i] == '.')) {
                    sb.append(expression[i])
                    i++
                }
                operands.push(sb.toString().toDouble())
                i--
            }
            char == '+' || char == '-' || char == '*' || char == '/' -> {
                while (operators.isNotEmpty() && hasPrecedence(char, operators.peek())) {
                    operands.push(applyOperator(operators.pop(), operands.pop(), operands.pop()))
                }
                operators.push(char)
            }
        }
        i++
    }

    while (operators.isNotEmpty()) {
        operands.push(applyOperator(operators.pop(), operands.pop(), operands.pop()))
    }

    return operands.pop()
}

fun hasPrecedence(currentOp: Char, prevOp: Char): Boolean {
    if (prevOp == '(' || prevOp == ')') return false
    return (currentOp != '*' && currentOp != '/') || (prevOp != '+' && prevOp != '-')
}

fun applyOperator(operator: Char, b: Double, a: Double): Double {
    return when (operator) {
        '+' -> a + b
        '-' -> a - b
        '*' -> a * b
        '/' -> {
            if (b == 0.0) throw ArithmeticException("Division by zero")
            a / b
        }
        else -> 0.0
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCalculatorApp() {
    Surface(color = Color.Black) {
        CalculatorApp()
    }
}


