import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.nonstiff.DormandPrince853Integrator;

public class CircleODE implements FirstOrderDifferentialEquations {

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
        yDot[0] = omega * (c[1] - y[1]);
        yDot[1] = omega * (y[0] - c[0]);
    }

	public static void main(String[] args){
		DormandPrince853Integrator dp853 = new DormandPrince853Integrator(
			1.0e-8, 100.0, 1.0e-10, 1.0e-10);
		CircleODE ode = new CircleODE(new double[] {1,1}, 0.1);

		double[] y = new double[] { 0.0, 1.0 }; // initial state

		for (double t=0; t < 1<<15; t+=0.3){
			dp853.integrate(ode, t, y, t+0.3, y);
			System.out.printf("t = %.3f\ty = [%.3f,%.3f]\n",t,y[0],y[1]);
		}
	}
}
