
package gravitationalmechanics;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.ArrayList;


public class GravitationalMechanics extends JFrame  {
    
    static ArrayList<Planet> planets = new ArrayList();     //creates an array list for planets so the user can add any planets they like

    int WIDTH = 1500;                              //width and height of screen
    int HEIGHT = 900;
    static int slowTime = 1;                       //can be used to adjust slow motion; does also increase precision
    static int fastTime = 3;                       //can be used to adjust fast motion; does also decrease precision


    static int framesPerSec = 100 * slowTime / fastTime;    //number frames that will be rendered per real time seconds, also used as a reference for time in calculations
                                                            //the higher the number, the more accurate the renderings will be as there will be less jumps between calculations

    static int sleepTime = (1000/framesPerSec) * slowTime / fastTime;     //calculates the sleep time for the program

    static boolean collisionOn = true;              //allows user to turn collisions on or off


    //draws all the planets once it is called
    public void paint(Graphics g) {
        //sleep(1);
        //g.setColor(Color.black);
        g.clearRect(0, 0, WIDTH, HEIGHT);

        for (int i = 0; i < planets.size(); i++) {
            double rad = planets.get(i).rad;
            double diam = rad*2;
            g.setColor(planets.get(i).col);
            g.fillOval((int) (planets.get(i).xCoor - rad), (int) (planets.get(i).yCoor - rad), (int) (diam), (int) (diam));

        }


    }

    //lets user add any planets they may want in the simulation
    //to add a planet, follow the model below:
    //
    //Planet planetName = new Planet(xCoordinate, yCoordinate, radius, mass, xVelocity, yVelocity, Color);
    //planets.add(planetName);
    //
    //or simply
    //
    //planets.add(new Planet(xCoordinate, yCoordinate, radius, mass, xVelocity, yVelocity, Color));
    public void addPlanets (){
        //planets.add(new Planet (#, #, #, #, #, #, Color.x));

    }

    
    //creates an array of planets with the same mass and radius
    public void arrayOfPlanetsSame (){
        int xA;
        int yA = 0;       
        for (int i = 0; i < 9; i++) {           
            yA += 100;
            xA = 250;
            for (int j = 0; j < 9; j++) {
                xA += 100;
                planets.add(new Planet (xA, yA, 20, 100000, 0, 0, Color.blue));
            }
        }
    }
   
    //creates an array of planets with randomized mass and radius
    public void arrayOfPlanetsRandom (){
        Random rand = new Random();
        int xB;
        int yB = 0;
        for (int i = 0; i < 9; i++) { 
            yB += 100;
            xB = 250;
            for (int j = 0; j < 9; j++) {
                int n = rand.nextInt(5) + 1; 
                int r = rand.nextInt(25) + 15;
                xB += 100;
                planets.add(new Planet (xB, yB, r, Math.pow(10, n), 0, 0, Color.blue));             
            }                  
        }
    }
    
    //creates a premade stable solar system
    public void addSolarSystem (){
        planets.add(new Planet (750, 500, 30, 10000000, 0, 0, Color.orange));
        planets.add(new Planet (750, 300, 15, 1000, 600, 0, Color.blue));
        planets.add(new Planet (750, 400, 10, 100, 900, 0, Color.red));
        planets.add(new Planet (750, 800, 20, 100, 500, 0, Color.cyan));
        planets.add(new Planet (750, 700, 5, 100, -600, 0, Color.magenta));
        planets.add(new Planet (750, 850, 10, 1000, 400, 0, Color.yellow));
      
    }

    public void addSolarSystemSameStart (){
        planets.add(new Planet (750, 500, 30, 10000000, 0, 0, Color.orange));
        planets.add(new Planet (750, 100, 10, 10, 420, 0, Color.blue));
        planets.add(new Planet (750, 200, 10, 10, 500, 0, Color.red));
        planets.add(new Planet (750, 300, 10, 10, 600, 0, Color.cyan));
        planets.add(new Planet (750, 400, 10, 10, 860, 0, Color.magenta));
    }

