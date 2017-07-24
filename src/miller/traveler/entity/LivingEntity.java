package miller.traveler.entity;

/**A LivingEntity is an Entity that can fight. They may use weapons and items and increase
 * their effectiveness over time. */
public abstract class LivingEntity extends BoundedEntity {
	
	public LivingEntity(float x, float y, int width, int height) {
		super(x, y, width, height);
	}
	
}
