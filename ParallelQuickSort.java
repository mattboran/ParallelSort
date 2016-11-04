//	This is the class file containing Parallel QuickSort
//
//	Written by Tudor Boran, 11/4/16
//	For Dr. Seyed - CSC 425-01
//	
//	
//


import java.util.Arrays;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

//This class implements a static class SortTask that implements RecursiveAction
//This class performs Quicksort in Parallel using Java's ForkJoinPool.
public class ParallelQuickSort {
	int[] list;
	int threshold;
	
	
	public ParallelQuickSort(int[] a, int threshold){
		this.list = a;
		this.threshold = threshold;
	}
	
	// 	This private method is called to partition the array given it by picking a left, right and pivot
	// 	It move the left_ptr and right_ptr towards each other to find a new pivot
	//
	//	Params: int[] list: the array we're sorting
	//			int left_prt: the left bound; this may not be necessary but we'll see
	//			int right_ptr: the right bound; this may also not be necessary
	//			int pivot: the pivot value.
	
	//	Returns: int - new pivot.
	public int partition(int[] list, int left_ptr, int right_ptr, int pivot){
		int iters = 500000000;
		while(iters > 0){
			iters--;
			//Move left_ptr rightwards until it is >= pivot
			while(list[left_ptr] < list[pivot] && left_ptr < list.length -1){
				left_ptr += 1;
			}
			//Move right_ptr leftwards until it is <= pivot
			while(list[right_ptr] > list[pivot] && right_ptr > 1){
				right_ptr -= 1;
			}
			//If the left and right pointers don't converge on the same spot
			if(left_ptr != right_ptr){
				//Swap list[left_ptr] and list[right_ptr]
				int rt_ptr_temp = list[right_ptr];
				list[right_ptr] = list[left_ptr];
				list[left_ptr] = rt_ptr_temp;
				left_ptr += 1;
				right_ptr -= 1;
			}else //left_ptr == right_ptr
			{
				return left_ptr;
			}
		}
		return -1;
	}
	public static void startMainTask(int[] list, int threshold){
		RecursiveAction mainTask = new SortTask(list, threshold);
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(mainTask);
	}
	
	private static class SortTask extends RecursiveAction{
		private int[] list;
		private int threshold;
		
		SortTask(int[] list, int threshold){
			this.list = list;
			this.threshold = threshold;
		}
		
		protected void compute(){
			if(list.length < threshold){
				Arrays.sort(list);
			}
		}
	}
}