    public void addStarPlanetMoon(){
        planets.add(new Planet (750, 500, 40, 10000000, 0, 0, Color.orange));
        planets.add(new Planet (1100, 500, 5, 100000, 0, 440, Color.cyan));
        planets.add(new Planet (1120, 500, 3, 1, 0, 594, Color.magenta));
        planets.add(new Planet (400, 500, 5, 100000, 0, -440, Color.cyan));
        planets.add(new Planet (380, 500, 3, 1, 0, -594, Color.magenta));


    }
    
    public void addEllipticalOrbit () {
        planets.add(new Planet (750, 500, 30, 30000000, 0, 0, Color.orange));
        planets.add(new Planet (650, 400, 10, 1000, 1350, 0, Color.blue));

    }
    
    public void addBinaryStars() {
        planets.add(new Planet (700, 500, 12, 400000, 0, 125, Color.gray));
        planets.add(new Planet (800, 500, 12, 400000, 0, -125, Color.gray));
    }


    //checks for collisions which which are then calculated into the movement of the planets 
    //if there are no collisions, it calculates the gravitational mechanics of the simulation and updates the movements of the planets
    //the collisions override the gravity calculations as they may interfere with the collision calculations

    public void updateMovement (){
      
        for (int i = 0; i < planets.size(); i++) {
            Planet planet1 = planets.get(i);
            for (int j = 0; j < planets.size(); j++) {
                if (i != j){
               
                    Planet planet2 = planets.get(j);
                    
                    Vector vec = planet1.vecBetween(planet2);       //gets vector between planets
                    double dist = vec.magnitude();                  //gets distance betwenn planets
                    //double angle = Vector.getAngle(vec);            //gets angle of the vector between planets
                    
                    if (collisionOn == true && Planet.collisionCheck(planet1, planet2, dist) == true){  //checks for collisions, an if collisions are turned on, collides the two planets


                        Planet.collide(planet1, planet2, dist, vec);
                        //Planet.merge(planet1, planet2);
                        Planet.fixOverlap(planet1, planet2, dist, vec);
                  
                    }
                    else {
                        double force = Gravity.forceBetween( planet1, planet2, dist );                  //calculations for the gravitational simulation 
                        double accel = force/planet1.mass / framesPerSec;
                        double xVelChange = Math.abs(vec.xComponent/dist * accel);
                        double yVelChange = Math.abs(vec.yComponent/dist * accel);

                        if (planet1.xCoor > planet2.xCoor){
                            xVelChange = -xVelChange;
                        }

                        if (planet1.yCoor > planet2.yCoor){
                            yVelChange = -yVelChange;   
                        }
                       
                        planet1.changeVel(xVelChange, yVelChange);
                           
                    }         
                }
            }
            
            //calculates position change according to current velocity of planets
            //framesPerSec are used as a reference for time
            double xPosChange = planet1.xVel / framesPerSec;
            double yPosChange = planet1.yVel / framesPerSec;
            
            //updates the position using calculated values
            planet1.changePos(xPosChange, yPosChange);
            
        }
        
    }
    //stops program operation for a given duration
    public static void sleep(int duration) {
        try {
            Thread.sleep(duration);
        } 
        catch (Exception e) {}
    }
    
    //initializes window
    public void initWindow(){
        setBackground(Color.BLACK);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                       
        setVisible(true);
        sleep(250);
    }
            
        //user can activate any of the methods they want to use
    public static void main(String[] args) {
        GravitationalMechanics gm = new GravitationalMechanics();
      
        gm.initWindow();

        
        gm.addPlanets();
        //gm.addSolarSystem();
        //gm.addSolarSystemSameStart();
        //gm.addStarPlanetMoon();
        //gm.addEllipticalOrbit();
        //gm.arrayOfPlanetsSame();
        //gm.arrayOfPlanetsRandom();
        //gm.addBinaryStars();

        while (true) {      //runs program until stopped by the user
            gm.updateMovement();  
            gm.repaint();
            sleep(sleepTime);
            
        }
        
    }
    
}
