import java.awt.Color;
import java.util.Arrays;

public class PlotCode {
	static FalconLinePlot fig3;
	static int i = 0;
	static int j = 0;
	public static void main(String[] args) {
		/***Poof Example***/
		//int i = 0;
		//Lets create a bank image
		fig3 = new FalconLinePlot(new double[][]{{0.0,0.0}});
		fig3.yGridOn();
		fig3.xGridOn();
		fig3.setYLabel("Y (feet)");
		fig3.setXLabel("X (feet)");
		fig3.setTitle("Top Down View of FRC Field - Blue Alliance (32ft x 27ft) \n shows the left and right wheel trajectories");
			
					
		//force graph to show field dimensions of 30ft x 27 feet
		double fieldWidth = 27.0;
		fig3.setXTic(0, 32, 1);
		fig3.setYTic(0, fieldWidth, 1);
		
		generateField();
		
	  	String desiredAuto = "CR";
	  	
    	double[][] runAuto;
    	double totalTime; //seconds
    	switch(desiredAuto) {
    	case "CL" :runAuto = new double[][]{
				{0,13},
				{3,13},
				{8,18},
				{11,18},
		}; totalTime = 3.1;
		break;
    	case "CR" :runAuto = new double[][]{
				{0,13},
				{3,13},
				{9,8},
				{11,8},
		}; totalTime = 3.1;
		break;
    	case "LR" :runAuto = new double[][]{
				{0,23},
				{10,23},
				{20,24},
				{20,3},
				{14,3},
				{14,7},
		}; totalTime = 13;
		break;
    	case "RL" :runAuto = new double[][] {
    		{0,4},
    		{10,4},
    		{20,3},
    		{20,24},
    		{14,24},
    		{14,20},
    	}; totalTime = 13;
    	break;
    	case "batshit":runAuto = new double[][]{
				{4, 8},
				{7, 2},
				{12, 8},
				{17, 14},
				{20, 8},
				{17, 2},
				{12, 8},
				{7, 14},
				{4, 8}
		};  totalTime = 10;
		break;
		default: runAuto = new double[][]{
			{0,23},
			{12,23},
	}; totalTime = 1.5;
    	}
	
	double timeStep = 0.1; //period of control loop on Rio, seconds
	double robotTrackWidth = 2.3208333; //distance between left and right wheels, feet
	
		FalconPathPlanner path = new FalconPathPlanner(runAuto);
		path.calculate(totalTime, timeStep, robotTrackWidth);

		//waypoint path
		fig3.addData(path.nodeOnlyPath,Color.blue,Color.green);

		//add all other paths
		fig3.addData(path.smoothPath, Color.blue, Color.blue);
		fig3.addData(path.leftPath, Color.red);
		fig3.addData(path.rightPath, Color.magenta);


		//Velocity
		FalconLinePlot fig4 = new FalconLinePlot(path.smoothCenterVelocity,null,Color.blue);
		fig4.yGridOn();
		fig4.xGridOn();
		fig4.setYLabel("Velocity (ft/sec)");
		fig4.setXLabel("time (seconds)");
		fig4.setTitle("Velocity Profile for Left and Right Wheels \n Left = Red, Right = Magenta");
		fig4.addData(path.smoothRightVelocity, Color.magenta, Color.magenta);
		fig4.addData(path.smoothLeftVelocity, Color.red, Color.red);
		
		System.out.println(Arrays.deepToString(path.smoothLeftVelocity));
		/*
		while (!(i>=path.smoothLeftVelocity.length)) {
			if (i<path.smoothLeftVelocity.length && j%5==0) {
			System.out.println(path.smoothLeftVelocity[i][1]);
				i++;
			}
			j++;
		System.out.println(j + "|" + i);
		}
		System.out.println("Length: " + path.smoothLeftVelocity.length);
		*/
	}

