package org.usfirst.frc.team7112.robot.commands.claw;
import org.usfirst.frc.team7112.robot.subsystems.Claw;

import edu.wpi.first.wpilibj.command.Command;

public class AutoOpenClaw extends Command {

    public AutoOpenClaw() {
        // Use requires() here to declare subsystem dependencies
        requires(Claw.getInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {}

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Claw.getInstance().switchPressed_Open())
    		Claw.getInstance().setMotorPower(Claw.getInstance().getSpeedmodifier());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !Claw.getInstance().switchPressed_Open();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Claw.getInstance().stopMotor();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {}

}