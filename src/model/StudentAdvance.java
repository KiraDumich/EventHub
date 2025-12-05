package model;

public class StudentAdvance extends  Advance {
    public StudentAdvance(int days) {
        super(days);
    }

    @Override
    public double getPrice() {
        return super.getPrice() / 2.0;
    }

    @Override
    public String getType() {
        return "StudentAdvance";
    }
}
