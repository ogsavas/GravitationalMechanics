
package gravitationalmechanics;
import java.awt.*; //needed for graphics


public class Planet {

    double xCoor,yCoor, rad, mass, xVel, yVel;  
    Color col;
    
    //constructor for Planet object
    public Planet (double x, double y, double r, double m, double vx, double vy, Color c) {
        this.xCoor = x;
        this.yCoor = y;
        this.rad = r;
        this.mass = m;
        this.xVel = vx;
        this.yVel = vy;
        this.col = c; 
        
    }
    
    //calculates vector between planets
    public Vector vecBetween (Planet other) {
        return new Vector( this.xCoor - other.xCoor, this.yCoor - other.yCoor);
    }
    
    //checks for any collisions
    public static boolean collisionCheck (Planet p1, Planet p2, double dist){
            
        if ( dist <=  p1.rad + p2.rad ){        
            return true;
        }
        else{     
            return false;
        }
    }

    //adds planets' masses together into 1 bigger planet
    public static void merge (Planet p1, Planet p2){
        if (p1.mass >= p2.mass) {
            p1.mass += p2.mass;
            p1.rad += p2.rad/2;
            GravitationalMechanics.planets.remove(p2);
        }
        else{
            p2.mass += p1.mass;
            p2.rad += p1.rad/2;
            GravitationalMechanics.planets.remove(p1);
        }
    }


    //collides the two given planets by calculating the momentum change/transfer
    public static void collide (Planet p1, Planet p2, double dist, Vector vec){
        
        double cosVal = vec.xComponent/dist;
        double sinVal = vec.yComponent/dist;
                      
        double P1CalcVelX = (2*p2.mass/(p1.mass+p2.mass)) * p2.xVel;
        double P1CalcVelY = (2*p2.mass/(p1.mass+p2.mass)) * p2.yVel;
        
        double xVelChangeP1 = Math.abs(cosVal * P1CalcVelX) + Math.abs(cosVal * P1CalcVelY);
        double yVelChangeP1 = Math.abs(sinVal * P1CalcVelX) + Math.abs(sinVal * P1CalcVelY);
        
        
        if (p1.xCoor < p2.xCoor){
            xVelChangeP1 = -xVelChangeP1;
        }
        
        if (p1.yCoor < p2.yCoor){
            yVelChangeP1 = -yVelChangeP1;   
        }
          
        double P2CalcVelX = (2*p1.mass/(p1.mass+p2.mass)) * p1.xVel;
        double P2CalcVelY = (2*p1.mass/(p1.mass+p2.mass)) * p1.yVel;
                
        double xVelChangeP2 = Math.abs(cosVal * P2CalcVelX) + Math.abs(cosVal * P2CalcVelY);
        double yVelChangeP2 = Math.abs(sinVal * P2CalcVelX) + Math.abs(sinVal * P2CalcVelY);

        if (p2.xCoor < p1.xCoor){
            xVelChangeP2 = -xVelChangeP2;
        }

        if (p2.yCoor < p1.yCoor){
            yVelChangeP2 = -yVelChangeP2;   
        }
        
        p1.changeVel(xVelChangeP1, yVelChangeP1);
        p2.changeVel(xVelChangeP2, yVelChangeP2);

    }
    
    //fixes any overlaps that may have occured during collision by moving planets away in relation to the amount that they have overlapped
    public static void fixOverlap (Planet p1, Planet p2, double dist, Vector vec){
        double currOverlap = dist - (p1.rad + p2.rad);
        double xOverlap = Math.abs(vec.xComponent/dist * currOverlap);
        double yOverlap = Math.abs(vec.yComponent/dist * currOverlap);
        
        
        
        double xOLP1 = xOverlap/2;
        double yOLP1 = yOverlap/2;
        double xOLP2 = xOLP1;
        double yOLP2 = yOLP1;
        
        if (p1.xCoor < p2.xCoor){
            xOLP1 = -xOLP1;
        }
        else if (p2.xCoor < p1.xCoor){
            xOLP2 = -xOLP2;
        }
        else{
            xOLP1 = 0;
            xOLP2 = 0;
        }
        
        if (p1.yCoor < p2.yCoor){
            yOLP1 = -yOLP1;   
        }
        else if (p2.yCoor < p1.yCoor){
            yOLP2 = -yOLP2;
        }
        else{
            yOLP1 = 0;
            yOLP2 = 0;
        }
        
        p1.changePos(xOLP1, yOLP1);
        p2.changePos(xOLP2, yOLP2);
        
        
    }
        
    //used to add onto or take away from each planet's velocities
    public void changeVel(double xV, double yV){    
        this.xVel += xV;
        this.yVel += yV;
    }
    
    //used to modify the positions of the planets
    public void changePos(double xP, double yP){    
        this.xCoor += xP;
        this.yCoor += yP;
        
    }
}