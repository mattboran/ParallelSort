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
				"comments on your program goes here"
		};
		static int threshold = 1000;
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
		
		long start, 
					elapsedTimeSerialSort, elapsedTimeParallelMergeSort,
					elapsedTimeParallelQuickSort,elapsedTimeJavaParallelSort;
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
					 miSerialMax.setEnabled(true);
					 miMultiThreadedMax.setEnabled(true);
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
						
						start = System.currentTimeMillis();
						k.mergeSort(b);
						elapsedTimeSerialSort = System.currentTimeMillis() - start;
						
						repaint();
					}
				else
					
					if("MultiThreaded MergeSort".equals(command))
					{
						// create a new array, copy original array to it
						int[] b = new int[a.length];
						System.arraycopy(a, 0, b, 0, a.length);
						
						start = System.currentTimeMillis();
						ParallelMergeSort.startMainTask(b,threshold);
						
						elapsedTimeParallelMergeSort = System.currentTimeMillis()-start;
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
							
							start = System.currentTimeMillis();
//+++++++ need to develop this class
							
							
							elapsedTimeParallelQuickSort = System.currentTimeMillis()-start;
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
						
						start = System.currentTimeMillis();
						Arrays.parallelSort(b);
						elapsedTimeJavaParallelSort = System.currentTimeMillis()-start;
						
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
				g.drawString(
					"Number of processors is "+Integer.toString( Runtime.getRuntime().availableProcessors() ),300,130);
				g.drawString("Number of Data Items = "+Integer.toString(NDataItems),300, 150);
				g.drawString("Threshold = "+Integer.toString(threshold),300, 170);
				
				if( "Serial Sort".equals(command ) 
					|| "MultiThreaded MergeSort".equals(command)
					|| "MultiThreaded QuickSort".equals(command)
					|| "Java Parallel Sort".equals(command))
				{
					// develop
				}	
				
				else	
				if("About".equals(command))
				{
					int x = 200;
					int y = 200;
					for(int i = 0; i < description.length; i++)
					{
						g.drawString(description[i], x, y);
						y = y +25;
					}
				}
				else
					if("Initialize Array".equals(command))
					{
						g.drawString("Array Initialized",200, 100);
					}	
			}

public void InitializeArrays ()
{
	maximumSerial=	maximumParallel = -1;
	
	start = elapsedTimeSerialMax = elapsedTimeParallelMax =
				elapsedTimeSerialSort =  elapsedTimeParallelMergeSort = 
						elapsedTimeParallelQuickSort = elapsedTimeJavaParallelSort = 0;
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
		



