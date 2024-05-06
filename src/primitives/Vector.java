package primitives;

public class Vector extends Point {
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if(xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("ZERO vector is not allowed");
    }

    public Vector(Double3 double3) {
        super(double3);
        if(xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("ZERO vector is not allowed");
    }

   public Vector add(Vector other){
        return new Vector(this.xyz.add(other.xyz));
   }
   public Vector scale (double num){
        return new Vector(this.xyz.scale(num));
   }
   public double dotProduct(Vector other){
        return this.xyz.d1 * other.xyz.d1 + this.xyz.d2 * other.xyz.d2 + this.xyz.d3 * other.xyz.d3;
   }
   public Vector crossProduct(Vector other){
        double x1=this.xyz.d2 * other.xyz.d3;
        double y1=this.xyz.d3 * other.xyz.d2;
        double x2=this.xyz.d3 * other.xyz.d1;
        double y2=this.xyz.d1 * other.xyz.d3;
        double x3=this.xyz.d1 * other.xyz.d2;
        double y3=this.xyz.d2 * other.xyz.d1;
        return new Vector(x1-y1,x2-y2,x3-y3);
    }

    public double lengthSquared() {
        return xyz.d1 * xyz.d1 + xyz.d2 * xyz.d2 + xyz.d3 * xyz.d3;
    }

    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    public Vector normalize(){
     double len=this.length();
     return new Vector(xyz.reduce(len));
    }

    @Override
    public String toString() {
        return "Vector ("+ xyz.d1+ " , " + xyz.d2+ " , " +xyz.d3+")";
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
