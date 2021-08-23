package leetcode.stack;

import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/valid-parentheses/
 */
public class _20_ValidParentheses {

    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '[' || c== '{') {
                stack.push(c);
            } else {
                if(stack.isEmpty()) return false;
                char ret = stack.pop();
                if (c == ')' && ret != '(') return false;
                if (c == ']' && ret != '[') return false;
                if (c == '}' && ret != '{') return false;
            }
        }
        return stack.isEmpty();
    }

}
