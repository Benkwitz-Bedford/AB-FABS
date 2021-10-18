# AB-FABS
The project is built to help you interpret track data and generate representative models live or en masse in a reproducible manner.

Typical use case:

-From video
1.	Download ImageJ Fiji (Our last used version was 1.53f51, Java 1.8.0_172 64-bit)
2.	Input your chosen video and select TrackMate (follow their own tutorials for use we were on version 7.2.0)
a.	After identifying and linking your tracks offload them by selecting ‘Tracks’ and exporting the spots tab to csv. This is the file we will use as input for AB-FABS
3.	Open AB-FABS and select the ‘extrapolate from trackmate’ option, select your csv, it’ll output a framework ready version ending with ‘extrapolated’
4.	Again, open AB-FABS and select the ‘load sequential extraps’ option (so called because you can select several of these extrapolated files if you like and they run sequentially)
5.	From here you are at the live visualisation panel, you can select options for data gathering or manipulate and observe the tracks as they play out.   

 
-Generating a model
1.	Open AB-FABS and select the ‘run a live traj manipulation’ option, it will take you to the live panel
a.	‘Run a data gathering set’ gives the option to set model parameters before running large numbers or stochastic model runs
2.	Select cell options and environmental options to modify the model hyperparameters
3.	To generate data output, select the ‘Gather data’ option set your parameters and offload the results  
a.	For a data gathering set the dialogue happens automatically


Initial run selection options:

-run a live traj manipulation

	Live observation and entity/environmental manipulation for observation
  
-run a data gathering set

	Like a live run but it removes the visualisation to improve compute performance and gives options for multiple model runs
  
-run a live traj manipulation from details

As above live run except it uses the ‘details.txt’ file that is generated when you offload results to set the initial hyperparameters

-run a data gathering set from details

	Again, as above but this time for the en masse run.
  
-load sequential extraps

	Visualisation and data gathering for tracks derived from video data.
  
-extrapolate from trackmate

	Converting trackmate input to a format that the framework can interpret.
  

Live frame options:

-Cell options

	Opens the dialogue for setting entity parameters (cell is a legacy term from the cellular automaton implementation)
  
-Environmental Options

	Again, opens the dialogue for environmental hyperparameters such as cubic curves, entity distribution and attractive zones.
  
-Generate Heatmap

	Opens the heatmap window, you can modify heatmap parameters here (they also affect the mosaic heatmap)
  
-Tails

	Toggle entity positional tails.
  
-Circles

	Toggle entity shape.
  
-Background

	Add a background image.
  
-Mosaic heatmap

	Overlay the heatmap on the live panel and generate as it goes (WARNING high compute increase).
  
-Show id’s

	Toggles ids above entities.
  
-Show Overlappers

	Toggles entity colour change when overlapping. 
  
-Show Bez Strength

	Display cubic (or Bezier) curves and their strength relative to the input parameters, below average is red and average or above is green.
  
-Gather Data

	Opens the options panel for starting data tracking and offload.
  
-Show Zones

	Toggle visualisation of attractive or deflective zones.
  

Data feedback options:

	Remember that image offload will obey the parameters set in the left most panel .
  
-File offload

	Offload the full file structure (WARNING the app will hang until complete)
  
-Offload results

	Offload only results.
  
-Off load all images

	Offload only images.
  
-Off load current images

	Offloads only the currently displayed images.
  
-Off load track images

	Offloads each individual track as a separate image.
