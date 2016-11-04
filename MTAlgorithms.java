	// GUI-related imports

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

	public class  MTAlgorithms extends Frame implements ActionListener
	{
		String[] description = new String[] {
				"This is Tudor Boran's submission to this Parallel Algorithms Assignment",
				"I have implemented QuickSort in Parallel using the Hoare Partition Scheme. This was a helpful",
				" exercise for me, because my independent study involves several implementations of ",
				"the Path Tracing algorithm for generating realistic 3D images:",
				"in serial C++, serial Rust, (possibly serial Java), Multithreaded C++ with OpenMP,",
				" Multithreaded C++ with std::thread, Multithreaded Rust with std::sync and std::thread,",
				"(possibly Multithreaded Java), and fully parallel on GPU with NVIDIA CUDA and AMD OpenCL.",
				" ",
				" I also really enjoy algorithm implementation, and am hoping to get into systems programming",
				" by going for a Masters in CS in either Systems or Distributed/Parallel Software Engineernig.",
				" ",
				" Unless I get into Cornell's Computer Graphics PhD program :)",
				
		};
		
		// These are the thresholds that will be tested for Parallel Merge and Quick sort. 
		// This list can be increased 
		static int[] threshold = new int[]{ 1000, 10000, 100000, 1000000, 2500000, 5000000 };
		static int num_tests = threshold.length;
		// Retrieved command code
				
		boolean arrayInitialized = false;
		int NDataItems = 10000000;
		int[] a = new int[NDataItems];
		
		int maximumSerial;
		int maximumParallel;
		
		MenuItem miAbout,
		         miInitArr,
		         miSerialSort,
		         miMultiThreadedMergeSort,
		         miMultiThreadedQuickSort,
		         miJavaParallelSort;
		
		
		
		long[] start = new long[num_tests];
		long[] elapsedTimeSerialSort = new long[num_tests];
		long[] elapsedTimeParallelMergeSort = new long[num_tests];
		long[] elapsedTimeParallelQuickSort = new long[num_tests];
		long[] elapsedTimeJavaParallelSort = new long[num_tests];
		
		
		String command = "";
			
		public static void main(String[] args)
		{
			Frame frame = new  MTAlgorithms();
			
			frame.setResizable(true);
			frame.setSize(800,500);
			frame.setVisible(true);
			
		}
		
		public  MTAlgorithms()
		{
			setTitle("Parallel Algorithms");
			
			
			// Create Menu Bar
			   			
			MenuBar mb = new MenuBar();
			setMenuBar(mb);
						
			Menu menu = new Menu("Operations");
			
			// Add it to Menu Bar
						
			mb.add(menu);
			
			// Create Menu Items
			// Add action Listener 
			// Add to "File" Menu Group
			
			miAbout = new MenuItem("About");
			miAbout.addActionListener(this);
			menu.add(miAbout);
			
		    miInitArr = new MenuItem("Initialize Array");
			miInitArr.addActionListener(this);
			menu.add(miInitArr);
			
			
			miSerialSort = new MenuItem("Serial Sort");
			miSerialSort.addActionListener(this);
			miSerialSort.setEnabled(false);
			menu.add(miSerialSort);
			
			miMultiThreadedMergeSort = new MenuItem("MultiThreaded MergeSort");
			miMultiThreadedMergeSort.addActionListener(this);
			miMultiThreadedMergeSort.setEnabled(false);
			menu.add(miMultiThreadedMergeSort);
			
			miMultiThreadedQuickSort = new MenuItem("MultiThreaded QuickSort");
			miMultiThreadedQuickSort.addActionListener(this);
			miMultiThreadedQuickSort.setEnabled(false);
			menu.add(miMultiThreadedQuickSort);
			
			miJavaParallelSort = new MenuItem("Java Parallel Sort");
			miJavaParallelSort.addActionListener(this);
			miJavaParallelSort.setEnabled(false);
			menu.add(miJavaParallelSort);
			
			MenuItem miExit = new MenuItem("Exit");
			miExit.addActionListener(this);
			menu.add(miExit);
				
			// End program when window is closed
			
			WindowListener l = new WindowAdapter()
			{
							
				public void windowClosing(WindowEvent ev)
				{
					System.exit(0);
				}
				
				public void windowActivated(WindowEvent ev)
				{
					repaint();
				}
				
				public void windowStateChanged(WindowEvent ev)
				{
					repaint();
				}
			
			};
			
			ComponentListener k = new ComponentAdapter()
			{
				public void componentResized(ComponentEvent e) 
				{
	        		repaint();           
	    		}
			};
			
			// register listeners
				
			this.addWindowListener(l);
			this.addComponentListener(k);

		}
		
	//******************************************************************************
	//  called by windows manager whenever the application window performs an action
	//  (select a menu item, close, resize, ....
	//******************************************************************************

		public void actionPerformed (ActionEvent ev) 
			{
				// figure out which command was issued
				
				command = ev.getActionCommand();
				
				// take action accordingly
				
				
				if("About".equals(command))
				{
					
					repaint();
				}
				else
				if("Initialize Array".equals(command))
				{
					InitializeArrays();
					arrayInitialized = true;
					// miSerialMax.setEnabled(true);
					// miMultiThreadedMax.setEnabled(true);
			         miSerialSort.setEnabled(true);
			         miMultiThreadedMergeSort.setEnabled(true);
			         miMultiThreadedQuickSort.setEnabled(true);
			         miJavaParallelSort.setEnabled(true);
					repaint();
				}
				else
					
					if("Serial Sort".equals(command))
					{
						MergeSort k = new MergeSort();
						int[] b = new int[a.length];
						System.arraycopy(a, 0, b, 0, a.length);
						
						start[0] = System.currentTimeMillis();
						k.mergeSort(b);
						elapsedTimeSerialSort[0] = System.currentTimeMillis() - start[0];
						
						repaint();
					}
				else
					
					if("MultiThreaded MergeSort".equals(command))
					{
						// create a new array
						int[] b = new int[a.length];
						
						for(int i = 0; i < num_tests; i++){
							//copy original array to new array (which is sorted after the 1st time this loop executes
							System.arraycopy(a, 0, b, 0, a.length);
							
							start[i] = System.currentTimeMillis();
							ParallelMergeSort.startMainTask(b,threshold[i]);
						
							elapsedTimeParallelMergeSort[i] = System.currentTimeMillis()-start[i];
						}
						if (isSorted(b))
							repaint();
						else
							System.out.println("Array is not sorted ---- multiThreaded MergeSort");
					}
					else
						if("MultiThreaded QuickSort".equals(command))
						{
							// create a new array, copy original array to it
							int[] b = new int[a.length];
							System.arraycopy(a, 0, b, 0, a.length);
							
							start[0] = System.currentTimeMillis();
//+++++++ need to develop this class
							
							
							elapsedTimeParallelQuickSort[0] = System.currentTimeMillis()-start[0];
							if (isSorted(b))
								repaint();
							else
								System.out.println("Array is not sorted ---- multiThreaded MergeSort");
						}
				else
					if("Java Parallel Sort".equals(command))
					{
						//create a new array, copy original array to it
						int[] b = new int[a.length];
						System.arraycopy(a, 0, b, 0, a.length);
						
						start[0] = System.currentTimeMillis();
						Arrays.parallelSort(b);
						elapsedTimeJavaParallelSort[0] = System.currentTimeMillis()-start[0];
						
						repaint();
					}
				else
					if("Exit".equals(command))
					{
						System.exit(0);
					}

			}
	//********************************************************
	// called by repaint() to redraw the screen
	//********************************************************
			
			public void paint(Graphics g)
			{
				Font title_font = new Font("TimesRoman", Font.BOLD, 16);
				g.setFont(title_font);
				g.setColor(Color.BLACK);
				g.drawString(
					"Number of processors is "+Integer.toString( Runtime.getRuntime().availableProcessors() ),300,130);
				g.drawString("Number of Data Items = "+Integer.toString(NDataItems),300, 150);
				g.drawString("Threshold = "+Integer.toString(threshold[0]),300, 170);
				
				if( "Serial Sort".equals(command ) )
				{
					int x = 150;
					int y = 230;
					g.drawLine(100,175, 600, 175);
					g.drawLine(100,250, 600, 250);
					g.drawString("Serial MergeSort", x, y);
					g.drawString(Long.toString(elapsedTimeSerialSort[0])+"ms", x+300, y);
				}
				else if( "MultiThreaded MergeSort".equals(command))
				{
					int x = 150;
					int y = 230;
					g.drawLine(100,175, 600, 175);
					g.drawLine(100,y+10, 600, y+10);
					g.drawString("Parallel MergeSort",  x + 150, y - 20);
					g.drawString("Threshold", x, y);
					g.drawString("Elapsed Time", x+300, y);
					for(int i = 0; i < num_tests; i++){
						y += 30;
						g.drawString(Long.toString(threshold[i]), x,  y);
						g.drawString(Long.toString(elapsedTimeParallelMergeSort[i])+"ms", x+300,y);
					}
				}
				else if( "MultiThreaded QuickSort".equals(command))
				{
					
				}
				else if( "Java Parallel Sort".equals(command))
				{
					int x = 150;
					int y = 230;
					g.drawLine(100,175, 600, 175);
					g.drawLine(100,250, 600, 250);
					g.drawString("Java Parallel Sort", x, y);
					g.drawString(Long.toString(elapsedTimeJavaParallelSort[0])+"ms", x+300, y);
				}
				else if("About".equals(command))
				{
					g.setColor(Color.WHITE);
					g.fillRect(0, 0, getWidth(), getHeight());
					g.setColor(Color.BLACK);
					int x = 100;
					int y = 100;
					for(int i = 0; i < description.length; i++)
					{
						g.drawString(description[i], x, y);
						y = y +25;
					}
				}
				else
					if("Initialize Array".equals(command))
					{
						g.setFont(new Font("TimesRoman", Font.ITALIC, 16));
						g.setColor(Color.RED);
						g.drawString("Array Initialized",200, 100);
					}	
			}

public void InitializeArrays ()
{
	maximumSerial=	maximumParallel = -1;
	for(int i = 0; i < num_tests; i++){
		start[i] = elapsedTimeSerialSort[i] =  elapsedTimeParallelMergeSort[i] = 
						elapsedTimeParallelQuickSort[i] = elapsedTimeJavaParallelSort[i] = 0;
	}
	for (int i=0; i<a.length; i++)
		a[i] = (int) (Math.random()*400000000);
}
public boolean isSorted(int[] list)
{
	boolean sorted = true;
	int index = 0;
	while (sorted & index<list.length-1)
	{
		if (list[index] > list[index+1])
			sorted = false;
		else
			index++;	
	}
	return sorted;
}
}
		



