package org.rmr.lavadora.model;

public class TrapezoidalMembershipFunction {
    private double a, b, c, d;

    public TrapezoidalMembershipFunction(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public double getMembership(double x) {
        /*if (x <= a || x >= d) {
            return 0.0;
        } else if (x >= b && x <= c) {
            return 1.0;
        } else if (x > a && x < b) {
            if (a == b)  return 1.0;
            return (x - a) / (b - a);
        } else {
            if (c == d) return 1.0;
            return (d - x) / (d - c);
        }*/

        if (x <= a || x >= d) return 0.0;
        if (x >= b && x <= c) return 1.0;
        return (x < b) ?
                ((a == b) ? 1.0 : (x - a) / (b-a)) :
                ((c == d) ? 1.0 : (d - x) / (d - c));
    }
}
