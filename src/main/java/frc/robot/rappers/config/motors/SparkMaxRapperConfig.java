package frc.robot.rappers.config.motors;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import frc.robot.rappers.motors.SparkMaxRapper;

public class SparkMaxRapperConfig {
    private SparkMaxRapper sparkMax = null;
    public boolean inverse = false;
    public boolean brakeAtIdle = true;
    public int followId = -1;

    public SparkMaxRapperConfig(SparkMaxRapper sparkMax) {
        this.sparkMax = sparkMax;
    }

    public static SparkMaxRapperConfig extractConfig(SparkMaxRapper sparkMax) {
        final SparkMax rawMax = sparkMax.getSparkMax();
        final SparkMaxRapperConfig config = new SparkMaxRapperConfig(sparkMax);

        config.inverse = rawMax.configAccessor.getInverted();
        config.brakeAtIdle = rawMax.configAccessor.getIdleMode() == IdleMode.kBrake ? true : false;
        config.followId = rawMax.isFollower() ? rawMax.configAccessor.getFollowerModeLeaderId() : -1;
        return config;
    }

    public boolean compare(SparkMaxRapperConfig config) {
        return inverse == config.inverse && brakeAtIdle == config.brakeAtIdle && followId == config.followId;
    }

    public void apply() {
        if (this.compare(SparkMaxRapperConfig.extractConfig(this.sparkMax))) return;

        SparkMaxConfig sparkMaxConfig = new SparkMaxConfig();
        sparkMaxConfig.inverted(this.inverse);
        sparkMaxConfig.idleMode(this.brakeAtIdle ? IdleMode.kBrake : IdleMode.kCoast);
        if (followId == -1) sparkMaxConfig.disableFollowerMode(); else sparkMaxConfig.follow(followId);
        sparkMax.getSparkMax().configure(sparkMaxConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
    }
}