	static void generateField() {
		//lets add field markers to help visual
		double[][] redSwitch = new double[][]{
				{11.7, 7.105},
				{16.3, 7.105},
				{16.3, 19.895},
				{11.7, 19.895},
				{11.7, 7.105},
			};
		fig3.addData(redSwitch, Color.black);
							
		// Auto Line
		double[][] autoLine = new double[][] {{10,0}, {10, 27}};
		fig3.addData(autoLine, Color.black);
								
		// Mid Field Up
		double[][] midLineUp = new double[][] {{27,27}, {27, 21}};
		fig3.addData(midLineUp, Color.black);
				
		// Mid Field Down
		double[][] midLineDown = new double[][] {{27,0}, {27, 6}};
		fig3.addData(midLineDown, Color.black);
								
		// Scale Up
		double[][] scaleUp = new double[][] {
				{25, 18}, 
				{29, 18},
				{29, 21},
				{25, 21},
				{25, 18},
			};
		fig3.addData(scaleUp, Color.blue);
								
		// Scale Down
		double[][] scaleDown = new double[][] {
				{25, 6}, 
				{29, 6},
				{29, 9},
				{25, 9},
				{25, 6},
			};
		fig3.addData(scaleDown, Color.red);
								
		// Scale sides
		double[][] scaleSides = new double[][] {
				{26.28, 9}, 
				{27.72, 9},
				{27.72, 18},
				{26.28, 18},
				{26.28, 9},
			};
		fig3.addData(scaleSides, Color.black);
								
		// Null zone Up
		double[][] nullZoneUp = new double[][] {
				{25, 19.06}, 
				{24, 19.06},
				{24, 27},
				{30, 27},
				{30, 19.06},
				{29, 19.06},
			};
		fig3.addData(nullZoneUp, Color.black);
				
		// Null zone Down
		double[][] nullZoneDown = new double[][] {
			    {25, 7.94}, 
				{24, 7.94},
				{24, 0},
				{30, 0},
				{30, 7.94},
				{29, 7.94},
			};
		fig3.addData(nullZoneDown, Color.black);
				
		// Platform Zone
		double[][] platformZoneOne = new double[][] {{16.3, 7.94}, {24, 7.94}};
		fig3.addData(platformZoneOne, Color.blue);
				
		// Platform Zone
		double[][] platformZoneTwo = new double[][] {{16.3, 19.06}, {24, 19.06}};
		fig3.addData(platformZoneTwo, Color.blue);
				
		// Platform
		double[][] platform = new double[][] {{21.78, 7.94}, {21.78, 19.06}};
		fig3.addData(platform, Color.blue);
				
		// Cube Zone
		double[][] cubeZone = new double[][] {
				{11.7, 11.625}, 
				{8.2, 11.625},
				{8.2, 15.375},
				{11.7, 15.375},
			};
		fig3.addData(cubeZone, Color.blue);
				
		// Switch Plate One
		double[][] switchPlateOne = new double[][] {
				{12, 7.405}, 
				{16, 7.405},
				{16, 10.405},
				{12, 10.405},
				{12, 7.405},
			};
		fig3.addData(switchPlateOne, Color.red);
				
		// Switch Plate Two
		double[][] switchPlateTwo = new double[][] {
				{12, 19.595}, 
				{16, 19.595},
				{16, 16.595},
				{12, 16.595},
				{12, 19.595},
			};
		fig3.addData(switchPlateTwo, Color.blue);
		
		// Portal Bottom
		double[][] portalBottom = new double[][] {{0, 2.5}, {2.916, 0}};
		fig3.addData(portalBottom, Color.blue);
		
		// Portal Top
		double[][] portalTop = new double[][] {{0, 24.5}, {2.916, 27}};
		fig3.addData(portalTop, Color.blue);
		
		// Exchange Zone
		double[][] exchangeZone = new double[][] {
				{0, 14.5}, 
				{3, 14.5},
				{3, 18.5},
				{0, 18.5},
			};
		fig3.addData(exchangeZone, Color.blue);
		
		// Cube Zone cubes
		double[][] cubeZoneCubes = new double[][] {
				{11.7, 15.209}, 
				{10.62, 15.209},
				{10.62, 14.669},
				{9.54, 14.669},
				{9.54, 14.129},
				{8.46, 14.129},
				{8.46, 12.871},
				{9.54, 12.871},
				{9.54, 12.331},
				{10.62, 12.331},
				{10.62, 11.791},
				{11.7, 11.791},
			};
		fig3.addData(cubeZoneCubes, Color.green);
		
		// Cube One
		double[][] cubeOne = new double[][] {
				{16.3, 19.895}, 
				{17.38, 19.895},
				{17.38, 18.815},
				{16.3, 18.815},
			};
		fig3.addData(cubeOne, Color.green);
		
		// Cube Two
		double[][] cubeTwo = new double[][] {
				{16.3, 17.557}, 
				{17.38, 17.557},
				{17.38, 16.477},
				{16.3, 16.477},
			};
		fig3.addData(cubeTwo, Color.green);
		
		// Cube Three
		double[][] cubeThree = new double[][] {
				{16.3, 15.219}, 
				{17.38, 15.219},
				{17.38, 14.139},
				{16.3, 14.139},
			};
		fig3.addData(cubeThree, Color.green);
		
		// Cube Three
		double[][] cubeFour = new double[][] {
				{16.3, 12.861}, 
				{17.38, 12.861},
				{17.38, 11.781},
				{16.3, 11.781},
			};
		fig3.addData(cubeFour, Color.green);
		
		// Cube Five
		double[][] cubeFive = new double[][] {
				{16.3, 10.523}, 
				{17.38, 10.523},
				{17.38, 9.443},
				{16.3, 9.443},
			};
		fig3.addData(cubeFive, Color.green);
		
		// Cube Six
		double[][] cubeSix = new double[][] {
				{16.3, 8.185}, 
				{17.38, 8.185},
				{17.38, 7.105},
				{16.3, 7.105},
			};
		fig3.addData(cubeSix, Color.green);
	}
	}

