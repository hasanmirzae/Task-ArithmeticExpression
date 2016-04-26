package edu.home.task;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Task description:
 * Given a string as input type of:  answer = x_1, x_2, ..., x_n (list of of numbers seperated by ",")
 * Replace all the "," so that the arithmetic expression become equal to the answer.
 * Example:  12=1,15,3 => 1*15-3
 * 
 * @author Hassan Mirzaee
 *
 */
public class Main {


	public static void main(String[] args){
		String[] operations = new String[]{"+","-","*"};
		
		String exp = "12=1,15,1,2,3,2";
		String[] arr = exp.split("=");
		int answer = Integer.valueOf(arr[0].trim());
		List<Integer> nums = parseNumbers(arr[1]);
		findExpresion(operations, nums.size()-1, 0, "", answer, nums);
	}
	
	private static void findExpresion(String[] operations,int n, int k, String v,
			int answer,List<Integer> numbers){
		if (n<=0 || k<0)
			return;
		if (n==1){
			for (String op: operations){
				int out = calcExpr(parseOperations(op), new LinkedList<>(numbers));
				if (out==answer)
					System.out.println(answer + " = "+getExpresion((List<String>) parseOperations(op), numbers));
			}
			return;
		}
		if (k==n){
			int out = calcExpr(parseOperations(v), new LinkedList<>(numbers));
			if (out==answer)
				System.out.println(answer + " = "+getExpresion((List<String>) parseOperations(v), numbers));
			return;
		}
		if (k==(n-1)){
			for (String op: operations){
				int out = calcExpr(parseOperations(v+(v.isEmpty()?"":",")+op), new LinkedList<>(numbers));
				if (out==answer)
					System.out.println(answer + " = "+getExpresion((List<String>) parseOperations(v+(v.isEmpty()?"":",")+op), numbers));
			}
			return;
		}
		if (n>1){
			for (String op : operations){
				for (String p : operations){
					findExpresion(operations, n, k+2, v+(v.isEmpty()?"":",")+op+","+p,answer,numbers);
				}
			}			
		}
	}

	
	private static int calcExpr(Deque<String> opList, Deque<Integer> nums){
		if (opList.size()!=(nums.size()-1))
			throw new IllegalArgumentException("Invalid number of operations or number!");
		int res = 0;
		while(!opList.isEmpty()){
			String operation = opList.pollLast();
			if (operation.equals("*")){
				int a = nums.pollLast();
				int b = nums.pollLast();
				nums.addLast(a*b);
			}else if (operation.equals("+")){
				res += nums.pollLast();
			}else {
				res -= nums.pollLast();
			}
			if (opList.isEmpty() && nums.size()==1)
				res += nums.poll();
					
		}
		return res;
	}
	
	private static List<Integer> parseNumbers(String expr){
		List<Integer> numsList = new LinkedList<>();
		String[] nums = expr.split(",");
		for (int i=0; i<nums.length; i++){
			numsList.add(Integer.valueOf(nums[i].trim()));
		}
		return numsList;
	}
	
	private static Deque<String> parseOperations(String expr){
		Deque<String> opList = new LinkedList<>();
		String[] operations = expr.split(",");
		for (int i=0; i<operations.length; i++){
			opList.addLast(operations[i]);
		}
		return opList;
	}
	
	private static String getExpresion(List<String> operations, List<Integer> numbers){
		StringBuilder sb = new StringBuilder();
		sb.append(numbers.get(0));
		for (int i=0; i<operations.size(); i++){
			sb.append(" ");
			sb.append(operations.get(i));
			sb.append(" ");
			sb.append(numbers.get(i+1));
		}
		return sb.toString();
	}

}
