/*CSE 205: Class #11333 / Tuesday Thursday 4:30
 *Assignment: 6
 *Author(s): Harrison Hong / 1217745542
 *Description: Sort implements a quicksort algorithm to sort an ArrayList
 *of all Players from highest overall rating to lowest. It has the method
 *quicksort and the helper methods: quicksortHelper, partition, swap, and
 *print.
 */

import java.util.ArrayList;

public class Sort {
	//main quicksort method, takes the original list as an argument and prints the sorted list
	public static void quicksort(ArrayList<Player> aList) {
		ArrayList<Player> sorted_list = quicksortHelper(aList, 0, aList.size() - 1);
		print(sorted_list);
	}
	
	//helper method to create a sorted list
	public static ArrayList<Player> quicksortHelper(ArrayList<Player> aList, int low_index, int high_index){
		if(low_index == high_index) { //a list of size 1 is already sorted, base case for recursion
			return aList;
		}
		
		//determining the final index of the pivot
		int pivot_point = partition(aList, low_index, high_index);
		
		//recursively sorting the left and right partitions
		//if statements avoid out of bounds errors
		if(pivot_point != low_index) {
			quicksortHelper(aList, low_index, pivot_point - 1);
		}
		if(pivot_point != high_index) {
			quicksortHelper(aList, pivot_point + 1, high_index);
		}
		
		return aList;
	}
	
	//helper method that partitions a list, returning the final index of the pivot
	public static int partition(ArrayList<Player> aList, int low_index, int high_index) {
		//declaring and initializing the pivot, store, and compare points
		int pivot_point = high_index;
		int store_point = low_index;
		int compare_point = low_index;
				
		while(compare_point != pivot_point) { //loops while the compare point has not reached the pivot point
			if(aList.get(compare_point).getOverallRating() > aList.get(pivot_point).getOverallRating()) {
				swap(aList, compare_point, store_point); //if the compare value is smaller than the pivot, it gets swapped with the store value
				store_point++;
			}
			compare_point++; //advances the compare point
		}
		swap(aList, store_point, pivot_point); //the pivot value and store value are swapped
		return store_point; //returns the final position of the pivot
	}
	
	//helper method that swaps two values in a list
	public static void swap(ArrayList<Player> aList, int index1, int index2) {
		Player temp = aList.get(index1); //stores the value of index1 for swapping
		aList.set(index1, aList.get(index2));
		aList.set(index2, temp);
	}
	
	//additional method for printing the final rankings
	public static void print(ArrayList<Player> aList) {
		System.out.println("\nOverall Ranking of All Players");
		System.out.println("Rank Name              Overall Rating");
		for(int i = 0; i < aList.size(); i++) {
			System.out.printf("%-5d", i+1);
			System.out.printf("%-18s", aList.get(i).getName());
			System.out.printf("%-4.2f\n", aList.get(i).getOverallRating());
		}
	}
}
