package wargame.assets;

/**
 * A interface to every object with icon
 */
public interface IIconObject {

	/**
	 * @return the icon of this objects
	 */
	ImageAsset getIcon();

	/**
	 * @return the icon when hover this object
	 */
	ImageAsset getIconHover();
	
	/**
	 * @return get the number of this element
	 */
	int ordinal();
}
