package org.usfirst.frc.team7112.robot.commands.climber;

import org.usfirst.frc.team7112.robot.OI;
import org.usfirst.frc.team7112.robot.subsystems.Climber;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class UseTape extends Command {

    public UseTape() {
        requires(Climber.getInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(OI.getInstance().GetPOV() == 0){ //counter clockwise
    		Climber.getInstance().setTapeMotorsPower(-Climber.getInstance().getTapePowerModifierOpen());
    	}
    	else if(OI.getInstance().GetPOV() == 180){ //clockwise
    		Climber.getInstance().setTapeMotorsPower(Climber.getInstance().getTapePowerModifierClose());
    	}
    	else if(OI.getInstance().GetPOV() == -1){
    		Climber.getInstance().stopTapeMotors();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
