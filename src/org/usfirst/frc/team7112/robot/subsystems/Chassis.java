package org.usfirst.frc.team7112.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

import static org.usfirst.frc.team7112.robot.RobotMap.Chassis_Talon_FrontLeft;
import static org.usfirst.frc.team7112.robot.RobotMap.Chassis_Talon_FrontRight;
import static org.usfirst.frc.team7112.robot.RobotMap.Chassis_Talon_BackLeft;
import static org.usfirst.frc.team7112.robot.RobotMap.Chassis_Talon_BackRight;

import org.usfirst.frc.team7112.robot.commands.chassis.ArcadeDrive;

import static org.usfirst.frc.team7112.robot.RobotMap.Chassis_Encoder_Left_A;
import static org.usfirst.frc.team7112.robot.RobotMap.Chassis_Encoder_Left_B;
import static org.usfirst.frc.team7112.robot.RobotMap.Chassis_Encoder_Right_A;
import static org.usfirst.frc.team7112.robot.RobotMap.Chassis_Encoder_Right_B;

/**
 * Chassis 
 * @author Maarahot
 *
 */
public class Chassis extends Subsystem {

	//------Talons------//
	private SpeedController talon_FrontLeft;
	private SpeedController talon_FrontRight;
	private SpeedController talon_BackLeft;
	private SpeedController talon_BackRight;
	
	private SpeedControllerGroup leftMotors;
	private SpeedControllerGroup rightMotors;
	
	private static Chassis instance;
	
	//------Encoders------//
	private Encoder encLeft, encRight;
	
	private ADXRS450_Gyro gyro;
	
	private DifferentialDrive Driver;
	
	private boolean isInverted = false;
	private static final double kSlowDriveMultiplier = 0.38;
	private static final double kFastDriveMultiplier = 0.8;
	private static double driveMultiplier;
	
	private static final double kDistancePerPulse = 0.00446809;

	private Chassis() {
		//encoders
		encLeft = new Encoder(Chassis_Encoder_Left_A, Chassis_Encoder_Left_B);
		encRight = new Encoder(Chassis_Encoder_Right_A, Chassis_Encoder_Right_B);
		encLeft.setReverseDirection(true);
		encLeft.setDistancePerPulse(kDistancePerPulse);
		encRight.setDistancePerPulse(kDistancePerPulse);
		encLeft.reset();
		encRight.reset();

		//gyro
		gyro = new ADXRS450_Gyro(); // temp
		
		//Talons
		talon_FrontLeft = new WPI_TalonSRX(Chassis_Talon_FrontLeft);
		talon_FrontRight = new WPI_TalonSRX(Chassis_Talon_FrontRight);
		talon_BackLeft = new WPI_TalonSRX(Chassis_Talon_BackLeft);
		talon_BackRight = new WPI_TalonSRX(Chassis_Talon_BackRight);
		leftMotors = new SpeedControllerGroup(talon_FrontLeft, talon_BackLeft);
		rightMotors = new SpeedControllerGroup(talon_FrontRight, talon_BackRight);
		Driver = new DifferentialDrive(leftMotors, rightMotors);
		driveMultiplier = 0.7;
	}
	
	/**
	 * @returns drive's multiplier
	 */
	public double getDriveMultiplier() {
		return driveMultiplier;
	}

	/**
	* @returns slow drive's multiplier
	 */
	public double getSlowDriveMultiplier() {
		return kSlowDriveMultiplier;
	}

	/**
	 * @return fast drive's multiplier
	 */
	public double getFastDriveMultiplier() {
		return kFastDriveMultiplier;
	}

	public boolean isInverted() {
		return isInverted;
	}

	/**
	* @param mult - Sets drive's multiplier
	 */
	public void setDriveMultiplier(double mult) {
		driveMultiplier = mult;
	}
	
	// getters for sensors
	public double getDistance() {
		return (encLeft.getDistance() + encRight.getDistance()) / 2; //maybe + instead of -
	}

	/**
	* @returns robot's speed
	*/
	public double getSpeed() {
		return (encLeft.getRate() + encRight.getRate()) / 2;// maybe + instead of -
	}
	
	/**
	 * @returns The current distance that the right encoder recorded
	 */
	public double getDistanceR() {
		return encRight.getDistance();
	}
	
	/**
	 * @returns The current distance that the left encoder recorded
	 */
	public double getDistanceL() {
		return encLeft.getDistance();
	}
	
	/**
	 * @returns the angle that the robot's at
	 */
	public double getAngle() {
		return gyro.getAngle()*5.86;
	}
	
	
	//Resets the driving encoders
	public void resetEncoders() {
		encLeft.reset();
		encRight.reset();
	}
	
	
	//Resets gyro
	public void resetGyro() {
		gyro.reset();
	}
	

	/**
	 * Inverts the perspective point of the robot, and while driving towards the flat side of the robot the drive multiplier is lower.
	 */
	public void switchPerspectives(){
		leftMotors.setInverted(!leftMotors.getInverted());
		rightMotors.setInverted(!rightMotors.getInverted());
		isInverted = !isInverted;
		if(driveMultiplier == 0.){
			driveMultiplier = 0.7;
		}
		else 
			driveMultiplier = 0.5;
	}
	
	/**
	 * Stops the motors of the chassis
	 */
	public void stopMotors(){
		talon_BackLeft.stopMotor();
		talon_BackRight.stopMotor();
		talon_FrontLeft.stopMotor();
		talon_FrontRight.stopMotor();
	}

	public static final void init() {
		instance = new Chassis();
	}

	public static final Chassis getInstance() {
		return instance;
	}

	/**
	* @param power - Sets left motors's speed
	 */
	public void setLeftMotorsPower(double power){
		((BaseMotorController)talon_FrontLeft).set(ControlMode.Velocity,power);
		((BaseMotorController)talon_BackLeft).set(ControlMode.Follower, Chassis_Talon_FrontLeft);
	}
	
	/**
	* @param power - Sets right motors's speed
	 */
	public void setRightMotorsPower(double power){
		((BaseMotorController)talon_FrontRight).set(ControlMode.Velocity,power);
		((BaseMotorController)talon_BackRight).set(ControlMode.Follower, Chassis_Talon_FrontRight);
	}
	
	public void arcadeDrive(double forward, double side) {
		Driver.arcadeDrive(forward, side);
	}
	
	public void tankDrive(double speedLeft, double speedRight){
		Driver.tankDrive(speedLeft, speedRight);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new ArcadeDrive());
	}

}