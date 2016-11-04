import java.util.Arrays;

public class SimpleQuickSort {

	public static void main(String[] args) {
		int[] a = new int[10];
		for(int i = 0; i < a.length; i++){
			a[i] = (int)(Math.random()*250);
		}
		for(int i = 0; i < a.length; i++){
			System.out.printf("%d, ", a[i]);
		}
		System.out.println("");
		int iters = 5;
		int left_ptr = 0;
		int rt_ptr = a.length-1-1;
		int pivot = a.length-1;
		while(iters > 0){
			iters--;
			while(a[left_ptr] < a[pivot] && left_ptr < a.length-2){
				left_ptr++;
				System.out.printf("Left ptr moved -> from %d to %d.\n", a[left_ptr-1], a[left_ptr]);
			}
			while(a[rt_ptr] > a[pivot] && rt_ptr > 1){
				rt_ptr--;
				System.out.printf("Rt ptr moved <- from %d to %d.\n", a[rt_ptr+1], a[rt_ptr]);
			}
			if(left_ptr != rt_ptr){
				int temp = a[rt_ptr];
				a[rt_ptr] = a[left_ptr];
				a[left_ptr] = temp;
				System.out.printf("Swapped %d for %d\nNew Array:\n", a[left_ptr], a[rt_ptr]);
				for(int i = 0; i < a.length; i++){
					System.out.printf("%d, ", a[i]);
				}
			}else {
				System.out.printf("Found new pivot at %d\n", left_ptr);
				System.out.printf("Swapping pivots - %d with %d" , a[pivot], a[left_ptr]);
			}
			
		}
		
		

	}

}
