package org.rmr.lavadora.model;

public class TriangularMembershipFunction {
    private double a, b, c;

    public TriangularMembershipFunction(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double getMembership (double x) {
        /*if (x <= a || x >= c) {
            return 0.0;
        } else if (x > a && x <= b) {
            return (x - a) / (b - a);
        } else {
            return (c - x) / (c - b);
        }*/
        return (x <= a || x >= c) ? 0.0 :
                (x <= b) ? (x - a) / (b - a) : (c - x) / (c - b);
    }
}
