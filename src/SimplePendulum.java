import org.apache.commons.math3.ode.*;
import org.apache.commons.math3.ode.nonstiff.*;
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
public class SimplePendulum implements FirstOrderDifferentialEquations{

    double l; // Length
    public static final double g = 9.807;  // earth gravity m·s^-2
    ExpandableStatefulODE efode;

    public SimplePendulum(){
        this(1.0);
    }

    public SimplePendulum(double l){
        this.l = l;
        efode = new ExpandableStatefulODE(this);
    }

    public int getDimension(){return 2;}

    public void computeDerivatives(double t, double[] x, double[] dxdt){
        dxdt[0] = x[1];
        dxdt[1] = -g/l*Math.sin(x[0]);
    }

    public static void main(String[] args){
        // Create the pendulum and set its initial state and time: x(0)=[0,.0]
        SimplePendulum sp = new SimplePendulum();
        sp.efode.setTime(0.0);
        sp.efode.setPrimaryState(new double[] {0.0,0.1});
        EmbeddedRungeKuttaIntegrator dopr853 = new DormandPrince853Integrator(
            1.0e-8, 1, 1.0e-10, 1.0e-10);
        EulerIntegrator euler = new EulerIntegrator(0.01);
        euler.addStepHandler(new PrintStepHandler());
        euler.integrate(sp.efode, 10.0);
    }
}
