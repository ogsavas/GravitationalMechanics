
package gravitationalmechanics;

public class Vector {
    
    double xComponent, yComponent;
    
    //constructs the Vector object
    public Vector(double x, double y) {
        this.xComponent = x;
        this.yComponent = y;
    }

    /*
    public static double getAngle( Vector v ){
        return Math.atan( v.yComponent/v.xComponent );
    }
    */
      
    //used to get magnitude of a vector
    public double magnitude() {
        return Math.sqrt( this.xComponent*this.xComponent + this.yComponent*this.yComponent);
    }
}