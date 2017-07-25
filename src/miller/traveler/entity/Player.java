package miller.traveler.entity;

import org.lwjgl.input.Keyboard;

import miller.traveler.SpriteSheet;
import miller.traveler.Traveler;
import miller.traveler.world.World;

public class Player extends LivingEntity {

	private SpriteSheet[] sheets;
	
	private int index;
	private int currentJumps;
	private int maxJumps;
	
	private boolean isFacingLeft;
	private boolean isJumping;
	private boolean isRolling;
	private boolean lock;
	
	public Player(float x, float y) {
		super(x, y, 32, 64);
		
		this.sheets = loadSheets();
		this.index = 0;
		this.maxJumps = 2;
		this.currentJumps = maxJumps;
		this.isFacingLeft = false;
		this.isJumping = false;
		this.isRolling = false;
		this.lock = false;
	}
	
	public void pollInput(int delta) {
		float adjustedSpeed = (speed * delta);
		boolean next = Keyboard.next();
		
		index = 0;
		
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			isFacingLeft = false;
			dx = adjustedSpeed;
			index = 1;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			isFacingLeft = true;
			dx = -adjustedSpeed;
			index = 1;
		} else {
			applyFriction(World.GROUND_FRICTION);
			index = (dx == 0) ? 0 : 2;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			index = 4;
		}
		
		if (next && Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			isRolling = true;
		} else if (sheets[3].hasCompletedAnimation()) {
			sheets[3].reset();
			isRolling = false;
		}
		
		if (next && Keyboard.isKeyDown(Keyboard.KEY_W)) {
			isFalling = true;
			
			if (!isJumping) {
				isJumping = true;
				dy = -adjustedSpeed;
				currentJumps--;
			} else if (isJumping && currentJumps > 0) {
				dy -= (adjustedSpeed / 2);
				currentJumps--;
			}
		} else if (y >= 656) {
			isJumping = false;
			isFalling = false;
			currentJumps = maxJumps;
			y = 656;
			dy = 0;
		} else {
			applyGravity(World.GRAVITY);
		}
		
		//Since rolling and jumping are locked, we need to check
		//if they are active to properly render.
		if (isJumping && isRolling) {
			index = 3;
		} else if (isJumping) {
			index = 2;
		} else if (isRolling) {
			index = 3;
		} 
	}
	
	@Override
	public void update(int delta) {		
		x += dx;
		y += dy;
	}
	
	@Override
	public void render() {
		sheets[index].play(isFacingLeft, x, y);
	}
	
	public SpriteSheet[] loadSheets() {
		SpriteSheet[] sheets = new SpriteSheet[] {
			new SpriteSheet(Traveler.loadTexture("res/models/player/idol.png"), 2, 32, 64, 8), //0
			new SpriteSheet(Traveler.loadTexture("res/models/player/walk.png"), 6, 32, 64, 5), //1
			new SpriteSheet(Traveler.loadTexture("res/models/player/jump.png"), 1, 32, 64),	   //2
			new SpriteSheet(Traveler.loadTexture("res/models/player/roll.png"), 6, 32, 64, 4), //3
			new SpriteSheet(Traveler.loadTexture("res/models/player/crouch.png"), 1, 32, 64)   //4
		};
		
		return sheets;
	}
	
	public boolean hasNextAnimation() {
		return (index > 0);
	}
}
