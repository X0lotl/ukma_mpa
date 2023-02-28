#include <iostream>
#include <stack>
#include <string>
#include <fstream>
#include <algorithm>

bool is_operand(const char &c) {
  return ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'z'));
}

std::string replaceSubstring(std::string str, std::string search, std::string replace) {
  size_t pos = 0;
  while ((pos = str.find(search, pos)) != std::string::npos) {
    str.replace(pos, search.length(), replace);
    pos += replace.length();
  }
  return str;
}

std::pair<std::string, std::string> findVariableInString(std::string str) {
  str.erase(std::remove_if(str.begin(), str.end(), [](char c) { return std::isspace(c); }), str.end());
  std::string key;
  std::string value;
  std::size_t pos = str.find("=");

  if (pos != std::string::npos) {
    key = str.substr(0, pos);
    value = str.substr(pos + 1);
  }

  return make_pair(key, value);
}

int evaluateRPN(std::string rpn) {
  std::stack<int> operands;


  for (int i = 0; i < rpn.size(); i++) {
    char c = rpn[i];

    if (is_operand(c)) {
      int num = c - '0';
      while (is_operand(rpn[++i])) {
        num = num * 10 + (rpn[i] - '0');
      }
      operands.push(num);
    }

    else if (rpn[i] != ' ') {
      int op2 = operands.top();
      operands.pop();
      int op1 = operands.top();
      operands.pop();

      switch (c) {
        case '+':
          operands.push(op1 + op2);
          break;
        case '-':
          operands.push(op1 - op2);
          break;
        case '*':
          operands.push(op1 * op2);
          break;
        case '/':
          operands.push(op1 / op2);
          break;
        default:
          throw std::invalid_argument("Invalid operator: " + c);
      }
    }
  }

  return operands.top();
}

int main() {
  std::ifstream input_file("/home/x0lotl/Documents/GitHub/ukma_algorithm/ex-22/input.txt");
  if (!input_file) {
    std::cerr << "Unable to open input file" << std::endl;
    return 1;
  }

  std::string inputLine;
  std::string variableLine;
  std::pair<std::string, std::string> inputVariable;
  int result;

  getline(input_file, inputLine);

  while (getline(input_file, variableLine)) {
    inputVariable = findVariableInString(variableLine);
    inputLine = replaceSubstring(inputLine, inputVariable.first, inputVariable.second);
  }

  std::cout << "RPN line: " << inputLine << std::endl;
  result = evaluateRPN(inputLine);
  std::cout << "Result: " << result << std::endl;


  input_file.close();
}
