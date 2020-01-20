package frc.misc2020;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public abstract class AbstractPIDSource implements PIDSource {

    protected PIDSourceType type;

    public AbstractPIDSource(PIDSourceType sourceType) {
        type = sourceType;
    }

    @Override
    public void setPIDSourceType(PIDSourceType sourceType) {
        type = sourceType;
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return type;
    }

    @Override
    public abstract double pidGet();
}
