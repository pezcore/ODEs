import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.nonstiff.DormandPrince853Integrator;

import static java.lang.Math.sin;
import static java.lang.Math.cos;

public class DoublePendulum implements FirstOrderDifferentialEquations{

    double L;   // Length of the upper arm
    double l1;  // Distance from point of suspension to center of mass of upper arm
    double I1;  // Moment of inertia of upper arm
    double l2;  // Distance from end of upper arm to center of mass of lower arm
    double I2;  // moment of inertia of lower arm
    double m1;  // mass of upper arm
    double m2;  // mass of lower arm
    double[] c = new double[5];     // Lagrang coefficients

    private static final double g = 9.80665;    // Gravitational acceleration

    public DoublePendulum(){
            this(1.0,0.5,1.0,0.5,1.0,2.0,2.0);
    }

    public DoublePendulum(double L, double l1, double I1, double l2, double I2,
    double m1, double m2){
        this.L = L;
        this.l1 = l1;
        this.l2 = l2;
        this.I1 = I1;
        this.I2 = I2;
        this.m1 = m1;
        this.m2 = m2;
        c[0] = m1*l1*l1/2 + I1/2 + m2*L*L/2;
        c[1] = m2*l2*l2/2 + I2/2;
        c[2] = m2*L*l2;
        c[3] = g*(m1*l1 + m2*L);
        c[4] = g*m2*l2;
    }

    public void computeDerivatives(double t, double[] x, double[] xDot){
        xDot[0] = x[1];
        xDot[1] =       2*c[1]*c[2]*sin(x[0]) +
                                c[2]*c[2]*x[1]*x[1]*sin(x[0]-x[2])*cos(x[0]-x[2]);
        xDot[2] = x[3];
        xDot[3] = 0;
    }

    public int getDimension(){
        return 4;
    }

    public static void main(String[] args){
        int φυκγ00 = 10;    // wow it works! 
        double BΘΘX = 1/20;   // wow it works! 
        DormandPrince853Integrator dopr853 = new DormandPrince853Integrator(
                1.0e-8, 100.0, 1.0e-10, 1.0e-10);
        DoublePendulum pend = new DoublePendulum();

        double[] x = new double[] {1,2,3,4};
        dopr853.integrate(pend, 0, x, 20, x);
    }
}
