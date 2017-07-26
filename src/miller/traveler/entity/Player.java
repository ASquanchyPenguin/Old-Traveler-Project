package miller.traveler.entity;

import org.lwjgl.input.Keyboard;

import miller.traveler.SpriteSheet;
import miller.traveler.Traveler;
import miller.traveler.tiles.TileType;
import miller.traveler.world.World;

public class Player extends LivingEntity {

	private SpriteSheet[] sheets;
	
	private boolean[] keys;
	
	private int action;
	private int jumps;
	private int maxJumps;
	
	private boolean isFacingLeft;
	private boolean isJumping;
	private boolean isRolling;
	
	public Player(float x, float y) {
		super(x, y, 32, 64);
		
		this.sheets = loadSheets();
		this.keys = new boolean[64];
		this.action = 0;
		this.jumps = 0;
		this.maxJumps = 2;
		this.isFacingLeft = false;
		this.isRolling = false;
		this.isJumping = false;
	}
	
	private void updateKeys() {
		for (int i = 0; i < keys.length; i++) {
			if (Keyboard.isKeyDown(i)) {
				keys[i] = true;
			} else {
				keys[i] = false;
			}
		}
	}
	
	public void pollInput(int delta) {
		updateKeys();
		
		float adjustedSpeed = (speed * delta);
		boolean next = Keyboard.next();
		
		//Prevent other animation when these are active.
		if (isRolling && isJumping) {
			action = 3;
			isRolling = !sheets[3].hasCompletedAnimation();
			return;
		} else if (isRolling) {
			isRolling = !sheets[3].hasCompletedAnimation();
			return;
		} 
				
		if (!next) {
			action = 0;
		}
		
		dx = 0;
		
		//Walking
		 if (keys[Keyboard.KEY_D]) {
			isFacingLeft = false;
			dx = adjustedSpeed;
			action = 1;
		} else if (keys[Keyboard.KEY_A]) {
			isFacingLeft = true;
			dx = -adjustedSpeed;
			action = 1;
		} 
		 
		//Crouching
		if (keys[Keyboard.KEY_S] || keys[Keyboard.KEY_SPACE]) {
			action = 4;
			dx = 0;
		}
		
		//Jumping
		if (next && keys[Keyboard.KEY_W]) {
			if (!isJumping) {
				dy = -adjustedSpeed * 1.35f;
				isFalling = true;
				isJumping = true;
				action = 2;
				jumps++;
			} else if (isJumping && (jumps < maxJumps)) {
				dy -= (adjustedSpeed / 2);
				jumps++;
			}
		}
		
		//While jumping and pluging
		if (isJumping && keys[Keyboard.KEY_SPACE]) {
			action = 4;
			dy = (adjustedSpeed * 3.5f);
		} else if (isJumping) {
			action = 2;
		}
		
		//Backstep and Rolling
		if (next && keys[Keyboard.KEY_LCONTROL]) {
			if (keys[Keyboard.KEY_D]) {
				dx = isJumping ? adjustedSpeed : (adjustedSpeed * 2);
				isRolling = true;
				action = 3;
			} else if (keys[Keyboard.KEY_A]) {
				dx = isJumping ? -adjustedSpeed : -(adjustedSpeed * 2);
				isRolling = true;
				action = 3;
			} else {
				if (!isJumping) {
					dx = isFacingLeft ? 10 : -10;
					action = 2;
				}
			}
		}
	}
	
	@Override
	public void update(int delta) {
		if (getBottomTile().getType() == TileType.BLANK) {
			isFalling = true;
			applyGravity(World.GRAVITY);
		} else if (isFalling) {
			if (getBottomTile().getType() != TileType.BLANK) {
				dy = 0;
				isFalling = false;
				if (isJumping) {
					isJumping = false;
					jumps = 0;
				}
			}
		}
						
		x += dx;
		y += dy;
	}
	
	@Override
	public void render() {
		sheets[action].play(isFacingLeft, x, y);
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
}
