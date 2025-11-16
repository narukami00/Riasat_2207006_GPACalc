# 🎓 GPA Calculator

<div align="center">

![Java](https://img.shields.io/badge/Java-21%2B-orange?style=for-the-badge&logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-21.0.6-blue?style=for-the-badge&logo=java&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.8%2B-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)

**A modern, intuitive, and visually stunning JavaFX application for calculating Grade Point Average (GPA)**

[Features](#-features) • [Installation](#-installation) • [Usage](#-usage) • [Screenshots](#-screenshots) • [Contributing](#-contributing)

</div>

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Key Features](#-features)
- [Technology Stack](#-technology-stack)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [Usage](#-usage)
- [Project Structure](#-project-structure)
- [Grading System](#-grading-system)
- [Export Features](#-export-features)
- [Screenshots](#-screenshots)
- [Build & Run](#%EF%B8%8F-build--run)
- [Contributing](#-contributing)
- [Troubleshooting](#-troubleshooting)
- [License](#-license)
- [Contact](#-contact)

---

## 🌟 Overview

**GPA Calculator** is a comprehensive desktop application designed to help students, educators, and academic institutions calculate Grade Point Average with ease and precision. Built with modern JavaFX, it offers a sleek, user-friendly interface with professional-grade PDF export capabilities.

Whether you're tracking your semester performance, planning your academic goals, or generating official transcripts, GPA Calculator provides all the tools you need in one elegant package.

### 🎯 Why Choose GPA Calculator?

- **🎨 Beautiful UI**: Modern, gradient-based design with smooth animations
- **📊 Visual Excellence**: Certificate-style results page that looks professional
- **📄 PDF Export**: Generate stunning, print-ready academic reports
- **✨ Intuitive**: Simple three-step process from input to results
- **🔒 Accurate**: Precise calculations with built-in validation
- **💾 Lightweight**: Fast and responsive with minimal system requirements

---

## ✨ Features

### 🏠 Home Screen
- Clean, welcoming interface with modern design
- Large, clear call-to-action button
- Emoji-enhanced visual appeal
- Responsive layout that scales beautifully

### 📝 Course Entry
- **Dynamic Course Management**: Add unlimited courses with ease
- **Real-time Credit Tracking**: Monitor your progress as you add courses
- **Edit & Delete**: Full CRUD operations on course entries
- **Smart Validation**: Prevents invalid data entry and credit overflow
- **Professional Table View**: Alternating row colors for easy reading
- **Grade Selection**: Dropdown with standard letter grades (A+ to F)
- **Credit Limit Control**: Strict enforcement of total credit limits
- **Reset Functionality**: Start over with a single click

### 🏆 Results Display
- **Certificate-Style Layout**: Professional academic achievement presentation
- **Prominent GPA Display**: Large, bold GPA score with context
- **Visual Hierarchy**: Color-coded sections for easy scanning
- **Detailed Breakdown**: Complete course-by-course analysis
- **Trophy Icon**: Celebratory design elements
- **Motivational Messages**: Encouraging feedback for students

### 📄 PDF Export
- **Professional Certificate Design**: Print-ready academic reports
- **Trophy Header**: Official-looking top section
- **Bordered Title**: Academic achievement certificate styling
- **Color-Coded Information**: Visual distinction between sections
- **Comprehensive Table**: All course details with alternating row colors
- **Metadata**: Automatic timestamp and generation info
- **High Quality**: Vector-based PDF for perfect printing

---

## 🛠️ Technology Stack

### Core Technologies
- **Java**: 21+ (LTS)
- **JavaFX**: 21.0.6
- **Maven**: Build automation and dependency management
- **iText7**: Professional PDF generation

### Key Libraries
- **JavaFX Controls**: UI components
- **JavaFX FXML**: Declarative UI design
- **ControlsFX**: Enhanced UI controls
- **FormsFX**: Form handling
- **ValidatorFX**: Input validation
- **iText Kernel**: PDF core functionality
- **iText Layout**: PDF layout engine
- **iText IO**: PDF I/O operations

### Development Tools
- **IntelliJ IDEA**: Recommended IDE
- **Maven Wrapper**: Included for easy builds
- **Git**: Version control

---

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

### Required
- **Java Development Kit (JDK)**: Version 21 or higher
  - Download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
  - Verify installation: `java -version`

### Optional (Recommended)
- **IntelliJ IDEA**: Community or Ultimate Edition
- **Git**: For cloning the repository
- **Windows OS**: Batch launcher provided (works on other OS with minor adjustments)

---

## 📥 Installation

### Option 1: Clone with Git

```bash
# Clone the repository
git clone https://github.com/narukami00/Riasat_2207006_GPACalc.git

# Navigate to project directory
cd Riasat_2207006_GPACalc

# Build the project
./mvnw clean install
```

### Option 2: Download ZIP

1. Click the green **Code** button on GitHub
2. Select **Download ZIP**
3. Extract to your desired location
4. Open terminal in the extracted folder

### Option 3: IntelliJ IDEA

1. **File** → **New** → **Project from Version Control**
2. Paste repository URL: `https://github.com/narukami00/Riasat_2207006_GPACalc.git`
3. Click **Clone**
4. Wait for Maven to import dependencies
5. Run the application

---

## 🚀 Usage

### Quick Start (Windows)

**Double-click `launch.bat`** in the project root directory!

### Manual Launch

#### Using Maven Wrapper

```bash
# Windows
mvnw.cmd javafx:run

# Linux/Mac
./mvnw javafx:run
```

#### Using Your Maven Installation

```bash
mvn javafx:run
```

### Step-by-Step Guide

#### 1️⃣ **Start the Application**
- Launch using one of the methods above
- You'll see the beautiful home screen

#### 2️⃣ **Enter Total Credits**
- Click **"Get Started ➜"**
- Enter your total credit hours (e.g., 15, 18, 21)
- Click **OK**

#### 3️⃣ **Add Your Courses**
- Fill in course details:
  - **Course Name**: Full course name (e.g., "Data Structures")
  - **Course Code**: Course identifier (e.g., "CSE201")
  - **Credit**: Credit hours (e.g., 3.0)
  - **Teacher 1**: Primary instructor (optional)
  - **Teacher 2**: Secondary instructor (optional)
  - **Grade**: Select from dropdown (A+, A, A-, B+, B, B-, C+, C, D, F)
- Click **"Add Course"**
- Repeat until you reach your total credits
- Use **Edit** or **Delete** buttons to modify entries

#### 4️⃣ **Calculate GPA**
- Once credits match or exceed total, **"Calculate GPA"** button activates
- Click to see your results

#### 5️⃣ **View & Export Results**
- Review your GPA and course breakdown
- Click **"💾 Export Results"** to save as PDF
- Choose save location
- Open PDF to view professional certificate

---

## 📁 Project Structure

```
GPA_Calculator/
│
├── 📄 launch.bat                      # Quick launcher (Windows)
├── 📄 pom.xml                         # Maven configuration
├── 📄 mvnw, mvnw.cmd                  # Maven wrapper scripts
├── 📄 README.md                       # This file!
│
├── 📂 src/main/
│   ├── 📂 java/
│   │   ├── 📂 com.project.gpa_calculator/
│   │   │   ├── 📄 MainApp.java        # Application entry point
│   │   │   │
│   │   │   ├── 📂 controllers/
│   │   │   │   ├── 📄 HomeController.java          # Home screen logic
│   │   │   │   ├── 📄 CourseEntryController.java   # Course management
│   │   │   │   └── 📄 ResultController.java        # Results & PDF export
│   │   │   │
│   │   │   └── 📂 model/
│   │   │       ├── 📄 Course.java     # Course data model
│   │   │       └── 📄 Grade.java      # Grade-to-point mapping
│   │   │
│   │   └── 📄 module-info.java        # Java module configuration
│   │
│   └── 📂 resources/
│       └── 📂 com.project.gpa_calculator/
│           ├── 📄 Home.fxml           # Home screen UI
│           ├── 📄 CourseEntry.fxml    # Course entry UI
│           ├── 📄 Result.fxml         # Results display UI
│           │
│           └── 📂 css/
│               └── 📄 styles.css      # Application styles
│
└── 📂 target/                         # Compiled classes (generated)
```

---

## 📊 Grading System

The application uses a standard 4.0 GPA scale:

| Letter Grade | Grade Points |
|:------------:|:------------:|
| **A+**       | 4.00         |
| **A**        | 3.75         |
| **A-**       | 3.50         |
| **B+**       | 3.25         |
| **B**        | 3.00         |
| **B-**       | 2.75         |
| **C+**       | 2.50         |
| **C**        | 2.25         |
| **D**        | 2.00         |
| **F**        | 0.00         |

### GPA Calculation Formula

```
GPA = Σ(Grade Points × Credit Hours) / Total Credit Hours
```

**Example:**
```
Course 1: A+ (4.0) × 3 credits = 12.0
Course 2: A  (3.75) × 3 credits = 11.25
Course 3: B+ (3.25) × 3 credits = 9.75
                Total Credits = 9

GPA = (12.0 + 11.25 + 9.75) / 9 = 3.667
```

---

## 📄 Export Features

### Professional PDF Certificate

The exported PDF includes:

#### 🏆 Header Section
- Large trophy emoji (🏆)
- "ACADEMIC ACHIEVEMENT CERTIFICATE" title with blue border
- "Grade Point Average Report" subtitle
- Generation timestamp

#### 📊 GPA Display Box
- Bordered section with light blue background
- **CUMULATIVE GPA** label
- Large, bold GPA value (42pt font)
- "out of 4.00" context
- Total credits completed

#### 📚 Course Breakdown Table
- Professional header with dark blue background
- Alternating row colors (white/light blue)
- Columns: Course Name, Code, Credit, Grade, Points
- Center-aligned data for easy reading
- Clean typography with Arial font

#### ✅ Footer
- Motivational message: "Keep up the excellent work!"
- Generation metadata
- Professional styling

### Technical Specifications
- **Format**: PDF/A-compatible
- **Page Size**: US Letter (8.5" × 11")
- **Margins**: 50pt on all sides
- **Fonts**: System fonts with fallbacks
- **Colors**: RGB color space
- **Print Quality**: 300 DPI equivalent

---

## 🖼️ Screenshots

### Home Screen
```
┌─────────────────────────────────────────────┐
│                                             │
│              🎓 GPA Calculator              │
│                                             │
│                    📊                       │
│           Calculate Your GPA                │
│         Quick • Easy • Accurate             │
│                                             │
│          [  Get Started ➜  ]               │
│                                             │
│         ─────────────────────                │
│           Designed by Rafsan                │
└─────────────────────────────────────────────┘
```

### Course Entry Screen
```
┌─────────────────────────────────────────────┐
│   Course Entry                              │
│   ─────────────────────────────────         │
│                                             │
│   Course Name: [___________________]        │
│   Course Code: [___________________]        │
│   Credit:      [___________________]        │
│   Teacher 1:   [___________________]        │
│   Teacher 2:   [___________________]        │
│   Grade:       [▼ Select Grade ]           │
│                                             │
│   [ Add Course ]  [ Reset ]  [ Calculate ]  │
│                                             │
│   Credits: 12.0 / 15.0                      │
│   ─────────────────────────────────         │
│   │ Course  │ Code │ Credit │ Grade │...    │
│   ├─────────┼──────┼────────┼───────┼...    │
│   │ Math    │ M201 │  3.0   │  A+   │...    │
│   │ Physics │ P301 │  4.0   │  A    │...    │
└─────────────────────────────────────────────┘
```

### Results Screen
```
┌─────────────────────────────────────────────┐
│           🏆 Final Results                  │
│                                             │
│   ┌─────────────────────────────────────┐  │
│   │      YOUR GPA        TOTAL CREDITS  │  │
│   │        3.667     │      15.0 hrs    │  │
│   │      / 4.00      │                  │  │
│   └─────────────────────────────────────┘  │
│                                             │
│   📚  COURSE BREAKDOWN                      │
│   ────────────────────────────────          │
│   │ Course  │ Code │ Credit │ Grade │Points│
│   ├─────────┼──────┼────────┼───────┼──────│
│   │ Math    │ M201 │  3.0   │  A+   │ 4.00 │
│   │ Physics │ P301 │  4.0   │  A    │ 3.75 │
│                                             │
│   [ 💾 Export Results ] [ ← Back to Home ] │
└─────────────────────────────────────────────┘
```

---

## ⚙️ Build & Run

### Development Mode

```bash
# Clean and compile
./mvnw clean compile

# Run without building
./mvnw javafx:run

# Full build with tests
./mvnw clean install

# Clean target directory
./mvnw clean
```

### Production Build

```bash
# Create executable JAR
./mvnw clean package

# Run the JAR
java -jar target/GPA_Calculator-1.0-SNAPSHOT.jar
```

### IDE Configuration

#### IntelliJ IDEA
1. **Import Project**: Open `pom.xml` as project
2. **JDK Setup**: File → Project Structure → Project SDK → Choose JDK 21+
3. **Maven Sync**: Right-click `pom.xml` → Maven → Reload Project
4. **Run Configuration**:
   - Edit Configurations → Add New → Maven
   - Working Directory: Project root
   - Command: `javafx:run`

#### Eclipse
1. **Import**: File → Import → Maven → Existing Maven Projects
2. **Select Root**: Choose project folder
3. **Finish**: Wait for dependencies to download
4. **Run**: Right-click project → Run As → Maven build → `javafx:run`

#### VS Code
1. **Open Folder**: Open project directory
2. **Install Extensions**: Java Extension Pack, Maven for Java
3. **Run**: Press `F5` or use Maven sidebar

---

## 🤝 Contributing

We love contributions! Here's how you can help make GPA Calculator even better:

### Ways to Contribute

- 🐛 **Report Bugs**: Found a bug? [Open an issue](https://github.com/narukami00/Riasat_2207006_GPACalc/issues)
- 💡 **Suggest Features**: Have an idea? Share it with us!
- 📖 **Improve Documentation**: Help others understand the project
- 🎨 **Design**: Suggest UI/UX improvements
- 💻 **Code**: Submit pull requests with fixes or features

### Development Setup

```bash
# Fork the repository on GitHub
# Clone your fork
git clone https://github.com/YOUR_USERNAME/Riasat_2207006_GPACalc.git

# Create a branch
git checkout -b feature/amazing-feature

# Make your changes
# Test thoroughly

# Commit with descriptive message
git commit -m "Add amazing feature"

# Push to your fork
git push origin feature/amazing-feature

# Open a Pull Request on GitHub
```

### Coding Guidelines

- Follow existing code style
- Add comments for complex logic
- Update README for new features
- Test on multiple platforms if possible
- Keep commits atomic and focused

---

## 🔧 Troubleshooting

### Common Issues

#### ❌ "Java version not compatible"
**Solution**: Ensure JDK 21+ is installed
```bash
java -version  # Should show 21 or higher
```

#### ❌ "Module not found" errors
**Solution**: Reload Maven dependencies
```bash
./mvnw clean install -U
```
Or in IDE: Right-click `pom.xml` → Maven → Reload Project

#### ❌ "JavaFX runtime components are missing"
**Solution**: Use the Maven plugin to run
```bash
./mvnw javafx:run
```
Don't run the main class directly

#### ❌ PDF export fails
**Solution**: Check write permissions for save location
- Try saving to desktop first
- Ensure iText dependencies are loaded

#### ❌ Batch file doesn't work
**Solution**: 
- Right-click `launch.bat` → Edit
- Ensure `mvnw.cmd` path is correct
- Run from project root directory

#### ❌ Window resizes when navigating
**Solution**: This is expected behavior. Each view has its own layout requirements.

### Getting Help

- 📧 **Email**: Contact repository owner
- 💬 **Issues**: [GitHub Issues](https://github.com/narukami00/Riasat_2207006_GPACalc/issues)
- 📚 **Wiki**: Check project wiki for guides

---

## 📜 License

This project is licensed under the **MIT License** - see below for details:

```
MIT License

Copyright (c) 2024 Rafsan

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

### What This Means
- ✅ Commercial use allowed
- ✅ Modification allowed
- ✅ Distribution allowed
- ✅ Private use allowed
- ❌ No warranty
- ❌ No liability

---

## 📞 Contact

**Developer**: Rafsan  
**GitHub**: [@narukami00](https://github.com/narukami00)  
**Repository**: [GPA Calculator](https://github.com/narukami00/Riasat_2207006_GPACalc)

---

## 🙏 Acknowledgments

### Technologies
- **JavaFX** - For the amazing UI framework
- **iText** - For professional PDF generation
- **Apache Maven** - For dependency management
- **ControlsFX** - For enhanced UI controls

### Inspiration
Built with ❤️ to help students track their academic progress and generate professional-looking transcripts.

### Special Thanks
- All contributors who help improve this project
- The JavaFX community for excellent resources
- Students who provide feedback and feature requests

---

## 🎯 Roadmap

### Planned Features
- [ ] Multiple GPA scales (4.0, 5.0, 10.0)
- [ ] Semester-wise GPA tracking
- [ ] Cumulative GPA across semesters
- [ ] GPA prediction tool
- [ ] Dark mode theme
- [ ] Multiple language support
- [ ] Cloud backup/sync
- [ ] Mobile companion app
- [ ] Statistics and analytics
- [ ] Grade distribution charts

### In Consideration
- Web-based version
- Database integration
- Multi-user support
- Institution customization
- Email report functionality
- Calendar integration

---

## 📈 Changelog

### Version 1.0.0 (Current)
- ✨ Initial release
- 🎨 Modern UI with gradient design
- 📊 Dynamic course management
- 🏆 Certificate-style results page
- 📄 Professional PDF export
- ✅ Input validation
- 🔒 Credit limit enforcement
- 🪟 Windows batch launcher

---

<div align="center">

### ⭐ Star this repository if you find it helpful!

**Made with ☕ and 💻 by Rafsan**

[⬆ Back to Top](#-gpa-calculator)

---

[![Made with JavaFX](https://img.shields.io/badge/Made%20with-JavaFX-orange?style=flat-square&logo=java)](https://openjfx.io/)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)
[![GitHub issues](https://img.shields.io/github/issues/narukami00/Riasat_2207006_GPACalc?style=flat-square)](https://github.com/narukami00/Riasat_2207006_GPACalc/issues)

</div>
