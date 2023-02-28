#include <iostream>
#include <stack>
#include <string>
#include <sstream>
#include <fstream>

bool is_operator(const char &c) {
  return (c == '+' || c == '-' || c == '*' || c == '/');
}

bool is_operand(const char &c) {
  return ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'z'));
}

int precedence(const char &c) {
  if (c == '+' || c == '-') {
    return 1;
  } else if (c == '*' || c == '/') {
    return 2;
  } else {
    return 0;
  }
}

std::string infix_to_rpn(const std::string &infix) {
  std::stringstream ss(infix);
  std::stack<char> operators;
  std::string output;

  char c;
  char prev_c = ' ';

  while (ss >> c) {
    if (is_operand(c)) {
      if (is_operand(prev_c) || prev_c == ' ' || prev_c == '(' || prev_c == ')') {
        output += c;
      } else {
        output += " ";
        output += c;
      }
    } else if (is_operator(c)) {
      while (!operators.empty() && is_operator(operators.top()) && precedence(operators.top()) >= precedence(c)) {
        if (is_operand(prev_c)) {
          output += " ";
        }
        output += operators.top();
        output += " ";
        operators.pop();
      }
      operators.push(c);
    } else if (c == '(') {
      operators.push(c);
    } else if (c == ')') {
      while (!operators.empty() && operators.top() != '(') {
        output += " ";
        output += operators.top();
        operators.pop();
      }
      if (!operators.empty() && operators.top() == '(') {
        operators.pop();
      }
    }
    prev_c = c;
  }

  while (!operators.empty()) {
    output += " ";
    output += operators.top();
    operators.pop();
  }

  return output;
}

int main() {
  std::ifstream input_file("/home/x0lotl/Documents/GitHub/ukma_algorithm/ex-2/input.txt");
  if (!input_file) {
    std::cerr << "Unable to open input file" << std::endl;
    return 1;
  }
  std::ofstream output_file("/home/x0lotl/Documents/GitHub/ukma_algorithm/ex-2/output.txt");
  if (!output_file) {
    std::cerr << "Unable to open output file" << std::endl;
    return 1;
  }

  std::string inputLine;
  std::string outputLine;
  while (getline(input_file, inputLine)) {
    std::cout << "Input line: " << inputLine << std::endl;
    outputLine = infix_to_rpn(inputLine);
    std::cout << "RPN: " << outputLine << "\n" << std::endl;
    output_file << outputLine << std::endl;
  }
  input_file.close();
  output_file.close();
}