import org.apache.commons.math3.ode.sampling.StepHandler;
import org.apache.commons.math3.ode.sampling.StepInterpolator;

public class PrintStepHandler implements StepHandler{

    public void init(double t0, double[] y0, double t) {
    }

    public void handleStep(StepInterpolator interpolator, boolean isLast) {
        double   t = interpolator.getCurrentTime();
        double[] y = interpolator.getInterpolatedState();
        System.out.printf("t = %f\tx = [%f, %f, %f, %f]\n", t,
        y[0],y[1],y[2],y[3]);
    }
}
