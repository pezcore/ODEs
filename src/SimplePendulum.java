import org.apache.commons.math3.ode.*;
import org.apache.commons.math3.ode.nonstiff.*;
import org.apache.commons.math3.exception.*;

import java.util.Arrays;
/**
A so-called "simple pendulum" is an idealization of a "real pendulum" but in an
isolated system using the following assumptions:

The rod or cord on which the bob swings is massless, inextensible and
always remains taut;

The bob is a point mass;

Motion occurs only in two dimensions, i.e. the bob does not trace an
ellipse but an arc.

The motion does not lose energy to friction or air resistance.

The gravitational field is uniform.

The support does not move.

The differential equation which represents the motion of a simple pendulum is

θ'' = - g / l sin(θ)
*/
public class SimplePendulum implements MainStateJacobianProvider{

    double l; // Length
    public static final double g = 9.807;  // earth gravity m·s^-2
    public ExpandableStatefulODE efode;
    public JacobianMatrices jm;

    public SimplePendulum(){
        this(1.0);
    }

    public SimplePendulum(double l){
        this.l = l;
        efode = new ExpandableStatefulODE(this);
        // create the variational equations and append them to the main
        // equations, as secondary equations
        jm = new JacobianMatrices(this);
        jm.registerVariationalEquations(efode);
    }

    public int getDimension(){return 2;}

    public void computeDerivatives(double t, double[] x, double[] dxdt){
        dxdt[0] = x[1];
        dxdt[1] = -g/l*Math.sin(x[0]);
    }

    public void computeMainStateJacobian(double t, double[] x, double[] xDot,
        double[][] dfdx) throws MaxCountExceededException,
            DimensionMismatchException{
        dfdx[0][0] = 0;
        dfdx[0][1] = 1;
        dfdx[1][0] = -g/l*Math.cos(x[0]);
        dfdx[1][1] = 0;
    }

    public static void main(String[] args){
        // Create the pendulum and set its initial state and time: x(0)=[0,0.1]
        SimplePendulum sp = new SimplePendulum(2);
        sp.efode.setTime(0.0);
        sp.efode.setPrimaryState(new double[] {0.0,0.2});
        System.out.println("Initialization");
        System.out.printf("t = %10.5f\n",sp.efode.getTime());
        System.out.printf("Primary State = %s\n",
            Arrays.toString(sp.efode.getPrimaryState()));
        System.out.printf("secondary state = %s\n",
            Arrays.toString(sp.efode.getSecondaryState(0)));
        System.out.printf("total State Dimension is %d\n",
            sp.efode.getTotalDimension());
        for(int ii = 0; ii < 80; ii++) System.out.printf("-");
        System.out.printf("\n");
        EmbeddedRungeKuttaIntegrator dopr853 = new DormandPrince853Integrator(
            1.0e-8, 0.1, 1.0e-10, 1.0e-10);
        EulerIntegrator euler = new EulerIntegrator(0.01);
        dopr853.addStepHandler(new PrintStepHandler(sp.jm));
        dopr853.integrate(sp.efode, 10.0);
        
    }
}
