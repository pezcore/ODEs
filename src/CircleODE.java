import org.apache.commons.math3.ode.*;
import org.apache.commons.math3.ode.nonstiff.DormandPrince853Integrator;;
import java.util.*;

public class CircleODE implements MainStateJacobianProvider{

    private double[] c;
    private double omega;

    public CircleODE(double[] c, double omega) {
        this.c     = c;
        this.omega = omega;
    }

    public int getDimension() {
        return 2;
    }

    public void computeDerivatives(double t, double[] y, double[] yDot) {
        // the state is a 2D point, the ODE therefore corresponds to the
        // velocity
        yDot[0] = omega * (c[1] - y[1]);
        yDot[1] = omega * (y[0] - c[0]);
    }

    public void computeMainStateJacobian(
    double t, double[] y, double[] yDot, double[][] dFdY) {

        // compute the Jacobian of the main state
        dFdY[0][0] = 0;
        dFdY[0][1] = -omega;
        dFdY[1][0] = omega;
        dFdY[1][1] = 0;
    }

    public static void main(String[] args){

        CircleODE circle = new CircleODE(new double[] {1.0,  1.0 }, 1);

        // here, we could select only a subset of the parameters, or use
        // another order
        JacobianMatrices jm = new JacobianMatrices(circle);

        ExpandableStatefulODE efode = new ExpandableStatefulODE(circle);
        efode.setTime(0);
        double[] y = { 1.0, 0.0 };
        efode.setPrimaryState(y);

        // create the variational equations and append them to the main
        // equations, as secondary equations
        jm.registerVariationalEquations(efode);

        // instantiate step handler
        PrintStepHandler psh = new PrintStepHandler();

        // integrate the compound state, with both main and additional
        // equations
        DormandPrince853Integrator integrator = new DormandPrince853Integrator(
            1.0e-6, 1.0e3, 1.0e-10, 1.0e-12);
        integrator.addStepHandler(psh);
        integrator.setMaxEvaluations(2<<10);
        integrator.integrate(efode, 20.0);

        // retrieve the Jacobian of the final state with respect to initial
        // state
        double[][] dYdY0 = new double[2][2];
        System.out.printf("Before getCurrentMainSetJacobian: [%f,%f\n%f,%f]\n",
        dYdY0[0][0],dYdY0[1][0],dYdY0[1][0],dYdY0[1][1]);

        jm.getCurrentMainSetJacobian(dYdY0);
    }
}
