# Ollama 3.2 AI Project

Welcome to the Ollama 3.2 AI Project! This project provides two main services:
1. **AI Prompt**: Ask a simple prompt and get an AI-generated response.
2. **AI CV/Resume Scanner**: Upload your CV/Resume along with a job description to get a score and suggestions for improvement.

## Features

- **AI Prompt**: Enter a prompt and receive an AI-generated response.
- **AI CV/Resume Scanner**: Upload your CV/Resume and a job description to get a detailed analysis and suggestions for improvement.
- **Responsive Design**: The UI is designed to be responsive and user-friendly.
- **Error Handling**: Displays error messages in a user-friendly manner.

## Technologies Used

- **Frontend**: HTML, CSS, JavaScript, Bootstrap
- **Backend**: Java Spring Boot
- **AI Integration**: Ollama 3.2 Free version

## Installation

### Prerequisites

- Java 11 or higher
- Maven
- Node.js and npm (for frontend dependencies)

### Steps

1. **Clone the repository**:
    ```sh
    git clone https://github.com/your-username/ollama3.2-ai-project.git
    cd ollama3.2-ai-project
    ```

2. **Backend Setup**:
    - Navigate to the backend directory:
      ```sh
      cd backend
      ```
    - Build the project using Maven:
      ```sh
      mvn clean install
      ```
    - Run the Spring Boot application:
      ```sh
      mvn spring-boot:run
      ```

3. **Frontend Setup**:
    - Navigate to the frontend directory:
      ```sh
      cd frontend
      ```
    - Install the dependencies:
      ```sh
      npm install
      ```
    - Start the frontend development server:
      ```sh
      npm start
      ```

## Usage

### AI Prompt

1. Enter your prompt in the input box.
2. Click the "Ask AI" button to get a response.
3. The response will be displayed below the input box.

### AI CV/Resume Scanner

1. Enter the job description in the text area.
2. Click the paperclip icon to upload your CV/Resume (PDF format only).
3. Click the "Scan CV by AI" button to get the analysis.
4. The score and suggestions will be displayed below the text area.

## Screenshots

### AI Prompt
![AI Prompt](screenshots/ai-prompt.png)

### AI CV/Resume Scanner
![AI CV/Resume Scanner](screenshots/ai-cv-scanner.png)

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request.

## License

This project is licensed under the MIT License. See the LICENSE file for details.

## Contact

For any questions or feedback, please contact:
- **Name**: Yaseen Sheikh
- **Email**: yaseen@example.com

---

Thank you for using the Ollama 3.2 AI Project!
