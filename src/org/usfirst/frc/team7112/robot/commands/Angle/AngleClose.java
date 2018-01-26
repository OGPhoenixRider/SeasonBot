package org.usfirst.frc.team7112.robot.commands.Angle;

import org.usfirst.frc.team7112.robot.subsystems.Angle;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AngleClose extends Command {

    public AngleClose() {
        // Use requires() here to declare subsystem dependencies
        requires(Angle.getInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() { 
    	if(Angle.getInstance().getEncoder().getDistance() == 50.0){
    		Angle.getInstance().setMotorPower(Angle.getInstance().getSlow());
    	}
    	
    	else {Angle.getInstance().setMotorPower(-0.3);}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (Angle.getInstance().isNotPressed()){return true;}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
