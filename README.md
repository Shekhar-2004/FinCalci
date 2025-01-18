# <div align="center">Finance Calculator App</div>

<p align="center">
  <img src="app/src/main/ic_launcher-playstore.png" alt="App Icon" width="120" height="120">
</p>

<div align="center">
  <img src="https://img.shields.io/badge/Kotlin-1.8.0-blueviolet?style=for-the-badge&logo=kotlin" alt="Kotlin">
  <img src="https://img.shields.io/badge/Jetpack%20Compose-Modern%20UI%20Toolkit-green?style=for-the-badge&logo=android" alt="Jetpack Compose">
</div>

---

## 📱 About the App<br>
**Finance Calculator** is a feature-rich, Kotlin-powered Android application designed to simplify financial calculations. <br>
With a sleek UI built using Jetpack Compose, it offers essential tools for:<br>
- Performing basic arithmetic operations.<br>
- Calculating taxes as per Indian tax slabs.<br>
- Storing and managing calculation history.<br>

---

## 🔥 Features<br>
- **Standard Calculator Mode**: Basic arithmetic operations (add, subtract, multiply, divide).<br>
- **Tax Calculator Mode**: Tax calculation based on Indian tax slabs for incomes up to ₹15,00,000 and beyond.<br>
- **History Tracking**: Save and view previous calculations for both modes.<br>
- **Modern UI**: Intuitive design leveraging Jetpack Compose and Material Design.<br>

---

## 🚀 Installation<br>
1. **Clone the repository**:<br>
   `git clone https://github.com/Shekhar-2004/FinCalci.git`<br>
2. Open the project in Android Studio.<br>
3. Build and run on an emulator or physical device.<br>

---

## 💻 How to Use<br>
**1. Standard Calculator Mode**<br>
- Perform basic arithmetic operations.<br>
- Press `=` to calculate the result.<br>
- Access history using the "View History" button.<br><br>

**2. Tax Calculator Mode**<br>
- Enter income to calculate taxes as per Indian tax rules.<br>
- View detailed results, including applicable slabs and total tax.<br><br>

**3. History**<br>
- Switch between modes and view stored calculations.<br>

---

## 🛠️ Code Structure<br>
| File/Directory         | Description                                   |<br>
|-------------------------|-----------------------------------------------|<br>
| `MainActivity.kt`       | Entry point of the application.               |<br>
| `StandardCalculator.kt` | UI and logic for Standard Calculator mode.    |<br>
| `TaxCalculator.kt`      | UI and logic for Tax Calculator mode.         |<br>
| `HistoryScreen.kt`      | Displays history of calculations.             |<br>
| `ui/theme/`             | Custom themes for the app.                    |<br>
| `utility/`              | Utility functions like `calculateTax` and `evaluateExpression`. |<br>

---

## 💡 Tax Calculation Logic<br>
| Income Range           | Tax Rate   |<br>
|-------------------------|------------|<br>
| Up to ₹3,00,000        | 0%         |<br>
| ₹3,00,001 - ₹6,00,000  | 5%         |<br>
| ₹6,00,001 - ₹9,00,000  | 10%        |<br>
| ₹9,00,001 - ₹12,00,000 | 15%        |<br>
| ₹12,00,001 - ₹15,00,000| 20%        |<br>
| Above ₹15,00,000       | 30%        |<br>

---

## 📦 Dependencies<br>
- Kotlin<br>
- Jetpack Compose<br>
- Material Design 3<br>

---

## 🤝 Contributing<br>
1. Fork the repository.<br>
2. Create a new branch:<br>
   `git checkout -b feature-name`<br>
3. Commit changes:<br>
   `git commit -m "Add some feature"`<br>
4. Push the branch:<br>
   `git push origin feature-name`<br>
5. Open a pull request.<br>

---

## 📝 License<br>
This project is licensed under the [MIT License](LICENSE).<br>

---

## 📧 Contact<br>
For questions or feedback, contact me at: <a href="mailto:indushekharsingh3@gmail.com">indushekharsingh3@gmail.com</a><br>
