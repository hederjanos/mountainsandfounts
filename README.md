# mountainsandfounts

# description

This application generates random but roughly lifelike terrain with hills and valleys. Its size can be parametrized.
The program finds one of the lowest point of the terrain and a spring bursts at this point and begins to fill the valleys.
The application simulates this process. The visualization can be streamed to the standard output or an animated gif can be created.

# rules:

- the edges of the terrain are considered to be as infinitely high walls
- water can only flow into the 4 adjacent areas if it has reached its height or is lower than the neighbor
- if water cannot flow anywhere, the water level will increase
- water flows into an adjacent valley and begins to saturate, so only the lowest water level always increases

# example:
![](C:\JAVAPROG\progmasters\mountainsandfounts\simulation.gif)simulation.gif
