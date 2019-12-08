package wargame.config;

import java.io.Serializable;

/**
 * Represente un slot de sauvegarde
 */
public class SavedConfig implements Serializable {
	private static final long serialVersionUID = -6544400139883295851L;
	private IConfig config;

	public SavedConfig() {
	}

	/**
	 * @return si une configuration est pr�sente dedans
	 */
	public boolean isActive() {
		return config != null;
	}

	/**
	 * d�finir la configuration sauvegard�
	 * 
	 * @param config
	 *            la sauvegarde
	 */
	public void setConfig(IConfig config) {
		this.config = config;
	}

	/**
	 * @return la sauvegarde ou null si vide
	 */
	public IConfig getConfig() {
		return config;
	}
}
