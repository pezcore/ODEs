import org.apache.commons.math3.ode.sampling.*;
import org.apache.commons.math3.ode.*;
import java.util.Arrays;

public class PrintStepHandler implements StepHandler{
    public JacobianMatrices jm;

    PrintStepHandler(JacobianMatrices jm){
        this.jm = jm;
    }
        
    public void init(double t0, double[] y0, double t) {
    }

    public void handleStep(StepInterpolator interpolator, boolean isLast) {
        double   t = interpolator.getCurrentTime();
        double[] y = interpolator.getInterpolatedState();
        double[] sec = interpolator.getInterpolatedSecondaryState(0);
        double[] secdot = interpolator.getInterpolatedSecondaryDerivatives(0);
        System.out.printf("%f\t", t);
        for(double f: y) System.out.printf("%10f, ", f);
        for(double f: sec) System.out.printf("%10f, ",f);
        for(double f: secdot) System.out.printf("%10f, ",f);

        // retrieve the Jacobian of the final state with respect to initial state
        double[][] dYdY0 = new double[2][2];
        jm.getCurrentMainSetJacobian(dYdY0);

        for(double[] fo: dYdY0)
            for(double f: fo)
                System.out.printf("%10f, ",f);

        System.out.printf("\n");
    }
}
