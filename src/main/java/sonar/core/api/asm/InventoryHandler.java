package sonar.core.api.asm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**use this with {@link IInventoryHander}*/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface InventoryHandler {

	/**specify the MODID required for the handler to load*/
	String modid();

	/**the identification string of the TileHandler*/
	String handlerID();
}
