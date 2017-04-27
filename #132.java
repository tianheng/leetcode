/**
132. Palindrome Partitioning II

Given a string s, partition s such that every substring of the partition is a palindrome.

Return the minimum cuts needed for a palindrome partitioning of s.

For example, given s = "aab",
Return 1 since the palindrome partitioning ["aa","b"] could be produced using 1 cut.
**/


/**
 create a int array minCut to cache the minimum cuts needed for a palindrome partition for string from position j to the
 end, initialize it with maximum number of possible cuts: length of substring - 1
 
 minCut[0] is the minimum cuts needed for a palindrome partition for the entire string, namely what we want to return
 
 create a 2-d boolean array to cache whether string is palindrome from position j to position i 
 
 for substring from position j to the end:
 try every partition possible i:
 if substring(j, i) is palindrome, as long as minimum cut cached is smaller than 1 + minimum cut for substring(i, end), update it
 edge case: if i reaches to end of string, then no minimum cut is needed for substring(j, i)
**/

public int minCut(String s) {
        if(s == null || s.length() <= 1) return 0;
        int n = s.length();
        int[] minCut = new int[n]; 
        boolean[][] isPalindrome = new boolean[n][n];

        for(int j = n - 1; j >= 0; j--){
            minCut[j] = n - j - 1;
            for(int i = j; i < n; i++){
                if(s.charAt(j) == s.charAt(i) && ((j + 1 >= i - 1) || isPalindrome[j + 1][i - 1])){
                    isPalindrome[j][i] = true;
                    if(i == n - 1) minCut[j] = 0;
                    else minCut[j] = Math.min(minCut[j], minCut[i + 1] + 1);
                }
            }
        }

        return minCut[0];
	}



