/**
Write a program to examine the outputs from a sequence of operations on a data structure called a single-output deque, and deduce the sequence of operations that produced that output.

An ordinary deque is data structure that represents a list and supports the following four operations:
pushFront(x): add x to the beginning of the list so that it becomes the first element of the list
pushBack(x): add x to the end of the list so that it becomes the last element of the list
popFront(): remove and return the first element of the list
popBack(): remove and return the last element of the list

In this problem, we are considering the behavior of a single-output deque, which is the same as a deque except that it supports only pushFront, pushBack, and popBack.
Furthermore, we modify pushFront and pushBack so that they do not accept an argument. Instead, pushFront and pushBack push the contents of a counter (whose initial value is 1) to the beginning or end of the deque and increment the counter.

Consider the following examples:
(pushBack, pushBack, pushFront) results in the following sequence of list states:
(1)
(1, 2)
(3, 1, 2)
(pushFront, pushFront, pushBack, popBack, pushBack, popBack) results in the following sequence of states and outputs:
(1)
(2, 1)
(2, 1, 3)
(2, 1) output: 3
(2, 1, 4)
(2, 1) output 4
In the previous example, the output of the entire sequence of operations can be written as (3, 4).
Your program will receive exactly one line of input, a comma-separated list of integers with no spaces in the following format:
x_1,x_2,...,x_N
where 1 <= N <= 100,000 and the sequence (x_1, x_2,..., x_N) is a permutation of the set of integers {1, 2,..., N} (i.e., the sequence is not empty, and there are no repeated or missing values).
Your program should produce exactly one line of output, a comma-separated list of operations with no spaces in the following format:
op_1,op_2,...,op_2N
where each op_i is a string from the set {pushFront, pushBack, popBack}. The output of the sequence of operations (op_1, op_2,..., op_2N) should be the sequence (x_1, x_2,..., x_N). If it is not possible to produce the output (x_1, x_2,..., x_N) with any sequence of single-output deque operations, simply print the string “impossible”. If there are multiple sequences of operations that result in the sequence received in the input, choose the output that is smallest lexicographically, ordered by standard alphabetical ordering.

Sample Testcases
Input:
3,2,1
Output:
pushBack,pushBack,pushBack,popBack,popBack,popBack

Input:
1,2,3
Output (note choice of pushBack over pushFront):
pushBack,popBack,pushBack,popBack,pushBack,popBack

Input:
4,1,5,2,3
Output (pushFront is needed in some cases):
pushBack,pushFront,pushFront,pushBack,popBack,popBack,pushBack,popBack,popBack,popBack

Input:
5,1,4,2,3
Output (some sequences are impossible):
impossible

To help with solving this problem in a timely manner, we provide the following hints that may help you work out one way of writing an efficient program to solve this problem:
1. Note that if the first element of the input sequence is 4, then the 5th element of the output must be popBack (unless the output is impossible). The reason for this is that 
at least 4 pushes must be executed to get 4 into the deque, and the 4th push must be a pushBack so that 4 is ready to be popped from the back of the deque. More than 4 pushes 
could be executed, but elements 5 and above must be pushed to the front, and this could easily be done after 4 was popped instead of before. Since popBack is lexicographically 
smaller than pushFront, we prefer to execute popBack as early as possible.
2. Consider simulating the deque as a way to efficiently determine which operations were performed on it. For example, as above, if the first element of the input is a 4, 
simulate a deque having the elements 1 through 4 pushed into it. Since you do not know whether each element was pushed to the front or the back, try pushing it on both sides 
and figuring out which side is correct later in the simulation.
**/

//All of the number show in order (from 1 to n)
//If a number x is shown, then all of the numbers before x should all been added to deque as well. For current number x, 
//loop over all numbers smaller than or equal to x, if its order is before x, then push to back of queue; if its order is after x, then push to the front of queue.
//If the last element of queue equals to current number, pop back the last element; else output "impossible" 

public static void main(String[] args){
	Scanner sc = new Scanner(System.in);
	String input = sc.nextLine();
	String[] strs = strs.split(",");
	int n = strs.length;

	TreeMap<Integer, Integer> tm = new TreeMap<>();

	for(int i = 0; i < n; i++){
		int val = Integer.parseInt(strs[i]);
		tm.put(val, i);
	}


	Deque<Integer> deque = new LinkedList<>();
	StringBuilder output = new StringBuilder();

	Set set = tm.entrySet();
	Iterator i = set.iterator();

	int num = 1;
	while(i.hasNext()){
		Map.Entry me = (Map.Entry)i.next();
		for(; num <= me.getKey(); num++){
			if(deque.isEmpty() || tm.get(deque.peekLast()) > tm.get(num)){
				deque.offerLast(num);
				output.append("pushBack,");
			} else {
				deque.offerFirst(num);
				output.append("pushFront,");
			}
		}
		if(deque.isEmpty() || (deque.peekLast() != me.getKey())){
			System.out.print("impossible");
		}
		deque.pollLast();
		output.append("popBack,");
	}

	System.out.print(output.toString().substring(0, output.length() - 1));
	return;
}



