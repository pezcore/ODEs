import org.apache.commons.math3.ode.sampling.StepHandler;
import org.apache.commons.math3.ode.sampling.StepInterpolator;
import java.util.Arrays;

public class PrintStepHandler implements StepHandler{

    public void init(double t0, double[] y0, double t) {
    }

    public void handleStep(StepInterpolator interpolator, boolean isLast) {
        double   t = interpolator.getCurrentTime();
        double[] y = interpolator.getInterpolatedState();
        double[] J = interpolator.getInterpolatedSecondaryState(0);
        System.out.printf("t = %f\tx = ", t);
        for(double f: y) System.out.printf("%15.10f, ", f);
        System.out.printf("J = %s",Arrays.toString(J));
        System.out.printf("\n");

    }
}
