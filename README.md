# Amazon Automation Testing Framework

## 📋 Project Overview
This is a comprehensive automation testing framework for Amazon's Add to Cart functionality using:
- **Selenium WebDriver 4.x**
- **TestNG** - Test execution framework
- **Cucumber BDD** - Behavior Driven Development
- **Page Object Model (POM)** - Design pattern
- **Data-Driven Testing** - Using JSON files
- **Extent Reports** - Advanced reporting

## 🏗️ Project Structure
AmazonAutomation/
├── src/
│ ├── main/java/
│ │ ├── pages/ # Page Object classes
│ │ ├── utils/ # Utility classes
│ │ └── constants/ # Constants
│ └── test/
│ ├── java/
│ │ ├── stepDefinitions/ # Cucumber step definitions
│ │ └── runners/ # TestNG runners
│ └── resources/
│ ├── features/ # Cucumber feature files
│ ├── config/ # Configuration files
│ └── testdata/ # Test data files
├── reports/ # Test execution reports
├── pom.xml # Maven dependencies
└── testng.xml # TestNG configuration
## 🚀 Prerequisites
- Java JDK 11 or higher
- Maven 3.6+
- Chrome/Firefox/Edge browser
- IDE (IntelliJ IDEA/Eclipse)

## 📦 Installation & Setup

### 1. Clone the repository
```bash
git clone <repository-url>
cd AmazonAutomation