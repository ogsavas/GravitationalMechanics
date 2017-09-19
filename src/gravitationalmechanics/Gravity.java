
package gravitationalmechanics;

public class Gravity{
    static double G = 6.67;     //the G-constant used in the calculations, actual G was too small
    
    //calculates the force of gravity between two given planets
    static public double forceBetween(Planet p1, Planet p2, double distance){   
        double rSquared = Math.pow(distance, 2);
        return (G * p1.mass * p2.mass) / rSquared;
    }
}
